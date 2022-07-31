package com.example.napkin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetCode extends AppCompatActivity {
    Button btnOK;
    EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_code);

        btnOK = findViewById(R.id.btn_ok);
        etCode = findViewById(R.id.et_code);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetCode.this, MainActivity.class);
                intent.putExtra("code", etCode.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}