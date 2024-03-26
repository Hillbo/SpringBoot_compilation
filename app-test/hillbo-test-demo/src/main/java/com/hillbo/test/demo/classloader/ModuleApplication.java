package com.hillbo.test.demo.classloader;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ModuleApplication {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    // bean单例
    private final static String SINGLETON = "singleton";

    // 加载的jar包里面的class
    private static Map<String, Map<String, Class>> loadedJarClassMap = new HashMap<>();

    private static Map<String, ModuleClassLoader> loadedClassLoaderMap = new HashMap<>();

    /**
     * jar包加载
     *
     * @param jarName            jar包名称
     * @param url                jar包文件地址
     * @param applicationContext 上下文
     */
    public void loadJar(String jarName, URL url, ApplicationContext applicationContext) {

        boolean gcFlag = false;
        if (loadedClassLoaderMap.containsKey(jarName)) {
            ModuleClassLoader moduleClassLoaderOld = loadedClassLoaderMap.get(jarName);
            moduleClassLoaderOld = null;
            gcFlag = true;
        }

        if (loadedJarClassMap.containsKey(jarName)) {
            Map<String, Class> loadedClassMap = loadedJarClassMap.get(jarName);
            if (loadedClassMap.size() > 0) {
                for (Map.Entry<String, Class> entry : loadedClassMap.entrySet()) {
                    Class loadedClass = entry.getValue();
                    loadedClass = null;
                }
                gcFlag = true;
            }
        }

        if (gcFlag) {
            System.gc();
        }

        ModuleClassLoader moduleClassLoader = new ModuleClassLoader(jarName,
                new URL[]{url},
                loadedJarClassMap,
                applicationContext.getClassLoader()
        );

        loadedClassLoaderMap.put(jarName, moduleClassLoader);

        // 加载的class文件map
        Map<String, Class> loadClassMap = moduleClassLoader.loadClass();

        for (Map.Entry<String, Class> entry : loadClassMap.entrySet()) {
            log.info("loadedClass:{}, loadedClassHashCode:{}", entry.getKey(), entry.getValue().hashCode());
        }


        // 解析到的xml文件
        MapperLoader mapperLoader = new MapperLoader();
        // 加载xml文件
        Map<String, Object> extObjMap = mapperLoader.refresh(sqlSessionFactory, moduleClassLoader.getXmlBytesMap());

        // 将各种资源放入spring容器
        registerBeans(applicationContext, loadClassMap, extObjMap);

    }

    /**
     * 装载bean到spring中
     *
     * @param applicationContext
     * @param loadClassMap
     */
    public void registerBeans(ApplicationContext applicationContext, Map<String, Class> loadClassMap, Map<String, Object> extObjMap) {

        // 将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // 有一些对象想给spring管理，则放入spring中，如mybatis的@Mapper修饰的接口的代理类
        if (extObjMap != null && !extObjMap.isEmpty()) {
            extObjMap.forEach((beanName, obj) -> {

                // 如果已经存在，则销毁之后再注册
                if (defaultListableBeanFactory.containsSingleton(beanName)) {
                    defaultListableBeanFactory.destroySingleton(beanName);
                }
                defaultListableBeanFactory.registerSingleton(beanName, obj);
            });
        }

        for (Map.Entry<String, Class> entry : loadClassMap.entrySet()) {
            String className = entry.getKey();
            Class<?> clazz = entry.getValue();
            // 将变量首字母置小写
            String beanName = StringUtils.uncapitalize(className);
            if (isSpringBeanClass(clazz)) {
                beanName = beanName.substring(beanName.lastIndexOf(".") + 1);
                beanName = StringUtils.uncapitalize(beanName);

                // 已经在spring容器就删了
                if (defaultListableBeanFactory.containsBeanDefinition(beanName)) {
                    defaultListableBeanFactory.removeBeanDefinition(beanName);
                }
                // 使用spring的BeanDefinitionBuilder将Class对象转成BeanDefinition
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
                BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

                // 设置当前bean定义对象是单例的
                beanDefinition.setScope(SINGLETON);

                // 以指定beanName注册上面生成的BeanDefinition
                defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }

    public static boolean isSpringBeanClass(Class<?> cla) {
        /**
         * 如果为空或是接口或是抽象类直接返回false
         */
        if (cla == null || cla.isInterface() || Modifier.isAbstract(cla.getModifiers())) {
            return false;
        }

        Class targetClass = cla;
        while (targetClass != null) {
            /**
             * 如果包含spring注解则返回true
             */
            if (targetClass.isAnnotationPresent(Component.class) ||
                    targetClass.isAnnotationPresent(Repository.class) ||
                    targetClass.isAnnotationPresent(Service.class) ||
                    targetClass.isAnnotationPresent(Configuration.class) ||
                    targetClass.isAnnotationPresent(Controller.class) ||
                    targetClass.isAnnotationPresent(RestController.class) ||
                    targetClass.isAnnotationPresent(Mapper.class)) {
                return true;
            }
            targetClass = targetClass.getSuperclass();
        }

        return false;
    }

}
