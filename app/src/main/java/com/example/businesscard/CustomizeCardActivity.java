package com.example.businesscard;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;

public class CustomizeCardActivity extends AppCompatActivity {

    private FrameLayout frameLayoutColorCardIndicator;
    private EditText nameEditText;
    private float originalYPosition;
    private TextView nameTextView;
    private MaterialButton continueButton;

    private boolean cardState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_card);


        cardState = false;


        // Set the status bar color
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        frameLayoutColorCardIndicator = findViewById(R.id.frameLayoutColorCardIndicator);
        nameEditText = findViewById(R.id.nameEditText);
        nameTextView = findViewById(R.id.nameTextView);
        continueButton = findViewById(R.id.buttonContinue);


        GradientDrawable gradientDrawable = (GradientDrawable)  frameLayoutColorCardIndicator.getBackground();

        String cardColor = DataHolder.getInstance().getCardColor();
        if (cardColor != null && !cardColor.isEmpty()) {
            gradientDrawable.setColor(Color.parseColor(cardColor));
        } else {
            // Handle the null or empty case, e.g., set a default color
            gradientDrawable.setColor(Color.parseColor("#FECAEF")); // Default color
        }


//        // Animation to move the FrameLayout down to the bottom of the screen
//        frameLayoutColorCardIndicator.post(new Runnable() {
//            @Override
//            public void run() {
//                // Get the height of the screen and the frame layout
//                int screenHeight = frameLayoutColorCardIndicator.getRootView().getHeight();
//                int frameHeight = frameLayoutColorCardIndicator.getHeight();
//
//                TranslateAnimation animation = new TranslateAnimation(
//                        0,  // fromXDelta
//                        0,  // toXDelta
//                        0,  // fromYDelta (start from current position)
//                        screenHeight - frameHeight // toYDelta (move down to the bottom of the screen)
//                );
//                animation.setDuration(500); // Animation duration in milliseconds
//                animation.setFillAfter(true); // Keep the position after the animation
//                frameLayoutColorCardIndicator.startAnimation(animation);
//            }
//        });

        // Set up a listener for focus change on the EditText
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Animate the FrameLayout back to its original position when EditText gains focus
                    animateFrameLayoutToOriginalPosition();

                    continueButton.setVisibility(View.VISIBLE);

                    cardState = true;

                }
            }
        });

        frameLayoutColorCardIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                originalYPosition = frameLayoutColorCardIndicator.getY();

                if(cardState){
                    // Animate the FrameLayout down to the bottom when the activity starts
                    animateFrameLayoutToBottom();

                    continueButton.setVisibility(View.GONE);

                    nameEditText.clearFocus();
                }
            }
        });

        // Run this code after the layout is drawn to ensure we have the correct measurements
        frameLayoutColorCardIndicator.post(new Runnable() {
            @Override
            public void run() {
                originalYPosition = frameLayoutColorCardIndicator.getY();

                // Animate the FrameLayout down to the bottom when the activity starts
                animateFrameLayoutToBottom();
            }
        });

        // Set up a TextWatcher to update the TextView as the user types
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before the text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the TextView with the current text in the EditText
                nameTextView.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after the text changes
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FinalTouchActivity.class));
            }
        });


    }

    private void animateFrameLayoutToBottom() {
        // Calculate the distance to move the FrameLayout down
        int endY = frameLayoutColorCardIndicator.getRootView().getHeight() - frameLayoutColorCardIndicator.getHeight();

        // ObjectAnimator to move the FrameLayout down
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                frameLayoutColorCardIndicator,
                "y",
                originalYPosition,
                endY
        );
        animator.setDuration(500); // Set the duration of the animation
        animator.start(); // Start the animation
    }

    private void animateFrameLayoutToOriginalPosition() {
        // ObjectAnimator to move the FrameLayout back up to its original position
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                frameLayoutColorCardIndicator,
                "y",
                frameLayoutColorCardIndicator.getY(), // Current position
                originalYPosition // Original position
        );
        animator.setDuration(500); // Set the duration of the animation
        animator.start(); // Start the animation
    }
}