package com.example.babycare.welcome;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;

public class Welcome extends AppCompatActivity {

    Animation welcome;
    ImageView image;
//    TextView logo;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Animations
        welcome = AnimationUtils.loadAnimation(this, R.anim.welcome_animation);

        //Hooks
        image = findViewById(R.id.imageView);

        image.setAnimation(welcome);
    }
}
