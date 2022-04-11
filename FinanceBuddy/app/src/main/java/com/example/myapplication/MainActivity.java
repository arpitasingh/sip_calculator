package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_btn).setOnClickListener(v->{
            Intent mIntent = new Intent(this, GoalsActivity.class);
            startActivity(mIntent);
            finish();
        });
    }
}