package com.example.madassignment4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Set;

public class HistorySummaryFragment extends Fragment {

    private TextView summaryTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_summary, container, false);

        summaryTextView = view.findViewById(R.id.TVHistorySummaryTitle);

        calculateSummary();

        return view;
    }

    private void calculateSummary() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FitnessPrefs", Context.MODE_PRIVATE);
        Set<String> logSet = sharedPreferences.getStringSet("LogEntries", null);

        int totalDuration = 0;

        if (logSet != null) {
            for (String log : logSet) {
                String[] parts = log.split(", Duration: ");
                String durationPart = parts[1].replace("min", "").trim();
                totalDuration += Integer.parseInt(durationPart);
            }
        }

        summaryTextView.setText("Total Duration: " + totalDuration + " min");
    }
}