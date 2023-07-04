package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

public class Player extends GameObject{

    private float rightX;
    private float bottomY;
    private int livesRemaining = 3;

    public Player(Context context) {

        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {

        if (leftX == 0){
            leftX = canvas.getWidth() / 2 - 100;
        }
        rightX = leftX + 200;

        topY = canvas.getHeight() - 150;
        bottomY = topY + 75;

        canvas.drawRect(leftX, topY, rightX, bottomY, paint);
    }

    @Override
    public void update(Canvas canvas) {

    }

    public void setPosition(float positionX) {
        this.leftX = positionX-100;
        this.rightX = this.leftX + 200;
    }

    public float getRightX() {
        return rightX;
    }

    public float getBottomY() {
        return bottomY;
    }

    public void reduceLives(){
        livesRemaining --;
    }

    public int getLivesRemaining(){
        return livesRemaining;
    }
}

