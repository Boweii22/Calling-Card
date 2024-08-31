package com.example.businesscard;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import org.jetbrains.annotations.NotNull;
import yuku.ambilwarna.AmbilWarnaDialog;

public class ChooseColorActivity extends AppCompatActivity {

    private static final String TAG = "ChooseColorActivity";
    private static final String[] DEFAULT_COLORS = {"#FECAEF", "#7CE0D6", "#FBB89E", "#D2E4F1", "#FFAEC1"};

    private TextView chooseColorTextView;
    private FrameLayout frameLayoutColorCardIndicator;
    private ScrollView scrollView;
    private MaterialButton continueButton;
    private String selectedCardColor;

    private final int[] viewIds = {
            R.id.viewColor1, R.id.viewColor2, R.id.viewColor3,
            R.id.viewColor4, R.id.viewColor5, R.id.viewColor6
    };

    private final int[] imageIds = {
            R.id.imageColor1, R.id.imageColor2, R.id.imageColor3,
            R.id.imageColor4, R.id.imageColor5, R.id.imageColor6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);

        // Initialize views
        chooseColorTextView = findViewById(R.id.choose_color_textview);
        frameLayoutColorCardIndicator = findViewById(R.id.frameLayoutColorCardIndicator);
        scrollView = findViewById(R.id.scrollView);
        continueButton = findViewById(R.id.buttonContinue);

        // Set initial color
        selectedCardColor = DEFAULT_COLORS[0];

        // Set up color views
        for (int i = 0; i < viewIds.length; i++) {
            final int index = i;
            findViewById(viewIds[i]).setOnClickListener(v -> onColorViewClicked(index));
        }

        // Color picker for viewColor6
        findViewById(viewIds[5]).setOnClickListener(v -> showColorPicker());

        // Set the status bar color
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        // Load and apply animations
        Animation floatInAnimation = AnimationUtils.loadAnimation(this, R.anim.float_in);
        chooseColorTextView.startAnimation(floatInAnimation);
        frameLayoutColorCardIndicator.startAnimation(floatInAnimation);
        scrollView.startAnimation(floatInAnimation);
        continueButton.startAnimation(floatInAnimation);

        continueButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CustomizeCardActivity.class);
            startActivity(intent);
            finish();
        });

        if (savedInstanceState != null) {
            selectedCardColor = savedInstanceState.getString("selectedColor", DEFAULT_COLORS[0]);
            setCardIndicatorColor();
            updateCheckedImages(getSelectedIndex());
        }
    }

    private void onColorViewClicked(int index) {
        selectedCardColor = DEFAULT_COLORS[index];
        updateCheckedImages(index);
        setCardIndicatorColor();
    }

    private void updateCheckedImages(int selectedIndex) {
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = findViewById(imageIds[i]);
            imageView.setImageResource(i == selectedIndex ? R.drawable.baseline_check : 0);
        }
    }

    private void showColorPicker() {
        AmbilWarnaDialog colorPickerDialog = new AmbilWarnaDialog(this, Color.parseColor(selectedCardColor), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Log.d(TAG, "Color Picker canceled.");
            }

            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {
                selectedCardColor = String.format("#%06X", (0xFFFFFF & color));

                // Update the color of the color picker view
                View colorView = findViewById(viewIds[5]);
                if (colorView.getBackground() instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) colorView.getBackground();
                    gradientDrawable.setColor(Color.parseColor(selectedCardColor));
                } else {
                    Log.e(TAG, "Background is not a GradientDrawable");
                }

                // Update the indicator color
                setCardIndicatorColor();

                // Set the checkmark only on the color picker view
                ((ImageView) findViewById(imageIds[5])).setImageResource(R.drawable.baseline_check);

                // Remove the checkmark from other views
                removeCheckmarksFromOtherViews();
            }
        });
        colorPickerDialog.show();
    }

    private void removeCheckmarksFromOtherViews() {
        for (int i = 0; i < imageIds.length; i++) {
            if (i != 5) { // Skip the color picker view
                ImageView imageView = findViewById(imageIds[i]);
                imageView.setImageResource(0); // Remove the checkmark
            }
        }
    }

//    private void setCardIndicatorColor() {
//        GradientDrawable gradientDrawable = (GradientDrawable) frameLayoutColorCardIndicator.getBackground();
//        if (gradientDrawable != null) {
//            gradientDrawable.setColor(Color.parseColor(selectedCardColor));
//        } else {
//            Log.e(TAG, "Background is not a GradientDrawable");
//        }
//        Log.d(TAG, "Selected color: " + selectedCardColor);
//        DataHolder.getInstance().setCardColor(selectedCardColor);
//    }

    private void setCardIndicatorColor() {
        // Get the current color of the indicator
        GradientDrawable gradientDrawable = (GradientDrawable) frameLayoutColorCardIndicator.getBackground();
        if (gradientDrawable != null) {
            int currentColor = Color.TRANSPARENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (gradientDrawable.getColor() != null) {
                    currentColor = gradientDrawable.getColor().getDefaultColor();
                }
            }

            // Parse the new selected color
            int newColor = Color.parseColor(selectedCardColor);

            // Create a color transition animation
            ObjectAnimator colorFade = ObjectAnimator.ofArgb(
                    gradientDrawable,
                    "color",
                    currentColor,
                    newColor
            );

            // Set animation duration
            colorFade.setDuration(500); // Duration in milliseconds
            colorFade.start();

            // Save the new color in the DataHolder
            DataHolder.getInstance().setCardColor(selectedCardColor);

            Log.d(TAG, "Selected color: " + selectedCardColor);
        } else {
            Log.e(TAG, "Background is not a GradientDrawable");
        }
    }


    private int getSelectedIndex() {
        for (int i = 0; i < DEFAULT_COLORS.length; i++) {
            if (DEFAULT_COLORS[i].equals(selectedCardColor)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedColor", selectedCardColor);
    }
}
