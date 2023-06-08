package com.example.babycare.sing_up;

import static com.example.babycare.data.Database.convertDateIntoString;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.dashboard.Dashboard;
import com.example.babycare.data.Database;
import com.example.babycare.data.User;
import com.example.babycare.home_page.MainActivity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class SignUp extends AppCompatActivity {
    Intent homePageIntent;
    Database database;
    Switch isDoctorSwitch;
    Button addressPage;
    EditText passwordVerifyInput;
    EditText passwordInput;
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    Button backButton;
    static User user;



    public boolean passwordEnteredProperly(String password, String passwordVerifyInput) {
        return password.equals(passwordVerifyInput);
    }

    public boolean listIsNotComplete(List<Object> informationList) {
        for (Object information : informationList) {
            if (information == null) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        backButton = findViewById(R.id.backButtonSignUp);
        emailInput = findViewById(R.id.emailInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        firstNameInput = findViewById(R.id.firstNameInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordVerifyInput = findViewById(R.id.passwordVerifyInput);
        addressPage = findViewById(R.id.addressPage);
        addressPage.setOnClickListener(view -> {
            database = new Database();
            isDoctorSwitch = findViewById(R.id.isDoctor);
            String firstName = firstNameInput.getText().toString();
            String lastName = lastNameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String passwordVerify = passwordVerifyInput.getText().toString();
            long key = User.generatePrimaryKey();
            if (isDoctorSwitch.isActivated()) {
                user = database.writeNewUser(true, key, firstName, lastName, email, password);
                startActivity(new Intent(getApplicationContext(), AddressPage.class));
            } else {
                user = database.writeNewUser(false, key, firstName, lastName, email, password);
                startActivity(new Intent(getApplicationContext(), AddressPage.class));
            }
            Log.e("Success","SignUp works");
        });


        backButton.setOnClickListener(v -> {

            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
        });
    }
}
