package com.thohao.readdb_json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    String mURL = "https://khoapham.vn/KhoaPhamTraining/json/tien/demo1.json";
    ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImg = findViewById(R.id.imgview);
        new ReadJSon().execute(mURL);
    }
    class ReadJSon extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return ReadDataFromURL(strings[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("CCC", "Start read data");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doc file json
            //convert sang Object
            try {
                JSONObject jsonObject = new JSONObject(s);
                //String monhoc = jsonObject.optString(("monhoc"));
                /*dùng optString sẽ bỏ qua data kg có key hoặc key =null*/
                String monhoc = jsonObject.optString(("monhoc"));
                String noihoc = jsonObject.optString(("noihoc"));
                String website = jsonObject.optString(("website"));
                String logo = jsonObject.optString(("logo"));
                Glide
                        .with(MainActivity.this)
                        .load(logo)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.ic_launcher_background)
                        .into(mImg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("CCC", s);
        }
    }

    private String ReadDataFromURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            //create a url object
            URL url = new URL(theUrl);
            //create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            //wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            //read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
