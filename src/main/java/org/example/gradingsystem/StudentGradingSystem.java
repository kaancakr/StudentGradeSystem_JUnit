package org.example.gradingsystem;

import java.util.HashMap;
import java.util.Map;

public class StudentGradingSystem {
    public int getStudentCount() {
        return students.size();
    }

    public Student getStudentById(String studentId) {
        return students.get(studentId);
    }

    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Course> courses = new HashMap<>();

    public void addStudent(String id, String name, String surname) {
        if (students.containsKey(id)) {
            throw new IllegalArgumentException("Student with this ID already exists.");
        }
        students.put(id, new Student(id, name, surname));
    }

    public void addCourse(String code, String name) {
        if (courses.containsKey(code)) {
            throw new IllegalArgumentException("Course with this code already exists.");
        }
        courses.put(code, new Course(code, name));
    }

    public void enrollStudentToCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student or Course not found.");
        }
        course.addStudent(student);
    }

    public void assignGrade(String studentId, String courseCode, double score) {
        Student student = students.get(studentId);
        if (!students.containsKey(studentId) || !courses.containsKey(courseCode)) {
            throw new IllegalArgumentException("Student or Course not found.");
        }
        student.addGrade(courseCode, score);
    }

    public double calculateCourseAverage(String courseCode) {
        Course course = courses.get(courseCode);
        if (course == null) return 0.0;

        double totalScore = 0;
        int gradedStudents = 0;
        for (Student student : course.getEnrolledStudents()) {
            double grade = student.getGrade(courseCode);
            if (grade != -1.0) {
                totalScore += grade;
                gradedStudents++;
            }
        }
        return gradedStudents == 0 ? 0.0 : totalScore / gradedStudents;
    }

    public static String convertScoreToLetterGrade(double score) {
        if (score > 100 || score < 0) throw new IllegalArgumentException("Invalid score");
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
}