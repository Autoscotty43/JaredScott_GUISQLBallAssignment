package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Ball {
    private final float radius = 50; // Ball's radius
    private float x; // Ball's center (x,y)
    private float y;
    private float speedX; // Ball's speed
    private float speedY;
    private final RectF bounds;
    public final Paint paint;
    private double ax, ay; // Adjust Speeds

    // Constructor
    public Ball(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
        Random r = new Random();

        // Random position and speed
        x = radius + r.nextInt(800);
        y = radius + r.nextInt(800);
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;
    }

    // Constructor with parameters
    public Ball(int color, float x, float y, float speedX, float speedY) {
        this(color);
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void setAcc(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }

    public void moveWithCollisionDetection(Box box) {
        // Get new (x,y) position
        x += speedX;
        y += speedY;

        // Add acceleration to speed
        speedX += (float) ax;
        speedY += (float) ay;

        // Detect collision and react
        if (x + radius > box.xMax) {
            speedX = -speedX;
            x = box.xMax - radius;
        } else if (x - radius < box.xMin) {
            speedX = -speedX;
            x = box.xMin + radius;
        }
        if (y + radius > box.yMax) {
            speedY = -speedY;
            y = box.yMax - radius;
        } else if (y - radius < box.yMin) {
            speedY = -speedY;
            y = box.yMin + radius;
        }
    }

    // Draw Balls
    public void draw(Canvas canvas) {
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawOval(bounds, paint);
        canvas.drawCircle(x, y, radius, paint);
    }

    // Getter for color
    public int getColor() {
        return paint.getColor();
    }

    // Getter for X position
    public float getX() {
        return x;
    }

    // Getter for Y position
    public float getY() {
        return y;
    }

    // Getter for X speed
    public float getSpeedX() {
        return speedX;
    }

    // Getter for Y speed
    public float getSpeedY() {
        return speedY;
    }
}
