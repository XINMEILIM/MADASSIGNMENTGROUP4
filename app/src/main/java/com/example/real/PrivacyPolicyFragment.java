package com.example.real;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PrivacyPolicyFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);

        // Initialize widgets
        TextView textViewTitle = view.findViewById(R.id.textView1); // Title TextView
        TextView textViewContent = view.findViewById(R.id.textView2); // Content TextView
        TextView textViewLastUpdated = view.findViewById(R.id.textView3); // Last Updated TextView

        // Set privacy policy title and content
        textViewTitle.setText("Privacy Policy");
        String policyContent = "At Tracker, we prioritize your privacy by protecting your personal information and explaining our practices clearly. "
                + "We collect data like personal details, health metrics, location (if enabled), and device information to improve our services, "
                + "personalize your experience, and track your fitness progress. "
                + "We may share information with trusted third-party providers to enhance app functionality, and we only disclose data as legally required. "
                + "We implement strong security measures to protect your data but note that no online system is entirely secure. "
                + "You can access, update, or delete your data, and manage notification preferences within the app. "
                + "Our app is not intended for children under 13, and we retain data only as needed. Updates to this policy will be communicated "
                + "through the app, and you can reach us with any questions at tracker@gmail.com.";
        textViewContent.setText(policyContent);

        // Simulate fetching the last updated version from the database
        String lastUpdatedVersion = getLastUpdatedVersion();
        textViewLastUpdated.setText("Last Updated: " + lastUpdatedVersion);

        return view;
    }

    private String getLastUpdatedVersion() {
        // Simulate retrieving the last updated version from the database
        // This should be replaced with actual database logic if needed
        return "2024-12-26";
    }
}
