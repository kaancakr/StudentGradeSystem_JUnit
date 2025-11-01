package org.example.gradingsystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Decision Table Tests for assignGrade method")
class AssignGradeDecisionTableTests {
    private StudentGradingSystem system;
    private final String VALID_STUDENT_ID = "101";
    private final String VALID_COURSE_CODE = "CS101";

    @BeforeEach
    void setUp() {
        system = new StudentGradingSystem();
        system.addStudent(VALID_STUDENT_ID, "Ahmet", "Yılmaz");
        system.addCourse(VALID_COURSE_CODE, "Intro to Programming");
        system.enrollStudentToCourse(VALID_STUDENT_ID, VALID_COURSE_CODE);
    }

    // Rule 1: Success Case (C1: Y, C2: Y, C3: Y)
    @Test
    @DisplayName("R1: Valid Student, Course, and Score should assign grade successfully")
    void rule1_validAssignment_shouldSucceed() {
        system.assignGrade(VALID_STUDENT_ID, VALID_COURSE_CODE, 88.5);
        Student student = system.getStudentById(VALID_STUDENT_ID);
        assertEquals(88.5, student.getGrade(VALID_COURSE_CODE));
    }

    // Rule 2: Missing Student Case (C1: N)
    @Test
    @DisplayName("R2: Non-existent Student should throw 'not found' exception")
    void rule2_nonExistentStudent_shouldThrowException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                system.assignGrade("999", VALID_COURSE_CODE, 70.0));
        assertEquals("Student or Course not found.", e.getMessage());
    }

    // Rule 3: Missing Course Case (C1: Y, C2: N)
    @Test
    @DisplayName("R3: Non-existent Course should throw 'not found' exception")
    void rule3_nonExistentCourse_shouldThrowException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                system.assignGrade(VALID_STUDENT_ID, "MATH999", 70.0));
        assertEquals("Student or Course not found.", e.getMessage());
    }

    // Rule 4: Invalid Score (Too Low) Case (C1: Y, C2: Y, C3: N - low)
    @Test
    @DisplayName("R4: Score < 0 should throw 'invalid score' exception")
    void rule4_invalidScoreTooLow_shouldThrowException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                system.assignGrade(VALID_STUDENT_ID, VALID_COURSE_CODE, -0.1));
        assertEquals("Score must be between 0 and 100.", e.getMessage());
    }

    // Rule 5: Invalid Score (Too High) Case (C1: Y, C2: Y, C3: N - high)
    @Test
    @DisplayName("R5: Score > 100 should throw 'invalid score' exception")
    void rule5_invalidScoreTooHigh_shouldThrowException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                system.assignGrade(VALID_STUDENT_ID, VALID_COURSE_CODE, 100.1));
        assertEquals("Score must be between 0 and 100.", e.getMessage());
    }
}