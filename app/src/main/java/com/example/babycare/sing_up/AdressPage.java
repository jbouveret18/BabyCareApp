package com.example.babycare.sing_up;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.babycare.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdressPage extends AppCompatActivity {
    Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        button = (Button) findViewById(R.id.AdressPage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.adress);
            }
        });
        button = (Button) findViewById(R.id.FinishSignUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.home_page);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getAllCountries());
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.countries_list);
        textView.setAdapter(adapter);
    }
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
