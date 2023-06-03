package cse340.finalproject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends TeamExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup appearance
        setTitle(TeamExerciseActivity.ActivityType.PROFILE);
        setLeftButton(TeamExerciseActivity.ActivityType.HOME);
        setRightButton(TeamExerciseActivity.ActivityType.SETTINGS);
        // Set home and settings button listeners
        findViewById(R.id.bottom_button_left).setOnClickListener(
                (View v) -> changeActivity(ProfileActivity.this, MainActivity.class));
        findViewById(R.id.bottom_button_right).setOnClickListener(
                (View v) -> changeActivity(ProfileActivity.this, SettingsActivity.class));
    }
}
