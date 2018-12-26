package com.eshel.classloaderdemo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Types;

public class TypeD<NNN> {
    public TypeD() {
    }

    public void test(){
        System.out.println(getClass());
        Class<?> superclass = getClass().getSuperclass();
        System.out.println(superclass);
        Type type = getClass().getGenericSuperclass();
        System.out.println(type);
        System.out.println(type instanceof ParameterizedType);
        {
            Type type1 = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Class<NNN> tClass = (Class<NNN>)type1;
            System.out.println(tClass);
        }
    }
}
