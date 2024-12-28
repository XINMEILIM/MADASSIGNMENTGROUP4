package com.example.madassignment4.UserModule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

public class UserHomeFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewGreeting = view.findViewById(R.id.textViewGreeting);

        // Access the database helper
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        dbHelper.populateHealthStatusTable();

        // Retrieve the username
        String username = dbHelper.getUsername();

        if (username != null) {
            textViewGreeting.setText("Hi, " + username + "!");
        } else {
            textViewGreeting.setText("Hi, User!"); // Default message if username is not found
        }

        RadioButton radioActive = view.findViewById(R.id.radioButton1);
        RadioButton radioSick = view.findViewById(R.id.radioButton2);
        RadioButton radioBreak = view.findViewById(R.id.radioButton3);
        RadioButton radioInjured = view.findViewById(R.id.radioButton4);
        Button btnSave = view.findViewById(R.id.btnSaveHealthStatus);


        btnSave.setOnClickListener(v -> {
            String selectedStatus = null;
            if (radioActive.isChecked()) {
                selectedStatus = "Active";
            } else if (radioSick.isChecked()) {
                selectedStatus = "Sick";
            } else if (radioBreak.isChecked()) {
                selectedStatus = "Take a Break";
            } else if (radioInjured.isChecked()) {
                selectedStatus = "Injured";
            }

            if (selectedStatus != null) {
                String userId = dbHelper.getUserIdByMostRecentLogin();

                dbHelper.saveOrUpdateUserHealthStatus(userId, selectedStatus);

                Toast.makeText(requireContext(), "Health status saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Please select a health status.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_home, container, false);
    }
}