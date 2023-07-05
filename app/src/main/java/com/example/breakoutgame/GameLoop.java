package com.example.breakoutgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread{
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        super.run();

        //Game Loop
        Canvas canvas = null;
        while (isRunning){
            //Try to update and render game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (this){
                    if (game.hasPlayerLost()) {
                        game.initialScreen(canvas);
                    } else if (game.isLevelFinished()){
                        game.nextLevelGeneration();
                    } else{
                        game.draw(canvas);
                        game.update(canvas);
                    }

                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }finally {
                if (canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    public void startLoop() {
        isRunning = true;
        start();
    }
}