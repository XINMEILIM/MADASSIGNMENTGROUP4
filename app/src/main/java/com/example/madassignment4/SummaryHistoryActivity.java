package com.example.madassignment4;

// SummaryHistoryActivity.java
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SummaryHistoryActivity extends AppCompatActivity {

    private ArrayList<Map<String, String>> historyList;

    // Declare the TextViews for each exercise type
    private TextView TVSummaryRun, TVSummaryWalk, TVSummarySwim, TVSummaryCycle,
            TVSummaryWorkout, TVSummaryWeightlift, TVSummaryOtherCardio, TVSummaryOtherWeightTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_history);

        // Get the history data passed from HistoryActivity
        historyList = (ArrayList<Map<String, String>>) getIntent().getSerializableExtra("historyList");

        // Initialize TextViews
        TVSummaryRun = findViewById(R.id.TVSummaryRun);
        TVSummaryWalk = findViewById(R.id.TVSummaryWalk);
        TVSummarySwim = findViewById(R.id.TVSummarySwim);
        TVSummaryCycle = findViewById(R.id.TVSummaryCycle);
        TVSummaryWorkout = findViewById(R.id.TVSummaryWorkout);
        TVSummaryWeightlift = findViewById(R.id.TVSummaryWeightlift);
        TVSummaryOtherCardio = findViewById(R.id.TVSummaryOtherCardio);
        TVSummaryOtherWeightTraining = findViewById(R.id.TVSummaryOtherWeightTraining);

        // Calculate totals
        calculateTotals();
    }

    private void calculateTotals() {
        int totalRun = 0;
        int totalWalk = 0;
        int totalSwim = 0;
        int totalCycle = 0;
        int totalWorkout = 0;
        int totalWeightlift = 0;
        int totalOtherCardio = 0;
        int totalOtherWeightTraining = 0;

        // Loop through the historyList to sum up the distances/durations
        for (Map<String, String> entry : historyList) {
            String exerciseType = entry.get("exerciseType");
            String value = entry.get("value"); // Assume "value" is the distance for running, swimming, etc., or duration for workouts.

            int exerciseValue = Integer.parseInt(value);  // Convert string to integer for calculation.

            switch (exerciseType) {
                case "Run":
                    totalRun += exerciseValue;
                    break;
                case "Walk":
                    totalWalk += exerciseValue;
                    break;
                case "Swim":
                    totalSwim += exerciseValue;
                    break;
                case "Cycle":
                    totalCycle += exerciseValue;
                    break;
                case "Workout":
                    totalWorkout += exerciseValue;
                    break;
                case "Weight-lift":
                    totalWeightlift += exerciseValue;
                    break;
                case "Other Cardio":
                    totalOtherCardio += exerciseValue;
                    break;
                case "Other Weight Training":
                    totalOtherWeightTraining += exerciseValue;
                    break;
            }
        }

        // Set the values in the corresponding TextViews
        TVSummaryRun.setText(totalRun + " KM");
        TVSummaryWalk.setText(totalWalk + " KM");
        TVSummarySwim.setText(totalSwim + " KM");
        TVSummaryCycle.setText(totalCycle + " KM");
        TVSummaryWorkout.setText(totalWorkout + " min");
        TVSummaryWeightlift.setText(totalWeightlift + " min");
        TVSummaryOtherCardio.setText(totalOtherCardio + " KM");
        TVSummaryOtherWeightTraining.setText(totalOtherWeightTraining + " min");
    }
}
