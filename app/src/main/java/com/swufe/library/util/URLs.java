package com.swufe.library.util;

public class URLs {
    private static final String IP = "http://10.31.152.181:8080/";
    private static final String server = IP+"api/";

    //登录
    public static final String login = server+"login";
    public static final String register = server+"register";
    public static final String getAllBooks = server+"allBook";
    public static final String resetPwd = server+"resetPwd";
    public static final String userInfo = server+"userInfo";
    public static final String allLend = server+"allLend";
    public static final String search = server+"search";
    public static final String addLend = server+"addLend";
    public static final String updateLend = server+"updateLend";
    public static final String getCollection = server+"getCollection";
    public static final String deleteCollection = server+"deleteCollection";
    public static final String addCollection = server+"addCollection";
    public static final String picture = IP+"static/book/";
}
