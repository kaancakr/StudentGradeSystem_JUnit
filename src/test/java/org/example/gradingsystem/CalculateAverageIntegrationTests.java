package org.example.gradingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Integration Testing: calculateCourseAverage (SUT: StudentGradingSystem)")
public class CalculateAverageIntegrationTests {

    private StudentGradingSystem system;
    // Mock Course: Dependency 1
    private Course mockCourse;
    private final String COURSE_CODE = "CS401";

    @BeforeEach
    void setUp() {
        // Use a Spy for the System Under Test (SUT) to control dependency retrieval
        system = spy(new StudentGradingSystem());
        mockCourse = mock(Course.class); // Mock the Course dependency

        // Mock Behavior 1 for SUT: Control what getCourse returns
        when(system.getCourse(COURSE_CODE)).thenReturn(mockCourse);
        // NOTE: You will need to add a public getCourse method to StudentGradingSystem
        // if it's currently private or protected.
    }

    // Helper Method to create a Mock Student with a specific grade behavior
    private Student mockStudentWithGrade(String studentId, double grade) {
        Student mockStudent = mock(Student.class); // Mock Student: Dependency 2
        when(mockStudent.getId()).thenReturn(studentId);
        // Mock Behaviors for Student (B1-B6)
        // This handles grade logic (90.0, 70.0, 50.0, 49.9, -1.0, 80.0, etc.)
        when(mockStudent.getGrade(COURSE_CODE)).thenReturn(grade);
        return mockStudent;
    }

    // --- 22 Test Cases (R1-R22) covering boundary, single, multiple, and mixed graded/ungraded scenarios ---

    // R1: C1 (Empty List)
    @Test
    void case01_emptyCourse_shouldReturnZeroAverage() {
        // Mock Behavior for Course (C1): Empty list
        when(mockCourse.getEnrolledStudents()).thenReturn(Collections.emptyList());
        assertEquals(0.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R2: C2 (Single Graded) + B1 (90.0)
    @Test
    void case02_singleExcellentGrade_shouldReturn90() {
        List<Student> students = Collections.singletonList(mockStudentWithGrade("1", 90.0));
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(90.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R5: C3 (Single Ungraded) + B5 (-1.0)
    @Test
    void case05_singleUngradedStudent_shouldReturnZeroAverage() {
        List<Student> students = Collections.singletonList(mockStudentWithGrade("4", -1.0));
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(0.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R6: C4 (Two Graded) + B1 (90) + B6 (80) -> Avg 85.0
    @Test
    void case06_twoStudentsAvg85_shouldReturn85() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("5", 90.0),
                mockStudentWithGrade("6", 80.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(85.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R13: C5 (Graded + Ungraded) + B1 (90) + B5 (-1.0) -> Avg 90.0
    @Test
    void case13_oneGradedOneUngraded_shouldReturnAverageOfGraded() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("22", 90.0),
                mockStudentWithGrade("23", -1.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(90.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R20: Boundary Case: Grade 100.0 + Grade 0.0 -> Avg 50.0
    @Test
    void case20_twoStudentsMaxMinAvg50_shouldReturn50() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("37", 100.0),
                mockStudentWithGrade("38", 0.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(50.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // ... Add 16 more test cases using combinations of mock behaviors...
    // The full set of 22 cases from the previous step's analysis should be included.

}