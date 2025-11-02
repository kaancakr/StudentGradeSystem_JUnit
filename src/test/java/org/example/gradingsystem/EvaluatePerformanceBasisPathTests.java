package org.example.gradingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Basis Path Testing for evaluateStudentPerformance()")
class EvaluatePerformanceBasisPathTests {

    private StudentGradingSystem system;
    private final String COURSE = "CS101";
    private final String STUDENT = "1001";

    @BeforeEach
    void setUp() {
        system = new StudentGradingSystem();
        system.addCourse(COURSE, "Programming");
        system.addStudent(STUDENT, "Ali", "Yılmaz");
        system.enrollStudentToCourse(STUDENT, COURSE);
    }

    // Path 1: Invalid student or course
    @Test
    void path1_invalidStudentOrCourse_shouldReturnInvalid() {
        assertEquals("Invalid", system.evaluateStudentPerformance("9999", COURSE));
        assertEquals("Invalid", system.evaluateStudentPerformance(STUDENT, "INVALID"));
    }

    // Path 2: No grade assigned
    @Test
    void path2_noGrade_shouldReturnNoGrade() {
        assertEquals("No Grade", system.evaluateStudentPerformance(STUDENT, COURSE));
    }

    // Path 3: Grade >= 85
    @Test
    void path3_excellentGrade_shouldReturnExcellent() {
        system.assignGrade(STUDENT, COURSE, 90.0);
        assertEquals("Excellent", system.evaluateStudentPerformance(STUDENT, COURSE));
    }

    // Path 4: Grade between 70–84
    @Test
    void path4_satisfactoryGrade_shouldReturnSatisfactory() {
        system.assignGrade(STUDENT, COURSE, 75.0);
        assertEquals("Satisfactory", system.evaluateStudentPerformance(STUDENT, COURSE));
    }

    // Path 5: Grade between 50–69
    @Test
    void path5_passGrade_shouldReturnPass() {
        system.assignGrade(STUDENT, COURSE, 60.0);
        assertEquals("Pass", system.evaluateStudentPerformance(STUDENT, COURSE));
    }

    // Path 6: Grade < 50
    @Test
    void path6_failGrade_shouldReturnFail() {
        system.assignGrade(STUDENT, COURSE, 40.0);
        assertEquals("Fail", system.evaluateStudentPerformance(STUDENT, COURSE));
    }
}
