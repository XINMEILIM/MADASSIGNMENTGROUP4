package com.example.madassignment4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LogFragment extends Fragment {

    private Spinner spinnerExerciseType, spinnerDuration;
    private Button btnAddLog, btnSaveLog;
    private ListView listViewLogs;
    private ArrayList<String> logEntries;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        spinnerExerciseType = view.findViewById(R.id.SpinnerDailyLogExercise);
        spinnerDuration = view.findViewById(R.id.SpinnerDailyLogDuration);
        btnAddLog = view.findViewById(R.id.BtnDailyLogAddExercise);
        btnSaveLog = view.findViewById(R.id.BtnDailyLogSave);
        listViewLogs = view.findViewById(R.id.LVDailyLog);

        logEntries = new ArrayList<>();

        btnAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLogEntry();
            }
        });

        btnSaveLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLogEntries();
            }
        });

        return view;
    }

    private void addLogEntry() {
        String exerciseType = spinnerExerciseType.getSelectedItem().toString();
        String duration = spinnerDuration.getSelectedItem().toString();

        String entry = "Exercise: " + exerciseType + ", Duration: " + duration;
        logEntries.add(entry);

        // Update ListView (simple implementation, adapter can be improved)
        listViewLogs.setAdapter(new android.widget.ArrayAdapter<>(
                getContext(), android.R.layout.simple_list_item_1, logEntries
        ));

        Toast.makeText(getContext(), "Log added!", Toast.LENGTH_SHORT).show();
    }

    private void saveLogEntries() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FitnessPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> logSet = new HashSet<>(logEntries);
        editor.putStringSet("LogEntries", logSet);
        editor.apply();

        Toast.makeText(getContext(), "Logs saved!", Toast.LENGTH_SHORT).show();
    }
}