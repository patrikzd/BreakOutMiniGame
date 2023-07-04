package com.example.breakoutgame;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject {
    protected float leftX;
    protected float topY;
    protected Paint paint;
    protected int canvasHeight;
    protected int canvasWidth;

    public GameObject (){
        super();
        paint = new Paint();
    }


    public abstract void draw(Canvas canvas);

    protected double getTopY() {
        return topY;
    }

    protected double getLeftX() {
        return leftX;
    }
}