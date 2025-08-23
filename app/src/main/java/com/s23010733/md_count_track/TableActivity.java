package com.s23010733.md_count_track;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TableActivity extends AppCompatActivity {

    Button btnGoToAcceptTable, btnGoToRejectTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        // 🔹 Connect buttons by their XML IDs
        btnGoToAcceptTable = findViewById(R.id.acceptTableBtn);
        btnGoToRejectTable = findViewById(R.id.rejectTableBtn);

        // 🔸 Navigate to Accept Table Activity
        btnGoToAcceptTable.setOnClickListener(v -> {
            Intent intent = new Intent(TableActivity.this, AcceptTableActivity.class);
            startActivity(intent);
        });


    }
}
