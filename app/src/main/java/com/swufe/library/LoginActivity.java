package com.swufe.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTxt_login_account;
    EditText edtTxt_login_pwd;
    Button btn_login_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtTxt_login_account = findViewById(R.id.edtTxt_login_account);
        edtTxt_login_pwd = findViewById(R.id.edtTxt_login_pwd);
        btn_login_submit = findViewById(R.id.btn_login_submit);
        btn_login_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String account = edtTxt_login_account.getText().toString();
        String pwd = edtTxt_login_pwd.getText().toString();
    }

    public void loginWithOkHttp(String address, String account, String pwd){

    }
}
