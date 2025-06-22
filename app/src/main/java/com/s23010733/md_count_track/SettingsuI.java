package com.s23010733.md_count_track;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsuI extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchDarkMode;
    Button btnLogout, btnOpenMap;

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsu_i);

        switchDarkMode = findViewById(R.id.switchDarkMode);
        btnLogout = findViewById(R.id.btnLogout);
        btnOpenMap = findViewById(R.id.locationBtn); // new location button

        // Dark Mode switch (for now, just toast)
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String msg = isChecked ? "Dark Mode Enabled" : "Dark Mode Disabled";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        // Logout
        btnLogout.setOnClickListener(v -> finish());

        // Location Button - open Google Maps at a location
        btnOpenMap.setOnClickListener(v -> {
            String geoUri = "geo:6.9271,79.8612?q=Colombo";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
            }
        });
        @SuppressLint("CutPasteId") Button btnLocation = findViewById(R.id.btnLogout);
        btnLocation.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsuI.this, goolemap.class);
            startActivity(intent);
        });
    }
}
