package com.example.real;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class TrackerFragment extends Fragment {

    private Button btnGetStarted1, btnGetStarted2, btnGetStarted3;
    private ImageView imageView2, imageView3, imageView4;
    private TextView textView2, textView3, textView4, textView5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tracker, container, false);

        // Initialize UI elements
        btnGetStarted1 = rootView.findViewById(R.id.btnGetStarted1);
        btnGetStarted2 = rootView.findViewById(R.id.btnGetStarted2);
        btnGetStarted3 = rootView.findViewById(R.id.btnGetStarted3);

        imageView2 = rootView.findViewById(R.id.imageView2);
        imageView3 = rootView.findViewById(R.id.imageView3);
        imageView4 = rootView.findViewById(R.id.imageView4);

        textView2 = rootView.findViewById(R.id.textView2);
        textView3 = rootView.findViewById(R.id.textView3);
        textView4 = rootView.findViewById(R.id.textView4);
        textView5 = rootView.findViewById(R.id.textView5);

        // Set text for the tracker sections
        textView2.setText("Tracker");
        textView3.setText("Mood Tracker");
        textView4.setText("Workout Tracker");
        textView5.setText("Wellness Tracker");

        // Set button click listeners for each tracker
        btnGetStarted1.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Mood Tracker Selected", Toast.LENGTH_SHORT).show();
            // Future: Replace with navigation to Mood Tracker fragment
        });

        btnGetStarted2.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Workout Tracker Selected", Toast.LENGTH_SHORT).show();
            // Future: Replace with navigation to Workout Tracker fragment
        });

        btnGetStarted3.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Wellness Tracker Selected", Toast.LENGTH_SHORT).show();
            // Future: Replace with navigation to Wellness Tracker fragment
        });

        return rootView;
    }
}
