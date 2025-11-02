package org.example.gradingsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Test Suite for Course Model Class
 * 
 * This test class validates all functionality of the Course entity including:
 * - Course creation and initialization
 * - Student enrollment management
 * - Data integrity and validation
 * - Edge cases and error handling
 * - Performance with large datasets
 * 
 * Test Categories:
 * 1. Basic Functionality: Course creation, property access
 * 2. Student Management: Enrollment, duplicate prevention, order maintenance
 * 3. Data Validation: Input validation, null handling
 * 4. Edge Cases: Empty strings, large datasets, boundary conditions
 * 5. Integration: Course-student relationship management
 * 
 * Each test method follows the Arrange-Act-Assert pattern with detailed logging
 * for better debugging and test result interpretation.
 * 
 * @author Eren Kaan Çakır, Berke Beyazbenli
 * @version 1.0
 */
@DisplayName("Tests for Course Model")
class CourseTest {

    /**
     * Test Case: Basic Course Creation
     * 
     * Purpose: Verifies that a Course object can be created with valid parameters
     * and that all initial properties are set correctly.
     * 
     * Test Steps:
     * 1. Create a new Course with course code and name
     * 2. Verify course code is set correctly
     * 3. Verify course name is set correctly
     * 4. Verify initial student enrollment list is empty
     * 
     * Expected Result: Course object created with correct properties and empty enrollment list
     */
    @Test
    @DisplayName("Course should be created correctly")
    void courseCreation() {
        System.out.println(">>> Running test: courseCreation");
        System.out.println("Action: Creating new Course('MAT102', 'Calculus II')...");
        Course course = new Course("MAT102", "Calculus II");

        System.out.println("Verification: Checking course properties and initial empty student list.");
        assertEquals("MAT102", course.getCourseCode());
        assertEquals("Calculus II", course.getCourseName());
        assertTrue(course.getEnrolledStudents().isEmpty());
        System.out.println("<<< Test finished: courseCreation");
    }

    /**
     * Test Case: Student Enrollment
     * 
     * Purpose: Verifies that students can be successfully enrolled in a course
     * and that the enrollment is properly tracked.
     * 
     * Test Steps:
     * 1. Create a new Course and Student
     * 2. Add the student to the course
     * 3. Verify student count increased to 1
     * 4. Verify the student is in the enrollment list
     * 
     * Expected Result: Student successfully enrolled and tracked in course
     */
    @Test
    @DisplayName("Should add student to course enrollment list")
    void addStudentToCourse() {
        System.out.println(">>> Running test: addStudentToCourse");
        Course course = new Course("MAT102", "Calculus II");
        Student student = new Student("201", "Fatma", "Öztürk");

        System.out.println("Action: Adding student '201' to course 'MAT102'.");
        course.addStudent(student);

        int studentCount = course.getEnrolledStudents().size();
        System.out.println("Verification: Enrolled student count is " + studentCount + ".");
        assertEquals(1, studentCount);
        assertTrue(course.getEnrolledStudents().contains(student));
        System.out.println("<<< Test finished: addStudentToCourse");
    }

    /**
     * Test Case: Duplicate Student Prevention
     * 
     * Purpose: Verifies that the same student cannot be enrolled in a course
     * multiple times, ensuring data integrity and preventing duplicate enrollments.
     * 
     * Test Steps:
     * 1. Create a Course and Student
     * 2. Add the student to the course (first time)
     * 3. Attempt to add the same student again (second time)
     * 4. Verify student count remains 1 (no duplication)
     * 
     * Expected Result: Student count remains 1, preventing duplicate enrollments
     */
    @Test
    @DisplayName("Should not add the same student twice to a course")
    void addSameStudentTwice_ShouldNotDuplicate() {
        System.out.println(">>> Running test: addSameStudentTwice_ShouldNotDuplicate");
        Course course = new Course("MAT102", "Calculus II");
        Student student = new Student("201", "Fatma", "Öztürk");

        System.out.println("Action: Adding student '201' for the first time.");
        course.addStudent(student);
        System.out.println("Action: Adding the same student '201' for the second time.");
        course.addStudent(student); // Attempt to add again

        int studentCount = course.getEnrolledStudents().size();
        System.out.println("Verification: Enrolled student count is " + studentCount + ". Expected is 1.");
        assertEquals(1, studentCount);
        System.out.println("<<< Test finished: addSameStudentTwice_ShouldNotDuplicate");
    }

