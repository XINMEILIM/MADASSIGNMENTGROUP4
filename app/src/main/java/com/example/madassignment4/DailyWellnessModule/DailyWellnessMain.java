package com.example.madassignment4.DailyWellnessModule;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.madassignment4.R;

public class DailyWellnessMain extends Fragment {


    public DailyWellnessMain() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_wellness_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find buttons in the layout
        Button BtnHydrationTracking = view.findViewById(R.id.BtnHydrationTracker);
        Button BtnStepTracking = view.findViewById(R.id.BtnStepTracking);
        Button BtnBMICalculator = view.findViewById(R.id.BtnBMICalculator);
        ImageButton BtnBack = view.findViewById(R.id.back_btn);
        BtnBack.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.trackerMain2);
        });

        // Set click listeners for navigation
        BtnHydrationTracking.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.HydrationTracking));

        BtnStepTracking.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.StepTracking));

        BtnBMICalculator.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.BMICalculator));
    }


}