package com.example.otterlibaraysystem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "bookInformation")
    public class bookTable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Book Title")
    private String bookTitle;

    @ColumnInfo(name = "Author")
    private String author;

    @ColumnInfo(name = "Genre")
    private String genre;




    public int getId() {
        return id;
    }
    public bookTable(String bookTitle , String author , String genre){
        this.bookTitle = bookTitle;
        this.author =author;
        this.genre = genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
