package com.example.napkin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences savedSettings;
    EditText etEmail, etToken;
    Button btnSave;
    private static final String TAG = "Napkin SettingsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        etEmail = findViewById(R.id.et_email);
        etToken = findViewById(R.id.et_token);
        btnSave = findViewById(R.id.btn_save);
        savedSettings = getSharedPreferences("Settings", MODE_PRIVATE);

        LoadSetting();
        initButton();
    }

    private void LoadSetting() {
        etEmail.setText(savedSettings.getString("email", ""));
        etToken.setText(savedSettings.getString("token", ""));
    }

    private void initButton() {
        btnSave.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            if (email.isEmpty()) {
                Log.i(TAG, "Email cannot be empty");
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            savedSettings.edit().putString("email", email).apply();

            String token = etToken.getText().toString();
            if (token.isEmpty()) {
                Log.i(TAG, "Token cannot be empty");
                Toast.makeText(this, "Token cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            savedSettings.edit().putString("token", token).apply();

            Toast.makeText(this, "Saved Settings", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Saved Settings Successfully with email: " + email + " and token: " + token + ".");

            finish();
        });
    }

}