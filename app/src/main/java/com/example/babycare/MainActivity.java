package com.example.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.sign_in.GoogleSignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    GoogleSignInActivity googleSignInActivity = new GoogleSignInActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signUpButton = findViewById(R.id.SignUp);
        Button googleSignInButton = findViewById(R.id.Google);
        googleSignInButton.setOnClickListener(v ->{
            GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleSignInClient client = GoogleSignIn.getClient(this, options);
            Intent intent = client.getSignInIntent();
            startActivityForResult(intent, googleSignInActivity.RC_SIGN_IN);
        });
        signUpButton.setOnClickListener(v -> {Intent signUpIntent = new Intent(MainActivity.this, GoogleSignInActivity.class);
        startActivity(signUpIntent);});
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == googleSignInActivity.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Successfully signed in
                GoogleSignInAccount account = task.getResult(ApiException.class);
                googleSignInActivity.firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                setContentView(R.layout.sign_up);
                // Sign-in failed
                Log.e("TAG", "Google sign-in failed", e);
            }
        }
    }
}
