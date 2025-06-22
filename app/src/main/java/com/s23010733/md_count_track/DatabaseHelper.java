package com.s23010733.md_count_track;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database info
    public static final String DB_NAME = "MDCountTrack.db";
    public static final int DB_VERSION = 2; // bump version because adding new table

    // Accepted data table (your existing one)
    public static final String ACCEPT_TABLE = "accepted_data";
    public static final String COL_ID = "id";
    public static final String COL_BARCODE = "barcode";
    public static final String COL_QTY = "quantity";
    public static final String COL_SHIFT = "shift";
    public static final String COL_TIMESTAMP = "timestamp";

    // Users table (for login system)
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create accepted_data table
        String createAcceptedTable = "CREATE TABLE " + ACCEPT_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BARCODE + " TEXT, " +
                COL_QTY + " INTEGER, " +
                COL_SHIFT + " TEXT, " +
                COL_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createAcceptedTable);

        // Create users table for login
        String createUserTable = "CREATE TABLE " + USER_TABLE + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_EMAIL + " TEXT UNIQUE, " +
                USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables and recreate on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + ACCEPT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    // ========== User table methods for login system ==========

    // Check if email & password match for login
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { USER_ID };
        String selection = USER_EMAIL + "=? AND " + USER_PASSWORD + "=?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Check if email already exists (for creating new account)
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { USER_ID };
        String selection = USER_EMAIL + "=?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // ========== (Optional) Add your accepted_data related methods below here ==========
    // Example insert method for accepted_data table

}
