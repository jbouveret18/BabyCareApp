package com.example.babycare.SignInGoogleOauth2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class SignInActivity  extends AppCompatActivity {
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
                startActivityForResult(intent,1234);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignInActivity.this, Objects.requireNonNull(task1.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
