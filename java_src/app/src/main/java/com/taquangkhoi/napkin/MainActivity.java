package com.taquangkhoi.napkin;

import static com.taquangkhoi.napkin.utils.testInternetConnection.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.taquangkhoi.napkin.utils.SendThought;
import com.taquangkhoi.napkin.utils.SendThoughtThread;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnSend, btnClear;
    EditText etThought, etSourceUrl;
    String email, token;
    ImageView ivSetting;
    Intent intentReceiver;

    SharedPreferences savedSettings;

    SendThought sendThought = new SendThought();

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
            sendThought.SendThought_OkHttp(
                    email,
                    token,
                    sharedText,
                    ""
            );
        }
    }

    /**
     * Handle image
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

    /**
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
        btnSend.setOnClickListener(view -> {
            Log.d(TAG, "initBtnSend: Clicked");
            // new thread to send the thought to the server

            SendThoughtThread sendThoughtThread = new SendThoughtThread();
            sendThoughtThread.start();

            // Check if the device is connected to the Internet
//            if (!isInternetAvailable(this)) {
//                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Internet Connection", Toast.LENGTH_SHORT).show();
//            }

//            sendThought.SendThought_OkHttp(
//                    email,
//                    token,
//                    etThought.getText().toString(),
//                    etSourceUrl.getText().toString()
//            );
        });
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



    // The end of MainActivity class
}