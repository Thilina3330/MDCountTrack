package com.s23010733.md_count_track;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class TableActivity extends AppCompatActivity {

    Button btnGoToAcceptTable; // Button to navigate to AcceptTableActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        // Connect button from layout
        btnGoToAcceptTable = findViewById(R.id.acceptTableBtn);

        // Set button click listener to open AcceptTableActivity
        btnGoToAcceptTable.setOnClickListener(v -> {
            Intent intent = new Intent(TableActivity.this, AcceptTableActivity.class);
            startActivity(intent);
        });
    }
}
