package com.example.babycare.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.home_page.HomePageActivity;
import com.example.babycare.R;
import com.example.babycare.data.Database;
import com.example.babycare.data.User;
import com.example.babycare.sing_up.SignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ManualSignInActivity extends AppCompatActivity {
    Database database = new Database();
 Button loginButton = findViewById(R.id.loginButton);
 Button resetPasswordButton = findViewById(R.id.resetPasswordButton);
 Button confirmResetPasswordButton = findViewById(R.id.confirmResetPassword);

 EditText newPasswordReset = findViewById(R.id.confirmPassword);
 EditText newEmail = findViewById(R.id.confirmEmail);
 EditText newVerifyPasswordReset = findViewById(R.id.confirmVerifyPassword);

 EditText signInEmail = findViewById(R.id.signInEmailInput);
 EditText passwordInput = findViewById(R.id.SignInPasswordInput);
 Intent homePageIntent = new Intent(ManualSignInActivity.this, HomePageActivity.class);
 Intent signUpIntent = new Intent(ManualSignInActivity.this, SignUp.class);
 public boolean fieldIsEmpty(Object input){
     return input == null;
 }
    private void userCredentialsInDatabase(String email, String password) {
     Database database = new Database();
        Query query = database.databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean userExists = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null && user.getPassword().equals(password)) {
                        userExists = true;
                        break;
                    }
                }
                if (userExists) {
                    startActivity(homePageIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have an account", Toast.LENGTH_SHORT).show();
                    startActivity(signUpIntent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Data base error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void resetPassword(String email, String newPassword) {
        Query query = database.databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userId = snapshot.getKey();
                    assert userId != null;
                    database.databaseReference.child(userId);
                    database.databaseReference.child("password").setValue(newPassword)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    startActivity(homePageIntent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "You don't have an account", Toast.LENGTH_SHORT).show();
                                    startActivity(signUpIntent);
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error case
            }
        });
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        loginButton.setOnClickListener(view -> {
            String email = signInEmail.getText().toString();
            String password = passwordInput.getText().toString();
            if(!fieldIsEmpty(email)&&!fieldIsEmpty(password)){
                userCredentialsInDatabase(email,password);
            } else {
                Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_SHORT).show();

            }
        });
        resetPasswordButton.setOnClickListener(view -> {
            setContentView(R.layout.reset_password);
            String newPassword = newPasswordReset.getText().toString();
            String email = newEmail.getText().toString();
            String confirmNewPassword = newVerifyPasswordReset.getText().toString();
            if(!fieldIsEmpty(newPassword) && !fieldIsEmpty(confirmNewPassword) && newPassword.equals(confirmNewPassword)){
                confirmResetPasswordButton.setOnClickListener(view1 -> {
                    resetPassword(email,newPassword);
                    startActivity(homePageIntent);
                });
            } else {
                Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
