package com.swufe.library.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swufe.library.LogManager;
import com.swufe.library.R;
import com.swufe.library.activity.DetailActivity;
import com.swufe.library.adapter.BookAdapter;
import com.swufe.library.adapter.CollectionAdapter;
import com.swufe.library.bean.Book;
import com.swufe.library.bean.Result;
import com.swufe.library.util.HttpUtil;
import com.swufe.library.util.URLs;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CollectionFragment extends Fragment implements Runnable{
    RecyclerView recyclerView;
    CollectionAdapter adapter;
    List<Book> bookList;
    String account;
    Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LogManager.SHARED_PREFERFEMCES_NAME, Activity.MODE_PRIVATE);
        account = sharedPreferences.getString("account","");

        recyclerView = view.findViewById(R.id.rv_collection);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Thread thread = new Thread(this);
        thread.start();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    bookList = (List)msg.obj;
                    adapter = new CollectionAdapter(bookList);
                    adapter.setOnItemLongClickListener(new BookAdapter.OnItemLongClickListener(){

                        @Override
                        public void onClick(int position) {
                            Book book = bookList.get(position);
                            final int book_id = book.getBook_id();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("提示")
                                    .setMessage("确定要取消收藏吗")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteCollection(account, book_id);
                                        }
                                    }).setNegativeButton("否",null);
                            builder.create().show();
                        }
                    });

                    adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int positon) {
                            Toast.makeText(getActivity(), "click"+positon, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            Book book = bookList.get(positon);
                            intent.putExtra("book",book);
                            startActivity(intent);
                        }
                    });

                    recyclerView.setAdapter(adapter);
                }
            }
        };
        return view;


    }

    @Override
    public void run() {
        List<Book> bookList = new ArrayList<>();

        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("account",account)
                    .build();
            Request request = new Request.Builder()
                    .url(URLs.getCollection)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            bookList = getBookList(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = handler.obtainMessage(1);
        message.obj = bookList;
        handler.sendMessage(message);
    }

    public List<Book> getBookList(String responseData){
        Gson gson = new Gson();
        Result<List<Book>> result = gson.fromJson(responseData,new TypeToken<Result<List<Book>>>(){}.getType());
        return result.getData();
    }

    public void deleteCollection(String account, int book_id){
        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("book_id",String.valueOf(book_id))
                .build();
        HttpUtil.sendOkHttpRequest(URLs.deleteCollection, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Looper.prepare();
                Result<Collection> result = gson.fromJson(responseData,new TypeToken<Result<Collection>>(){}.getType());
                if(result.getCode() == 200) {
                    Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        });
    }
}
