package com.swufe.library.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swufe.library.R;
import com.swufe.library.bean.Reader;
import com.swufe.library.bean.Result;
import com.swufe.library.util.HttpUtil;
import com.swufe.library.util.URLs;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ForgetActivity extends AppCompatActivity {
    EditText edtTxt_forget_account, edtTxt_forget_telephone, edtTxt_forget_pwd, edtTxt_forget_pwdConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        edtTxt_forget_account = findViewById(R.id.edtTxt_forget_account);
        edtTxt_forget_telephone = findViewById(R.id.edtTxt_forget_telephone);
        edtTxt_forget_pwd = findViewById(R.id.edtTxt_forget_password);
        edtTxt_forget_pwdConfirm = findViewById(R.id.edtTxt_forget_passwordConfirm);
    }

    public void findPwd(View view){
        String account = edtTxt_forget_account.getText().toString().trim();
        String telephone = edtTxt_forget_telephone.getText().toString().trim();
        String pwd = edtTxt_forget_pwd.getText().toString().trim();
        String pwdConfirm = edtTxt_forget_pwdConfirm.getText().toString().trim();

        if(TextUtils.isEmpty(account)){
            edtTxt_forget_account.setError("Enter your username");
            edtTxt_forget_account.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(account)) {
            edtTxt_forget_telephone.setError("Enter your username");
            edtTxt_forget_telephone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(account)) {
            edtTxt_forget_pwd.setError("Enter your username");
            edtTxt_forget_pwd.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(account)) {
            edtTxt_forget_pwdConfirm.setError("Enter your username");
            edtTxt_forget_pwdConfirm.requestFocus();
            return;
        }
        if(!pwd.equals(pwdConfirm)){
            edtTxt_forget_pwdConfirm.setError("两次输入的密码不同");
            edtTxt_forget_pwdConfirm.requestFocus();
            return;
        }
        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("telephone",telephone)
                .add("password",pwd)
                .build();
        HttpUtil.sendOkHttpRequest(URLs.findPwd, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Result<Reader> result = gson.fromJson(responseData, new TypeToken<Result<Reader>>(){}.getType());
                Looper.prepare();
                if(result.code == 200){
                    Toast.makeText(ForgetActivity.this, result.message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgetActivity.this,LoginActivity.class));
                    finish();
                }else {
                    Toast.makeText(ForgetActivity.this, result.message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
