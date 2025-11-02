package org.example.gradingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Decision Table Testing for determineExamEligibility()")
class DetermineExamEligibilityDecisionTableTests {

    private StudentGradingSystem system;
    private final String COURSE = "CS102";
    private final String STUDENT = "2001";

    @BeforeEach
    void setUp() {
        system = new StudentGradingSystem();
        system.addCourse(COURSE, "Data Structures");
        system.addStudent(STUDENT, "Ayşe", "Demir");
        system.enrollStudentToCourse(STUDENT, COURSE);
    }

    // Decision Table:
    // | Rule | AttendanceComplete | DisciplinePenalty | HasGrade | Expected Outcome |
    // |------|--------------------|-------------------|-----------|------------------|
    // | R1   | N | - | - | Not Eligible |
    // | R2   | Y | Y | - | Not Eligible |
    // | R3   | Y | N | N | Conditional |
    // | R4   | Y | N | Y | Eligible |

    // R1
    @Test
    void rule1_incompleteAttendance_shouldReturnNotEligible() {
        assertEquals("Not Eligible", system.determineExamEligibility(STUDENT, COURSE, false, false));
    }

    // R2
    @Test
    void rule2_withDisciplinePenalty_shouldReturnNotEligible() {
        assertEquals("Not Eligible", system.determineExamEligibility(STUDENT, COURSE, true, true));
    }

    // R3
    @Test
    void rule3_noGradeButEligibleAttendance_shouldReturnConditional() {
        assertEquals("Conditional", system.determineExamEligibility(STUDENT, COURSE, true, false));
    }

    // R4
    @Test
    void rule4_allGoodConditions_shouldReturnEligible() {
        system.assignGrade(STUDENT, COURSE, 80.0);
        assertEquals("Eligible", system.determineExamEligibility(STUDENT, COURSE, true, false));
    }

    // Validation: Invalid student or course
    @Test
    void invalidStudentOrCourse_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                system.determineExamEligibility("999", COURSE, true, false));
    }
}
