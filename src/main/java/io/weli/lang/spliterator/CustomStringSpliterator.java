package io.weli.lang.spliterator;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

// 自定义 Spliterator 用于分割字符串数组
class CustomStringSpliterator extends Spliterators.AbstractSpliterator<String> {
    private final String[] data; // 数据源
    private int start; // 当前分片的起始索引
    private int end; // 当前分片的结束索引

    public CustomStringSpliterator(String[] data, int start, int end) {
        // 构造函数，传入数据和范围，设置 Spliterator 特性
        super(end - start, SIZED | SUBSIZED | NONNULL | IMMUTABLE);
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean tryAdvance(Consumer<? super String> action) {
        // 逐个处理元素
        if (start < end) {
            action.accept(data[start++]);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<String> trySplit() {
        // 尝试将当前数据分片
        int mid = start + (end - start) / 2; // 二分法分割
        if (mid <= start || mid >= end) {
            return null; // 数据无法再分，返回 null
        }

        // 创建新的 Spliterator，覆盖前半部分
        CustomStringSpliterator newSpliterator = new CustomStringSpliterator(data, start, mid);
        // 更新当前 Spliterator 的起始位置为后半部分
        this.start = mid;
        return newSpliterator;
    }

    @Override
    public long estimateSize() {
        // 估计当前分片的大小
        return end - start;
    }
}

