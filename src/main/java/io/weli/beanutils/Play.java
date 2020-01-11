package io.weli.beanutils;


import org.apache.commons.beanutils.BeanUtils;
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

        Student student = new Student();
        String studentName = "Joe";
        student.setName(studentName);

        PropertyUtils.setMappedProperty(course, "enrolledStudent(ST-1)", student);

        System.out.println(course);

        {
            Course course2 = new Course();
            course2.setName("Computer Science");
            course2.setCodes(Arrays.asList("CS"));
            course2.setEnrolledStudent("ST-1", new Student());

            CourseEntity courseEntity = new CourseEntity();

            // Remember this will copy the properties with the same name only.
            // Therefore, it will not copy the property enrolledStudent in Course
            // class because there is no property with the same name in CourseEntity class.
            BeanUtils.copyProperties(courseEntity, course2);
            System.out.print(course2);
            System.out.print(courseEntity);
        }

        {
            Student s = new Student();
            PropertyUtils.setProperty(s, "name", "tom");
            System.out.println(s);
        }
    }
}
