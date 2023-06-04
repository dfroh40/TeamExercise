package cse340.finalproject;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        // Make layout inflater
        LayoutInflater inflater = LayoutInflater.from(mContext);
        // Populate exercises list
        LinearLayout list = findViewById(R.id.option_list);
        for(int exercise : exercises){
            // Make current view
            View v = inflater.inflate(R.layout.checkbox_tab, list, false);
            // Get exercise name
            String exerciseName = getString(exercise);
            // Set label
            TextView label = v.findViewById(R.id.checkbox_label);
            label.setText(exerciseName);
            // Get checkbox
            CheckBox box = v.findViewById(R.id.checkbox);
            // Set checkbox state
            if (getPrefs().getBoolean("m" + exerciseName, false)) {
                box.setChecked(true);
            } else {
                box.setChecked(false);
                SharedPreferences.Editor sharedPreferencesEditor = getPrefs().edit();
                sharedPreferencesEditor.putBoolean("m" + exerciseName, false);
                sharedPreferencesEditor.apply();
            }
            // Set checkbox listener
            box.setOnClickListener((View b) -> {
                SharedPreferences.Editor editor = getPrefs().edit();
                if (box.isChecked()) {
                    editor.putBoolean("m" + exerciseName, true);
                } else {
                    editor.putBoolean("m" + exerciseName, false);
                }
                editor.apply();
            });
            list.addView(v);
        }
    }
}
