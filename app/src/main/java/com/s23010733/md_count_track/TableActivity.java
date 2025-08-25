package com.s23010733.md_count_track;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TableActivity extends AppCompatActivity {

    Button btnGoToAcceptTable, btnGoToRejectTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        btnGoToAcceptTable = findViewById(R.id.acceptTableBtn);
        btnGoToRejectTable = findViewById(R.id.rejectTableBtn);

        // Accept Table navigation
        btnGoToAcceptTable.setOnClickListener(v -> {
            Intent intent = new Intent(TableActivity.this, AcceptTableActivity.class);
            startActivity(intent);
        });

        // Reject Table navigation
        btnGoToRejectTable.setOnClickListener(v -> {
            Intent intent = new Intent(TableActivity.this, RejectTableActivity.class);
            startActivity(intent);
        });
    }
}
