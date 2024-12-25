package com.example.real.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "FitJourney.db";
    private static final int DATABASE_VERSION = 1;

    // User table
    public static final String TABLE_USER = "User";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_USER_NAME = "UserName";

    // Health status table
    public static final String TABLE_HEALTH_STATUS = "HealthStatus";
    public static final String COLUMN_HEALTH_STATUS_ID = "HealthStatusID";
    public static final String COLUMN_HEALTH_STATUS_DESC = "HealthStatusDesc";

    // User health status table
    public static final String TABLE_USER_HEALTH_STATUS = "UserHealthStatus";
    public static final String COLUMN_USER_HEALTH_STATUS_ID = "UserHealthStatusID";
    public static final String COLUMN_USER_HEALTH_DATE = "Date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User table
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT);";
        db.execSQL(createUserTable);

        // Create Health Status table
        String createHealthStatusTable = "CREATE TABLE " + TABLE_HEALTH_STATUS + " (" +
                COLUMN_HEALTH_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HEALTH_STATUS_DESC + " TEXT);";
        db.execSQL(createHealthStatusTable);

        // Create User Health Status table
        String createUserHealthStatusTable = "CREATE TABLE " + TABLE_USER_HEALTH_STATUS + " (" +
                COLUMN_USER_HEALTH_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_HEALTH_STATUS_ID + " INTEGER, " +
                COLUMN_USER_HEALTH_DATE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_HEALTH_STATUS_ID + ") REFERENCES " + TABLE_HEALTH_STATUS + "(" + COLUMN_HEALTH_STATUS_ID + "));";
        db.execSQL(createUserHealthStatusTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_HEALTH_STATUS);
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
    public void saveHealthStatus(String healthStatusDesc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEALTH_STATUS_DESC, healthStatusDesc);
        db.insert(TABLE_HEALTH_STATUS, null, values);
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
}
