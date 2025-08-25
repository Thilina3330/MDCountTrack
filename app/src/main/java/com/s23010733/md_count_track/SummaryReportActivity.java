package com.s23010733.md_count_track;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryReportActivity extends AppCompatActivity {

    private TableLayout tableShift1, tableShift2;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);

        dbHelper = new DatabaseHelper(this);

        tableShift1 = findViewById(R.id.tableShift1);
        tableShift2 = findViewById(R.id.tableShift2);

        displayShiftData("A", tableShift1); // Shift A
        displayShiftData("B", tableShift2); // Shift B
    }

    private void displayShiftData(String shift, TableLayout tableLayout) {
        tableLayout.removeAllViews();

        // Header
        TableRow header = new TableRow(this);
        String[] columns = {"Barcode", "Quantity", "PCS"};
        for (String col : columns) {
            TextView tv = new TextView(this);
            tv.setText(col);
            tv.setPadding(16,16,16,16);
            tv.setTypeface(null, android.graphics.Typeface.BOLD);
            header.addView(tv);
        }
        tableLayout.addView(header);

        // Data rows
        Cursor cursor = dbHelper.getAcceptedByShift(shift);
        int totalQty = 0;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                TableRow row = new TableRow(this);

                String barcode = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_BARCODE));
                int qty = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QTY));
                totalQty += qty;
                int pcs = qty; // PCS = quantity

                TextView tvBarcode = new TextView(this);
                tvBarcode.setText(barcode);
                tvBarcode.setPadding(16,16,16,16);

                TextView tvQty = new TextView(this);
                tvQty.setText(String.valueOf(qty));
                tvQty.setPadding(16,16,16,16);

                TextView tvPCS = new TextView(this);
                tvPCS.setText(String.valueOf(pcs));
                tvPCS.setPadding(16,16,16,16);

                row.addView(tvBarcode);
                row.addView(tvQty);
                row.addView(tvPCS);

                tableLayout.addView(row);
            }
            cursor.close();
        }

        // Total Quantity Row
        TableRow totalRow = new TableRow(this);
        TextView tvTotal = new TextView(this);
        tvTotal.setText("Total");
        tvTotal.setPadding(16,16,16,16);
        tvTotal.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvTotalQty = new TextView(this);
        tvTotalQty.setText(String.valueOf(totalQty));
        tvTotalQty.setPadding(16,16,16,16);
        tvTotalQty.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvEmpty = new TextView(this);
        tvEmpty.setText("");
        tvEmpty.setPadding(16,16,16,16);

        totalRow.addView(tvTotal);
        totalRow.addView(tvTotalQty);
        totalRow.addView(tvEmpty);

        tableLayout.addView(totalRow);
    }
}
