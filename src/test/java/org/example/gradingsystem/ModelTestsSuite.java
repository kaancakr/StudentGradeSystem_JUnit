package org.example.gradingsystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Model Classes Test Suite for Student and Course Entities
 * 
 * This test suite focuses specifically on testing the model classes (entities)
 * in the grading system. It provides isolated testing of the core data models
 * without dependencies on the main system functionality.
 * 
 * Test Coverage:
 * 
 * - StudentTest: Comprehensive testing of the Student model class including:
 *   * Student creation and validation
 *   * Grade assignment and retrieval
 *   * Data integrity and edge cases
 *   * Input validation and error handling
 * 
 * - CourseTest: Comprehensive testing of the Course model class including:
 *   * Course creation and management
 *   * Student enrollment functionality
 *   * Course information retrieval
 *   * Enrollment validation and edge cases
 * 
 * This suite is useful for:
 * - Unit testing model classes in isolation
 * - Validating data model behavior
 * - Testing entity relationships
 * - Verifying model constraints and validations
 *
 * @author Eren Kaan Çakır, Berke Beyazbenli
 * @version 1.0
 */
@Suite
@SuiteDisplayName("Model Classes Test Suite (Student and Course)")
@SelectClasses({
                StudentTest.class,  // Student entity tests
                CourseTest.class    // Course entity tests
})
public class ModelTestsSuite {
    // This class serves as a specialized test suite for model classes
    // Focuses on entity-level testing without system dependencies
}