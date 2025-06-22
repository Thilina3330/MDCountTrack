package com.s23010733.md_count_track;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class fristone extends AppCompatActivity {

    EditText emailField, passwordField;
    Button signinBtn, logingBtn; // signin = create account | loging = login

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fristone);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        signinBtn = findViewById(R.id.signinBtn);
        logingBtn = findViewById(R.id.logingbtn);

        dbHelper = new DatabaseHelper(this);

        // LOGIN BUTTON (logingbtn)
        logingBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isValid = dbHelper.checkUser(email, password);
            if (isValid) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(fristone.this, MainActivity2.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        // CREATE ACCOUNT BUTTON (signinBtn)
        signinBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkEmailExists(email)) {
                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            } else {
                boolean created = dbHelper.addUser(email, password);
                if (created) {
                    Toast.makeText(this, "Account created. You can now log in.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Account creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
