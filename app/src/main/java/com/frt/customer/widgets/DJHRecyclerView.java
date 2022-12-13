package com.frt.customer.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class DJHRecyclerView extends RecyclerView {
    public DJHRecyclerView(Context context) {
        super(context);
    }

    public DJHRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DJHRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(event);
    }
}
