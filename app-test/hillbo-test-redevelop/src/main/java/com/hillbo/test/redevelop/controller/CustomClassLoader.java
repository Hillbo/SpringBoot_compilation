package com.hillbo.test.redevelop.controller;

import java.lang.reflect.Field;
import java.util.Vector;

public class CustomClassLoader extends ClassLoader {


    public void unloadClass(String name) throws ReflectiveOperationException {

        ClassLoader classLoader = getClass().getClassLoader();
//        Class<?> clazz = classLoader.loadClass(name);
        Field classLoaderField = Class.class.getDeclaredField("classLoader");
        classLoaderField.setAccessible(true);
//        classLoaderField.set(clazz, null);
    }

    public void clearClassReferences(String name) throws NoSuchFieldException, IllegalAccessException {
        ClassLoader classLoader = getClass().getClassLoader();
        Field classesField = ClassLoader.class.getDeclaredField("classes");
        classesField.setAccessible(true);
        Vector<Class<?>> classes = (Vector<Class<?>>) classesField.get(classLoader);

        for (Class<?> clazz : classes) {
            if (clazz.getName().equals(name)) {
                classes.remove(clazz);
                break;
            }
        }
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        CustomClassLoader classLoader = new CustomClassLoader();
//        Class<?> clazz = classLoader.loadClass("com.example.MyClass");
        classLoader.unloadClass("com.example.MyClass");
        classLoader.clearClassReferences("com.example.MyClass");

        System.gc();
    }

}
