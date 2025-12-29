# MENTAL BUILDING APP - SPECIFICATION

**Project:** Mental Building  
**Platform:** Android (Jetpack Compose)  
**Purpose:** Personal mental health tracking with focus on patterns, triggers, and coping mechanisms  
**Last Updated:** 2025-12-29

---

## 🎯 CORE CONCEPT

**NOT:** Diagnoza, terapia, dziennik emocji  
**YES:** Functional tracking - co robisz, co działa, co nie działa, jakie są wzorce

---

## 📊 KEY FEATURES

### 1. ONBOARDING (First Launch)
**Goal:** Zbierz baseline - informacje które faktycznie UŻYJEMY w trackingu

**Questions (10 max):**

1. **Nałogi/Coping Mechanisms** - Multi-select
   - Używamy w: daily tracking, trigger correlation
   - Options: Zioło, Nikotyna, Alkohol, Narkotyki, Leki, Social media, Gry, Jedzenie
   
2. **Godzina wstawania (średnio)**
   - Używamy w: sleep pattern analysis, morning state correlation
   - Input: Time picker
   
3. **Godzina snu (średnio)**
   - Używamy w: sleep quality tracking baseline
   - Input: Time picker
   
4. **Główny coping mechanism jak jest ciężko**
   - Używamy w: daily quick log, pattern recognition
   - Options: Zioło, Telefon, Sen, Jedzenie, Gry/TV, Izolacja
   
5. **Czy pracujesz?**
   - Używamy w: work context tracking on/off
   - Options: Tak / Nie / L4-fit note
   
6. **Tryb pracy** (jeśli pracujesz)
   - Używamy w: trigger correlation (biuro vs home)
   - Options: Zdalna, Hybryda, Biuro, Nie dotyczy
   
7. **Co najczęściej rozwala Twój dzień?**
   - Używamy w: trigger categories
   - Options: Stres, Myśli, Ludzie, Brak struktury, Napięcie fizyczne
   
8. **Baseline symptoms** - Multi-select
   - Używamy w: tracking improvement over time
   - Options: Prokrastynacja, Brain fog, Napięcie, Trudność z rozpoczęciem, Nadpobudliwość, Freeze, Nadmierne analizowanie, Unikanie
   
9. **Czy chcesz reminder notifications?**
   - Używamy w: notification setup
   - Options: Tak (wybierz godziny) / Nie
   
10. **Preferowana godzina wieczornego logu**
    - Używamy w: evening reminder time
    - Input: Time picker (default 20:00)

**Storage:** UserProfile entity in Room DB

---

## 📱 MAIN APP SCREENS

### TODAY SCREEN
- Morning log (wake up, sleep quality)
- Mental state (anxiety, tension, brain fog)
- Trigger tracker (yes/no + severity)
- Cannabis/substance log
- Screen time
- Coping mechanism used
- Quick action FAB

### EVENING SCREEN
- Day rating (1-10)
- What worked today
- What didn't work
- Tomorrow's intention

### TRENDS SCREEN
- Charts: mental state over time
- Correlation insights (sleep vs anxiety, etc.)
- Trigger frequency
- Coping patterns

### HISTORY SCREEN
- Calendar view
- Past entries list
- Edit/delete options

### SETTINGS SCREEN
- Edit profile/baseline
- Notifications
- Export data (PDF/CSV)
- Privacy

---

## 🗄️ DATA MODEL

### UserProfile
- userId (primary key)
- addictions: List<String>
- avgWakeTime: String
- avgSleepTime: String
- mainCopingMechanism: String
- worksCurrently: Boolean
- workMode: String
- mainDayDisruptors: List<String>
- baselineSymptoms: List<String>
- notificationsEnabled: Boolean
- eveningReminderTime: String

### DailyLog (existing)
- date, wakeTime, sleepQuality, wakeUps, etc.

### MentalStateEntry (existing)
- timestamp, anxiety, tension, brainFog, states

### TriggerEntry (existing)
- timestamp, type, severity, response

### CannabisEntry (existing)
- timestamp, amount, type, effects

---

## 🎨 DESIGN SYSTEM

**Colors:**
- Primary: #2196F3
- Background: #FAFAFA
- Text: #212121
- Success: #4CAF50
- Warning: #FF9800
- Error: #F44336

**Typography:**
- Title: 24sp Bold
- Section: 18sp SemiBold
- Body: 14sp Regular

---

## 🔔 NOTIFICATIONS

1. Morning reminder (based on avgWakeTime + 30min)
2. Evening reminder (user-selected time)
3. Missed log reminder (if no entry by 22:00)

---

## 📈 ANALYTICS & INSIGHTS

**Auto-generated insights:**
- "Your anxiety is 40% lower on days you wake before 8am"
- "Triggers occur 3x more often after poor sleep"
- "Cannabis use correlates with lower brain fog next day"

**Export formats:**
- PDF summary (weekly/monthly)
- CSV raw data

---

## 🚀 ROADMAP

**Phase 1 (Current):**
- [x] Basic tracking screens
- [x] Data models
- [ ] Onboarding flow
- [ ] Trends charts
- [ ] Export PDF

**Phase 2:**
- [ ] Correlation engine
- [ ] Advanced insights
- [ ] Habit streaks
- [ ] Breathing exercises tracker

**Phase 3:**
- [ ] Integration with Google Fit (sleep data)
- [ ] Screen time API integration
- [ ] Backup to cloud

---

## 🔒 PRIVACY

- All data stored locally (Room DB)
- No cloud sync (unless user opts in)
- No analytics tracking
- Export = user owns their data

---

## 🛠️ TECH STACK

**Current:**
- Kotlin 1.9.22
- Jetpack Compose (BOM 2024.02.00)
- Material 3
- Room Database 2.6.1
- WorkManager (notifications)
- MPAndroidChart (graphs)
- iText7 (PDF export)

**Build:**
- Gradle 8.2
- AGP 8.2.0
- Min SDK 26, Target SDK 34

---

## 📝 NOTES

- Focus on FUNCTIONAL data, not emotional journaling
- Quick logging (< 1 min per entry)
- Visual feedback, not text-heavy
- Celebrate patterns, not individual days
- Non-judgmental language everywhere

---

**Next Steps:**
1. Implement onboarding flow
2. Add UserProfile entity to database
3. Build trends screen with charts
4. PDF export functionality
