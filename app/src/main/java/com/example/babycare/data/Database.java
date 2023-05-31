package com.example.babycare.data;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Database extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static Date convertDateIntoString(String string) throws ParseException {
        return simpleDateFormat.parse(string);
    }
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    public User writeNewUser(String firstName, String lastName, String email, Date birthday,long key, String password) {
        User user = new User(key, firstName,lastName,email,birthday, password);
        databaseReference.child("user").child(String.valueOf(key)).setValue(user);
        return user;
    }
    public void writeUserAddress(User user,String region, String country,String street, int postalCode){
        Address address = new Address(user.getKey(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday(),user.getPassword(),region,country,street,postalCode);
        databaseReference.child("address").child(String.valueOf(user.getKey())).setValue(address);
    }
    EditText emailInput = findViewById(R.id.emailInput);
    EditText lastNameInput = findViewById(R.id.lastNameInput);
    EditText firstNameInput = findViewById(R.id.firstNameInput);
    EditText birthdayInput = findViewById(R.id.birthdayInput);
    AutoCompleteTextView countryInput = findViewById(R.id.countries_list);
    AutoCompleteTextView regionInput = findViewById(R.id.region_list);
    EditText streetInput = findViewById(R.id.streetInput);
    EditText postalCodeInput = findViewById(R.id.postalCodeInput);
    EditText passwordInput = findViewById(R.id.passwordInput);
    EditText passwordVerifyInput = findViewById(R.id.passwordVerifyInput);
    Button addressPage = findViewById(R.id.AddressPage);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Date finalBirthday = birthday;

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

        addressPage.setOnClickListener(view -> {
            if(!isEmpty(userInformation) && passwordEnteredProperly(password,passwordVerify)){
                setContentView(R.layout.adress);
                writeNewUser(firstName,lastName,email, finalBirthday,key, password);
            } else {
                displayAlert(isEmpty(userInformation));
            }
        });
        finishSignUp.setOnClickListener(view -> {
            if(!isEmpty(addressInformation)){
                setContentView(R.layout.user_information_page);
                writeUserAddress(writeNewUser(firstName,lastName,email,finalBirthday,key,password),region,country,street,postalCode);
            } else {
                displayAlert(isEmpty(addressInformation));
            }

        });
        skip.setOnClickListener(view -> setContentView(R.layout.user_information_page));
        databaseReference.child("user").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
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

            }
        });

        databaseReference.child("address").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
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
    }
}

