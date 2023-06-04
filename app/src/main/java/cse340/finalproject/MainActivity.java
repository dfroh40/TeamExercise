package cse340.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
        // Make layout inflater
        LayoutInflater inflater = LayoutInflater.from(mContext);
        // Populate location list
        LinearLayout list = findViewById(R.id.option_list);
        // Identify that have everything necessary to search
        if(!checkPermissions()){
            View v = inflater.inflate(R.layout.lacking_data_tab, list);
            TextView feedback = v.findViewById(R.id.feedback);
            feedback.setText(R.string.need_permissions);
        } else if(!checkExercises()){
            View v = inflater.inflate(R.layout.lacking_data_tab, list);
            TextView feedback = v.findViewById(R.id.feedback);
            feedback.setText(R.string.need_exercises);
        }
    }

    /**
     * Used to identify if all necessary permissions are active
     * @return True if permissions are enabled
     */
    private boolean checkPermissions(){
        for(String permission : permissions)
            if(!isPermissionGranted(mContext, permission)) return false;
        return true;
    }

    /**
     * Used to identify that some exercises have been selected
     * @return True if an exercise is enabled
     */
    private boolean checkExercises(){
        for(int exercise : exercises)
            if(getPrefs().getBoolean("m" + getString(exercise), false)) return true;
        return false;
    }
}