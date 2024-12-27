package com.example.madassignment4;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FitnessDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FitnessApp010.db";
    private static final int DATABASE_VERSION = 3;

    // Table names
    public static final String TABLE_FITNESS_SETTING = "fitnessSetting";
    public static final String TABLE_GOAL_SETTING = "goalSetting";
    public static final String TABLE_EXERCISE_LOG = "exerciseLog";

    // SQL to create fitnessSetting table
    private static final String CREATE_FITNESS_SETTING_TABLE = "CREATE TABLE " + TABLE_FITNESS_SETTING + " (" +
            "fitnessID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "timeFrame TEXT NOT NULL CHECK(timeFrame IN ('Daily', 'Weekly', 'Monthly')), " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

    // SQL to create goalSetting table
    private static final String CREATE_GOAL_SETTING_TABLE = "CREATE TABLE " + TABLE_GOAL_SETTING + " (" +
            "goalID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "fitnessID INTEGER NOT NULL, " +
            "exerciseType TEXT NOT NULL, " +
            "attributes TEXT, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY (fitnessID) REFERENCES " + TABLE_FITNESS_SETTING + "(fitnessID));";

    // SQL to create exerciseLog table
    private static final String CREATE_EXERCISE_LOG_TABLE = "CREATE TABLE " + TABLE_EXERCISE_LOG + " (" +
            "logID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Date TEXT NOT NULL, " +
            "exerciseType TEXT NOT NULL, " +
            "attributes TEXT, " +
            "fitnessID INTEGER NOT NULL, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY (fitnessID) REFERENCES " + TABLE_FITNESS_SETTING + "(fitnessID));";

    public FitnessDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FITNESS_SETTING_TABLE);
        db.execSQL(CREATE_GOAL_SETTING_TABLE);
        db.execSQL(CREATE_EXERCISE_LOG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FITNESS_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE_LOG);
        onCreate(db);
    }

    public Cursor getLatestFitnessSettingAndGoals(SQLiteDatabase db) {
        String query = "SELECT g.exerciseType, g.attributes " +
                "FROM " + TABLE_GOAL_SETTING + " g " +
                "JOIN " + TABLE_FITNESS_SETTING + " f ON g.fitnessID = f.fitnessID " +
                "WHERE f.fitnessID = (SELECT MAX(fitnessID) FROM " + TABLE_FITNESS_SETTING + ")";
        return db.rawQuery(query, null);
    }
}

