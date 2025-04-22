package io.weli.lambda;

import java.util.ArrayList;
import java.util.List;

public class PlayWithLambda {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        
        list.forEach(System.out::println);
    }
}
