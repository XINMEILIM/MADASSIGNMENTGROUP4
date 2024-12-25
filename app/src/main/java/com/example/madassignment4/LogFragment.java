package com.example.madassignment4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class LogFragment extends Fragment {

    private Spinner spinnerExerciseType1, spinnerDuration1, spinnerExerciseType2, spinnerDuration2;
    private Button btnAddExercise1, btnAddExercise2, btnUpdateLog;
    private ListView lvDailyLog;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<HashMap<String, String>> activityList; // To hold exercise details
    private ArrayList<String> displayList; // To show in ListView
    private FitnessDatabaseHelper databaseHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new FitnessDatabaseHelper(getContext());
        activityList = new ArrayList<>();
        displayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        // Initialize Views
        spinnerExerciseType1 = view.findViewById(R.id.SpinnerDailyLogExercise);
        spinnerDuration1 = view.findViewById(R.id.SpinnerDailyLogDuration);
        spinnerExerciseType2 = view.findViewById(R.id.SpinnerDailyLogExercise2);
        spinnerDuration2 = view.findViewById(R.id.SpinnerDailyLogDuration2);
        btnAddExercise1 = view.findViewById(R.id.BtnDailyLogAddExercise);
        btnAddExercise2 = view.findViewById(R.id.BtnDailyLogAddExercise2);
        btnUpdateLog = view.findViewById(R.id.BtnDailyLogSave);
        lvDailyLog = view.findViewById(R.id.LVDailyLog);

        // Setup Spinners
        setupSpinner(spinnerExerciseType1, R.array.cardio_exercise_types);
        setupSpinner(spinnerDuration1, R.array.exercise_distance);
        setupSpinner(spinnerExerciseType2, R.array.weight_exercise_types);
        setupSpinner(spinnerDuration2, R.array.exercise_duration);

        // Setup ListView
        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, displayList);
        lvDailyLog.setAdapter(listAdapter);

        // Button Actions
        btnAddExercise1.setOnClickListener(v -> addActivity(spinnerExerciseType1, spinnerDuration1));
        btnAddExercise2.setOnClickListener(v -> addActivity(spinnerExerciseType2, spinnerDuration2));
        btnUpdateLog.setOnClickListener(v -> updateLog());

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

    private void addActivity(Spinner exerciseSpinner, Spinner attributeSpinner) {
        String exerciseType = exerciseSpinner.getSelectedItem().toString();
        String attribute = attributeSpinner.getSelectedItem().toString();

        if (exerciseType.equals("Select exercise") || attribute.equals("Select")) {
            Toast.makeText(getContext(), "Please select valid inputs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare activity details
        HashMap<String, String> activity = new HashMap<>();
        activity.put("ExerciseType", exerciseType);
        activity.put("Attribute", attribute);

        // Add activity to lists
        activityList.add(activity);
        displayList.add(exerciseType + ": " + attribute);
        listAdapter.notifyDataSetChanged();
    }

    private void updateLog() {
        if (activityList.isEmpty()) {
            Toast.makeText(getContext(), "No activities to save", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try {
            // Get the latest fitnessID
            int fitnessID = getLatestFitnessID(db);
            if (fitnessID == -1) {
                Toast.makeText(getContext(), "No fitness settings found", Toast.LENGTH_SHORT).show();
                return;
            }

            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            for (HashMap<String, String> activity : activityList) {
                String exerciseType = activity.get("ExerciseType");
                String attribute = activity.get("Attribute");

                // Extract numeric value from attribute
                String numericAttribute = attribute.replaceAll("[^0-9.]", "");
                if (numericAttribute.isEmpty()) numericAttribute = "0";

                String insertQuery = "INSERT INTO " + FitnessDatabaseHelper.TABLE_EXERCISE_LOG +
                        " (Date, ExerciseType, Attributes, fitnessID) VALUES ('" +
                        currentDate + "', '" +
                        exerciseType + "', '" +
                        numericAttribute + "', " +
                        fitnessID + ");";
                db.execSQL(insertQuery);
            }
            Toast.makeText(getContext(), "Activities saved successfully", Toast.LENGTH_SHORT).show();
            activityList.clear();
            displayList.clear();
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error saving activities: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private int getLatestFitnessID(SQLiteDatabase db) {
        int fitnessID = -1;
        Cursor cursor = null;

        try {
            String query = "SELECT fitnessID FROM " + FitnessDatabaseHelper.TABLE_FITNESS_SETTING + " ORDER BY fitnessID DESC LIMIT 1;";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                fitnessID = cursor.getInt(0);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error fetching fitnessID: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return fitnessID;
    }

    @Override
    public void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }
}
