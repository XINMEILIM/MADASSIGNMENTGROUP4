package com.example.madassignment4;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ExerciseHomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_home, container, false);

        ImageButton btnProgressGoal = view.findViewById(R.id.BtnProgressGoal);
        ImageButton btnLog = view.findViewById(R.id.BtnLog);
        ImageButton btnHistory = view.findViewById(R.id.BtnHistory);

        // Set up button listeners
        btnProgressGoal.setOnClickListener(v -> openFragment(new ProgressGoalFragment()));
        btnLog.setOnClickListener(v -> openFragment(new LogFragment()));
        btnHistory.setOnClickListener(v -> openFragment(new HistoryFragment()));

        return view;
    }

    private void openFragment(Fragment fragment) {
        if (getActivity() != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}