package cse340.finalproject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends TeamExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup appearance
        setTitle(ActivityType.SETTINGS);
        setLeftButton(ActivityType.HOME);
        setRightButton(ActivityType.PROFILE);
        // Set home and profile button listeners
        findViewById(R.id.bottom_button_left).setOnClickListener(
                (View v) -> changeActivity(SettingsActivity.this, MainActivity.class));
        findViewById(R.id.bottom_button_right).setOnClickListener(
                (View v) -> changeActivity(SettingsActivity.this, ProfileActivity.class));
    }
}
