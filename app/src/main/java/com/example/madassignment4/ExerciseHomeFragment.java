package com.example.madassignment4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ExerciseHomeFragment extends Fragment {

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_home, container, false);

        // Calculate and update progress
        calculateAndSetProgress();

        FitnessDatabaseHelper dbHelper = new FitnessDatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.getLatestFitnessSettingAndGoals(db);

        StringBuilder descriptionBuilder = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String exerciseType = cursor.getString(cursor.getColumnIndexOrThrow("exerciseType"));
                String attributes = cursor.getString(cursor.getColumnIndexOrThrow("attributes"));

                // Format and append to the description
                descriptionBuilder.append(exerciseType)
                        .append(" - ")
                        .append(attributes)
                        .append("\n");
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();

        // Set the formatted description to the TextView
        TextView tvPGDescription = view.findViewById(R.id.TVPGDescription);
        tvPGDescription.setText(descriptionBuilder.toString());

        // Get references to buttons and progress bar
        ImageButton btnProgressGoal = view.findViewById(R.id.BtnProgressGoal);
        ImageButton btnLog = view.findViewById(R.id.BtnLog);
        ImageButton btnHistory = view.findViewById(R.id.BtnHistory);
        progressBar = view.findViewById(R.id.PBProgressGoal); // Reference to the progress bar

        // Set up button click listeners
        btnProgressGoal.setOnClickListener(v -> openFragment(new ProgressGoalFragment()));
        btnLog.setOnClickListener(v -> openFragment(new LogFragment()));
        btnHistory.setOnClickListener(v -> openFragment(new HistoryFragment()));

        return view;
    }

    // Method to replace the fragment
    private void openFragment(Fragment fragment) {
        if (getActivity() != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    // Method to calculate and set progress
    private void calculateAndSetProgress() {
        FitnessDatabaseHelper dbHelper = new FitnessDatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            // Retrieve the latest fitnessID
            Cursor latestFitnessCursor = db.rawQuery(
                    "SELECT MAX(fitnessID) AS latestFitnessID FROM " + FitnessDatabaseHelper.TABLE_FITNESS_SETTING,
                    null
            );
            int latestFitnessID = -1; // Default to -1 in case no record exists
            if (latestFitnessCursor.moveToFirst()) {
                latestFitnessID = latestFitnessCursor.getInt(0); // Retrieve the latest fitnessID
            }
            latestFitnessCursor.close();

            if (latestFitnessID == -1) {
                Toast.makeText(getContext(), "No fitness setting found.", Toast.LENGTH_SHORT).show();
                return; // Exit early if no fitnessID exists
            }

            // Query total sum of attributes in TABLE_EXERCISE_LOG for the latest fitnessID
            Cursor logCursor = db.rawQuery(
                    "SELECT SUM(CAST(attributes AS INTEGER)) AS totalAttributes " +
                            "FROM " + FitnessDatabaseHelper.TABLE_EXERCISE_LOG + " " +
                            "WHERE fitnessID = ?",
                    new String[]{String.valueOf(latestFitnessID)}
            );
            int totalLogAttributes = 0;
            if (logCursor.moveToFirst()) {
                totalLogAttributes = logCursor.getInt(0); // Get the sum of attributes from the log
            }
            logCursor.close();

            // Query total sum of attributes in TABLE_GOAL_SETTING for the latest fitnessID
            Cursor goalCursor = db.rawQuery(
                    "SELECT SUM(CAST(attributes AS INTEGER)) AS totalAttributes " +
                            "FROM " + FitnessDatabaseHelper.TABLE_GOAL_SETTING + " " +
                            "WHERE fitnessID = ?",
                    new String[]{String.valueOf(latestFitnessID)}
            );
            int totalGoalAttributes = 0;
            if (goalCursor.moveToFirst()) {
                totalGoalAttributes = goalCursor.getInt(0); // Get the sum of attributes from goals
            }
            goalCursor.close();

            // Calculate progress ratio
            int progress = (totalGoalAttributes == 0) ? 0 : (int) (((float) totalLogAttributes / totalGoalAttributes) * 100);
            progressBar.setProgress(progress); // Update the progress bar
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error calculating progress: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

}
