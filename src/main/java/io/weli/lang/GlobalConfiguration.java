package io.weli.lang;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalConfiguration {
   public static final Map<String, Object> CONFIGURATION = new ConcurrentHashMap<>();

   public static void main(String[] args) {
      GlobalConfiguration.CONFIGURATION.get(null);
   }
}
