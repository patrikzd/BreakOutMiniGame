package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private double rectangleWidth;
    private double rectangleHeight;
    private Paint paint;
    private float left = -1;
    private float top;

    public Player(Context context, double rectangleWidth, double rectangleHeight) {
        this.rectangleWidth = rectangleWidth;
        this.rectangleHeight = rectangleHeight;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {

        if (left == -1){
            left = (canvas.getWidth() - (float) rectangleWidth) / 2 ; // Rectangle position X
        }

        top = (canvas.getHeight() - (float) rectangleHeight) - 150f; // Rectangle position Y
        float right = left + (float) rectangleWidth; // Rectangle Width
        float bottom = top + (float) rectangleHeight; // Rectangle Height

        canvas.drawRect(left, top, right, bottom, paint);
    }

    public void update() {
    }

    public void setPosition(double positionX) {
        left = (float) positionX;
    }
}

//   android:screenOrientation="landscape"
