package com.example.pc.card_set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText et;
    ListView lv;
    int token = -1;
    String nickname;
    SharedPreferences tokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.nick);
        tokens = getSharedPreferences("users", Context.MODE_PRIVATE);
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
                if (reg.status.equals("error")) {
                    token = tokens.getInt("token", -1);
                    Log.d("RegistrationAnswer", " token = " + token);
                }
                else {
                    token = reg.token;
                    tokens.edit().putInt("token", token).apply();
                    Log.d("RegistrationAnswer", " token = " + token);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
            if (token != -1 && nickname != null) {
                Intent i = new Intent(MainActivity.this, Game.class);
                i.putExtra("name", String.valueOf(nickname));
                i.putExtra("token", token);
                startActivity(i);
            }
            else {
                Toast t = Toast.makeText(MainActivity.this, "Enter different name", Toast.LENGTH_LONG);
                t.show();
            }
        }
    }

    public void onClick(View v) throws InterruptedException {
        nickname = et.getText().toString();
        RequestTask task = new RequestTask();
        task.execute(new int[]{1});
    }
}
