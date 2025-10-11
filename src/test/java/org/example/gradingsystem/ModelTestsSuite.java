package org.example.gradingsystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Model Classes Test Suite (Student and Course)")
@SelectClasses({
                StudentTest.class,
                CourseTest.class
})
public class ModelTestsSuite {
}