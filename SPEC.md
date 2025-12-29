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

### **Core Tables (Phase 1)**

#### UserProfile
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
- onboardingCompleted: Boolean

#### DailyLog (existing)
- date, wakeTime, sleepQuality, wakeUps, etc.

#### MentalStateEntry (existing)
- timestamp, anxiety, tension, brainFog, states

#### TriggerEntry (existing)
- timestamp, type, severity, response

#### CannabisEntry (existing)
- timestamp, amount, type, effects

### **New Tables (Phase 2-4)**

#### SupplementEntry
- id (primary key)
- date: LocalDate
- supplementName: String
- dosage: String
- timeOfDay: String
- notes: String (optional)

#### WeightEntry (extend existing)
- id (primary key)
- date: LocalDate
- weightKg: Float
- bmi: Float (calculated)
- notes: String (optional)

#### SobrietyTracker
- id (primary key)
- substanceName: String
- startDate: LocalDate
- currentStreak: Int
- longestStreak: Int
- lastRelapseDate: LocalDate (nullable)
- relapseNotes: String (nullable)

#### Appointment
- id (primary key)
- appointmentType: String (Psycholog, Psychiatra, Lekarz, Terapia)
- date: LocalDateTime
- location: String (optional)
- doctorName: String (optional)
- preNotes: String (what to discuss)
- postNotes: String (what was discussed)
- actionItems: List<String>
- nextAppointmentDate: LocalDateTime (nullable)
- reminderEnabled: Boolean

#### EmergencyContact
- id (primary key)
- contactType: String (Personal, Hotline)
- name: String
- phone: String
- country: String
- description: String (e.g., "24/7 Crisis Line")
- isPrimary: Boolean

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

## 🚀 DEVELOPMENT ROADMAP

### **PHASE 1: CORE TRACKING (MVP)** ⏳ 2-3 weeks
**Goal:** Functional daily tracking app

- [x] Basic data models (Room DB)
- [x] Today screen with mental state tracking
- [x] Trigger logging
- [x] Cannabis/substance tracking
- [ ] **Onboarding flow** (10 questions setup)
- [ ] **UserProfile entity** + persistence
- [ ] **Evening screen** (day summary)
- [ ] **History screen** (calendar view + past entries)
- [ ] **Basic notifications** (morning + evening reminders)

**Deliverable:** Usable tracking app with core features

---

### **PHASE 2: HEALTH INTEGRATION & ANALYTICS** ⏳ 3-4 weeks
**Goal:** Data insights + external health data

**Analytics:**
- [ ] **Trends screen** with charts (MPAndroidChart)
  - Mental state trends (line chart)
  - Trigger frequency (bar chart)
  - Sleep quality trends (area chart)
- [ ] **Correlation engine**
  - Sleep vs anxiety patterns
  - Substance use vs brain fog
  - Work days vs trigger frequency
- [ ] **Auto-generated insights** (e.g., "Anxiety 40% lower on 8h+ sleep days")

**Health Data:**
- [ ] **Google Health Connect integration**
  - Import sleep data
  - Import steps/activity data
  - Import heart rate (optional)
- [ ] **Smartwatch sync** (if Health Connect supported)
  - Wear OS companion app (optional)
  - Quick log from watch (optional)

**Supplements & Vitals:**
- [ ] **Supplement/Vitamin log**
  - Add supplement entries (name, dosage, time)
  - Daily checklist view
  - Correlation with mental state
- [ ] **Weight tracking** (already in data model - add UI)
  - Manual entry
  - Chart over time
  - BMI calculator (optional)

**Deliverable:** Data-driven insights + health platform integration

---

### **PHASE 3: SOBRIETY & HABIT TRACKING** ⏳ 2 weeks
**Goal:** Support recovery and habit building

- [ ] **Sobriety counter** (dni bez używki)
  - Per-substance tracking (weed, alcohol, nicotine, etc.)
  - Streak display on home screen
  - Milestone celebrations (1 day, 7 days, 30 days, 90 days)
  - Relapse tracking (reset with note)
- [ ] **Habit streaks**
  - Meditation streak
  - Exercise streak
  - Good sleep streak
- [ ] **Breathing exercises tracker** (already mentioned)
  - Guided breathing timer
  - Daily completion tracking
  - Stress reduction correlation

**Deliverable:** Recovery support tools

---

### **PHASE 4: APPOINTMENTS & CRISIS SUPPORT** ⏳ 1-2 weeks
**Goal:** Healthcare coordination + safety net

**Appointment Management:**
- [ ] **Kalendarz wizyt**
  - Psycholog appointments
  - Psychiatrist appointments
  - General doctor visits
  - Therapy sessions
- [ ] **Appointment reminders** (notifications)
- [ ] **Pre-appointment notes**
  - "What to discuss" checklist
  - Recent symptoms summary
  - Auto-export last 7 days for therapist
- [ ] **Post-appointment log**
  - What was discussed
  - Action items
  - Next appointment date

