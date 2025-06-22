package com.s23010733.md_count_track;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class fristone extends AppCompatActivity {

    EditText emailField, passwordField;
    Button signinBtn, logingBtn;

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

        // -------- LOGIN BUTTON --------
        logingBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!dbHelper.checkEmailExists(email)) {
                // No account exists → go to MainActivity (create account screen)
                Toast.makeText(this, "No account found. Please create one.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(fristone.this, MainActivity.class); // navigate to signup screen
                intent.putExtra("email", email); // optional: pass entered email
                startActivity(intent);
                finish();
            } else if (dbHelper.checkUser(email, password)) {
                // Login success → go to MainActivity2
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(fristone.this, MainActivity2.class));
                finish();
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


