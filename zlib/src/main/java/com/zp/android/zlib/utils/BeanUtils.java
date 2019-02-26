package com.zp.android.zlib.utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings({ "WeakerAccess", "unused" })
public class BeanUtils {

    public static void copyProperties(Object from, Object to) throws Exception {
        copyPropertiesExclude(from, to, null);
    }

    public static void copyPropertiesExclude(Object from, Object to, String[] excludeArray) throws Exception {
        List<String> excludesList = null;
        if (excludeArray != null && excludeArray.length > 0) {
            excludesList = Arrays.asList(excludeArray); //构造列表对象
        }
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod, toMethod;
        String fromMethodName, toMethodName;
        for (Method method : fromMethods) {
            fromMethod = method;
            fromMethodName = fromMethod.getName();
            if (!fromMethodName.contains("get") || fromMethodName.contains("getId")) { continue; }
            //排除列表检测
            if (excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if (toMethod == null) { continue; }
            Object value = fromMethod.invoke(from);
            if (value == null) { continue; }
            //集合类判空处理
            if (value instanceof Collection) {
                Collection newValue = (Collection)value;
                if (newValue.size() <= 0) { continue; }
            }
            toMethod.invoke(to, value);
        }
    }

    public static void copyPropertiesInclude(Object from, Object to, String[] includeArray) throws Exception {
        List<String> includesList;
        if (includeArray != null && includeArray.length > 0) {
            includesList = Arrays.asList(includeArray); //构造列表对象
        }
        else {
            return;
        }
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod, toMethod;
        String fromMethodName, toMethodName;
        for (Method method : fromMethods) {
            fromMethod = method;
            fromMethodName = fromMethod.getName();
            if (!fromMethodName.contains("get")) { continue; }
            //排除列表检测
            String str = fromMethodName.substring(3);
            if (!includesList.contains(str.substring(0, 1).toLowerCase() + str.substring(1))) {
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if (toMethod == null) { continue; }
            Object value = fromMethod.invoke(from);
            if (value == null) { continue; }
            //集合类判空处理
            if (value instanceof Collection) {
                Collection newValue = (Collection)value;
                if (newValue.size() <= 0) { continue; }
            }
            toMethod.invoke(to, value);
        }
    }

    public static Method findMethodByName(Method[] methods, String name) {
        for (Method method : methods) {
            if (method.getName().equals(name)) { return method; }
        }
        return null;
    }
}
