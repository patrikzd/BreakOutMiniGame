package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

public class Ball extends GameObject{


    private float MAX_SPEED = 10;
    private final Player player;
    private final float ballRadius = 30;
    private boolean hit = false;
    private float ballDX;

    public Ball(Context context, float positionX, float positionY, Player player) {
        super(positionX, positionY);
        this.player = player;

        int color = ContextCompat.getColor(context, R.color.enemy);
        paint.setColor(color);
    }

    @Override
    public synchronized void draw(Canvas canvas) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        double playerx = player.getPositionX();
        double playery = player.getPositionY();

        canvas.drawCircle(positionX, positionY, ballRadius, paint);

        /*
            positionX + ballRadius >= player.getPositionX() - This condition checks if the right edge of the ball is greater than or equal to the left edge
         */

        if (positionX + ballRadius >= player.getPositionX() && positionX - ballRadius <= player.recWight &&
                positionY + ballRadius >= player.getPositionY() && positionY - ballRadius <= player.recHeight ){
            System.out.println("hoooeta");
            hit = true;
        } else if ((positionX - ballRadius <= 0 && positionY - ballRadius <= 0) ||
                (positionX + ballRadius >= canvas.getWidth() && positionY - ballRadius <= 0) ||
                (positionX - ballRadius <= 0 && positionY + ballRadius >= canvas.getHeight()) ||
                (positionX + ballRadius >= canvas.getWidth() && positionY + ballRadius >= canvas.getHeight()) ||
                (positionY - ballRadius <= 0) ||
                (positionX + ballRadius >= canvasWidth)) {
            System.out.println("hoooeta222");
            hit = false;
        }
    }

    @Override
    public void update() {

        if (hit){
            positionY = positionY - MAX_SPEED;
            positionX = positionX + 5;
        } else {
            positionY = positionY + MAX_SPEED;
        }

    }
}
