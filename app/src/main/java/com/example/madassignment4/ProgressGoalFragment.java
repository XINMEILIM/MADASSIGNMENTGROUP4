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
import java.util.HashMap;
import java.util.List;

public class ProgressGoalFragment extends Fragment {

    private Spinner spinnerTimeFrame, spinnerExerciseType1, spinnerExerciseType2, spinnerDuration1, spinnerDuration2;
    private Button btnAddExercise1, btnAddExercise2, btnSave;
    private ListView listViewGoals;

    private HashMap<String, List<String>> progressGoals; // To save goals by time frame
    private ArrayAdapter<String> listViewAdapter;
    private ArrayList<String> goalDisplayList; // To display in the ListView

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_goal, container, false);

        // Initialize views
        spinnerTimeFrame = view.findViewById(R.id.SpinnerTimeFrame);
        spinnerExerciseType1 = view.findViewById(R.id.SpinnerPGDailyLogExercise);
        spinnerExerciseType2 = view.findViewById(R.id.SpinnerPGDailyLogExercise2);
        spinnerDuration1 = view.findViewById(R.id.SpinnerPGDailyLogDuration);
        spinnerDuration2 = view.findViewById(R.id.SpinnerPGDailyLogDuration2);

        btnAddExercise1 = view.findViewById(R.id.BtnPGDailyLogAddExercise);
        btnAddExercise2 = view.findViewById(R.id.BtnPGDailyLogAddExercise2);
        btnSave = view.findViewById(R.id.BtnPGSave);

        listViewGoals = view.findViewById(R.id.LVFG);

        // Initialize data storage
        progressGoals = new HashMap<>();
        goalDisplayList = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, goalDisplayList);
        listViewGoals.setAdapter(listViewAdapter);

        // Setup spinners
        setupSpinners();

        // Add listeners for buttons
        btnAddExercise1.setOnClickListener(v -> addExercise(spinnerExerciseType1, spinnerDuration1));
        btnAddExercise2.setOnClickListener(v -> addExercise(spinnerExerciseType2, spinnerDuration2));
        btnSave.setOnClickListener(v -> saveGoals());

        return view;
    }

    private void setupSpinners() {
        // Time Frame Spinner
        ArrayAdapter<CharSequence> timeFrameAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_frames, android.R.layout.simple_spinner_item);
        timeFrameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeFrame.setAdapter(timeFrameAdapter);

        // Exercise Type Spinners
        ArrayAdapter<CharSequence> exerciseAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.cardio_exercise_types, android.R.layout.simple_spinner_item);
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExerciseType1.setAdapter(exerciseAdapter);

        ArrayAdapter<CharSequence> exerciseAdapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.weight_exercise_types, android.R.layout.simple_spinner_item);
        exerciseAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExerciseType2.setAdapter(exerciseAdapter2);

        // Duration Spinners
        ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.exercise_distance, android.R.layout.simple_spinner_item);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration1.setAdapter(distanceAdapter);

        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.exercise_duration, android.R.layout.simple_spinner_item);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDuration2.setAdapter(durationAdapter);
    }

    private void addExercise(Spinner spinnerExerciseType, Spinner spinnerDuration) {
        String exerciseType = spinnerExerciseType.getSelectedItem().toString();
        String duration = spinnerDuration.getSelectedItem().toString();
        String timeFrame = spinnerTimeFrame.getSelectedItem().toString();

        if (timeFrame.equals("Select")) {
            Toast.makeText(getContext(), "Please select a time frame", Toast.LENGTH_SHORT).show();
            return;
        }

        String goal = timeFrame + ": " + exerciseType + " for " + duration;
        goalDisplayList.add(goal);
        listViewAdapter.notifyDataSetChanged();
    }

    private void saveGoals() {
        String timeFrame = spinnerTimeFrame.getSelectedItem().toString();
        if (timeFrame.equals("Select")) {
            Toast.makeText(getContext(), "Please select a time frame", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> goalsForTimeFrame = new ArrayList<>(goalDisplayList);
        progressGoals.put(timeFrame, goalsForTimeFrame);

        Toast.makeText(getContext(), "Goals saved successfully", Toast.LENGTH_SHORT).show();
        goalDisplayList.clear();
        listViewAdapter.notifyDataSetChanged();
    }
}
