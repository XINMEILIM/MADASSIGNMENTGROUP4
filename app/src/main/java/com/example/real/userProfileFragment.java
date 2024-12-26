package com.example.real;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.real.Database.DatabaseHelper_module1;

public class userProfileFragment extends Fragment {

    private EditText editTextAge, editTextGender, editTextHeight, editTextWeight, editTextYearOfBirth;
    private Button saveButton;
    private TextView textViewName;
    private DatabaseHelper_module1 dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Initialize database helper
        dbHelper = new DatabaseHelper_module1(getContext());

        // Initialize widgets
        textViewName = view.findViewById(R.id.textViewName);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextGender = view.findViewById(R.id.editTextGender);
        editTextHeight = view.findViewById(R.id.editTextHeight);
        editTextWeight = view.findViewById(R.id.editTextWeight);
        editTextYearOfBirth = view.findViewById(R.id.editTextYearOfBirth);
        saveButton = view.findViewById(R.id.saveButton);

        // Get username from arguments
        String username = getArguments() != null ? getArguments().getString("username") : "User";

        // Display username
        textViewName.setText(username);

        // Set up Save button click listener
        saveButton.setOnClickListener(v -> saveAndDisplayUserProfile());

        return view;
    }

    private void saveAndDisplayUserProfile() {
        String ageString = editTextAge.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        String heightString = editTextHeight.getText().toString().trim();
        String weightString = editTextWeight.getText().toString().trim();
        String yearOfBirthString = editTextYearOfBirth.getText().toString().trim();

        if (ageString.isEmpty() || gender.isEmpty() || heightString.isEmpty() || weightString.isEmpty() || yearOfBirthString.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageString);
        int height = Integer.parseInt(heightString);
        float weight = Float.parseFloat(weightString);
        int yearOfBirth = Integer.parseInt(yearOfBirthString);

        // Save to database
        dbHelper.saveUserProfile(textViewName.getText().toString(), age, gender, height, weight, yearOfBirth);
        Toast.makeText(getContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
