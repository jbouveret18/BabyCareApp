package com.example.babycare.sign_in;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.home_page.HomePageActivity;
import com.example.babycare.R;
import com.example.babycare.sing_up.SignUp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleSignInActivity extends AppCompatActivity {
    public final int RC_SIGN_IN = 1234;


   public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent homePageIntent = new Intent(this, HomePageActivity.class);
            startActivity(homePageIntent);
            setContentView(R.layout.home_page);
            finish();
        } else {
            setContentView(R.layout.sign_up);
            Intent signUpIntent = new Intent(this, SignUp.class);
            startActivity(signUpIntent);
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
