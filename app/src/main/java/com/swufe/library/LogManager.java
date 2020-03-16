package com.swufe.library;

import android.content.Context;
import android.content.SharedPreferences;

public class LogManager {

    private static final String SHARED_PREFERFEMCES_NAME = "logSharedPreferences";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";

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

    public void userLogin(User user){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERFEMCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ACCOUNT, user.getAccount());
        editor.putString(PASSWORD, user.getPassword());
        editor.apply();

    }

    public Boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERFEMCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD,null) != null;
    }

}
