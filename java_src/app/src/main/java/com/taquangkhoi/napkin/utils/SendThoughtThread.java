package com.taquangkhoi.napkin.utils;

import static android.content.Context.MODE_PRIVATE;
import static com.taquangkhoi.napkin.utils.testInternetConnection.testHttpURLConnection;

import android.content.SharedPreferences;
import android.util.Log;

public class SendThoughtThread extends Thread {
    private static final String TAG = "SendThoughtThread";
    private String email, token, thought, sourceUrl;
    SendThought sendThought = new SendThought();


    public SendThoughtThread(String email, String token, String thought, String sourceUrl) {
        this.email = email;
        this.token = token;
        this.thought = thought;
        this.sourceUrl = sourceUrl;
    }

    @Override
    public void run() {
        super.run();
        String result = sendThought.SendThought_OkHttp(
                email,
                token,
                thought,
                sourceUrl
        );
        Log.d(TAG, "run: " + result);
    }
}
