package com.frt.customer.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.frt.customer.R;


@SuppressLint("AppCompatCustomView")
public class ItemRoundRectCornerImageView extends ImageView {

    private float radius = getResources().getDimension(R.dimen._5sdp);
    private Path path;
    private RectF rect;

    public ItemRoundRectCornerImageView(Context context) {
        super(context);
        init();
    }

    public ItemRoundRectCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemRoundRectCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }



}
