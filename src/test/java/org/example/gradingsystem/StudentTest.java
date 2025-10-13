package org.example.gradingsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Test Suite for Student Model Class
 * 
 * This test class validates all functionality of the Student entity including:
 * - Student creation and initialization
 * - Grade assignment and management
 * - Data validation and error handling
 * - Edge cases and boundary conditions
 * - Input validation and null handling
 * 
 * Test Categories:
 * 1. Basic Functionality: Student creation, property access, information retrieval
 * 2. Grade Management: Grade assignment, retrieval, updates, multiple courses
 * 3. Data Validation: Input validation, null handling, invalid grade ranges
 * 4. Edge Cases: Boundary values (0.0, 100.0), invalid inputs, null values
 * 5. Integration: Student-grade relationship management
 * 
 * Each test method follows the Arrange-Act-Assert pattern with detailed logging
 * for better debugging and test result interpretation.
 * 
 * @author Test Suite
 * @version 1.0
 */
@DisplayName("Tests for Student Model")
class StudentTest {

    /**
     * Test Case: Basic Student Creation
     * 
     * Purpose: Verifies that a Student object can be created with valid parameters
     * and that all initial properties are set correctly.
     * 
     * Test Steps:
     * 1. Create a new Student with ID, name, and surname
     * 2. Verify student ID is set correctly
     * 3. Verify student name is set correctly
     * 4. Verify student surname is set correctly
     * 5. Verify initial grades map is empty
     * 
     * Expected Result: Student object created with correct properties and empty grades map
     */
    @Test
    @DisplayName("Student should be created with correct properties")
    void studentCreation() {
        System.out.println(">>> Running test: studentCreation");
        System.out.println("Action: Creating new Student('201', 'Fatma', 'Öztürk')...");
        Student student = new Student("201", "Fatma", "Öztürk");

        System.out.println("Verification: Checking student properties and initial empty grades.");
        assertEquals("201", student.getId());
        assertEquals("Fatma", student.getName());
        assertEquals("Öztürk", student.getSurname());
        assertTrue(student.getCourseGrades().isEmpty());
        System.out.println("<<< Test finished: studentCreation");
    }

    /**
     * Test Case: Grade Assignment
     * 
     * Purpose: Verifies that grades can be successfully assigned to students
     * for specific courses and that the grades are properly stored and retrievable.
     * 
     * Test Steps:
     * 1. Create a new Student
     * 2. Add a grade for a specific course
     * 3. Retrieve the grade to verify it was stored correctly
     * 4. Verify the grades map is no longer empty
     * 
     * Expected Result: Grade successfully assigned and retrievable from student
     */
    @Test
    @DisplayName("Should add grade to student correctly")
    void addGradeToStudent() {
        System.out.println(">>> Running test: addGradeToStudent");
        Student student = new Student("201", "Fatma", "Öztürk");
        System.out.println("Action: Adding grade 95.5 for course 'PHY101'.");
        student.addGrade("PHY101", 95.5);

        double actualGrade = student.getGrade("PHY101");
        boolean isEmpty = student.getCourseGrades().isEmpty();
        System.out.println("Verification: Retrieved grade is " + actualGrade + ". Is grades map empty? " + isEmpty);
        assertEquals(95.5, actualGrade);
        assertFalse(isEmpty);
        System.out.println("<<< Test finished: addGradeToStudent");
    }

    /**
     * Test Case: Invalid Grade Validation
     * 
     * Purpose: Verifies that the Student class properly validates grade inputs
     * and throws appropriate exceptions for invalid grade values.
     * 
     * Test Steps:
     * 1. Create a new Student
     * 2. Attempt to add a negative grade (should throw exception)
     * 3. Attempt to add a grade greater than 100 (should throw exception)
     * 4. Verify both attempts throw IllegalArgumentException
     * 
     * Expected Result: IllegalArgumentException thrown for both invalid grade values
     */
    @Test
    @DisplayName("Should throw exception for invalid grade")
    void addInvalidGrade_ShouldThrowException() {
        System.out.println(">>> Running test: addInvalidGrade_ShouldThrowException");
        Student student = new Student("201", "Fatma", "Öztürk");

        System.out.println("Action: Attempting to add a negative grade (-10). Expecting exception.");
        assertThrows(IllegalArgumentException.class, () -> student.addGrade("PHY101", -10));
        System.out.println("Verified: Exception was thrown for negative grade.");

        System.out.println("Action: Attempting to add a grade greater than 100 (101). Expecting exception.");
        assertThrows(IllegalArgumentException.class, () -> student.addGrade("PHY101", 101));
        System.out.println("Verified: Exception was thrown for grade > 100.");
        System.out.println("<<< Test finished: addInvalidGrade_ShouldThrowException");
    }

