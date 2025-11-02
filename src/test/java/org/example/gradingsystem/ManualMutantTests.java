package org.example.gradingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Manual Mutation Testing for GradingSystemTest methods
 * 
 * This test class contains 10 manually created mutant versions of the original
 * tests.
 * Each mutant test represents a common mutation operator applied to the code.
 * 
 * Mutation Operators Used:
 * 1. Relational Operator Replacement (ROR)
 * 2. Arithmetic Operator Replacement (AOR)
 * 3. Conditional Boundary Mutation (CBM)
 * 4. Negation Operator Insertion (NOT)
 * 5. Increment/Decrement (INC/DEC)
 * 6. Variable Replacement (VAR)
 * 7. Constant Replacement (CRP)
 * 
 * Original test: assignGradeToStudent from GradingSystemTest
 */
@DisplayName("Manual Mutation Testing - 10 Mutants")
class ManualMutantTests {

    private StudentGradingSystem system;

    @BeforeEach
    void setUp() {
        system = new StudentGradingSystem();
        system.addStudent("101", "Ahmet", "Yılmaz");
        system.addCourse("CS101", "Introduction to Programming");
        system.enrollStudentToCourse("101", "CS101");
    }

    // ========== MUTANT 1: Relational Operator Replacement (ROR) ==========
    // Original: grade == 85.0
    // Mutant: grade >= 85.0 (changed == to >=)
    @Test
    @DisplayName("Mutant 1: ROR - Changed == to >=")
    void mutant1_relationalOperatorReplacement() {
        system.assignGrade("101", "CS101", 85.0);
        // Original test would check: assertEquals(85.0, ...)
        // Mutant changes the comparison operator
        double grade = system.getStudentById("101").getGrade("CS101");
        assertTrue(grade >= 85.0, "Mutant 1: Should be >= 85.0 instead of == 85.0");
    }

    // ========== MUTANT 2: Arithmetic Operator Replacement (AOR) ==========
    // Original: assignGrade("101", "CS101", 85.0)
    // Mutant: assignGrade("101", "CS101", 85.0 + 1.0) (changed value)
    // Simulating: grade = 85.0 + 1.0 instead of grade = 85.0
    @Test
    @DisplayName("Mutant 2: AOR - Changed + to -")
    void mutant2_arithmeticOperatorReplacement() {
        // Simulating mutation in calculation: 85.0 - 1.0 = 84.0
        system.assignGrade("101", "CS101", 84.0); // Mutant: changed from 85.0 to 85.0-1.0
        double grade = system.getStudentById("101").getGrade("CS101");
        assertEquals(84.0, grade, "Mutant 2: Arithmetic operator changed from + to -");
    }

    // ========== MUTANT 3: Conditional Boundary Mutation (CBM) ==========
    // Original: if (score < 0 || score > 100)
    // Mutant: if (score <= 0 || score >= 100) (changed < to <=, > to >=)
    @Test
    @DisplayName("Mutant 3: CBM - Changed < to <=")
    void mutant3_conditionalBoundaryMutation() {
        // Testing boundary mutation: score <= 0 instead of score < 0
        // Using convertScoreToLetterGrade which has boundary checks
        // Original: if (score < 0) throws exception
        // Mutant: if (score <= 0) throws exception (would reject 0.0)
        assertThrows(IllegalArgumentException.class, () -> {
            StudentGradingSystem.convertScoreToLetterGrade(-1.0); // Original rejects < 0
        }, "Mutant 3: Boundary condition changed from < 0 to <= 0");

        // With mutant, 0.0 would throw exception, but in original it's valid
        assertDoesNotThrow(() -> {
            StudentGradingSystem.convertScoreToLetterGrade(0.0); // Original accepts 0.0
        }, "Mutant 3: Original should accept 0.0, but mutant would reject it");
    }

    // ========== MUTANT 4: Negation Operator Insertion (NOT) ==========
    // Original: if (!students.containsKey(studentId))
    // Mutant: if (students.containsKey(studentId)) (removed !)
    @Test
    @DisplayName("Mutant 4: NOT - Removed negation operator")
    void mutant4_negationOperatorRemoval() {
        // Mutant removes negation, so condition is inverted
        // Original: if (!students.containsKey(...)) throw
        // Mutant: if (students.containsKey(...)) throw (WRONG!)
        assertDoesNotThrow(() -> {
            system.assignGrade("101", "CS101", 85.0);
        }, "Mutant 4: Negation operator removed from condition");
    }

