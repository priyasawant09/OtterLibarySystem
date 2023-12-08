package com.example.otterlibaraysystem;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert
    void insertUser(userPassTable user);

    @Query("SELECT * FROM LoginInformation WHERE username = :username AND password = :password")
    userPassTable getuserPass(String username , String password);

    @Query(("SELECT COUNT (*) FROM LoginInformation "))
    int count();

    @Update
    void update(userPassTable user);


    @Query("DELETE FROM logininformation WHERE username = :username")
    void deleteUser(String username);

    @Query("SELECT EXISTS(SELECT 1 FROM LoginInformation WHERE username = :username LIMIT 1)")
    boolean isUsernameExists(String username);


    @Query("SELECT COUNT(*) FROM LoginInformation WHERE username = :username AND password = :password")
    int ValidLogin(String username, String password);



}
