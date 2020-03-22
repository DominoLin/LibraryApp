package com.swufe.library.bean;

public class BorrowItem {
    String deadline, bookName, borrow_date, back_date;

    public BorrowItem(String deadline, String bookName, String borrow_date, String back_date) {
        this.deadline = deadline;
        this.bookName = bookName;
        this.borrow_date = borrow_date;
        this.back_date = back_date;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(String borrow_date) {
        this.borrow_date = borrow_date;
    }

    public String getBack_date() {
        return back_date;
    }

    public void setBack_date(String back_date) {
        this.back_date = back_date;
    }
}
