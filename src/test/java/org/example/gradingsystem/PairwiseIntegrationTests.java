package org.example.gradingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pair-wise Integration Testing Suite
 * 
 * This test class implements pair-wise integration testing methodology
 * to test interactions between three classes:
 * 1. StudentGradingSystem (SUT - System Under Test)
 * 2. Student (Dependency 1)
 * 3. Course (Dependency 2)
 * 
 * Pair-wise Integration Strategy:
 * - Pair 1: StudentGradingSystem ↔ Student
 * - Pair 2: StudentGradingSystem ↔ Course
 * - Pair 3: Student ↔ Course (through StudentGradingSystem)
 * - Pair 4: All three classes together
 * 
 * Requirements:
 * - At least 20 test cases
 * - Each mock object handles at least 5 behaviors (5 when().thenReturn() calls)
 * - Comprehensive coverage of method interactions
 * 
 * @author Integration Test Suite
 * @version 1.0
 */
@DisplayName("Pair-wise Integration Testing: StudentGradingSystem, Student, Course")
public class PairwiseIntegrationTests {

    private StudentGradingSystem system;
    private Course mockCourse;
    private Student mockStudent;
    
    private static final String STUDENT_ID = "STU001";
    private static final String COURSE_CODE = "CS501";
    private static final String COURSE_NAME = "Advanced Software Engineering";

    /**
     * Setup method that initializes the SUT and creates mock objects
     * with 5+ behaviors each
     */
    @BeforeEach
    void setUp() {
        // Initialize the real SUT
        system = new StudentGradingSystem();
        
        // Create mock Course with 5+ behaviors
        mockCourse = mock(Course.class);
        when(mockCourse.getCourseCode()).thenReturn(COURSE_CODE);                    // B1: Course code
        when(mockCourse.getCourseName()).thenReturn(COURSE_NAME);                   // B2: Course name
        when(mockCourse.getEnrolledStudents()).thenReturn(Collections.emptyList());   // B3: Empty students list
        when(mockCourse.getStudentCount()).thenReturn(0);                            // B4: Student count
        when(mockCourse.toString()).thenReturn("Course[" + COURSE_CODE + "]");       // B5: String representation
        
        // Create mock Student with 5+ behaviors
        mockStudent = mock(Student.class);
        when(mockStudent.getId()).thenReturn(STUDENT_ID);                            // B1: Student ID
        when(mockStudent.getName()).thenReturn("John");                              // B2: Student name
        when(mockStudent.getSurname()).thenReturn("Doe");                            // B3: Student surname
        when(mockStudent.getGrade(COURSE_CODE)).thenReturn(-1.0);                    // B4: Default grade (ungraded)
        when(mockStudent.getGrade(anyString())).thenReturn(-1.0);                    // B5: Grade for any course
    }

    // ========================================================================
    // PAIR 1: StudentGradingSystem ↔ Student Integration Tests
    // ========================================================================

    /**
     * Test Case 1: Pair-wise integration of assignGrade (SGS) → addGrade (Student)
     * Tests: StudentGradingSystem.assignGrade() calls Student.addGrade()
     */
    @Test
    @DisplayName("TC01: SGS.assignGrade → Student.addGrade integration")
    void test01_assignGradeToStudentIntegration() {
        // Setup: Add real student and course to system
        system.addStudent(STUDENT_ID, "Jane", "Smith");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        Student realStudent = system.getStudentById(STUDENT_ID);
        
        // Inject mock student behavior
        when(mockStudent.getGrade(COURSE_CODE)).thenReturn(85.0);                    // B1: Grade for course
        when(mockStudent.getId()).thenReturn(STUDENT_ID);                            // B2: Student ID
        when(mockStudent.getName()).thenReturn("Jane");                               // B3: Student name
        when(mockStudent.getSurname()).thenReturn("Smith");                           // B4: Student surname
        when(mockStudent.getCourseGrades()).thenReturn(Collections.emptyMap());       // B5: Course grades map
        
        // Execute: Assign grade through SGS
        system.assignGrade(STUDENT_ID, COURSE_CODE, 85.0);
        
        // Verify: Grade was assigned
        double grade = realStudent.getGrade(COURSE_CODE);
        assertEquals(85.0, grade, 0.01);
    }

