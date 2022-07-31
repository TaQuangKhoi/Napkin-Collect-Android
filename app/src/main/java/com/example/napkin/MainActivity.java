package com.example.napkin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnSend;
    EditText etThought;
    TextView tvAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btn_send);

        etThought = findViewById(R.id.et_thought);

        Bundle bd = getIntent().getExtras();
        if(bd != null) {
            String code = bd.getString("code");
            etThought.setText(code);
        }
    }

    private void SendThought() {

    }


}