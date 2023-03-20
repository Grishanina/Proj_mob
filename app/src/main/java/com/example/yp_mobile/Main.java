package com.example.yp_mobile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main extends AppCompatActivity {

    private TextView greetings;
    private ImageView icon;

    private AdapterQuotes QouterAdapter;
    private List<QueteMaska> Quotelist = new ArrayList<>();

    private AdapterFeelings FeelingsAdapter;
    private List<MaskaFeelings> feelingMaska = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //убрать шапку с название проекта
        getSupportActionBar().hide();

        ListView Product = findViewById(R.id.ListQuotes);
        QouterAdapter = new AdapterQuotes(Main.this, Quotelist);
        Product.setAdapter(QouterAdapter);
        new GetQuotes().execute();


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RecyclerView Mood = findViewById(R.id.moodList);
        Mood.setHasFixedSize(true);
        Mood.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        FeelingsAdapter = new AdapterFeelings(feelingMaska,Main.this);
        Mood.setAdapter(FeelingsAdapter);


        greetings = findViewById(R.id.privetstvie);
        greetings.setText("С возвращением, "+ Login.Users.getNickName() + "!");
        icon=findViewById(R.id.profilImage);
        new DownloadImageTask((ImageView) icon).execute(Login.Users.getAvatar());


        new GetQuotes().execute();
        new GetFeeling().execute();

    }
    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    private class GetFeeling extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                feelingMaska.clear();
                FeelingsAdapter.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    MaskaFeelings tempProduct = new MaskaFeelings(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getInt("position")
                    );
                    feelingMaska.add(tempProduct);
                    FeelingsAdapter.notifyDataSetChanged();
                }
                feelingMaska.sort(Comparator.comparing(MaskaFeelings::getPosition));
                FeelingsAdapter.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                Toast.makeText(Main.this, "При выводе данных произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private class GetQuotes extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();

                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result=new StringBuilder();
                String line= "";
                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
               JSONObject object=new JSONObject(s);
                JSONArray tempArray= object.getJSONArray("data") ;
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    QueteMaska tempProduct = new QueteMaska(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getString("description")
                    );

                    Quotelist.add(tempProduct);
                    QouterAdapter.notifyDataSetInvalidated();
                }
            }
            catch (Exception exception)
            {
                Toast.makeText(Main.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Listen (View v) {
        Intent intent = new Intent( this, Listen.class);
        startActivity(intent);
    }

    public void profile (View v) {
        Intent intent = new Intent( this, Profile.class);
        startActivity(intent);
    }
}