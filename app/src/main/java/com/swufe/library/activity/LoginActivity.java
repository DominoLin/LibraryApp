package com.swufe.library.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swufe.library.LogManager;
import com.swufe.library.R;
import com.swufe.library.bean.Result;
import com.swufe.library.util.URLs;
import com.swufe.library.bean.Reader;
import com.swufe.library.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    String TAG = "LoginActivity";
    EditText edtTxt_login_account, edtTxt_login_pwd;

    Button btn_login_submit, btn_login_forget, btn_login_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //判断是否登录
        if(LogManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        edtTxt_login_account = findViewById(R.id.edtTxt_login_account);
        edtTxt_login_pwd = findViewById(R.id.edtTxt_login_pwd);
        btn_login_submit = findViewById(R.id.btn_login_submit);
        btn_login_forget = findViewById(R.id.btn_login_forget);
        btn_login_register = findViewById(R.id.btn_login_register);



    }

    public void register(View view){
        final Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
        finish();
    }
    public void forget(View view){
       final Intent forgetIntent = new Intent(this,ForgetActivity.class);
       startActivity(forgetIntent);
       finish();
    }

    public void login(View view){
        //获取输入框输入
        String account = edtTxt_login_account.getText().toString();
        String pwd = edtTxt_login_pwd.getText().toString();
        //判断是否为空
        if(TextUtils.isEmpty(account)){
            edtTxt_login_account.setError("请输入用户名");
            edtTxt_login_account.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            edtTxt_login_pwd.setError("请输入密码");
            edtTxt_login_pwd.requestFocus();
            return;
        }

        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("password",pwd)
                .build();
        HttpUtil.sendOkHttpRequest(URLs.login, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Looper.prepare();
                Log.i(TAG, "onFailure: 无法连接"+URLs.login);
                Looper.loop();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                String responseData = response.body().string();
                Result<Reader> result = gson.fromJson(responseData, new TypeToken<Result<Reader>>(){}.getType());
                Looper.prepare();
                if(result.code == 200){
                    Toast.makeText(LoginActivity.this, result.message, Toast.LENGTH_SHORT).show();
                    Reader reader = result.getData();
                    LogManager.getInstance(getApplicationContext()).readerLogin(reader);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else if(result.code == 0){
                    Toast.makeText(LoginActivity.this, result.message, Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        });
    }



}
