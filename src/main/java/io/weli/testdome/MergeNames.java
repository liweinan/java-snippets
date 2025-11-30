package io.weli.testdome;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

// https://www.testdome.com/library?page=1&skillArea=30&questionSets=public&questionId=85846
public class MergeNames {

    public static String[] uniqueNames(String[] names1, String[] names2) {
        Set<String> set = new LinkedHashSet<>();
        Collections.addAll(set, names1);
        Collections.addAll(set, names2);
        return set.toArray(new String[0]);
    }

    public static String[] uniqueNames2(String[] names1, String[] names2) {
        return Stream.concat(Arrays.stream(names1), Arrays.stream(names2))
                .distinct()
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        String[] names1 = new String[] {"Ava", "Emma", "Olivia"};
        String[] names2 = new String[] {"Olivia", "Sophia", "Emma"};
        System.out.println(String.join(", ", MergeNames.uniqueNames(names1, names2))); // should print Ava, Emma, Olivia, Sophia
    }
}
