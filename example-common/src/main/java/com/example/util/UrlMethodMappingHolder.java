package com.example.util;

import org.glassfish.jersey.server.model.ResourceMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by binglin on 2016/10/18.
 */
public class UrlMethodMappingHolder {
    public static HashMap<String, ResourceMethod> mapping = new HashMap<>();

    public static boolean isEmpty() {
        return mapping.isEmpty();
    }

    public static ResourceMethod get(String key) {
        return mapping.get(key);
    }

    public static ResourceMethod get(String url, String method) {
        Map.Entry<String, ResourceMethod> stringResourceMethodEntry = mapping.entrySet().stream().filter(e -> {
            String[] split = e.getKey().split("#");
            Pattern pattern = Pattern.compile(split[0]);
            Matcher matcher = pattern.matcher(url);
            return (matcher.matches() && split[1].equalsIgnoreCase(method));
        }).findFirst().orElse(null);
        return stringResourceMethodEntry != null ?
                stringResourceMethodEntry.getValue() : null;
    }
}