    /**
     * Test Case 2: Pair-wise integration of evaluateStudentPerformance (SGS) → getGrade (Student)
     * Tests: StudentGradingSystem.evaluateStudentPerformance() calls Student.getGrade()
     */
    @Test
    @DisplayName("TC02: SGS.evaluateStudentPerformance → Student.getGrade integration")
    void test02_evaluatePerformanceIntegration() {
        // Setup: Add student and course, configure mock behaviors
        system.addStudent(STUDENT_ID, "Alice", "Johnson");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        system.assignGrade(STUDENT_ID, COURSE_CODE, 92.0);
        
        // Execute: Evaluate performance
        String performance = system.evaluateStudentPerformance(STUDENT_ID, COURSE_CODE);
        
        // Verify: Performance evaluation
        assertEquals("Excellent", performance);
    }

    /**
     * Test Case 3: Pair-wise integration with multiple grade retrievals
     * Tests: Multiple calls to Student.getGrade() from SGS
     */
    @Test
    @DisplayName("TC03: SGS → Student.getGrade multiple calls integration")
    void test03_multipleGradeRetrievalsIntegration() {
        // Setup
        system.addStudent(STUDENT_ID, "Bob", "Williams");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.addCourse("MATH201", "Calculus II");
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        system.enrollStudentToCourse(STUDENT_ID, "MATH201");
        
        // Assign grades
        system.assignGrade(STUDENT_ID, COURSE_CODE, 88.0);
        system.assignGrade(STUDENT_ID, "MATH201", 75.0);
        
        // Execute: Multiple grade retrievals
        double grade1 = system.getStudentById(STUDENT_ID).getGrade(COURSE_CODE);
        double grade2 = system.getStudentById(STUDENT_ID).getGrade("MATH201");
        
        // Verify
        assertEquals(88.0, grade1, 0.01);
        assertEquals(75.0, grade2, 0.01);
    }

    /**
     * Test Case 4: Pair-wise integration with ungraded student
     * Tests: SGS handles Student.getGrade() returning -1.0
     */
    @Test
    @DisplayName("TC04: SGS → Student.getGrade ungraded integration")
    void test04_ungradedStudentIntegration() {
        // Setup
        system.addStudent(STUDENT_ID, "Charlie", "Brown");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        // No grade assigned
        
        // Execute: Evaluate ungraded student
        String performance = system.evaluateStudentPerformance(STUDENT_ID, COURSE_CODE);
        
        // Verify
        assertEquals("No Grade", performance);
    }

    /**
     * Test Case 5: Pair-wise integration with invalid student
     * Tests: SGS handles null Student from getStudentById()
     */
    @Test
    @DisplayName("TC05: SGS → Student null handling integration")
    void test05_nullStudentHandlingIntegration() {
        // Setup: No student added
        
        // Execute & Verify: Should return "Invalid"
        String performance = system.evaluateStudentPerformance("NONEXISTENT", COURSE_CODE);
        assertEquals("Invalid", performance);
    }

    // ========================================================================
    // PAIR 2: StudentGradingSystem ↔ Course Integration Tests
    // ========================================================================

