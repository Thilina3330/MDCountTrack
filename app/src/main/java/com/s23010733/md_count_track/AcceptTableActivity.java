package com.s23010733.md_count_track;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AcceptTableActivity extends AppCompatActivity {

    TableLayout tableLayout;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_table);

        tableLayout = findViewById(R.id.acceptTableLayout);
        dbHelper = new DatabaseHelper(this);

        loadAcceptedData();
    }

    private void loadAcceptedData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.ACCEPT_TABLE, null);

        // Table Header
        TableRow header = new TableRow(this);
        String[] titles = {"Barcode", "Qty", "Shift", "Date"};
        for (String title : titles) {
            TextView tv = new TextView(this);
            tv.setText(title);
            tv.setTypeface(null, Typeface.BOLD); // bold header
            tv.setTextColor(getResources().getColor(android.R.color.black));
            tv.setPadding(16, 16, 16, 16);
            header.addView(tv);
        }
        tableLayout.addView(header);

        // Table Data
        while (cursor.moveToNext()) {
            String barcode = cursor.getString(cursor.getColumnIndexOrThrow("barcode"));
            String qty = cursor.getString(cursor.getColumnIndexOrThrow("quantity"));
            String shift = cursor.getString(cursor.getColumnIndexOrThrow("shift"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

            TableRow row = new TableRow(this);
            for (String item : new String[]{barcode, qty, shift, date}) {
                TextView tv = new TextView(this);
                tv.setText(item);
                tv.setTextColor(getResources().getColor(android.R.color.black));
                tv.setPadding(16, 16, 16, 16);
                row.addView(tv);
            }
            tableLayout.addView(row);
        }

        cursor.close();
        db.close();
    }
}

