package com.librarytask;

public class Transaction {
    private int id;
    private int bookId;
    private int patronId;
    private String borrowedDate;
    private String returnedDate;

    public Transaction(int id, int bookId, int patronId, String borrowedDate, String returnedDate) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }
}
