package com.lokia.rpc.service.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class RpcInvokeTest {
    public static void main(String[] args) {
        try {
            // Getting the jar URL which contains target class
            URL[] classLoaderUrls = new URL[]{new URL("file:///D://tmp//jar//knoledge-note-collection-1.0-SNAPSHOT.jar")};
            // Create a new URLClassLoader
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

            // Load the target class
            Class<?> beanClass = urlClassLoader.loadClass("com.lokia.rpc.service.HelloServiceImpl");

            // Create a new instance from the loaded class
            Constructor<?> constructor = beanClass.getConstructor();
            Object beanObj = constructor.newInstance();

            // Getting a method from the loaded class and invoke it
            Method method = beanClass.getMethod("sayHello");
            Object response = method.invoke(beanObj);
            System.out.println("response:"+response);
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
