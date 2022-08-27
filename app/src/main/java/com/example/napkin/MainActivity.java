package com.example.napkin;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    Button btnSend;
    EditText etThought;
    TextView tvAppName;
    File f_code, f_uid, f_token;
    String code, uid, token;
    HttpURLConnection urlConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        btnSend = findViewById(R.id.btn_send);
        etThought = findViewById(R.id.et_thought);

        // Chạy hàm tìm code, uid, token

        /*try {
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
        }*/


        // Cái này dùng để làm gì?
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendThoughtWithCodeAndUid("AHQfjzdo4JcUbVh6uXyGCD9CdOI3",
                        "45769",
                        etThought.getText().toString(),
                        "https://metruyenchu.com/tai-khoan/tu-truyen");
            }
        });
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
            /*urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);*/
//            urlConnection.setDoInput(true);
//            urlConnection.setChunkedStreamingMode(0);
//            String respondMessage = urlConnection.getResponseMessage().toString();

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(postData.toString());
            writer.flush();
            writer.close();
            out.close();

//            urlConnection.connect();
            Toast.makeText(this, "respondMessage", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
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
//        Toast.makeText(this, "Nhân nút rồi", Toast.LENGTH_SHORT).show();
//        HttpURLConnection urlConnection = null;

        try {
            JSONObject postData = new JSONObject();
            postData.put("email", email);
            postData.put("token", token);
            postData.put("thought", thought);
            postData.put("sourceUrl", sourceUrl);


            URL url = new URL("https://app.napkin.one/api/createThought");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            Toast.makeText(this, urlConnection.getResponseMessage(), Toast.LENGTH_SHORT).show();

            /*OutputStream out = new BufferedOutputStream(urlconnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(postData.toString());
            writer.flush();*/

            /*int code = urlconnection.getResponseCode();
            if (code !=  201) {
                throw new IOException("Invalid response from server: " + code);
            }*/

            /*BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlconnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                Log.i("data", line);
            }*/
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