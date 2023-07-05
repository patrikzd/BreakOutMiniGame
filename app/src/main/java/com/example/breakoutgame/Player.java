package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

public class Player extends GameObject{

    private float rightSideXCoordinates;
    private float bottomSideYCoordinates;
    private int totalLivesAvailable = 3;
    private boolean firstDraw = true;

    public Player(Context context) {

        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {

        if (firstDraw ){
            canvasHeight = canvas.getHeight();
            canvasWidth = canvas.getWidth();
            leftSideXCoordinates = canvasWidth / 2 - 100;
            firstDraw = false;
        }
        rightSideXCoordinates = leftSideXCoordinates + 200;

        topSideYCoordinates = canvas.getHeight() - 150;
        bottomSideYCoordinates = topSideYCoordinates + 75;

        canvas.drawRect(leftSideXCoordinates, topSideYCoordinates, rightSideXCoordinates, bottomSideYCoordinates, paint);
    }

    public void setPosition(float positionX) {
        this.leftSideXCoordinates = positionX-100;
        this.rightSideXCoordinates = this.leftSideXCoordinates + 200;
    }

    public float getRightSideXCoordinates() {
        return rightSideXCoordinates;
    }

    public float getBottomSideYCoordinates() {
        return bottomSideYCoordinates;
    }

    public void reduceLives(){
        totalLivesAvailable--;
    }

    public int getTotalLivesAvailable(){
        return totalLivesAvailable;
    }

    public void playerReset() {
        totalLivesAvailable = 3;
        leftSideXCoordinates = canvasWidth / 2 - 100;
        rightSideXCoordinates = leftSideXCoordinates + 200;
        topSideYCoordinates = canvasHeight - 150;
        bottomSideYCoordinates = topSideYCoordinates + 75;
    }
}