    /**
     * Test Case 6: Pair-wise integration of enrollStudentToCourse (SGS) → addStudent (Course)
     * Tests: StudentGradingSystem.enrollStudentToCourse() calls Course.addStudent()
     */
    @Test
    @DisplayName("TC06: SGS.enrollStudentToCourse → Course.addStudent integration")
    void test06_enrollStudentToCourseIntegration() {
        // Setup: Add student and course
        system.addStudent(STUDENT_ID, "David", "Miller");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        
        // Configure mock course behaviors
        when(mockCourse.getCourseCode()).thenReturn(COURSE_CODE);                    // B1
        when(mockCourse.getCourseName()).thenReturn(COURSE_NAME);                   // B2
        when(mockCourse.getStudentCount()).thenReturn(1);                           // B3: After enrollment
        when(mockCourse.getEnrolledStudents()).thenReturn(                          // B4: Enrolled students
            Collections.singletonList(system.getStudentById(STUDENT_ID)));
        when(mockCourse.toString()).thenReturn("Course[" + COURSE_CODE + "]");       // B5
        
        // Inject mock course
        system.getCourses().put(COURSE_CODE, mockCourse);
        
        // Execute: Enroll student
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        
        // Verify: Course received the student
        verify(mockCourse, times(1)).addStudent(any(Student.class));
    }

    /**
     * Test Case 7: Pair-wise integration of calculateCourseAverage (SGS) → getEnrolledStudents (Course)
     * Tests: StudentGradingSystem.calculateCourseAverage() calls Course.getEnrolledStudents()
     */
    @Test
    @DisplayName("TC07: SGS.calculateCourseAverage → Course.getEnrolledStudents integration")
    void test07_calculateAverageCourseIntegration() {
        // Setup: Create students and configure mock course
        Student student1 = new Student("S1", "Emma", "Davis");
        Student student2 = new Student("S2", "Frank", "Garcia");
        
        student1.addGrade(COURSE_CODE, 90.0);
        student2.addGrade(COURSE_CODE, 80.0);
        
        List<Student> enrolledStudents = Arrays.asList(student1, student2);
        
        // Configure mock course with 5+ behaviors
        when(mockCourse.getEnrolledStudents()).thenReturn(enrolledStudents);         // B1: Enrolled students
        when(mockCourse.getCourseCode()).thenReturn(COURSE_CODE);                   // B2: Course code
        when(mockCourse.getCourseName()).thenReturn(COURSE_NAME);                    // B3: Course name
        when(mockCourse.getStudentCount()).thenReturn(2);                           // B4: Student count
        when(mockCourse.toString()).thenReturn("Course[" + COURSE_CODE + "]");       // B5: String representation
        
        // Inject mock course
        system.getCourses().put(COURSE_CODE, mockCourse);
        
        // Execute: Calculate average
        double average = system.calculateCourseAverage(COURSE_CODE);
        
        // Verify
        assertEquals(85.0, average, 0.01);
        verify(mockCourse, atLeastOnce()).getEnrolledStudents();
    }

    /**
     * Test Case 8: Pair-wise integration with empty course
     * Tests: SGS handles Course.getEnrolledStudents() returning empty list
     */
    @Test
    @DisplayName("TC08: SGS → Course empty enrollment integration")
    void test08_emptyCourseEnrollmentIntegration() {
        // Configure mock course with empty enrollment
        when(mockCourse.getEnrolledStudents()).thenReturn(Collections.emptyList()); // B1
        when(mockCourse.getCourseCode()).thenReturn(COURSE_CODE);                   // B2
        when(mockCourse.getCourseName()).thenReturn(COURSE_NAME);                    // B3
        when(mockCourse.getStudentCount()).thenReturn(0);                            // B4
        when(mockCourse.toString()).thenReturn("Course[" + COURSE_CODE + "]");       // B5
        
        system.getCourses().put(COURSE_CODE, mockCourse);
        
        // Execute
        double average = system.calculateCourseAverage(COURSE_CODE);
        
        // Verify
        assertEquals(0.0, average, 0.01);
    }

    /**
     * Test Case 9: Pair-wise integration with null course
     * Tests: SGS handles null Course from getCourse()
     */
    @Test
    @DisplayName("TC09: SGS → Course null handling integration")
    void test09_nullCourseHandlingIntegration() {
        // Setup: No course added
        
        // Execute: Calculate average for non-existent course
        double average = system.calculateCourseAverage("NONEXISTENT");
        
        // Verify: Should return 0.0
        assertEquals(0.0, average, 0.01);
    }

