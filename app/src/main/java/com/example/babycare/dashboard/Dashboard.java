package com.example.babycare.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babycare.HelperClasses.HomeAdapter.FeaturedAdapter.FeaturedAdapter;
import com.example.babycare.HelperClasses.HomeAdapter.FeaturedAdapter.FeaturedHelperClass;
import com.example.babycare.R;
import com.example.babycare.calendar.CalendarActivity;
import com.example.babycare.map.MapActivity;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    RecyclerView featuredRecycler;
    RecyclerView featuredRecyclerMyProfil;
    RecyclerView featuredRecyclerCategories;
    RecyclerView.Adapter adapter, adapter2;
    RelativeLayout openMap;
    RelativeLayout calendarView;
    Intent calendarIntent;
    Intent openMapIntent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);

//        featuredRecyclerMyProfil = findViewById(R.id.featured_recycler_my_profil);
        featuredRecyclerCategories = findViewById(R.id.featured_recycler_categories);


        openMap = findViewById(R.id.openMap);
        calendarView = findViewById(R.id.calendarView);
        openMap.setOnClickListener(view -> {
            openMapIntent = new Intent(getApplicationContext(), MapActivity.class);
            setContentView(R.layout.map);
        });
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarIntent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(calendarIntent);
            }
        });

        featuredRecycler();


    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

//        featuredRecyclerMyProfil.setHasFixedSize(true);
//        featuredRecyclerMyProfil.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        ArrayList<FeaturedHelperClass> featuredLocations2 = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.article_baby_alimentation, "How to feed your baby ?", "Discover what to give to your baby and what is the best for its heath."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.article_baby_sport, "Baby physical activity", "Doing your sport exercices while a baby is playing around can be quite chalenging."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.article_baby, "What does baby find funny ?", "Best jokes to tell to your baby to make him feel good"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.article_lambda_baby, "Baby IQ", "Find out if your baby is intelligent"));


//        featuredLocations2.add(new FeaturedHelperClass(R.drawable.article_baby_alimentation, "How to feed your baby ?", "Discover what to give to your baby and what is the best for its heath."));
//        featuredLocations2.add(new FeaturedHelperClass(R.drawable.article_baby_sport, "Baby physical activity", "Doing your sport exercices while a baby is playing around can be quite chalenging."));
//        featuredLocations2.add(new FeaturedHelperClass(R.drawable.article_baby, "What does baby find funny ?", "Best jokes to tell to your baby to make him feel good"));
//        featuredLocations2.add(new FeaturedHelperClass(R.drawable.article_lambda_baby, "Baby IQ", "Find out if your baby is intelligent"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);

//        adapter2 = new FeaturedAdapter(featuredLocations2);
//        featuredRecyclerMyProfil.setAdapter(adapter2);


    }
}
