package com.swufe.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;

public class SharedPreferencesManager {
    private static final String DEFAULT_SHARED_PREFERENCE = "DEFAULT_SHARED_PREFERENCE";
    private static HashMap<String, SharedPreferencesManager> preferencesManagerHashMap = new HashMap<>();//存储项目中的sharedPreferences
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Gson gson;
    //构造函数
    private SharedPreferencesManager(Context context, String name) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);//获取sharedPreference
        editor = sharedPreferences.edit();//生成editor
    }


    private static SharedPreferencesManager getSharedPreferencesManager(Context context, String sharedPreferenceName) {
        if (preferencesManagerHashMap == null)
            preferencesManagerHashMap = new HashMap<>();
        // 如果preferenceManagerHashMap 为null就新建一个
        String name = TextUtils.isEmpty(sharedPreferenceName) ? DEFAULT_SHARED_PREFERENCE : sharedPreferenceName;
        SharedPreferencesManager sharedPreferencesManager = preferencesManagerHashMap.get(name);
        if (sharedPreferencesManager == null) {
            sharedPreferencesManager = new SharedPreferencesManager(context, name);
            preferencesManagerHashMap.put(name, sharedPreferencesManager);
        }
        return sharedPreferencesManager;
    }

    public SharedPreferencesManager putValue(String key, Object value) {

        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            gson = new Gson();
            String json = gson.toJson(value);
            editor.putString(key, json);
        }
        editor.commit();
        return this;
    }

    public <T> T getValue(String key, Class<T> type) {
        if (type == Integer.class) {
            Integer value = sharedPreferences.getInt(key, 0);
            return (T) value;
        } else if (type == Boolean.class) {
            Boolean value = sharedPreferences.getBoolean(key, false);
            return (T) value;
        } else if (type == Float.class) {
            Float value = sharedPreferences.getFloat(key, 0);
            return (T) value;
        } else if (type == String.class) {
            String value = sharedPreferences.getString(key, "");
            return (T) value;
        } else if (type == Long.class) {
            Long value = sharedPreferences.getLong(key, 0);
            return (T) value;
        } else {
            String json = sharedPreferences.getString(key, "");
            if (TextUtils.isEmpty(json)) {
                return null;
            } else {
                T value = gson.fromJson(json, type);
                return value;
            }
        }
    }

    public static synchronized void init(Context context, boolean createDefaultPreference, String... names) {

        if (createDefaultPreference) {
            getSharedPreferencesManager(context, DEFAULT_SHARED_PREFERENCE);
        }
        if (names == null || names.length == 0) return;
        for (String name : names) {
            getSharedPreferencesManager(context, name);
        }
    }

    //根据name返回实例
    public static synchronized SharedPreferencesManager getInstance(String name) {
        SharedPreferencesManager sharedPreferencesManager = preferencesManagerHashMap.get(name);
        if (sharedPreferencesManager == null)
            throw new IllegalStateException("The share preference: " + name + " is not initialized before. You have to initialize it first by calling init(Context, boolean, String...) function");
        return sharedPreferencesManager;
    }

    /**
     * 返回默认的SharedPreferencesManager 实例: {@link #DEFAULT_SHARED_PREFERENCE}
     *
     * @return
     */
    public static synchronized SharedPreferencesManager getInstance() {
        SharedPreferencesManager sharedPreferencesManager = preferencesManagerHashMap.get(DEFAULT_SHARED_PREFERENCE);
        if (sharedPreferencesManager == null)
            throw new IllegalStateException("The default share preference is not initialized before. You have to initialize it first by calling init(Context, boolean, String...) function");
        return sharedPreferencesManager;
    }






}
