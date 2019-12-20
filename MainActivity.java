package com.example.pc.card_set;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.nick);
    }

//    public void cotin(String nickname) throws IOException {
//        String set_server_url = "http://194.176.114.21:8050";
//        URL url = new URL(set_server_url);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setDoOutput(true);
//
//        OutputStream out = urlConnection.getOutputStream();
//        String data = "{ \"action\": \"register\", \"nickname\": \"" + nickname + "\" }";
//        out.write(data.getBytes());
//
//        Gson gson = new Gson();
//        RegisterData reg = gson.fromJson(new InputStreamReader(urlConnection.getInputStream()), RegisterData.class);
//        Log.d("TestRegistr", "status: " + reg.status + " token: " + reg.token);
//        urlConnection.disconnect();
//    }

    public void onClick(View v) throws IOException {
        String nickname = et.getText().toString();
        Intent i = new Intent(this, Game.class);
        i.putExtra("name", String.valueOf(nickname));
        startActivity(i);
    }
}
