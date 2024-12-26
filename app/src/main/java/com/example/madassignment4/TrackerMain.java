package com.example.madassignment4;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.madassignment4.DailyWellnessModule.DailyWellnessActivity;
import com.example.madassignment4.ExerciseModule.ExerciseActivityMain;
import com.example.madassignment4.MoodModule.MoodMainActivity;

public class TrackerMain extends Fragment {

    public TrackerMain(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracker_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BtnMoodTracker = view.findViewById(R.id.BtnMoodTracker);
        BtnMoodTracker.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MoodMainActivity.class);
            startActivity(intent);
        });

        Button BtnWorkoutTracker = view.findViewById(R.id.BtnWorkoutTracker);
        BtnWorkoutTracker.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(), ExerciseActivityMain.class);
            startActivity(intent);
        });

        Button BtnWellnessTracker = view.findViewById(R.id.BtnWellnessTracker);
        BtnWellnessTracker.setOnClickListener(v ->{
                Intent intent=new Intent(getContext(), DailyWellnessActivity.class);
                startActivity(intent);
        });
    }
}
