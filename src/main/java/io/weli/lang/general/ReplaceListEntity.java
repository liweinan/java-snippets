package io.weli.lang.general;

import java.util.ArrayList;
import java.util.List;

public class ReplaceListEntity {

    static class Wrapper {
        private Object obj;

        public Wrapper(Object obj) {
            this.obj = obj;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Original");
        
        System.out.println("Before replacement: " + list);
        
        list.set(0, "Replaced");
        
        System.out.println("After replacement: " + list);
    }
}
