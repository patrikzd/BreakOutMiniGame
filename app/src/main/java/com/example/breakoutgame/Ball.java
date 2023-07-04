package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Ball extends GameObject{


    private float ballSpeed = 30;
    private final Player player;
    private final float ballRadius = 30;
    private boolean ballUpwardMovement = false;
    private boolean firstDraw = true;
    private float ballXChanges = 0;
    private List<BreakingBlocks> breakingBlocksList = new ArrayList<BreakingBlocks>();
    private List<BreakingBlocks> breakingBlocksList2 = new ArrayList<BreakingBlocks>();

    public Ball(Context context, Player player, List<BreakingBlocks> breakingBlocksList) {
        super();
        this.player = player;
        this.breakingBlocksList = breakingBlocksList;

        int color = Color.GREEN;
        paint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        if (firstDraw) {
            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();
            topY = canvasHeight / 2;
            leftX = canvasWidth / 2;
            firstDraw = false;
        }
        canvas.drawCircle(leftX, topY, ballRadius, paint);
    }

    public void update(Canvas canvas) {
        paint.setTextSize(50);
        canvas.drawText("Lives Remaining: " + player.getLivesRemaining(), 0, 100, paint);
        canvas.drawText("Bricks Destroyed: " + breakingBlocksList2.size(), canvasWidth/2, 100, paint);


        if (checkIfBallIsLost()) {
            return;
        }
        checkIfBallHitBlocks();
        setBallMovement();
        changeBallMovementCoordinates();

    }
    private boolean checkIfBallIsLost() {
        if (topY > canvasHeight){
            player.reduceLives();
            firstDraw = true;
            ballXChanges = 0;
            return true;
        }
        return false;
    }
    private void checkIfBallHitBlocks() {
        for (BreakingBlocks breakingBlocksListArray: breakingBlocksList){
            if (leftX + ballRadius >= breakingBlocksListArray.getLeftX() && leftX - ballRadius <= breakingBlocksListArray.getRightX() &&
                    topY + ballRadius >= breakingBlocksListArray.getTopY() && topY - ballRadius <= breakingBlocksListArray.getBottomY()){

                BreakingBlocks var = breakingBlocksList.stream().filter(id -> id.blockNumber == breakingBlocksListArray.blockNumber).findFirst().get();
                breakingBlocksList2.add(var);
                if (topY >= breakingBlocksListArray.getBottomY()){
                    ballUpwardMovement = false;
                }
                if (topY <= breakingBlocksListArray.getTopY()){
                    ballUpwardMovement = true;
                }
                ballSpeed = 30 + breakingBlocksList2.size(); //Increase ball speed
            }
        }
        breakingBlocksList.removeAll(breakingBlocksList2);
    }
    private void setBallMovement(){
        double paddleWidth = player.getRightX() - player.getLeftX(); // Calculate paddle Width
        double ballHit = leftX - player.getLeftX(); // Calculate where on the paddle did the ball hit

        if (leftX + ballRadius >= player.getLeftX() && leftX - ballRadius <= player.getRightX() &&
                topY + ballRadius >= player.getTopY() && topY - ballRadius <= player.getBottomY()){
            ballUpwardMovement = true;
            if ((ballHit - paddleWidth / 2 <= 20) && ((ballHit - paddleWidth / 2) >= -20))
            {
                ballXChanges = 0;
            }else if (ballHit > paddleWidth / 2) {
                ballXChanges = (float) ((ballHit - paddleWidth/2) * 0.1) ;
            } else {
                ballXChanges = (float) (-(paddleWidth/2 - ballHit)*0.1);
            }
        } else if ((topY - ballRadius <= 0 && leftX - ballRadius <= 0) ||
                (leftX + ballRadius >= canvasWidth && topY - ballRadius <= 0) ||
                (leftX - ballRadius <= 0 && topY + ballRadius >= canvasHeight) ||
                (leftX + ballRadius >= canvasWidth && topY + ballRadius >= canvasHeight) ||
                (topY - ballRadius <= 0)) {
            ballUpwardMovement = false;
        }
    }
    private void changeBallMovementCoordinates(){
        if ((leftX - ballRadius <= 0) || (leftX + ballRadius >= canvasWidth)) {
            ballXChanges = -ballXChanges;
        }
        if (ballUpwardMovement){
            topY = topY - ballSpeed;
        } else {
            topY = topY + ballSpeed;
        }
        leftX = leftX + ballXChanges;
    }
    public void resetBall() {
        breakingBlocksList2 = new ArrayList<BreakingBlocks>();
        topY = canvasHeight / 2;
        leftX = canvasWidth / 2;
        ballSpeed = 30;
        ballXChanges = 0;
    }
}