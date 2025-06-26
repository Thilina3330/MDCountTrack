package com.s23010733.md_count_track;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database info
    public static final String DB_NAME = "MDCountTrack.db";
    public static final int DB_VERSION = 2;

    //Accepted data table
    public static final String ACCEPT_TABLE = "accepted_data";
    public static final String COL_ID = "id";
    public static final String COL_BARCODE = "barcode";
    public static final String COL_QTY = "quantity";
    public static final String COL_SHIFT = "shift";
    public static final String COL_TIMESTAMP = "timestamp";

    //  Users table (for login/signup)
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Accepted data table
        String createAcceptTable = "CREATE TABLE " + ACCEPT_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BARCODE + " TEXT, " +
                COL_QTY + " INTEGER, " +
                COL_SHIFT + " TEXT, " +
                COL_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createAcceptTable);

        // Users table
        String createUserTable = "CREATE TABLE " + USER_TABLE + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_EMAIL + " TEXT UNIQUE, " +
                USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate tables
        db.execSQL("DROP TABLE IF EXISTS " + ACCEPT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    //  Insert new user (for Sign In)
    public boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);

        long result = db.insert(USER_TABLE, null, values);
        db.close();
        return result != -1;
    }

    //  Check if user email already exists (for Sign In)
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                USER_TABLE,
                new String[]{USER_ID},
                USER_EMAIL + "=?",
                new String[]{email},
                null, null, null
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    //  Check email/password match (for Login)
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                USER_TABLE,
                new String[]{USER_ID},
                USER_EMAIL + "=? AND " + USER_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Optional: Insert accepted barcode data
    public void insertAcceptedData(String barcode, String qty, String shift) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BARCODE, barcode);
        values.put(COL_QTY, qty);
        values.put(COL_SHIFT, shift);
        db.insert(ACCEPT_TABLE, null, values);
        db.close();
    }
}
