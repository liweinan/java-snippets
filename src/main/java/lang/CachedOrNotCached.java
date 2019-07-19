package lang;

import java.util.HashMap;
import java.util.Map;

public class CachedOrNotCached {
   public static void main(String[] args) {
      System.out.println("a".hashCode());

      StringBuilder builder = new StringBuilder();
      builder.append("a");
      Object obj = builder.toString().hashCode();
      System.out.println(obj.hashCode());

      Map<Object, Object> map = new HashMap<>();
      map.put(obj, 1);
      map.put("a", 2);

      System.out.println(map.toString());
   }
}
