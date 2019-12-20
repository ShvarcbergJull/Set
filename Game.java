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
import java.util.ArrayList;
import java.util.List;


public class Game extends AppCompatActivity {
    String nickname;
    TextView tv;
    ArrayList<TextView> tvs = new ArrayList<>();
    int token;
    SetCard cardos;

    class CardTask extends AsyncTask<Integer, Integer, Void> {
        public SetCard getCards() {
            String set_server_url = "http://194.176.114.21:8050";
            try {
                URL url = new URL(set_server_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);

                OutputStream out = urlConnection.getOutputStream();
                String data = "{ \"action\": \"fetch_cards\", \"token\": " + token + " }";
                out.write(data.getBytes());

                Gson gson = new Gson();
                SetCard setCard = gson.fromJson(new InputStreamReader(urlConnection.getInputStream()), SetCard.class);
                urlConnection.disconnect();
                return setCard;
            }
            catch (IOException e) {
                Log.e("mytag", "Error", e);
                return new SetCard();
            }
        }

        @Override
        protected Void doInBackground(Integer... ints) {
            for (int i: ints) {
                cardos = getCards();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("cardcard", cardos.toString());
            List<Card> c = cardos.cards;
            for (int i = 0;i < c.toArray().length;i++) {
                String out = "count: " + c.get(i).count + "\n" +
                             "color: " + c.get(i).color + "\n" +
                             "shape: " + c.get(i).shape + "\n" +
                             "fill: " + c.get(i).fill;
//                Log.d("cardosing", out);
                String key = "line" + String.valueOf(i + 1);
                tvs.get(i).setText(out);
            }
        }
    }

    private void getting() {
        tv = findViewById(R.id.line1);
        tvs.add(tv);
        tv = findViewById(R.id.line2);
        tvs.add(tv);
        tv = findViewById(R.id.line3);
        tvs.add(tv);
        tv = findViewById(R.id.line4);
        tvs.add(tv);
        tv = findViewById(R.id.line5);
        tvs.add(tv);
        tv = findViewById(R.id.line6);
        tvs.add(tv);
        tv = findViewById(R.id.line7);
        tvs.add(tv);
        tv = findViewById(R.id.line8);
        tvs.add(tv);
        tv = findViewById(R.id.line9);
        tvs.add(tv);
        tv = findViewById(R.id.line10);
        tvs.add(tv);
        tv = findViewById(R.id.line11);
        tvs.add(tv);
        tv = findViewById(R.id.line12);
        tvs.add(tv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent i = getIntent();
        getting();
        nickname = i.getStringExtra("name");
        token = i.getIntExtra("token", 50);
        CardTask task = new CardTask();
        task.execute(1);

    }

    public void onClick() {

    }
}
