package com.s23010733.md_count_track;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RejectActivity extends AppCompatActivity {

    TextView rejectedBarcodeView;
    Spinner reasonSpinner;
    Button submitRejectBtn;
    DatabaseHelper dbHelper; // DB helper instance

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject);

        // Connect views
        rejectedBarcodeView = findViewById(R.id.rejectedBarcodeView);
        reasonSpinner = findViewById(R.id.reasonSpinner);
        submitRejectBtn = findViewById(R.id.submitRejectBtn);

        // DB helper instance
        dbHelper = new DatabaseHelper(this);

        // Get barcode from MainActivity3
        String barcode = getIntent().getStringExtra("rejected_barcode");
        if (barcode == null || barcode.isEmpty()) {
            barcode = "Not Provided";
        }
        rejectedBarcodeView.setText("Rejected Barcode: " + barcode);

        // Spinner setup
        String[] reasons = {"Select Reason", "MD Fail", "Quantity Error"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, reasons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonSpinner.setAdapter(adapter);

        String finalBarcode = barcode;

        submitRejectBtn.setOnClickListener(v -> {
            String selectedReason = reasonSpinner.getSelectedItem().toString();

            if (selectedReason.equals("Select Reason")) {
                Toast.makeText(this, "Please select a rejection reason", Toast.LENGTH_SHORT).show();
            } else {
                // Insert rejected data into SQLite using DatabaseHelper
                boolean inserted = dbHelper.insertRejected(
                        finalBarcode,
                        selectedReason,
                        1,          // qty (dynamic data from MainActivity3 if available)
                        "Shift1",   // shift (dynamic from MainActivity3)
                        "EPF123"    // EPF (dynamic if available)
                );

                if (inserted) {
                    Toast.makeText(this, "Rejected successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error saving rejected data!", Toast.LENGTH_SHORT).show();
                }

                // Navigate back to MainActivity3
                Intent intent = new Intent(RejectActivity.this, MainActivity3.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}

