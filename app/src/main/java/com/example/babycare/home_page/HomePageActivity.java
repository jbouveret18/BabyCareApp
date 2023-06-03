package com.example.babycare.home_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.example.babycare.map.MapActivity;

public class HomePageActivity extends AppCompatActivity {
    Button openMap = findViewById(R.id.openMap);
    Intent openMapIntent = new Intent(HomePageActivity.this, MapActivity.class);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        openMap.setOnClickListener(view -> startActivity(openMapIntent));
    }
}
