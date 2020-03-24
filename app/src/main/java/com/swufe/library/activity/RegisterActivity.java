package com.swufe.library.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
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
    EditText account, telephone, username, pwd, pwdConfirm, college, major;
    Button register,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        account = findViewById(R.id.edtTxt_register_account);
        telephone = findViewById(R.id.edtTxt_register_telephone);
        username = findViewById(R.id.edtTxt_register_username);
        pwd = findViewById(R.id.edtTxt_register_pwd);
        pwdConfirm = findViewById(R.id.edtTxt_register_pwdConfirm);
        college = findViewById(R.id.edtTxt_register_college);
        major = findViewById(R.id.edtTxt_register_major);
        register = findViewById(R.id.btn_register_register);
        cancel = findViewById(R.id.btn_register_cancel);

    }

    public void register(View view){
        final String account = this.account.getText().toString().trim();
        final String telephone = this.telephone.getText().toString().trim();
        final String username = this.username.getText().toString().trim();
        final String pwd = this.pwd.getText().toString().trim();
        final String pwdConfirm = this.pwdConfirm.getText().toString().trim();
        final String college = this.college.getText().toString().trim();
        final String major = this.major.getText().toString().trim();


        if(TextUtils.isEmpty(college)){
            this.college.setError("学院不能为空");
            this.college.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(major)){
            this.major.setError("专业不能为空");
            this.major.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(username)){
            this.username.setError("姓名不能为空");
            this.username.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(account)){
            this.account.setError("学号不能为空");
            this.account.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(telephone)){
            this.telephone.setError("电话号码不能为空");
        }

        if(TextUtils.isEmpty(pwd)){
            this.pwd.setError("密码不能为空");
            this.pwd.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(pwdConfirm)){
            this.pwdConfirm.setError("密码不能为空");
            this.pwdConfirm.requestFocus();
            return;
        }

        if(!pwd.equals(pwdConfirm)){
            this.pwdConfirm.setError("两次输入密码不一致");
            this.pwdConfirm.requestFocus();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("telephone",telephone)
                .add("username",username)
                .add("password",pwd)
                .add("college",college)
                .add("major",major)
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
                Gson gson = new Gson();
                Result<Reader> result = gson.fromJson(responseData, new TypeToken<Result<Reader>>(){}.getType());
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

    public void cancel(View view){
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

}
