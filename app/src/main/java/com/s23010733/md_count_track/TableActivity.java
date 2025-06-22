package com.s23010733.md_count_track;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class TableActivity extends AppCompatActivity {

    public static Object DatabaseHelper;
    Button btnGoToAcceptTable; // 🔹 Declare the button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        // 🔹 Bind the button
        btnGoToAcceptTable = findViewById(R.id.acceptTableBtn); // Match with your XML button ID

        // 🔹 Set onClick listener
        btnGoToAcceptTable.setOnClickListener(v -> {
            Intent intent = new Intent(TableActivity.this, AcceptTableActivity.class);
            startActivity(intent);
        });
    }

    public static class DatabaseHelper {
        public DatabaseHelper(AcceptTableActivity acceptTableActivity) {
        }

        public SQLiteDatabase getReadableDatabase() {
            return null;
        }
    }
}
