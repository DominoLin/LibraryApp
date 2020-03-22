//package com.swufe.library;
//
//import android.util.Log;
//import android.view.View;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.swufe.library.adapter.BookAdapter;
//import com.swufe.library.bean.Book;
//import com.swufe.library.bean.Result;
//import com.swufe.library.util.HttpUtil;
//import com.swufe.library.util.URLs;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class Temp {
//
//     btn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String bookName = search.getText().toString();
//            if(!bookName.equals("")){
//                RequestBody body = new FormBody.Builder()
//                        .add("bookName",bookName)
//                        .build();
//                HttpUtil.sendOkHttpRequest(URLs.search, body, new Callback() {
//                    @Override
//                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                        String responseData = response.body().string();
//                        Log.i(TAG, "onResponse: "+responseData);
//                        Gson gson = new Gson();
//                        Result<List<Book>> result = gson.fromJson(responseData,new TypeToken<Result<List<Book>>>(){}.getType());
//                        List<Book> books = result.getData();
//                        BookAdapter bookAdapter= new BookAdapter(books);
//                        RecyclerView r = view.findViewById(R.id.recyclerView);
//                        LinearLayoutManager l = new LinearLayoutManager(getContext());
//                        r.setLayoutManager(l);
//                        r.setAdapter(bookAdapter);
//                    }
//                });
//            }else {
//                return;
//            }
//        }
//    });
//}
