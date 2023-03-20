package com.example.yp_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Onboarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        //убрать шапку с название проекта
        getSupportActionBar().hide();
    }
    public void Entrance(View v) {
        Intent intent = new Intent( this, Login.class);
        startActivity(intent);
    }
}