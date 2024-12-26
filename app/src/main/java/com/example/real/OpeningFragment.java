package com.example.real;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.real.Database.DatabaseHelper_module1;

public class OpeningFragment extends Fragment {

    private EditText editTextUsername;
    private Button buttonSave;
    private DatabaseHelper_module1 databaseHelperModule1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opening, container, false);

        // Initialize database helper
        databaseHelperModule1 = new DatabaseHelper_module1(getContext());

        // Find views
        editTextUsername = view.findViewById(R.id.nameInput);
        buttonSave = view.findViewById(R.id.saveButton);

        // Save button click listener
        buttonSave.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            if (!username.isEmpty()) {
                // Save to database
                databaseHelperModule1.saveUser(username);

                // Save to SharedPreferences
                SharedPreferences preferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user_name", username);
                editor.apply();

                Toast.makeText(getContext(), "Username saved: " + username, Toast.LENGTH_SHORT).show();

                // Navigate to HomeFragment
                Navigation.findNavController(view).navigate(R.id.action_openingFragment_to_homeFragment);
            } else {
                Toast.makeText(getContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
