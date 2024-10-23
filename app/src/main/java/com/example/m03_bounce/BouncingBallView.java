package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class BouncingBallView extends View {

    // Declaring Ball Array and Box
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final Box box;

    DBClass DBtest;

    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.v("BouncingBallView", "Constructor BouncingBallView");

        // Load Box
        box = new Box(Color.BLUE);
        DBtest = new DBClass(context);

        // Load existing balls from the database
        List<DataModel> ALL = DBtest.findAll();
        for (DataModel one : ALL) {
            Log.w("DataModel", "Item => " + one.toString());
            balls.add(new Ball(Color.BLUE, one.getModelX(),
                    one.getModelY(), one.getModelDX(), one.getModelDY()));
        }

        this.setFocusable(true);
        this.requestFocus();
        this.setFocusableInTouchMode(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the box and balls
        box.draw(canvas);
        for (Ball b : balls) {
            b.draw(canvas);
            b.moveWithCollisionDetection(box);
        }
        this.invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        box.set(0, 0, w, h);
        Log.w("BouncingBallLog", "onSizeChanged w=" + w + " h=" + h);
    }

    // Add Balls
    public void JaredButtonPressed(int color, int x, int y, int dx, int dy) {
        Log.d("BouncingBallView BUTTON", "User tapped the button ... VIEW");

        // Adds new balls based on color, x, y, dx, dy
        balls.add(new Ball(color, x, y, dx, dy));

        Log.v("BouncingBallView BUTTON", "n...add ball to DB");
        DataModel newBall = new DataModel(0, (float) x, (float) y, (float) dx, (float) dy);
        DBtest.save(newBall);  // Save the ball to the database

        invalidate();
    }

    public void clearBalls() {
        balls.clear();
        invalidate();
    }

    // Getter for the number of balls
    public int getBallCount() {
        return balls.size();
    }

    // Getter for a specific ball
    public Ball getBall(int i) {
        if (i >= 0 && i < balls.size()) {
            return balls.get(i);
        }
        return null;
    }
}
