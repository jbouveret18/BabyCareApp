package com.example.babycare.sing_up;

import static com.example.babycare.data.Database.convertDateIntoString;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.data.Database;
import com.example.babycare.data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignUp extends AppCompatActivity {
    Database database = new Database();
    AutoCompleteTextView countryInput = findViewById(R.id.countries_list);
    AutoCompleteTextView regionInput = findViewById(R.id.region_list);
    EditText streetInput = findViewById(R.id.streetInput);
    EditText postalCodeInput = findViewById(R.id.postalCodeInput);
    Button finishSignUp = findViewById(R.id.FinishSignUp);
    Button skip = findViewById(R.id.skip);
    EditText firstNameDisplay = findViewById(R.id.firstNameDisplay);
    EditText lastNameDisplay = findViewById(R.id.lastNameDisplay);
    EditText emailDisplay = findViewById(R.id.emailDisplay);
    EditText birthdayDisplay = findViewById(R.id.birthdayDisplay);
    EditText countryDisplay = findViewById(R.id.countryDisplay);
    EditText regionDisplay = findViewById(R.id.regionDisplay);
    EditText streetDisplay = findViewById(R.id.streetDisplay);
    EditText postalCodeDisplay = findViewById(R.id.postalCodeDisplay);

    public boolean passwordEnteredProperly(String password, String passwordVerifyInput){
        return password.equals(passwordVerifyInput);
    }
    public boolean isEmpty(List<Object> informationList){
        for(Object information : informationList){
            if(information == null){
                return true;
            }
        }
        return false;
    }

    public void displayAlert(boolean check){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input error");
        builder.setMessage("All the fields haven't been entered properly");
        AlertDialog dialog = builder.create();
        if(check){
            return;
        }
        dialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        EditText emailInput = findViewById(R.id.emailInput);
        EditText lastNameInput = findViewById(R.id.lastNameInput);
        EditText firstNameInput = findViewById(R.id.firstNameInput);
        EditText birthdayInput = findViewById(R.id.birthdayInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        EditText passwordVerifyInput = findViewById(R.id.passwordVerifyInput);
        Button addressPage = findViewById(R.id.addressPage);
        addressPage.setOnClickListener(view -> {
            String country = countryInput.getText().toString();
            String region = regionInput.getText().toString();
            int postalCode = Integer.parseInt(postalCodeInput.getText().toString());
            String street = streetInput.getText().toString();
            setContentView(R.layout.sign_up);
            String firstName = firstNameInput.getText().toString();
            String lastName = lastNameInput.getText().toString();
            String birthdayUnformatted = birthdayInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String passwordVerify = passwordVerifyInput.getText().toString();
            displayAlert(passwordEnteredProperly(password,passwordVerify));
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
            List<Object> addressInformation = new ArrayList<>();
            addressInformation.add(country);
            addressInformation.add(region);
            addressInformation.add(street);
            addressInformation.add(postalCode);
            if(!isEmpty(userInformation) && passwordEnteredProperly(password,passwordVerify)){
                database.writeNewUser(firstName,lastName,email, birthday,key, password);
                setContentView(R.layout.adress);
            } else {
                displayAlert(isEmpty(userInformation));
            }
            database.databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        firstNameDisplay.setText(firstName);
                        lastNameDisplay.setText(lastName);
                        emailDisplay.setText(email);
                        birthdayDisplay.setText(birthdayUnformatted);
                    } else {
                        firstNameDisplay.setText("Not Found");
                        lastNameDisplay.setText("Not Found");
                        emailDisplay.setText("Not Found");
                        birthdayDisplay.setText("Not Found");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //TODO
                }
            });

            database.databaseReference.child("address").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        countryDisplay.setText(country);
                        regionDisplay.setText(region);
                        streetDisplay.setText(street);
                        postalCodeDisplay.setText(postalCode);
                    }
                    countryDisplay.setText("Not Found");
                    regionDisplay.setText("Not Found");
                    streetDisplay.setText("Not Found");
                    postalCodeDisplay.setText("Not Found");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}