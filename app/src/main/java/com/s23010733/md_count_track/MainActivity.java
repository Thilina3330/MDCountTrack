package com.s23010733.md_count_track;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText emailField, passwordField;
    Button signinBtn;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //

        // Bind views
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        signinBtn = findViewById(R.id.signinBtn);
        dbHelper = new DatabaseHelper(this);

        signinBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else if (dbHelper.checkEmailExists(email)) {
                Toast.makeText(MainActivity.this, "Account already exists", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = dbHelper.insertUser(email, password);
                if (inserted) {
                    Toast.makeText(MainActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                    // Navigate to login screen (fristone.java)
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Error creating account!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
