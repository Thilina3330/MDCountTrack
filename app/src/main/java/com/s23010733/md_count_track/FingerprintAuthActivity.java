package com.s23010733.md_count_track;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class FingerprintAuthActivity extends AppCompatActivity {

    private TextView tvStatus;
    private Button btnAuth;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_auth);

        tvStatus = findViewById(R.id.tvStatus);
        btnAuth = findViewById(R.id.btnAuthenticate);

        // Receive EPF + Shift from MainActivity2
        String epf = getIntent().getStringExtra("epf");
        String shift = getIntent().getStringExtra("shift");

        // 1) Check device support
        int authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            authenticators |= BiometricManager.Authenticators.DEVICE_CREDENTIAL;
        }

        BiometricManager bm = BiometricManager.from(this);
        switch (bm.canAuthenticate(authenticators)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                tvStatus.setText("Fingerprint available");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                tvStatus.setText("No biometric hardware");
                btnAuth.setEnabled(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                tvStatus.setText("Biometric hardware unavailable");
                btnAuth.setEnabled(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                tvStatus.setText("No fingerprint enrolled. Add one in Settings.");
                btnAuth.setEnabled(false);
                break;
            default:
                tvStatus.setText("Biometric not supported");
                btnAuth.setEnabled(false);
                break;
        }

        // 2) Create BiometricPrompt + callbacks
        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                tvStatus.setText("Error: " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                tvStatus.setText("Authenticated!");
                goToMain3(epf, shift);
            }

            @Override
            public void onAuthenticationFailed() {
                tvStatus.setText("Fingerprint not recognized. Try again.");
            }
        });

        // 3) Build prompt
        BiometricPrompt.PromptInfo.Builder builder = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock with fingerprint")
                .setSubtitle("Confirm your identity");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            builder.setAllowedAuthenticators(authenticators);
        } else {
            builder.setNegativeButtonText("Use app PIN/Cancel");
        }

        promptInfo = builder.build();

        // 4) Button action
        btnAuth.setOnClickListener(v -> showBiometricPrompt());
    }

    private void showBiometricPrompt() {
        biometricPrompt.authenticate(promptInfo);
    }

    // ✅ Navigate to MainActivity3 with EPF + Shift
    private void goToMain3(String epf, String shift) {
        Intent i = new Intent(FingerprintAuthActivity.this, MainActivity3.class);
        i.putExtra("epf", epf);
        i.putExtra("shift", shift);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Auto-prompt fingerprint when screen appears
        if (btnAuth.isEnabled()) {
            showBiometricPrompt();
        }
    }
}