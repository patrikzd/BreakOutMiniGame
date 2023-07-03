package com.example.breakoutgame;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject {
    protected float leftX;
    protected float topY;
    protected Paint paint;

    public GameObject (){
        super();
        paint = new Paint();
    }


    public abstract void draw(Canvas canvas);
    public abstract void update();

    protected double getTopY() {
        return topY;
    }

    protected double getLeftX() {
        return leftX;
    }
}