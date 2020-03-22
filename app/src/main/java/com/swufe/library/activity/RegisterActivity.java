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
import com.swufe.library.R;
import com.swufe.library.bean.Reader;
import com.swufe.library.bean.Result;
import com.swufe.library.util.URLs;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    String TAG = "TestRegister";
    EditText edtTxt_register_account, edtTxt_register_telephone, edtTxt_register_username,  edtTxt_register_pwd, edtTxt_register_pwdConfirm;
    Button btn_register_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtTxt_register_account = findViewById(R.id.edtTxt_register_account);
        edtTxt_register_telephone = findViewById(R.id.edtTxt_register_telephone);
        edtTxt_register_username = findViewById(R.id.edtTxt_register_username);
        edtTxt_register_pwd = findViewById(R.id.edtTxt_register_pwd);
        edtTxt_register_pwdConfirm = findViewById(R.id.edtTxt_register_pwdConfirm);
        btn_register_register = findViewById(R.id.btn_register_register);

    }

    public void register(View view){
        final String account = edtTxt_register_account.getText().toString().trim();
        final String telephone = edtTxt_register_telephone.getText().toString().trim();
        final String username = edtTxt_register_username.getText().toString().trim();
        final String pwd = edtTxt_register_pwd.getText().toString().trim();
        final String pwdConfirm = edtTxt_register_pwdConfirm.getText().toString().trim();

        if(TextUtils.isEmpty(account)){
            edtTxt_register_account.setError("Enter account");
            edtTxt_register_account.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(telephone)){
            edtTxt_register_telephone.setError("电话号码不能为空");
        }

        if(TextUtils.isEmpty(username)){
            edtTxt_register_username.setError("Enter username");
            edtTxt_register_username.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(pwd)){
            edtTxt_register_pwd.setError("Enter password");
            edtTxt_register_pwd.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(pwdConfirm)){
            edtTxt_register_pwdConfirm.setError("Enter password again");
            edtTxt_register_pwdConfirm.requestFocus();
            return;
        }

        if(!pwd.equals(pwdConfirm)){
            edtTxt_register_pwdConfirm.setError("Passwords do not match");
            edtTxt_register_pwdConfirm.requestFocus();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("telephone",telephone)
                .add("username",username)
                .add("password",pwd)
                .build();

        Request request = new Request.Builder()
                .url(URLs.register)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                //                Log.i(TAG, "responseData:"+responseData);
                Gson gson = new Gson();
                Result<Reader> result = new Result<>();
                result = gson.fromJson(responseData, new TypeToken<Result<Reader>>(){}.getType());
                Looper.prepare();
                if(result.code == 200){
                    Toast.makeText(getApplicationContext(), result.message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this, result.message, Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        });
    }
}
