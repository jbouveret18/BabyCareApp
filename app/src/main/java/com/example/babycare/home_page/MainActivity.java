package com.example.babycare.home_page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.sign_in.GoogleSignInActivity;
import com.example.babycare.sign_in.ManualSignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    GoogleSignInActivity googleSignInActivity = new GoogleSignInActivity();
    Button signUpButton = findViewById(R.id.SignUp);
    Button googleSignInButton = findViewById(R.id.Google);
    Button signInButton = findViewById(R.id.SignIn);
    Button aboutButton = findViewById(R.id.About);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        signInButton.setOnClickListener(view -> {
            setContentView(R.layout.sign_in);
            Intent manualSignInIntent = new Intent(MainActivity.this, ManualSignInActivity.class);
            startActivity(manualSignInIntent);
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
