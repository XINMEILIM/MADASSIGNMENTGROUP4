package com.example.real.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper_module1 extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "FitJourney.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_USER = "User";
    public static final String TABLE_USER_PROFILE = "UserProfile";
    public static final String TABLE_HEALTH_STATUS = "HealthStatus";
    public static final String TABLE_USER_HEALTH_STATUS = "UserHealthStatus";
    public static final String TABLE_LANGUAGE = "Language";
    public static final String TABLE_USER_LANGUAGE_SETTING = "UserLanguageSetting";
    public static final String TABLE_HYDRATION_GOAL = "HydrationGoal";
    public static final String TABLE_HYDRATION_INTAKE = "HydrationIntake";
    public static final String TABLE_STEP_TRACKING = "StepTracking";
    public static final String TABLE_MOOD_LOG = "MoodLog";
    public static final String TABLE_JOURNAL = "Journal";
    public static final String TABLE_MIND_FULNESS_EXERCISE = "MindfulnessExercise";
    public static final String TABLE_EXERCISE = "Exercise";
    public static final String TABLE_ACTIVITY = "Activity";
    public static final String TABLE_FITNESS_SETTING = "FitnessSetting";
    public static final String TABLE_PRIVACY_POLICY = "PrivacyPolicy";


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
    public static final String COLUMN_HEALTH_STATUS_DESC = "HealthStatus";

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
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_CONTENT = "Content";

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

    public DatabaseHelper_module1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_CREATED_AT + " TIMESTAMP, " +
                COLUMN_LAST_LOGIN + " TIMESTAMP);");

        db.execSQL("CREATE TABLE " + TABLE_LANGUAGE + " (" +
                COLUMN_LANGUAGE_ID + " TEXT PRIMARY KEY, " +
                COLUMN_LANGUAGE_NAME + " TEXT, " +
                COLUMN_LANGUAGE_CODE + " TEXT);");

        db.execSQL("CREATE TABLE " + TABLE_HYDRATION_GOAL + " (" +
                COLUMN_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_GOAL_DATE + " TEXT, " +
                COLUMN_HYDRATION_GOAL + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));");

        db.execSQL("CREATE TABLE " + TABLE_USER_LANGUAGE_SETTING + " (" +
                COLUMN_USER_LANGUAGE_ID + " TEXT PRIMARY KEY, " +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_LANGUAGE_ID + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY (" + COLUMN_LANGUAGE_ID + ") REFERENCES " + TABLE_LANGUAGE + "(" + COLUMN_LANGUAGE_ID + "));");

            // Create LastUpdated table
            String createLastUpdatedTable = "CREATE TABLE LastUpdated ("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "Version TEXT)";
            db.execSQL(createLastUpdatedTable);

            // Insert default last updated version
            ContentValues values = new ContentValues();
            values.put("Version", "2024-12-26");
            db.insert("LastUpdated", null, values);
        }


        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HYDRATION_GOAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LANGUAGE_SETTING);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIVACY_POLICY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);


            if (oldVersion < newVersion) {
                db.execSQL("CREATE TABLE IF NOT EXISTS PrivacyPolicy (ID INTEGER PRIMARY KEY AUTOINCREMENT, Content TEXT)");
            }


        // Drop other tables if needed
        onCreate(db);

    }

    // Insert a new user
    public void saveUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    // Fetch the first username
    public String getUsername() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_USER_NAME}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String username = cursor.getString(0);
            cursor.close();
            db.close();
            return username;
        }
        return null;
    }

    // Insert a health status
    public void saveHealthStatus(String username, String healthStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEALTH_STATUS_DESC, healthStatus);

        db.update(TABLE_USER, values, COLUMN_USER_NAME + "=?", new String[]{username});
        db.close();
    }

    // Save user health status
    public void saveUserHealthStatus(int userId, int healthStatusId, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_HEALTH_STATUS_ID, healthStatusId);
        values.put(COLUMN_USER_HEALTH_DATE, date);
        db.insert(TABLE_USER_HEALTH_STATUS, null, values);
        db.close();
    }

    // Fetch health status by user ID
    public Cursor getUserHealthStatus(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USER_HEALTH_STATUS, null, COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    // Get user ID by username
    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_USER_ID}, COLUMN_USER_NAME + "=?", new String[]{username}, null, null, null);
        int userId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return userId;
    }

    // Get health status ID by description
    public int getHealthStatusId(String healthStatusDesc) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HEALTH_STATUS, new String[]{COLUMN_HEALTH_STATUS_ID}, COLUMN_HEALTH_STATUS_DESC + "=?", new String[]{healthStatusDesc}, null, null, null);
        int healthStatusId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            healthStatusId = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return healthStatusId;
    }

    // Save User Profile
    public void saveUserProfile(String name, int age, String gender, int height, float weight, int yearOfBirth) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_YEAR_OF_BIRTH, yearOfBirth);
        values.put(COLUMN_UPDATED_AT, getCurrentDateTime());

        // Insert into the table
        long insert = db.insert(TABLE_USER_PROFILE, null, values);
        db.close();
    }

    // Method to retrieve user profile details
    public Cursor getUserProfile() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Retrieve the user profile data, assuming only one row
        return db.query(TABLE_USER_PROFILE, null, null, null, null, null, null);
    }

    // Method to get the current date and time
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private static final String CREATE_PRIVACY_POLICY_TABLE =
            "CREATE TABLE IF NOT EXISTS PrivacyPolicy (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Content TEXT NOT NULL);";

    // Privacy Policy Methods
    public void insertPrivacyPolicy(String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT, content);
        db.insert(TABLE_PRIVACY_POLICY, null, values);
        db.close();
    }

    public String getPrivacyPolicy() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRIVACY_POLICY, new String[]{COLUMN_CONTENT}, null, null, null, null, null);
        String policy = null;
        if (cursor != null && cursor.moveToFirst()) {
            policy = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));
            cursor.close();
        }
        db.close();
        return policy;
    }
    public void setLastUpdatedVersion(String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Version", version);

        db.replace("LastUpdated", null, values); // Replace if it exists, insert otherwise
    }

    public String getLastUpdatedVersion() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "LastUpdated",
                new String[]{"Version"},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String version = cursor.getString(0);
            cursor.close();
            return version;
        }

        if (cursor != null) {
            cursor.close();
        }

        return "Not Available"; // Default value if no version is set
    }


    // Insert Languages into Language Table
    public void insertLanguages() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] languages = {"English", "Arabic", "Spanish", "French", "Indonesian", "Russian", "German", "Italian", "Portuguese", "Thai", "Malay", "Chinese", "Hindi"};
        String[] languageCodes = {"en", "ar", "es", "fr", "id", "ru", "de", "it", "pt", "th", "ms", "zh", "hi"};

        for (int i = 0; i < languages.length; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_LANGUAGE_NAME, languages[i]);
            values.put(COLUMN_LANGUAGE_CODE, languageCodes[i]);
            db.insert(TABLE_LANGUAGE, null, values);
        }
        db.close();
    }

    // Set User Preferred Language
    public void setUserPreferredLanguage(String userId, int languageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_LANGUAGE_ID, languageId);
        db.insert(TABLE_USER_LANGUAGE_SETTING, null, values);
        db.close();
    }

    // Get User Preferred Language
    public String getUserPreferredLanguage(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_LANGUAGE_CODE + " FROM " + TABLE_LANGUAGE + " L " +
                "JOIN " + TABLE_USER_LANGUAGE_SETTING + " ULS ON L." + COLUMN_LANGUAGE_ID + " = ULS." + COLUMN_LANGUAGE_ID + " " +
                "WHERE ULS." + COLUMN_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId});
        String languageCode = null;
        if (cursor != null && cursor.moveToFirst()) {
            int index = cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE_CODE);
            languageCode = cursor.getString(index);
            cursor.close();
        }
        db.close();
        return languageCode;
    }
}



