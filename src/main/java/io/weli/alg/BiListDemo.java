package io.weli.alg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "rawtypes"})
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

        Optional<T> reduce(BinaryOperator<T> bifunc);

        BiList<T> slice(int start, int end);

        BiList<T> slice(int start);

        BiList<T> slice();

        boolean every(Predicate<T> p);

        List<T> copyStore();

        void dump();

        T get(int idx);

        void add(T element);

        int size();

        List<T> toList();
    }

    //    @NotThreadSafe
    static class BiListImpl<T> implements BiList<T> {
        private final ArrayList<T> store = new ArrayList<>();

        @Override
        public T get(int idx) {
            if (store.isEmpty())
                return null;
            if (idx < 0 || idx > store.size() - 1)
                return null;
            return store.get(idx);
        }

        @Override
        public List<T> copyStore() {
            return new ArrayList<>(store);
        }

        public BiListImpl(T obj, BiList<T> subList) {
            store.addAll(subList.copyStore());
            this.lpush(obj);
        }

        public BiListImpl(BiList<T> subList, T obj) {
            store.addAll(subList.copyStore());
            this.rpush(obj);
        }

        @SuppressWarnings("unchecked")
        public BiListImpl(List<T> subList) {
            store.addAll(subList);
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
            try {
                store.add(obj);
            } catch (UnsupportedOperationException e) {
                // System.out.println(e);
            }
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
            var lst = new BiListImpl(store.subList(start, end));
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
            // System.out.print(":::DUMP::: ");
            for (Object obj : store) {
                // System.out.print(obj.toString());
                // System.out.print(", ");
            }
        }

        @Override
        public void add(T element) {
            store.add(element);
        }

        @Override
        public int size() {
            return store.size();
        }

        @Override
        public List<T> toList() {
            List<T> result = new ArrayList<>();
            for (Object obj : store) {
                result.add((T) obj);
            }
            return result;
        }
    }

    public static void main(String[] args) {
        BiList<String> lst = new BiListImpl<>();
        lst.lpush("a");
        lst.rpush("b");
        lst.dump();
        lst.lpush("x");
        lst.rpush("y");
        lst.dump();
        lst.lpush("bar");
        lst.lpush("foo");
        lst.dump();
        
        Optional<String> result = lst.reduce((x, y) -> x + y);

        BiList<String> lst2 = lst.slice(2, 3);
        lst2.dump();

        lst.dump();
        lst.slice(0, 5).dump();
        lst.slice(0).dump();
        lst.slice(6, 6).dump();

        BiList<String> lst3 = new BiListImpl<>();
        lst3.lpush("x");
        lst3.lpush("x");
        lst3.lpush("x");
        lst3.every(x -> x.equals("x"));

        int[] _data = {-57, 9, -72, -72, -62, 45, -97, 24, -39, 35, -82, -4, -63, 1, -93, 42, 44, 1, -75, -25, -87, -16, 9, -59, 20};

        BiList<Integer> lst4 = new BiListImpl<>();
        for (int i : _data) {
            lst4.push(i);
        }
        lst4.dump();

        int[] bigdata = Helper.read("/tmp/10000-ints-array.txt");
        BiList<Integer> lst5 = new BiListImpl<>();
        for (int i : bigdata) {
            lst5.push(i);
        }
        lst5.dump();

        long start = System.currentTimeMillis();
        int r = maxSubArray(lst5);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(r);

        BiList<Integer> lst6 = new BiListImpl<>();
        for (int i : _data) {
            lst6.push(i);
        }
        lst6.dump();
        System.out.println(maxSubArrary2(lst6));

        BiList<Integer> lst7 = new BiListImpl<>();
        for (int i : _data) {
            lst7.push(i);
        }
        lst7.dump();
        System.out.println(maxSubArrary3(lst7));

        BiList<Integer> lst8 = new BiListImpl<>();
        for (int i : bigdata) {
            lst8.push(i);
        }
        lst8.dump();
        System.out.println("::::::多线程实现:::::::");
        start = System.currentTimeMillis();
        System.out.println(maxSubArrary3(lst8));
        System.out.println("::::::::" + (System.currentTimeMillis() - start));

        BiList<Integer> lst9 = new BiListImpl<>();
        for (int i : bigdata) {
            lst9.push(i);
        }
        lst9.dump();
        System.out.println("::::::单线程实现:::::::");
        start = System.currentTimeMillis();
        System.out.println(maxSubArrary2(lst9));
        System.out.println("::::::::" + (System.currentTimeMillis() - start));
    }

    @SuppressWarnings("unchecked")
    static BiList<Integer> loop(BiList<Integer> nums, Just<Integer> maxSum) {
        nums.dump();
        Integer num = nums.shift();
        while (num != null && num <= 0 && nums.length() > 0) {
            num = nums.shift();
        }
        if (num != null) {
            nums.unshift(num);
        }

        num = nums.pop();
        while (num != null && num <= 0 && nums.length() > 0) {
            num = nums.pop();
        }
        if (num != null) {
            nums.push(num);
        }

        int left = 0;
        Integer n = nums.shift();
        int right = 0;

        while (n != null && n >= 0) {
            left += n;
            n = nums.shift();
        }
        if (n != null) nums.unshift(n);
        if (left > 0) nums.unshift(left);

        maxSum.setObj(Math.max(left, maxSum.getObj()));

        if (n != null) {
            if (left + n > 0) {
                nums = new BiListImpl<>(left + n, nums.slice(2));
            } else {
                nums = nums.slice(2);
            }
        }

        n = nums.pop();
        while (n != null && n >= 0) {
            right += n;
            n = nums.pop();
        }
        if (n != null) nums.push(n);
        if (right > 0) nums.push(right);
        maxSum.setObj(Math.max(right, maxSum.getObj()));
        if (n != null) {
            if (right + n > 0) {
                nums = new BiListImpl<>(nums.slice(0, nums.length() - 2), right + n);
            } else {
                nums = nums.slice(0, nums.length() - 2);
            }
        }
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

    @SuppressWarnings("unchecked")
    static int maxSubArray(BiList<Integer> nums) {
        Just<Integer> maxSum = new Just<>();
        maxSum.setObj(Integer.MIN_VALUE);

        if (nums.every(n -> n <= 0)) {
            return (int) nums.reduce((p, c) -> Math.max(p, c)).get();
        }

        while (nums.length() > 1) {
            nums = loop(nums, maxSum);
        }

        return Math.max(nums.get(0), maxSum.getObj());
    }

    static int maxSubArrary2(BiList<Integer> nums) {
        int length = 0;
        Just<Integer> maxSum = new Just<>();
        maxSum.setObj(Integer.MIN_VALUE);

        while (length <= nums.length()) {
            new MaxFinder(nums, length, maxSum).findMax();
            length++;
        }
        return maxSum.getObj();
    }

    static int maxSubArrary3(BiList<Integer> nums) {
        int length = 0;
        List<Just<Integer>> maxSumPool = new ArrayList<>();
        ForkJoinPool pool = ForkJoinPool.commonPool();

        while (length <= nums.length()) {
            Just<Integer> maxSum = new Just<>();
            maxSum.setObj(Integer.MIN_VALUE);
            maxSumPool.add(maxSum);
            pool.execute(new MaxFinder(nums, length, maxSum));
            length++;
        }

        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return maxSumPool.stream()
                .map(Just::getObj)
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE);
    }

    static class MaxFinder extends Thread {
        private final BiList<Integer> nums;
        private final int length;
        private final Just<Integer> maxSum;

        public MaxFinder(BiList<Integer> nums, int length, Just<Integer> maxSum) {
            this.nums = nums;
            this.length = length;
            this.maxSum = maxSum;
        }

        @Override
        public void run() {
            findMax();
        }

        @SuppressWarnings("unchecked")
        private void findMax() {
            for (int i = 0; i <= nums.length() - length; i++) {
                BiList<Integer> subarray = nums.slice(i, i + length);
                if (subarray.length() > 0) {
                    Optional<Integer> sum = subarray.reduce(Integer::sum);
                    sum.ifPresent(value -> {
                        if (value > maxSum.getObj()) {
                            maxSum.setObj(value);
                        }
                    });
                }
            }
        }
    }

    public static class Helper {
        public static int[] read(String filename) {
            try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                return Arrays.stream(sb.toString().split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            } catch (IOException ex) {
                ex.printStackTrace();
                return new int[0];
            }
        }
    }

    public static <T> BiList<T> from(List<T> list) {
        BiListImpl<T> result = new BiListImpl<>();
        result.store.addAll(list);
        return result;
    }
}
