package com.example.real;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.real.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Set up navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment == null) {
            throw new IllegalStateException("NavHostFragment not found. Check your layout file.");
        }
        NavController navController = navHostFragment.getNavController();
    }
}
