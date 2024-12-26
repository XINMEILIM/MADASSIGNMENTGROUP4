package com.example.madassignment4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.madassignment4.DailyWellnessModule.HydrationIntakeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Name and Version
    private static final String DATABASE_NAME = "FitJourney.db";
    private static final int DATABASE_VERSION = 6;

    // Table Names
    public static final String TABLE_USER = "User";
    public static final String TABLE_USER_PROFILE = "UserProfile";
    public static final String TABLE_HEALTH_STATUS = "HealthStatus";
    public static final String TABLE_USER_HEALTH_STATUS = "UserHealthStatus";
    public static final String TABLE_LANGUAGE = "Language";
    public static final String TABLE_USER_LANGUAGE_SETTING = "UserLanguageSetting";
    public static final String TABLE_PRIVACY_POLICY = "PrivacyPolicy";
    public static final String TABLE_HYDRATION_GOAL = "HydrationGoal";
    public static final String TABLE_HYDRATION_INTAKE = "HydrationIntake";
    public static final String TABLE_STEP_TRACKING = "StepTracking";
    public static final String TABLE_MOOD_LOG = "MoodLog";
    public static final String TABLE_JOURNAL = "Journal";
    public static final String TABLE_MIND_FULNESS_EXERCISE = "MindfulnessExercise";
    public static final String TABLE_FITNESS_SETTING = "FitnessSetting";
    public static final String TABLE_GOAL_SETTING = "GoalSetting";
    public static final String TABLE_EXERCISE_LOG = "ExerciseLog";


    // User Table Columns
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_USER_NAME = "UserName";
    public static final String COLUMN_USER_EMAIL = "UserEmail";
    public static final String COLUMN_USER_PASSWORD = "Password";  // Storing password
    public static final String COLUMN_CREATED_AT = "CreatedAt";
    public static final String COLUMN_LAST_LOGIN = "LastLogin";

    // User Profile Columns
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_AGE = "Age";
    public static final String COLUMN_GENDER = "Gender";
    public static final String COLUMN_HEIGHT = "Height";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_YEAR_OF_BIRTH = "YearOfBirth";
    public static final String COLUMN_UPDATED_AT = "UpdatedAt";

    // Health Status Table Columns
    public static final String COLUMN_HEALTH_STATUS_ID = "HealthStatusID";
    public static final String COLUMN_HEALTH_STATUS_DESC = "HealthStatusDesc";

    // User Health Status Columns
    public static final String COLUMN_USER_HEALTH_STATUS_ID = "UserHealthStatusID";
    public static final String COLUMN_USER_HEALTH_DATE = "Date";

    // Language Columns
    public static final String COLUMN_LANGUAGE_ID = "LanguageID";
    public static final String COLUMN_LANGUAGE_NAME = "LanguageName";
    public static final String COLUMN_LANGUAGE_CODE = "LanguageCode";

    // User Language Setting Columns
    public static final String COLUMN_USER_LANGUAGE_ID = "UserLanguageID";

    // Privacy Policy Columns
    public static final String COLUMN_PRIVACY_POLICY_ID = "ID";
    public static final String COLUMN_PRIVACY_POLICY_CONTENT = "Content";
    public static final String COLUMN_PRIVACY_POLICY_LAST_UPDATED = "LastUpdated";

    // Hydration Goal Columns
    public static final String COLUMN_GOAL_ID = "GoalID";
    public static final String COLUMN_HYDRATION_GOAL = "HydrationGoal";  // Hydration goal in ml
    public static final String COLUMN_GOAL_DATE = "Date";

    // Hydration Intake Columns
    public static final String COLUMN_INTAKE_ID = "IntakeID";
    public static final String COLUMN_INTAKE_TIMESTAMP = "IntakeTimeStamp";
    public static final String COLUMN_QUANTITY_OF_WATER = "QuantityofWater";  // Intake in ml

    // Step Tracking Columns
    public static final String COLUMN_STEP_GOAL_ID = "StepGoalID";
    public static final String COLUMN_STEP_GOAL = "StepGoal";
    public static final String COLUMN_STEP_COUNT = "StepCount";
    public static final String COLUMN_DISTANCE_WALKED = "DistanceWalked";
    public static final String COLUMN_CALORIES_BURNED = "CaloriesBurned";
    public static final String COLUMN_STEP_TRACK_DATE = "Date";

    // Mood Log Columns
    public static final String COLUMN_MOOD_DATE = "MoodDate";
    public static final String COLUMN_MOOD_TYPE = "MoodType";

    // Journal Columns
    public static final String COLUMN_JOURNAL_DATE = "JournalDate";
    public static final String COLUMN_JOURNAL_PHOTO_PATH = "PhotoPath";
    public static final String COLUMN_JOURNAL_WEATHER = "Weather";
    public static final String COLUMN_JOURNAL_NOTE = "Note";

    // Mindfulness Exercise Columns
    public static final String COLUMN_MINDFULNESS_ID = "MindfulnessID";
    public static final String COLUMN_MINDFULNESS_TYPE = "MindfulnessType";
    public static final String COLUMN_MINDFULNESS_DURATION = "Duration";
    public static final String COLUMN_MINDFULNESS_DATE = "MindfulnessDate";

    //Fitness Settings Columns
    public static final String COLUMN_FITNESS_ID = "FitnessID";
    public static final String COLUMN_TIME_FRAME = "TimeFrame";
    public static final String COLUMN_FITNESS_CREATED_AT = "Created_At";

    //Goal Setting Columns
    public static final String COLUMN_FITNESS_GOAL_ID = "GoalID";
    public static final String COLUMN_GOAL_FITNESS_ID = "FitnessID";
    public static final String COLUMN_GOAL_EXERCISE_TYPE = "ExerciseType";
    public static final String COLUMN_GOAL_ATTRIBUTES = "Attributes";
    public static final String COLUMN_GOAL_CREATED_AT = "Created_At";

    //Exercise Log Columns
    public static final String COLUMN_LOG_ID = "LogID";
    public static final String COLUMN_LOG_DATE = "Date";
    public static final String COLUMN_LOG_EXERCISE_TYPE = "ExerciseType";
    public static final String COLUMN_LOG_ATTRIBUTES = "Attributes";
    public static final String COLUMN_LOG_FITNESS_ID = "FitnessID";
    public static final String COLUMN_LOG_CREATED_AT = "Created_At";



    // SQL for creating User Table
    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_NAME + " TEXT, " +
                    COLUMN_USER_EMAIL + " TEXT, " +
                    COLUMN_USER_PASSWORD + " TEXT, " +
                    COLUMN_CREATED_AT + " TIMESTAMP, " +
                    COLUMN_LAST_LOGIN + " TIMESTAMP);";

    // SQL for creating UserProfile Table (Foreign Key to User Table)
    private static final String CREATE_USER_PROFILE_TABLE =
            "CREATE TABLE " + TABLE_USER_PROFILE + " (" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY, " +  // Foreign Key reference to User table
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_AGE + " INTEGER, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_HEIGHT + " DOUBLE, " +
                    COLUMN_WEIGHT + " DOUBLE, " +
                    COLUMN_YEAR_OF_BIRTH + " INTEGER, " +
                    COLUMN_UPDATED_AT + " TIMESTAMP, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating Health Status Table
    private static final String CREATE_HEALTH_STATUS_TABLE =
            "CREATE TABLE " + TABLE_HEALTH_STATUS + " (" +
                    COLUMN_HEALTH_STATUS_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_HEALTH_STATUS_DESC + " TEXT);";

    // SQL for creating User Health Status Table
    private static final String CREATE_USER_HEALTH_STATUS_TABLE =
            "CREATE TABLE " + TABLE_USER_HEALTH_STATUS + " (" +
                    COLUMN_USER_HEALTH_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_HEALTH_STATUS_ID + " TEXT, " +
                    COLUMN_USER_HEALTH_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_HEALTH_STATUS_ID + ") REFERENCES " + TABLE_HEALTH_STATUS + "(" + COLUMN_HEALTH_STATUS_ID + "));";

    // SQL for creating Language Table
    private static final String CREATE_LANGUAGE_TABLE =
            "CREATE TABLE " + TABLE_LANGUAGE + " (" +
                    COLUMN_LANGUAGE_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_LANGUAGE_NAME + " TEXT, " +
                    COLUMN_LANGUAGE_CODE + " TEXT);";

    // SQL for creating User Language Setting Table
    private static final String CREATE_USER_LANGUAGE_SETTING_TABLE =
            "CREATE TABLE " + TABLE_USER_LANGUAGE_SETTING + " (" +
                    COLUMN_USER_LANGUAGE_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_LANGUAGE_ID + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_LANGUAGE_ID + ") REFERENCES " + TABLE_LANGUAGE + "(" + COLUMN_LANGUAGE_ID + "));";

    // SQL for creating Privacy Policy Table
    private static final String CREATE_PRIVACY_POLICY_TABLE =
            "CREATE TABLE " + TABLE_PRIVACY_POLICY + " (" +
                    COLUMN_PRIVACY_POLICY_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_PRIVACY_POLICY_CONTENT + " TEXT, " +
                    COLUMN_PRIVACY_POLICY_LAST_UPDATED + " TIMESTAMP);";

    // SQL for creating HydrationGoal Table
    private static final String CREATE_HYDRATION_GOAL_TABLE =
            "CREATE TABLE " + TABLE_HYDRATION_GOAL + " (" +
                    COLUMN_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_GOAL_DATE + " TEXT, " +
                    COLUMN_HYDRATION_GOAL + " INTEGER, " +  // Hydration goal in ml
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating HydrationIntake Table
    private static final String CREATE_HYDRATION_INTAKE_TABLE =
            "CREATE TABLE " + TABLE_HYDRATION_INTAKE + " (" +
                    COLUMN_INTAKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_GOAL_ID + " INTEGER, " +
                    COLUMN_INTAKE_TIMESTAMP + " TEXT, " +
                    COLUMN_QUANTITY_OF_WATER + " INTEGER, " +
                    COLUMN_GOAL_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_GOAL_ID + ") REFERENCES " + TABLE_HYDRATION_GOAL + "(" + COLUMN_GOAL_ID + "));";

    // SQL for creating StepTracking Table
    private static final String CREATE_STEP_TRACKING_TABLE =
            "CREATE TABLE " + TABLE_STEP_TRACKING + " (" +
                    COLUMN_STEP_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_STEP_GOAL + " INTEGER, " +
                    COLUMN_STEP_COUNT + " INTEGER, " +
                    COLUMN_DISTANCE_WALKED + " DOUBLE, " +
                    COLUMN_CALORIES_BURNED + " DOUBLE, " +
                    COLUMN_STEP_TRACK_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating Mood Log Table
    private static final String CREATE_MOOD_LOG_TABLE =
            "CREATE TABLE " + TABLE_MOOD_LOG + " (" +
                    COLUMN_MOOD_DATE + " TEXT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_MOOD_TYPE + " TEXT, " +
                    "PRIMARY KEY(" + COLUMN_MOOD_DATE + ", " + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";

    // SQL for creating Journal Table
    private static final String CREATE_JOURNAL_TABLE =
            "CREATE TABLE " + TABLE_JOURNAL + " (" +
                    COLUMN_JOURNAL_DATE + " TEXT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_JOURNAL_PHOTO_PATH + " BLOB, " +
                    COLUMN_JOURNAL_WEATHER + " TEXT, " +
                    COLUMN_JOURNAL_NOTE + " TEXT, " +
                    "PRIMARY KEY(" + COLUMN_JOURNAL_DATE + ", " + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";

    // SQL for creating Mindfulness Exercise Table
    private static final String CREATE_MINDFULNESS_EXERCISE_TABLE =
            "CREATE TABLE " + TABLE_MIND_FULNESS_EXERCISE + " (" +
                    COLUMN_MINDFULNESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_MINDFULNESS_TYPE + " TEXT, " +
                    COLUMN_MINDFULNESS_DURATION + " TEXT, " +
                    COLUMN_MINDFULNESS_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    //SQL for creating Fitness Setting Table
    private static final String CREATE_FITNESS_SETTING_TABLE =
            "CREATE TABLE " + TABLE_FITNESS_SETTING + " (" +
                    COLUMN_FITNESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_TIME_FRAME + " TEXT NOT NULL CHECK(" + COLUMN_TIME_FRAME + " IN ('Daily', 'Weekly', 'Monthly')), " +
                    COLUMN_FITNESS_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    //SQL for creating Goal Settings Table
    // SQL for creating Goal Settings Table
    private static final String CREATE_GOAL_SETTING_TABLE =
            "CREATE TABLE " + TABLE_GOAL_SETTING + " (" +
                    COLUMN_FITNESS_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GOAL_FITNESS_ID + " INTEGER NOT NULL, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_GOAL_EXERCISE_TYPE + " TEXT NOT NULL, " +
                    COLUMN_GOAL_ATTRIBUTES + " TEXT, " +
                    COLUMN_GOAL_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (" + COLUMN_GOAL_FITNESS_ID + ") REFERENCES " + TABLE_FITNESS_SETTING + "(" + COLUMN_FITNESS_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL to create Exercise Log Table
    private static final String CREATE_EXERCISE_LOG_TABLE =
            "CREATE TABLE " + TABLE_EXERCISE_LOG + " (" +
                    COLUMN_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_LOG_DATE + " TEXT NOT NULL, " +
                    COLUMN_LOG_EXERCISE_TYPE + " TEXT NOT NULL, " +
                    COLUMN_LOG_ATTRIBUTES + " TEXT, " +
                    COLUMN_LOG_FITNESS_ID + " INTEGER NOT NULL, " +
                    COLUMN_LOG_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (" + COLUMN_LOG_FITNESS_ID + ") REFERENCES " + TABLE_FITNESS_SETTING + "(" + COLUMN_FITNESS_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;"); // Enable foreign key support
        // Create tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_PROFILE_TABLE);
        db.execSQL(CREATE_HEALTH_STATUS_TABLE);
        db.execSQL(CREATE_USER_HEALTH_STATUS_TABLE);
        db.execSQL(CREATE_LANGUAGE_TABLE);
        db.execSQL(CREATE_USER_LANGUAGE_SETTING_TABLE);
        db.execSQL(CREATE_PRIVACY_POLICY_TABLE);
        db.execSQL(CREATE_HYDRATION_GOAL_TABLE);
        db.execSQL(CREATE_HYDRATION_INTAKE_TABLE);
        db.execSQL(CREATE_STEP_TRACKING_TABLE);
        db.execSQL(CREATE_MOOD_LOG_TABLE);
        db.execSQL(CREATE_JOURNAL_TABLE);
        db.execSQL(CREATE_MINDFULNESS_EXERCISE_TABLE);
        db.execSQL(CREATE_FITNESS_SETTING_TABLE);
        db.execSQL(CREATE_GOAL_SETTING_TABLE);
        db.execSQL(CREATE_EXERCISE_LOG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_HEALTH_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LANGUAGE_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIVACY_POLICY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HYDRATION_GOAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HYDRATION_INTAKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEP_TRACKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOOD_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MIND_FULNESS_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_FITNESS_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_GOAL_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_EXERCISE_LOG);
        onCreate(db);
    }
    public void saveHydrationGoal(String userId, String date, int hydrationGoal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserID", userId);  // Replace with actual user ID
        values.put("Date", date);
        values.put("HydrationGoal", hydrationGoal);

        // Insert or update the goal
        db.replace(TABLE_HYDRATION_GOAL, null, values);
        db.close();
    }

    public int getHydrationGoal(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HYDRATION_GOAL, new String[]{"HydrationGoal"},
                "UserID = ? AND Date = ?", new String[]{userId, date}, null, null, null);

        int goal = -1; // Default value if no goal is found

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("HydrationGoal");
                if (columnIndex != -1) {
                    goal = cursor.getInt(columnIndex);
                }
            }
            cursor.close();
        }

        return goal;
    }

    // Method to save hydration intake
    public boolean saveHydrationIntake(String userId, String date, String timeStamp, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserID", userId);
        values.put("Date", date);
        values.put("IntakeTimeStamp", timeStamp);
        values.put("QuantityofWater", quantity);

        long result = db.insert(TABLE_HYDRATION_INTAKE, null, values);
        db.close();
        return result != -1;
    }

    // Method to fetch hydration intake data
    public List<HydrationIntakeModel> getTodayHydrationRecords(String userId, String todayDate) {
        List<HydrationIntakeModel> intakeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query only today's hydration intake
        Cursor cursor = db.query(TABLE_HYDRATION_INTAKE, new String[]{"IntakeTimeStamp", "QuantityofWater"},
                "UserID = ? AND Date = ?", new String[]{userId, todayDate}, null, null, "IntakeTimeStamp ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int intakeIimeStampIndex= cursor.getColumnIndex("IntakeTimeStamp");
                int quantityOfWaterIndex = cursor.getColumnIndex("QuantityofWater");
                String intakeTimestamp = cursor.getString(intakeIimeStampIndex);
                int quantityOfWater = cursor.getInt(quantityOfWaterIndex);
                HydrationIntakeModel intake = new HydrationIntakeModel(intakeTimestamp, quantityOfWater);
                intakeList.add(intake);
            }
            cursor.close();
        }

        return intakeList;
    }

    public int getTotalWaterIntake(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HYDRATION_INTAKE, new String[]{"QuantityofWater"},
                "UserID = ? AND Date = ?", new String[]{userId, date}, null, null, null);

        int totalIntake = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int quantityOfWaterIndex = cursor.getColumnIndex("QuantityofWater");
                int quantityOfWater = cursor.getInt(quantityOfWaterIndex);
                totalIntake += quantityOfWater;
            }
            cursor.close();
        }

        return totalIntake;
    }

    public Map<String, Integer> getTotalWaterIntakeByDay(String userId, String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Integer> dailyIntake = new LinkedHashMap<>();

        // Query to sum water intake grouped by Date
        String query = "SELECT Date, SUM(QuantityofWater) AS TotalIntake " +
                "FROM " + TABLE_HYDRATION_INTAKE +
                " WHERE UserID = ? AND Date BETWEEN ? AND ? " +
                "GROUP BY Date ORDER BY Date";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{userId, startDate, endDate});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int DateIndex=cursor.getColumnIndex("Date");
                int TotalIntakeIndex=cursor.getColumnIndex("TotalIntake");
                String date = cursor.getString(DateIndex);
                int totalIntake = cursor.getInt(TotalIntakeIndex);
                dailyIntake.put(date, totalIntake); // Store date and total intake in the map
            }
            cursor.close();
        }

        return dailyIntake; // Return totals grouped by date
    }
    public void saveStepData(String userId, int stepGoal, int stepCount, float distanceWalked, float caloriesBurned, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_STEP_GOAL, stepGoal);
        values.put(COLUMN_STEP_COUNT, stepCount);
        values.put(COLUMN_DISTANCE_WALKED, distanceWalked);
        values.put(COLUMN_CALORIES_BURNED, caloriesBurned);
        values.put(COLUMN_STEP_TRACK_DATE, date);

        db.insert(TABLE_STEP_TRACKING, null, values);
        db.close();
    }

    // Method to load the last saved data
    public Cursor loadLastStepData(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STEP_TRACKING, null, COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, COLUMN_STEP_TRACK_DATE + " DESC", "1");
    }


    public void saveDailyStepTracking(String userId, String date, int stepCount, float distanceWalked, float caloriesBurned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("UserID", userId);
        values.put("Date", date);
        values.put("StepCount", stepCount);
        values.put("DistanceWalked", distanceWalked);
        values.put("CaloriesBurned", caloriesBurned);

        // Insert or replace the record for the given date
        db.insertWithOnConflict(TABLE_STEP_TRACKING, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Map<String, Map<String, Object>> getTotalStepCount(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Map<String, Object>> history = new LinkedHashMap<>();

        String query = "SELECT Date, StepCount, DistanceWalked, CaloriesBurned FROM " + TABLE_STEP_TRACKING +
                " WHERE UserID = ? ORDER BY Date DESC";

        Cursor cursor = db.rawQuery(query, new String[]{userId});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int DateIndex=cursor.getColumnIndex("Date");
                int StepsCountIndex=cursor.getColumnIndex("StepCount");
                String date = cursor.getString(DateIndex);
                int steps = cursor.getInt(StepsCountIndex);

                Map<String, Object> dailyData = new HashMap<>();
                dailyData.put("Steps", steps);
                history.put(date, dailyData);
            }
            cursor.close();
        }

        return history;
    }

    public int getStepGoalId(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("StepTracking", new String[]{"StepGoalID"}, "UserID = ? AND Date = ?", new String[]{userId, date}, null, null, null);
        int stepGoalId = -1;

        if (cursor != null && cursor.moveToFirst()) {
            int StepGoalIDIndex=cursor.getColumnIndex("StepGoalID");
            stepGoalId = cursor.getInt(StepGoalIDIndex);
            cursor.close();
        }

        return stepGoalId;
    }

    public void saveStepGoal(String userId, String date, int goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        int existingGoal = getStepGoal(userId, date);

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_STEP_TRACK_DATE, date);
        values.put(COLUMN_STEP_GOAL, goal);

        if (existingGoal == -1) {
            db.insert(TABLE_STEP_TRACKING, null, values);
        } else {
            db.update(TABLE_STEP_TRACKING, values, COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?",
                    new String[]{userId, date});
        }
    }

    public int getStepGoal(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        int goal = -1;

        // Query the database for the user's goal on the given date
        String query = "SELECT " + COLUMN_STEP_GOAL + " FROM " + TABLE_STEP_TRACKING +
                " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId, date});

        if (cursor != null && cursor.moveToFirst()) {
            // If a record exists, get the goal value
            int goalIndex = cursor.getColumnIndex(COLUMN_STEP_GOAL);
            goal = cursor.getInt(goalIndex);
            cursor.close();
        }

        return goal;
    }

    public void saveOrUpdateStepTrackingData(String userId, int stepGoal, int stepCount, float distanceWalked, float caloriesBurned, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_STEP_COUNT, stepCount);
        values.put(COLUMN_DISTANCE_WALKED, distanceWalked);
        values.put(COLUMN_CALORIES_BURNED, caloriesBurned);
        values.put(COLUMN_STEP_TRACK_DATE, date);

        // Check if a goal is already set for this user and date
        int existingGoal = getStepGoal(userId, date);

        // If no goal is set for the day, insert the step goal
        if (existingGoal == -1) {
            // If there is no goal set for the day, insert the goal along with the other tracking data
            values.put(COLUMN_STEP_GOAL, stepGoal);  // Add step goal if it doesn't exist
            db.insert(TABLE_STEP_TRACKING, null, values);
        } else {
            // If a goal is already set for the day, don't update the goal, just update the other tracking data
            db.update(TABLE_STEP_TRACKING, values, COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?",
                    new String[]{userId, date});
        }
    }

    public int getTotalStepsForDay(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STEP_TRACKING, new String[]{COLUMN_STEP_COUNT}, COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?",
                new String[]{userId, date}, null, null, null);

        int totalSteps = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int StepCountIndex=cursor.getColumnIndex(COLUMN_STEP_COUNT);
            totalSteps = cursor.getInt(StepCountIndex);
            cursor.close();
        }

        return totalSteps;
    }

    public float getDistanceWalkedForDay(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        float distanceWalked = 0.0f;

        // Query the database for the user's distance walked on the given date
        String query = "SELECT " + COLUMN_DISTANCE_WALKED +
                " FROM " + TABLE_STEP_TRACKING +
                " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, new String[]{userId, date});

            if (cursor != null && cursor.moveToFirst()) {
                int distanceWalkedIndex = cursor.getColumnIndex(COLUMN_DISTANCE_WALKED);
                distanceWalked = cursor.getFloat(distanceWalkedIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close(); // Ensure the cursor is closed
            }
        }

        return distanceWalked;
    }


    public float getCaloriesBurnedForDay(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        float caloriesBurned = 0.0f;

        // Query the database for the user's calories burned on the given date
        String query = "SELECT " + COLUMN_CALORIES_BURNED +
                " FROM " + TABLE_STEP_TRACKING +
                " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId, date});

        if (cursor != null && cursor.moveToFirst()) {
            int caloriesBurnedIndex = cursor.getColumnIndex(COLUMN_CALORIES_BURNED);
            caloriesBurned = cursor.getFloat(caloriesBurnedIndex);
            cursor.close();
        }

        return caloriesBurned;
    }
    public void saveMood(String userId, String date, String mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_MOOD_DATE, date);
        values.put(COLUMN_MOOD_TYPE, mood);
        db.insertWithOnConflict(TABLE_MOOD_LOG, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public String getMood(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOOD_LOG,
                new String[]{COLUMN_MOOD_TYPE},
                COLUMN_USER_ID + "=? AND " + COLUMN_MOOD_DATE + "=?",
                new String[]{userId, date},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String mood = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOOD_TYPE));
            cursor.close();
            return mood;
        }

        return null;
    }

    public void saveJournal(String userID, String date, byte[] newPhoto, String newWeather, String newNote) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Retrieve existing data for the date
        Cursor cursor = getJournal(userID, date);
        byte[] existingPhoto = null;
        String existingWeather = null;
        String existingNote = null;

        if (cursor != null && cursor.moveToFirst()) {
            existingPhoto = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_PHOTO_PATH));
            existingWeather = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_WEATHER));
            existingNote = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_NOTE));
            cursor.close();
        }

        // Use new data if provided; otherwise, use existing data
        byte[] finalPhoto = (newPhoto != null) ? newPhoto : existingPhoto;
        String finalWeather = (newWeather != null && !newWeather.isEmpty()) ? newWeather : existingWeather;
        String finalNote = (newNote != null && !newNote.isEmpty()) ? newNote : existingNote;

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userID);
        values.put(COLUMN_JOURNAL_DATE, date);
        values.put(COLUMN_JOURNAL_PHOTO_PATH, finalPhoto);
        values.put(COLUMN_JOURNAL_WEATHER, finalWeather);
        values.put(COLUMN_JOURNAL_NOTE, finalNote);

        db.insertWithOnConflict(TABLE_JOURNAL, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getJournal(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_JOURNAL,
                null,
                COLUMN_USER_ID + "=? AND " + COLUMN_JOURNAL_DATE + "=?",
                new String[]{userId, date},
                null, null, null
        );
    }


    public byte[] getPhoto(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] photo = null;

        // Query to get the photo column for the given userId and date
        Cursor cursor = db.query(
                TABLE_JOURNAL,
                new String[]{COLUMN_JOURNAL_PHOTO_PATH}, // Only retrieve the photo column
                COLUMN_USER_ID + "=? AND " + COLUMN_JOURNAL_DATE + "=?", // Where clause
                new String[]{userId, date},         // Arguments for the where clause
                null,                               // Group by
                null,                               // Having
                null                                // Order by
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                photo = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_PHOTO_PATH));
            }
            cursor.close();
        }

        return photo; // Returns null if no photo is found
    }

    public Cursor getLatestFitnessSettingAndGoals(SQLiteDatabase db) {
        String query = "SELECT " +
                "g." + COLUMN_GOAL_EXERCISE_TYPE + " AS ExerciseType, " +
                "g." + COLUMN_GOAL_ATTRIBUTES + " AS Attributes " +
                "FROM " + TABLE_GOAL_SETTING + " g " +
                "JOIN " + TABLE_FITNESS_SETTING + " f ON g." + COLUMN_GOAL_FITNESS_ID + " = f." + COLUMN_FITNESS_ID + " " +
                "WHERE f." + COLUMN_FITNESS_ID + " = (SELECT MAX(" + COLUMN_FITNESS_ID + ") FROM " + TABLE_FITNESS_SETTING + ")";
        return db.rawQuery(query, null);
    }






}




