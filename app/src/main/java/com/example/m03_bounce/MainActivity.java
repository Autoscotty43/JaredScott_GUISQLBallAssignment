package com.example.m03_bounce;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private BouncingBallView bbView;
    private int currentColor;
    private static final String PREFS_NAME = "BallPrefs";
    private static final String BALL_COUNT_KEY = "ball_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bbView = findViewById(R.id.custView);

        // SeekBar for color
        SeekBar seekbar_color = findViewById(R.id.seekBar_Color);
        View colorView = findViewById(R.id.colorView);

        // Global Color
        currentColor = Color.rgb(0, 100, 100);

        // Set initial background color
        colorView.setBackgroundColor(currentColor);

        // Seekbar with dynamic color
        seekbar_color.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int red = progress;
                int green = 100;
                int blue = 100;
                currentColor = Color.rgb(red, green, blue);
                colorView.setBackgroundColor(currentColor);
            }

            // Start and Stop Tracking
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        loadBallData();
    }

    // Button for adding a new ball
    public void onJaredButtonClick(View v) {
        Log.d("MainActivity BUTTON", "User tapped the button ... MAIN");

        // Get the values from the SeekBars
        SeekBar seekbar_x = findViewById(R.id.seekBar_X);
        SeekBar seekbar_y = findViewById(R.id.seekBar_Y);
        SeekBar seekbar_dx = findViewById(R.id.seekBar_DX);
        SeekBar seekbar_dy = findViewById(R.id.seekBar_DY);

        // Convert SeekBar progress to meaningful values
        int x = seekbar_x.getProgress();
        int y = seekbar_y.getProgress();
        int dx = seekbar_dx.getProgress() - 5;
        int dy = seekbar_dy.getProgress() - 5;

        Log.d("MainActivity BUTTON", "Color=" + currentColor + " X=" + x + " Y=" + y + " DX=" + dx + " DY=" + dy);

        // Add a new ball using the current color, x, y, dx, dy
        bbView.JaredButtonPressed(currentColor, x, y, dx, dy);

        // Save ball data to SharedPreferences
        saveBallData();
    }

    // Button for clearing balls
    public void onClearButtonClick(View v) {
        Log.d("MainActivity CLEAR BUTTON", "User tapped the Clear button ...");

        // Reset the SeekBars to default values
        SeekBar seekbar_color = findViewById(R.id.seekBar_Color);
        SeekBar seekbar_x = findViewById(R.id.seekBar_X);
        SeekBar seekbar_y = findViewById(R.id.seekBar_Y);
        SeekBar seekbar_dx = findViewById(R.id.seekBar_DX);
        SeekBar seekbar_dy = findViewById(R.id.seekBar_DY);

        seekbar_color.setProgress(0);
        seekbar_x.setProgress(0);
        seekbar_y.setProgress(0);
        seekbar_dx.setProgress(0);
        seekbar_dy.setProgress(0);

        Log.d("MainActivity CLEAR BUTTON", "SeekBars reset to default values");

        // Clear balls from the view and SharedPreferences
        bbView.clearBalls();
        clearBallData();
    }

    // Save ball data to SharedPreferences
    private void saveBallData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Store the number of balls
        editor.putInt(BALL_COUNT_KEY, bbView.getBallCount());

        // Stored individual ball data
        for (int i = 0; i < bbView.getBallCount(); i++) {
            Ball ball = bbView.getBall(i);
            editor.putInt("ball_" + i + "_color", ball.getColor());
            editor.putFloat("ball_" + i + "_x", ball.getX());
            editor.putFloat("ball_" + i + "_y", ball.getY());
            editor.putFloat("ball_" + i + "_dx", ball.getSpeedX());
            editor.putFloat("ball_" + i + "_dy", ball.getSpeedY());
        }

        editor.apply();  // Save the changes
        Log.d("MainActivity", "Ball data saved.");
    }

    // Load ball data from SharedPreferences
    private void loadBallData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int ballCount = sharedPreferences.getInt(BALL_COUNT_KEY, 0);

        for (int i = 0; i < ballCount; i++) {
            int color = sharedPreferences.getInt("ball_" + i + "_color", Color.RED);
            float x = sharedPreferences.getFloat("ball_" + i + "_x", 0);
            float y = sharedPreferences.getFloat("ball_" + i + "_y", 0);
            float dx = sharedPreferences.getFloat("ball_" + i + "_dx", 0);
            float dy = sharedPreferences.getFloat("ball_" + i + "_dy", 0);

            // Load ball to the view
            bbView.JaredButtonPressed(color, (int) x, (int) y, (int) dx, (int) dy);
        }
        Log.d("MainActivity", "Ball data loaded.");
    }

    // Clear ball data from SharedPreferences
    private void clearBallData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();  // Clear all data
        editor.apply();
        Log.d("MainActivity", "Ball data cleared.");
    }
}
