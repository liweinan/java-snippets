package io.weli.beanutils;


import org.apache.commons.beanutils.PropertyUtils;

import java.util.Arrays;
import java.util.List;

// https://www.baeldung.com/apache-commons-beanutils
public class Play {
    public static void main(String[] args) throws Exception {
        Course course = new Course();
        String name = "Computer Science";
        List<String> codes = Arrays.asList("CS", "CS01");

        PropertyUtils.setSimpleProperty(course, "name", name);
        PropertyUtils.setSimpleProperty(course, "codes", codes);

        System.out.println(course);
    }
}
