# 📱 FIGMA PROMPT – ONBOARDING FLOW

**App:** Mental Building  
**Screen Type:** First-time user onboarding (one-time setup)  
**Goal:** Collect baseline data for personalized tracking  
**Style:** Clean, minimal, non-clinical, supportive

---

## 🎯 DESIGN BRIEF

**What this is:**
- First launch experience
- 10 quick questions (< 3 minutes total)
- Functional data collection (NO fluff questions)
- Sets up: tracking categories, reminders, baseline patterns

**What this is NOT:**
- Medical questionnaire
- Emotional diary
- Clinical diagnosis tool

---

## 📐 SCREEN FLOW (10 STEPS)

### **SCREEN 0: WELCOME**
**Layout:**
- App logo/icon (centered)
- Title: "Mental Building"
- Subtitle: "Śledzenie wzorców, nie emocji"
- Button: "Zacznijmy" (Primary blue, large)
- Small text: "~2 minuty setup"

**Style:**
- Calm, welcoming
- Minimal text
- Clear call to action

---

### **SCREEN 1: NAŁOGI/COPING**
**Question:** "Co używasz do regulacji napięcia?"  
**Subtitle:** "Zaznacz wszystkie które dotyczą"

**Multi-select chips (wrap layout):**
- [ ] Zioło
- [ ] Nikotyna
- [ ] Alkohol
- [ ] Narkotyki
- [ ] Leki
- [ ] Social media
- [ ] Gry
- [ ] Jedzenie

**Bottom:**
- Progress indicator (1/10)
- Button: "Dalej" (enabled when ≥1 selected OR "Pomiń" option)

**Design:**
- Chips: outlined, toggle to filled when selected
- Color: Primary blue for selected
- Allow "none" option

---

### **SCREEN 2: GODZINY SNU I WSTAWANIA**
**Question:** "Kiedy zazwyczaj śpisz?"  
**Subtitle:** "Średnio, nie co do minuty"

**Two time pickers (side by side or stacked):**
- "Kładę się o:" [Time picker, default 23:00]
- "Wstaję o:" [Time picker, default 7:00]

**Bottom:**
- Progress: 2/10
- Button: "Dalej"

**Design:**
- Native Material 3 time picker
- 24h format
- Clear visual separation

---

### **SCREEN 3: GŁÓWNY COPING**
**Question:** "Co robisz najczęściej, gdy jest ciężko?"  
**Subtitle:** "Wybierz JEDNO"

**Single-select large buttons (vertical stack):**
- ( ) Zioło
- ( ) Telefon / scroll
- ( ) Sen / leżenie
- ( ) Jedzenie
- ( ) Gry / TV
- ( ) Izolacja / unikanie

**Bottom:**
- Progress: 3/10
- Button: "Dalej" (enabled when selected)

**Design:**
- Radio buttons or toggle cards
- Clear selected state
- Tappable entire card

---

### **SCREEN 4: STATUS PRACY**
**Question:** "Aktualnie:"

**Single-select:**
- ( ) Pracuję
- ( ) L4 / fit note
- ( ) Nie pracuję

**Bottom:**
- Progress: 4/10
- Button: "Dalej"

---

### **SCREEN 5: TRYB PRACY**
**Question:** "Jak pracujesz?"  
**Visibility:** Show ONLY if "Pracuję" selected in previous screen

**Single-select:**
- ( ) Zdalnie
- ( ) Hybryda
- ( ) Biuro
- ( ) Nie dotyczy

**Bottom:**
- Progress: 5/10
- Button: "Dalej"

**Design:**
- Conditional screen (skip if not working)

---

### **SCREEN 6: CO ROZWALA DZIEŃ**
**Question:** "Co najczęściej rozwala Twój dzień?"  
**Subtitle:** "Zaznacz główne"

**Multi-select chips:**
- [ ] Stres
- [ ] Myśli (overthinking)
- [ ] Ludzie
- [ ] Brak struktury
- [ ] Napięcie fizyczne

**Bottom:**
- Progress: 6/10
- Button: "Dalej"

---

### **SCREEN 7: BASELINE SYMPTOMS**
**Question:** "Co występuje u Ciebie regularnie?"  
**Subtitle:** "To nie diagnoza - tylko wzorce do śledzenia"

**Multi-select chips (wrap):**
- [ ] Prokrastynacja
- [ ] Brain fog
- [ ] Napięcie
- [ ] Trudność z rozpoczęciem
- [ ] Nadpobudliwość
- [ ] Freeze (zamrożenie)
- [ ] Nadmierne analizowanie
- [ ] Unikanie kontaktu

**Bottom:**
- Progress: 7/10
- Button: "Dalej"

**Design:**
- Smaller chips (many options)
- Scrollable if needed
- Allow "none" option

---

