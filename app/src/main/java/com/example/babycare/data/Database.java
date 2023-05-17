package com.example.babycare.data;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class Database extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    public void writeNewUser(String firstName, String lastName, String email, Date birthday,long key) {
        User user = new User(key, firstName,lastName,email,birthday);
        databaseReference.child("users").child(String.valueOf(key)).setValue(user);
    }
    public void writeUserAddress(User user,String region, String country,String street, int postalCode){
        Address address = new Address(user.key, user.firstName, user.lastName, user.email, user.birthday,region,country,street,postalCode);
        databaseReference.child("address").child(String.valueOf(user.key)).setValue(address);
    }
    EditText emailInput = findViewById(R.id.emailInput);
    EditText lastNameInput = findViewById(R.id.lastNameInput);
    EditText firstNameInput = findViewById(R.id.firstNameInput);
    EditText birthdayInput = findViewById(R.id.birthdayInput);
    AutoCompleteTextView countryInput = findViewById(R.id.countries_list);
    AutoCompleteTextView regionInput = findViewById(R.id.region_list);
    EditText streetInput = findViewById(R.id.streetInput);
    EditText postalCodeInput = findViewById(R.id.postalCodeInput);
}
