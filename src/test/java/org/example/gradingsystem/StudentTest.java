package org.example.gradingsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Student Model")
class StudentTest {

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
