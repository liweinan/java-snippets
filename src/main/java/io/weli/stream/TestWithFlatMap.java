package io.weli.stream;

import java.util.Arrays;

public class TestWithFlatMap {
    public static void main(String[] args) {
        var books1 = Arrays.asList(new Book(10, "AAA"), new Book(20, "BBB"));

        Writer w1 = new Writer("Mohan", books1);

        var books2 =  Arrays.asList(new Book(10, "XXX"), new Book(15, "ZZZ"));

        Writer w2 = new Writer("Sohan", books2);

        var writers = Arrays.asList(w1, w2);

        Book book = writers.stream().flatMap(writer -> writer.getBooks().stream())
                .max(new BookComparator()).get();
        System.out.println(book);
    }
}
