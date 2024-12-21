package com.example.madassignment4.DailyWellnessModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment4.R;


public class bmiCalculator extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bmi_calculator, container, false);

        // Initialize the UI elements
        EditText heightInput = view.findViewById(R.id.ETHeight);
        EditText weightInput = view.findViewById(R.id.ETWeight);
        Button BtnCalculate = view.findViewById(R.id.BtnCalculate);
        TextView resultBMI = view.findViewById(R.id.ResultBMI);
        TextView yourBMI = view.findViewById(R.id.YourBmi);
        TextView resultBMICategory = view.findViewById(R.id.ResultBMICategory);
        TextView BMICategories = view.findViewById(R.id.BMICategories);
        ImageView BMICategoriesTable = view.findViewById(R.id.BMICategoriesTable);
        ImageButton BtnBack = view.findViewById(R.id.back_btn);

        // Set up button click listener
        BtnCalculate.setOnClickListener(v -> {
            String StrHeight = heightInput.getText().toString().trim();
            String StrWeight = weightInput.getText().toString().trim();

            if (!StrHeight.isEmpty() && !StrWeight.isEmpty()) {
                try {
                    double height = Double.parseDouble(StrHeight);
                    double weight = Double.parseDouble(StrWeight);

                    if (height > 0 && weight > 0) {
                        double BMI = weight / (height * height);

                        resultBMI.setText(String.format("%.2f", BMI));
                        resultBMI.setVisibility(View.VISIBLE);

                        String category;
                        if (BMI <= 18.4) {
                            category = "underweight.";
                        } else if (BMI >= 18.5 && BMI <= 24.9) {
                            category = "normal.";
                        } else if (BMI >= 25.0 && BMI <= 29.9) {
                            category = "overweight.";
                        } else {
                            category = "obese.";
                        }

                        resultBMICategory.setText("which is " + category);
                        yourBMI.setVisibility(View.VISIBLE);
                        resultBMICategory.setVisibility(View.VISIBLE);
                        BMICategories.setVisibility(View.VISIBLE);
                        BMICategoriesTable.setVisibility(View.VISIBLE);
                    } else {
                        // If negative or zero values entered
                        Toast.makeText(requireContext(), "Please enter valid positive values for height and weight.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    // If non-numeric values entered
                    Toast.makeText(requireContext(), "Please enter numeric values for height and weight.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // If empty input fields
                Toast.makeText(requireContext(), "Please fill in both height and weight fields.", Toast.LENGTH_SHORT).show();
            }
        });

        BtnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}