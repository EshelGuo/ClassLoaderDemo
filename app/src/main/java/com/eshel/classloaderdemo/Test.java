package com.eshel.classloaderdemo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Test {

    public static void main(String args[]){

//        ArrayList<String> l1 = new ArrayList<>();
//        ArrayList<Integer> l2 = new ArrayList<>();
//        System.out.println(l1.getClass());
//        System.out.println(l2.getClass());
        TypeD d = new TypeD<String>(){};
        TypeD d2 = new TypeD<Integer>(){};
        Class<? extends TypeD> dClass = d.getClass();
        Class<? extends TypeD> d2Class = d2.getClass();
        System.out.println(dClass);
        System.out.println(d2Class);
        Type type = ((ParameterizedType) dClass.getGenericSuperclass()).getActualTypeArguments()[0];
//        == d2Class.getGenericSuperclass();

//        Type genType = d.getClass().getGenericSuperclass();
//        d.test();
//        System.out.println(genType);
//        d.test();
        /*MyClassLoader loader = new MyClassLoader();
        try {
            Class<?> ClassLoaderTest = loader.loadClass("com.eshel.classloaderdemo.ClassLoaderTest");
            try {
                Method test = ClassLoaderTest.getMethod("test");
                try {
                    String result = (String) test.invoke(null);
                    System.out.println(result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
