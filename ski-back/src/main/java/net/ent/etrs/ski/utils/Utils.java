package net.ent.etrs.ski.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {
    
    public static Map<String,String> strToMap(String str) {
        Map<String, String> map = new LinkedHashMap<>();
        String[] strArr = str.split(";");
        if (strArr.length > 0 && !str.isBlank()) {
            for (String s : strArr) {
                String[] s2 = s.split(":");
                map.put(s2[0], s2[1]);
            }
        }
        return map;
    }
    
}
