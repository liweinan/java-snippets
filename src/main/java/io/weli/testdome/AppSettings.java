package io.weli.testdome;

import java.util.HashMap;
import java.util.Map;

// https://www.testdome.com/library?page=1&skillArea=30&questionSets=public&questionId=149265
// because the return type of get appsettings is object, not hashmap, it should be casted to map.
public class AppSettings {
    static Object getAppSettings(boolean detailed) {
        if (detailed) {
            Map<String, String> map = new HashMap<>();
            map.put("os", System.getProperty("os.name"));
            return map;
        }
        return System.getProperty("device.name");
    }

    public static void main(String[] args) {
        Object appSettings = getAppSettings(true);
        System.out.println(((Map) appSettings).get("os"));
    }
}

