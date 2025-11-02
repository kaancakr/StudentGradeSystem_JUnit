# Basis Path Testing Documentation

## 1. Method: `evaluateStudentPerformance(String studentId, String courseCode)`

### Flowchart

```
                    [START]
                       |
                       v
        ┌──────────────────────────────┐
        │ Student student =             │
        │   students.get(studentId);   │
        │ Course course =               │
        │   courses.get(courseCode);   │
        └──────────────┬───────────────┘
                       |
                       v
        ┌──────────────────────────────┐
        │ if (student == null ||        │
        │     course == null)           │
        └──────┬─────────────┬──────────┘
               |             |
        [TRUE] |             | [FALSE]
               |             |
               v             |
        ┌─────────────┐     |
        │ return      │     |
        │ "Invalid"   │     |
        └─────────────┘     |
               |            |
               |            v
               |    ┌──────────────────────┐
               |    │ double grade =       │
               |    │   student.getGrade(  │
               |    │     courseCode);     │
               |    └──────────┬───────────┘
               |               |
               |               v
               |    ┌──────────────────────┐
               |    │ if (grade == -1.0)   │
               |    └──────┬───────────────┘
               |          |
               |    [TRUE]|     | [FALSE]
               |          |     |
               |          v     |
               |    ┌───────────┐
               |    │ return    │
               |    │ "No Grade"│
               |    └───────────┘
               |          |
               |          v
               |    ┌──────────────────────┐
               |    │ if (grade >= 85)      │
               |    └──────┬───────────────┘
               |          |
               |    [TRUE]|     | [FALSE]
               |          |     |
               |          v     |
               |    ┌───────────┐
               |    │ return    │
               |    │ "Excellent"│
               |    └───────────┘
               |          |
               |          v
               |    ┌──────────────────────┐
               |    │ else if (grade >= 70)│
               |    └──────┬───────────────┘
               |          |
               |    [TRUE]|     | [FALSE]
               |          |     |
               |          v     |
               |    ┌───────────┐
               |    │ return    │
               |    │ "Satisfactory"│
               |    └───────────┘
               |          |
               |          v
               |    ┌──────────────────────┐
               |    │ else if (grade >= 50)│
               |    └──────┬───────────────┘
               |          |
               |    [TRUE]|     | [FALSE]
               |          |     |
               |          v     |
               |    ┌───────────┐
               |    │ return    │
               |    │ "Pass"    │
               |    └───────────┘
               |          |
               |          v
               |    ┌───────────┐
               |    │ return    │
               |    │ "Fail"    │
               |    └───────────┘
               |          |
               └──────────┴───────┐
                                 |
                                 v
                            [END]
```

### Cyclomatic Complexity Calculation

**Formula:** V(G) = E - N + 2P

Where:
- E = Number of edges
- N = Number of nodes
- P = Number of connected components (usually 1)

**Counting Decision Points:**
1. `if (student == null || course == null)` → 1 decision
2. `if (grade == -1.0)` → 1 decision
3. `if (grade >= 85)` → 1 decision
4. `else if (grade >= 70)` → 1 decision
5. `else if (grade >= 50)` → 1 decision

**Alternative Formula:** V(G) = Number of Decision Points + 1

**Cyclomatic Complexity = 5 + 1 = 6**

**Basis Paths (V(G) + 1 = 7 paths):**

1. **Path 1:** student == null OR course == null → return "Invalid"
2. **Path 2:** student != null AND course != null AND grade == -1.0 → return "No Grade"
3. **Path 3:** student != null AND course != null AND grade != -1.0 AND grade >= 85 → return "Excellent"
4. **Path 4:** student != null AND course != null AND grade != -1.0 AND grade < 85 AND grade >= 70 → return "Satisfactory"
5. **Path 5:** student != null AND course != null AND grade != -1.0 AND grade < 70 AND grade >= 50 → return "Pass"
6. **Path 6:** student != null AND course != null AND grade != -1.0 AND grade < 50 → return "Fail"
7. **Path 7:** Independent path (combination of above)

---

## 2. Method: `convertScoreToLetterGrade(double score)`

### Flowchart

```
                    [START]
                       |
                       v
        ┌──────────────────────────────┐
        │ if (score > 100 || score < 0)│
        └──────┬─────────────┬──────────┘
               |             |
        [TRUE] |             | [FALSE]
               |             |
               v             |
        ┌─────────────┐     |
        │ throw       │     |
        │ IllegalArgumentException│
        └─────────────┘     |
               |            |
               |            v
               |    ┌──────────────────────┐
               |    │ if (score >= 90)      │
               |    └──────┬───────────────┘
               |          |
               |    [TRUE]|     | [FALSE]
               |          |     |
               |          v     |
               |    ┌───────────┐
               |    │ return "A"│
               |    └───────────┘
               |          |
               |          v
               |    ┌──────────────────────┐
               |    │ if (score >= 80)      │
               |    └──────┬───────────────┘
               |          |
               |    [TRUE]|     | [FALSE]
               |          |     |
               |          v     |
               |    ┌───────────┐
               |    │ return "B"│
               |    └───────────┘
               |          |
               |          v
               |    ┌──────────────────────┐
               |    │ if (score >= 70)      │
               |    └──────┬───────────────┘
               |          |
               |    [TRUE]|     | [FALSE]
               |          |     |
               |          v     |
               |    ┌───────────┐
               |    │ return "C"│
               |    └───────────┘
               |          |
               |          v
               |    ┌──────────────────────┐
               |    │ if (score >= 60)      │
               |    └──────┬───────────────┘
               |          |
               |    [TRUE]|     | [FALSE]
               |          |     |
               |          v     |
               |    ┌───────────┐
               |    │ return "D"│
               |    └───────────┘
               |          |
               |          v
               |    ┌───────────┐
               |    │ return "F"│
               |    └───────────┘
               |          |
               └──────────┴───────┐
                                 |
                                 v
                            [END]
```

### Cyclomatic Complexity Calculation

**Decision Points:**
1. `if (score > 100 || score < 0)` → 1 decision
2. `if (score >= 90)` → 1 decision
3. `if (score >= 80)` → 1 decision
4. `if (score >= 70)` → 1 decision
5. `if (score >= 60)` → 1 decision

**Cyclomatic Complexity = 5 + 1 = 6**

**Basis Paths (V(G) + 1 = 7 paths):**

1. **Path 1:** score > 100 OR score < 0 → throw IllegalArgumentException
2. **Path 2:** 0 <= score < 60 → return "F"
3. **Path 3:** 60 <= score < 70 → return "D"
4. **Path 4:** 70 <= score < 80 → return "C"
5. **Path 5:** 80 <= score < 90 → return "B"
6. **Path 6:** 90 <= score <= 100 → return "A"
7. **Path 7:** Independent path

---

## Test Coverage

Both methods have **Cyclomatic Complexity >= 4**, satisfying the homework requirement:
- `evaluateStudentPerformance`: **CC = 6** ✓
- `convertScoreToLetterGrade`: **CC = 6** ✓

All basis paths are covered by unit tests in:
- `EvaluatePerformanceBasisPathTests.java`
- `ConvertScoreToLetterGradeBasisPathTests.java`

