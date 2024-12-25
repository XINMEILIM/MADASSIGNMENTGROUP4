package com.example.madassignment4;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ProgressGoalFragment extends Fragment {

    private FitnessDatabaseHelper databaseHelper;
    private ArrayList<String> exerciseEntries; // Temporarily stores goal settings
    private ArrayAdapter<String> listViewAdapter; // Adapter for the ListView
    private long fitnessID; // Tracks the fitness setting ID

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_goal, container, false);

        // Initialize database helper
        databaseHelper = new FitnessDatabaseHelper(getContext());

        // Initialize views
        Spinner spinnerTimeFrame = view.findViewById(R.id.SpinnerTimeFrame);
        Spinner spinnerExerciseType1 = view.findViewById(R.id.SpinnerPGDailyLogExercise);
        Spinner spinnerDuration1 = view.findViewById(R.id.SpinnerPGDailyLogDuration);
        Spinner spinnerExerciseType2 = view.findViewById(R.id.SpinnerPGDailyLogExercise2);
        Spinner spinnerDuration2 = view.findViewById(R.id.SpinnerPGDailyLogDuration2);
        Button btnAdd1 = view.findViewById(R.id.BtnPGDailyLogAddExercise);
        Button btnAdd2 = view.findViewById(R.id.BtnPGDailyLogAddExercise2);
        Button btnUpdate = view.findViewById(R.id.BtnPGSave); // Update button
        ListView listView = view.findViewById(R.id.LVFG);

        // Setup Spinners
        setupSpinner(spinnerTimeFrame, R.array.time_frames);
        setupSpinner(spinnerExerciseType1, R.array.cardio_exercise_types);
        setupSpinner(spinnerDuration1, R.array.exercise_distance);
        setupSpinner(spinnerExerciseType2, R.array.weight_exercise_types);
        setupSpinner(spinnerDuration2, R.array.exercise_duration);

        // Initialize ListView
        exerciseEntries = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, exerciseEntries);
        listView.setAdapter(listViewAdapter);

        // Add button listeners
        btnAdd1.setOnClickListener(v -> addToList(spinnerExerciseType1, spinnerDuration1));
        btnAdd2.setOnClickListener(v -> addToList(spinnerExerciseType2, spinnerDuration2));

        // Update button listener
        btnUpdate.setOnClickListener(v -> updateDatabase(spinnerTimeFrame));

        return view;
    }

    private void setupSpinner(Spinner spinner, int arrayResource) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                arrayResource,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void addToList(Spinner exerciseTypeSpinner, Spinner durationSpinner) {
        String exerciseType = exerciseTypeSpinner.getSelectedItem().toString();
        String duration = durationSpinner.getSelectedItem().toString();

        if (exerciseType.equals("Select exercise")) {
            Toast.makeText(getContext(), "Please select a valid exercise type", Toast.LENGTH_SHORT).show();
            return;
        }

        if (duration.equals("Select duration")) {
            Toast.makeText(getContext(), "Please select a valid duration", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add entry to the ListView
        String entry = exerciseType + " - " + duration;
        exerciseEntries.add(entry);
        listViewAdapter.notifyDataSetChanged();
    }

    private void updateDatabase(Spinner timeFrameSpinner) {
        String timeFrame = timeFrameSpinner.getSelectedItem().toString();

        if (timeFrame.equals("Select")) {
            Toast.makeText(getContext(), "Please select a valid time frame", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try {
            // Insert time frame into fitnessSetting table
            ContentValues fitnessSettingValues = new ContentValues();
            fitnessSettingValues.put("timeFrame", timeFrame);
            fitnessID = db.insert(FitnessDatabaseHelper.TABLE_FITNESS_SETTING, null, fitnessSettingValues);

            if (fitnessID == -1) {
                Toast.makeText(getContext(), "Error saving fitness setting", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert each entry from the list into goalSetting table
            for (String entry : exerciseEntries) {
                String[] parts = entry.split(" - ");
                String exerciseType = parts[0];
                String attribute = parts[1];

                ContentValues goalSettingValues = new ContentValues();
                goalSettingValues.put("fitnessID", fitnessID);
                goalSettingValues.put("exerciseType", exerciseType);
                goalSettingValues.put("attributes", attribute);

                long goalSettingId = db.insert(FitnessDatabaseHelper.TABLE_GOAL_SETTING, null, goalSettingValues);
                if (goalSettingId == -1) {
                    Toast.makeText(getContext(), "Error saving goal setting for: " + exerciseType, Toast.LENGTH_SHORT).show();
                }
            }

            Toast.makeText(getContext(), "Database updated successfully", Toast.LENGTH_SHORT).show();
            exerciseEntries.clear(); // Clear the list after saving
            listViewAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error updating database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }
}
