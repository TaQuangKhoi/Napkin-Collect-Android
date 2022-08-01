package com.example.napkin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Button btnSend;
    EditText etThought;
    TextView tvAppName;
    File f_code, f_uid, f_token;
    String code, uid, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        btnSend = findViewById(R.id.btn_send);
        etThought = findViewById(R.id.et_thought);

        // Chạy hàm tìm code, uid, token
        TestCode();

        // Cái này dùng để làm gì?
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());

        Bundle bd = getIntent().getExtras();
        if(bd != null) {
            String code = bd.getString("code");
            etThought.setText(code);
        }
    }

    private void SendThought() {

    }

    // Hàm kiểm tra đã có code, uid, token chưa??
    private boolean TestCode() {
        try{
            FileInputStream fis_code = new FileInputStream(f_code);
            DataInputStream in_code = new DataInputStream(fis_code);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in_code) );
            String strLine;
            while ((strLine = br.readLine()) != null) {
                code = code + strLine;
                return true;
            }
            in_code.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}