package com.example.babycare.sing_up;

import static com.example.babycare.data.Database.convertDateIntoString;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    public boolean passwordEnteredProperly(String password, String passwordVerifyInput){
        return password.equals(passwordVerifyInput);
    }
    public boolean listIsNotComplete(List<Object> informationList){
        for(Object information : informationList){
            if(information == null){
                return true;
            }
        }
        return false;
    }

    public void displayAlert(boolean check){
        if(check){
            return;
        }
        Toast.makeText(getApplicationContext(), "Fields not complete", Toast.LENGTH_SHORT).show();
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
            if(!listIsNotComplete(userInformation) && passwordEnteredProperly(password,passwordVerify)){
                database.writeNewUser(firstName,lastName,email, birthday,key, password);
                setContentView(R.layout.adress);
            } else {
                displayAlert(listIsNotComplete(userInformation));
            }
            database.databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

                    } else {

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

                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}