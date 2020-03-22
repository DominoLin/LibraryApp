package com.swufe.library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.swufe.library.activity.LoginActivity;
import com.swufe.library.bean.Reader;

public class LogManager {

    public static final String SHARED_PREFERFEMCES_NAME = "logSharedPreferences";
    private static final String ACCOUNT = "account";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String TELEPHONE = "telephone";

    private static LogManager mInstance;
    private static Context mContext;

    private LogManager(Context context){
        mContext = context;
    }

    public static synchronized LogManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new LogManager(context);
        }
        return mInstance;
    }

    public void readerLogin(Reader reader){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERFEMCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCOUNT, String.valueOf(reader.getAccount()));
        editor.putString(USERNAME, reader.getUsername());
        editor.putString(PASSWORD, reader.getPassword());
        editor.putString(TELEPHONE,reader.getTelephone());
        editor.apply();

    }

    public Boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERFEMCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ACCOUNT,null) != null;
    }

    public void logout(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERFEMCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }

}
