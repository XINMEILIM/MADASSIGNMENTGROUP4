package com.example.madassignment4.DailyWellnessModule;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HydrationTracking extends Fragment {
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbHelper = new DatabaseHelper(requireContext());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hydration_tracking, container, false);

        // Initialize the UI components
        TextView GoalAmount = view.findViewById(R.id.GoalSteps);
        showHydrationGoalDialog(GoalAmount);  // Show the hydration goal dialog when the fragment is created

        Button BtnAdd = view.findViewById(R.id.BtnAdd);
        BtnAdd.setOnClickListener(v -> showHydrationIntakeDialog());

        Button BtnHistory = view.findViewById(R.id.BtnHistory);
        BtnHistory.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_hydrationTracking_to_hydrationHistory);
        });

        // Set up the back button
        ImageButton BtnBack = view.findViewById(R.id.back_btn);
        BtnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void showHydrationGoalDialog(TextView GoalAmount) {
        // Get the current date
        String currentDate = getCurrentDate();

        // Get user ID (replace this with actual user ID if applicable)
        String userId = "testUser";  // Example user ID; replace with actual logic

        // Check if the user has already set a goal today from the database
        int hydrationGoal = dbHelper.getHydrationGoal(userId, currentDate);

        // If the goal has already been set for today, show a message and do not allow input
        if (hydrationGoal != -1) {
            Toast.makeText(getContext(), "You have already set your hydration goal for today.", Toast.LENGTH_SHORT).show();
            GoalAmount.setText(String.valueOf(hydrationGoal));  // Show the saved goal
            return; // Do not show the dialog if goal was already set today
        }

        // Create the dialog for setting hydration goal
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_hydration_goal);
        dialog.setCancelable(false);

        // Initialize dialog views
        EditText ETHydrationGoal = dialog.findViewById(R.id.ETStepsInput);
        Button BtnSetGoal = dialog.findViewById(R.id.BtnSetGoal);

        BtnSetGoal.setOnClickListener(v -> {
            String goalInput = ETHydrationGoal.getText().toString();
            if (!goalInput.isEmpty()) {
                try {
                    int newHydrationGoal = Integer.parseInt(goalInput);

                    if (newHydrationGoal > 0) {
                        // Save the goal in the database
                        dbHelper.saveHydrationGoal(userId, currentDate, newHydrationGoal);

                        // Update the TextView with the new goal
                        GoalAmount.setText(String.valueOf(newHydrationGoal));

                        // Show a confirmation toast
                        Toast.makeText(getContext(), "Hydration goal set to " + newHydrationGoal + " ml", Toast.LENGTH_SHORT).show();

                        // Dismiss the dialog only if the fragment is still attached
                        if (isAdded()) {
                            dialog.dismiss();
                        }
                    } else {
                        // Show error if the value is negative or zero
                        Toast.makeText(getContext(), "Please enter a positive number.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    // Show error if the input is not a valid number
                    Toast.makeText(getContext(), "Invalid input. Please enter a number.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Show error if input is empty
                Toast.makeText(getContext(), "Goal cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });

        // Show the dialog
        if (isAdded()) {
            dialog.show();
        }
    }


    private void showHydrationIntakeDialog() {
        // Get the current date and timestamp
        String currentDate = getCurrentDate();
        String currentTimeStamp = getCurrentTimeStamp();

        // Get user ID (replace this with actual user ID if applicable)
        String userId = "testUser";  // Example user ID; replace with actual logic

        // Create the dialog for entering hydration intake
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_hydration_intake); // Layout for input

        // Initialize dialog views
        EditText ETIntakeAmount = dialog.findViewById(R.id.ETStepsInput);
        Button BtnAddIntake = dialog.findViewById(R.id.BtnSetGoal);

        BtnAddIntake.setOnClickListener(v -> {
            String intakeInput = ETIntakeAmount.getText().toString();

            if (!intakeInput.isEmpty()) {
                try {
                    int intakeAmount = Integer.parseInt(intakeInput);

                    if (intakeAmount > 0) {
                        // Save the intake in the database
                        boolean isInserted = dbHelper.saveHydrationIntake(userId, currentDate, currentTimeStamp, intakeAmount);

                        if (isInserted) {
                            // Update the TextView with the new intake amount in the recycler view
                            loadHydrationData();

                            // Show a confirmation toast
                            Toast.makeText(getContext(), "Intake added: " + intakeAmount + " ml", Toast.LENGTH_SHORT).show();

                            // Dismiss the dialog only if the fragment is still attached
                            if (isAdded()) {
                                dialog.dismiss();
                            }

                            displayTotalWaterIntake();
                        } else {
                            // Show error if database insertion fails
                            Toast.makeText(getContext(), "Failed to add intake. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please enter a positive number.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Input cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });

        // Show the dialog
        if (isAdded()) {
            dialog.show();
        }
    }

    private void loadHydrationData() {
        if (getView() == null) return; // Prevent NullPointerException

        String todayDate = getCurrentDate();  // Get today's date
        String userId = "testUser";  // Replace with actual user ID logic

        // Get today's hydration intake records
        List<HydrationIntakeModel> intakeList = dbHelper.getTodayHydrationRecords(userId, todayDate);

        // Set up the RecyclerView
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create and set the adapter
        HydrationIntakeAdapter adapter = new HydrationIntakeAdapter(intakeList);
        recyclerView.setAdapter(adapter);
    }

    private void displayTotalWaterIntake() {
        // Get SharedPreferences to store the congrats flag
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("WaterIntakePrefs", Context.MODE_PRIVATE);
        boolean isCongrats = sharedPreferences.getBoolean("isCongrats", true);

        if (getView() == null) return; // Prevent NullPointerException

        String currentDate = getCurrentDate();
        String userId = "testUser";  // Replace with actual user logic

        // Get the total water intake for the user
        int totalWaterIntake = dbHelper.getTotalWaterIntake(userId, currentDate);

        // Update the TextView with the total water intake
        TextView totalIntakeTextView = getView().findViewById(R.id.TotalWaterIntake);
        totalIntakeTextView.setText(String.valueOf(totalWaterIntake));

        // Check if the total water intake meets or exceeds the hydration goal
        int hydrationGoal = dbHelper.getHydrationGoal(userId, currentDate);

        // Only show the congrats dialog if it's not shown already and the goal is met
        if (totalWaterIntake >= hydrationGoal && hydrationGoal != 0 && isCongrats) {
            showCongratulatoryDialog();

            // Update the flag to prevent showing the dialog again
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isCongrats", false); // Set the flag to false after showing the dialog
            editor.apply();
        }
    }


    private void showCongratulatoryDialog() {
        // Create a dialog to congratulate the user for reaching the goal
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_achieve_goal);  // You need to create this dialog layout

        // Initialize dialog views (you can customize the dialog as needed)
        Button BtnClose = dialog.findViewById(R.id.btnCloseDialog);
        BtnClose.setOnClickListener(v -> dialog.dismiss());

        // Show the congratulatory dialog
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Load data for hydration tracking
        loadHydrationData();
        displayTotalWaterIntake();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
