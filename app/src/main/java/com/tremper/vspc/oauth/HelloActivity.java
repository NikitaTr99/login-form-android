package com.tremper.vspc.oauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {

    public static final String ARG_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        TextView tv = findViewById(R.id.hello_tv);

        tv.setText(getIntent().getStringExtra(ARG_NAME));

    }
}