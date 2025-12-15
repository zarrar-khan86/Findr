# 🔍 Findr

<div align="center">

**A Modern Android Application for Seamless User Authentication**

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Firebase](https://img.shields.io/badge/Firebase-Integrated-orange.svg?style=flat&logo=firebase)](https://firebase.google.com)
[![Android](https://img.shields.io/badge/Android-SDK%2034-green.svg?style=flat&logo=android)](https://developer.android.com)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat)](LICENSE)

</div>

---

## 📱 About Findr

**Findr** is a community-driven Lost & Found mobile application designed to help people report, find, and recover lost or found items efficiently. Built with Kotlin and powered by Firebase, Findr provides a seamless offline-first experience with automatic cloud synchronization, photo sharing, and real-time notifications.

Losing personal belongings is a common issue, and current solutions are often unorganized or limited to specific locations. Findr offers a centralized platform where anyone can report lost and found items, even without internet access, and get notified when a matching item is discovered. Whether you're a student, traveler, or commuter, Findr makes recovering lost items easier than ever.

### 🎯 Key Highlights

- **📱 Offline-First** - Report lost/found items even without internet
- **☁️ Auto Cloud Sync** - Automatic synchronization when online
- **📸 Photo Sharing** - Upload and view photos of items
- **🔔 Smart Notifications** - Get alerted when matching items are found
- **🔐 Secure Authentication** - Email/mobile authentication with Firebase
- **🔍 Advanced Search** - Find items by category, location, or description
- **🌍 Community-Driven** - Help others recover their belongings

---

## ✨ Features

### 🔑 Core Features

- **📝 Report Lost/Found Items**
  - Submit reports with detailed descriptions
  - Upload photos of items
  - Add location information
  - Works offline with automatic sync

- **🔍 Smart Search & Discovery**
  - Search by category (electronics, documents, accessories, etc.)
  - Filter by location and date
  - Browse recent submissions
  - Smart match suggestions

- **📸 Photo Management**
  - Upload multiple photos per item
  - Cloud storage with Firebase Storage
  - Image compression for faster loading
  - Download and view high-quality images

- **🔔 Push Notifications**
  - Get notified when matching items are found
  - Real-time updates via Firebase Cloud Messaging
  - Custom notification preferences

- **🔐 User Authentication**
  - Email/Password registration and login
  - Google Sign-In integration
  - Mobile OTP authentication
  - Secure session management

- **📱 Offline Support**
  - Create reports without internet
  - Local database with Room
  - Automatic sync when online
  - Queue management for pending uploads

### 🎯 Target Users

- **Students** - Lost items on campus
- **Travelers** - Items lost in public transport
- **Commuters** - Daily route lost & found
- **General Public** - Parks, malls, cafes, and public spaces
- **Organizations** - Schools, universities, offices with organized lost-and-found systems

---

## 🛠️ Technology Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin |
| **Build System** | Gradle (Kotlin DSL) |
| **UI Framework** | Android Views / Material Design |
| **Architecture** | MVVM Pattern |
| **Backend** | Firebase / RESTful Web API |
| **Cloud Database** | Firebase Firestore |
| **Local Database** | Room (SQLite) |
| **Image Storage** | Firebase Storage |
| **Authentication** | Firebase Auth (Email/Mobile OTP) |
| **OAuth** | Google Sign-In |
| **Notifications** | Firebase Cloud Messaging (FCM) |
| **Networking** | Retrofit / OkHttp |
| **IDE** | Android Studio |

---

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- ✅ **Android Studio** (Hedgehog 2023.1.1 or later)
- ✅ **JDK 11** or higher
- ✅ **Android SDK 34** (minimum SDK 24)
- ✅ **Git** for version control
- ✅ **Firebase Account** (free tier is sufficient)

---

## 📦 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/zarrar-khan86/Findr.git
cd Findr
```

### 2. Configure Firebase

1. Go to the [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or use an existing one
3. Add an Android app to your Firebase project
4. Download the `google-services.json` file
5. Place the `google-services.json` file in the `app/` directory

For detailed Firebase setup instructions, see [FIREBASE_SETUP.md](FIREBASE_SETUP.md)

### 3. Configure Google Sign-In

1. In Firebase Console, enable Google Sign-In under Authentication > Sign-in method
2. Add your SHA-1 and SHA-256 fingerprints to Firebase project settings
3. Download the updated `google-services.json` file

#### Getting SHA Fingerprints

Run the following command in the project directory:

```bash
# For Windows (PowerShell)
.\gradlew signingReport

# For Mac/Linux
./gradlew signingReport
```

**Note**: Each developer needs to add their own SHA fingerprints to the Firebase project for Google Sign-In to work on their machine.

### 4. Set up Java/JDK

Ensure `JAVA_HOME` is set in your environment variables:

```powershell
# Windows PowerShell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
```

### 5. Build the Project

Open the project in Android Studio and let it sync the Gradle files, or run:

```bash
.\gradlew build
```

### 6. Run the Application

- Connect an Android device or start an emulator
- Click "Run" in Android Studio or use:

```bash
.\gradlew installDebug
```

---

## 📂 Project Structure

```
Findr/
├── 📁 app/
│   ├── 📁 src/
│   │   └── 📁 main/
│   │       ├── 📁 java/com/example/project/
│   │       │   ├── 📄 LoginActivity.kt          # Login screen logic
│   │       │   ├── 📄 SignupActivity.kt         # Signup screen logic
│   │       │   └── 📄 MainActivity.kt           # Main app screen
│   │       ├── 📁 res/                          # Resources (layouts, drawables, etc.)
│   │       └── 📄 AndroidManifest.xml           # App manifest
│   ├── 📄 build.gradle.kts                      # App-level build config
│   └── 🔒 google-services.json                  # Firebase config (not in git)
├── 📁 gradle/                                    # Gradle wrapper files
├── 📄 build.gradle.kts                          # Project-level build config
├── 📄 settings.gradle.kts                       # Project settings
├── 📄 FIREBASE_SETUP.md                         # Firebase setup guide
└── 📄 README.md                                 # This file
```

---

## 🔀 Git Workflow

### 📌 Branching Strategy

| Branch | Purpose |
|--------|---------|
| `main` | Production-ready, stable code |
| `user1` | Development branch for collaborative work |
| `feature/*` | Feature-specific branches |

### 🔄 Pulling Latest Changes

```bash
# Fetch latest updates from remote
git fetch origin

# Pull changes from main branch
git pull origin main
```

### 📤 Pushing Your Changes

```bash
# Check status of your changes
git status

# Stage all changes
git add .

# Commit with a meaningful message
git commit -m "feat: Add user profile feature"

# Push to your branch
git push origin user1
```

### 🔗 Creating a Pull Request

1. Push your changes to your branch
2. Go to [GitHub Repository](https://github.com/zarrar-khan86/Findr)
3. Click "Compare & pull request"
4. Add a descriptive title and description
5. Request review from team members
6. Merge after approval

---

## ⚠️ Troubleshooting

### 🔴 Google Sign-In Not Working

**Problem:** "Google sign in failed" or "Error 10" during Google authentication

**Solutions:**

1. **Verify SHA Fingerprints**
   ```bash
   .\gradlew signingReport
   ```
   Copy both SHA-1 and SHA-256 and add them to Firebase Console

2. **Update google-services.json**
   - Download the latest `google-services.json` from Firebase
   - Replace the old file in `app/` directory
   
3. **Clean and Rebuild**
   ```bash
   .\gradlew clean
   .\gradlew build
   ```

4. **Check Package Name**
   - Ensure package name in Firebase matches your app's package name

> **Important:** Each developer working on the project must add their own SHA fingerprints to Firebase!

### 🔴 JAVA_HOME Not Set

**Problem:** "JAVA_HOME is not set and no 'java' command could be found"

**Solution:**

**For Windows:**
```powershell
# Temporary (current session)
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"

# Permanent (add to system environment variables)
[System.Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Program Files\Android\Android Studio\jbr', 'User')
```

**For Mac/Linux:**
```bash
export JAVA_HOME=$(/usr/libexec/java_home)
```

### 🔴 Build Failed / Dependency Issues

**Problem:** Gradle sync fails or dependencies cannot be resolved

**Solutions:**

1. **Invalidate Caches**
   - In Android Studio: `File > Invalidate Caches > Invalidate and Restart`

2. **Update Gradle**
   ```bash
   .\gradlew wrapper --gradle-version=8.2
   ```

3. **Check Internet Connection**
   - Ensure you can access Maven Central and Google Maven

### 🔴 App Crashes on Startup

**Problem:** App crashes immediately after launch

**Solutions:**

1. Check `google-services.json` is present in `app/` directory
2. Verify Firebase configuration is correct
3. Check Logcat for specific error messages
4. Ensure all Firebase dependencies are properly synced

---

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

### How to Contribute

1. **Fork the repository**
   ```bash
   git clone https://github.com/zarrar-khan86/Findr.git
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Write clean, documented code
   - Follow Kotlin coding conventions
   - Add comments where necessary

4. **Test thoroughly**
   - Test on multiple devices/emulators
   - Ensure no regressions
   - Check for memory leaks

5. **Commit with meaningful messages**
   ```bash
   git commit -m "feat: Add user profile picture upload"
   ```

6. **Push and create a Pull Request**
   ```bash
   git push origin feature/your-feature-name
   ```

### Commit Message Convention

- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting)
- `refactor:` - Code refactoring
- `test:` - Adding tests
- `chore:` - Maintenance tasks

---

## 👥 Team

This project is developed and maintained by a collaborative team of passionate developers committed to creating innovative solutions for community-driven lost and found services.

### Lead Developers

- **Zarrar Khan** - [GitHub](https://github.com/zarrar-khan86) | [i221105@nu.edu.pk](mailto:i221105@nu.edu.pk)
- **Ahsan Raza** - [i221203@nu.edu.pk](mailto:i221203@nu.edu.pk)

### Collaborators

- **Jawairia Waseem** - [i221274@nu.edu.pk](mailto:i221274@nu.edu.pk)

Want to join the team? Check out our [Contributing Guidelines](#-contributing)!

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2025 Findr Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```

---

## 📞 Contact & Support

### Get in Touch

- **GitHub Issues:** [Report bugs or request features](https://github.com/zarrar-khan86/Findr/issues)
- **Repository:** [github.com/zarrar-khan86/Findr](https://github.com/zarrar-khan86/Findr)
- **Email:** [i221105@nu.edu.pk](mailto:i221105@nu.edu.pk) | [i221203@nu.edu.pk](mailto:i221203@nu.edu.pk)

### Support the Project

If you find this project helpful, please consider:

- ⭐ **Starring** the repository
- 🐛 **Reporting** bugs or issues
- 💡 **Suggesting** new features
- 🔀 **Contributing** code improvements
- 📢 **Sharing** with others who might find it useful

---

## 🙏 Acknowledgments

- **Firebase** for providing robust authentication infrastructure
- **Google** for Google Sign-In services
- **Android Developer Community** for continuous support and resources
- **Kotlin Team** for the amazing programming language
- All **contributors** who have helped improve this project

---

## 📊 Project Status

🟢 **Active Development** - This project is actively maintained and welcoming contributions

### Roadmap

- [ ] Implement map view for last-seen location
- [ ] Add smart match suggestions using AI
- [ ] Social media authentication (Facebook, Twitter)
- [ ] Implement biometric authentication
- [ ] Add comprehensive user profile management
- [ ] Dark mode support
- [ ] Multi-language support
- [ ] In-app chat for item verification
- [ ] Create comprehensive test suite

---

<div align="center">

**Made with ❤️ by the Findr Team**

⭐ **Star this repository if you find it useful!** ⭐

[Report Bug](https://github.com/zarrar-khan86/Findr/issues) · [Request Feature](https://github.com/zarrar-khan86/Findr/issues) · [Documentation](FIREBASE_SETUP.md)

</div>

