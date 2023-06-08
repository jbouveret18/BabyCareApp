package com.example.babycare.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.dashboard.Dashboard;
import com.example.babycare.home_page.MainActivity;
import com.example.babycare.sign_in.ManualSignInActivity;




public class CalendarActivity extends AppCompatActivity {
    private Button backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_page);
        backButton = findViewById(R.id.Back);

        backButton.setOnClickListener(v -> {

            Intent backIntent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(backIntent);
        });
    }
}
