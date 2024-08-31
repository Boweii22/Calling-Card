package com.example.businesscard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Find the TextViews by ID
        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);

        // Create TranslateAnimation for text1 (floating in from the left)
        TranslateAnimation animateText1 = new TranslateAnimation(
                -500, 0,  // X from, X to (from left to its position)
                0, 0      // Y from, Y to (no vertical movement)
        );
        animateText1.setDuration(1000); // 1 second
        animateText1.setFillAfter(true); // Keep the position after the animation

        // Set the animation listener to make the TextView visible before starting the animation
        animateText1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                text1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation ended
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeat
            }
        });

        // Create TranslateAnimation for text2 (floating in from the right)
        TranslateAnimation animateText2 = new TranslateAnimation(
                500, 0,   // X from, X to (from right to its position)
                0, 0      // Y from, Y to (no vertical movement)
        );
        animateText2.setDuration(1000); // 1 second
        animateText2.setFillAfter(true); // Keep the position after the animation
        animateText2.setStartOffset(1000); // Start after text1 animation finishes

        // Set the animation listener to make the TextView visible before starting the animation
        animateText2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                text2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation ended
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeat
            }
        });

        // Start the animations
        text1.startAnimation(animateText1);
        text2.startAnimation(animateText2);

        // Find the ImageView by ID
        ImageView imageView = findViewById(R.id.myImageView);

        // Create an AnimationSet to combine animations
        AnimationSet animationSet = new AnimationSet(true);

        // Create TranslateAnimation for the image (floating to its position)
        TranslateAnimation translate = new TranslateAnimation(
                0, 0,  // X from, X to (no horizontal movement)
                500, 0 // Y from (off-screen), Y to (final position)
        );
        translate.setDuration(1000); // 1 second
        translate.setFillAfter(true); // Keep the image in the new position after the animation

        // Create AlphaAnimation for the image (fading in)
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f); // From invisible to fully visible
        fadeIn.setDuration(1000); // 1 second

        // Add both animations to the AnimationSet
        animationSet.addAnimation(translate);
        animationSet.addAnimation(fadeIn);

        // Start the animation
        imageView.startAnimation(animationSet);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateActivity.class));
                finish();
            }
        });
    }
}
