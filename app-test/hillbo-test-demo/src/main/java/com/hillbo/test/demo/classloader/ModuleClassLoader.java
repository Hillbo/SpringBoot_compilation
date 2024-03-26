package com.hillbo.test.demo.classloader;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.loader.WebappClassLoaderBase;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class ModuleClassLoader extends URLClassLoader {

    // jar包文件
    private JarFile jarFile;

    // jar包文件名称
    private String jarFileName;

    // jar包文件url地址
    private URL jarUrl;

    // jar包解析出来的的class字节数组文件
    private Map<String, byte[]> resolvedClassBytesMap = new HashMap<>();

    // 已经加载的class
    Map<String, Class> loadedClassMap = new HashMap<>();

    // 已加载的jar包中的class
    private Map<String, Map<String, Class>> loadedJarClassMap;

    // 已加载的xml文件
    private Map<String, byte[]> loadedXmlMap = new HashMap<>();

    // class文件后缀
    private final static String CLASS_SUFFIX = ".class";

    // xml文件后缀
    private final static String XML_SUFFIX = ".xml";

    // xml文件路径前缀
    private final static String MAPPER_SUFFIX = "mappings";

    public ModuleClassLoader(String jarFileName, URL[] urls, Map<String, Map<String, Class>> loadedJarClassMap, ClassLoader parent) {
        super(urls, parent);

        try {
            this.jarFileName = jarFileName;
            this.jarFile = new JarFile(urls[0].getPath());
            this.loadedJarClassMap = loadedJarClassMap;
            this.jarUrl = urls[0];
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        log.info("-----findClass:{}-----", name);
        byte[] buf = resolvedClassBytesMap.get(name);
        if (buf == null) {
            return super.findClass(name);
        }
        if (loadedClassMap.containsKey(name)) {
            return loadedClassMap.get(name);
        }

        return loadClassByBytes(name, buf);
    }

    /**
     * 使用反射强行将类装载的归属给当前类加载器的父类加载器也就是AppClassLoader，如果报ClassNotFoundException
     * 则递归装载
     */
    private Class<?> loadClassByBytes(String name, byte[] bytes) throws ClassNotFoundException {
        Class<?> result = null;
        try {
            result = Class.forName(name);
            // 存在直接返回，否则才加载
            return result;
        } catch (ClassNotFoundException e) {
            result = null;
        }

        try {
            /**
             * 拿到当前类加载器的parent加载器AppClassLoader
             */
            ClassLoader parent = this.getParent();
            /**
             * 首先要明确反射是万能的，仿造org.springframework.cglib.core.ReflectUtils的写法，强行获取被保护
             * 的方法defineClass的对象，然后调用指定类加载器的加载字节码方法，强行将加载归属塞给它，避免被spring的AOP或者@Transactional
             * 触碰到的类需要生成代理对象，而在AppClassLoader下加载不到外部的扩展类而报错，所以这里强行将加载外部扩展包的类的归属给
             *              * AppClassLoader，让spring的cglib生成代理对象时可以加载到原对象
             */
            if (parent instanceof WebappClassLoaderBase) {
                Method classLoaderAddUrl = (Method) AccessController.doPrivileged((PrivilegedExceptionAction) () -> WebappClassLoaderBase.class.getDeclaredMethod("addURL", URL.class));
                if (!classLoaderAddUrl.isAccessible()) {
                    classLoaderAddUrl.setAccessible(true);
                }

                Object[] addURLArgs = new Object[]{jarUrl};
                classLoaderAddUrl.invoke(parent, addURLArgs);
                result = Class.forName(name);
            } else {
                Method classLoaderDefineClass = (Method) AccessController.doPrivileged((PrivilegedExceptionAction) () ->
                        ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE));
                if (!classLoaderDefineClass.isAccessible()) {
                    classLoaderDefineClass.setAccessible(true);
                }

                Object[] defineArgs = new Object[]{name, bytes, 0, bytes.length};
                result = (Class<?>) classLoaderDefineClass.invoke(parent, defineArgs);
            }

        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                String message = ((InvocationTargetException) e).getTargetException().getCause().toString();
                /**
                 * 这边异常捕获去加载未加载到的类，场景如下
                 * 先加载A类，A类依赖于B类，所以只有当B类成功加载，A类才能够加载，B类不能加载成功，A类也不能加载成功
                 */
                if (message.startsWith("java.lang.ClassNotFoundException")) {
                    String notClassName = message.split(":")[1];
                    if (StringUtils.isEmpty(notClassName)) {
                        throw new ClassNotFoundException(message);
                    }
                    notClassName = notClassName.trim();
                    byte[] bytes1 = resolvedClassBytesMap.get(notClassName);
                    if (bytes1 == null) {
                        throw new ClassNotFoundException(message);
                    }
                    /**
                     * 递归装载未找到的类
                     */
                    Class<?> notClass = loadClassByBytes(notClassName, bytes1);
                    if (notClass == null) {
                        throw new ClassNotFoundException(message);
                    }
//                    classesMap.put(notClassName, notClass);
                    return loadClassByBytes(name, bytes);
                }
            } else {
                log.error(e.getMessage(), e);
                return null;
            }
        }

        return result;
    }

    public Map<String, Class> loadClass() {

        // 解析jar包每一项
        Enumeration<JarEntry> enumeration = jarFile.entries();
        InputStream inputStream = null;
        try {
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                String name = jarEntry.getName();
                // 这里添加了路径扫描限制
                if (name.endsWith(CLASS_SUFFIX)) {
                    String className = name.replace(CLASS_SUFFIX, "").replaceAll("/", ".");
                    inputStream = jarFile.getInputStream(jarEntry);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int bufferSize = 4096;
                    byte[] buffer = new byte[bufferSize];
                    int bytesNumRead = 0;
                    while ((bytesNumRead = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesNumRead);
                    }
                    byte[] classBytes = byteArrayOutputStream.toByteArray();

                    resolvedClassBytesMap.put(className, classBytes);
                } else if (name.endsWith(XML_SUFFIX) && name.startsWith(MAPPER_SUFFIX)) {
                    inputStream = jarFile.getInputStream(jarEntry);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int bufferSize = 4096;
                    byte[] buffer = new byte[bufferSize];
                    int bytesNumRead = 0;
                    while ((bytesNumRead = inputStream.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesNumRead);
                    }
                    byte[] xmlBytes = baos.toByteArray();

                    loadedXmlMap.put(name, xmlBytes);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

//        Map<String, Class> loadedClassMap = new HashMap<>();
        // 将jar中的每一个class字节码进行Class载入
        for (Map.Entry<String, byte[]> entry : resolvedClassBytesMap.entrySet()) {
            String className = entry.getKey();
            Class<?> aClass = null;
            try {
                aClass = loadClass(className);
                if (null != aClass){
                    loadedClassMap.put(className, aClass);
                }
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }
        loadedJarClassMap.put(jarFileName, loadedClassMap);
        return loadedClassMap;
    }

    public Map<String, byte[]> getXmlBytesMap() {
        return loadedXmlMap;
    }

    public void clearResolvedClassBytesMap(){
        resolvedClassBytesMap.clear();
    }

    public void clearLoadedClassMap(){
        loadedClassMap.clear();
    }

    public void clearLoadedXmlMap(){
        loadedXmlMap.clear();
    }

}
