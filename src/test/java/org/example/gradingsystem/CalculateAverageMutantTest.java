package org.example.gradingsystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Unit Test for calculateCourseAverage (PIT Target)")
public class CalculateAverageMutantTest {

    @Test
    void calculateAverage_MutantTest() {
        StudentGradingSystem system = new StudentGradingSystem();
        system.addCourse("CS101", "Programming");
        system.addStudent("101", "Alice", "Smith");
        system.addStudent("102", "Bob", "Johnson");
        system.addStudent("103", "Charlie", "Brown"); // Ungraded student

        system.enrollStudentToCourse("101", "CS101");
        system.enrollStudentToCourse("102", "CS101");
        system.enrollStudentToCourse("103", "CS101");

        system.assignGrade("101", "CS101", 100.0);
        system.assignGrade("102", "CS101", 60.0);

        // Expected result: (100.0 + 60.0) / 2 graded students = 80.0
        assertEquals(80.0, system.calculateCourseAverage("CS101"), 0.01,
                "The calculation must equal 80.0, verifying loop, addition, and division.");
    }
}