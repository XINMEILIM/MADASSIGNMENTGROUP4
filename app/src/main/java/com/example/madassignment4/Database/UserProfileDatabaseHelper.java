package com.example.real.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserProfileDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userProfile.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "UserProfile";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_AGE = "Age";
    public static final String COLUMN_GENDER = "Gender";
    public static final String COLUMN_HEIGHT = "Height";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_YEAR_OF_BIRTH = "YearOfBirth";
    public static final String COLUMN_UPDATED_AT = "UpdatedAt";

    public UserProfileDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_NAME + " TEXT,"
                + COLUMN_AGE + " INTEGER,"
                + COLUMN_GENDER + " TEXT,"
                + COLUMN_HEIGHT + " INTEGER,"
                + COLUMN_WEIGHT + " REAL,"
                + COLUMN_YEAR_OF_BIRTH + " INTEGER,"
                + COLUMN_UPDATED_AT + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to save user profile details
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
        long insert = db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Method to retrieve user profile details
    public Cursor getUserProfile() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Retrieve the user profile data, assuming only one row
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    // Method to get the current date and time
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
