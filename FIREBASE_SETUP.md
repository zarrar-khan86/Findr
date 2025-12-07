# Firebase Setup Instructions for Findr App

## Prerequisites
- Android Studio installed
- A Google account

## Step 1: Create a Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project" or "Create a project"
3. Enter project name: `Findr` (or your preferred name)
4. Click Continue
5. Disable Google Analytics (optional, can enable later)
6. Click "Create Project"

## Step 2: Add Android App to Firebase Project

1. In Firebase Console, click the Android icon to add an Android app
2. Enter your package name: `com.example.project`
3. Enter app nickname: `Findr`
4. Leave SHA-1 blank for now (needed for Google Sign-In later)
5. Click "Register app"

## Step 3: Download google-services.json

1. Download the `google-services.json` file
2. **IMPORTANT**: Replace the placeholder file at:
   ```
   app/google-services.json
   ```
   with the downloaded file from Firebase Console

## Step 4: Enable Authentication Methods

1. In Firebase Console, go to **Authentication** > **Sign-in method**
2. Enable **Email/Password**:
   - Click on "Email/Password"
   - Toggle "Enable" switch
   - Click "Save"

3. Enable **Google Sign-In**:
   - Click on "Google"
   - Toggle "Enable" switch
   - Select a support email
   - Click "Save"

## Step 5: Get SHA-1 Certificate (For Google Sign-In)

### Debug SHA-1:
Run this command in your project directory:
```bash
./gradlew signingReport
```

Or use this keytool command:
```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

### Add SHA-1 to Firebase:
1. Copy the SHA-1 fingerprint
2. Go to Firebase Console > Project Settings
3. Scroll to "Your apps" section
4. Click on your Android app
5. Click "Add fingerprint"
6. Paste the SHA-1 and click "Save"
7. Download the updated `google-services.json` file
8. Replace the old file in your project

## Step 6: Enable Firestore Database (Optional but Recommended)

1. In Firebase Console, go to **Firestore Database**
2. Click "Create database"
3. Select "Start in test mode" (for development)
4. Choose a Cloud Firestore location close to your users
5. Click "Enable"

## Step 7: Firestore Security Rules

Replace the default rules with:
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users collection - users can read/write their own data
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Items collection (for lost/found items)
    match /items/{itemId} {
      allow read: if request.auth != null;
      allow create: if request.auth != null;
      allow update, delete: if request.auth != null && 
                               request.auth.uid == resource.data.userId;
    }
  }
}
```

## Step 8: Update default_web_client_id

1. Open the downloaded `google-services.json` file
2. Find the `oauth_client` section with `"client_type": 3`
3. Copy the `client_id` value
4. Update in `app/src/main/res/values/strings.xml`:
   ```xml
   <string name="default_web_client_id" translatable="false">381595841386-lnod8ac04asu83js7db50such18nngnf.apps.googleusercontent.com</string>
   ```

## Step 9: Sync and Build

1. In Android Studio, click **File** > **Sync Project with Gradle Files**
2. Wait for sync to complete
3. Build and run your app

## Features Implemented

### ✅ Email/Password Authentication
- User registration with email and password
- User login with email and password
- Password validation (minimum 6 characters)
- Email validation
- Password reset via email

### ✅ Google Sign-In
- One-tap Google authentication
- Automatic user profile creation
- Seamless integration with Firebase Auth

### ✅ User Data Management
- User profile stored in Firestore
- User data includes:
  - User ID
  - Full Name
  - Email
  - Creation timestamp

### ✅ Session Management
- Automatic login persistence
- Check for existing session on app start
- Secure logout functionality

## Testing

### Test User Registration:
1. Open the app
2. Click "Sign Up"
3. Fill in the form with:
   - Full Name: Test User
   - Email: test@example.com
   - Password: test123456
   - Confirm Password: test123456
4. Click "Sign Up"
5. You should see "Account created successfully!" message
6. Navigate through onboarding screens to Home

### Test User Login:
1. Open the app
2. Enter credentials:
   - Email: test@example.com
   - Password: test123456
3. Click "Login"
4. You should see "Login successful!" message
5. Navigate directly to Home

### Test Google Sign-In:
1. Open the app
2. Click "Continue with Google"
3. Select a Google account
4. Grant permissions
5. You should be logged in and navigated to onboarding or home

### Test Forgot Password:
1. On login screen, enter your email
2. Click "Forgot Password?"
3. Check your email for password reset link

## Troubleshooting

### Google Sign-In Not Working:
- Make sure SHA-1 fingerprint is added to Firebase Console
- Verify `default_web_client_id` is correct in strings.xml
- Download fresh `google-services.json` after adding SHA-1

### Build Errors:
- Run `./gradlew clean` and then rebuild
- Make sure `google-services.json` is in the correct location (`app/` directory)
- Check that all Firebase dependencies are synced

### Authentication Failed:
- Check Firebase Console for authentication errors
- Verify internet connection
- Check Firestore rules if using Firestore

## Security Best Practices

1. **Never commit google-services.json to public repositories**
   - Add to `.gitignore`:
   ```
   google-services.json
   ```

2. **Use ProGuard/R8** in release builds to obfuscate code

3. **Update Firestore Security Rules** before production:
   - Move from test mode to production mode
   - Implement proper data validation

4. **Enable App Check** in Firebase Console for production

5. **Implement proper error handling** for production builds

## Next Steps

1. Implement logout functionality
2. Add user profile screen
3. Implement email verification
4. Add phone number authentication
5. Implement social login (Facebook, Twitter, etc.)
6. Add biometric authentication
7. Implement password strength indicator
8. Add two-factor authentication (2FA)

## Support

For Firebase documentation:
- [Firebase Auth Docs](https://firebase.google.com/docs/auth)
- [Firestore Docs](https://firebase.google.com/docs/firestore)
- [Firebase Android Setup](https://firebase.google.com/docs/android/setup)

For issues specific to this implementation, check the code comments in:
- `LoginActivity.kt`
- `SignupActivity.kt`

