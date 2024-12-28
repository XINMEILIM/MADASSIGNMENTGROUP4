package com.example.real.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class DatabaseHelper_module1 extends SQLiteOpenHelper {

    private Context context;

    // Database Name and Version
    private static final String DATABASE_NAME = "FitJourney.db";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    public static final String TABLE_USER_PROFILE = "UserProfile";

    // User Profile Columns
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_USERNAME = "Name";
    public static final String COLUMN_AGE = "Age";
    public static final String COLUMN_GENDER = "Gender";
    public static final String COLUMN_HEIGHT = "Height";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_YEAR_OF_BIRTH = "YearOfBirth";

    // SQL for creating UserProfile Table
    private static final String CREATE_USER_PROFILE_TABLE =
            "CREATE TABLE " + TABLE_USER_PROFILE + " (" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_AGE + " INTEGER, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_HEIGHT + " DOUBLE, " +
                    COLUMN_WEIGHT + " DOUBLE, " +
                    COLUMN_YEAR_OF_BIRTH + " INTEGER);";

    // Table for Privacy Policy
    private static final String TABLE_PRIVACY_POLICY = "PrivacyPolicy";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_LAST_UPDATED = "lastUpdated";

    // Table Names
    public static final String TABLE_LANGUAGE = "Language";
    public static final String TABLE_USER_LANGUAGE_SETTINGS = "UserLanguageSettings";

    // Columns for Language Table
    public static final String COLUMN_LANGUAGE_ID = "idlanguage";
    public static final String COLUMN_LANGUAGE_CODE = "code";

    // Columns for UserLanguageSettings Table
    public static final String COLUMN_USER_LANGUAGE_ID = "iduserlanguage";

    // SQL for creating Language Table
    private static final String CREATE_LANGUAGE_TABLE =
            "CREATE TABLE " + TABLE_LANGUAGE + " (" +
                    COLUMN_LANGUAGE_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_LANGUAGE_CODE + " TEXT NOT NULL);";

    // SQL for creating UserLanguageSettings Table
    private static final String CREATE_USER_LANGUAGE_SETTINGS_TABLE =
            "CREATE TABLE " + TABLE_USER_LANGUAGE_SETTINGS + " (" +
                    COLUMN_USER_LANGUAGE_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_ID + " TEXT NOT NULL, " +
                    COLUMN_LANGUAGE_ID + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER_PROFILE + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_LANGUAGE_ID + ") REFERENCES " + TABLE_LANGUAGE + "(" + COLUMN_LANGUAGE_ID + "));";

    // Table names
    public static final String TABLE_HEALTH_STATUS = "HealthStatus";
    public static final String TABLE_USER_HEALTH_STATUS = "UserHealthStatus";

    // HealthStatus table columns
    public static final String COLUMN_ID_HEALTH_STATUS = "IDHealthStatus";
    public static final String COLUMN_DESCRIPTION = "Description";

    // UserHealthStatus table columns
    public static final String COLUMN_ID_USER_HEALTH_STATUS = "IDUserHealthStatus";
    public static final String COLUMN_HEALTH_STATUS_ID = "HealthStatusID";
    public static final String COLUMN_DATE = "Date";

    // SQL for creating HealthStatus table
    private static final String CREATE_HEALTH_STATUS_TABLE =
            "CREATE TABLE " + TABLE_HEALTH_STATUS + " (" +
                    COLUMN_ID_HEALTH_STATUS + " TEXT PRIMARY KEY, " +
                    COLUMN_DESCRIPTION + " TEXT NOT NULL);";

    // SQL for creating UserHealthStatus table
    private static final String CREATE_USER_HEALTH_STATUS_TABLE =
            "CREATE TABLE " + TABLE_USER_HEALTH_STATUS + " (" +
                    COLUMN_ID_USER_HEALTH_STATUS + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_ID + " TEXT NOT NULL, " +
                    COLUMN_HEALTH_STATUS_ID + " TEXT NOT NULL, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER_PROFILE + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_HEALTH_STATUS_ID + ") REFERENCES " + TABLE_HEALTH_STATUS + "(" + COLUMN_ID_HEALTH_STATUS + "));";

    public DatabaseHelper_module1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_USER_PROFILE_TABLE);

        // Insert initial test data
        db.execSQL("INSERT INTO " + TABLE_USER_PROFILE + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_USERNAME + ", " +
                COLUMN_AGE + ", " +
                COLUMN_GENDER + ", " +
                COLUMN_HEIGHT + ", " +
                COLUMN_WEIGHT + ", " +
                COLUMN_YEAR_OF_BIRTH +
                ") VALUES ('1', 'Test User', 25, 'Male', 180.5, 75.0, 1998);");

        Log.d("Database", "Database created with initial test data");

        // Create Privacy Policy table
        String createPrivacyPolicyTable = "CREATE TABLE " + TABLE_PRIVACY_POLICY + " ("
                + COLUMN_ID + " TEXT PRIMARY KEY, "
                + COLUMN_CONTENT + " TEXT, "
                + COLUMN_LAST_UPDATED + " TEXT);";
        db.execSQL(createPrivacyPolicyTable);

        db.execSQL(CREATE_LANGUAGE_TABLE);
        db.execSQL(CREATE_USER_LANGUAGE_SETTINGS_TABLE);
        db.execSQL(CREATE_HEALTH_STATUS_TABLE);
        db.execSQL(CREATE_USER_HEALTH_STATUS_TABLE);

        // Insert initial health statuses
        db.execSQL("INSERT INTO " + TABLE_HEALTH_STATUS + " VALUES ('001', 'ACTIVE');");
        db.execSQL("INSERT INTO " + TABLE_HEALTH_STATUS + " VALUES ('002', 'SICK');");
        db.execSQL("INSERT INTO " + TABLE_HEALTH_STATUS + " VALUES ('003', 'TAKE A BREAK');");
        db.execSQL("INSERT INTO " + TABLE_HEALTH_STATUS + " VALUES ('004', 'INJURED');");

        // Populate the Language table
        insertInitialLanguages(db);

        // Insert initial privacy policy data
        String initialContent = "At Tracker, we prioritize your privacy by protecting your personal information and explaining our practices clearly. "
                + "We collect data like personal details, health metrics, location (if enabled), and device information to improve our services, "
                + "personalize your experience, and track your fitness progress. "
                + "We may share information with trusted third-party providers to enhance app functionality, and we only disclose data as legally required. "
                + "We implement strong security measures to protect your data but note that no online system is entirely secure. "
                + "You can access, update, or delete your data, and manage notification preferences within the app. "
                + "Our app is not intended for children under 13, and we retain data only as needed. Updates to this policy will be communicated "
                + "through the app, and you can reach us with any questions at tracker@gmail.com.";

        String initialID = UUID.randomUUID().toString();
        String initialDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String insertPrivacyPolicy = "INSERT INTO " + TABLE_PRIVACY_POLICY + " ("
                + COLUMN_ID + ", "
                + COLUMN_CONTENT + ", "
                + COLUMN_LAST_UPDATED + ") VALUES ('"
                + initialID + "', '"
                + initialContent + "', '"
                + initialDate + "');";
        db.execSQL(insertPrivacyPolicy);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIVACY_POLICY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE);

        onCreate(db);
    }

    // Insert data for a new user profile
    public void saveUserProfile(String userId, String username, int age, String gender, double height, double weight, int yearOfBirth) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_ID, userId);
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_HEIGHT, height);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_YEAR_OF_BIRTH, yearOfBirth);

        long result = db.insert(TABLE_USER_PROFILE, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to save profile", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Profile saved successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to fetch all user profiles
    public Cursor getAllUserProfiles() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER_PROFILE, null);
    }

    // Method to log all user profiles for debugging
    public void logAllProfiles() {
        Cursor cursor = getAllUserProfiles();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                int age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
                double height = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_HEIGHT));
                double weight = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT));
                int yearOfBirth = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR_OF_BIRTH));

                Log.d("DatabaseDebug", "UserID: " + userId +
                        ", Name: " + name +
                        ", Age: " + age +
                        ", Gender: " + gender +
                        ", Height: " + height +
                        ", Weight: " + weight +
                        ", YearOfBirth: " + yearOfBirth);
            } while (cursor.moveToNext());
        } else {
            Log.d("DatabaseDebug", "No profiles found in database");
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    // Method to fetch a user profile by username
    public Cursor getUserProfileByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_USER_PROFILE + " WHERE " + COLUMN_USERNAME + " = ?",
                new String[]{username}
        );
    }

    public void clearUserProfileTable() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            if (db != null) {
                db.execSQL("DELETE FROM " + TABLE_USER_PROFILE);
                Log.d("Database", "User profile table cleared successfully.");
            } else {
                Log.e("Database", "Writable database is null.");
            }
        } catch (Exception e) {
            Log.e("Database", "Error clearing user profile table", e);
        }
    }

    public String[] getPrivacyPolicy() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PRIVACY_POLICY + " LIMIT 1;";
        android.database.Cursor cursor = db.rawQuery(query, null);

        String[] result = new String[2];
        if (cursor.moveToFirst()) {
            result[0] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));
            result[1] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_UPDATED));
        }
        cursor.close();
        return result;
    }

    private void insertInitialLanguages(SQLiteDatabase db) {
        insertLanguage(db, UUID.randomUUID().toString(), "en");
        insertLanguage(db, UUID.randomUUID().toString(), "ms");
        insertLanguage(db, UUID.randomUUID().toString(), "hi");
        insertLanguage(db, UUID.randomUUID().toString(), "zh");
    }

    private void insertLanguage(SQLiteDatabase db, String idlanguage, String code) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANGUAGE_ID, idlanguage);
        values.put(COLUMN_LANGUAGE_CODE, code);
        db.insert(TABLE_LANGUAGE, null, values);
    }

    public void saveUserLanguageSetting(String username, String languageId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Fetch the userid from UserProfile table
        String userId = getUserIdByUsername(username);

        if (userId == null) {
            Log.e("Database", "User ID not found for username: " + username);
            return;
        }
        // Insert into UserLanguageSettings table
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_LANGUAGE_ID, UUID.randomUUID().toString()); // Random ID for the setting
        values.put(COLUMN_USER_ID, userId); // Fetched User ID
        values.put(COLUMN_LANGUAGE_ID, languageId); // Selected Language ID

        long result = db.insert(TABLE_USER_LANGUAGE_SETTINGS, null, values);
        if (result == -1) {
            Log.e("Database", "Failed to save user language setting");
        } else {
            Log.d("Database", "User language setting saved successfully");
        }
    }

    private String getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USER_PROFILE + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        String userId = null;
        if (cursor.moveToFirst()) {
            userId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
        }
        cursor.close();
        return userId;
    }

    public String getLanguageIdByCode(String languageCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_LANGUAGE_ID + " FROM " + TABLE_LANGUAGE + " WHERE " + COLUMN_LANGUAGE_CODE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{languageCode});

        String languageId = null;
        if (cursor.moveToFirst()) {
            languageId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE_ID));
        }
        cursor.close();
        return languageId;
    }

    // Save UserHealthStatus
    public void saveUserHealthStatus(String userHealthStatusID, String userID, String healthStatusID, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID_USER_HEALTH_STATUS, userHealthStatusID);
        cv.put(COLUMN_USER_ID, userID);
        cv.put(COLUMN_HEALTH_STATUS_ID, healthStatusID);
        cv.put(COLUMN_DATE, date);

        long result = db.insert(TABLE_USER_HEALTH_STATUS, null, cv);
        if (result == -1) {
            Log.e("Database", "Failed to save user health status");
        } else {
            Log.d("Database", "User health status saved successfully");
        }
    }

    // Fetch UserHealthStatus
    public Cursor getUserHealthStatus(String userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_USER_HEALTH_STATUS + " WHERE " + COLUMN_USER_ID + " = ?",
                new String[]{userID});
    }



}
