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
import androidx.navigation.fragment.NavHostFragment;

public class OpeningFragment extends Fragment {

        private EditText nameInput;
        private Button saveButton;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_opening, container, false);

            nameInput = view.findViewById(R.id.nameInput);
            saveButton = view.findViewById(R.id.saveButton);

            saveButton.setOnClickListener(v -> {
                String name = nameInput.getText().toString().trim();
                if (!name.isEmpty()) {
                    saveNameToPreferences(name);
                    navigateToHomeFragment();
                } else {
                    Toast.makeText(getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        private void saveNameToPreferences(String name) {
            SharedPreferences preferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user_name", name);
            editor.apply();
        }

        private void navigateToHomeFragment() {
            NavHostFragment.findNavController(this).navigate(R.id.action_openingFragment_to_homeFragment);
        }
    }
