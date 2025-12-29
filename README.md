# Mental Building App

Aplikacja do diagnostycznego trackingu zdrowia psychicznego, zaprojektowana jako narzędzie kliniczne dla śledzenia wzorców lęku, unikania, agencji i funkcjonowania codziennego.

## 📱 Instalacja APK na telefonie

### Krok 1: Pobierz APK z GitHub Actions

1. Przejdź do zakładki **Actions** w tym repozytorium
2. Kliknij na najnowszy workflow "Build Android APK"
3. Przewiń w dół do sekcji **Artifacts**
4. Pobierz **mental-building-debug.zip**
5. Rozpakuj ZIP → otrzymasz **app-debug.apk**

### Krok 2: Zainstaluj na telefonie

1. Prześlij **app-debug.apk** na telefon (email, USB, Google Drive, etc.)
2. Na telefonie: otwórz plik APK
3. System poprosi o pozwolenie "Install from unknown sources"
   - Android 8+: **Settings → Apps → Special access → Install unknown apps** → wybierz aplikację (np. Chrome/Files) → włącz
4. Kliknij **Install**
5. Po instalacji: otwórz **Mental Building**

## 🎨 Funkcje (wersja preview)

### Ekran DZIŚ (TODAY) ✅
- **Poranek**: Log godziny wstawania, wake feeling, jakość snu
- **Samopoczucie**: Sliders dla lęku, napięcia, brain fog (0-5)
- **Trigger**: Logowanie triggerów z automatycznymi myślami
- **Quick logs**: Jedzenie, zioło (ilość + efekt), screen time
- Białe GUI z niebieskimi akcentami

### W budowie:
- Evening Integration (Zamknij Dzień)
- Historia (kalendarz + szczegóły dni)
- Trendy (wykresy, korelacje, pattern detection)
- PDF Export (raport dla psychologa)
- Notifications (9:00, 14:00, 22:00)

## 🛠️ Tech Stack

- **Kotlin** + **Jetpack Compose** (Material 3)
- **Room Database** (local-first, offline)
- **MVVM Architecture**
- **Coroutines + Flow** (reactive data)

## 📊 Design System

- **Background**: White (#FFFFFF)
- **Primary**: Blue (#2196F3)
- **Cards**: White with 1px gray border
- **Typography**: 24sp headers, 16sp body
- **Components**: Large touch targets (56dp buttons), sliders, chips

## 🔧 Development

### Wymagania:
- Android Studio Electric Eel+
- JDK 17
- Android SDK 26+

### Build lokalnie:
```bash
cd MentalBuildingApp
./gradlew assembleDebug
# APK w: app/build/outputs/apk/debug/app-debug.apk
```

## 📝 Status

- ✅ Database schema (13 tabel)
- ✅ UI Design system
- ✅ TODAY screen (kompletny)
- ⏳ Evening screen
- ⏳ History screen
- ⏳ Trends + Analytics
- ⏳ PDF Export
- ⏳ Notifications

---

**Branch**: `claude/wellness-tracking-app-RRnv5`
**Preview build**: Zobacz Actions tab dla najnowszego APK
