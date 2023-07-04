package com.example.breakoutgame;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

public class BreakingBlocks extends GameObject{

    private final Player player;
    public int blockNumber;
    private float rightX;
    private float bottomY;

    public BreakingBlocks (Context context, Player player, int blockNumber)
    {
        super();
        this.player = player;
        this.blockNumber = blockNumber;

        int color = ContextCompat.getColor(context, R.color.enemy);
        paint.setColor(color);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void draw(Canvas canvas) {
        float canvasWidth = canvas.getWidth();
        int numRectanglesPerLine = 10; // Number of rectangles per line
        int gapWidth = 50; // Gap width between rectangles

        int totalGapWidth = gapWidth * (numRectanglesPerLine - 1);
        float rectangleWidth = (canvasWidth - totalGapWidth) / numRectanglesPerLine;

        int lineGap = 100; // Gap between the two lines
        float line1TopY = 200; // Top y-coordinate for the first line
        float line2TopY = line1TopY + lineGap; // Top y-coordinate for the second line

        if (blockNumber <= numRectanglesPerLine) {
            leftX = (blockNumber - 1) * (rectangleWidth + gapWidth);
            rightX = leftX + rectangleWidth;
            topY = line1TopY;
            bottomY = topY + 50; // Rectangle height
        } else {
            int blockNumberInLine2 = blockNumber - numRectanglesPerLine;
            leftX = (blockNumberInLine2 - 1) * (rectangleWidth + gapWidth);
            rightX = leftX + rectangleWidth;
            topY = line2TopY;
            bottomY = topY + 50; // Rectangle height
        }

        canvas.drawRect(leftX, topY, rightX, bottomY, paint);
    }

    @Override
    public void update(Canvas canvas) {

    }

    public float getRightX() {
        return rightX;
    }

    public float getBottomY() {
        return bottomY;
    }
}
