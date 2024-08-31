package com.example.businesscard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.ncorti.slidetoact.SlideToActView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarouselAdapter adapter;
    private Handler autoScrollHandler;
    private int currentPosition = 0;
    private final long delayMillis = 3000; // 3 seconds delay between each scroll


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);  // Set the layout file

        // Find the SlideToActView by its ID
        SlideToActView slideToActView = findViewById(R.id.slideToActView);

        // Set the OnSlideCompleteListener for the SlideToActView
        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NotNull SlideToActView slideToActView) {
                // Handle the slide complete action here
                // Example:
                Log.d("CreateActivity","Slide completed successfully");
//                Toast.makeText(CreateActivity.this, "Slide completed!", Toast.LENGTH_SHORT).show();
                // Optionally start another activity
                Intent intent = new Intent(CreateActivity.this, ChooseColorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set the status bar color
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        // Find the RecyclerView by its ID
        recyclerView = findViewById(R.id.recyclerView);

        // Set RecyclerView properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new CarouselLayoutManager());  // Assuming this is a custom layout manager

        // Attach SnapHelper (for snapping effect)
        SnapHelper snapHelper = new CarouselSnapHelper();  // Assuming this is a custom SnapHelper
        snapHelper.attachToRecyclerView(recyclerView);

        // Create a list of images
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.card1);
        imageList.add(R.drawable.card2);
        imageList.add(R.drawable.card3);
        imageList.add(R.drawable.card4);
        imageList.add(R.drawable.card5);

        // Initialize and set the adapter for the RecyclerView
        adapter = new CarouselAdapter(imageList);
        recyclerView.setAdapter(adapter);

        // Setup auto-scrolling
        autoScrollHandler = new Handler(Looper.getMainLooper());
        startAutoScroll();
    }

    private void startAutoScroll() {
        autoScrollHandler.postDelayed(autoScrollRunnable, delayMillis);
    }

    private Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            currentPosition++;
            if (currentPosition >= adapter.getItemCount()) {
                currentPosition = 0;
            }

            smoothScrollToPositionWithSpeed(currentPosition);
            autoScrollHandler.postDelayed(this, delayMillis);
        }
    };

    private void smoothScrollToPositionWithSpeed(int position) {
        RecyclerView.SmoothScroller smoothScroller = new CustomSmoothScroller(this);
        smoothScroller.setTargetPosition(position);
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    // Custom LinearSmoothScroller to control scroll speed
    private class CustomSmoothScroller extends LinearSmoothScroller {

        private static final float MILLISECONDS_PER_INCH = 100f; // Adjust this value to control speed

        CustomSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
        }

        @Override
        protected int calculateTimeForScrolling(int dx) {
            return Math.min(2000, super.calculateTimeForScrolling(dx)); // Max scroll duration is 2 seconds
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoScrollHandler.removeCallbacks(autoScrollRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autoScrollHandler.removeCallbacks(autoScrollRunnable);
    }
}
