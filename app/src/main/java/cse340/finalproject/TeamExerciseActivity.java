package cse340.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public abstract class TeamExerciseActivity extends AppCompatActivity {

    /** Values corresponding to which TeamExercise activity is being run */
    protected enum ActivityType {
        HOME(0), PROFILE(1), SETTINGS(2);

        private int value;
        private ActivityType(int val) {
            this.value = val;
        }
    };

    /* Values for setting the title text */
    private final int[] titles = {R.string.main_title,
            R.string.profile_title, R.string.settings_title};

    /* Values for setting the button images */
    private final int[] buttonDrawables = {R.drawable.home,
            R.drawable.account_edit, R.drawable.settings};

    /* Values for setting the button content description */
    private final int[] buttonDescriptions = {R.string.home_button,
            R.string.profile_button, R.string.settings_button};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Used to setup the title
     * @param type The type of Activity being run
     */
    protected void setTitle(ActivityType type){
        TextView title = findViewById(R.id.title_text);
        title.setText(titles[type.value]);
    }

    /**
     * Helper method for setting bottom buttons
     * @param type The type of Activity the button will change to
     * @param buttonId The id corresponding to button
     */
    private void setBottomButton(ActivityType type, int buttonId){
        ImageButton button = findViewById(buttonId);
        button.setImageDrawable(getDrawable(buttonDrawables[type.value]));
        button.setContentDescription(getString(buttonDescriptions[type.value]));
    }

    /**
     * Used to setup the left button
     * @param type The type of Activity the button will change to
     */
    protected void setLeftButton(ActivityType type){
        setBottomButton(type, R.id.bottom_button_left);
    }

    /**
     * Used to setup the right button
     * @param type The type of Activity the button will change to
     */
    protected void setRightButton(ActivityType type){
        setBottomButton(type, R.id.bottom_button_right);
    }

    /**
     * Used to change between activities. Used to retain shared attributes.
     * @param packageContext A Context of the application using the method
     * @param cls The component class to be used for the Intent
     */
    protected void changeActivity(android.content.Context packageContext, Class<?> cls){
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
    }
}
