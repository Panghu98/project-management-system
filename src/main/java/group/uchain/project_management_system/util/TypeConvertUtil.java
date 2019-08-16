package group.uchain.project_management_system.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import group.uchain.project_management_system.entity.ProjectInfo;

import java.util.Collections;
import java.util.List;

/**
 * @author project_management_system
 * @title: TypeConvertUtil
 * @projectName project_management_system
 * @date 19-7-13 上午8:21
 */
public class TypeConvertUtil {

    /**
     * 任意的对象转化为字符串
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }

    }

    /**
     * 任意的字符串转化为对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    public static String convertListProjectToString(List<ProjectInfo> list){
        JSONArray array = new JSONArray(Collections.singletonList(list));
        String string = array.toJSONString();
        string = string.substring(1,string.length()-1);
        return string;
    }

}