### **SCREEN 8: NOTIFICATIONS**
**Question:** "Chcesz przypomnienia?"

**Toggle + explanation:**
- [Switch] Włącz powiadomienia
- Small text: "Porannik + wieczorny log"

**If enabled, show:**
- "Rano: automatycznie (30 min po wstaniu)"
- "Wieczór: [Time picker, default 20:00]"

**Bottom:**
- Progress: 8/10
- Button: "Dalej"

---

### **SCREEN 9: CONFIRM SETUP**
**Title:** "Gotowe!"  
**Subtitle:** "Możesz to zmienić później w ustawieniach"

**Summary card (collapsible sections):**
- Twoje coping mechanisms: [list]
- Godziny snu: [times]
- Praca: [status]
- Przypomnienia: [on/off]

**Bottom:**
- Progress: 9/10
- Button: "Zapisz i zacznij"

**Design:**
- Read-only summary
- Link: "Edytuj" → goes back
- Reassuring tone

---

### **SCREEN 10: FIRST DAY PROMPT**
**Title:** "Loguj pierwszy dzień"  
**Subtitle:** "Nie musisz wypełniać wszystkiego - zacznij od małych kroków"

**Quick action buttons:**
- "Jak się dzisiaj obudziłeś?" → Morning log
- "Jak teraz się czujesz?" → Mental state
- "Przejdź do dashboardu" → Main app

**Bottom:**
- No progress bar
- Button: "Przejdź do aplikacji"

---

## 🎨 DESIGN SPECS

### **General Layout**
- Safe area padding: 16dp horizontal, 24dp vertical
- Max content width: 400dp (center on tablets)
- Bottom buttons always visible (sticky)
- Progress indicator: top of screen, subtle

### **Colors**
- Primary: #2196F3 (buttons, selected states)
- Background: #FAFAFA
- Text: #212121
- Chips outline: #9E9E9E
- Selected chip: #2196F3 fill + white text

### **Typography**
- Question (title): 20sp, SemiBold, #212121
- Subtitle: 14sp, Regular, #757575
- Button: 16sp, Medium, white on blue
- Chip text: 14sp, Regular

### **Components**
- Chips: 36dp height, 12dp padding, 18dp corner radius
- Buttons: 48dp height, full width, 8dp corner radius
- Time pickers: Material 3 default
- Toggle switches: Material 3 default

### **Interactions**
- Tap feedback on all interactive elements
- Smooth transitions between screens (slide left/right)
- "Back" button goes to previous question
- Progress auto-saves (can resume if app closes)

### **Accessibility**
- High contrast text
- Large tap targets (min 48dp)
- Screen reader support
- Clear focus indicators

---

## ✅ DELIVERABLES

1. **10 onboarding screens** (full mockups)
2. **Component library:**
   - Question header
   - Multi-select chips
   - Single-select cards
   - Time picker integration
   - Progress indicator
   - Bottom action button
3. **Flow prototype:** clickable transitions
4. **Empty/error states:** e.g., "Please select at least one"

---

## 🚫 AVOID

- ❌ Clinical language ("symptoms", "diagnosis", "treatment")
- ❌ Judgment ("you should", "this is bad")
- ❌ Overwhelm (max 10 questions, keep it tight)
- ❌ Unnecessary fields (don't ask what we won't use)
- ❌ Dark patterns (all questions skippable except critical ones)

---

## 💡 TONE & VOICE

**DO:**
- "Co używasz do regulacji?"
- "Kiedy zazwyczaj śpisz?"
- "Co rozwala Twój dzień?"

**DON'T:**
- "Jakie masz zaburzenia?"
- "Opisz swoje problemy"
- "Dlaczego tak się czujesz?"

---

## 📱 EXAMPLE INTERACTION FLOW

1. User opens app first time
2. See welcome screen → tap "Zacznijmy"
3. Question 1 → select chips → "Dalej"
4. Question 2 → pick times → "Dalej"
5. ... continue through 10 screens
6. Confirm screen → "Zapisz i zacznij"
7. First day prompt → choose action or go to dashboard
8. **Onboarding complete** → never show again (unless "Reset setup" in Settings)

---

## 🔧 TECHNICAL NOTES FOR DEVS

- Store responses in `UserProfile` entity (Room DB)
- Save progress after each screen (allow resume)
- Flag `onboardingCompleted = true` after finish
- Show onboarding ONLY if flag = false
- Allow re-editing profile in Settings screen

---

**TL;DR:**  
Design 10-screen onboarding flow with clean Material 3 UI. Ask only functional questions we'll actually use for tracking. Non-judgmental, supportive tone. Quick setup (< 3 min). Multi-select chips, time pickers, radio buttons. Progress indicator. Save and allow editing later.

---

**Copy this prompt to Figma AI or share with designer.**