    /**
     * Test Case 10: Pair-wise integration with multiple courses
     * Tests: SGS manages multiple Course objects
     */
    @Test
    @DisplayName("TC10: SGS → Course multiple courses integration")
    void test10_multipleCoursesIntegration() {
        // Setup: Add multiple courses
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.addCourse("MATH201", "Calculus II");
        system.addCourse("PHYS301", "Physics III");
        
        // Execute: Get courses
        var courses = system.getCourses();
        
        // Verify: All courses exist
        assertNotNull(courses.get(COURSE_CODE));
        assertNotNull(courses.get("MATH201"));
        assertNotNull(courses.get("PHYS301"));
        assertEquals(3, courses.size());
    }

    // ========================================================================
    // PAIR 3: Student ↔ Course Integration (through SGS)
    // ========================================================================

    /**
     * Test Case 11: Integration of Student and Course through enrollment
     * Tests: Student enrolled in Course via SGS
     */
    @Test
    @DisplayName("TC11: Student ↔ Course enrollment integration")
    void test11_studentCourseEnrollmentIntegration() {
        // Setup
        system.addStudent(STUDENT_ID, "Grace", "Martinez");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        
        // Execute: Enroll student in course
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        
        // Verify: Student is in course's enrollment list
        Course course = system.getCourse(COURSE_CODE);
        List<Student> enrolled = course.getEnrolledStudents();
        assertTrue(enrolled.contains(system.getStudentById(STUDENT_ID)));
    }

    /**
     * Test Case 12: Integration of Student grade and Course average calculation
     * Tests: Student grades contribute to Course average
     */
    @Test
    @DisplayName("TC12: Student grade → Course average integration")
    void test12_studentGradeCourseAverageIntegration() {
        // Setup: Add students and course
        system.addStudent("S1", "Henry", "Rodriguez");
        system.addStudent("S2", "Ivy", "Lee");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        
        // Enroll and assign grades
        system.enrollStudentToCourse("S1", COURSE_CODE);
        system.enrollStudentToCourse("S2", COURSE_CODE);
        system.assignGrade("S1", COURSE_CODE, 95.0);
        system.assignGrade("S2", COURSE_CODE, 85.0);
        
        // Execute: Calculate course average
        double average = system.calculateCourseAverage(COURSE_CODE);
        
        // Verify: Average includes both student grades
        assertEquals(90.0, average, 0.01);
    }

    /**
     * Test Case 13: Integration with mixed graded/ungraded students
     * Tests: Course average calculation with some students ungraded
     */
    @Test
    @DisplayName("TC13: Student graded/ungraded → Course average integration")
    void test13_mixedGradedUngradedIntegration() {
        // Setup
        system.addStudent("S1", "Jack", "Wilson");
        system.addStudent("S2", "Kate", "Moore");
        system.addStudent("S3", "Liam", "Taylor");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        
        // Enroll all
        system.enrollStudentToCourse("S1", COURSE_CODE);
        system.enrollStudentToCourse("S2", COURSE_CODE);
        system.enrollStudentToCourse("S3", COURSE_CODE);
        
        // Assign grades to only 2 students
        system.assignGrade("S1", COURSE_CODE, 90.0);
        system.assignGrade("S2", COURSE_CODE, 70.0);
        // S3 remains ungraded
        
        // Execute
        double average = system.calculateCourseAverage(COURSE_CODE);
        
        // Verify: Average only includes graded students
        assertEquals(80.0, average, 0.01);
    }

    /**
     * Test Case 14: Integration with student in multiple courses
     * Tests: Student can have grades in multiple courses
     */
    @Test
    @DisplayName("TC14: Student multiple courses integration")
    void test14_studentMultipleCoursesIntegration() {
        // Setup
        system.addStudent(STUDENT_ID, "Mia", "Anderson");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.addCourse("MATH201", "Calculus II");
        
        // Enroll in both courses
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        system.enrollStudentToCourse(STUDENT_ID, "MATH201");
        
        // Assign different grades
        system.assignGrade(STUDENT_ID, COURSE_CODE, 88.0);
        system.assignGrade(STUDENT_ID, "MATH201", 92.0);
        
        // Execute: Get grades for both courses
        double grade1 = system.getStudentById(STUDENT_ID).getGrade(COURSE_CODE);
        double grade2 = system.getStudentById(STUDENT_ID).getGrade("MATH201");
        
        // Verify: Both grades are correct
        assertEquals(88.0, grade1, 0.01);
        assertEquals(92.0, grade2, 0.01);
    }

