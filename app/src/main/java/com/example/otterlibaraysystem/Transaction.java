package com.example.otterlibaraysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.otterlibaraysystem.databinding.ActivityManageSystemoptionsBinding;
import com.example.otterlibaraysystem.databinding.ActivityTransactionBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Transaction extends AppCompatActivity {


    private bookDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        db = bookDatabase.getInstance(getApplicationContext());
        db.populateInitialData();



        TextView main = findViewById(R.id.maininventory);

        View mainButton = findViewById(R.id.main_button);
        mainButton.bringToFront();

        List<TransactionTable> transactions = db.transaction_Dao().getAllTransactions();

        for (TransactionTable transactionTable : transactions){
            if (!transactions.isEmpty()){
                if (Objects.equals(transactionTable.getType(), "New Account")){
                    main.append("\n");
                    main.append("\n   1. Transaction Type: " + transactionTable.getType());
                    main.append("\n   2. Customer's username: " + transactionTable.getUsername());
                }
                else if (Objects.equals(transactionTable.getType(), "Rental")) {
                    main.append("\n\nTransaction Number: " + transactionTable.getId());
                    main.append("\n     Reservation Number: " + transactionTable.getReservation());
                    main.append("\n     Username: " + transactionTable.getUsername());
                    main.append("\n     Transaction Type: " + transactionTable.getType());
                    main.append("\n     Title of Book reserved: " + transactionTable.getTitle());
                }
            }

        }


        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMainMenu();
            }
        });
    }

    private void showMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}
