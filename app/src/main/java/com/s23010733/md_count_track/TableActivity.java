package com.s23010733.md_count_track;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class TableActivity extends AppCompatActivity {

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
}
