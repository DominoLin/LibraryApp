package com.swufe.library.bean;

import java.util.Date;

public class Lend {
    private int account;
    private int book_id;
    private String book_name;



    private String lend_date;
    private String back_date;

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getLend_date() {
        return lend_date;
    }

    public void setLend_date(String lend_date) {
        this.lend_date = lend_date;
    }

    public String getBack_date() {
        return back_date;
    }

    public void setBack_date(String back_date) {
        this.back_date = back_date;
    }
}
