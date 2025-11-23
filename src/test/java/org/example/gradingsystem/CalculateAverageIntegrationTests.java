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

    /**
     * setUp() to include 5 distinct mock behaviors for Course.
     */
    @BeforeEach
    void setUp() {
        // 1. Use the real SUT object
        system = new StudentGradingSystem();

        // 2. Mock the Course dependency
        mockCourse = mock(Course.class);

        // --- Mock Behaviors for Course (Dependency 1) - 5 distinct behaviors ---
        when(mockCourse.getEnrolledStudents()).thenReturn(Collections.emptyList());   // B1
        when(mockCourse.getCourseCode()).thenReturn(COURSE_CODE);                    // B2
        when(mockCourse.getCourseName()).thenReturn("Software Quality Assurance");   // B3
        when(mockCourse.toString()).thenReturn("Course[CS401]");                     // B4

        when(mockCourse.getStudentCount()).thenReturn(0);

        // 3. Inject the mock Course directly into the real system's map
        system.getCourses().put(COURSE_CODE, mockCourse);
    }

    /**
     * Helper Method to create a Mock Student with 5 distinct mock behaviors.
     */
    private Student mockStudentWithGrade(String studentId, double grade) {
        Student mockStudent = mock(Student.class); // Mock Student: Dependency 2

        // --- Mock Behaviors for Student (Dependency 2) - 5 distinct behaviors ---
        when(mockStudent.getId()).thenReturn(studentId); // B1: Student ID getter
        when(mockStudent.getName()).thenReturn("Name" + studentId); // B2: Student Name getter
        when(mockStudent.getSurname()).thenReturn("Surname" + studentId); // B3: Student Surname getter
        when(mockStudent.getGrade(COURSE_CODE)).thenReturn(grade); // B4: Grade for SUT course (primary behavior)
        // B5: Grade for a different, hypothetical course code
        when(mockStudent.getGrade(eq("OTHER_COURSE"))).thenReturn(-1.0);

        return mockStudent;
    }

    // --- 22 Test Cases (R1-R22) covering boundary, single, multiple, and mixed graded/ungraded scenarios ---

    // R1: C1 (Empty List)
    @Test
    void case01_emptyCourse_shouldReturnZeroAverage() {
        // Mock Behavior for Course: Empty list
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

    // R3: Single Ungraded (R1/C1 from last turn)
    @Test
    void case03_singleUngradedStudent2_shouldReturnZeroAverage() {
        List<Student> students = Collections.singletonList(mockStudentWithGrade("2", -1.0));
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(0.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R4: Single Boundary Case: Grade just below 'Pass' (49.9)
    @Test
    void case04_singleFailBoundaryGrade_shouldReturn49_9() {
        List<Student> students = Collections.singletonList(mockStudentWithGrade("3", 49.9));
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(49.9, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R5: Single Ungraded (Original from first uploaded file)
    @Test
    void case05_singleUngradedStudent_shouldReturnZeroAverage() {
        List<Student> students = Collections.singletonList(mockStudentWithGrade("4", -1.0));
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(0.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R6: Two Graded Students (90 + 80) -> Avg 85.0 (Original from first uploaded file)
    @Test
    void case06_twoStudentsAvg85_shouldReturn85() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("5", 90.0),
                mockStudentWithGrade("6", 80.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(85.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R7: Two Students with Lower Boundary of 'C' Grade (70.0)
    @Test
    void case07_twoStudentsAvg70_shouldReturn70() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("7", 70.0),
                mockStudentWithGrade("8", 70.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(70.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R8: Two Students Boundary: Satisfactory (84.9) and Excellent (85.0) -> Avg 84.95
    @Test
    void case08_twoStudentsBoundaryAvg84_95_shouldReturn84_95() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("9", 84.9),
                mockStudentWithGrade("10", 85.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(84.95, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R9: Two Students Minimum Grades (0.0) -> Avg 0.0
    @Test
    void case09_twoStudentsMinGrades_shouldReturnZero() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("11", 0.0),
                mockStudentWithGrade("12", 0.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(0.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R10: Three Students Maximum Grades (100.0) -> Avg 100.0
    @Test
    void case10_threeStudentsMaxGrades_shouldReturn100() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("13", 100.0),
                mockStudentWithGrade("14", 100.0),
                mockStudentWithGrade("15", 100.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(100.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R11: Three Students with mixed Pass/Excellent grades (50, 50, 80) -> Avg 60.0
    @Test
    void case11_threeStudentsAvg60_shouldReturn60() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("16", 50.0),
                mockStudentWithGrade("17", 50.0),
                mockStudentWithGrade("18", 80.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(60.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R12: Three Students with D/C/B grades (60, 70, 80) -> Avg 70.0
    @Test
    void case12_threeStudentsAvg70_shouldReturn70() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("19", 60.0),
                mockStudentWithGrade("20", 70.0),
                mockStudentWithGrade("21", 80.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(70.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R13: C5 (Graded + Ungraded) + B1 (90) + B5 (-1.0) -> Avg 90.0 (Original from first uploaded file)
    @Test
    void case13_oneGradedOneUngraded_shouldReturnAverageOfGraded() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("22", 90.0),
                mockStudentWithGrade("23", -1.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(90.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R14: Three Students (2 Graded, 1 Ungraded) -> Avg 80.0 ( (90+70)/2 )
    @Test
    void case14_twoGradedOneUngraded_shouldReturnAverageOfGraded() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("24", 90.0),
                mockStudentWithGrade("25", 70.0),
                mockStudentWithGrade("26", -1.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(80.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R15: Three Students (1 Graded, 2 Ungraded) -> Avg 50.0
    @Test
    void case15_oneGradedTwoUngraded_shouldReturnAverageOfGraded() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("27", 50.0),
                mockStudentWithGrade("28", -1.0),
                mockStudentWithGrade("29", -1.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(50.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R16: Four Students (2 Graded, 2 Ungraded) -> Avg 75.0 ( (100+50)/2 )
    @Test
    void case16_twoGradedTwoUngraded_shouldReturn75() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("30", 100.0),
                mockStudentWithGrade("31", -1.0),
                mockStudentWithGrade("32", 50.0),
                mockStudentWithGrade("33", -1.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(75.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R17: Five Students, all with Pass grade (50.0) -> Avg 50.0
    @Test
    void case17_fiveStudentsAllPass_shouldReturn50() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("34", 50.0),
                mockStudentWithGrade("35", 50.0),
                mockStudentWithGrade("36", 50.0),
                mockStudentWithGrade("39", 50.0),
                mockStudentWithGrade("40", 50.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(50.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R18: Five Students, one high score, four low scores -> Avg 20.0
    @Test
    void case18_fiveStudentsSpread_shouldReturn20() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("41", 0.0),
                mockStudentWithGrade("42", 0.0),
                mockStudentWithGrade("43", 100.0),
                mockStudentWithGrade("44", 0.0),
                mockStudentWithGrade("45", 0.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(20.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R19: Five Students, all minimum score above zero (1.0) -> Avg 1.0
    @Test
    void case19_fiveStudentsLowScores_shouldReturn1() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("46", 1.0),
                mockStudentWithGrade("47", 1.0),
                mockStudentWithGrade("48", 1.0),
                mockStudentWithGrade("49", 1.0),
                mockStudentWithGrade("50", 1.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(1.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R20: Boundary Case: Grade 100.0 + Grade 0.0 -> Avg 50.0 (Original from first uploaded file)
    @Test
    void case20_twoStudentsMaxMinAvg50_shouldReturn50() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("37", 100.0),
                mockStudentWithGrade("38", 0.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(50.0, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R21: Boundary Case: Pass (50.0) and Fail (49.9) -> Avg 49.95
    @Test
    void case21_twoStudentsPassFailBoundaryAvg49_95_shouldReturn49_95() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("51", 50.0),
                mockStudentWithGrade("52", 49.9)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(49.95, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }

    // R22: Boundary Case: D (69.9) and C (70.0) -> Avg 69.95
    @Test
    void case22_twoStudentsDCBoundaryAvg69_95_shouldReturn69_95() {
        List<Student> students = Arrays.asList(
                mockStudentWithGrade("53", 69.9),
                mockStudentWithGrade("54", 70.0)
        );
        when(mockCourse.getEnrolledStudents()).thenReturn(students);
        assertEquals(69.95, system.calculateCourseAverage(COURSE_CODE), 0.01);
    }
}