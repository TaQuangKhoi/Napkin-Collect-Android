package com.example.napkin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btn_send);

        Bundle bd = getIntent().getExtras();
        if(bd != null){
            String code = bd.getString("code");
            Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
        }
    }

    private void SendThought() {

    }


}