    // ========== MUTANT 5: Increment/Decrement (INC/DEC) ==========
    // Original: return totalScore / gradedStudents
    // Mutant: return totalScore / (gradedStudents + 1) (increment)
    @Test
    @DisplayName("Mutant 5: INC - Incremented denominator")
    void mutant5_incrementMutation() {
        system.assignGrade("101", "CS101", 90.0);
        // Original calculation: average = 90.0 / 1 = 90.0
        // Mutant calculation: average = 90.0 / (1 + 1) = 45.0
        double average = system.calculateCourseAverage("CS101");
        // Mutant would return wrong value: 90.0 / 2 = 45.0 instead of 90.0
        assertEquals(90.0, average, 0.01, "Mutant 5: Increment mutation in denominator");
    }

    // ========== MUTANT 6: Variable Replacement (VAR) ==========
    // Original: student.addGrade(courseCode, score)
    // Mutant: course.addGrade(courseCode, score) (wrong variable)
    @Test
    @DisplayName("Mutant 6: VAR - Wrong variable used")
    void mutant6_variableReplacement() {
        // Mutant uses wrong variable: course instead of student
        // This would cause a compilation error or runtime error
        assertDoesNotThrow(() -> {
            system.assignGrade("101", "CS101", 85.0);
        }, "Mutant 6: Variable replacement would cause error");

        // Verify correct variable was used
        assertNotNull(system.getStudentById("101").getGrade("CS101"));
    }

    // ========== MUTANT 7: Constant Replacement (CRP) ==========
    // Original: if (grade == -1.0) return "No Grade"
    // Mutant: if (grade == -2.0) return "No Grade" (changed constant)
    @Test
    @DisplayName("Mutant 7: CRP - Changed constant value")
    void mutant7_constantReplacement() {
        // Original: grade == -1.0 means no grade
        // Mutant: grade == -2.0 means no grade (WRONG!)
        String result = system.evaluateStudentPerformance("101", "CS101");
        // With mutant, this might return different result
        assertEquals("No Grade", result, "Mutant 7: Constant value changed from -1.0 to -2.0");
    }

    // ========== MUTANT 8: Logical Operator Replacement (LOR) ==========
    // Original: if (student == null || course == null)
    // Mutant: if (student == null && course == null) (changed || to &&)
    @Test
    @DisplayName("Mutant 8: LOR - Changed || to &&")
    void mutant8_logicalOperatorReplacement() {
        // Original: returns "Invalid" if student is null OR course is null
        // Mutant: returns "Invalid" only if BOTH are null (WRONG!)
        String result1 = system.evaluateStudentPerformance("999", "CS101"); // Invalid student
        assertEquals("Invalid", result1, "Mutant 8: Logical OR changed to AND");

        String result2 = system.evaluateStudentPerformance("101", "INVALID"); // Invalid course
        assertEquals("Invalid", result2, "Mutant 8: Logical OR changed to AND");
    }

    // ========== MUTANT 9: Statement Deletion (SDL) ==========
    // Original: students.put(id, new Student(id, name, surname));
    // Mutant: // students.put(id, new Student(id, name, surname)); (deleted line)
    @Test
    @DisplayName("Mutant 9: SDL - Statement deleted")
    void mutant9_statementDeletion() {
        // Mutant deletes the statement that adds student to map
        // Original: system.addStudent(...) should add student
        // Mutant: system.addStudent(...) does nothing (statement deleted)
        system.addStudent("999", "Test", "User");
        assertNotNull(system.getStudentById("999"), "Mutant 9: Statement deletion would prevent student addition");
    }

    // ========== MUTANT 10: Return Value Mutation (RVM) ==========
    // Original: return "Excellent"
    // Mutant: return "Good" (changed return value)
    @Test
    @DisplayName("Mutant 10: RVM - Changed return value")
    void mutant10_returnValueMutation() {
        system.assignGrade("101", "CS101", 90.0); // Score >= 85
        String result = system.evaluateStudentPerformance("101", "CS101");
        // Original returns "Excellent"
        // Mutant returns "Good" instead
        assertEquals("Excellent", result, "Mutant 10: Return value changed from 'Excellent' to 'Good'");
    }

    // ========== SUMMARY ==========
    /**
     * Mutation Testing Summary:
     * 
     * Total Mutants: 10
     * 
     * Mutant Categories:
     * - Relational Operator Replacement (ROR): 1
     * - Arithmetic Operator Replacement (AOR): 1
     * - Conditional Boundary Mutation (CBM): 1
     * - Negation Operator Insertion (NOT): 1
     * - Increment/Decrement (INC/DEC): 1
     * - Variable Replacement (VAR): 1
     * - Constant Replacement (CRP): 1
     * - Logical Operator Replacement (LOR): 1
     * - Statement Deletion (SDL): 1
     * - Return Value Mutation (RVM): 1
     * 
     * All mutants should be KILLED by the test suite.
     * A mutant is KILLED when a test fails due to the mutation.
     * A mutant SURVIVES when tests still pass despite the mutation.
     */
}
