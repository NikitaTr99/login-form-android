package com.tremper.vspc.oauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {

    private static final String ARG_NAME = "name";

    public static Intent newIntent(Context context, String account_name){
        Intent intent = new Intent(context,HelloActivity.class);
        intent.putExtra(ARG_NAME, account_name);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        TextView tv = findViewById(R.id.hello_tv);

        tv.setText(getIntent().getStringExtra(ARG_NAME));

    }
}