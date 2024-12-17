package com.example.madassignment4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Name and Version
    private static final String DATABASE_NAME = "FitJourney.db";
    private static final int DATABASE_VERSION = 1;

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
    public static final String TABLE_EXERCISE = "Exercise";
    public static final String TABLE_ACTIVITY = "Activity";
    public static final String TABLE_FITNESS_SETTING = "FitnessSetting";


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
    public static final String COLUMN_MOOD_ID = "MoodID";
    public static final String COLUMN_MOOD_DATE = "MoodDate";
    public static final String COLUMN_MOOD_TYPE = "MoodType";

    // Journal Columns
    public static final String COLUMN_JOURNAL_ID = "JournalID";
    public static final String COLUMN_JOURNAL_DATE = "JournalDate";
    public static final String COLUMN_JOURNAL_PHOTO_PATH = "PhotoPath";
    public static final String COLUMN_JOURNAL_WEATHER = "Weather";
    public static final String COLUMN_JOURNAL_NOTE = "Note";

    // Mindfulness Exercise Columns
    public static final String COLUMN_MINDFULNESS_ID = "MindfulnessID";
    public static final String COLUMN_MINDFULNESS_TYPE = "MindfulnessType";
    public static final String COLUMN_MINDFULNESS_DURATION = "Duration";
    public static final String COLUMN_MINDFULNESS_DATE = "MindfulnessDate";

    // Exercise Columns
    public static final String COLUMN_EXERCISE_ID = "ExerciseID";
    public static final String COLUMN_EXERCISE_DATE = "Date";
    public static final String COLUMN_EXERCISE_TIME = "Time";
    public static final String COLUMN_EXERCISE_TYPE = "ExerciseType";
    public static final String COLUMN_EXERCISE_ATTRIBUTE = "Attribute";

    // Activity Columns
    public static final String COLUMN_ACTIVITY_ID = "ActivityID";
    public static final String COLUMN_ACTIVITY_TYPE = "ExerciseType";
    public static final String COLUMN_ACTIVITY_ATTRIBUTE = "Attribute";

    // Fitness Setting Columns
    public static final String COLUMN_FITNESS_SETTING_ID = "FitnessSettingID";
    public static final String COLUMN_FITNESS_SETTING_TIMELINE = "Timeline";


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
                    COLUMN_MOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_MOOD_DATE + " TEXT, " +
                    COLUMN_MOOD_TYPE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating Journal Table
    private static final String CREATE_JOURNAL_TABLE =
            "CREATE TABLE " + TABLE_JOURNAL + " (" +
                    COLUMN_JOURNAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_JOURNAL_DATE + " TEXT, " +
                    COLUMN_JOURNAL_PHOTO_PATH + " TEXT, " +
                    COLUMN_JOURNAL_WEATHER + " TEXT, " +
                    COLUMN_JOURNAL_NOTE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating Mindfulness Exercise Table
    private static final String CREATE_MINDFULNESS_EXERCISE_TABLE =
            "CREATE TABLE " + TABLE_MIND_FULNESS_EXERCISE + " (" +
                    COLUMN_MINDFULNESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_MINDFULNESS_TYPE + " TEXT, " +
                    COLUMN_MINDFULNESS_DURATION + " TEXT, " +
                    COLUMN_MINDFULNESS_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating Exercise Table
    private static final String CREATE_EXERCISE_TABLE =
            "CREATE TABLE " + TABLE_EXERCISE + " (" +
                    COLUMN_EXERCISE_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_EXERCISE_DATE + "TEXT," +
                    COLUMN_EXERCISE_TIME + "TEXT," +
                    COLUMN_EXERCISE_TYPE + " TEXT, " +
                    COLUMN_EXERCISE_ATTRIBUTE + " DOUBLE, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating Activity Table
    private static final String CREATE_ACTIVITY_TABLE =
            "CREATE TABLE " + TABLE_ACTIVITY + " (" +
                    COLUMN_ACTIVITY_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_ACTIVITY_TYPE + " TEXT, " +
                    COLUMN_ACTIVITY_ATTRIBUTE + " DOUBLE);";

    // SQL for creating Fitness Setting Table
    private static final String CREATE_FITNESS_SETTING_TABLE =
            "CREATE TABLE " + TABLE_FITNESS_SETTING + " (" +
                    COLUMN_FITNESS_SETTING_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_FITNESS_SETTING_TIMELINE + " INTEGER, " +
                    COLUMN_ACTIVITY_ID + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_ACTIVITY_ID + ") REFERENCES " + TABLE_ACTIVITY + "(" + COLUMN_ACTIVITY_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_ACTIVITY_TABLE);
        db.execSQL(CREATE_FITNESS_SETTING_TABLE);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FITNESS_SETTING);
        onCreate(db);
    }
}
