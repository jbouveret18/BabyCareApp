package com.example.babycare.sing_up;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddressPage extends AppCompatActivity {
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
}
