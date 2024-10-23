package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

// Declaring Box
public class Box {
    int xMin, xMax, yMin, yMax;
    private final Paint paint;
    private final Rect bounds;

    // Box Color
    public Box(int color) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#4da6ff"));
        bounds = new Rect();
    }

    // Box Width and Height
    public void set(int x, int y, int width, int height) {
        xMin = x;
        xMax = x + width - 1;
        yMin = y;
        yMax = y + height - 1;
        bounds.set(xMin, yMin, xMax, yMax);
    }

    // Draw Box
    public void draw(Canvas canvas) {
        canvas.drawRect(bounds, paint);
    }

    // State Color
    public void setColor(int color) {
        paint.setColor(color);
    }

    // Receive Color
    public int getColor() {
        return paint.getColor();
    }
}
