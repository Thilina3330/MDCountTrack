package com.s23010733.md_count_track;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        barcodeInput = findViewById(R.id.barcodeInput);
        qtyInput = findViewById(R.id.qtyInput);
        acceptBtn = findViewById(R.id.acceptBtn);
        rejectBtn = findViewById(R.id.rejectBtn);
        resetBtn = findViewById(R.id.resetBtn);

        acceptBtn.setOnClickListener(v -> {
            String barcode = barcodeInput.getText().toString().trim();
            String qty = qtyInput.getText().toString().trim();

            if (barcode.isEmpty() || qty.isEmpty()) {
                Toast.makeText(this, "Please enter both barcode and quantity", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Accepted\nBarcode: " + barcode + "\nQty: " + qty, Toast.LENGTH_LONG).show();
                // TODO: Save to database or update list
            }
        });

        rejectBtn.setOnClickListener(v -> {
            String barcode = barcodeInput.getText().toString().trim();

            Intent intent = new Intent(MainActivity3.this, RejectActivity.class);
            intent.putExtra("rejected_barcode", barcode); // ✅ Send barcode
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









    }

}
