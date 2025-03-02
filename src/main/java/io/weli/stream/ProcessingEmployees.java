package io.weli.stream;

import java.util.Arrays;
import java.util.List;

public class ProcessingEmployees {
    public static void main(String[] args) {
        Employee[] employees = new Employee[]{
                new Employee("Jason", "Red", 5000, "IT"),
                new Employee("Ashley", "Green", 7600, "IT"),
                new Employee("Matthew", "Indigo", 3587.5, "Sales"),
                new Employee("James", "Indigo", 4700.77, "Marketing"),
                new Employee("Luke", "Indigo", 6200, "IT"),
                new Employee("Jason", "Blue", 3200, "Sales"),
                new Employee("Wendy", "Brown", 4236.4, "Marketing"),
        };

        List<Employee> list = Arrays.asList(employees);

        System.out.println("Complete Employee list: ");
        list.forEach(System.out::println);
    }
}

