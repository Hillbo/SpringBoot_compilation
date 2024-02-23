package com.hillbo.test.classloader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.net.URL;

@Slf4j
@Component
//@DependsOn("moduleApplication")
public class ClassLoaderConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private ModuleApplication moduleApplication;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void loadJarByPath(String jarName, String jarPath) {
        try {
            moduleApplication.loadJar(jarName, new URL("file:" + jarPath), applicationContext);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
