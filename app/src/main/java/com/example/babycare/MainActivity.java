
package com.example.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.sing_up.SignInActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.SignUp);
        Button googleSignInButton = findViewById(R.id.Google);
        googleSignInButton.setOnClickListener(v -> launchControllerActivity());
        button.setOnClickListener(v -> setContentView(R.layout.sign_up));
    }
    private void launchControllerActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
