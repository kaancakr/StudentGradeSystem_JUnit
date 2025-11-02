# Student Grading System - JUnit Testing Project

## Ödev İçeriği

Bu proje, yazılım test teknikleri kapsamında hazırlanmış bir öğrenci notlandırma sistemi ve kapsamlı test suite'idir.

## Ödev Gereksinimleri ve Çözümler

### 1. Basis Path Testing ✅

**Gereksinim:** Flowchart çizimi, basis path gösterimi ve white-box testler (en az 4 cyclomatic complexity)

**Çözüm:**
- ✅ **Method 1:** `evaluateStudentPerformance()` - Cyclomatic Complexity = 6
  - Flowchart ve basis path dokümantasyonu: `BASIS_PATH_TESTING.md`
  - Test sınıfı: `EvaluatePerformanceBasisPathTests.java`
  
- ✅ **Method 2:** `convertScoreToLetterGrade()` - Cyclomatic Complexity = 6
  - Flowchart ve basis path dokümantasyonu: `BASIS_PATH_TESTING.md`
  - Test sınıfı: `ConvertScoreToLetterGradeBasisPathTests.java`

**Dokümantasyon:** `BASIS_PATH_TESTING.md` dosyasında detaylı flowchart'lar ve cyclomatic complexity hesaplamaları bulunmaktadır.

### 2. Mutation Testing ✅

**Gereksinim:** HW1'deki unit testlerden biri için 10 mutant kod yazılması (PIT kullanarak bonus veya manuel)

**Çözüm:**
- ✅ **PIT Mutation Testing (Bonus):**
  - PIT konfigürasyonu: `pom.xml`
  - Mutation Score: **96%** (75 mutasyon, 72 öldürüldü)
  - Line Coverage: **100%**
  - Raporlar: `target/pit-reports/index.html`
  
- ✅ **10 Manuel Mutant Test:**
  - Test sınıfı: `ManualMutantTests.java`
  - 10 farklı mutation operator kullanıldı:
    1. Relational Operator Replacement (ROR)
    2. Arithmetic Operator Replacement (AOR)
    3. Conditional Boundary Mutation (CBM)
    4. Negation Operator Insertion (NOT)
    5. Increment/Decrement (INC/DEC)
    6. Variable Replacement (VAR)
    7. Constant Replacement (CRP)
    8. Logical Operator Replacement (LOR)
    9. Statement Deletion (SDL)
    10. Return Value Mutation (RVM)

### 3. Decision Table Testing ✅

**Gereksinim:** Bir metod için tablo tabanlı test senaryosu ve testler

**Çözüm:**
- ✅ **Method:** `determineExamEligibility()`
- ✅ **Decision Table:** 4 kural (R1, R2, R3, R4)
- ✅ **Test Sınıfı:** `DetermineExamEligibilityDecisionTableTests.java`
- ✅ **Dokümantasyon:** `DECISION_TABLE_TESTING.md` dosyasında formal decision table bulunmaktadır

**Decision Table:**
| Rule | Attendance | Discipline | Has Grade | Result |
|------|------------|------------|-----------|--------|
| R1   | N          | -          | -         | Not Eligible |
| R2   | Y          | Y          | -         | Not Eligible |
| R3   | Y          | N          | N         | Conditional |
| R4   | Y          | N          | Y         | Eligible |

## Proje Yapısı

```
StudentGradeSystem_JUnit-master/
├── src/
│   ├── main/java/org/example/gradingsystem/
│   │   ├── StudentGradingSystem.java    # Ana sistem sınıfı
│   │   ├── Student.java                 # Öğrenci modeli
│   │   └── Course.java                 # Kurs modeli
│   └── test/java/org/example/gradingsystem/
│       ├── GradingSystemTest.java                      # Ana test suite
│       ├── EvaluatePerformanceBasisPathTests.java      # Basis path test 1
│       ├── ConvertScoreToLetterGradeBasisPathTests.java # Basis path test 2
│       ├── DetermineExamEligibilityDecisionTableTests.java # Decision table test
│       ├── ManualMutantTests.java                      # 10 manuel mutant
│       ├── StudentTest.java
│       └── CourseTest.java
├── BASIS_PATH_TESTING.md         # Basis path dokümantasyonu
├── DECISION_TABLE_TESTING.md     # Decision table dokümantasyonu
├── pom.xml                        # Maven konfigürasyonu
└── README.md                      # Bu dosya
```

## Çalıştırma Komutları

### Tüm Testleri Çalıştırma
```bash
mvn test
```

### Sadece Basis Path Testleri
```bash
mvn test -Dtest=EvaluatePerformanceBasisPathTests,ConvertScoreToLetterGradeBasisPathTests
```

### Sadece Decision Table Testleri
```bash
mvn test -Dtest=DetermineExamEligibilityDecisionTableTests
```

### Manuel Mutant Testleri
```bash
mvn test -Dtest=ManualMutantTests
```

### PIT Mutation Testing Çalıştırma
```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

PIT raporları: `target/pit-reports/index.html`

### PIT Raporunu Görüntüleme
```bash
# macOS/Linux
open target/pit-reports/index.html

# Windows
start target/pit-reports/index.html
```

## Test Sonuçları Özeti

- **Total Test Classes:** 7
- **Total Test Methods:** 66+
- **Line Coverage:** 100%
- **Mutation Score:** 96%
- **Basis Path Coverage:** %100 (her iki metod için)
- **Decision Table Coverage:** %100 (4 kural)

## Teknolojiler

- **Java:** 17
- **JUnit:** 5.10.2
- **Maven:** 3.x
- **PIT Mutation Testing:** 1.15.0

## Yazarlar

- Eren Kaan Çakır
- Berke Beyazbenli

## Lisans

Bu proje eğitim amaçlıdır.

