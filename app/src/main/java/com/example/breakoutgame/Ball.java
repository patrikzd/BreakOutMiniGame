package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ball extends GameObject{


    private float ballSpeed = 30;
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
    public void draw(Canvas canvas) {
        if (firstDraw) {
            topY = canvas.getHeight() / 2;
            leftX = canvas.getWidth() / 2;
            firstDraw = false;
        }
        canvas.drawCircle(leftX, topY, ballRadius, paint);
    }

    @Override
    public void update(Canvas canvas) {
        double paddleWidth = player.getRightX() - player.getLeftX(); // Calculate paddle Width
        double ballHit = leftX - player.getLeftX(); // Calculate where on the paddle did the ball hit
        paint.setTextSize(50);
        canvas.drawText("Hit: " + hit, 100, 600, paint);
        canvas.drawText("leftX: " + leftX, 100, 700, paint);
        canvas.drawText("topY: " + topY, 100, 800, paint);

        if (topY > canvas.getHeight()){
            player.reduceLives();
            firstDraw = true;
            return;
        }
        for (BreakingBlocks breakingBlocksListArray: breakingBlocksList){
            if (leftX + ballRadius >= breakingBlocksListArray.getLeftX() && leftX - ballRadius <= breakingBlocksListArray.getRightX() &&
                    topY + ballRadius >= breakingBlocksListArray.getTopY() && topY - ballRadius <= breakingBlocksListArray.getBottomY()){

                BreakingBlocks var = breakingBlocksList.stream().filter(id -> id.blockNumber == breakingBlocksListArray.blockNumber).findFirst().get();
                breakingBlocksList2.add(var);
                if (topY >= breakingBlocksListArray.getBottomY()){
                    hit = false;
                }
                if (topY <= breakingBlocksListArray.getTopY()){
                    hit = true;
                }
                //breakingBlocksList.removeIf(id -> id.blockNumber == breakingBlocksListArray.blockNumber);
                //breakingBlocksList.remove(var);
                ballSpeed = 10 + breakingBlocksList2.size();
            }
        }
        paint.setTextSize(50);

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

        if (hit){
            topY = topY - ballSpeed;
        } else {
            topY = topY + ballSpeed;
        }
        leftX = leftX + ballXChanges;
    }
}