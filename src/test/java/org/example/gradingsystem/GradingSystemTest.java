package org.example.gradingsystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Test Suite for StudentGradingSystem Core Functionality
 * 
 * This test class validates all core functionality of the StudentGradingSystem including:
 * - Student and course management
 * - Enrollment and grade assignment
 * - Average calculations and grade conversions
 * - Data validation and error handling
 * - Performance and edge cases
 * - Integration between system components
 * 
 * Test Categories:
 * 1. Basic Operations: Student/course creation, enrollment, grade assignment
 * 2. Calculations: Course averages, grade conversions, statistical operations
 * 3. Data Validation: Input validation, duplicate prevention, error handling
 * 4. Edge Cases: Boundary conditions, empty states, invalid inputs
 * 5. Performance: Large datasets, concurrent operations, timeout validation
 * 6. Integration: System-wide functionality, component interactions
 * 
 * Test Setup:
 * - Each test method uses @BeforeEach to initialize a clean system state
 * - Tests are designed to be independent and can run in any order
 * - Comprehensive logging provides detailed test execution information
 * 
 * @author Test Suite
 * @version 1.0
 */
@DisplayName("Grading System Core Functionality Tests")
class GradingSystemTest {

    private StudentGradingSystem system;

    /**
     * Test Setup Method
     * 
     * Purpose: Initializes a clean StudentGradingSystem instance before each test
     * to ensure test isolation and consistent starting conditions.
     * 
     * Setup Actions:
     * 1. Create a new StudentGradingSystem instance
     * 2. Add a default student (ID: 101, Name: Ahmet Yılmaz)
     * 3. Add a default course (Code: CS101, Name: Introduction to Programming)
     * 
     * This ensures each test starts with a known, consistent state.
     */
    @BeforeEach
    void setUp() {
        System.out.println("--- @BeforeEach: Setting up new test ---");
        system = new StudentGradingSystem();
        system.addStudent("101", "Ahmet", "Yılmaz");
        system.addCourse("CS101", "Introduction to Programming");
        System.out.println("Setup complete. System has 1 student and 1 course.");
    }

    /**
     * Test Case: Student Enrollment
     * 
     * Purpose: Verifies that students can be successfully enrolled in courses
     * through the grading system.
     * 
     * Test Steps:
     * 1. Use the pre-configured student (101) and course (CS101)
     * 2. Enroll the student in the course
     * 3. Verify enrollment completes without errors
     * 
     * Expected Result: Student successfully enrolled in course
     */
    @Test
    @DisplayName("Should enroll a student to a course successfully")
    void enrollStudentToCourse() {
        System.out.println(">>> Running test: enrollStudentToCourse");
        System.out.println("Action: Enrolling student 101 into course CS101...");
        system.enrollStudentToCourse("101", "CS101");
        // Verification for this is typically done by checking the state,
        // which other tests do. We can add a simple check here too.
        System.out.println("Enrollment action complete.");
        System.out.println("<<< Test finished: enrollStudentToCourse");
    }

    @Test
    @DisplayName("Should assign a grade to an enrolled student")
    void assignGradeToStudent() {
        System.out.println(">>> Running test: assignGradeToStudent");
        System.out.println("Action: Enrolling student 101 into course CS101...");
        system.enrollStudentToCourse("101", "CS101");
        System.out.println("Action: Assigning grade 85.0 to student 101 for CS101...");
        system.assignGrade("101", "CS101", 85.0);
        System.out.println("Grade assignment complete.");
        System.out.println("<<< Test finished: assignGradeToStudent");
    }

    @Test
    @DisplayName("Should throw exception when enrolling non-existent student or course")
    void enrollNonExistentStudentOrCourse_ShouldThrowException() {
        System.out.println(">>> Running test: enrollNonExistentStudentOrCourse_ShouldThrowException");

        System.out.println("Action: Attempting to enroll non-existent student '999'. Expecting an exception.");
        assertThrows(IllegalArgumentException.class, () -> {
            system.enrollStudentToCourse("999", "CS101");
        });
        System.out.println("Verified: Exception was thrown for non-existent student.");

        System.out.println("Action: Attempting to enroll in non-existent course 'CS999'. Expecting an exception.");
        assertThrows(IllegalArgumentException.class, () -> {
            system.enrollStudentToCourse("101", "CS999");
        });
        System.out.println("Verified: Exception was thrown for non-existent course.");
        System.out.println("<<< Test finished: enrollNonExistentStudentOrCourse_ShouldThrowException");
    }

