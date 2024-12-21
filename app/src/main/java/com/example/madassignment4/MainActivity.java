package com.example.madassignment4;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.madassignment4.Database.DatabaseHelper;
import androidx.core.graphics.Insets;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handling window insets (padding for system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize DatabaseHelper to trigger table creation
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Get a writable database to ensure onCreate() is called
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Close the database after ensuring table creation
        db.close();

        // Set up the Navigation Component
        // Find the NavHostFragment using its ID
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_tracker_fragment);

        // Get the NavController from the NavHostFragment
        NavController navController = navHostFragment.getNavController();
    }
}