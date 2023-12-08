package com.example.otterlibaraysystem;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM transaction_table")
    List<TransactionTable> getAllTransactions();

    @Insert
    void addTransaction(TransactionTable transaction);

    @Query("SELECT * FROM transaction_table WHERE id = :transactionId")
    TransactionTable getTransactionById(int transactionId);


}

