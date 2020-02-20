package io.alchemystudio.beanutils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseEntity {
    private String name;
    private List<String> codes;
    private Map<String, Student> students = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public Map<String, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<String, Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        append(builder, "Name: " + name);
        append(builder, "Codes: " + codes.toString());
        append(builder, "Students: " + students.toString());
        return builder.toString();
    }

    private void append(StringBuilder builder, String text) {
        builder.append(text + "\n");
    }
}
