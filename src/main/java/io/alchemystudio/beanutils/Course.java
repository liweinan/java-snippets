package io.alchemystudio.beanutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {
    private String name;
    private List<String> codes;
    private Map<String, Student> enrolledStudent = new HashMap<>();

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

    public Map<String, Student> getEnrolledStudent() {
        return enrolledStudent;
    }

    public void setEnrolledStudent(Map<String, Student> enrolledStudent) {
        this.enrolledStudent = enrolledStudent;
    }

    private void append(StringBuilder builder, String text) {
        builder.append(text + "\n");
    }

    public void setEnrolledStudent(String k, Student student) {
        Map<String, Student> enroll = new HashMap<>();
        enroll.put(k, student);
        this.enrolledStudent = enroll;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        append(builder, "Name: " + name);
        append(builder, "Codes: " + codes.toString());
        append(builder, "Enrolled Student: " + enrolledStudent.toString());
        return builder.toString();
    }
}
