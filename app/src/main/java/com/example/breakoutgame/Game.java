package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/*
    Game manages all objects in the game and is responsible for updating all states and render all objects
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private GameLoop gameLoop;
    private Context context;

    public Game(Context context) {
        super(context);

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context = context;
        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialize player
        player = new Player(context, 200,70);

        setFocusable(true);
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
                player.setPosition((double) event.getX());
                return true;
            case MotionEvent.ACTION_MOVE:
                player.setPosition((double) event.getX());
                return true;
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
    }

    public void drawUps(Canvas canvas){
        String averageUps = Double.toString(gameLoop.getAverageUps());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUps, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas){
        String averageUps = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageUps, 100, 200, paint);
    }

    public void update() {
        //Update game state
        player.update();
    }
}