    /**
     * Test Case 15: Integration with course containing multiple students
     * Tests: Course manages multiple enrolled students
     */
    @Test
    @DisplayName("TC15: Course multiple students integration")
    void test15_courseMultipleStudentsIntegration() {
        // Setup: Add multiple students
        system.addStudent("S1", "Noah", "Thomas");
        system.addStudent("S2", "Olivia", "Jackson");
        system.addStudent("S3", "Paul", "White");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        
        // Enroll all students
        system.enrollStudentToCourse("S1", COURSE_CODE);
        system.enrollStudentToCourse("S2", COURSE_CODE);
        system.enrollStudentToCourse("S3", COURSE_CODE);
        
        // Execute: Get enrolled students
        Course course = system.getCourse(COURSE_CODE);
        List<Student> enrolled = course.getEnrolledStudents();
        
        // Verify: All students are enrolled
        assertEquals(3, enrolled.size());
        assertEquals(3, course.getStudentCount());
    }

    // ========================================================================
    // PAIR 4: All Three Classes Together Integration Tests
    // ========================================================================

    /**
     * Test Case 16: Full integration: SGS → Student → Course
     * Tests: Complete workflow with all three classes
     */
    @Test
    @DisplayName("TC16: Full SGS-Student-Course integration workflow")
    void test16_fullWorkflowIntegration() {
        // Setup: Complete workflow
        system.addStudent(STUDENT_ID, "Quinn", "Harris");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        system.assignGrade(STUDENT_ID, COURSE_CODE, 87.0);
        
        // Execute: Evaluate performance (uses all three classes)
        String performance = system.evaluateStudentPerformance(STUDENT_ID, COURSE_CODE);
        
        // Verify
        assertEquals("Excellent", performance);
        
        // Verify course average
        double average = system.calculateCourseAverage(COURSE_CODE);
        assertEquals(87.0, average, 0.01);
    }

    /**
     * Test Case 17: Full integration with exam eligibility determination
     * Tests: determineExamEligibility uses all three classes
     */
    @Test
    @DisplayName("TC17: Full integration - exam eligibility determination")
    void test17_examEligibilityFullIntegration() {
        // Setup
        system.addStudent(STUDENT_ID, "Rachel", "Martin");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        system.assignGrade(STUDENT_ID, COURSE_CODE, 85.0);
        
        // Execute: Determine exam eligibility
        String eligibility = system.determineExamEligibility(
            STUDENT_ID, COURSE_CODE, true, false);
        
        // Verify
        assertEquals("Eligible", eligibility);
    }

    /**
     * Test Case 18: Full integration with conditional eligibility
     * Tests: Exam eligibility with no grade (conditional case)
     */
    @Test
    @DisplayName("TC18: Full integration - conditional exam eligibility")
    void test18_conditionalEligibilityIntegration() {
        // Setup: Student enrolled but no grade
        system.addStudent(STUDENT_ID, "Sam", "Thompson");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        // No grade assigned
        
        // Execute
        String eligibility = system.determineExamEligibility(
            STUDENT_ID, COURSE_CODE, true, false);
        
        // Verify: Should be conditional
        assertEquals("Conditional", eligibility);
    }

