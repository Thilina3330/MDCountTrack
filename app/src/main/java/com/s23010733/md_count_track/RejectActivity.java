package com.s23010733.md_count_track;

import android.annotation.SuppressLint;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject);

        rejectedBarcodeView = findViewById(R.id.rejectedBarcodeView);
        reasonSpinner = findViewById(R.id.reasonSpinner);
        submitRejectBtn = findViewById(R.id.submitRejectBtn);

        String barcode = getIntent().getStringExtra("rejected_barcode");
        if (barcode == null || barcode.isEmpty()) {
            barcode = "Not Provided";
        }

        rejectedBarcodeView.setText("Rejected Barcode: " + barcode);

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
                Toast.makeText(this, "Rejected Barcode: " + finalBarcode + "\nReason: " + selectedReason, Toast.LENGTH_LONG).show();
                setResult(RESULT_OK); // Return success result
                finish(); // Close and go back
            }
        });
    }
}
