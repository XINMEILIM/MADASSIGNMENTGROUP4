package com.example.real;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.real.Database.DatabaseHelper_module1;
import java.util.UUID;

public class HomeFragment extends Fragment {

    private RadioButton radioButtonActive, radioButtonSick, radioButtonInjured, radioButtonTakeABreak;
    private Button btnSaveHealthStatus;
    private DatabaseHelper_module1 dbHelper;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize database helper
        dbHelper = new DatabaseHelper_module1(getContext());

        // Initialize widgets
        radioButtonActive = view.findViewById(R.id.radioButton1);
        radioButtonSick = view.findViewById(R.id.radioButton2);
        radioButtonInjured = view.findViewById(R.id.radioButton4);
        radioButtonTakeABreak = view.findViewById(R.id.radioButton3);
        btnSaveHealthStatus = view.findViewById(R.id.btnSaveHealthStatus);

        // Get username from arguments or elsewhere
        String username = getArguments() != null ? getArguments().getString("username") : "DefaultUser";

        // Generate a unique user ID for the username
        userId = generateUniqueUserId(username);

        // Set up Save button click listener
        btnSaveHealthStatus.setOnClickListener(v -> saveHealthStatus());

        //test for language

        return inflater.inflate(R.layout.fragment_home, container, false);

        //return view;
    }

    private String generateUniqueUserId(String username) {
        return username + "-" + UUID.randomUUID().toString();
    }

    private void saveHealthStatus() {
        try {
            String healthStatus = null;

            // Check which RadioButton is selected
            if (radioButtonActive.isChecked()) {
                healthStatus = radioButtonActive.getText().toString();
            } else if (radioButtonSick.isChecked()) {
                healthStatus = radioButtonSick.getText().toString();
            } else if (radioButtonInjured.isChecked()) {
                healthStatus = radioButtonInjured.getText().toString();
            } else if (radioButtonTakeABreak.isChecked()) {
                healthStatus = radioButtonTakeABreak.getText().toString();
            }

            // Validate selection
            if (healthStatus == null) {
                Toast.makeText(getContext(), "Please select a health status", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save health status to database
            dbHelper.saveHealthStatus(userId, healthStatus);
            Toast.makeText(getContext(), "Health status saved successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            // Display error toast
            Toast.makeText(getContext(), "Error saving health status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
