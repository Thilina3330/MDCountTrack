package com.s23010733.md_count_track;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsuI extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchDarkMode;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsu_i);

        switchDarkMode = findViewById(R.id.switchDarkMode);
        btnLogout = findViewById(R.id.btnLogout);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String msg = isChecked ? "Dark Mode Enabled" : "Dark Mode Disabled";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> finish()); // Just finish for now
    }
}
