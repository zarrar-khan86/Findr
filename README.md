# Findr

An Android application built with Kotlin and Firebase for authentication and user management.

## Features

- **User Authentication**
  - Email/Password login and signup
  - Google Sign-In integration
  - Password reset functionality
  - Secure password validation

- **Firebase Integration**
  - Firebase Authentication
  - Google Services

## Technology Stack

- **Language**: Kotlin
- **Build System**: Gradle (Kotlin DSL)
- **Architecture**: Android SDK
- **Authentication**: Firebase Auth
- **Google Sign-In**: Google Play Services

## Prerequisites

Before you begin, ensure you have the following installed:
- Android Studio (latest version)
- JDK 11 or higher
- Android SDK
- Git

## Setup Instructions

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

## Project Structure

```
Findr/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/project/
│   │       │   ├── LoginActivity.kt
│   │       │   ├── SignupActivity.kt
│   │       │   └── MainActivity.kt
│   │       ├── res/
│   │       └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── google-services.json (not tracked in git)
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Working with Git

### Branching Strategy

- `main` - Production-ready code
- `user1` - Development branch for user 1
- Create feature branches as needed

### Pulling Latest Changes

```bash
# Fetch and pull from main
git fetch origin
git pull origin main
```

### Pushing Changes

```bash
# Stage your changes
git add .

# Commit with a meaningful message
git commit -m "Add: Description of changes"

# Push to your branch
git push origin user1
```

## Common Issues

### Google Sign-In Not Working

**Issue**: "Google sign in failed" error

**Solution**: 
1. Ensure SHA-1 and SHA-256 fingerprints are added to Firebase
2. Each developer must add their own SHA fingerprints
3. Download the updated `google-services.json` after adding fingerprints
4. Clean and rebuild the project

### JAVA_HOME Not Set

**Issue**: "JAVA_HOME is not set" error

**Solution**:
Set the `JAVA_HOME` environment variable to point to your JDK installation (usually bundled with Android Studio at `C:\Program Files\Android\Android Studio\jbr`)

## Contributing

1. Create a new branch for your feature
2. Make your changes
3. Test thoroughly
4. Commit and push to your branch
5. Create a pull request to merge into `main`

## Team

Collaborative project by multiple developers.

## License

[Add your license information here]

## Contact

[Add contact information here]

