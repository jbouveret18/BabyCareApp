package com.example.babycare.sing_up;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddressPage extends SignUp{
    AutoCompleteTextView countryInput;
    List<String> countries;
    ArrayAdapter<String> adapter;
    AutoCompleteTextView regionInput;
    EditText streetInput;
    EditText postalCodeInput;
    Button finishSignUp;
    Button skip;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adress);
        countryInput = findViewById(R.id.countries_list);
        countries = getAllCountries();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countries);
        regionInput = findViewById(R.id.region_list);
        streetInput = findViewById(R.id.streetInput);
        postalCodeInput = findViewById(R.id.postalCodeInput);
        finishSignUp = findViewById(R.id.FinishSignUp);
        skip = findViewById(R.id.skip);
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
        if (!listIsNotComplete(addressInformation)) {
            //database.writeUserAddress(user, region, country, street, postalCode);
            startActivity(homePageIntent);
        } else {
            displayAlert(listIsNotComplete(addressInformation));
        }
    }
}
