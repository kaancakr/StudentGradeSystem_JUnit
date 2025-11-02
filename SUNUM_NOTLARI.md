# Homework 2 - Sunum Notları

## Ödev Kapsamı

Homework 2 üç ana bölümden oluşuyor:
1. **Basis Path Testing** - Flowchart çizimi ve white-box testler
2. **Mutation Testing** - 10 manuel mutant test (veya PIT aracı ile)
3. **Decision Table Testing** - Tablo tabanlı test senaryoları

---

## 1. BASIS PATH TESTING (Bazı Yol Testleri)

### Nedir?
Basis Path Testing, bir metodun tüm olası yollarını (path'lerini) test eden bir white-box test tekniğidir. Her bir karar noktası (if-else) yeni bir yol yaratır.

### Ne İşe Yarar?
- Kodun tüm dalları test edilir
- Test kapsamı (coverage) artar
- Kodun mantıksal hataları bulunur

### Projede Ne Yaptık?
**2 metod test ettik:**

1. **`evaluateStudentPerformance()`** - Öğrenci performansını değerlendirme
   - Cyclomatic Complexity: **6** (5 karar noktası + 1)
   - 6 farklı path test edildi:
     - Path 1: Geçersiz öğrenci/kurs → "Invalid"
     - Path 2: Not yok → "No Grade"
     - Path 3: Not >= 85 → "Excellent"
     - Path 4: 70 <= Not < 85 → "Satisfactory"
     - Path 5: 50 <= Not < 70 → "Pass"
     - Path 6: Not < 50 → "Fail"

2. **`convertScoreToLetterGrade()`** - Sayısal notu harf notuna çevirme
   - Cyclomatic Complexity: **6**
   - 6 farklı path test edildi:
     - Path 1: Geçersiz not (< 0 veya > 100) → Exception
     - Path 2: Not < 60 → "F"
     - Path 3: 60 <= Not < 70 → "D"
     - Path 4: 70 <= Not < 80 → "C"
     - Path 5: 80 <= Not < 90 → "B"
     - Path 6: 90 <= Not <= 100 → "A"

### Kodlar Nerede?
- Test dosyaları:
  - `EvaluatePerformanceBasisPathTests.java`
  - `ConvertScoreToLetterGradeBasisPathTests.java`
- Dokümantasyon: `BASIS_PATH_TESTING.md` (flowchart'lar içerir)

---

## 2. MUTATION TESTING (Mutasyon Testleri)

### Nedir?
Mutation Testing, test kalitesini ölçmek için kullanılan bir tekniktir. Kodda küçük değişiklikler (mutantlar) yapılır ve testlerin bu değişiklikleri yakalayıp yakalamadığı kontrol edilir.

### Mutant Nedir?
Orijinal kodda yapılan küçük bir değişikliktir. Örneğin:
- `if (score > 100)` → `if (score >= 100)` (mutant)
- `return "Excellent"` → `return "Good"` (mutant)

### Mutant'ın Durumları:
1. **KILLED (Öldürüldü):** Test başarısız oldu → Test iyi çalışıyor ✅
2. **SURVIVED (Hayatta kaldı):** Test başarılı oldu → Test yetersiz ❌

### Ne İşe Yarar?
- Test suite'inizin ne kadar güçlü olduğunu gösterir
- Eksik testleri bulur
- Test kalitesini ölçer

### Projede Ne Yaptık?
**2 yöntem kullandık:**

#### A) PIT Aracı (Otomatik - Bonus)
- Kodda otomatik olarak 75 mutasyon yaptı
- **72 mutant öldürüldü** (96% başarı)
- **3 mutant hayatta kaldı**
- Line Coverage: **100%**

#### B) 10 Manuel Mutant Test
10 farklı mutation operator kullandık:

1. **ROR** - İlişkisel operatör değiştirme (`==` → `>=`)
2. **AOR** - Aritmetik operatör değiştirme (`+` → `-`)
3. **CBM** - Koşul sınırı değiştirme (`<` → `<=`)
4. **NOT** - Negasyon operatörü kaldırma (`!` → kaldırıldı)
5. **INC/DEC** - Artırma/azaltma (`+1` eklendi)
6. **VAR** - Yanlış değişken kullanma (`student` → `course`)
7. **CRP** - Sabit değer değiştirme (`-1.0` → `-2.0`)
8. **LOR** - Mantıksal operatör değiştirme (`||` → `&&`)
9. **SDL** - Satır silme (bir satırı silme)
10. **RVM** - Dönüş değeri değiştirme (`"Excellent"` → `"Good"`)

### Kodlar Nerede?
- Test dosyası: `ManualMutantTests.java`
- PIT raporları: `target/pit-reports/index.html`

---

## 3. DECISION TABLE TESTING (Karar Tablosu Testleri)

### Nedir?
Decision Table Testing, bir metodun tüm olası koşul kombinasyonlarını tablo halinde gösteren bir test tekniğidir.

### Ne İşe Yarar?
- Tüm mantıksal kombinasyonları sistematik olarak test eder
- Eksik test senaryolarını bulur
- Test kapsamını garanti eder

### Projede Ne Yaptık?
**`determineExamEligibility()` metodu için:**

**3 Koşul (Condition):**
- C1: `attendanceComplete` - Devam tamamlandı mı?
- C2: `hasDisciplinePenalty` - Disiplin cezası var mı?
- C3: `hasGrade` - Not var mı?

**4 Kural (Rule):**

| Kural | Devam | Cezası | Not | Sonuç |
|-------|-------|--------|-----|-------|
| R1 | Hayır | - | - | Not Eligible |
| R2 | Evet | Var | - | Not Eligible |
| R3 | Evet | Yok | Yok | Conditional |
| R4 | Evet | Yok | Var | Eligible |

**Testler:**
- Her kural için bir test yazıldı
- Tüm kombinasyonlar kapsandı

### Kodlar Nerede?
- Test dosyası: `DetermineExamEligibilityDecisionTableTests.java`
- Dokümantasyon: `DECISION_TABLE_TESTING.md`

---

## SUNUMDA ÇALIŞTIRILACAK KOMUTLAR

### 1. Tüm Testleri Çalıştırma
```bash
mvn test
```

### 2. Sadece Basis Path Testleri
```bash
mvn test -Dtest=EvaluatePerformanceBasisPathTests
mvn test -Dtest=ConvertScoreToLetterGradeBasisPathTests
```

### 3. Sadece Decision Table Testleri
```bash
mvn test -Dtest=DetermineExamEligibilityDecisionTableTests
```

### 4. Manuel Mutant Testleri
```bash
mvn test -Dtest=ManualMutantTests
```

### 5. PIT Mutation Testing
```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

### 6. PIT Raporunu Açma (macOS)
```bash
open target/pit-reports/index.html
```

---

## ÖNEMLİ DOSYALAR

### Test Dosyaları:
1. `EvaluatePerformanceBasisPathTests.java` - Basis path test 1
2. `ConvertScoreToLetterGradeBasisPathTests.java` - Basis path test 2
3. `DetermineExamEligibilityDecisionTableTests.java` - Decision table test
4. `ManualMutantTests.java` - 10 manuel mutant test

### Dokümantasyon:
1. `BASIS_PATH_TESTING.md` - Flowchart'lar ve açıklamalar
2. `DECISION_TABLE_TESTING.md` - Decision table açıklamaları
3. `README.md` - Genel proje bilgileri

---

## SUNUM AKIŞI ÖNERİSİ

1. **Basis Path Testing:**
   - `BASIS_PATH_TESTING.md` dosyasını göster (flowchart'lar)
   - `mvn test -Dtest=EvaluatePerformanceBasisPathTests` çalıştır
   - Sonuçları göster

2. **Decision Table Testing:**
   - `DECISION_TABLE_TESTING.md` dosyasındaki tabloyu göster
   - `mvn test -Dtest=DetermineExamEligibilityDecisionTableTests` çalıştır
   - Sonuçları göster

3. **Mutation Testing:**
   - `ManualMutantTests.java` dosyasını göster
   - `mvn test -Dtest=ManualMutantTests` çalıştır
   - PIT çalıştır: `mvn org.pitest:pitest-maven:mutationCoverage`
   - PIT raporunu aç: `open target/pit-reports/index.html`
   - Mutation score: 96% göster

---

## ÖZET İSTATİSTİKLER

- **Basis Path Testleri:** 2 metod, her biri CC=6
- **Decision Table:** 4 kural, %100 kapsam
- **Manuel Mutant:** 10 farklı mutation operator
- **PIT Mutation Score:** %96 (75 mutasyon, 72 öldürüldü)
- **Line Coverage:** %100

