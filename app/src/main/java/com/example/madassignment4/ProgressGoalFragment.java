package com.example.madassignment4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProgressGoalFragment extends Fragment {

    private Spinner spinnerTimeFrame, spinnerExerciseType, spinnerDistance, spinnerDuration;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_goal, container, false);

        spinnerTimeFrame = view.findViewById(R.id.SpinnerTimeFrame);
        spinnerExerciseType = view.findViewById(R.id.SpinnerPGDailyLogExercise);
        spinnerDistance = view.findViewById(R.id.SpinnerPGDailyLogDuration);
        spinnerDuration = view.findViewById(R.id.SpinnerPGDailyLogDuration2);
        btnSave = view.findViewById(R.id.BtnPGSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProgressGoal();
            }
        });

        return view;
    }

    private void saveProgressGoal() {
        String timeFrame = spinnerTimeFrame.getSelectedItem().toString();
        String exerciseType = spinnerExerciseType.getSelectedItem().toString();
        String distance = spinnerDistance.getSelectedItem().toString();
        String duration = spinnerDuration.getSelectedItem().toString();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FitnessPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TimeFrame", timeFrame);
        editor.putString("ExerciseType", exerciseType);
        editor.putString("Distance", distance);
        editor.putString("Duration", duration);
        editor.apply();
    }
}