    @Test
    @DisplayName("Should throw exception when creating student with null values")
    void createStudentWithNullValues_ShouldThrowException() {
        System.out.println(">>> Running test: createStudentWithNullValues_ShouldThrowException");

        System.out.println("Action: Attempting to create student with null ID.");
        assertThrows(IllegalArgumentException.class, () -> new Student(null, "Fatma", "Öztürk"));
        System.out.println("Verified: Exception was thrown for null ID.");

        System.out.println("Action: Attempting to create student with null name.");
        assertThrows(IllegalArgumentException.class, () -> new Student("201", null, "Öztürk"));
        System.out.println("Verified: Exception was thrown for null name.");

        System.out.println("Action: Attempting to create student with null surname.");
        assertThrows(IllegalArgumentException.class, () -> new Student("201", "Fatma", null));
        System.out.println("Verified: Exception was thrown for null surname.");

        System.out.println("<<< Test finished: createStudentWithNullValues_ShouldThrowException");
    }

    @Test
    @DisplayName("Should handle multiple grades for different courses")
    void addMultipleGradesForDifferentCourses() {
        System.out.println(">>> Running test: addMultipleGradesForDifferentCourses");
        Student student = new Student("201", "Fatma", "Öztürk");

        System.out.println("Action: Adding grades for multiple courses.");
        student.addGrade("MATH101", 85.0);
        student.addGrade("PHY101", 92.5);
        student.addGrade("CHEM101", 78.0);

        System.out.println("Verification: Checking all grades are stored correctly.");
        assertEquals(85.0, student.getGrade("MATH101"));
        assertEquals(92.5, student.getGrade("PHY101"));
        assertEquals(78.0, student.getGrade("CHEM101"));
        assertEquals(3, student.getCourseGrades().size());

        System.out.println("<<< Test finished: addMultipleGradesForDifferentCourses");
    }

    @Test
    @DisplayName("Should update existing grade when adding same course again")
    void updateExistingGrade() {
        System.out.println(">>> Running test: updateExistingGrade");
        Student student = new Student("201", "Fatma", "Öztürk");

        System.out.println("Action: Adding initial grade 75.0 for MATH101.");
        student.addGrade("MATH101", 75.0);
        assertEquals(75.0, student.getGrade("MATH101"));

        System.out.println("Action: Updating grade to 88.0 for same course.");
        student.addGrade("MATH101", 88.0);
        assertEquals(88.0, student.getGrade("MATH101"));
        assertEquals(1, student.getCourseGrades().size());

        System.out.println("<<< Test finished: updateExistingGrade");
    }

    @Test
    @DisplayName("Should handle edge case grades (0.0 and 100.0)")
    void handleEdgeCaseGrades() {
        System.out.println(">>> Running test: handleEdgeCaseGrades");
        Student student = new Student("201", "Fatma", "Öztürk");

        System.out.println("Action: Adding minimum grade (0.0) and maximum grade (100.0).");
        student.addGrade("MATH101", 0.0);
        student.addGrade("PHY101", 100.0);

        System.out.println("Verification: Both edge case grades are stored correctly.");
        assertEquals(0.0, student.getGrade("MATH101"));
        assertEquals(100.0, student.getGrade("PHY101"));

        System.out.println("<<< Test finished: handleEdgeCaseGrades");
    }

    @Test
    @DisplayName("Should return correct student information")
    void getStudentInformation() {
        System.out.println(">>> Running test: getStudentInformation");
        Student student = new Student("201", "Fatma", "Öztürk");

        System.out.println("Verification: Checking all getter methods return correct values.");
        assertEquals("201", student.getId());
        assertEquals("Fatma", student.getName());
        assertEquals("Öztürk", student.getSurname());
        assertNotNull(student.getCourseGrades());
        assertTrue(student.getCourseGrades() instanceof java.util.Map);

        System.out.println("<<< Test finished: getStudentInformation");
    }

    @Test
    @DisplayName("Should retrieve correct grade after assigning")
    void retrieveAssignedGrade() {
        System.out.println(">>> Running test: retrieveAssignedGrade");
        Student student = new Student("201", "Fatma", "Öztürk");
        System.out.println("Action: Adding grade 95.5 for course 'PHY101'.");
        student.addGrade("PHY101", 95.5);

        double actualGrade1 = student.getGrade("PHY101");
        System.out.println("Verification 1: Retrieved grade for 'PHY101' is " + actualGrade1 + ". Expected is 95.5.");
        assertEquals(95.5, actualGrade1);

        double actualGrade2 = student.getGrade("CHEM101");
        System.out.println("Verification 2: Retrieved grade for non-existent course 'CHEM101' is " + actualGrade2
                + ". Expected is -1.0.");
        assertEquals(-1.0, actualGrade2);
        System.out.println("<<< Test finished: retrieveAssignedGrade");
    }
}
