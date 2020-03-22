package com.swufe.library.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.swufe.library.R;
import com.swufe.library.bean.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity  {

    List<Book> bookList;
    EditText search;
    ImageButton btn;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bookList = new ArrayList<>();
        search = findViewById(R.id.edtTxt_search_search);
        btn = findViewById(R.id.imgBtn_search_search);


    }
}
