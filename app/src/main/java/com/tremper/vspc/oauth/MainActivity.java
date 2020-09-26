package com.tremper.vspc.oauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private static final String USER_LOGIN = "Tremper";

    private static final String USER_PASSWORD = "1234";

    private TextInputEditText login_in;

    private TextInputEditText password_in;

    private MaterialButton login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_in = findViewById(R.id.input_login);

        password_in = findViewById(R.id.input_password);

        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(loginOnClickListener);
    }

    private final View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(login_in.getText().toString().equals(USER_LOGIN) && password_in.getText().toString().equals(USER_PASSWORD)){
                Intent intent = new Intent(view.getContext(),HelloActivity.class);
                intent.putExtra(HelloActivity.ARG_NAME, USER_LOGIN);
                startActivity(intent);
            }
            else {
                Toast.makeText(view.getContext(),"Неверный логин или пароль.", Toast.LENGTH_SHORT).show();
            }
        }
    };

}