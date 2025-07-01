package com.s23010733.md_count_track;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class fristone extends AppCompatActivity {

    EditText emailField, passwordField;
    Button loginBtn, signinBtn;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fristone);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        loginBtn = findViewById(R.id.logingbtn);    // Check your XML id matches this
        signinBtn = findViewById(R.id.signinBtn);

        dbHelper = new DatabaseHelper(this);

        // Login → Validate and go to MainActivity2
        loginBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(fristone.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                //  Validate login credentials
                boolean isValid = dbHelper.checkUser(email, password);
                if (isValid) {
                    Toast.makeText(fristone.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(fristone.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(fristone.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //  Sign In → Go to Sign Up
        signinBtn.setOnClickListener(v -> {
            Intent intent = new Intent(fristone.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
