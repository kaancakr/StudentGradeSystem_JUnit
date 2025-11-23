package org.example.gradingsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basis Path Testing for convertScoreToLetterGrade() method
 * Cyclomatic Complexity: 6
 * Decision Points:
 * 1. if (score > 100 || score < 0) → throw exception
 * 2. if (score >= 90) → return "A"
 * 3. if (score >= 80) → return "B"
 * 4. if (score >= 70) → return "C"
 * 5. if (score >= 60) → return "D"
 * 6. else → return "F"
 * Basis Paths:
 * 1. Invalid score (> 100 or < 0) → Exception
 * 2. Score < 60 → "F"
 * 3. 60 <= Score < 70 → "D"
 * 4. 70 <= Score < 80 → "C"
 * 5. 80 <= Score < 90 → "B"
 * 6. 90 <= Score <= 100 → "A"
 */
@DisplayName("Basis Path Testing for convertScoreToLetterGrade()")
class ConvertScoreToLetterGradeBasisPathTests {

    // Path 1: Invalid score (score > 100)
    @Test
    void path1_invalidScoreGreaterThan100_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            StudentGradingSystem.convertScoreToLetterGrade(101.0);
        });
    }

    // Path 1 (variant): Invalid score (score < 0)
    @Test
    void path1_invalidScoreLessThan0_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            StudentGradingSystem.convertScoreToLetterGrade(-1.0);
        });
    }

    // Path 2: Score < 60 → "F"
    @Test
    void path2_scoreLessThan60_shouldReturnF() {
        assertEquals("F", StudentGradingSystem.convertScoreToLetterGrade(0.0));
        assertEquals("F", StudentGradingSystem.convertScoreToLetterGrade(30.0));
        assertEquals("F", StudentGradingSystem.convertScoreToLetterGrade(59.9));
    }

    // Path 3: 60 <= Score < 70 → "D"
    @Test
    void path3_scoreBetween60And70_shouldReturnD() {
        assertEquals("D", StudentGradingSystem.convertScoreToLetterGrade(60.0));
        assertEquals("D", StudentGradingSystem.convertScoreToLetterGrade(65.0));
        assertEquals("D", StudentGradingSystem.convertScoreToLetterGrade(69.9));
    }

    // Path 4: 70 <= Score < 80 → "C"
    @Test
    void path4_scoreBetween70And80_shouldReturnC() {
        assertEquals("C", StudentGradingSystem.convertScoreToLetterGrade(70.0));
        assertEquals("C", StudentGradingSystem.convertScoreToLetterGrade(75.0));
        assertEquals("C", StudentGradingSystem.convertScoreToLetterGrade(79.9));
    }

    // Path 5: 80 <= Score < 90 → "B"
    @Test
    void path5_scoreBetween80And90_shouldReturnB() {
        assertEquals("B", StudentGradingSystem.convertScoreToLetterGrade(80.0));
        assertEquals("B", StudentGradingSystem.convertScoreToLetterGrade(85.0));
        assertEquals("B", StudentGradingSystem.convertScoreToLetterGrade(89.9));
    }

    // Path 6: 90 <= Score <= 100 → "A"
    @Test
    void path6_scoreBetween90And100_shouldReturnA() {
        assertEquals("A", StudentGradingSystem.convertScoreToLetterGrade(90.0));
        assertEquals("A", StudentGradingSystem.convertScoreToLetterGrade(95.0));
        assertEquals("A", StudentGradingSystem.convertScoreToLetterGrade(100.0));
    }

    // Edge Cases: Boundary values
    @Test
    void edgeCase_boundaryValues_shouldReturnCorrectGrades() {
        // Lower boundary of D
        assertEquals("D", StudentGradingSystem.convertScoreToLetterGrade(60.0));

        // Lower boundary of C
        assertEquals("C", StudentGradingSystem.convertScoreToLetterGrade(70.0));

        // Lower boundary of B
        assertEquals("B", StudentGradingSystem.convertScoreToLetterGrade(80.0));

        // Lower boundary of A
        assertEquals("A", StudentGradingSystem.convertScoreToLetterGrade(90.0));

        // Upper boundary
        assertEquals("A", StudentGradingSystem.convertScoreToLetterGrade(100.0));
    }
}
