package com.hillbo.test.utils;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class SpringContextHolderUtil implements ApplicationContextAware {

    protected static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolderUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static void assertContextInjected() {
        Validate.validState(applicationContext != null, "Applicaitoncontext property is not injected, please define springcontextholder. XML in applicationcontext.xml");
    }

}
