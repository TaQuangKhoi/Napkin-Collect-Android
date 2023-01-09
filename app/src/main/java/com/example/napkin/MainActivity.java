package com.example.napkin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btnSend, btnClear;
    EditText etThought, etSourceUrl;
    String email, token;
    ImageView ivSetting;
    Intent intentReceiver;

    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences savedSettings;

    private static final String TAG = "Napkin MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddControls();
        AddEvents();

        LoadSetting();
        initReceiver();
    }

    private void AddControls() {
        btnSend = findViewById(R.id.btn_send);
        btnClear = findViewById(R.id.btn_clear_text);
        etThought = findViewById(R.id.et_thought);
        etSourceUrl = findViewById(R.id.et_source_url);
        ivSetting = findViewById(R.id.iv_settings);
        savedSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        etSourceUrl.setText("");
    }

    /*
     * Func to Receive data sending from outside (from Browsers)
     * using Intent
     */
    private void initReceiver() {
        Log.d(TAG, "initReceiver: Init receiver");
        intentReceiver = getIntent();
        String action = intentReceiver.getAction();
        String type = intentReceiver.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSentText(intentReceiver); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intentReceiver); // Handle multiple images being sent
            }
        }
    }

    /*
     * Func to handle text being sent
     */
    void handleSentText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        String sharedUrl = intent.getStringExtra(Intent.EXTRA_REFERRER);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            Toast.makeText(this, "Text Sending : " + sharedText, Toast.LENGTH_SHORT).show();
            etThought.setText(sharedText);
            if (sharedUrl != null) {
                etSourceUrl.setText(sharedUrl);
            }
            SendThought_OkHttp(
                    email,
                    token,
                    sharedText,
                    ""
            );
        }
    }

    /*
     * Func to handle image
     *
     */
    void handleSendImage() {
        //Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        Toast.makeText(this, "Don't allow image", Toast.LENGTH_SHORT).show();
    }

    /*
     * Func to handle multiple images
     */
    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            Toast.makeText(this, "Don't allow images", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Load the Setting from SharedPreferences
     * and set the email and token
     */
    private void LoadSetting() {
        email = savedSettings.getString("email", "");
        token = savedSettings.getString("token", "");
    }

    private void AddEvents() {
        initBtnSend();
        initBtnSetting();
        initBtnClear();
    }

    /*
     * Init the Setting Button
     * When click, it will open the Setting Activity
     */
    private void initBtnSetting() {
        ivSetting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    /*
     * Init the Send Button
     * When click, it will send the thought to the server
     */
    private void initBtnSend() {
        btnSend.setOnClickListener(view -> SendThought_OkHttp(
                email,
                token,
                etThought.getText().toString(),
                etSourceUrl.getText().toString()
        ));
    }

    /*
     * Init the Clear Button
     * When click, it will clear the text in the EditText etThought and etSourceUrl
     */
    private void initBtnClear() {
        btnClear.setOnClickListener(view -> {
            etThought.setText("");
            etSourceUrl.setText("");
        });
    }

    /*
     * Send the thought to the server
     * using OkHttp
     */
    private void SendThought_OkHttp(String email, String token, String thought, String sourceUrl) {
        Snackbar sb = Snackbar.make(btnSend, "Sent! ✅", Snackbar.LENGTH_SHORT);
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
                    if (response.isSuccessful()) {
                        final String myResponse = response.body().string();
                        sb.show();
                        Log.d(TAG, "Response " + myResponse);
                    } else {
                        Log.d(TAG, "Response Not Successful");
                    }
                }
            });

//            Toast.makeText(this, "Sent ✅", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error - Details in Log 😲", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Send the thought to the server
     * using Apache HttpClient
     */
    private void SendThought_Apache_HttpClient(String email, String token, String thought, String sourceUrl) {

    }

    private void SendThought_Retrofit(String email, String token, String thought, String sourceUrl) {

    }

    // The end of MainActivity class
}