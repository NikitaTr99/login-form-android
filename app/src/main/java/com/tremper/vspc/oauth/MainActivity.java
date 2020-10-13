package com.tremper.vspc.oauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tremper.vspc.oauth.account.Account;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    private static final String USER_LOGIN = "Tremper";

    private static final String USER_PASSWORD = "1234";

    //    public static final String CONNECTION_STRING = "http://192.168.0.141:8085/api/account/auth?";
    public static final String CONNECTION_STRING = "http://172.28.192.1:8085/api/account/auth?";

    @BindView(R.id.input_login)
    TextInputEditText login_in;

    @BindView(R.id.input_password)
    TextInputEditText password_in;

    @BindView(R.id.login_button)
    MaterialButton login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butterknife.ButterKnife.bind(this);

        login_button.setOnClickListener(loginOnClickListener);
    }

    private final View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Account account = new Account(login_in.getText().toString(), password_in.getText().toString());

            new CheckAccount2(view.getContext()).execute(account);

        }
    };


    private class CheckAccount extends AsyncTask<Account, Void, Void> {

        boolean isLogin;

        Context context;

        CheckAccount(Context c) {
            context = c;
        }

        @Override
        protected Void doInBackground(Account... accounts) {


            final String url = CONNECTION_STRING + "login=" + accounts[0].getLogin() + "&password=" + accounts[0].getPassword();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            boolean greeting = restTemplate.getForObject(url, boolean.class);

            isLogin = greeting;

//            URL obj = null;
//            HttpURLConnection connection = null;
//            BufferedReader in = null;
//
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//
//            try {
//                obj = new URL(CONNECTION_STRING + "login=" + accounts[0].getLogin() + "&password=" + accounts[0].getPassword());
//                connection = (HttpURLConnection) obj.openConnection();
//                connection.setReadTimeout(1000);
//                connection.setRequestMethod("GET");
//
//                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            isLogin = response.toString().equals("true");


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (isLogin) {
                startActivity(HelloActivity.newIntent(context, login_in.getText().toString()));
            } else {
                Toast.makeText(context, "Неверный логин или пароль.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CheckAccount2 extends AsyncTask<Account, Void, Response> {

        boolean isLogin;

        Context context;

        CheckAccount2(Context c) {
            context = c;
        }

        @Override
        protected Response doInBackground(Account... accounts) {


//
//            final String url = CONNECTION_STRING + "login=" + accounts[0].getLogin() + "&password=" + accounts[0].getPassword();
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            boolean greeting = restTemplate.getForObject(url, boolean.class);
//
//            isLogin = greeting;

            URL obj = null;
            HttpURLConnection connection = null;
            BufferedReader in = null;

            String inputLine;
            StringBuilder response = new StringBuilder();

            try {
                obj = new URL(CONNECTION_STRING + "login=" + accounts[0].getLogin() + "&password=" + accounts[0].getPassword());
                connection = (HttpURLConnection) obj.openConnection();
                connection.setConnectTimeout(1000);
                connection.setRequestMethod("GET");

                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } catch (SocketTimeoutException ex) {
                return Response.ERROR;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response.toString().equals("true")) return Response.GRANTED;

            return null;
        }


        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (response) {
                case GRANTED:
                    startActivity(HelloActivity.newIntent(context, login_in.getText().toString()));
                    break;
                case DENIED:
                    Toast.makeText(context, "Неверный логин или пароль.", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(context, "Сервер недоступен.", Toast.LENGTH_LONG).show();
            }
        }

    }
}