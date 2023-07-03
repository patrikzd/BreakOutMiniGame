package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ball extends GameObject{


    private float MAX_SPEED = 20;
    private final Player player;
    private final float ballRadius = 30;
    private boolean hit = false;
    private boolean firstDraw = true;
    private float ballXChanges = 0;
    private List<BreakingBlocks> breakingBlocksList = new ArrayList<BreakingBlocks>();
    private List<BreakingBlocks> breakingBlocksList2 = new ArrayList<BreakingBlocks>();

    public Ball(Context context, Player player, List<BreakingBlocks> breakingBlocksList) {
        super();
        this.player = player;
        this.breakingBlocksList = breakingBlocksList;

        int color = ContextCompat.getColor(context, R.color.enemy);
        paint.setColor(color);
    }

    @Override
    public synchronized void draw(Canvas canvas) {
        double paddleWidth = player.getRightX() - player.getLeftX(); // Calculate paddle Width
        double ballHit = leftX - player.getLeftX(); // Calculate where on the paddle did the ball hit
        if (firstDraw) {
            topY = canvas.getHeight() / 2;
            leftX = canvas.getWidth() / 2;
            firstDraw = false;
        }
        canvas.drawCircle(leftX, topY, ballRadius, paint);

        for (BreakingBlocks breakingBlocksListArray: breakingBlocksList){
            if (leftX + ballRadius >= breakingBlocksListArray.getLeftX() && leftX - ballRadius <= breakingBlocksListArray.getRightX() &&
                    topY + ballRadius >= breakingBlocksListArray.getTopY() && topY - ballRadius <= breakingBlocksListArray.getBottomY()){

                System.out.println("aaaa");
                BreakingBlocks var = breakingBlocksList.stream().filter(id -> id.blockNumber == breakingBlocksListArray.blockNumber).findFirst().get();
                breakingBlocksList2.add(var);
                //breakingBlocksList.removeIf(id -> id.blockNumber == breakingBlocksListArray.blockNumber);
                //breakingBlocksList.remove(var);


            }
        }
        paint.setTextSize(50);
        canvas.drawText("topY: " + breakingBlocksList2.size(), 100, 800, paint);

        breakingBlocksList.removeAll(breakingBlocksList2);

        if (leftX + ballRadius >= player.getLeftX() && leftX - ballRadius <= player.getRightX() &&
                topY + ballRadius >= player.getTopY() && topY - ballRadius <= player.getBottomY()){
            hit = true;
            if ((ballHit - paddleWidth / 2 <= 20) && ((ballHit - paddleWidth / 2) >= -20))
            {
                ballXChanges = 0;
            }else if (ballHit > paddleWidth / 2) {
                ballXChanges = (float) ((ballHit - paddleWidth/2) * 0.1) ;
            } else {
                ballXChanges = (float) (-(paddleWidth/2 - ballHit)*0.1);
            }
        } else if ((topY - ballRadius <= 0 && leftX - ballRadius <= 0) ||
                (leftX + ballRadius >= canvas.getWidth() && topY - ballRadius <= 0) ||
                (leftX - ballRadius <= 0 && topY + ballRadius >= canvas.getHeight()) ||
                (leftX + ballRadius >= canvas.getWidth() && topY + ballRadius >= canvas.getHeight()) ||
                (topY - ballRadius <= 0)) {
            hit = false;
        }

        if ((leftX - ballRadius <= 0) || (leftX + ballRadius >= canvas.getWidth())) {
            ballXChanges = -ballXChanges;
        }
    }

    @Override
    public void update() {

        if (hit){
            topY = topY - MAX_SPEED;
        } else {
            topY = topY + MAX_SPEED;
        }
        leftX = leftX + ballXChanges;
    }
}