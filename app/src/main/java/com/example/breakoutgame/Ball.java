package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

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
    private List<BreakingBlocks> listOfBrokenBlocks = new ArrayList<BreakingBlocks>();

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
            topSideYCoordinates = canvasHeight / 3; // ball middle Y coordinates
            leftSideXCoordinates = canvasWidth / 2; // ball middle X coordinates
            firstDraw = false;
            ballSpeed = 10;
        }
        canvas.drawCircle(leftSideXCoordinates, topSideYCoordinates, ballRadius, paint);
    }

    public void update(Canvas canvas) {
        paint.setTextSize(50);
        canvas.drawText("Lives Remaining: " + player.getTotalLivesAvailable(), 0, 100, paint);
        canvas.drawText("Bricks Destroyed: " + listOfBrokenBlocks.size(), canvasWidth/2, 100, paint);

        if (checkIfBallIsLost()) {
            return;
        }
        checkIfBallHitBlocks();
        setBallMovement();
        changeBallMovementCoordinates();

    }
    private boolean checkIfBallIsLost() {
        if (topSideYCoordinates > canvasHeight){
            player.reduceLives();
            firstDraw = true;
            ballXChanges = 0;
            return true;
        }
        return false;
    }
    private void checkIfBallHitBlocks() {
        for (BreakingBlocks breakingBlock : breakingBlocksList){
            if (leftSideXCoordinates + ballRadius >= breakingBlock.getLeftSideXCoordinates() && leftSideXCoordinates - ballRadius <= breakingBlock.getRightSideXCoordinates() &&
                    topSideYCoordinates + ballRadius >= breakingBlock.getTopSideYCoordinates() && topSideYCoordinates - ballRadius <= breakingBlock.getBottomSideYCoordinates()){

                BreakingBlocks blockToBeBroken = breakingBlocksList.stream().filter(id -> id.blockNumber == breakingBlock.blockNumber).findFirst().get();
                listOfBrokenBlocks.add(blockToBeBroken);
                if (topSideYCoordinates >= breakingBlock.getBottomSideYCoordinates()){
                    ballUpwardMovement = false;
                }
                if (topSideYCoordinates <= breakingBlock.getTopSideYCoordinates()){
                    ballUpwardMovement = true;
                }
                ballSpeed = 30 + listOfBrokenBlocks.size(); //Increase ball speed
            }
        }
        breakingBlocksList.removeAll(listOfBrokenBlocks);
    }
    private void setBallMovement(){
        double paddleWidth = player.getRightSideXCoordinates() - player.getLeftSideXCoordinates(); // Calculate paddle Width
        double ballHitCoordinates = leftSideXCoordinates - player.getLeftSideXCoordinates(); // Calculate where on the paddle did the ball hit

        if (leftSideXCoordinates + ballRadius >= player.getLeftSideXCoordinates() && leftSideXCoordinates - ballRadius <= player.getRightSideXCoordinates() &&
                topSideYCoordinates + ballRadius >= player.getTopSideYCoordinates() && topSideYCoordinates - ballRadius <= player.getBottomSideYCoordinates()){
            ballUpwardMovement = true;
            ballSpeed = 30 + listOfBrokenBlocks.size(); //Increase ball speed from initial 10
            if ((ballHitCoordinates - paddleWidth / 2 <= 20) && ((ballHitCoordinates - paddleWidth / 2) >= -20))
            {
                ballXChanges = 0;
            }else if (ballHitCoordinates > paddleWidth / 2) {
                ballXChanges = (float) ((ballHitCoordinates - paddleWidth/2) * 0.1) ;
            } else {
                ballXChanges = (float) (-(paddleWidth/2 - ballHitCoordinates)*0.1);
            }
        } else if ((topSideYCoordinates - ballRadius <= 0 && leftSideXCoordinates - ballRadius <= 0) ||
                (leftSideXCoordinates + ballRadius >= canvasWidth && topSideYCoordinates - ballRadius <= 0) ||
                (leftSideXCoordinates - ballRadius <= 0 && topSideYCoordinates + ballRadius >= canvasHeight) ||
                (leftSideXCoordinates + ballRadius >= canvasWidth && topSideYCoordinates + ballRadius >= canvasHeight) ||
                (topSideYCoordinates - ballRadius <= 0)) {
            ballUpwardMovement = false;
        }
    }
    private void changeBallMovementCoordinates(){
        if ((leftSideXCoordinates - ballRadius <= 0) || (leftSideXCoordinates + ballRadius >= canvasWidth)) {
            ballXChanges = -ballXChanges;
        }
        if (ballUpwardMovement){
            topSideYCoordinates = topSideYCoordinates - ballSpeed;
        } else {
            topSideYCoordinates = topSideYCoordinates + ballSpeed;
        }
        leftSideXCoordinates = leftSideXCoordinates + ballXChanges;
    }
    public void resetBall() {
        listOfBrokenBlocks = new ArrayList<BreakingBlocks>();
        topSideYCoordinates = canvasHeight / 2;
        leftSideXCoordinates = canvasWidth / 2;
        ballSpeed = 30;
        ballXChanges = 0;
    }
}