package com.s23010733.md_count_track;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    EditText barcodeInput, qtyInput;
    Button acceptBtn, rejectBtn, resetBtn;

    DatabaseHelper dbHelper;

    // 🔁 Launcher to get result from RejectActivity
    ActivityResultLauncher<Intent> rejectLauncher;

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

        // 🔄 Register launcher for reject result
        rejectLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        barcodeInput.setText("");
                        qtyInput.setText("");
                        Toast.makeText(this, "Rejected and cleared", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Accept button - save to DB
        acceptBtn.setOnClickListener(v -> {
            String barcode = barcodeInput.getText().toString().trim();
            String qty = qtyInput.getText().toString().trim();
            String shift = getIntent().getStringExtra("shift");

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

        // Reject button - use launcher
        rejectBtn.setOnClickListener(v -> {
            String barcode = barcodeInput.getText().toString().trim();
            Intent intent = new Intent(MainActivity3.this, RejectActivity.class);
            intent.putExtra("rejected_barcode", barcode);
            rejectLauncher.launch(intent);
        });

        // Reset button
        resetBtn.setOnClickListener(v -> {
            barcodeInput.setText("");
            qtyInput.setText("");
        });

        // Navigation
        ImageView imgGoToTable = findViewById(R.id.imgGoToTable);
        imgGoToTable.setOnClickListener(v -> startActivity(new Intent(MainActivity3.this, TableActivity.class)));

        ImageView goToSummary = findViewById(R.id.goToSummary);
        goToSummary.setOnClickListener(v -> startActivity(new Intent(MainActivity3.this, SummaryReportActivity.class)));

        ImageView goToSettings = findViewById(R.id.goToSettings);
        goToSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity3.this, SettingsuI.class)));

        rejectBtn.setOnClickListener(v -> {
            String barcode = barcodeInput.getText().toString().trim();
            String qty = qtyInput.getText().toString().trim();
            String shift = getIntent().getStringExtra("shift");
            String epf = getIntent().getStringExtra("epf");

            Intent intent = new Intent(MainActivity3.this, RejectActivity.class);
            intent.putExtra("rejected_barcode", barcode);
            intent.putExtra("qty", qty);
            intent.putExtra("shift", shift);
            intent.putExtra("epf", epf);
            rejectLauncher.launch(intent);
        });

    }
}
