package com.example.project

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var ivTogglePassword: ImageView
    private lateinit var ivToggleConfirmPassword: ImageView
    private lateinit var btnSignUp: Button
    private lateinit var tvLogin: TextView
    private lateinit var btnGoogleSignIn: Button

    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    companion object {
        private const val TAG = "SignupActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_signup)

            // Initialize Firebase
            auth = FirebaseAuth.getInstance()
            firestore = FirebaseFirestore.getInstance()

            // Configure Google Sign-In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)

            // Hide action bar
            supportActionBar?.hide()

            // Initialize views
            initializeViews()

            // Setup click listeners
            setupClickListeners()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error loading signup screen: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun initializeViews() {
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        ivTogglePassword = findViewById(R.id.ivTogglePassword)
        ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        tvLogin = findViewById(R.id.tvLogin)
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)
    }

    private fun setupClickListeners() {
        // Toggle password visibility
        ivTogglePassword.setOnClickListener {
            try {
                isPasswordVisible = !isPasswordVisible
                if (isPasswordVisible) {
                    etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    ivTogglePassword.setImageResource(R.drawable.ic_visibility)
                } else {
                    etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    ivTogglePassword.setImageResource(R.drawable.ic_visibility_off)
                }
                etPassword.setSelection(etPassword.text.length)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Toggle confirm password visibility
        ivToggleConfirmPassword.setOnClickListener {
            try {
                isConfirmPasswordVisible = !isConfirmPasswordVisible
                if (isConfirmPasswordVisible) {
                    etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    ivToggleConfirmPassword.setImageResource(R.drawable.ic_visibility)
                } else {
                    etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    ivToggleConfirmPassword.setImageResource(R.drawable.ic_visibility_off)
                }
                etConfirmPassword.setSelection(etConfirmPassword.text.length)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Sign up button
        btnSignUp.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (validateInputs(fullName, email, password, confirmPassword)) {
                signUpWithEmail(fullName, email, password)
            }
        }

        // Login text
        tvLogin.setOnClickListener {
            // Navigate back to login screen
            finish()
        }

        // Google sign in
        btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signUpWithEmail(fullName: String, email: String, password: String) {
        // Disable button to prevent multiple clicks
        btnSignUp.isEnabled = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    // Update user profile with display name
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                // Save additional user data to Firestore
                                saveUserToFirestore(user.uid, fullName, email)
                            }
                        }

                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    navigateToOnboarding()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this,
                        "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    btnSignUp.isEnabled = true
                }
            }
    }

    private fun saveUserToFirestore(userId: String, fullName: String, email: String) {
        val user = hashMapOf(
            "userId" to userId,
            "fullName" to fullName,
            "email" to email,
            "createdAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "User document created successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding user document", e)
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    user?.let {
                        // Save user data to Firestore if new user
                        saveUserToFirestore(it.uid, it.displayName ?: "", it.email ?: "")
                    }
                    Toast.makeText(this, "Google sign in successful!", Toast.LENGTH_SHORT).show()
                    navigateToOnboarding()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateInputs(fullName: String, email: String, password: String, confirmPassword: String): Boolean {
        if (fullName.isEmpty()) {
            etFullName.error = "Full name is required"
            etFullName.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            etEmail.error = "Email or phone is required"
            etEmail.requestFocus()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Please enter a valid email address"
            etEmail.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            etPassword.error = "Password is required"
            etPassword.requestFocus()
            return false
        }

        if (password.length < 6) {
            etPassword.error = "Password must be at least 6 characters"
            etPassword.requestFocus()
            return false
        }

        if (confirmPassword.isEmpty()) {
            etConfirmPassword.error = "Please confirm your password"
            etConfirmPassword.requestFocus()
            return false
        }

        if (password != confirmPassword) {
            etConfirmPassword.error = "Passwords do not match"
            etConfirmPassword.requestFocus()
            return false
        }

        return true
    }

    private fun navigateToOnboarding() {
        val intent = Intent(this, OnboardingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

