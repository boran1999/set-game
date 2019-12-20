package com.example.cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText et;
    String nick;
    TextView tv;
    int token;
    SharedPreferences trents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.nick);
        trents = getSharedPreferences("users", Context.MODE_PRIVATE);
        tv = findViewById(R.id.t1);
    }

    class RequestTask extends AsyncTask<int[], Integer, Void> {

        public Registr getData() {
            String set_server_url = "http://194.176.114.21:8050";
            try {
                URL url = new URL(set_server_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);

                OutputStream out = urlConnection.getOutputStream();
                String data = "{ \"action\": \"register\", \"nickname\": \"" + nick + "\" }";
                out.write(data.getBytes());

                Gson gson = new Gson();
                Registr reg = gson.fromJson(new InputStreamReader(urlConnection.getInputStream()), Registr.class);
                urlConnection.disconnect();
                return reg;
            }
            catch (IOException e) {
                return new Registr();
            }
        }

        protected Void doInBackground(int[]... values) {
            for (int value: values[0]) {
                Registr reg = getData();
                if(reg.status.equals("error")){
                    trents.edit().putInt("trent", -1).apply();
                }
                else {
                    token = reg.token;
                    trents.edit().putInt("trent", token).apply();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(token==-1){
                tv.setText("Enter other name");
            }
            else {
                nick = et.getText().toString();
                Intent i = new Intent(MainActivity.this, GameCLass.class);
                i.putExtra("name", String.valueOf(nick));
                i.putExtra("token", token);
                startActivity(i);
            }
        }
    }


    public void onClick(View v) throws InterruptedException {
        RequestTask async = new RequestTask();
        async.execute(new int[]{1});
    }
}