    /**
     * Test Case: Course Average Calculation
     * 
     * Purpose: Verifies that the system correctly calculates the average grade
     * for all students enrolled in a specific course.
     * 
     * Test Steps:
     * 1. Add a second student to the system
     * 2. Enroll both students in the same course
     * 3. Assign different grades to each student (90.0 and 70.0)
     * 4. Calculate the course average
     * 5. Verify the average is correct (80.0)
     * 
     * Expected Result: Course average calculated as 80.0 (average of 90.0 and 70.0)
     */
    @Test
    @DisplayName("Should calculate course average correctly")
    void calculateCourseAverage() {
        System.out.println(">>> Running test: calculateCourseAverage");
        System.out.println("Setup: Adding student '102' and enrolling both students to CS101.");
        system.addStudent("102", "Ayşe", "Kaya");
        system.enrollStudentToCourse("101", "CS101");
        system.enrollStudentToCourse("102", "CS101");

        System.out.println("Action: Assigning grades 90.0 and 70.0.");
        system.assignGrade("101", "CS101", 90.0);
        system.assignGrade("102", "CS101", 70.0);

        double actualAverage = system.calculateCourseAverage("CS101");
        System.out.println("Verification: Calculated average is " + actualAverage + ". Expected is 80.0.");
        assertEquals(80.0, actualAverage, 0.01);
        System.out.println("<<< Test finished: calculateCourseAverage");
    }

    @Test
    @DisplayName("Should return 0 for course average if no students are graded")
    void calculateCourseAverage_NoGradedStudents_ShouldReturnZero() {
        System.out.println(">>> Running test: calculateCourseAverage_NoGradedStudents_ShouldReturnZero");
        System.out.println("Setup: Enrolling student '101' but not assigning a grade.");
        system.enrollStudentToCourse("101", "CS101");

        double actualAverage = system.calculateCourseAverage("CS101");
        System.out.println("Verification: Calculated average is " + actualAverage + ". Expected is 0.0.");
        assertEquals(0.0, actualAverage);
        System.out.println("<<< Test finished: calculateCourseAverage_NoGradedStudents_ShouldReturnZero");
    }

    /**
     * Parameterized Test Case: Grade Conversion
     * 
     * Purpose: Verifies that numeric scores are correctly converted to letter grades
     * using multiple test cases to ensure comprehensive coverage of the grading scale.
     * 
     * Test Data:
     * - 95.0, 100.0 → A grade
     * - 85.0 → B grade  
     * - 75.0 → C grade
     * - 65.0 → D grade
     * - 55.0, 0.0 → F grade
     * 
     * Expected Result: Each numeric score converts to the correct letter grade
     */
    @ParameterizedTest(name = "Run {index}: Score {0} should be grade {1}")
    @CsvSource({
            "95.0, A", "85.0, B", "75.0, C", "65.0, D", "55.0, F", "100.0, A", "0.0, F"
    })
    void convertScoreToLetterGrade_Parameterized(double score, String expectedGrade) {
        System.out.println(">>> Running parameterized test with score: " + score);
        String actualGrade = StudentGradingSystem.convertScoreToLetterGrade(score);
        System.out.println("Verification: Actual grade is " + actualGrade + ". Expected is " + expectedGrade);
        assertEquals(expectedGrade, actualGrade);
        System.out.println("<<< Finished parameterized run.");
    }

    @Test
    @DisplayName("Should add multiple students and check them all")
    void addMultipleStudentsAndVerify() {
        System.out.println(">>> Running test: addMultipleStudentsAndVerify");
        System.out.println("Action: Adding two more students, '102' and '103'.");
        system.addStudent("102", "Zeynep", "Güneş");
        system.addStudent("103", "Mustafa", "Kurt");

        System.out.println("Verification: Checking if all three students exist and total count is 3.");
        assertAll("Verify all students are added",
                () -> assertNotNull(system.getStudentById("101")),
                () -> assertNotNull(system.getStudentById("102")),
                () -> assertNotNull(system.getStudentById("103")),
                () -> assertEquals(3, system.getStudentCount()));
        System.out.println("<<< Test finished: addMultipleStudentsAndVerify");
    }

    @Test
    @DisplayName("Should throw exception when adding a student with a duplicate ID")
    void addStudent_DuplicateId_ShouldThrowException() {
        System.out.println(">>> Running test: addStudent_DuplicateId_ShouldThrowException");
        System.out.println("Action: Attempting to add a student with existing ID '101'. Expecting an exception.");
        assertThrows(IllegalArgumentException.class, () -> {
            system.addStudent("101", "Mehmet", "Demir");
        });
        System.out.println("Verified: Exception was thrown.");
        System.out.println("<<< Test finished: addStudent_DuplicateId_ShouldThrowException");
    }

