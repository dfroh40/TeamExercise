package cse340.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mapbox.search.ResponseInfo;
import com.mapbox.search.SearchEngine;
import com.mapbox.search.SearchEngineSettings;
import com.mapbox.search.SearchOptions;
import com.mapbox.search.SearchSuggestionsCallback;
import com.mapbox.search.result.SearchSuggestion;

import java.util.List;

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
        LinearLayout optionList = findViewById(R.id.option_list);
        // Identify that have everything necessary to search
        if(!checkPermissions()){
            View v = inflater.inflate(R.layout.lacking_data_tab, optionList);
            TextView feedback = v.findViewById(R.id.feedback);
            feedback.setText(R.string.need_permissions);
        } else if(!checkExercises()){
            View v = inflater.inflate(R.layout.lacking_data_tab, optionList);
            TextView feedback = v.findViewById(R.id.feedback);
            feedback.setText(R.string.need_exercises);
        } else {
            //Setup SearchEngine
            SearchEngine searchEngine = SearchEngine.createSearchEngine(
                    new SearchEngineSettings(getString(R.string.mapbox_access_token))
            );
            // Search
            for(int exercise : exercises){
                SearchOptions options = new SearchOptions.Builder().limit(5).build();
                searchEngine.search(getString(exercise), options,
                        new SearchSuggestionsCallback(){

                            @Override
                            public void onError(Exception e) {

                            }

                            @Override
                            public void onSuggestions(List<SearchSuggestion> list, ResponseInfo responseInfo) {
                                for(SearchSuggestion suggestion : list){
                                    // Make an option tab
                                    View v = inflater.inflate(R.layout.option_tab, optionList, false);
                                    // Label with current exercise
                                    TextView label = v.findViewById(R.id.option_name);
                                    label.setText(exercise);
                                    // Placeholder for participant # because feature is not implemented
                                    TextView participants = v.findViewById(R.id.option_participants);
                                    participants.setText("0 Participants - Feature not implemented");
                                    // Label with location address
                                    TextView location = v.findViewById(R.id.option_location);
                                    location.setText(suggestion.getAddress().formattedAddress());
                                    optionList.addView(v);
                                }

                            }
                });
            }
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