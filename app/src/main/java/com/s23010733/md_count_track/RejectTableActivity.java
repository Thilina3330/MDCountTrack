package com.s23010733.md_count_track;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RejectTableActivity extends AppCompatActivity {

    private TableLayout rejectTable;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejecttableactivity);

        rejectTable = findViewById(R.id.rejectTableLayout);
        dbHelper = new DatabaseHelper(this);

        loadRejectedData();
    }

    private void loadRejectedData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.REJECT_TABLE, null);

        // Table Header without Time
        TableRow header = new TableRow(this);
        String[] headers = {"Barcode", "Reason", "Qty", "Shift", "EPF"};
        for (String h : headers) {
            TextView tv = new TextView(this);
            tv.setText(h);
            tv.setPadding(10, 10, 10, 10);
            tv.setTextColor(Color.BLACK);
            header.addView(tv);
        }
        rejectTable.addView(header);

        // Table Rows
        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);
            String[] values = {
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_BARCODE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_REASON)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QTY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SHIFT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EPF))
            };
            for (String val : values) {
                TextView tv = new TextView(this);
                tv.setText(val);
                tv.setPadding(10, 10, 10, 10);
                tv.setTextColor(Color.BLACK);
                row.addView(tv);
            }
            rejectTable.addView(row);
        }

        cursor.close();
        db.close();
    }
}
