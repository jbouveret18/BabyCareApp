package com.example.babycare.sing_up;

import static com.example.babycare.data.Database.convertDateIntoString;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.dashboard.Dashboard;
import com.example.babycare.data.Database;
import com.example.babycare.data.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignUp extends AppCompatActivity {
    Intent homePageIntent;
    Database database;
    Switch isDoctorSwitch;


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

    public void displayAlert(boolean check) {
        if (check) {
            Toast.makeText(getApplicationContext(), "Fields not complete", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // Initialize views
        homePageIntent = new Intent(SignUp.this, Dashboard.class);
        database = new Database();
        isDoctorSwitch = findViewById(R.id.isDoctor);

        EditText emailInput = findViewById(R.id.emailInput);
        EditText lastNameInput = findViewById(R.id.lastNameInput);
        EditText firstNameInput = findViewById(R.id.firstNameInput);
        EditText birthdayInput = findViewById(R.id.birthdayInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        EditText passwordVerifyInput = findViewById(R.id.passwordVerifyInput);
        Button addressPage = findViewById(R.id.addressPage);
        addressPage.setOnClickListener(view -> {
            User user;
            String firstName = firstNameInput.getText().toString();
            String lastName = lastNameInput.getText().toString();
            String birthdayUnformatted = birthdayInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String passwordVerify = passwordVerifyInput.getText().toString();
            displayAlert(passwordEnteredProperly(password, passwordVerify));
            long key = User.generatePrimaryKey();
            Date birthday;
            try {
                birthday = convertDateIntoString(birthdayUnformatted);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            List<Object> userInformation = new ArrayList<>();
            userInformation.add(firstName);
            userInformation.add(lastName);
            userInformation.add(email);
            userInformation.add(birthdayUnformatted);
            userInformation.add(password);
            userInformation.add(passwordVerify);
            if (!listIsNotComplete(userInformation) && passwordEnteredProperly(password, passwordVerify)) {
                if (isDoctorSwitch.isActivated()) {
                    user = database.writeNewUser(true, key, firstName, lastName, email, birthday, password);
                } else {
                    user = database.writeNewUser(false, key, firstName, lastName, email, birthday, password);
                }
            } else {
                displayAlert(listIsNotComplete(userInformation));
            }
        });
    }
}
