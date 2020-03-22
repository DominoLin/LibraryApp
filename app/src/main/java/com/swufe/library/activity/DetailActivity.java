package com.swufe.library.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.swufe.library.R;
import com.swufe.library.bean.Book;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    String TAG = "DetailActivity";
    TextView author, publish, pub_date, position, number, introduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle(book.getName());
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        author = findViewById(R.id.tv_detail_author);
        publish = findViewById(R.id.tv_detail_publish);
        pub_date = findViewById(R.id.tv_detail_pubDate);
        position = findViewById(R.id.tv_detail_position);
        number = findViewById(R.id.tv_detail_number);
        introduction = findViewById(R.id.tv_detail_introduction);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

//        Log.i(TAG, "onCreate: "+simpleDateFormat.format(book.getPub_date()));
        author.setText(book.getAuthor());
        publish.setText(book.getPublish());
        pub_date.setText(simpleDateFormat.format(book.getPub_date()));
        number.setText(String.valueOf(book.getNumber()));
        introduction.setText(book.getIntroduction());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
