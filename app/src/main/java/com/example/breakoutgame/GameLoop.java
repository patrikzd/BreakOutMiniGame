package com.example.breakoutgame;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.zip.Adler32;

public class GameLoop extends Thread{
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private double averageUps;
    private double averageFps;
    private static final double MAX_UPS = 60.0;
    private static final double UPS_Period = 1E+3/MAX_UPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageUps() {
        return averageUps;
    }

    public double getAverageFPS() {
        return averageFps;
    }

    @Override
    public void run() {
        super.run();

        //Declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //Game Loop
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while (isRunning){
            //Try to update and render game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (this){
                    game.draw(canvas);
                    game.update(canvas);
                    updateCount ++;

                    if (game.playerLost()) {
                        isRunning = false;
                        game.resetCanvas(canvas);
                    }

                    if (game.proceedToNextLevel()) {
                        isRunning = false;
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }finally {
                if (canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount ++;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }


            //Calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                //averageUps = updateCount / (elapsedTime / 1000);
                averageUps = updateCount / (elapsedTime/1000);
                averageFps = frameCount / (elapsedTime / 1000);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }

        if (game.playerLost()) {
            game.resetCanvas(canvas);
        }
        if (game.proceedToNextLevel()) {
            game.resetCanvas2();
        }

    }

    public void startLoop() {
        isRunning = true;
        start();
    }
}