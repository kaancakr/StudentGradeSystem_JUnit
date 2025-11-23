# Call Graph Verification

cd /Users/kaancakir/IdeaProjects/StudentGradeSystem && dot -Tpng call_graph.dot -o call_graph.png && dot -Tsvg call_graph.dot -o call_graph.svg && echo "Call graph generated!"

## Verified Method Calls:

1. ✅ **SGS.assignGrade() → Student.addGrade()**
   - Line 53: `student.addGrade(courseCode, score);`

2. ✅ **SGS.calculateCourseAverage() → Student.getGrade()**
   - Line 63: `double grade = student.getGrade(courseCode);`

3. ✅ **SGS.calculateCourseAverage() → Course.getEnrolledStudents()**
   - Line 62: `for (Student student : course.getEnrolledStudents())`

4. ✅ **SGS.enrollStudentToCourse() → Course.addStudent()**
   - Line 45: `course.addStudent(student);`

5. ✅ **SGS.evaluateStudentPerformance() → Student.getGrade()**
   - Line 90: `double grade = student.getGrade(courseCode);`

6. ✅ **SGS.determineExamEligibility() → Student.getGrade()**
   - Line 119: `boolean hasGrade = student.getGrade(courseCode) != -1.0;`

7. ✅ **SGS.addStudent() → Student constructor**
   - Line 26: `students.put(id, new Student(id, name, surname));`

8. ✅ **SGS.addCourse() → Course constructor**
   - Line 36: `courses.put(code, new Course(code, name));`

## Result: All method calls in the call graph are CORRECT! ✅

