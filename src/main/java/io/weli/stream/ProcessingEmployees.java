package io.weli.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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

        Predicate<Employee> fourToSixThousand = employee -> employee.getSalary() >= 4000 && employee.getSalary() <= 6000;

        System.out.printf("%n Employees earning $4000-$6000 per month sorted by salary:%n");

        list.stream()
                .filter(fourToSixThousand)
                .sorted(Comparator.comparing(Employee::getSalary))
                .forEach(System.out::println);

        System.out.printf("%nFirst employee who earns $4000-$6000:%n%s%n",
                list
                        .stream()
                        .filter(fourToSixThousand)
                        .sorted(Comparator.comparing(Employee::getSalary))
                        .findFirst()
                        .get());

        Function<Employee, String> byFirstName = Employee::getFirstName;


    }
}

