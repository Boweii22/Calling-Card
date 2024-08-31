package com.example.businesscard;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;

public class FinalTouchActivity extends AppCompatActivity {


    private FrameLayout card_front, card_back;
    private boolean isFront = true;
    private MaterialButton continueButton;
    private MaterialButton save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_touch);

        // Set the status bar color
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        card_front = findViewById(R.id.frameLayoutColorCardIndicator);
        card_back = findViewById(R.id.frameLayoutCardDetailsForm);
        continueButton = findViewById(R.id.continue_button);
        save_button = findViewById(R.id.save_card_button);


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip_card_anim();
                continueButton.setVisibility(View.GONE);
                save_button.setVisibility(View.VISIBLE);
            }
        });

        card_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip_card_anim();
                continueButton.setVisibility(View.VISIBLE);
                save_button.setVisibility(View.GONE);
            }
        });


    }

//    private void flip_card_anim(){
//        AnimatorSet setOut = (AnimatorSet) AnimatorInflater.loadAnimator(FinalTouchActivity.this, R.animator.card_flip_out);
//        AnimatorSet setIn = (AnimatorSet) AnimatorInflater.loadAnimator(FinalTouchActivity.this, R.animator.card_flip_in);
//
//
//        setOut.setTarget(isFront ? card_front : card_back);
//        setIn.setTarget(isFront ? card_back : card_front);
//
//        setOut.start();
//        setIn.start();
//
//
//        isFront = !isFront;
//
//    }
private void flip_card_anim() {
    AnimatorSet setOut = (AnimatorSet) AnimatorInflater.loadAnimator(FinalTouchActivity.this, R.animator.card_flip_out);
    AnimatorSet setIn = (AnimatorSet) AnimatorInflater.loadAnimator(FinalTouchActivity.this, R.animator.card_flip_in);

    setOut.setTarget(isFront ? card_front : card_back);
    setIn.setTarget(isFront ? card_back : card_front);

    // Play the "out" and "in" animations with a small delay between them
    setOut.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            setIn.start();
        }
    });

    setOut.start();

    isFront = !isFront;
}

}