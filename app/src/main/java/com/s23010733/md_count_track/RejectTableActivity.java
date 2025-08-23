package com.s23010733.md_count_track;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RejectTableActivity extends AppCompatActivity {

    TableLayout rejectTable = findViewById(R.id.rejectTableLayout);
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejecttableactivity);

        dbHelper = new DatabaseHelper(this);

        loadRejectedData();
    }

    private void loadRejectedData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.REJECT_TABLE, null);

        TableRow header = new TableRow(this);
        {
            TextView tv = new TextView(this);
            tv.setText("Barcode");
            tv.setPadding(10, 10, 10, 10);
            header.addView(tv);
        }
        {
            TextView tv = new TextView(this);
            tv.setText("Reason");
            tv.setPadding(10, 10, 10, 10);
            header.addView(tv);
        }
        {
            TextView tv = new TextView(this);
            tv.setText("Qty");
            tv.setPadding(10, 10, 10, 10);
            header.addView(tv);
        }
        {
            TextView tv = new TextView(this);
            tv.setText("Shift");
            tv.setPadding(10, 10, 10, 10);
            header.addView(tv);
        }
        {
            TextView tv = new TextView(this);
            tv.setText("EPF");
            tv.setPadding(10, 10, 10, 10);
            header.addView(tv);
        }
        {
            TextView tv = new TextView(this);
            tv.setText("Time");
            tv.setPadding(10, 10, 10, 10);
            header.addView(tv);
        }
        rejectTable.addView(header);

        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);
            String[] values = {
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_BARCODE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_REASON)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QTY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SHIFT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EPF)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TIMESTAMP))
            };
            for (String val : values) {
                TextView tv = new TextView(this);
                tv.setText(val);
                tv.setPadding(10, 10, 10, 10);
                row.addView(tv);
            }
            rejectTable.addView(row);
        }

        cursor.close();
        db.close();
    }
}
