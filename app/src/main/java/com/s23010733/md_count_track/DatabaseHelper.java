package com.s23010733.md_count_track;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "MDCountTrack.db";
    public static final int DB_VERSION = 3;

    // ---------- ACCEPTED TABLE ----------
    public static final String ACCEPT_TABLE = "accepted_data";
    public static final String COL_ID = "id";
    public static final String COL_BARCODE = "barcode";
    public static final String COL_QTY = "quantity";
    public static final String COL_SHIFT = "shift";
    public static final String COL_TIMESTAMP = "timestamp";

    // ---------- REJECTED TABLE ----------
    public static final String REJECT_TABLE = "rejected_data";
    public static final String COL_REASON = "reason";
    public static final String COL_EPF = "epf";

    // ---------- USER TABLE ----------
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // ACCEPTED DATA TABLE
        String createAcceptTable = "CREATE TABLE " + ACCEPT_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BARCODE + " TEXT, " +
                COL_QTY + " INTEGER, " +
                COL_SHIFT + " TEXT, " +
                COL_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createAcceptTable);

        // REJECTED DATA TABLE
        String createRejectTable = "CREATE TABLE " + REJECT_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BARCODE + " TEXT, " +
                COL_REASON + " TEXT, " +
                COL_QTY + " INTEGER, " +
                COL_SHIFT + " TEXT, " +
                COL_EPF + " TEXT, " +
                COL_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createRejectTable);

        // USER TABLE
        String createUserTable = "CREATE TABLE " + USER_TABLE + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_EMAIL + " TEXT UNIQUE, " +
                USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCEPT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REJECT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    // ---------- ACCEPTED DATA ----------
    public boolean insertAccepted(String barcode, int qty, String shift) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BARCODE, barcode);
        values.put(COL_QTY, qty);
        values.put(COL_SHIFT, shift);
        long result = db.insert(ACCEPT_TABLE, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getAcceptedByShift(String shift) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + ACCEPT_TABLE + " WHERE " + COL_SHIFT + "=?", new String[]{shift});
    }

    public Cursor getAllAccepted() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + ACCEPT_TABLE, null);
    }

    // ---------- REJECTED DATA ----------
    public boolean insertRejected(String barcode, String qty, int shift, String reason, String epf) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BARCODE, barcode);
        values.put(COL_QTY, qty);
        values.put(COL_SHIFT, shift);
        values.put(COL_REASON, reason);
        values.put(COL_EPF, epf);
        long result = db.insert(REJECT_TABLE, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getRejectedByShift(String shift) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + REJECT_TABLE + " WHERE " + COL_SHIFT + "=?", new String[]{shift});
    }

    public Cursor getAllRejected() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + REJECT_TABLE, null);
    }

    // ---------- USER DATA ----------
    public boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        long result = db.insert(USER_TABLE, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " +
                USER_EMAIL + "=? AND " + USER_PASSWORD + "=?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkEmailExists(String email) {
        return false;
    }
}
