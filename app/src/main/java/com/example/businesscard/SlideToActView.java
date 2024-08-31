package com.example.businesscard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class SlideToActView extends View {

    // Listener interface
    public interface OnSlideCompleteListener {
        void onSlideComplete();
    }

    private OnSlideCompleteListener listener;

    public SlideToActView(Context context) {
        super(context);
    }

    public SlideToActView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideToActView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Set the listener
    public void setOnSlideCompleteListener(OnSlideCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Detect the slide action and trigger the listener
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (listener != null) {
                listener.onSlideComplete();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}
