package com.example.napkin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences savedSettings;
    EditText etEmail, etToken;
    Button btnSave;

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

            savedSettings.edit().putString("email", etEmail.getText().toString()).apply();
            savedSettings.edit().putString("token", etToken.getText().toString()).apply();
            savedSettings.edit().commit();

            Toast.makeText(this, "Saved Settings", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

}