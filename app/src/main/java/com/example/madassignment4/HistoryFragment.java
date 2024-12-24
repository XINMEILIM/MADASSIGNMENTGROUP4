package com.example.madassignment4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        Button summaryButton = view.findViewById(R.id.BtnHistorySummary);
        summaryButton.setOnClickListener(v -> {
            // Replace the current fragment with HistorySummaryFragment
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, new HistorySummaryFragment());
            fragmentTransaction.addToBackStack(null); // Add to back stack for "Back" navigation
            fragmentTransaction.commit();
        });

        return view;
    }
}
