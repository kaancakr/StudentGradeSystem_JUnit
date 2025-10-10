package org.example.gradingsystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

// Slayt 37: @Suite ve @SelectClasses ile test suite oluşturma
@Suite
@SuiteDisplayName("Complete Test Suite for Grading System")
@SelectClasses({
        GradingSystemTest.class,
        StudentAndCourseTest.class
})
public class AllTestsSuite {
}