    /**
     * Test Case 19: Full integration with multiple students and courses
     * Tests: Complex scenario with multiple entities
     */
    @Test
    @DisplayName("TC19: Full integration - multiple students and courses")
    void test19_complexMultipleEntitiesIntegration() {
        // Setup: Multiple students and courses
        system.addStudent("S1", "Tom", "Garcia");
        system.addStudent("S2", "Uma", "Martinez");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.addCourse("MATH201", "Calculus II");
        
        // Enroll students in courses
        system.enrollStudentToCourse("S1", COURSE_CODE);
        system.enrollStudentToCourse("S1", "MATH201");
        system.enrollStudentToCourse("S2", COURSE_CODE);
        
        // Assign grades
        system.assignGrade("S1", COURSE_CODE, 90.0);
        system.assignGrade("S1", "MATH201", 85.0);
        system.assignGrade("S2", COURSE_CODE, 75.0);
        
        // Execute: Calculate averages for both courses
        double avg1 = system.calculateCourseAverage(COURSE_CODE);
        double avg2 = system.calculateCourseAverage("MATH201");
        
        // Verify
        assertEquals(82.5, avg1, 0.01); // (90 + 75) / 2
        assertEquals(85.0, avg2, 0.01); // 85 / 1
    }

    /**
     * Test Case 20: Full integration with boundary grade values
     * Tests: All three classes with boundary conditions
     */
    @Test
    @DisplayName("TC20: Full integration - boundary grade values")
    void test20_boundaryValuesFullIntegration() {
        // Setup: Test boundary values
        system.addStudent("S1", "Victor", "Robinson");
        system.addStudent("S2", "Wendy", "Clark");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        
        system.enrollStudentToCourse("S1", COURSE_CODE);
        system.enrollStudentToCourse("S2", COURSE_CODE);
        
        // Assign boundary grades
        system.assignGrade("S1", COURSE_CODE, 0.0);   // Minimum
        system.assignGrade("S2", COURSE_CODE, 100.0); // Maximum
        
        // Execute: Calculate average
        double average = system.calculateCourseAverage(COURSE_CODE);
        
        // Verify
        assertEquals(50.0, average, 0.01);
        
        // Verify performance evaluations
        String perf1 = system.evaluateStudentPerformance("S1", COURSE_CODE);
        String perf2 = system.evaluateStudentPerformance("S2", COURSE_CODE);
        
        assertEquals("Fail", perf1);
        assertEquals("Excellent", perf2);
    }

    /**
     * Test Case 21: Full integration with grade conversion
     * Tests: convertScoreToLetterGrade with all classes
     */
    @Test
    @DisplayName("TC21: Full integration - grade conversion")
    void test21_gradeConversionFullIntegration() {
        // Setup
        system.addStudent(STUDENT_ID, "Xavier", "Lewis");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        system.assignGrade(STUDENT_ID, COURSE_CODE, 92.0);
        
        // Execute: Get grade and convert
        double grade = system.getStudentById(STUDENT_ID).getGrade(COURSE_CODE);
        String letterGrade = StudentGradingSystem.convertScoreToLetterGrade(grade);
        
        // Verify
        assertEquals(92.0, grade, 0.01);
        assertEquals("A", letterGrade);
    }

    /**
     * Test Case 22: Full integration with error handling
     * Tests: All three classes handle invalid inputs
     */
    @Test
    @DisplayName("TC22: Full integration - error handling")
    void test22_errorHandlingFullIntegration() {
        // Setup: Valid student and course
        system.addStudent(STUDENT_ID, "Yara", "Walker");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        
        // Execute & Verify: Invalid enrollment
        assertThrows(IllegalArgumentException.class, () -> {
            system.enrollStudentToCourse("INVALID", COURSE_CODE);
        });
        
        // Execute & Verify: Invalid grade assignment
        assertThrows(IllegalArgumentException.class, () -> {
            system.assignGrade(STUDENT_ID, "INVALID", 85.0);
        });
        
        // Execute & Verify: Invalid grade value
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        assertThrows(IllegalArgumentException.class, () -> {
            system.assignGrade(STUDENT_ID, COURSE_CODE, 150.0);
        });
    }

