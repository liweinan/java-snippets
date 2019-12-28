package io.weli.alg;

import net.jcip.annotations.NotThreadSafe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class BiListDemo {

    interface BiList<T> {
        void lpush(T obj);

        T lpop();

        void rpush(T obj);

        T rpop();

        T shift();

        void unshift(T obj);

        T pop();

        void push(T obj);

        int length();

        Optional reduce(BinaryOperator bifunc);

        BiList<T> slice(int start, int end);

        BiList<T> slice(int start);

        BiList<T> slice();

        boolean every(Predicate p);

        List copyStore();

        void dump();

        T get(int idx);
    }

    @NotThreadSafe
    static class BiListImpl<T> implements BiList<T> {


        @Override
        public T get(int idx) {
            if (store.size() == 0)
                return null;
            if (idx < 0 || idx > store.size() - 1)
                return null;
            return store.get(idx);
        }

        @Override
        public List copyStore() {
            return List.of(store.toArray());
        }

        private List<T> store = new ArrayList<>();

        public BiListImpl(T obj, BiList<T> subList) {
            store = subList.copyStore();
            this.lpush(obj);
        }

        public BiListImpl(BiList<T> subList, T obj) {
            store = subList.copyStore();
            this.rpush(obj);
        }

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
            if (store.size() == 0)
                return null;
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
            if (store.size() == 0)
                return null;
            T obj = store.get(store.size() - 1);
            store.remove(store.size() - 1);
            return obj;
        }

        @Override
        public T shift() {
            return lpop();
        }

        @Override
        public void unshift(T obj) {
            lpush(obj);
        }


        @Override
        public T pop() {
            return rpop();
        }

        @Override
        public void push(T obj) {
            rpush(obj);
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
            if (store.size() == 0) {
                return new BiListImpl<T>();
            }
            if (start > store.size()) {
                return new BiListImpl<T>();
            }
            if (end > store.size()) {
                end = store.size();
            }
            var lst = new BiListImpl<>(store.subList(start, end));
            return lst;
        }

        @Override
        public BiList<T> slice(int start) {
            return slice(start, store.size());
        }

        @Override
        public BiList<T> slice() {
            return slice(0);
        }

        @Override
        public boolean every(Predicate p) {
            return Arrays.stream(store.toArray()).allMatch(p);
        }

        @Override
        public void dump() {
            System.out.print(":::DUMP::: ");
            for (Object obj : store) {
                System.out.print(obj.toString());
                System.out.print(", ");
            }
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
        var lst2 = lst.slice(2, 3);
        lst2.dump();
        System.out.println(lst2.lpop());
        System.out.println(lst2.lpop());
        System.out.println(lst2.rpop());
        System.out.println(lst2.rpop());

        lst.dump();
        lst.slice(0, 5).dump();
        lst.slice(0).dump();
        lst.slice(6, 6).dump();

        var lst3 = new BiListImpl<>();
        lst3.lpush("x");
        lst3.lpush("x");
        lst3.lpush("x");
        System.out.println(lst3.every(x -> {
            System.out.println("_x: " + x);
            return x.toString().equals("x");
        }));

        int[] _data = {-57, 9, -72, -72, -62, 45, -97, 24, -39, 35, -82, -4, -63, 1, -93, 42, 44, 1, -75, -25, -87, -16, 9, -59, 20};

        BiList<Integer> lst4 = new BiListImpl();
        for (int i : _data) {
            lst4.push(i);
        }
        lst4.dump();

        System.out.println(maxSubArray(lst4));
    }

    static BiList<Integer> loop(BiList<Integer> nums, Just<Integer> maxSum) {
        System.out.println("------------------loop nums: ");
        nums.dump();
        System.out.println("------max sum: " + maxSum.getObj());
        // step 1: trim all negative numbers in head
        var num = nums.shift();
        while (num <= 0 && nums.length() > 0) {
            num = nums.shift();
        }
        nums.unshift(num);
        System.out.println("left trimmed: ");
        nums.dump();
        // step 2: trim all negative numbers in tail
        num = nums.pop();
        while (num <= 0 && nums.length() > 0) {
            num = nums.pop();
        }
        nums.push(num);
        System.out.println("right trimmed: ");
        nums.dump();

        var left = 0;
        var n = nums.shift();
        var right = 0;
        // step 3: sum left nums
        while (n != null && n >= 0) {
            left += n;
            n = nums.shift();
        }
        if (n != null) nums.unshift(n);
        if (left > 0) nums.unshift(left);
        System.out.println("left added: ");
        nums.dump();
        maxSum.setObj(Math.max(left, maxSum.getObj()));

        if (n != null) {
            System.out.println("// n must be < 0: " + n);
            if (left + n > 0) {
                nums = new BiListImpl<>(left + n, nums.slice(2));
            } else {
                nums = nums.slice(2);
            }
        }
        System.out.println("left calc nums: ");
        nums.dump();
        // step 4: sum right nums
        n = nums.pop();
        while (n != null && n >= 0) {
            right += n;
            n = nums.pop();
        }
        if (n != null) nums.push(n);
        if (right > 0) nums.push(right);
        maxSum.setObj(Math.max(right, maxSum.getObj()));
        if (n != null) {
            System.out.println("// n must be < 0: " + n);
            if (right + n > 0) {
                nums = new BiListImpl<>(nums.slice(0, nums.length() - 2), right + n);
            } else {
                nums = nums.slice(0, nums.length() - 2);
            }
        }
        System.out.println("right calc nums: ");
        nums.dump();
        return nums;
    }


    static class Just<T> {
        private T obj;

        public T getObj() {
            return obj;
        }

        public void setObj(T obj) {
            this.obj = obj;
        }
    }

    static int maxSubArray(BiList<Integer> nums) {
        Just<Integer> maxSum = new Just<>();
        maxSum.setObj(Integer.MIN_VALUE);

        if (nums.every(n -> (Integer) n <= 0))
            return (int) nums.reduce((p, c) -> {
                        return Math.max((int) p, (int) c);
                    }
            ).get();

        while (nums.length() > 1) {
            nums = loop(nums, maxSum);
        }
        System.out.println("---FINAL---");

        int _final = Math.max(nums.get(0), maxSum.getObj());
        System.out.println(_final);
        return _final;
    }

}
