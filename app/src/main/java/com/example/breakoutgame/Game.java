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
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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
    boolean firstStart = true;

    public Game(Context context) {
        super(context);

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        initObjects();
        //Initialize player
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
                if (gameLoop.isAlive()) {
                    player.setPosition(event.getX());
                    System.out.println("hihi");
                    return true;
                } else {
                    float clickX = event.getX();
                    float clickY = event.getY();

                    // Calculate the bounds of the text on the canvas
                    float textLeft = 540 - retryTextBounds.width() / 2;
                    float textRight = 540 + retryTextBounds.width() / 2;
                    float textTop = 598 + 150 - retryTextBounds.height();
                    float textBottom = 598 + 150;


                    if (clickX >= textLeft && clickX <= textRight && clickY >= textTop && clickY <= textBottom) {
                        // Player clicked on the "Retry" text, perform the desired action here
                        // For example, you can call a method to retry the game
                        initObjects();
                        gameLoop.startLoop();
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
        drawUps(canvas);
        drawFPS(canvas);

        player.draw(canvas);
        ball.draw(canvas);
        for (BreakingBlocks breakingBlocksListArray: breakingBlocksList){
            breakingBlocksListArray.draw(canvas);
        }
    }

    public void drawUps(Canvas canvas){
        String averageUps = Double.toString(gameLoop.getAverageUps());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUps, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas){
        String averageUps = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageUps, 100, 200, paint);
    }

    public void update(Canvas canvas) {
        //Update game state
        player.update(canvas);
        ball.update(canvas);
    }

    public boolean playerLost() {
        if (player.getLivesRemaining() == 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean proceedToNextLevel(){
        if (breakingBlocksList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void resetCanvas(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        getContext();
        int color = ContextCompat.getColor(getContext(), R.color.player);
        Paint paint = new Paint();
        paint.setColor(color);
        // Clear the canvas

        // Set text properties
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);

        // Calculate the center coordinates of the canvas
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 3;

        // Draw the "Game Over" message
        canvas.drawText("Game Over", centerX, centerY, paint);

        centerY = canvas.getHeight() / 3;
        // Draw the retry and exit options
        paint.setTextSize(50);
        int yOffset = 150;
        paint.getTextBounds("Retry", 0, "Retry".length(), retryTextBounds);
        paint.getTextBounds("Exit", 0, "Exit".length(), exitTextBounds);

        canvas.drawText("Retry", centerX, centerY + yOffset, paint);
        canvas.drawText("Exit", centerX, centerY + yOffset * 2, paint);
    }

    public void initObjects(){
        player = null;
        ball = null;
        breakingBlocksList = null;
        breakingBlocksList = new ArrayList<BreakingBlocks>();
        player = new Player(getContext());

        for (int i = 1; i <= 1; i++) {
            breakingBlocksList.add(new BreakingBlocks(getContext(), player, i));
        }

        ball = new Ball(getContext(), player, breakingBlocksList);
        setFocusable(true);
    }

    public void resetCanvas2() {

        SurfaceHolder surfaceHolder = getHolder();

        initObjects();

        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this, surfaceHolder);
        gameLoop.startLoop();
    }
}