# Sunum İçin Basit Komutlar

## 1. BASIS PATH TESTLERİ

### Test 1: evaluateStudentPerformance
```bash
mvn test -Dtest=EvaluatePerformanceBasisPathTests
```

### Test 2: convertScoreToLetterGrade
```bash
mvn test -Dtest=ConvertScoreToLetterGradeBasisPathTests
```

## 2. DECISION TABLE TESTLERİ

```bash
mvn test -Dtest=DetermineExamEligibilityDecisionTableTests
```

## 3. MUTANT TESTLERİ

### Manuel Mutant Testler
```bash
mvn test -Dtest=ManualMutantTests
```

### PIT Mutation Testing (Otomatik)
```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

### PIT Raporunu Aç
```bash
open target/pit-reports/index.html
```

## 4. TÜM TESTLER

```bash
mvn test
```

