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
        if(FindCode() && FindUid() && FindToken())
        {
            // Nếu tìm thấy code, uid, token thì gán giá trị cho các biến
            code = ReadCode();
            uid = ReadUid();
            token = ReadToken();
        }
        else
        {
            // Nếu không tìm thấy code, uid, token thì gán giá trị mặc định cho các biến
            code = "";
            uid = "";
            token = "";

            // Chạy Activity Đăng nhập
        }

        // Cái này dùng để làm gì?
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());

        Bundle bd = getIntent().getExtras();
        if(bd != null) {
            String code = bd.getString("code");
            etThought.setText(code);
        }
    }

    private String ReadToken() {
        String line;
        String token = "";
        try {
            FileInputStream fis = new FileInputStream(f_token);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                token = line;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    private String ReadUid() {
        String uid = "";
        try {
            f_uid = new File(getFilesDir(), "uid.txt");
            FileInputStream fis = new FileInputStream(f_uid);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            uid = br.readLine();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uid;
    }

    private String ReadCode() {
        String code = "";
        try {
            f_code = new File(getFilesDir(), "code.txt");
            FileInputStream fis = new FileInputStream(f_code);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            code = br.readLine();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    private void SendThought() {

    }

    // Hàm kiểm tra đã có code, uid, token chưa??
    private boolean FindCode() {
        boolean ketqua = false;
        try{
            FileInputStream fis_code = new FileInputStream(f_code);
            DataInputStream in_code = new DataInputStream(fis_code);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in_code) );
            String strLine;
            while ((strLine = br.readLine()) != null) {
                //code = code + strLine;
                ketqua = true;
            }
            in_code.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return ketqua;
    }

    // Hàm kiểm tra đã có uid chưa??
    private boolean FindUid(){
        boolean ketqua = false;
        try{
            FileInputStream fis_uid = new FileInputStream(f_uid);
            DataInputStream in_uid = new DataInputStream(fis_uid);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in_uid) );
            String strLine;
            while ((strLine = br.readLine()) != null) {
                //uid = uid + strLine;
                ketqua = true;
            }
            in_uid.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return ketqua;
    }

    // Hàm kiểm tra đã có token chưa??
    private boolean FindToken(){
        boolean ketqua = false;
        try{
            FileInputStream fis_token = new FileInputStream(f_token);
            DataInputStream in_token = new DataInputStream(fis_token);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in_token) );
            String strLine;
            while ((strLine = br.readLine()) != null) {
                //token = token + strLine;
                ketqua = true;
            }
            in_token.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return ketqua;
    }
}