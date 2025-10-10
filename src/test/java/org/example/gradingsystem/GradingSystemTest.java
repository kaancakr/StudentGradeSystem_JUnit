package org.example.gradingsystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Grading System Core Functionality Tests")
class GradingSystemTest {

    private StudentGradingSystem system;

    @BeforeEach
    void setUp() {
        System.out.println("--- @BeforeEach: Setting up new test ---");
        system = new StudentGradingSystem();
        system.addStudent("101", "Ahmet", "Yılmaz");
        system.addCourse("CS101", "Introduction to Programming");
        System.out.println("Setup complete. System has 1 student and 1 course.");
    }

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
                () -> assertEquals(3, system.getStudentCount())
        );
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
}