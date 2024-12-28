package com.example.real;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.real.Database.DatabaseHelper_module1;

public class OpeningFragment extends Fragment {

    private EditText editTextUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opening, container, false);

        // Initialize UI components
        editTextUsername = view.findViewById(R.id.nameInput);
        Button saveButton = view.findViewById(R.id.saveButton);

        // Save button listener
        saveButton.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();

            if (!username.isEmpty()) {
                // Pass username to UserProfileFragment
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                Navigation.findNavController(view).navigate(R.id.action_openingFragment_to_userProfileFragment, bundle);
            } else {
                // Show Toast if the username is empty
                Toast.makeText(getContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper_module1 databaseHelper = new DatabaseHelper_module1(requireContext());
        databaseHelper.clearUserProfileTable(); // Clear user profile data on app start
    }

}
