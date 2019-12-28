package io.weli.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class BiListDemo {

    interface BiList<T> {
        void lpush(T obj);

        T lpop();

        void rpush(T obj);

        T rpop();

        int length();

        Optional reduce(BinaryOperator bifunc);

        BiList<T> slice(int start, int end);

        void dump();
    }

    static class BiListImpl<T> implements BiList<T> {

        private List<T> store = new ArrayList<>();


        public BiListImpl(List<T> subList) {
            store = subList;
        }

        public BiListImpl() {

        }

        @Override
        public void lpush(T obj) {
            store.add(0, obj);
        }

        @Override
        public T lpop() {
            T obj = store.get(0);
            store.remove(0);
            return obj;
        }

        @Override
        public void rpush(T obj) {
            store.add(obj);
        }

        @Override
        public T rpop() {
            T obj = store.get(store.size() - 1);
            store.remove(store.size() - 1);
            return obj;
        }

        @Override
        public int length() {
            return store.size();
        }

        @Override
        public Optional reduce(BinaryOperator bifunc) {
            return Arrays.stream(store.toArray()).reduce(bifunc);
        }

        @Override
        public BiList<T> slice(int start, int end) {
            var lst = new BiListImpl<T>(store.subList(start, end));
            return lst;
        }

        @Override
        public void dump() {
            System.out.println(":::DUMP::: " + store.toString());
        }
    }

    public static void main(String[] args) {
        var lst = new BiListImpl<>();
        lst.lpush("a");
        lst.rpush("b");
        lst.dump();
        lst.lpush("x");
        lst.rpush("y");
        lst.dump();
        System.out.println(lst.lpop());
        lst.dump();
        System.out.println(lst.rpop());
        lst.dump();
        lst.lpush("bar");
        lst.lpush("foo");
        lst.dump();
        Optional result = lst.reduce((x, y) -> (String) x + y);
        System.out.println(result.get());
        System.out.println(lst.length());
        var lst2 = lst.slice(2,3);
        lst2.dump();
    }
}
