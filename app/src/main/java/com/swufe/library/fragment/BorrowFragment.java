package com.swufe.library.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
import com.swufe.library.adapter.BookAdapter;
import com.swufe.library.adapter.BorrowAdapter;
import com.swufe.library.bean.BorrowItem;
import com.swufe.library.bean.Lend;
import com.swufe.library.bean.Result;
import com.swufe.library.util.URLs;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BorrowFragment extends Fragment implements Runnable{

    RecyclerView recyclerView;
    BorrowAdapter adapter;
    List<BorrowItem> borrowItemList;
    String account;
    Handler handler;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow, container, false);
        recyclerView = view.findViewById(R.id.rv_borrow);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Thread thread = new Thread(this);
        thread.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    borrowItemList = (List) msg.obj;
                    adapter = new BorrowAdapter(borrowItemList);
                    adapter.setOnItemLongClickListener(new BookAdapter.OnItemLongClickListener() {
                        @Override
                        public void onClick(int position) {
                            BorrowItem borrowItem = borrowItemList.get(position);
                            final int lend_id = borrowItem.getLend_id();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("提示")
                                    .setMessage("确定要还书吗")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
//                                            Log.i(TAG, "onClick: 对话框事件处理");
                                            returnBook(lend_id);
                                        }
                                    }).setNegativeButton("否",null);
                            builder.create().show();

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
        List<BorrowItem> borrowItemList = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LogManager.SHARED_PREFERFEMCES_NAME, Activity.MODE_PRIVATE);
        account = sharedPreferences.getString("account","");
        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("account",account)
                    .build();
            Request request = new Request.Builder()
                    .url(URLs.allLend)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            borrowItemList = getBorrowItemList(responseData);
        }catch (Exception e){
            e.printStackTrace();
        }
        Message message = handler.obtainMessage(1);
        message.obj = borrowItemList;
        handler.sendMessage(message);




    }

    public List<BorrowItem> getBorrowItemList(String responseData){
        List<BorrowItem> borrowItems = new ArrayList<>();
        Gson gson = new Gson();
        Result<List<Lend>> result = gson.fromJson(responseData,new TypeToken<Result<List<Lend>>>(){}.getType());
        List<Lend> lendList = result.getData();
        for(Lend lend:lendList){
            BorrowItem item = new BorrowItem(getDays(lend.getLend_date()),lend.getBook_name(),lend.getLend_date(),lend.getBack_date(),lend.getLend_id());
            borrowItems.add(item);
        }


        return borrowItems;
    }

    public String getDays(String borrowDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String now1 = formatter.format(now);
        Long l = 0L;
        try {
            Date now2 = formatter.parse(now1);
            long back = now2.getTime();
            Date b = formatter.parse(borrowDate);
            long borrow = b.getTime();
            l = (borrow-back) / (1000*60*60*24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(30+l.intValue());
    }

    public void returnBook(int lend_id){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("lend_id",String.valueOf(lend_id))
                .build();

        Request request = new Request.Builder()
                .url(URLs.updateLend)
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
                Result<Lend> result = gson.fromJson(responseData,new TypeToken<Result<Lend>>(){}.getType());
                Looper.prepare();
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
