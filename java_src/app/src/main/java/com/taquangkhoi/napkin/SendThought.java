package com.taquangkhoi.napkin;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendThought {

    private static final String TAG = "Napkin SendThought";

    private final OkHttpClient client = new OkHttpClient();

    private static final String POST_URL = "https://app.napkin.one/api/createThought";

    public SendThought() {
    }

    /*
     * Send the thought to the server
     * using OkHttp
     */
    public String SendThought_OkHttp(String email, String token, String thought, String sourceUrl) {
        Log.d(TAG, "SendThought_OkHttp: Sending thought to server... ");
        String msg = "Finally - SendThought_OkHttp: Sent thought to server! ";

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
                    .url(POST_URL)
                    .header("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .method("POST", formBody)
                    .build();

            Call call = client.newCall(request);

            Response response = call.execute();

            Log.d(TAG, "SendThought_OkHttp: Response: " + response.toString());

            assert response.body() != null : "Response body is null";

//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                    if (response.isSuccessful()) {
//                        final String myResponse = response.body().string();
//                        toast.show();
//                        Log.d(TAG, "Response " + myResponse);
//                    } else {
//                        Log.d(TAG, "Response Not Successful");
//                    }
//                }
//            });

//            Toast.makeText(this, "Sent ✅", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            Log.d(TAG, msg);
        }
        return null;
    }

    /*
     * Send the thought to the server
     * using Apache HttpClient
     */
    public void SendThought_Apache_HttpClient(String email, String token, String thought, String sourceUrl) {
//        // Send a POST request with HttpUrlConnection?
//        HttpClient httpclient = HttpClients.createDefault();
//        HttpPost httppost = new HttpPost("http://www.a-domain.com/foo/");
//
//// Request parameters and other properties.
//        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//        params.add(new BasicNameValuePair("param-1", "12345"));
//        params.add(new BasicNameValuePair("param-2", "Hello!"));
//        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//
////Execute and get the response.
//        HttpResponse response = httpclient.execute(httppost);
//        HttpEntity entity = response.getEntity();
//
//        if (entity != null) {
//            try (InputStream instream = entity.getContent()) {
//                // do something useful
//            }
//        }
//
//
//        HttpPost post = new HttpPost("http://jakarata.apache.org/");
//        NameValuePair[] data = {
//                new NameValuePair("user", "joe"),
//                new NameValuePair("password", "bloggs")
//        };
//        post.setRequestBody(data);
//// execute method and handle any error responses.
//
//        InputStream in = post.getResponseBodyAsStream();
// handle response.


//Source: https://stackoverflow.com/questions/3324717
    }

    public void SendThought_Retrofit(String email, String token, String thought, String sourceUrl) {

    }

    public void SendThought_HttpURL(String email, String token, String thought, String sourceUrl) throws IOException {

    }

}
