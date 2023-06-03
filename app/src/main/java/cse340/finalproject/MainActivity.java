package cse340.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends TeamExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Adjust layout to main activity appearance
        setTitle(ActivityType.HOME);
        setLeftButton(ActivityType.PROFILE);
        setRightButton(ActivityType.SETTINGS);
        // Set profile and settings button listeners
        findViewById(R.id.bottom_button_left).setOnClickListener(
                (View v) -> changeActivity(MainActivity.this, ProfileActivity.class));
        findViewById(R.id.bottom_button_right).setOnClickListener(
                (View v) -> changeActivity(MainActivity.this, SettingsActivity.class));
    }
}