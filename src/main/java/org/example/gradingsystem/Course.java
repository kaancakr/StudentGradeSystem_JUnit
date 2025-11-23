package org.example.gradingsystem;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private final String courseCode;
    private final String courseName;
    private final List<Student> enrolledStudents = new ArrayList<>();

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public int getStudentCount() {
        return enrolledStudents.size();
    }

    // Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    // Methods
    public void addStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }
}