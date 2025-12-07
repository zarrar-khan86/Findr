package com.example.project

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var ivTogglePassword: ImageView
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignUp: TextView
    private lateinit var btnGoogleSignIn: Button

    private var isPasswordVisible = false

    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_login)

            // Initialize Firebase
            auth = FirebaseAuth.getInstance()

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
            Toast.makeText(this, "Error loading login screen: ${e.message}", Toast.LENGTH_LONG).show()
            // Navigate to MainActivity as fallback
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is already signed in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already logged in, navigate to home
            navigateToHome()
        }
    }

    private fun initializeViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        ivTogglePassword = findViewById(R.id.ivTogglePassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
        tvSignUp = findViewById(R.id.tvSignUp)
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
                // Move cursor to end
                etPassword.setSelection(etPassword.text.length)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Login button
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (validateInputs(email, password)) {
                loginWithEmail(email, password)
            }
        }

        // Forgot password
        tvForgotPassword.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            resetPassword(email)
        }

        // Sign up
        tvSignUp.setOnClickListener {
            // Navigate to sign up screen
            startActivity(Intent(this, SignupActivity::class.java))
        }

        // Google sign in
        btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun loginWithEmail(email: String, password: String) {
        // Disable button to prevent multiple clicks
        btnLogin.isEnabled = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    btnLogin.isEnabled = true
                }
            }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Password reset email sent to $email",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Failed to send reset email: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
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
                    Toast.makeText(this, "Google sign in successful!", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            etEmail.error = "Email or phone is required"
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

        return true
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

