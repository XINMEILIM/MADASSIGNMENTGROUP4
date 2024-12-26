package com.example.real;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class ProfileFragment extends Fragment {

    private TextView textViewUserName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Get username from arguments or shared preference (as an example)
        String username = getArguments() != null ? getArguments().getString("username") : "User";

        // Set the username text
        textViewUserName = view.findViewById(R.id.textViewUserName);
        textViewUserName.setText(username);

        // Set up button navigation and toasts
        setupNavigation(view);

        return view;
    }

    private void setupNavigation(View view) {
        // Button 1: Navigate to My Profile
        view.findViewById(R.id.imageButton11).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_userProfileFragment));

        // Button 2: Show Toast for My Workout (later put navigation to my workout here)
        view.findViewById(R.id.imageButton7).setOnClickListener(v ->
                Toast.makeText(getContext(), "Navigating to My Workout", Toast.LENGTH_SHORT).show());

        // Button 3: Show Toast for My Mood (later put navigation to my mood here)
        view.findViewById(R.id.imageButton10).setOnClickListener(v ->
                Toast.makeText(getContext(), "Navigating to My Mood", Toast.LENGTH_SHORT).show());

        // Button 4: Show Toast for My Wellness (later put navigation to my wellness here)
        view.findViewById(R.id.imageButton8).setOnClickListener(v ->
                Toast.makeText(getContext(), "Navigating to My Wellness", Toast.LENGTH_SHORT).show());

        // Button 5: Navigate to Privacy Policy
        view.findViewById(R.id.imageButton3).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_privacyPolicyFragment));

        // Button 6: Navigate to Language Settings
        view.findViewById(R.id.imageButton9).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_languageFragment));
    }
}
