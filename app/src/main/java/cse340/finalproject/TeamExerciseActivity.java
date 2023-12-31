package cse340.finalproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

    /* Collection of all permissions used */
    protected final String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};

    /* Values for user preferred exercises */
    protected final int[] exercises = {R.string.basketball,
            R.string.baseball, R.string.football, R.string.running};

    /* Interface for accessing and modifying preference data */
    protected SharedPreferences mSharedPreferences;

    /* Interface for accessing context */
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framework);
        mContext = this;
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

    // Copied from Accessibility
    /**
     * Get the shared preferences for this activity/context based on the app package name.
     * First time through it gets this from the system.
     * @return The shared preferences for the application, or null if we were unable to get it.
     */
    protected SharedPreferences getPrefs() {
        if (mSharedPreferences == null) {
            try {
                Context context = getApplicationContext();
                mSharedPreferences = context.getSharedPreferences(
                        context.getPackageName() + ".PREFERENCES",
                        Context.MODE_PRIVATE
                );
            } catch (Exception e) { // Failed to edit shared preferences file
                showToast(R.string.shared_pref_error);
            }
        }
        return mSharedPreferences;
    }

    // Copied from Accessibility
    /** Show an error toast with the given message ID.
     * @param id The id of the error message to show in the Toast.
     */
    protected void showToast(int id) {
        Toast.makeText(this, getString(id), Toast.LENGTH_LONG).show();
    }

    // Copied from Accessibility
    /**
     * Checks whether the permission has been granted in the given context.
     * @param context The context to check self permissions.
     * @param permission The permissions to check.
     * @return True if the permissions are granted, otherwise false.
     */
    protected boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    // Copied from Accessibility
    /**
     * Show a message dialog with an OK button with the message with the particular string ID.
     * @param stringID The ID of the message from the xml file to show.
     */
    protected void showAlertDialog(int stringID, DialogInterface.OnClickListener positiveCallback) {
        new AlertDialog.Builder(mContext)
                .setMessage(getString(stringID))
                .setPositiveButton(R.string.ok, positiveCallback)
                .show();
    }
}
