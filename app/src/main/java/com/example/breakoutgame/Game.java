package com.example.breakoutgame;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/*
    Game manages all objects in the game and is responsible for updating all states and render all objects
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private Player player;
    private Ball ball;
    private GameLoop gameLoop;
    private List<BreakingBlocks> breakingBlocksList = new ArrayList<BreakingBlocks>();
    Rect retryTextBounds = new Rect();
    Rect exitTextBounds = new Rect();
    int level = 1;
    int canvasHeight;
    int canvasWidth;


    public Game(Context context) {
        super(context);

        initObjects();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //handle touch event actions
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (!hasPlayerLost()) {
                    player.setPosition(event.getX());
                    return true;
                } else{
                    float clickX = event.getX();
                    float clickY = event.getY();

                    // Calculate the bounds of the text on the canvas
                    float textLeft = canvasWidth / 2 - retryTextBounds.width() / 2;
                    float textRight = canvasWidth / 2 + retryTextBounds.width() / 2;
                    float textTop = canvasHeight / 3 + 150 - retryTextBounds.height();
                    float textBottom = canvasHeight / 3 + 150;


                    if (clickX >= textLeft && clickX <= textRight && clickY >= textTop && clickY <= textBottom) {
                        player.playerReset();
                        ball.resetBall();
                        setLevelOne();
                        createLevel();
                    } else if (clickX >= textLeft && clickX <= textRight && clickY >= textTop + 150 && clickY <= textBottom + 150) {
                        System.exit(0);
                    }
                }

        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        player.draw(canvas);
        ball.draw(canvas);
        for (BreakingBlocks breakingBlocksListArray: breakingBlocksList){
            breakingBlocksListArray.draw(canvas);
        }
    }

    public void update(Canvas canvas) {
        ball.update(canvas);
    }

    public boolean hasPlayerLost() {
        if (player.getTotalLivesAvailable() == 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean isLevelFinished(){
        if (breakingBlocksList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void initialScreen(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);

        int centerX = canvasWidth / 2;
        int centerY = canvasHeight / 3;

        // Draw the "Game Over" message
        canvas.drawText("Game Over", centerX, centerY, paint);

        centerY = canvas.getHeight() / 3;
        paint.setTextSize(50);
        int yOffset = 150;
        paint.getTextBounds("Retry", 0, "Retry".length(), retryTextBounds);
        paint.getTextBounds("Exit", 0, "Exit".length(), exitTextBounds);

        canvas.drawText("Retry", centerX, centerY + yOffset, paint);
        canvas.drawText("Exit", centerX, centerY + yOffset * 2, paint);
    }

    private void setLevelOne(){
        level = 1;
    }
    private void createLevel(){
        breakingBlocksList.clear();
        for (int i = 1; i <= 9+level; i++) {
            breakingBlocksList.add(new BreakingBlocks(getContext(), player, i));
        }
    }

    public void nextLevelGeneration(){
        player.playerReset();
        ball.resetBall();
        level++;
        createLevel();
    }

    private void initObjects(){
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        player = new Player(getContext());
        createLevel();
        ball = new Ball(getContext(), player, breakingBlocksList);
        setFocusable(true);
    }
}