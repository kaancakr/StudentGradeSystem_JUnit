package org.example.gradingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Basis Path Tests for calculateCourseAverage")
class CalculateAverageBasisPathTests {
    private StudentGradingSystem system;
    private final String VALID_COURSE_CODE = "CS101";

    @BeforeEach
    void setUp() {
        system = new StudentGradingSystem();
        system.addCourse(VALID_COURSE_CODE, "Programming");
    }

    // P1: Non-existent Course (D1 is True)
    @Test
    @DisplayName("Path 1: Non-existent course should return 0.0")
    void path1_nonExistentCourse_shouldReturnZero() {
        assertEquals(0.0, system.calculateCourseAverage("NONEXISTENT"), 0.01);
    }

    // P2: No Enrolled Students (D2 loop does not execute)
    @Test
    @DisplayName("Path 2: Course exists, no enrollment, should return 0.0")
    void path2_courseExistsButNoEnrollment_shouldReturnZero() {
        assertEquals(0.0, system.calculateCourseAverage(VALID_COURSE_CODE), 0.01);
    }

    // P3: Enrolled, No Graded Students (D3 always False, D4 is True)
    @Test
    @DisplayName("Path 3: Enrolled but no grades assigned, should return 0.0")
    void path3_enrolledButNoGrades_shouldReturnZero() {
        system.addStudent("101", "A", "B");
        system.enrollStudentToCourse("101", VALID_COURSE_CODE);
        // Student 101 is enrolled but has no grade (-1.0)
        assertEquals(0.0, system.calculateCourseAverage(VALID_COURSE_CODE), 0.01);
    }

    // P4: Enrolled, Mixed Graded/Ungraded Students (D3 sometimes True, D4 is False)
    @Test
    @DisplayName("Path 4: Mixed graded/ungraded students, avg only counts graded")
    void path4_mixedGradedUngradedStudents_shouldReturnAverageOfGraded() {
        system.addStudent("101", "A", "B"); // Graded: 90.0
        system.addStudent("102", "C", "D"); // Graded: 70.0
        system.addStudent("103", "E", "F"); // Ungraded: -1.0

        system.enrollStudentToCourse("101", VALID_COURSE_CODE);
        system.enrollStudentToCourse("102", VALID_COURSE_CODE);
        system.enrollStudentToCourse("103", VALID_COURSE_CODE);

        system.assignGrade("101", VALID_COURSE_CODE, 90.0);
        system.assignGrade("102", VALID_COURSE_CODE, 70.0);

        // (90.0 + 70.0) / 2 = 80.0
        assertEquals(80.0, system.calculateCourseAverage(VALID_COURSE_CODE), 0.01);
    }

    // P5: Enrolled, All Graded Students (D3 always True, D4 is False)
    @Test
    @DisplayName("Path 5: All enrolled students are graded, should return full average")
    void path5_allEnrolledStudentsAreGraded_shouldReturnFullAverage() {
        system.addStudent("101", "A", "B");
        system.addStudent("102", "C", "D");

        system.enrollStudentToCourse("101", VALID_COURSE_CODE);
        system.enrollStudentToCourse("102", VALID_COURSE_CODE);

        system.assignGrade("101", VALID_COURSE_CODE, 100.0);
        system.assignGrade("102", VALID_COURSE_CODE, 60.0);

        // (100.0 + 60.0) / 2 = 80.0
        assertEquals(80.0, system.calculateCourseAverage(VALID_COURSE_CODE), 0.01);
    }
}