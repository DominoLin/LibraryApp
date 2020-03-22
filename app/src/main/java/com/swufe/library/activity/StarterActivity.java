package com.swufe.library.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.swufe.library.R;

public class StarterActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        final Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(1000);
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();//结束当前Activity
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
       thread.start();
    }
}
