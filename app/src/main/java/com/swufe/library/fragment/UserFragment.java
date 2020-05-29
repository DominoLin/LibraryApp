package com.swufe.library.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swufe.library.LogManager;
import com.swufe.library.R;
import com.swufe.library.bean.Reader;
import com.swufe.library.bean.ReaderInfo;
import com.swufe.library.bean.Result;
import com.swufe.library.util.HttpUtil;
import com.swufe.library.util.URLs;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;

import okhttp3.RequestBody;
import okhttp3.Response;

public class UserFragment extends Fragment{
    TextView tv_user_college, tv_user_major, tv_user_account, tv_user_name, tv_user_telephone;
    String college,major,account,name,telephone;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        tv_user_college = view.findViewById(R.id.tv_user_college);
        tv_user_major = view.findViewById(R.id.tv_user_major);
        tv_user_account = view.findViewById(R.id.tv_user_account);
        tv_user_name = view.findViewById(R.id.tv_user_name);

        tv_user_telephone = view.findViewById(R.id.tv_user_telephone);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LogManager.SHARED_PREFERFEMCES_NAME, Activity.MODE_PRIVATE);
        account = sharedPreferences.getString("account","");
        name = sharedPreferences.getString("username","");
        telephone = sharedPreferences.getString("telephone","");

        tv_user_account.setText(account);
        tv_user_name.setText(name);
        tv_user_telephone.setText(telephone);

        tv_user_telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_user_telephone.setEnabled(true);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getReaderInfo(account);


    }

    private void getReaderInfo(String account) {
        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .build();
        HttpUtil.sendOkHttpRequest(URLs.userInfo, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Result<Reader> result = gson.fromJson(responseData, new TypeToken<Result<Reader>>(){}.getType());

                if(result.getCode() == 200){
                    Looper.prepare();
                    Reader reader = result.getData();
                    college = reader.getCollege();
                    major = reader.getMajor();
                    tv_user_college.setText(college);
                    tv_user_major.setText(major);

                }else {
                    Looper.prepare();
                    Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                Looper.loop();
            }
        });
    }

}