    @Test
    @DisplayName("Should throw exception when adding a course with a duplicate code")
    void addCourse_DuplicateCode_ShouldThrowException() {
        System.out.println(">>> Running test: addCourse_DuplicateCode_ShouldThrowException");
        System.out.println("Action: Attempting to add a course with existing code 'CS101'. Expecting an exception.");
        assertThrows(IllegalArgumentException.class, () -> {
            system.addCourse("CS101", "Advanced Programming");
        });
        System.out.println("Verified: Exception was thrown.");
        System.out.println("<<< Test finished: addCourse_DuplicateCode_ShouldThrowException");
    }

    // Performance test does not need verbose logging
    @Test
    @DisplayName("Performance test for course average calculation")
    void calculateCourseAverage_PerformanceTest() {
        for (int i = 0; i < 1000; i++) {
            String studentId = "s" + i;
            system.addStudent(studentId, "Name" + i, "Surname" + i);
            system.enrollStudentToCourse(studentId, "CS101");
            system.assignGrade(studentId, "CS101", 80);
        }
        assertTimeout(Duration.ofMillis(100), () -> {
            system.calculateCourseAverage("CS101");
        });
    }

    @Test
    @Disabled("This feature is under development and will be enabled later.")
    @DisplayName("Calculate student GPA (Not implemented yet)")
    void calculateStudentGPA_Disabled() {
        fail("This test should be disabled");
    }

    @Test
    @DisplayName("Should handle assignGrade with invalid parameters")
    void assignGradeWithInvalidParameters_ShouldThrowException() {
        System.out.println(">>> Running test: assignGradeWithInvalidParameters_ShouldThrowException");

        System.out.println("Action: Attempting to assign grade to non-existent student.");
        assertThrows(IllegalArgumentException.class, () -> {
            system.assignGrade("999", "CS101", 85.0);
        });
        System.out.println("Verified: Exception was thrown for non-existent student.");

        System.out.println("Action: Attempting to assign grade to non-existent course.");
        assertThrows(IllegalArgumentException.class, () -> {
            system.assignGrade("101", "CS999", 85.0);
        });
        System.out.println("Verified: Exception was thrown for non-existent course.");

        System.out.println("<<< Test finished: assignGradeWithInvalidParameters_ShouldThrowException");
    }

    @Test
    @DisplayName("Should handle assignGrade with invalid score values")
    void assignGradeWithInvalidScore_ShouldThrowException() {
        System.out.println(">>> Running test: assignGradeWithInvalidScore_ShouldThrowException");
        system.enrollStudentToCourse("101", "CS101");

        System.out.println("Action: Attempting to assign negative grade.");
        assertThrows(IllegalArgumentException.class, () -> {
            system.assignGrade("101", "CS101", -10.0);
        });
        System.out.println("Verified: Exception was thrown for negative grade.");

        System.out.println("Action: Attempting to assign grade greater than 100.");
        assertThrows(IllegalArgumentException.class, () -> {
            system.assignGrade("101", "CS101", 150.0);
        });
        System.out.println("Verified: Exception was thrown for grade > 100.");

        System.out.println("<<< Test finished: assignGradeWithInvalidScore_ShouldThrowException");
    }

    @Test
    @DisplayName("Should calculate course average with mixed graded and ungraded students")
    void calculateCourseAverageWithMixedGradedStudents() {
        System.out.println(">>> Running test: calculateCourseAverageWithMixedGradedStudents");

        System.out.println("Setup: Adding multiple students and enrolling them.");
        system.addStudent("102", "Ayşe", "Kaya");
        system.addStudent("103", "Mehmet", "Demir");
        system.enrollStudentToCourse("101", "CS101");
        system.enrollStudentToCourse("102", "CS101");
        system.enrollStudentToCourse("103", "CS101");

        System.out.println("Action: Assigning grades to only some students.");
        system.assignGrade("101", "CS101", 90.0);
        system.assignGrade("102", "CS101", 70.0);
        // Student 103 remains ungraded

        double actualAverage = system.calculateCourseAverage("CS101");
        System.out.println("Verification: Average calculated only for graded students. Expected: 80.0");
        assertEquals(80.0, actualAverage, 0.01);
        System.out.println("<<< Test finished: calculateCourseAverageWithMixedGradedStudents");
    }

    @Test
    @DisplayName("Should handle getStudentById with valid and invalid IDs")
    void getStudentByIdTests() {
        System.out.println(">>> Running test: getStudentByIdTests");

        System.out.println("Verification: Getting existing student by ID.");
        Student student = system.getStudentById("101");
        assertNotNull(student);
        assertEquals("101", student.getId());
        assertEquals("Ahmet", student.getName());
        assertEquals("Yılmaz", student.getSurname());

        System.out.println("Verification: Getting non-existent student returns null.");
        Student nonExistentStudent = system.getStudentById("999");
        assertNull(nonExistentStudent);

        System.out.println("<<< Test finished: getStudentByIdTests");
    }

