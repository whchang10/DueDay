package com.example.kl.a268project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;


public class Welcome_Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_s);

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
