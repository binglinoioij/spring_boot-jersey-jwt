package com.example.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b><code>JsonUtil</code></b> <p> Json工具类 </p> <b>Creation Time:</b> 2015年3月4日 下午5:59:53
 *
 * @author kext
 * @since mdoctor 1.0
 */
public final class JsonUtils {

    /**
     * object to json，默认关闭循环引用，不然无法解析同一个list中的同一个引用对象
     *
     * @since mdoctor 1.0
     */
    public static String toJsonString(Object object) {
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * object to json use single quotes
     *
     * @since mdoctor 1.0
     */
    public static String toJsonStringUseSingleQuotes(Object object) {
        return JSON.toJSONString(object, SerializerFeature.UseSingleQuotes);
    }

    /**
     * date to json, yyyy-MM-dd HH:mm:ss
     *
     * @since mdoctor 1.0
     */
    public static String toJsonStringForDate(Date date) {
        return JSON.toJSONString(date, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * json to object
     *
     * @since mdoctor 1.0
     */
    public static Object parseJson(String json) {
        return JSON.parse(json);
    }

    /**
     * json to map
     *
     * @since mdoctor 1.0
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseJsonToMap(
            String json) {
        return (Map<String, Object>) JSON.parse(json);
    }

    /**
     * json to T type
     *
     * @since mdoctor 1.0
     */
    public static <T> T parseJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * json to list
     *
     * @since mdoctor 1.0
     */
    public static <T> List<T> parseJsonToList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    public static boolean isJsonArray(String json) {
        return json != null && json.trim().startsWith("[");
    }

    public static boolean isJsonObject(String json) {
        return json != null && json.trim().startsWith("{");
    }

}
