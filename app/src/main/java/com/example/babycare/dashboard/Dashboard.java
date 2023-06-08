package com.example.babycare.dashboard;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babycare.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.babycare.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.example.babycare.R;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    RecyclerView featuredRecycler;
    RecyclerView featuredRecyclerMyProfil;
    RecyclerView featuredRecyclerCategories;
    RecyclerView.Adapter adapter, adapter2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);

        featuredRecyclerMyProfil = findViewById(R.id.featured_recycler_my_profil);
        featuredRecyclerCategories = findViewById(R.id.featured_recycler_categories);

        featuredRecycler();
//        featuredRecycler2();

    }

    private void featuredRecycler(){

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.article_baby_alimentation, "How to feed your baby ?", "Discover what to give to your baby and what is the best for its heath."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.article_baby_sport, "Baby physical activity", "Doing your sport exercices while a baby is playing around can be quite chalenging."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.article_baby, "What does baby find funny ?", "Best jokes to tell to your baby to make him feel good"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.article_lambda_baby, "Baby IQ", "Find out if your baby is intelligent"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);


    }

//    private void featuredRecycler2(){
//        //Adapter 2
//
//        featuredRecyclerMyProfil.setHasFixedSize(true);
//        featuredRecyclerMyProfil.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//        ArrayList<FeaturedHelperClass> featuredLocations2 = new ArrayList<>();
//
//        featuredLocations2.add(new FeaturedHelperClass(R.drawable.article_baby_alimentation, "How to feed your baby ?", "Discover what to give to your baby and what is the best for its heath.", new ColorDrawable(getResources().getColor(R.color.light_pink))));
//        featuredLocations2.add(new FeaturedHelperClass(R.drawable.article_baby_sport, "Baby physical activity", "Doing your sport exercices while a baby is playing around can be quite chalenging.", new ColorDrawable(getResources().getColor(R.color.light_pink))));
//        featuredLocations2.add(new FeaturedHelperClass(R.drawable.article_baby, "What does baby find funny ?", "Best jokes to tell to your baby to make him feel good", new ColorDrawable(getResources().getColor(R.color.light_pink))));
//        featuredLocations2.add(new FeaturedHelperClass(R.drawable.article_lambda_baby, "Baby IQ", "Find out if your baby is intelligent", new ColorDrawable(getResources().getColor(R.color.light_pink))));
//
//        adapter2 = new FeaturedAdapter(featuredLocations2);
//        featuredRecyclerMyProfil.setAdapter(adapter2);
//
//
////        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
//
//
//
//    }
}