**Crisis Support:**
- [ ] **Infolinie z pomocą** (crisis hotlines)
  - Country-specific hotlines (Poland, UK, etc.)
  - One-tap call button
  - SMS/chat options where available
  - Emergency contacts (personal)
- [ ] **Safety plan** (optional)
  - Warning signs list
  - Coping strategies reminder
  - Emergency contacts
  - Crisis resources

**Deliverable:** Healthcare integration + safety features

---

### **PHASE 5: DATA OWNERSHIP & EXPORT** ⏳ 1-2 weeks
**Goal:** User owns their data

- [ ] **PDF Export** (already using iText7)
  - Weekly summary
  - Monthly summary
  - Custom date range
  - Charts + insights included
  - Shareable with therapist
- [ ] **CSV Export**
  - Raw data export
  - All tables
  - Date-filtered
- [ ] **Cloud backup** (optional)
  - Google Drive export
  - Auto-backup schedule
  - Encrypted backup
- [ ] **Import data** (restore from backup)

**Deliverable:** Full data portability

---

### **PHASE 6: ADVANCED FEATURES** ⏳ 3-4 weeks (optional)
**Goal:** Nice-to-haves and polish

- [ ] **Screen time API integration**
  - Android Digital Wellbeing API
  - Auto-import daily screen time
  - App-specific tracking
- [ ] **Medication tracker**
  - Prescription log
  - Dosage reminders
  - Refill reminders
- [ ] **Journaling** (optional - keep minimal)
  - Short daily notes
  - Voice-to-text option
  - Tag with mood/context
- [ ] **Dark mode**
- [ ] **Widgets** (home screen)
  - Quick log widget
  - Today's summary widget
  - Streak counter widget
- [ ] **Wear OS app**
  - Quick mental state log
  - Breathing exercise timer
  - View today's summary

**Deliverable:** Polished, full-featured app

---

### **PHASE 7: COMMUNITY & SHARING (Future)** ⏳ TBD
**Goal:** Optional peer support (privacy-first)

- [ ] Anonymous community (optional)
  - Share patterns (not personal data)
  - "What works for me" tips
  - Strictly moderated
- [ ] Therapist sharing (opt-in)
  - Generate shareable link
  - Read-only access for therapist
  - Time-limited access
- [ ] Group challenges (optional)
  - 30-day meditation challenge
  - Sobriety support groups
  - Anonymous participation

**Deliverable:** Community features (only if user demand exists)

---

## 📊 PRIORITY MATRIX

### **MUST HAVE (Phase 1-2)**
1. Onboarding
2. Daily tracking (morning + evening)
3. History view
4. Basic charts
5. Notifications

### **SHOULD HAVE (Phase 3-4)**
1. Sobriety counter
2. Appointment calendar
3. Crisis hotlines
4. Supplement log
5. PDF export

### **NICE TO HAVE (Phase 5-6)**
1. Health Connect integration
2. Smartwatch sync
3. Widgets
4. Dark mode
5. Advanced analytics

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

## 🆘 DEFAULT CRISIS HOTLINES

Pre-populated in app (Phase 4):

### **Poland 🇵🇱**
- **Telefon Zaufania dla Dzieci i Młodzieży:** 116 111
- **Kryzysowa Linia Wsparcia:** 800 70 2222
- **Linia wsparcia emocjonalnego:** 116 123
- **Niebieska Linia (przemoc):** 800 12 00 02

### **United Kingdom 🇬🇧**
- **Samaritans:** 116 123 (24/7)
- **Crisis Text Line:** Text SHOUT to 85258
- **NHS Mental Health:** 111
- **Mind Infoline:** 0300 123 3393

### **United States 🇺🇸**
- **988 Suicide & Crisis Lifeline:** 988
- **Crisis Text Line:** Text HOME to 741741
- **SAMHSA National Helpline:** 1-800-662-4357

### **International 🌍**
- **International Association for Suicide Prevention:** findahelpline.com
- **Befrienders Worldwide:** befrienders.org

**Note:** User can add personal emergency contacts + local resources

---

## 📝 NOTES

- Focus on FUNCTIONAL data, not emotional journaling
- Quick logging (< 1 min per entry)
- Visual feedback, not text-heavy
- Celebrate patterns, not individual days
- Non-judgmental language everywhere
- **Privacy-first:** All data local by default, no analytics, no tracking
- **Recovery-supportive:** Non-judgmental tone for substance use/relapse
- **Therapist-friendly:** Easy export for sharing with healthcare providers

---

## 🎯 SUCCESS METRICS

**User engagement:**
- Daily active users logging ≥ 3 days/week
- Avg. time to complete daily log: < 2 minutes
- Onboarding completion rate: > 80%

**Value delivered:**
- Users report actionable insights from correlation engine
- PDF exports used for therapy appointments
- Sobriety streaks motivate recovery

**Not tracking:**
- No user analytics sent to servers
- No A/B testing
- No behavioral tracking
- User owns ALL their data

---

**Next Steps (Phase 1):**
1. Implement onboarding flow (10 screens)
2. Add UserProfile entity to database
3. Build evening screen
4. Build history screen with calendar
5. Setup notification system
