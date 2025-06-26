package com.s23010733.md_count_track;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    Spinner spinnerShift;
    EditText epfNumber;
    Button enterBtn;

    String selectedShift = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Bind Views
        spinnerShift = findViewById(R.id.spinnerShift);
        epfNumber = findViewById(R.id.epfNumber);
        enterBtn = findViewById(R.id.enterBtn);

        //  Setup Spinner Data
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Select your shift", "A", "B"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShift.setAdapter(adapter);

        // Spinner selection listener
        spinnerShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedShift = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedShift = "";
            }
        });

        // ENTER button click
        enterBtn.setOnClickListener(v -> {
            String epfText = epfNumber.getText().toString().trim();

            if (selectedShift.equals("Select your shift") || selectedShift.isEmpty()) {
                Toast.makeText(MainActivity2.this, "Please select your shift", Toast.LENGTH_SHORT).show();
            } else if (epfText.isEmpty()) {
                Toast.makeText(MainActivity2.this, "Please enter your EPF number", Toast.LENGTH_SHORT).show();
            } else {
                // All good → Navigate to MainActivity3
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);

                // You can pass the shift + epf number like this:
                intent.putExtra("shift", selectedShift);
                intent.putExtra("epf", epfText);

                startActivity(intent);
            }
        });

    }
}


