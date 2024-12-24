package com.example.madassignment4;

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

import java.util.ArrayList;
import java.util.List;

public class LogFragment extends Fragment {

    private Spinner spinnerExerciseType1, spinnerExerciseType2, spinnerDuration1, spinnerDuration2;
    private Button btnAddExercise1, btnAddExercise2, btnSave;
    private ListView listViewLogs;

    private List<String> exerciseLogList; // To store logged exercises
    private ArrayAdapter<String> listViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        // Initialize views
        spinnerExerciseType1 = view.findViewById(R.id.SpinnerDailyLogExercise);
        spinnerExerciseType2 = view.findViewById(R.id.SpinnerDailyLogExercise2);
        spinnerDuration1 = view.findViewById(R.id.SpinnerDailyLogDuration);
        spinnerDuration2 = view.findViewById(R.id.SpinnerDailyLogDuration2);

        btnAddExercise1 = view.findViewById(R.id.BtnDailyLogAddExercise);
        btnAddExercise2 = view.findViewById(R.id.BtnDailyLogAddExercise2);
        btnSave = view.findViewById(R.id.BtnDailyLogSave);

        listViewLogs = view.findViewById(R.id.LVDailyLog);

        // Initialize the exercise log
        exerciseLogList = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, exerciseLogList);
        listViewLogs.setAdapter(listViewAdapter);

        // Set up spinners
        setupSpinners();

        // Add listeners for buttons
        btnAddExercise1.setOnClickListener(v -> addExercise(spinnerExerciseType1, spinnerDuration1));
        btnAddExercise2.setOnClickListener(v -> addExercise(spinnerExerciseType2, spinnerDuration2));
        btnSave.setOnClickListener(v -> saveLog());

        return view;
    }

    private void setupSpinners() {
        // Exercise Type Spinners
        ArrayAdapter<CharSequence> exerciseAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.cardio_exercise_types, android.R.layout.simple_spinner_item);
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExerciseType1.setAdapter(exerciseAdapter);


        ArrayAdapter<CharSequence> exerciseAdapter2 = ArrayAdapter.createFromResource(
                getContext(), R.array.weight_exercise_types, android.R.layout.simple_spinner_item);
        exerciseAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExerciseType2.setAdapter(exerciseAdapter2);

        // Duration Spinners
        ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.exercise_distance, android.R.layout.simple_spinner_item);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration1.setAdapter(distanceAdapter);

        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.exercise_duration, android.R.layout.simple_spinner_item);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration2.setAdapter(durationAdapter);

        // Add a new spinner for Time Frames (if needed for filtering logs)
        Spinner timeFrameSpinner = getView().findViewById(R.id.SpinnerTimeFrame); // Example ID
        ArrayAdapter<CharSequence> timeFrameAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.time_frames, android.R.layout.simple_spinner_item);
        timeFrameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeFrameSpinner.setAdapter(timeFrameAdapter);
    }

    private void addExercise(Spinner spinnerExerciseType, Spinner spinnerDuration) {
        String exerciseType = spinnerExerciseType.getSelectedItem().toString();
        String duration = spinnerDuration.getSelectedItem().toString();

        if (exerciseType.equals("Select") || duration.equals("Select")) {
            Toast.makeText(getContext(), "Please select valid exercise type and duration", Toast.LENGTH_SHORT).show();
            return;
        }

        String log = "Exercise: " + exerciseType + " | Duration: " + duration;
        exerciseLogList.add(log);
        listViewAdapter.notifyDataSetChanged();

        Toast.makeText(getContext(), "Exercise added to log", Toast.LENGTH_SHORT).show();
    }

    private void saveLog() {
        if (exerciseLogList.isEmpty()) {
            Toast.makeText(getContext(), "No exercises to save", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save log data (implementation can vary: shared preferences, database, etc.)
        // Here, we just show a confirmation toast.
        Toast.makeText(getContext(), "Exercise log saved successfully", Toast.LENGTH_SHORT).show();
    }
}
