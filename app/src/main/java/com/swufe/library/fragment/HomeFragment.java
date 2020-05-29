package com.swufe.library.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swufe.library.R;
import com.swufe.library.activity.DetailActivity;
import com.swufe.library.bean.Result;
import com.swufe.library.util.HttpUtil;
import com.swufe.library.util.URLs;
import com.swufe.library.adapter.BookAdapter;
import com.swufe.library.bean.Book;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment implements Runnable{

    String TAG = "HomeFragmentTest";

    RecyclerView recyclerView;
    List<Book> bookList;
    BookAdapter adapter;
    Handler handler;
    EditText search;
    ImageButton btn;

    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        Thread thread = new Thread(this);
        thread.start();
        search = view.findViewById(R.id.edtTxt_home_search);
        btn = view.findViewById(R.id.imgBtn_home_search);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        bookList = new ArrayList<>();



        handler = new Handler(){
            @SuppressLint("WrongConstant")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    bookList = (List)msg.obj;
//                    Log.i(TAG, "handleMessage: "+bookList);

                    adapter = new BookAdapter(bookList);
                    adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int positon) {
                            Toast.makeText(getActivity(), "click"+positon, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),DetailActivity.class);
                            Book book = bookList.get(positon);
                            intent.putExtra("book",book);
                            Log.i(TAG, "onClick: "+book.getName());
                            startActivity(intent);
                        }
                    });
                    adapter.setOnItemLongClickListener(new BookAdapter.OnItemLongClickListener() {
                        @Override
                        public void onClick(int position) {
                            Toast.makeText(getActivity(), "Long click"+position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = search.getText().toString();
                if(!bookName.equals("")){
                    RequestBody body = new FormBody.Builder()
                            .add("bookName",bookName)
                            .build();
                    HttpUtil.sendOkHttpRequest(URLs.search, body, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String responseData = response.body().string();
                            Log.i(TAG, "onResponse: "+responseData);
                            Gson gson = new Gson();
                            Result<List<Book>> result = gson.fromJson(responseData,new TypeToken<Result<List<Book>>>(){}.getType());
                            final List<Book> books = result.getData();
                            final BookAdapter bookAdapter= new BookAdapter(books);
                            bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(int positon) {
                                    Toast.makeText(getActivity(), "click"+positon, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(),DetailActivity.class);
                                    Book book = books.get(positon);
                                    intent.putExtra("book",book);
                                    Log.i(TAG, "onClick: "+book.getName());
                                    startActivity(intent);
                                }
                            });
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RecyclerView r = view.findViewById(R.id.recyclerView);
                                    LinearLayoutManager l = new LinearLayoutManager(getContext());
                                    r.setLayoutManager(l);
                                    r.setAdapter(bookAdapter);
                                }
                            });


                        }
                    });
                }else {
                    return;
                }
            }
        });

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void run() {
        List<Book> books = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URLs.getAllBooks)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            books= parseJsonWithGson(responseData);


        } catch (Exception e) {
            e.printStackTrace();
        }


        Message message = handler.obtainMessage(1);
        message.obj = books;
        handler.sendMessage(message);

    }


    public List<Book> parseJsonWithGson(String responseData){
        Gson gson = new Gson();
        List<Book> bookList = gson.fromJson(responseData,new TypeToken<List<Book>>(){}.getType());
        return bookList;

    }


}
