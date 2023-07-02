package com.example.breakoutgame;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject {
    protected float positionY;
    protected float positionX;
    protected Paint paint;

    public GameObject (float positionX, float positionY){
        this.positionX = positionX;
        this.positionY = positionY;

        paint = new Paint();
    }

    protected static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();

    protected double getPositionX() {
        return positionX;
    }

    protected double getPositionY() {
        return positionY;
    }
}
