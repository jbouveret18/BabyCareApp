package com.example.babycare.home_page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.sign_in.GoogleSignInActivity;
import com.example.babycare.sign_in.ManualSignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123; // Arbitrary request code for Google Sign-In

    private GoogleSignInActivity googleSignInActivity;
    private Button signUpButton;
    private Button googleSignInButton;
    private Button signInButton;
    private Button facebookSignInButton;
    private Button appleSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleSignInActivity = new GoogleSignInActivity();
        signUpButton = findViewById(R.id.SignUp);
        googleSignInButton = findViewById(R.id.Google);
        signInButton = findViewById(R.id.SignIn);
        facebookSignInButton = findViewById(R.id.Facebook);
        appleSignInButton = findViewById(R.id.Apple);

        googleSignInButton.setOnClickListener(v -> {
            GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleSignInClient client = GoogleSignIn.getClient(this, options);
            Intent intent = client.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        });

        facebookSignInButton.setOnClickListener(v -> {
            Intent homePageIntent1 = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(homePageIntent1);
        });

        appleSignInButton.setOnClickListener(v -> {
            Intent homePageIntent2 = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(homePageIntent2);
        });

        signUpButton.setOnClickListener(v -> {
            Intent signUpIntent = new Intent(MainActivity.this, GoogleSignInActivity.class);
            startActivity(signUpIntent);
        });

        signInButton.setOnClickListener(view -> {
            setContentView(R.layout.sign_in);
            Intent manualSignInIntent = new Intent(MainActivity.this, ManualSignInActivity.class);
            startActivity(manualSignInIntent);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
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
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String email = account.getEmail();
            String firstName = account.getGivenName();
            String lastName = account.getFamilyName();
            Intent homePage = new Intent(MainActivity.this,HomePageActivity.class);
            startActivity(homePage);
            // Continue with your desired logic
        } else {
            // Sign-in failed
            // Handle the error or display an error message to the user
        }
    }

}
