package com.example.whyattc4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static String opponent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeScreen( View v ) {
        Button value = (Button)v;

        opponent = value.getText().toString();

        Intent myIntent = new Intent(this,GameUI.class);
        this.startActivity(myIntent);

    }



}