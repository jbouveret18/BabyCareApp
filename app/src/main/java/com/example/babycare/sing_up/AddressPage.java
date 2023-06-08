package com.example.babycare.sing_up;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.dashboard.Dashboard;
import com.example.babycare.data.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddressPage extends AppCompatActivity{
    List<String> countries;
    ArrayAdapter<String> adapter;
    AutoCompleteTextView regionInput;
    EditText streetInput;
    EditText postalCodeInput;
    Button finishSignUp;
    Button skip;
    Database database;
    TextView countryInput;

    public static List<String> getAllCountries() {
        Locale[] locales = Locale.getAvailableLocales();
        List<String> countries = new ArrayList<>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (!country.isEmpty() && !countries.contains(country)) {
                countries.add(country);
            }
        }
        return countries;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adress);
        regionInput = findViewById(R.id.region_list);
        streetInput = findViewById(R.id.streetInput);
        postalCodeInput = findViewById(R.id.postalCodeInput);
        finishSignUp = findViewById(R.id.FinishSignUp);
        skip = findViewById(R.id.skip);
        countryInput = findViewById(R.id.countries_list);
        finishSignUp.setOnClickListener(view -> {
            String country = countryInput.getText().toString();
            String region = regionInput.getText().toString();
            int postalCode = Integer.parseInt(postalCodeInput.getText().toString());
            String street = streetInput.getText().toString();
            database.writeUserAddress(SignUp.user.isDoctor(),SignUp.user.getKey(),SignUp.user.getFirstName(),SignUp.user.getLastName(),SignUp.user.getEmail(),SignUp.user.getPassword(),region,country,street,postalCode);
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
        });
        skip.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Dashboard.class)));

    }
}
