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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_home, container, false);

        // Database helper and connection
        FitnessDatabaseHelper dbHelper = new FitnessDatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        StringBuilder descriptionBuilder = new StringBuilder("Your TODO List : \n");
        TextView tvPGDescription = view.findViewById(R.id.TVPGDescription);

        try (Cursor cursor = dbHelper.getLatestFitnessSettingAndGoals(db)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String exerciseType = cursor.getString(cursor.getColumnIndexOrThrow("exerciseType"));
                    String attributes = cursor.getString(cursor.getColumnIndexOrThrow("attributes"));
                    descriptionBuilder.append(exerciseType).append(" - ").append(attributes).append("\n");
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvPGDescription.setText(descriptionBuilder.toString());

        // Progress bar setup
        ProgressBar progressBar = view.findViewById(R.id.PBProgressGoal);
        progressBar.setMax(100);

        double totalGoals = 0, totalLogs = 0;

        try (Cursor cursor2 = dbHelper.getLatestFitnessSettingAndGoals(db)) {
            if (cursor2 != null && cursor2.moveToFirst()) {
                do {
                    String attributes = cursor2.getString(cursor2.getColumnIndexOrThrow("attributes"));
                    if (attributes != null) {
                        String[] parts = attributes.split(" ");
                        totalGoals += Double.parseDouble(parts[0]); // Parse carefully
                    }
                } while (cursor2.moveToNext());
            }
        }

        try (Cursor logCursor = db.rawQuery("SELECT attributes FROM " + FitnessDatabaseHelper.TABLE_EXERCISE_LOG + " WHERE fitnessID = (SELECT MAX(fitnessID) FROM " + FitnessDatabaseHelper.TABLE_FITNESS_SETTING + ")", null)) {
            if (logCursor != null && logCursor.moveToFirst()) {
                do {
                    String attributes = logCursor.getString(logCursor.getColumnIndexOrThrow("attributes"));
                    if (attributes != null) {
                        String[] parts = attributes.split(" ");
                        totalLogs += Double.parseDouble(parts[0]);
                    }
                } while (logCursor.moveToNext());
            }
        }

        int progressPercentage = totalGoals > 0 ? (int) ((totalLogs / totalGoals) * 100) : 0;
        progressBar.setProgress(progressPercentage);

        //Toast.makeText(getContext(), "Progress: " + progressPercentage + "%", Toast.LENGTH_SHORT).show();

        // Button listeners
        view.findViewById(R.id.BtnProgressGoal).setOnClickListener(v -> openFragment(new ProgressGoalFragment()));
        view.findViewById(R.id.BtnLog).setOnClickListener(v -> openFragment(new LogFragment()));
        view.findViewById(R.id.BtnHistory).setOnClickListener(v -> openFragment(new HistoryFragment()));

        db.close();

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
}
