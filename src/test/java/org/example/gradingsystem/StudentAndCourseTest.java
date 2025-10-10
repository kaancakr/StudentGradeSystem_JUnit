package org.example.gradingsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Student and Course Models")
class StudentAndCourseTest {

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
        System.out.println("Verification 2: Retrieved grade for non-existent course 'CHEM101' is " + actualGrade2 + ". Expected is -1.0.");
        assertEquals(-1.0, actualGrade2);
        System.out.println("<<< Test finished: retrieveAssignedGrade");
    }
}