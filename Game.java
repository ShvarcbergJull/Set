package com.example.pc.card_set;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;


public class Game extends AppCompatActivity {
    String nickname;
    TextView tv;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent i = getIntent();
        tv = findViewById(R.id.welc);
        nickname = i.getStringExtra("name");
        tv.setText("Hello, " + nickname);
        RequestTask task = new RequestTask();
        task.execute(new int[]{1});
    }

    class RequestTask extends AsyncTask<int[], Integer, Void> {

        public RegisterData getData() {
            String set_server_url = "http://194.176.114.21:8050";
            try {
                URL url = new URL(set_server_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);

                OutputStream out = urlConnection.getOutputStream();
                String data = "{ \"action\": \"register\", \"nickname\": \"" + nickname + "\" }";
                out.write(data.getBytes());

                Gson gson = new Gson();
                RegisterData reg = gson.fromJson(new InputStreamReader(urlConnection.getInputStream()), RegisterData.class);
                Log.d("testich", "hey");
                urlConnection.disconnect();
                return reg;
            }
            catch (IOException e) {
                Log.e("mytag", "Error", e);
                return new RegisterData();
            }
        }

        protected Void doInBackground(int[]... values) {
            for (int value: values[0]) {
                RegisterData reg = getData();
                Log.d("RegistrationAnswer", "status = " + reg.status + " token = " + reg.token);
                token = reg.token;
            }
            return null;
        }
    }

    public void onClick() {

    }
}
