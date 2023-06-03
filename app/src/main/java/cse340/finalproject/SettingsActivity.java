package cse340.finalproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class SettingsActivity extends TeamExerciseActivity {

    /** Collection of all permissions used */
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};

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
        // Make layout inflater
        LayoutInflater inflater = LayoutInflater.from(mContext);
        // Populate permissions list
        LinearLayout list = findViewById(R.id.option_list);
        for(String permission : permissions){
            // Make current view
            View v = inflater.inflate(R.layout.toggle_tab, list, false);
            // Get button
            CompoundButton button = v.findViewById(R.id.toggle_button);
            // Set label
            int labelInd = permission.lastIndexOf(".");
            String label = permission.substring(labelInd+1);
            button.setText(label);
            // Set button state
            if (isPermissionGranted(mContext, permission) &&
                    getPrefs().getBoolean("m" + label, false)) {
                button.setChecked(true);
            } else {
                button.setChecked(false);
                SharedPreferences.Editor sharedPreferencesEditor = getPrefs().edit();
                sharedPreferencesEditor.putBoolean("m" + label, false);
                sharedPreferencesEditor.apply();
            }
            // Set button listener
            button.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Copied from Accessibility but with some modifications
                if(isPermissionGranted(mContext, permission)) {
                    button.setChecked(false);

                    new AlertDialog.Builder(mContext)
                            .setMessage(getString(R.string.access_needed, permission))
                            .setNegativeButton(R.string.no, null)
                            .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                                Intent settingsIntent = new Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                settingsIntent.setData(uri);
                                startActivity(settingsIntent);
                            }).show();
                }

                SharedPreferences.Editor editor = getPrefs().edit();
                if (isChecked) {
                    editor.putBoolean("m" + label, true);
                    editor.apply();
                } else {
                    if (isPermissionGranted(mContext, Manifest.permission.ACCESS_FINE_LOCATION) &&
                            isPermissionGranted(mContext, Manifest.permission.INTERNET)) {
                        showAlertDialog(R.string.permission_off_warning, null);
                    }

                    editor.putBoolean("m" + label, false);
                    editor.apply();
                }
            });
            list.addView(v);
        }
    }
}
