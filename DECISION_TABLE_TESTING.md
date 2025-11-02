# Decision Table Testing Documentation

## Method: `determineExamEligibility(String studentId, String courseCode, boolean attendanceComplete, boolean hasDisciplinePenalty)`

### Decision Table

The method evaluates exam eligibility based on three conditions:
- **C1:** `attendanceComplete` - Has the student completed attendance requirements?
- **C2:** `hasDisciplinePenalty` - Does the student have any discipline penalties?
- **C3:** `hasGrade` - Does the student have at least one grade for the course?

### Formal Decision Table

| Rule | Condition 1: Attendance Complete | Condition 2: Discipline Penalty | Condition 3: Has Grade | Action |
|------|-----------------------------------|--------------------------------|-------------------------|--------|
| R1   | N                                 | -                             | -                       | Not Eligible |
| R2   | Y                                 | Y                             | -                       | Not Eligible |
| R3   | Y                                 | N                             | N                       | Conditional |
| R4   | Y                                 | N                             | Y                       | Eligible |

**Legend:**
- **Y** = Yes (True)
- **N** = No (False)
- **-** = Don't Care (condition is not evaluated due to short-circuit logic)

### Decision Table Analysis

#### Conditions:
1. **C1 - Attendance Complete:** `boolean attendanceComplete`
   - Values: True (Y) or False (N)

2. **C2 - Discipline Penalty:** `boolean hasDisciplinePenalty`
   - Values: True (Y) or False (N)

3. **C3 - Has Grade:** `boolean hasGrade = student.getGrade(courseCode) != -1.0`
   - Values: True (Y) or False (N)

#### Rules:

**Rule 1 (R1):** `attendanceComplete = false`
- **Action:** Return "Not Eligible"
- **Rationale:** Even if other conditions are met, incomplete attendance makes student ineligible
- **Short-circuit:** Conditions C2 and C3 are not evaluated (Don't Care)

**Rule 2 (R2):** `attendanceComplete = true` AND `hasDisciplinePenalty = true`
- **Action:** Return "Not Eligible"
- **Rationale:** Discipline penalty overrides all other positive conditions
- **Short-circuit:** Condition C3 is not evaluated (Don't Care)

**Rule 3 (R3):** `attendanceComplete = true` AND `hasDisciplinePenalty = false` AND `hasGrade = false`
- **Action:** Return "Conditional"
- **Rationale:** Student has good attendance and no penalties, but no grades yet

**Rule 4 (R4):** `attendanceComplete = true` AND `hasDisciplinePenalty = false` AND `hasGrade = true`
- **Action:** Return "Eligible"
- **Rationale:** All conditions are met - student is fully eligible

### Test Cases

Each rule corresponds to a test case in `DetermineExamEligibilityDecisionTableTests.java`:

1. **Test R1:** `rule1_incompleteAttendance_shouldReturnNotEligible()`
   - Input: `attendanceComplete = false`, `hasDisciplinePenalty = false`
   - Expected: "Not Eligible"

2. **Test R2:** `rule2_withDisciplinePenalty_shouldReturnNotEligible()`
   - Input: `attendanceComplete = true`, `hasDisciplinePenalty = true`
   - Expected: "Not Eligible"

3. **Test R3:** `rule3_noGradeButEligibleAttendance_shouldReturnConditional()`
   - Input: `attendanceComplete = true`, `hasDisciplinePenalty = false`, `hasGrade = false`
   - Expected: "Conditional"

4. **Test R4:** `rule4_allGoodConditions_shouldReturnEligible()`
   - Input: `attendanceComplete = true`, `hasDisciplinePenalty = false`, `hasGrade = true`
   - Expected: "Eligible"

### Coverage

**Total Possible Combinations:** 2^3 = 8 combinations

**Covered by Decision Table:** 4 rules (all feasible combinations)

**Uncovered Combinations:**
- `attendanceComplete = false, hasDisciplinePenalty = true` → Handled by R1 (short-circuit)
- `attendanceComplete = false, hasDisciplinePenalty = false, hasGrade = true` → Handled by R1 (short-circuit)
- `attendanceComplete = false, hasDisciplinePenalty = true, hasGrade = true` → Handled by R1 (short-circuit)
- `attendanceComplete = false, hasDisciplinePenalty = true, hasGrade = false` → Handled by R1 (short-circuit)

All combinations are effectively covered due to short-circuit evaluation in the code.

### Code Implementation

```java
public String determineExamEligibility(String studentId, String courseCode, 
                                      boolean attendanceComplete, 
                                      boolean hasDisciplinePenalty) {
    Student student = students.get(studentId);
    Course course = courses.get(courseCode);

    if (student == null || course == null) {
        throw new IllegalArgumentException("Student or Course not found.");
    }

    boolean hasGrade = student.getGrade(courseCode) != -1.0;

    // Rule 1: Check attendance first
    if (!attendanceComplete) return "Not Eligible";
    
    // Rule 2: Check discipline penalty
    if (hasDisciplinePenalty) return "Not Eligible";
    
    // Rule 3: Check grade availability
    if (!hasGrade) return "Conditional";
    
    // Rule 4: All conditions met
    return "Eligible";
}
```

### Decision Table Testing Benefits

1. **Systematic Coverage:** Ensures all logical combinations are tested
2. **Clear Documentation:** Table format is easy to understand
3. **Maintainability:** Easy to add new rules or conditions
4. **Traceability:** Each test case maps directly to a decision rule

