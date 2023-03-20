package com.example.yp_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Listen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);

        //убрать шапку с название проекта
        getSupportActionBar().hide();

    }
}