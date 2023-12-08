package com.example.otterlibaraysystem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_table")

public class TransactionTable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "reservation")
    private int reservation;



    public TransactionTable(String username, String type, String title, int reservation) {
        this.username = username;
        this.type = type;
        this.title = title;
        this.reservation = reservation;

    }


    public TransactionTable(String transaction_type, String username) {
        this.username = transaction_type;
        this.type = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReservation() {
        return reservation;
    }

    public void setReservation(int reservation) {
        this.reservation = reservation;
    }

}
