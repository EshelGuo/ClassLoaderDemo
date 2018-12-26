package com.eshel.classloaderdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String args[]){
//        ProxyBean bean = new ProxyBean();
        ProxyBean bean = (ProxyBean) Proxy.newProxyInstance(ProxyBean.class.getClassLoader(), new Class[]{ProxyBean.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.getName().equals("test"))
                    System.out.println("I am Proxy!!!");
                return null;
            }
        });
        bean.test();
    }
}
