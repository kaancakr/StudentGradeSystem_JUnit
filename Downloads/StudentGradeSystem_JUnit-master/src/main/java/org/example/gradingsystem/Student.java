package org.example.gradingsystem;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private final String id;
    private final String name;
    private final String surname;
    private final Map<String, Double> courseGrades = new HashMap<>();

    public Student(String id, String name, String surname) {
        if (id == null || name == null || surname == null) {
            throw new IllegalArgumentException("Student properties cannot be null");
        }
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Map<String, Double> getCourseGrades() {
        return courseGrades;
    }

    // Methods
    public void addGrade(String courseCode, double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }
        courseGrades.put(courseCode, score);
    }

    public double getGrade(String courseCode) {
        return courseGrades.getOrDefault(courseCode, -1.0);
    }
}