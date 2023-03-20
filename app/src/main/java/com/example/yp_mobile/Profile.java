package com.example.yp_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Profile extends AppCompatActivity {

    private TextView nickname;
    private ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //убрать шапку с название проекта
        getSupportActionBar().hide();

        nickname = findViewById(R.id.Name);
        nickname.setText(Login.Users.getNickName());
        icon=findViewById(R.id.profilImage);
        new DownloadImageTask((ImageView) icon).execute(Login.Users.getAvatar());
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void Listen (View v) {
        Intent intent = new Intent( this, Listen.class);
        startActivity(intent);
    }

    public void Main (View v) {
        Intent intent = new Intent( this, Main.class);
        startActivity(intent);
    }

    public void login (View v) {
        Intent intent = new Intent( this, Login.class);
        startActivity(intent);
    }
}