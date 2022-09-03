package com.example.napkin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btnSend;
    EditText etThought, etSourceUrl;
    String email, token;
    HttpURLConnection urlConnection = null;
    private final OkHttpClient client = new OkHttpClient();
    ImageView ivSetting;
    SharedPreferences savedSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        btnSend = findViewById(R.id.btn_send);
        etThought = findViewById(R.id.et_thought);
        etSourceUrl = findViewById(R.id.et_source_url);
        ivSetting = findViewById(R.id.iv_settings);
        savedSettings = getSharedPreferences("Settings", MODE_PRIVATE);

        LoadSetting();
        initBtnSend();
        initBtnSetting();
    }

    private void LoadSetting() {
        email = savedSettings.getString("email", "");
        token = savedSettings.getString("token", "");
    }

    private void initBtnSetting() {
        ivSetting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void initBtnSend() {
        btnSend.setOnClickListener(view -> SendThoughtWithToken(
                email,
                token,
                etThought.getText().toString(),
                etSourceUrl.getText().toString()
        ));
    }

    private void SendThoughtWithToken(String email, String token, String thought, String sourceUrl) {
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
                    }
                    else
                    {
                        Log.d("Response", "Not Successful");
                    }
                }
            });
            Toast.makeText(this, "Sent ✅", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error :v", Toast.LENGTH_SHORT).show();
        } finally {
            if (urlConnection != null) {
                Toast.makeText(this, "Can't connect", Toast.LENGTH_SHORT).show();
                urlConnection.disconnect();
            }
        }
    }
}