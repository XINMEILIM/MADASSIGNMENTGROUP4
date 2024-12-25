package com.example.madassignment4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerViewHistory;
    private HistoryAdapter historyAdapter;
    private ArrayList<HashMap<String, String>> historyList;
    private FitnessDatabaseHelper databaseHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new FitnessDatabaseHelper(getContext());
        historyList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        Button btnSummary = view.findViewById(R.id.BtnHistorySummary);
        btnSummary.setOnClickListener(v -> openFragment(new HistorySummaryFragment()));

        // Initialize RecyclerView
        recyclerViewHistory = view.findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new HistoryAdapter(historyList);
        recyclerViewHistory.setAdapter(historyAdapter);

        // Load data from database
        loadHistoryFromDatabase();

        return view;
    }

    private void loadHistoryFromDatabase() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT Date, ExerciseType, Attributes FROM " + FitnessDatabaseHelper.TABLE_EXERCISE_LOG + " ORDER BY Date DESC;";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> exerciseLog = new HashMap<>();
                    exerciseLog.put("Date", cursor.getString(0));
                    exerciseLog.put("ExerciseType", cursor.getString(1));
                    exerciseLog.put("Attributes", cursor.getString(2));

                    historyList.add(exerciseLog);
                } while (cursor.moveToNext());
            }

            historyAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error loading history: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
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

