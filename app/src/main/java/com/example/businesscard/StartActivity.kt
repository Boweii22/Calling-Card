package com.example.businesscard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.ncorti.slidetoact.SlideToActView

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)  // Set the layout file


        // Find the SlideToActView by its ID
        val slideToActView = findViewById<SlideToActView>(R.id.slideToActView)

// Set the OnSlideCompleteListener for the SlideToActView




        // Set the status bar color
        window.apply {
            // Clear FLAG_TRANSLUCENT_STATUS flag:
            clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            // Add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window:
            addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            // Finally, change the color
            statusBarColor = ContextCompat.getColor(this@StartActivity, R.color.black)
        }


        // Find the RecyclerView by its ID
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Set RecyclerView properties
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = CarouselLayoutManager()  // Assuming this is a custom layout manager

        // Attach SnapHelper (for snapping effect)
        val snapHelper: SnapHelper = CarouselSnapHelper()  // Assuming this is a custom SnapHelper
        snapHelper.attachToRecyclerView(recyclerView)

        // Create a list of images
        val imageList = mutableListOf<Int>()
        imageList.add(R.drawable.card1)
        imageList.add(R.drawable.card2)
        imageList.add(R.drawable.card3)
        imageList.add(R.drawable.card4)
        imageList.add(R.drawable.card5)

        // Initialize and set the adapter for the RecyclerView
        val adapter = CarouselAdapter(imageList)
        recyclerView.adapter = adapter
    }
}

//private fun SlideToActView.onSlideCompleteListener(value: () -> Unit) {
//
//}
