package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.utils.KeyboardUtil;
import com.example.utils.TextUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button request;
    private TextView tvGson;
    private EditText etAdcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request = findViewById(R.id.btnRequest);
        request.setOnClickListener(this);
        tvGson = findViewById(R.id.tvGson);
        etAdcode = findViewById(R.id.etAdcode);
    }

    public void loadData() {
        try {
            String adcode = etAdcode.getText().toString().trim();
            String serviceUrl = AppConst.ServerUrl(adcode);
            URL url = new URL(serviceUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == AppConst.NET_STATUS_OK) {
                httpURLConnection.setConnectTimeout(1000);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String readLine;
                while ((readLine = bufferedReader.readLine()) != null) {
                    Log.d(TAG, "loadData: " + readLine);
                    final String toJson = TextUtils.stringToJson(readLine);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvGson.setText(toJson);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "loadData: " + e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        KeyboardUtil.hideKeyboard(etAdcode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }).start();
    }
}
