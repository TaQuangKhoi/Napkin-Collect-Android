package com.example.napkin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btnSend;
    EditText etThought;
    File f_code, f_uid, f_token;
    String code, uid, token;
    HttpURLConnection urlConnection = null;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        btnSend = findViewById(R.id.btn_send);
        etThought = findViewById(R.id.et_thought);

        initBtnSend();

    }

    private void initBtnSend() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendThoughtWithToken("khoi0941@gmail.com",
                        "napkin-android-m1c5zf79fof",
                        etThought.getText().toString(),
                        "https://napkin-api.herokuapp.com/api/v1/thought");
            }
        });
    }

    private void Test(String email, String token, String thought, String sourceUrl) {

        // Body of POST
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("token", token)
                .add("thought", thought)
                .add("sourceUrl", sourceUrl)
                .build();

        // The request to send (connect with the body now!)
        Request request = new Request.Builder()
                .url("https://app.napkin.one/api/createThought")
                .header("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();
                    Toast.makeText(MainActivity.this, "Sent Successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Can't Send", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Find() {
        try {
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

                // Chạy Activity Đăng nhập
                Intent intent = new Intent(MainActivity.this, ChooseLoginType.class);
                startActivity(intent);

            }
        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private String ReadToken() {
        String line; // chứa text từ file
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

    private void SendThoughtWithCodeAndUid(String uid, String code, String thought, String sourceUrl) {
        try {
            JSONObject postData = new JSONObject();
            postData.put("uid", uid);
            postData.put("code", code);
            postData.put("content", thought);
            postData.put("sourceUrl", sourceUrl);
            postData.put("integrationType", "chrome-extension");

            String FullUrl = "https://us-central1-deepthoughtworks.cloudfunctions.net/addTextToAccount?" +
                    "uid=AHQfjzdo4JcUbVh6uXyGCD9CdOI3&" +
                    "code=45769&" +
                    "content=Thử nghiệm API của Napkin&" +
                    "sourceUrl=https://budgetbakers.com/support-feedback/&" +
                    "integrationType=chrome-extension";
            URL url = new URL(FullUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); // to include a request body.

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(postData.toString());
            writer.flush();
            writer.close();
            out.close();

//            urlConnection.connect();
            Toast.makeText(this, "respondMessage", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
//            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(e.toString())
                    .setTitle("Lỗi")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
        } finally {
            if (urlConnection != null) {
                Toast.makeText(this, "đcm đéo thể connect", Toast.LENGTH_SHORT).show();
                urlConnection.disconnect();
            }
        }
    }

    @Nullable
    private Void SendThoughtWithToken(String email, String token, String thought, String sourceUrl) {

        try {
            // Body of POST
            RequestBody formBody = new FormBody.Builder()
                    .add("email", email)
                    .add("token", token)
                    .add("thought", thought)
                    .add("sourceUrl", sourceUrl)
                    .build();

            // The request to send (connect with the body now!)
            Request request = new Request.Builder()
                    .url("https://app.napkin.one/api/createThought")
                    .header("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .post(formBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        final String myResponse = response.body().string();
                        Log.d("Response", myResponse);
                        etThought.setText(myResponse);
                    }
                    else
                    {
                        Log.d("Response", "Not Successful");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi rồi :v", Toast.LENGTH_SHORT).show();
        } finally {
            if (urlConnection != null) {
                Toast.makeText(this, "đcm đéo thể connect", Toast.LENGTH_SHORT).show();
                urlConnection.disconnect();
            }
        }
        return null;
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