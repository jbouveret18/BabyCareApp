package com.example.babycare.sign_in;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.HomePage;
import com.example.babycare.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleSignInActivity extends AppCompatActivity {
    public final int RC_SIGN_IN = 1234;


   public void updateUI(FirebaseUser user) {
        if (user != null) {
            String displayName = user.getDisplayName();
            String email = user.getEmail();
            TextView displayNameTextView = findViewById(R.id.firstNameDisplay);
            TextView emailTextView = findViewById(R.id.emailDisplay);
            displayNameTextView.setText(displayName);
            emailTextView.setText(email);
            Intent userInformationIntent = new Intent(this, HomePage.class);
            startActivity(userInformationIntent);
            setContentView(R.layout.user_information_page);
            finish();
        } else {
            setContentView(R.layout.sign_up);
            Intent userInformationIntent = new Intent(this, GoogleSignInActivity.class);
            startActivity(userInformationIntent);
        }
    }
    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.e("TAG", "signInWithCredential:failure", task.getException());
                        Error error = new Error("Connection error");
                    }
                });

    }

}
