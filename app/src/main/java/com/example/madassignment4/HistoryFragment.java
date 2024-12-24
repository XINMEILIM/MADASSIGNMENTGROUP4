package com.example.madassignment4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HistoryFragment extends Fragment {

    private LinearLayout historyContainer;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyContainer = view.findViewById(R.id.historyContainer);

        // Load and display history logs
        Map<String, List<String>> groupedLogs = loadHistoryLogs();
        displayHistory(groupedLogs);

        return view;
    }

    private Map<String, List<String>> loadHistoryLogs() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FitnessPrefs", Context.MODE_PRIVATE);
        Set<String> logSet = sharedPreferences.getStringSet("LogEntries", null);
        Map<String, List<String>> groupedLogs = new HashMap<>();

        if (logSet != null) {
            for (String log : logSet) {
                String[] parts = log.split(" - "); // Assuming log format: "2024-12-01 - Exercise: Run, Duration: 30min"
                String date = parts[0];
                String entry = parts[1];

                if (!groupedLogs.containsKey(date)) {
                    groupedLogs.put(date, new ArrayList<>());
                }
                groupedLogs.get(date).add(entry);
            }
        }
        return groupedLogs;
    }

    private void displayHistory(Map<String, List<String>> groupedLogs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (Map.Entry<String, List<String>> entry : groupedLogs.entrySet()) {
            String date = entry.getKey();
            List<String> logs = entry.getValue();

            // Create a TextView for the date
            TextView dateTextView = new TextView(getContext());
            dateTextView.setText(date);
            dateTextView.setTextSize(18);
            dateTextView.setPadding(16, 16, 16, 8);

            // Add the date TextView to the container
            historyContainer.addView(dateTextView);

            // Create TextViews for each log entry
            for (String log : logs) {
                TextView logTextView = new TextView(getContext());
                logTextView.setText(log);
                logTextView.setTextSize(16);
                logTextView.setPadding(32, 8, 16, 8);

                // Add the log TextView to the container
                historyContainer.addView(logTextView);
            }
        }
    }
}