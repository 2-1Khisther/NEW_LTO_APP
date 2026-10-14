# LTO Reviewer App

A hybrid (offline + online) Android reviewer application for the Philippine Land Transportation Office (LTO) driver's license exam. Built as an undergraduate capstone project.

Users can take mock exams and review study materials fully offline, or create an account to sync their exam history, scores, and streaks online once connected to the internet.

---

## ✨ Features

- **Non-Professional & Professional license reviewers** — category-based mock exams and review materials
- **Road Signs reviewer** — browse through official road signs with explanations
- **Fines And Penalties reviewer** — browse common traffic violations and their penalties
- **Mock Exam mode** — timed, multiple-choice practice exams per category
- **Exam Results** — automatic scoring, pass/fail status, and a correct/incorrect/skipped breakdown
- **Review Materials mode** — read-only walkthrough of questions and correct answers
- **Profile** — exam stats (questions answered, correct/incorrect counts)
- **Settings** — notification, sound, vibration, and dark mode toggles; account options
- **Login / Register** — account creation for syncing progress across devices
- **Offline-first** — mock exams and review materials work without an internet connection
- **English / Filipino language toggle**

---

## 🛠️ Tech Stack

**Current (Android app):**
- Kotlin
- Android Views + ConstraintLayout (XML)
- Material Components for Android

**Planned:**
- Room (SQLite) — local offline storage for questions, review materials, and queued exam results
- REST API backend (connecting the app and the admin panel to MySQL)
- MySQL — central database for user accounts, questions, exam history, and streaks
- Admin web panel — for managing questions, review materials, and users

---

## 📱 Screens

| Screen | Description |
|---|---|
| Home / Dashboard | Overall score, mock exam progress, category shortcuts |
| Non-Professional / Professional | Category dashboard with Mock Exam and Review Materials entry points |
| Road Signs | Browsable road sign reference |
| Fines And Penalties | Browsable violations and penalty amounts |
| Mock Exam | Timed multiple-choice exam, per category |
| Exam Results | Score, pass/fail status, answer breakdown |
| Review Materials | Read-only question + correct answer walkthrough |
| Profile | User stats |
| Settings | App preferences and account options |
| Login / Register | Account creation and sign-in |

---

## 🚧 Project Status

This project is being built in phases:

- [x] **Phase 1 — UI** — all core screens built and navigable
- [ ] **Phase 2 — Local database (Room)** — in progress
- [ ] **Phase 3 — MySQL schema design**
- [ ] **Phase 4 — Backend REST API**
- [ ] **Phase 5 — Connect app to API (login, sync)**
- [ ] **Phase 6 — Admin web panel**
- [ ] **Phase 7 — Polish & thesis documentation**

Questions, review content, and exam data are currently hardcoded as sample data within the app for UI development purposes, and will be migrated to a local database and backend in upcoming phases.

---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/<your-repo-name>.git
   ```
2. Open the project in **Android Studio**.
3. Let Gradle sync finish.
4. Run on an emulator or physical device (minimum SDK / target SDK as configured in `app/build.gradle`).

---

## 📂 Project Structure

```
app/src/main/
├── java/com/example/lto_app/
│   ├── MainActivity.kt
│   ├── NonProfessionalActivity.kt
│   ├── ProfessionalActivity.kt
│   ├── RoadSignsActivity.kt
│   ├── FinesPenaltiesActivity.kt
│   ├── MockExamActivity.kt
│   ├── ReviewActivity.kt
│   ├── ExamResultsActivity.kt
│   ├── ProfileActivity.kt
│   ├── SettingsActivity.kt
│   ├── LoginActivity.kt
│   ├── RegisterActivity.kt
│   ├── BottomNavHelper.kt
│   └── LanguageHelper.kt
└── res/
    ├── layout/
    ├── drawable/
    ├── values/
    └── menu/
```

---

## 🎓 Capstone Project

This application is developed as an undergraduate thesis/capstone project. Development progress is tracked in the accompanying project logbook and build roadmap.

---

## 📄 License

*(Add your preferred license here — e.g. MIT, or leave as "All rights reserved" if this is submitted as academic coursework.)*
