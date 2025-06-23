package com.s23010733.md_count_track;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {
    EditText barcodeInput, qtyInput;
    Button acceptBtn, rejectBtn, resetBtn;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        barcodeInput = findViewById(R.id.barcodeInput);
        qtyInput = findViewById(R.id.qtyInput);
        acceptBtn = findViewById(R.id.acceptBtn);
        rejectBtn = findViewById(R.id.rejectBtn);
        resetBtn = findViewById(R.id.resetBtn);
        dbHelper = new DatabaseHelper(this);

        // Accept button - save to DB
        acceptBtn.setOnClickListener(v -> {
            String barcode = barcodeInput.getText().toString().trim();
            String qty = qtyInput.getText().toString().trim();
            String shift = getIntent().getStringExtra("shift"); // Get shift from previous activity

            if (barcode.isEmpty() || qty.isEmpty()) {
                Toast.makeText(this, "Please enter both barcode and quantity", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues values = new ContentValues();
                values.put("barcode", barcode);
                values.put("quantity", qty);
                values.put("shift", shift);

                long result = dbHelper.getWritableDatabase().insert(DatabaseHelper.ACCEPT_TABLE, null, values);

                if (result != -1) {
                    Toast.makeText(this, "Saved: " + barcode, Toast.LENGTH_SHORT).show();
                    barcodeInput.setText("");
                    qtyInput.setText("");
                } else {
                    Toast.makeText(this, "Error saving!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rejectBtn.setOnClickListener(v -> {
            String barcode = barcodeInput.getText().toString().trim();
            Intent intent = new Intent(MainActivity3.this, RejectActivity.class);
            intent.putExtra("rejected_barcode", barcode);
            startActivity(intent);
        });

        resetBtn.setOnClickListener(v -> {
            barcodeInput.setText("");
            qtyInput.setText("");
        });

        ImageView imgGoToTable = findViewById(R.id.imgGoToTable);
        imgGoToTable.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, TableActivity.class);
            startActivity(intent);
        });

        ImageView goToSummary = findViewById(R.id.goToSummary);
        goToSummary.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, SummaryReportActivity.class);
            startActivity(intent);
        });

        ImageView goToSettings = findViewById(R.id.goToSettings);
        goToSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, SettingsuI.class);
            startActivity(intent);
        });
    }
}

