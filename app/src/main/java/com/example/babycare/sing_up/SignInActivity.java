package com.example.babycare.sing_up;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.HomePage;
import com.example.babycare.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {
    private GoogleSignInClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Button button = findViewById(R.id.Google);
        client = GoogleSignIn.getClient(this, options);
        button.setOnClickListener(v -> {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent,RC_SIGN_IN);
        });
    }
    private static final int RC_SIGN_IN = 1234;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Successfully signed in
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Sign-in failed
                Log.e("TAG", "Google sign-in failed", e);
            }
        }
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in, update UI accordingly
            String displayName = user.getDisplayName();
            String email = user.getEmail();

            // Example: Display user information in TextViews or ImageView
            TextView displayNameTextView = findViewById(R.id.displayNameTextView);
            TextView emailTextView = findViewById(R.id.emailTextView);

            displayNameTextView.setText(displayName);
            emailTextView.setText(email);

            // Example: Navigate to the main screen or perform any other desired actions
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
            finish(); // Optional: Close the current activity to prevent going back to authentication screen
        } else {
            // User is signed out, update UI accordingly
            // Example: Show a sign-in button or display a message
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Successfully authenticated with Firebase
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        // You can access the user's information using the 'user' object

                        // Optional: You can perform additional actions after authentication
                        // For example, update UI, navigate to the main screen, etc.

                        // Example: Update UI after successful authentication
                        updateUI(user);
                    } else {
                        // Authentication failed
                        Log.e("TAG", "signInWithCredential:failure", task.getException());
                        Error error = new Error("Connection error");
                        System.out.println(error);
                        // Optional: You can handle the failure case as needed
                        // For example, display an error message, show a toast, etc.
                    }
                });
    }

}
