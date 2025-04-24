package io.weli.lang.spliterator;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SpliteratorExample {
    public static void main(String[] args) {
        // 准备数据
        String[] words = {"apple", "banana", "cherry", "date", "elderberry", "fig", "grape"};

        // 创建自定义 Spliterator
        Spliterator<String> spliterator = new CustomStringSpliterator(words, 0, words.length);

        // 创建并行 Stream
        Stream<String> parallelStream = StreamSupport.stream(spliterator, true);

        // 并行处理：将每个单词转换为大写并打印
        parallelStream.forEach(word -> System.out.println(Thread.currentThread().getName() + ": " + word.toUpperCase()));
    }
}