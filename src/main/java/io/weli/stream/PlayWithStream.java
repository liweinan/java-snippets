package io.weli.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayWithStream {
    static class Product {

        private int idx;
        private String name;

        public Product(int idx, String name) {
            this.idx = idx;
            this.name = name;
        }

        public int getIdx() {
            return idx;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        {
            List<Product> productList = Arrays.asList(new Product(23, "potatoes"),
                    new Product(14, "orange"), new Product(13, "lemon"),
                    new Product(23, "bread"), new Product(13, "sugar"));
            List<String> collectorCollection =
                    productList.stream().map(Product::getName).collect(Collectors.toList());

        }
    }
}
