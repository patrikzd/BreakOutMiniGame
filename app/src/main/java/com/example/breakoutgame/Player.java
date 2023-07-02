package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player extends GameObject{

    private float rectangleWidth;
    private float rectangleHeight;
    public float recWight; //right
    public float recHeight; //bottom

    public Player(Context context, float rectangleWidth, float rectangleHeight, float positionY, float positionX) {
        super(positionX, positionY);

        this.rectangleWidth = rectangleWidth;
        this.rectangleHeight = rectangleHeight;

        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {

        if (positionX == 0){
            positionX = (canvas.getWidth() - rectangleWidth) / 2 ;
        }

        positionY = (canvas.getHeight() -  rectangleHeight) - 150f;
        //positionX, positionY, right, bottom, paint
        recWight  = positionX + rectangleWidth; // Rectangle Width
        recHeight = positionY +  rectangleHeight; // Rectangle Height

        canvas.drawRect(positionX, positionY, recWight, recHeight, paint);
    }

    public void update() {
    }

    public void setPosition(float positionX) {
        this.positionX = positionX-100;
    }

    public float rectangleWidth() {
        return recWight;
    }
}

//   android:screenOrientation="landscape"