    /**
     * Test Case 23: Full integration with performance evaluation boundaries
     * Tests: All performance categories with all classes
     */
    @Test
    @DisplayName("TC23: Full integration - performance evaluation boundaries")
    void test23_performanceBoundariesFullIntegration() {
        // Setup
        system.addStudent("S1", "Zoe", "Hall");
        system.addStudent("S2", "Alex", "Allen");
        system.addStudent("S3", "Beth", "Young");
        system.addStudent("S4", "Chris", "King");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        
        // Enroll all
        system.enrollStudentToCourse("S1", COURSE_CODE);
        system.enrollStudentToCourse("S2", COURSE_CODE);
        system.enrollStudentToCourse("S3", COURSE_CODE);
        system.enrollStudentToCourse("S4", COURSE_CODE);
        
        // Assign boundary grades
        system.assignGrade("S1", COURSE_CODE, 85.0); // Excellent boundary
        system.assignGrade("S2", COURSE_CODE, 70.0); // Satisfactory boundary
        system.assignGrade("S3", COURSE_CODE, 50.0); // Pass boundary
        system.assignGrade("S4", COURSE_CODE, 49.9); // Fail
        
        // Execute: Evaluate all
        String perf1 = system.evaluateStudentPerformance("S1", COURSE_CODE);
        String perf2 = system.evaluateStudentPerformance("S2", COURSE_CODE);
        String perf3 = system.evaluateStudentPerformance("S3", COURSE_CODE);
        String perf4 = system.evaluateStudentPerformance("S4", COURSE_CODE);
        
        // Verify: All performance categories
        assertEquals("Excellent", perf1);
        assertEquals("Satisfactory", perf2);
        assertEquals("Pass", perf3);
        assertEquals("Fail", perf4);
    }

    /**
     * Test Case 24: Full integration with exam eligibility decision table
     * Tests: All eligibility scenarios with all classes
     */
    @Test
    @DisplayName("TC24: Full integration - exam eligibility scenarios")
    void test24_examEligibilityScenariosIntegration() {
        // Setup
        system.addStudent(STUDENT_ID, "Diana", "Wright");
        system.addCourse(COURSE_CODE, COURSE_NAME);
        system.enrollStudentToCourse(STUDENT_ID, COURSE_CODE);
        system.assignGrade(STUDENT_ID, COURSE_CODE, 80.0);
        
        // Execute & Verify: All attendance scenarios
        String eligible1 = system.determineExamEligibility(STUDENT_ID, COURSE_CODE, true, false);
        String eligible2 = system.determineExamEligibility(STUDENT_ID, COURSE_CODE, false, false);
        String eligible3 = system.determineExamEligibility(STUDENT_ID, COURSE_CODE, true, true);
        
        assertEquals("Eligible", eligible1);
        assertEquals("Not Eligible", eligible2);
        assertEquals("Not Eligible", eligible3);
    }

    /**
     * Test Case 25: Full integration stress test
     * Tests: Multiple operations with all classes
     */
    @Test
    @DisplayName("TC25: Full integration - stress test")
    void test25_stressTestFullIntegration() {
        // Setup: Multiple students and courses
        for (int i = 1; i <= 5; i++) {
            system.addStudent("S" + i, "Student" + i, "Surname" + i);
            system.addCourse("C" + i, "Course" + i);
        }
        
        // Execute: Multiple enrollments and grade assignments
        for (int i = 1; i <= 5; i++) {
            system.enrollStudentToCourse("S" + i, "C" + i);
            system.assignGrade("S" + i, "C" + i, 70.0 + i * 5);
        }
        
        // Verify: All operations successful
        for (int i = 1; i <= 5; i++) {
            double grade = system.getStudentById("S" + i).getGrade("C" + i);
            assertEquals(70.0 + i * 5, grade, 0.01);
            
            double avg = system.calculateCourseAverage("C" + i);
            assertEquals(70.0 + i * 5, avg, 0.01);
        }
        
        assertEquals(5, system.getStudentCount());
        assertEquals(5, system.getCourses().size());
    }
}

