package com.example.cards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GameCLass extends AppCompatActivity {
    String nickname;
    TextView[] tiles = new TextView[12];
    int token;
    Card kart;
    cards karts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_class);
        Intent i = getIntent();
        tiles[0]=findViewById(R.id.a1);
        tiles[1]=findViewById(R.id.a2);
        tiles[2]=findViewById(R.id.a3);
        tiles[3]=findViewById(R.id.a4);
        tiles[4]=findViewById(R.id.a5);
        tiles[5]=findViewById(R.id.a6);
        tiles[6]=findViewById(R.id.a7);
        tiles[7]=findViewById(R.id.a8);
        tiles[8]=findViewById(R.id.a9);
        tiles[9]=findViewById(R.id.a10);
        tiles[10]=findViewById(R.id.a11);
        tiles[11]=findViewById(R.id.a12);
        nickname = i.getStringExtra("name");
        token = i.getIntExtra("token", 100);
        Karton karti = new Karton();
        karti.execute(1);

    }

    class Karton extends AsyncTask<Integer, Integer, Void> {

        public Card getcards() {
            String set_server_url = "http://194.176.114.21:8050";
            try {
                URL url = new URL(set_server_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);

                OutputStream out = urlConnection.getOutputStream();
                String data = "{ \"action\": \"fetch_cards\", \"token\": " + token + " }";
                out.write(data.getBytes());

                Gson gson = new Gson();
                Card card = gson.fromJson(new InputStreamReader(urlConnection.getInputStream()), Card.class);
                urlConnection.disconnect();
                return card;
            } catch (IOException e) {
                return new Card();
            }
        }

        protected Void doInBackground(Integer... values) {
            for (int value: values) {
                kart = getcards();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            for(int i =0; i<kart.cards.toArray().length; i++){
                tiles[i].setText(String.valueOf(kart.cards.toArray()[i]));
            }
        }
    }

}
