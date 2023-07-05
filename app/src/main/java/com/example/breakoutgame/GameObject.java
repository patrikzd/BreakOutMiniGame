package com.example.breakoutgame;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject {
    protected float leftSideXCoordinates;
    protected float topSideYCoordinates;
    protected Paint paint;
    protected int canvasHeight;
    protected int canvasWidth;

    public GameObject (){
        super();
        paint = new Paint();
    }


    public abstract void draw(Canvas canvas);

    protected double getTopSideYCoordinates() {
        return topSideYCoordinates;
    }

    protected double getLeftSideXCoordinates() {
        return leftSideXCoordinates;
    }
}