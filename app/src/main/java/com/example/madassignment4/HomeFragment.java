package com.example.real;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.real.Database.DatabaseHelper;

public class HomeFragment extends Fragment {

    private TextView textViewGreeting;
    private RadioGroup radioGroupHealth;
    private Button btnSaveHealthStatus;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(getContext());

        // Find views
        textViewGreeting = view.findViewById(R.id.textViewGreeting);
        radioGroupHealth = view.findViewById(R.id.radioGroup);
        btnSaveHealthStatus = view.findViewById(R.id.btnSaveHealthStatus);

        // Set greeting text from SharedPreferences
        SharedPreferences preferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        String username = preferences.getString("user_name", "User");
        textViewGreeting.setText("Hi, " + username + "!");

        // Save button click listener
        btnSaveHealthStatus.setOnClickListener(v -> saveHealthStatus());

        return view;
    }

    private void saveHealthStatus() {
        // Get selected RadioButton
        int selectedId = radioGroupHealth.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = radioGroupHealth.findViewById(selectedId);
            String healthStatus = selectedRadioButton.getText().toString();

            // Save health status to database
            databaseHelper.saveHealthStatus(healthStatus);
            Toast.makeText(getContext(), "Health status saved: " + healthStatus, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Please select a health status", Toast.LENGTH_SHORT).show();
        }
    }
}
