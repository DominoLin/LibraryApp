package com.swufe.library.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swufe.library.LogManager;
import com.swufe.library.R;
import com.swufe.library.bean.Book;
import com.swufe.library.bean.Lend;
import com.swufe.library.bean.Result;
import com.swufe.library.util.HttpUtil;
import com.swufe.library.util.URLs;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    String TAG = "DetailActivity";
    TextView author, publish, pub_date, isbn, language, position, number, introduction;
    ImageView imageView;
    Intent intent;
    SharedPreferences sharedPreferences;
    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent();
        sharedPreferences = getSharedPreferences(LogManager.SHARED_PREFERFEMCES_NAME, Activity.MODE_PRIVATE);

        book = (Book) intent.getSerializableExtra("book");


        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle(book.getName());
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        imageView = findViewById(R.id.img_detail);
        author = findViewById(R.id.tv_detail_author);
        publish = findViewById(R.id.tv_detail_publish);
        pub_date = findViewById(R.id.tv_detail_pubDate);
        isbn = findViewById(R.id.tv_detail_ISBN);
        language = findViewById(R.id.tv_detail_language);
        position = findViewById(R.id.tv_detail_position);
        number = findViewById(R.id.tv_detail_number);
        introduction = findViewById(R.id.tv_detail_introduction);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        Glide.with(getApplicationContext()).load(URLs.picture+book.getISBN()+".jpg").into(imageView);
        author.setText(book.getAuthor());
        publish.setText(book.getPublish());
        pub_date.setText(simpleDateFormat.format(book.getPub_date()));
        isbn.setText(book.getISBN());
        language.setText(book.getLanguage());
        number.setText(String.valueOf(book.getNum()));
        position.setText(book.getPosition());
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

    public void collect(View view){
        String account = sharedPreferences.getString("account","");
        String book_id = String.valueOf(book.getBook_id());
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("book_id",book_id)
                .build();

        Request request = new Request.Builder()
                .url(URLs.addCollection)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseDate = response.body().string();
                Gson gson = new Gson();
                Result<Collection> result = gson.fromJson(responseDate,new TypeToken<Result<Collection>>(){}.getType());
                Looper.prepare();
                if(result.getCode() == 200) {
                    Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        });
    }
    public void borrow(View view) {

        String account = sharedPreferences.getString("account","");
        String book_id = String.valueOf(book.getBook_id());
        String book_name = book.getName();

        Log.d(TAG, "borrow: "+account+book_id+book_name);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("book_id",book_id)
                .add("book_name",book_name)
                .build();

        Request request = new Request.Builder()
                .url(URLs.addLend)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseDate = response.body().string();
                Gson gson = new Gson();
                Result<Lend> result = gson.fromJson(responseDate,new TypeToken<Result<Lend>>(){}.getType());
                Looper.prepare();
                if(result.getCode() == 200) {
                    Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        });
    }


}
