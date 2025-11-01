package org.example.gradingsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Basis Path Tests for convertScoreToLetterGrade")
class ConvertScoreBasisPathTests {

    // Paths 1 & 2: Invalid Score (Boundary and Edge Cases)
    @Test
    @DisplayName("Path 1 & 2: Invalid scores should throw exception")
    void paths1_2_invalidScore_shouldThrowException() {
        // P1: score > 100
        assertThrows(IllegalArgumentException.class, () ->
                StudentGradingSystem.convertScoreToLetterGrade(100.1));
        // P2: score < 0
        assertThrows(IllegalArgumentException.class, () ->
                StudentGradingSystem.convertScoreToLetterGrade(-0.1));
    }

    // P3: Path to 'A' (>= 90)
    @Test
    @DisplayName("Path 3: Score >= 90 should return A")
    void path3_score90_shouldReturnA() {
        assertEquals("A", StudentGradingSystem.convertScoreToLetterGrade(90.0));
    }

    // P4: Path to 'B' (>= 80 and < 90)
    @Test
    @DisplayName("Path 4: Score 80-89.9 should return B")
    void path4_score85_shouldReturnB() {
        assertEquals("B", StudentGradingSystem.convertScoreToLetterGrade(85.0));
    }

    // P5: Path to 'C' (>= 70 and < 80)
    @Test
    @DisplayName("Path 5: Score 70-79.9 should return C")
    void path5_score75_shouldReturnC() {
        assertEquals("C", StudentGradingSystem.convertScoreToLetterGrade(75.0));
    }

    // P6: Path to 'D' (>= 60 and < 70)
    @Test
    @DisplayName("Path 6: Score 60-69.9 should return D")
    void path6_score65_shouldReturnD() {
        assertEquals("D", StudentGradingSystem.convertScoreToLetterGrade(65.0));
    }

    // P7: Path to 'F' (< 60)
    @Test
    @DisplayName("Path 7: Score < 60 should return F")
    void path7_score55_shouldReturnF() {
        assertEquals("F", StudentGradingSystem.convertScoreToLetterGrade(55.0));
    }
}