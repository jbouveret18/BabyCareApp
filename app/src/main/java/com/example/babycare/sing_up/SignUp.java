package com.example.babycare.sing_up;

import static com.example.babycare.data.Database.convertDateIntoString;
import static com.example.babycare.sing_up.AddressPage.getAllCountries;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.data.Database;
import com.example.babycare.data.User;
import com.example.babycare.home_page.HomePageActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignUp extends AppCompatActivity {
    Intent homePageIntent = new Intent(SignUp.this, HomePageActivity.class);
    Database database = new Database();
    Switch isDoctorSwitch = findViewById(R.id.isDoctor);
    AutoCompleteTextView countryInput = findViewById(R.id.countries_list);
    List<String> countries = getAllCountries();
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countries);
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
            Toast.makeText(getApplicationContext(), "Fields not complete", Toast.LENGTH_SHORT).show();
        }
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
            User user;
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
            if(!listIsNotComplete(userInformation) && passwordEnteredProperly(password,passwordVerify)){
                if(isDoctorSwitch.isActivated()){
                    user = database.writeNewUser(true,key,firstName,lastName,email,birthday,password);
                } else {
                    user = database.writeNewUser(false,key,firstName,lastName,email,birthday,password);
                }
                setContentView(R.layout.adress);
                finishSignUp.setOnClickListener(view1 -> {
                    String country = countryInput.getText().toString();
                    countryInput.setAdapter(adapter);
                    String region = regionInput.getText().toString();
                    int postalCode = Integer.parseInt(postalCodeInput.getText().toString());
                    String street = streetInput.getText().toString();
                    List<Object> addressInformation = new ArrayList<>();
                    addressInformation.add(country);
                    addressInformation.add(region);
                    addressInformation.add(street);
                    addressInformation.add(postalCode);
                    if(!listIsNotComplete(addressInformation)){
                        database.writeUserAddress(user,region,country,street,postalCode);
                        startActivity(homePageIntent);
                    } else {
                        displayAlert(listIsNotComplete(addressInformation));
                    }
                });
                skip.setOnClickListener(view12 -> startActivity(homePageIntent));
            } else {
                displayAlert(listIsNotComplete(userInformation));
            }
        });
    }
}