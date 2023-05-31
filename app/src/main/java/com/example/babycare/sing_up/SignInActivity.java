package com.example.babycare.sing_up;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.HomePage;
import com.example.babycare.R;
import com.example.babycare.data.Database;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {
    public final int RC_SIGN_IN = 1234;


   public void updateUI(FirebaseUser user) {
        if (user != null) {
            String displayName = user.getDisplayName();
            String email = user.getEmail();
            TextView displayNameTextView = findViewById(R.id.firstNameDisplay);
            TextView emailTextView = findViewById(R.id.emailDisplay);
            displayNameTextView.setText(displayName);
            emailTextView.setText(email);
            Intent intent = new Intent(this, Database.class);
            startActivity(intent);
            setContentView(R.layout.user_information_page);
            finish();
        } else {
            setContentView(R.layout.sign_up);
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
