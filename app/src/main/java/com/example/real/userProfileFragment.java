package com.example.real;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Random;

public class userProfileFragment extends Fragment {

    private EditText editTextAge, editTextGender, editTextHeight, editTextWeight, editTextYearOfBirth;
    private Button saveButton, displayButton, updateButton;
    private TextView textViewUsername, textViewUserData;
    private DatabaseHelper_module1 databaseHelperModule1;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Initialize database helper
        databaseHelperModule1 = new DatabaseHelper_module1(getContext());

        // Initialize widgets
        textViewUsername = view.findViewById(R.id.textViewName);
        textViewUserData = view.findViewById(R.id.textViewUserData);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextGender = view.findViewById(R.id.editTextGender);
        editTextHeight = view.findViewById(R.id.editTextHeight);
        editTextWeight = view.findViewById(R.id.editTextWeight);
        editTextYearOfBirth = view.findViewById(R.id.editTextYearOfBirth);
        saveButton = view.findViewById(R.id.saveButton);

        // Retrieve username from arguments
        username = getArguments() != null ? getArguments().getString("username") : null;

        if (username != null) {
            textViewUsername.setText("Username: " + username);
        } else {
            Toast.makeText(getContext(), "No username received", Toast.LENGTH_SHORT).show();
        }

        // Save button listener
        saveButton.setOnClickListener(v -> {
            saveUserProfile();
            displayUserProfile(); // Display the profile immediately after saving
        });

        return view;
    }


    // Save instance state to preserve username
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", username);
    }

    // Restore username after a configuration change
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            username = savedInstanceState.getString("username");
            if (username != null) {
                textViewUsername.setText("Username: " + username);
            }
        }
    }

    private void saveUserProfile() {
        int userId = new Random().nextInt(100000);
        String ageString = editTextAge.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        String heightString = editTextHeight.getText().toString().trim();
        String weightString = editTextWeight.getText().toString().trim();
        String yearOfBirthString = editTextYearOfBirth.getText().toString().trim();

        if (ageString.isEmpty() || gender.isEmpty() || heightString.isEmpty() ||
                weightString.isEmpty() || yearOfBirthString.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int age = Integer.parseInt(ageString);
            double height = Double.parseDouble(heightString);
            double weight = Double.parseDouble(weightString);
            int yearOfBirth = Integer.parseInt(yearOfBirthString);

            databaseHelperModule1.saveUserProfile(String.valueOf(userId), username, age, gender, height, weight, yearOfBirth);
            Toast.makeText(getContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid input. Please enter valid numeric values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayUserProfile() {
        if (username == null || username.isEmpty()) {
            textViewUserData.setText("No username provided");
            return;
        }

        Cursor cursor = databaseHelperModule1.getUserProfileByUsername(username);

        if (cursor != null && cursor.moveToFirst()) {
            String userId = cursor.getString(cursor.getColumnIndexOrThrow("UserID"));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow("Age"));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("Gender"));
            double height = cursor.getDouble(cursor.getColumnIndexOrThrow("Height"));
            double weight = cursor.getDouble(cursor.getColumnIndexOrThrow("Weight"));
            int yearOfBirth = cursor.getInt(cursor.getColumnIndexOrThrow("YearOfBirth"));

            String userData = "User ID: " + userId +
                    "\nUsername: " + username +
                    "\nAge: " + age +
                    "\nGender: " + gender +
                    "\nHeight: " + height +
                    "\nWeight: " + weight +
                    "\nYear of Birth: " + yearOfBirth;

            textViewUserData.setText(userData);
        } else {
            textViewUserData.setText("No profile found for username: " + username);
        }

        if (cursor != null) {
            cursor.close();
        }
    }


    private void testDatabaseData() {
        Cursor cursor = databaseHelperModule1.getAllUserProfiles();
        if (cursor != null) {
            Log.d("Database", "Cursor count: " + cursor.getCount()); // Log the number of rows returned

            if (cursor.moveToFirst()) {
                do {
                    // Fetch and log data for each row
                    String userId = cursor.getString(cursor.getColumnIndexOrThrow("UserID"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                    int age = cursor.getInt(cursor.getColumnIndexOrThrow("Age"));
                    String gender = cursor.getString(cursor.getColumnIndexOrThrow("Gender"));
                    double height = cursor.getDouble(cursor.getColumnIndexOrThrow("Height"));
                    double weight = cursor.getDouble(cursor.getColumnIndexOrThrow("Weight"));
                    int yearOfBirth = cursor.getInt(cursor.getColumnIndexOrThrow("YearOfBirth"));

                    Log.d("Database", "Data: " + userId + ", " + name + ", " + age + ", " + gender + ", " + height + ", " + weight + ", " + yearOfBirth);
                } while (cursor.moveToNext());
            } else {
                Log.e("Database", "Cursor exists but no data to iterate");
            }
            cursor.close();
        } else {
            Log.e("Database", "Cursor is null");
        }
    }


}