    @Test
    @DisplayName("Should handle multiple students enrollment")
    void addMultipleStudentsToCourse() {
        System.out.println(">>> Running test: addMultipleStudentsToCourse");
        Course course = new Course("CS101", "Programming I");
        Student student1 = new Student("201", "Fatma", "Öztürk");
        Student student2 = new Student("202", "Ali", "Veli");
        Student student3 = new Student("203", "Ayşe", "Kaya");

        System.out.println("Action: Adding three different students to the course.");
        course.addStudent(student1);
        course.addStudent(student2);
        course.addStudent(student3);

        System.out.println("Verification: All three students are enrolled.");
        assertEquals(3, course.getEnrolledStudents().size());
        assertTrue(course.getEnrolledStudents().contains(student1));
        assertTrue(course.getEnrolledStudents().contains(student2));
        assertTrue(course.getEnrolledStudents().contains(student3));
        System.out.println("<<< Test finished: addMultipleStudentsToCourse");
    }

    @Test
    @DisplayName("Should return correct course information")
    void getCourseInformation() {
        System.out.println(">>> Running test: getCourseInformation");
        Course course = new Course("PHY201", "Advanced Physics");

        System.out.println("Verification: Checking all getter methods return correct values.");
        assertEquals("PHY201", course.getCourseCode());
        assertEquals("Advanced Physics", course.getCourseName());
        assertNotNull(course.getEnrolledStudents());
        assertTrue(course.getEnrolledStudents() instanceof java.util.List);
        assertTrue(course.getEnrolledStudents().isEmpty());
        System.out.println("<<< Test finished: getCourseInformation");
    }

    @Test
    @DisplayName("Should handle empty course code and name")
    void createCourseWithEmptyStrings() {
        System.out.println(">>> Running test: createCourseWithEmptyStrings");

        System.out.println("Action: Creating course with empty code and name.");
        Course course = new Course("", "");

        System.out.println("Verification: Course is created successfully with empty strings.");
        assertEquals("", course.getCourseCode());
        assertEquals("", course.getCourseName());
        assertTrue(course.getEnrolledStudents().isEmpty());
        System.out.println("<<< Test finished: createCourseWithEmptyStrings");
    }

    @Test
    @DisplayName("Should maintain student order in enrollment list")
    void maintainStudentOrderInEnrollment() {
        System.out.println(">>> Running test: maintainStudentOrderInEnrollment");
        Course course = new Course("MATH301", "Linear Algebra");
        Student student1 = new Student("301", "Ahmet", "Yılmaz");
        Student student2 = new Student("302", "Zeynep", "Demir");
        Student student3 = new Student("303", "Mehmet", "Kurt");

        System.out.println("Action: Adding students in specific order.");
        course.addStudent(student1);
        course.addStudent(student2);
        course.addStudent(student3);

        System.out.println("Verification: Students are maintained in enrollment order.");
        var enrolledStudents = course.getEnrolledStudents();
        assertEquals(student1, enrolledStudents.get(0));
        assertEquals(student2, enrolledStudents.get(1));
        assertEquals(student3, enrolledStudents.get(2));
        System.out.println("<<< Test finished: maintainStudentOrderInEnrollment");
    }

    @Test
    @DisplayName("Should handle null student addition gracefully")
    void addNullStudentToCourse() {
        System.out.println(">>> Running test: addNullStudentToCourse");
        Course course = new Course("CHEM101", "General Chemistry");

        System.out.println("Action: Attempting to add null student.");
        // Course class doesn't validate null, so it will add null to the list
        course.addStudent(null);

        System.out.println("Verification: Null student was added to the list.");
        assertEquals(1, course.getEnrolledStudents().size());
        assertTrue(course.getEnrolledStudents().contains(null));
        System.out.println("<<< Test finished: addNullStudentToCourse");
    }

    @Test
    @DisplayName("Should handle large number of students")
    void addLargeNumberOfStudents() {
        System.out.println(">>> Running test: addLargeNumberOfStudents");
        Course course = new Course("CS499", "Capstone Project");

        System.out.println("Action: Adding 100 students to test scalability.");
        for (int i = 1; i <= 100; i++) {
            Student student = new Student("S" + String.format("%03d", i), "Student" + i, "Surname" + i);
            course.addStudent(student);
        }

        System.out.println("Verification: All 100 students are enrolled.");
        assertEquals(100, course.getEnrolledStudents().size());
        System.out.println("<<< Test finished: addLargeNumberOfStudents");
    }
}
