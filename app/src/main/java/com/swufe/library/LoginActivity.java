package com.swufe.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtTxt_login_account, edtTxt_login_pwd;
    Button btn_login_submit, btn_login_forget, btn_login_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //判断是否登录



        edtTxt_login_account = findViewById(R.id.edtTxt_login_account);
        edtTxt_login_pwd = findViewById(R.id.edtTxt_login_pwd);
        btn_login_submit = findViewById(R.id.btn_login_submit);
        btn_login_forget = findViewById(R.id.btn_login_forget);
        btn_login_register = findViewById(R.id.btn_login_register);



    }

    public void register(View view){
        final Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }
    public void forget(View view){
        Toast.makeText(getApplicationContext(), "找回密码",Toast.LENGTH_LONG).show();

    }



    public void login(View view){
        String url = "http://192.168.0.152:8080/user/api";
        String account = edtTxt_login_account.getText().toString();
        String pwd = edtTxt_login_pwd.getText().toString();

        if(TextUtils.isEmpty(account)){
            edtTxt_login_account.setError("Enter your username");
            edtTxt_login_account.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            edtTxt_login_pwd.setError("Enter your password");
            edtTxt_login_pwd.requestFocus();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("password",pwd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("TestLogin", "onFailure: 连接失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "登录成功",Toast.LENGTH_LONG).show();
                    Looper.loop();
                    String json = response.body().string();
                    Log.i("TestLogin", "json: "+json);
                }
            }
        });
    }



}