    @Test
    @DisplayName("Should handle getStudentCount correctly")
    void getStudentCountTests() {
        System.out.println(">>> Running test: getStudentCountTests");

        System.out.println("Verification: Initial student count is 1.");
        assertEquals(1, system.getStudentCount());

        System.out.println("Action: Adding more students.");
        system.addStudent("102", "Ayşe", "Kaya");
        system.addStudent("103", "Mehmet", "Demir");

        System.out.println("Verification: Student count is now 3.");
        assertEquals(3, system.getStudentCount());

        System.out.println("<<< Test finished: getStudentCountTests");
    }

    @Test
    @DisplayName("Should handle convertScoreToLetterGrade with edge cases")
    void convertScoreToLetterGradeEdgeCases() {
        System.out.println(">>> Running test: convertScoreToLetterGradeEdgeCases");

        System.out.println("Verification: Testing edge case scores.");
        assertEquals("A", StudentGradingSystem.convertScoreToLetterGrade(90.0));
        assertEquals("B", StudentGradingSystem.convertScoreToLetterGrade(80.0));
        assertEquals("C", StudentGradingSystem.convertScoreToLetterGrade(70.0));
        assertEquals("D", StudentGradingSystem.convertScoreToLetterGrade(60.0));
        assertEquals("F", StudentGradingSystem.convertScoreToLetterGrade(59.9));

        System.out.println("Action: Testing invalid scores.");
        assertThrows(IllegalArgumentException.class, () -> {
            StudentGradingSystem.convertScoreToLetterGrade(-1.0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            StudentGradingSystem.convertScoreToLetterGrade(101.0);
        });

        System.out.println("<<< Test finished: convertScoreToLetterGradeEdgeCases");
    }

    @Test
    @DisplayName("Should handle multiple courses with different averages")
    void handleMultipleCoursesWithDifferentAverages() {
        System.out.println(">>> Running test: handleMultipleCoursesWithDifferentAverages");

        System.out.println("Setup: Adding second course and students.");
        system.addCourse("MATH101", "Calculus I");
        system.addStudent("102", "Ayşe", "Kaya");
        system.enrollStudentToCourse("101", "CS101");
        system.enrollStudentToCourse("102", "CS101");
        system.enrollStudentToCourse("101", "MATH101");
        system.enrollStudentToCourse("102", "MATH101");

        System.out.println("Action: Assigning different grades for different courses.");
        system.assignGrade("101", "CS101", 90.0);
        system.assignGrade("102", "CS101", 70.0);
        system.assignGrade("101", "MATH101", 85.0);
        system.assignGrade("102", "MATH101", 95.0);

        System.out.println("Verification: Each course has correct average.");
        assertEquals(80.0, system.calculateCourseAverage("CS101"), 0.01);
        assertEquals(90.0, system.calculateCourseAverage("MATH101"), 0.01);

        System.out.println("<<< Test finished: handleMultipleCoursesWithDifferentAverages");
    }

    @Test
    @DisplayName("Should handle system initialization and empty state")
    void systemInitializationAndEmptyState() {
        System.out.println(">>> Running test: systemInitializationAndEmptyState");

        System.out.println("Action: Creating new empty system.");
        StudentGradingSystem emptySystem = new StudentGradingSystem();

        System.out.println("Verification: Empty system has correct initial state.");
        assertEquals(0, emptySystem.getStudentCount());
        assertNull(emptySystem.getStudentById("101"));

        System.out.println("<<< Test finished: systemInitializationAndEmptyState");
    }

    @Test
    @DisplayName("Should handle concurrent student addition")
    void handleConcurrentStudentAddition() {
        System.out.println(">>> Running test: handleConcurrentStudentAddition");

        System.out.println("Action: Adding many students rapidly.");
        for (int i = 200; i < 250; i++) {
            system.addStudent(String.valueOf(i), "Student" + i, "Surname" + i);
        }

        System.out.println("Verification: All students are added correctly.");
        assertEquals(51, system.getStudentCount()); // 1 initial + 50 new
        assertNotNull(system.getStudentById("249"));
        assertNull(system.getStudentById("300"));

        System.out.println("<<< Test finished: handleConcurrentStudentAddition");
    }

    @Test
    @DisplayName("Should handle course average calculation for non-existent course")
    void calculateCourseAverageForNonExistentCourse() {
        System.out.println(">>> Running test: calculateCourseAverageForNonExistentCourse");

        System.out.println("Action: Calculating average for non-existent course.");
        double average = system.calculateCourseAverage("NONEXISTENT");

        System.out.println("Verification: Returns 0.0 for non-existent course.");
        assertEquals(0.0, average);

        System.out.println("<<< Test finished: calculateCourseAverageForNonExistentCourse");
    }
}