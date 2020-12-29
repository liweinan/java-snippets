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

    public static void main(String[] args) throws Exception {
        List lst = new ArrayList<>();
        Object foo = new Object();

        lst.add(foo);


        Object get = lst.get(0);
        Wrapper wrapper = new Wrapper(get);

        lst.set(0, wrapper);


        System.out.println(lst.get(0));
        System.out.println(((Wrapper) lst.get(0)).getObj());

    }
}
