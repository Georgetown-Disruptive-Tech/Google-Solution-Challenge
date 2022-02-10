package com.example.google_solution_challenge

//import com.google.firebase.quickstart.auth.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth


class FirebaseUIActivity : AppCompatActivity() {
    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_ui)
        createSignInIntent()
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult?) {
        val response = result?.idpResponse
        if (result != null) {
            if (result.resultCode == RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
        else{
            throw NullPointerException("RESULT IS NULL")
        }
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            //AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
        // [END auth_fui_create_intent]
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }
    }

    private fun delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_delete]
    }

    private fun privacyAndTerms() {
        val providers = emptyList<AuthUI.IdpConfig>()
        // [START auth_fui_pp_tos]
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTosAndPrivacyPolicyUrls(
                "https://example.com/terms.html",
                "https://example.com/privacy.html")
            .build()
        signInLauncher.launch(signInIntent)
        // [END auth_fui_pp_tos]
    }



}