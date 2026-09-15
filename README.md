# Dlauncher 🖤
### A Dumb Phone Minimalist Launcher for Android

[![Download Latest APK](https://img.shields.io/badge/Download-Latest%20APK-white?style=for-the-badge&logo=github&logoColor=black)](https://github.com/rahul-mallick001/dlauncher/releases)
[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://github.com/rahul-mallick001/dlauncher)
[![Theme](https://img.shields.io/badge/Theme-Pure%20AMOLED%20Black-000000?style=for-the-badge)](https://github.com/rahul-mallick001/dlauncher)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0.en.html)
[![Privacy](https://img.shields.io/badge/Data-100%25%20Private%20%26%20Offline-brightgreen?style=for-the-badge)](https://github.com/rahul-mallick001/dlauncher)

---

## 📥 Download & Install

Direct APK downloads are hosted on GitHub Releases:

### [👉 **Click Here to Download Latest APK**](https://github.com/rahul-mallick001/dlauncher/releases)

1. Download the latest `app-debug.apk` or release `.apk` from the **Releases** page.
2. Tap the downloaded file and select **Install** (allow "Install unknown apps" if prompted).
3. Press your device **Home** button and select **Dlauncher** as your default home app.
4. *(Recommended)* Grant **Usage Access** permission in Settings to enable live Screen Time tracking and Stage 1 Vault reflection.

---

## 🧠 Philosophy: Built on *Atomic Habits*

Dlauncher is intentionally engineered to make your smartphone **dumber, less attractive, and friction-heavy for bad habits**, turning your phone into an intentional tool rather than a dopamine trap.

> *"The inversion of the 3rd Law of Behavior Change is make it difficult. Increase the friction associated with bad behaviors. When friction is high, habits are difficult."*  
>
> *"The best way to break a bad habit is to make it impractical to do. Increase the friction until you don't even have the option to act."*  
> — **James Clear, *Atomic Habits***

### How Dlauncher Inverts the Laws of Behavior Change:

| Inverted Law | Atomic Habits Principle | Dlauncher Implementation |
| :--- | :--- | :--- |
| **1st Law Inversion** | **Make it Invisible** | No app icons, no notification dots, no wallpaper distractions. |
| **2nd Law Inversion** | **Make it Unattractive** | Pure `#000000` AMOLED monochrome theme; strips away colorful stimuli. |
| **3rd Law Inversion** | **Make it Difficult** | Unskippable **5-Stage 3FA Vault** guarding distracting apps with high friction. |
| **4th Law Inversion** | **Make it Unsatisfying** | Large screen-time display and home-screen priority checklist constantly confront you with the real opportunity cost. |

---

## ✨ Key Features

### 🔒 5-Stage 3FA Restricted App Vault
Place addictive apps (social media, games, short-form video) inside the high-friction Restricted Vault. Accessing a vaulted app requires passing **all 5 sequential checkpoints**:

```
[Tap Vaulted App]
       │
       ▼
 1. Question 1 ───► What is your exact Screen Time in hours? (Self-reflection check)
       │
       ▼
 2. Question 2 ───► Did you complete your daily priorities? (Accountability check)
       │
       ▼
 3. Factor 1 (1FA) ► System Biometrics (Fingerprint / Face Unlock)
       │
       ▼
 4. Factor 2 (2FA) ► Custom 3x3 Security Pattern Lock
       │
       ▼
 5. Factor 3 (3FA) ► Master Numeric PIN
       │
       ▼
[Access Granted]
```

---

### 📝 Scrollable Home Screen To-Do Checklist
* **Focus on Real Priorities**: Prominent checklist widget sits directly on your home screen.
* **Smooth In-Place Scrolling**: Handles unlimited tasks without cutting off your view or jittering.
* **In-Place Visual Feedback**: Tap tasks to toggle checkmarks, strike-through formatting, and text dimming.
* **Dynamic Progress Badge**: Real-time ratio indicator (`1/3`, `2/3`, `ALL DONE ✓`).
* **Quick-Add (`+ ADD`)**: Add urgent priorities directly from your home screen in seconds.

---

### 🖤 Pure AMOLED Black (`#000000`) & System Lock Screen
* **No Wallpapers**: Home screen wallpaper rendering is disabled for maximum OLED battery savings and zero visual clutter.
* **AMOLED Lock Screen**: Automatically applies pitch-black wallpaper to Android's system lock screen, adapting Android's Monet engine to monochrome dark clock, notifications, and fingerprint prompts.

---

### ⚡ Distraction-Free Productivity
* **Instant Calculator**: Calculate expressions directly inside the search bar (`25 * 4`, `(120+30)/2`).
* **Fuzzy Typo-Tolerant Search**: Quickly find apps even if misspelled.
* **Web Prefix Shortcuts**: Type `g query` (Google), `y query` (YouTube), `w query` (Wikipedia), or `! query` (DuckDuckGo).
* **Mindful Pause (Friction Mode)**: Enforce a countdown delay before launching apps to curb impulse opens.
* **App Categories**: Organize apps into custom categories (*Work*, *Tools*, *Favorites*).

---

### 🛡️ 100% Private, Offline & Open Source
* **Zero Tracking**: No telemetry, no analytics, no ads, no trackers.
* **Zero Internet Dependency**: Completely offline operation.
* **Open Source**: Licensed under [GNU GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html).

---

## 🛠️ Building From Source

If you prefer to build the APK yourself:

```bash
# Clone the repository
git clone https://github.com/rahul-mallick001/dlauncher.git
cd dlauncher

# Build Debug APK
./gradlew assembleDebug

# Output APK location:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 📄 License

This project is licensed under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).  
Based upon and evolved from the open-source Olauncher project.
