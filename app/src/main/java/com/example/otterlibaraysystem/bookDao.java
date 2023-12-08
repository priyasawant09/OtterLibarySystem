package com.example.otterlibaraysystem;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface bookDao {
    @Insert
    void insertBook(bookTable book);

    @Query("SELECT * FROM bookInformation WHERE `Book Title` = :bookTitle AND Author = :author AND Genre =:genre")
    bookTable getbookTAble(String bookTitle , String author , String genre);

    @Query(("SELECT COUNT (*) FROM bookInformation "))
    int count();

    @Update
    void update(bookTable book);


    @Delete
    void delete(bookTable book);
    @Query("SELECT * FROM bookInformation")
    List<bookTable> getAll();

    @Query("SELECT * FROM bookInformation WHERE genre = :genre AND `Book Title`  = :title LIMIT 1")
    bookTable getBookByGenreAndTitle(String genre, String title);

    @Query("SELECT * FROM bookInformation WHERE genre = :genre")
    List<bookTable> getBooksByGenre(String genre);


    @Query("UPDATE bookInformation SET `Book Title` = '' WHERE `Book Title` = :bookTitle" )
    void deleteBookByTitle(String bookTitle);



    @Query("SELECT DISTINCT genre FROM bookInformation")
    List<String> getAllGenres();


}
