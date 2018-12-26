package com.eshel.jump;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.eshel.jump.anno.IntentParser;
import com.eshel.jump.anno.Params;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public class JumpUtil {

    @SuppressWarnings("unchecked")
    public static<T> T create(Class<T> clazz){
        checkNull(clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new JumpInvokeHandler());
    }

    public static void parseIntent(Object target, @NonNull Intent intent){
        parseIntent(0, target, intent);
    }

    /**
     * 解析 MemoryIntent 并将其移除
     * @param target Activity, 即使用了 @IntentParser 注解的类
     */
    public static void parseMemoryIntentAndRecycle(Object target){
        parseMemoryIntentAndRecycle(0, target);
    }

    public static void parseMemoryIntentAndRecycle(int id, Object target){
        parseIntent(id, target, null, true);
    }

    public static void parseIntent(int id, Object target, @NonNull Intent intent){
        parseIntent(id, target, intent, false);
    }

    private static void parseIntent(int id, Object target, @NonNull Intent intent, boolean needRecycle){
        checkNull(target);
        Class<?> clazz = target.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        Method targetMethod = null;
        IntentParser targetAnno = null;
        for (Method method : methods) {
//            if(!Modifier.isPublic(method))
            IntentParser annotation = method.getAnnotation(IntentParser.class);
            if(annotation == null)
                continue;
            if(id != annotation.id())
                continue;
            targetMethod = method;
            targetAnno = annotation;
            break;
        }

        if(targetMethod == null){
            new Exception(JumpException.JumpExpType.NoneIntentParser.message).printStackTrace();
            return;
        }

        if(!Modifier.isPublic(targetMethod.getModifiers()))
            targetMethod.setAccessible(true);

        Annotation[][] annos = targetMethod.getParameterAnnotations();
        Class<?>[] types = targetMethod.getParameterTypes();

        if(annos == null || types == null)
            return;

        if(annos.length != types.length){
            throw new JumpException(JumpException.JumpExpType.AnnoParamsLenthUnlikeness, target, targetMethod);
        }

        if(annos.length == 0)
            return;

        Object[] params = new Object[types.length];
        MemoryIntent intentM = null;

        for (int i = 0; i < types.length; i++) {

//            Class<?> type = types[i];
            Params anno = getParamsAnno(annos[i]);
            if(anno == null)
                throw new JumpException(JumpException.JumpExpType.AnnoParamsLenthUnlikeness, target, targetMethod);

            Object param = null;
            if(targetAnno.intentType() == IntentType.Intent){
                if(intent == null){
                    new JumpException(JumpException.JumpExpType.InvokeParseIntentIsNull).printStackTrace();
                    return;
                }

                param = intent.getSerializableExtra(anno.key());
            }else if(targetAnno.intentType() == IntentType.MemoryIntent){
                if(intentM == null)
                    intentM = MemoryIntent.getIntent((Class<? extends Activity>) target.getClass());

                param = intentM.load(anno.key(), Object.class);
            }
            params[i] = param;
        }

        //回收MemoryIntent对象
        if(needRecycle && intentM != null){
            MemoryIntent.recycleIntent(intentM);
        }

        try {
            targetMethod.invoke(target, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Params getParamsAnno(Annotation[] annos) {
        if(annos == null)
            return null;
        for (Annotation annotation : annos) {
            if( annotation instanceof Params)
                return (Params) annotation;
        }
        return null;
    }

    public static Activity getActivity(Context context) {
        // Gross way of unwrapping the Activity so we can get the FragmentManager
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private static void checkNull(Object obj){
        if(isNull(obj)){
            throw new NullPointerException("object is null!!!");
        }
    }

    private static boolean isNull(Object obj){
        return obj == null;
    }
}
