package org.example.gradingsystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Complete Test Suite for the Student Grading System
 * 
 * This test suite aggregates all test classes in the grading system to provide
 * comprehensive testing coverage. It includes:
 * 
 * - GradingSystemTest: Tests the core functionality of the grading system
 *   including student enrollment, grade assignment, and average calculations
 * 
 * - StudentTest: Tests the Student model class functionality including
 *   student creation, grade management, and data validation
 * 
 * - CourseTest: Tests the Course model class functionality including
 *   course creation, student enrollment, and course management
 * 
 * Usage: Run this suite to execute all tests in the grading system
 * 
 * @author Test Suite
 * @version 1.0
 */
@Suite
@SuiteDisplayName("Complete Test Suite for Grading System")
@SelectClasses({
                GradingSystemTest.class,  // Core system functionality tests
                StudentTest.class,        // Student model tests
                CourseTest.class          // Course model tests
})
public class AllTestsSuite {
    // This class serves as a test suite container
    // No implementation needed - annotations handle the test execution
}