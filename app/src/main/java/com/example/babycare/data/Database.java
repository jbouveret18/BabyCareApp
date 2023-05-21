package com.example.babycare.data;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Database extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static Date convertDateIntoString(String string) throws ParseException {
        return simpleDateFormat.parse(string);
    }
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    public User writeNewUser(String firstName, String lastName, String email, Date birthday,long key) {
        User user = new User(key, firstName,lastName,email,birthday);
        databaseReference.child("user").child(String.valueOf(key)).setValue(user);
        return user;
    }
    public void writeUserAddress(User user,String region, String country,String street, int postalCode){
        Address address = new Address(user.getKey(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday(),region,country,street,postalCode);
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
    Button addressPage = findViewById(R.id.AdressPage);
    Button finishSignUp = findViewById(R.id.FinishSignUp);
    Button skip = findViewById(R.id.skip);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        databaseReference.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        long key = User.generatePrimaryKey();
        Date birthday;
        try {
            birthday = convertDateIntoString(birthdayUnformatted);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date finalBirthday = birthday;
        addressPage.setOnClickListener(view -> {
            setContentView(R.layout.adress);
            writeNewUser(firstName,lastName,email, finalBirthday,key);
        });
        finishSignUp.setOnClickListener(view -> {
            setContentView(R.layout.user_information_page);
            writeUserAddress(writeNewUser(firstName,lastName,email,finalBirthday,key),region,country,street,postalCode);
        });
        skip.setOnClickListener(view -> setContentView(R.layout.user_information_page));
    }
}

