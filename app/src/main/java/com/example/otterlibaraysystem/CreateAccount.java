package com.example.otterlibaraysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.otterlibaraysystem.databinding.ActivityCreateAccountBinding;

public class CreateAccount extends AppCompatActivity {
    private EditText username;
    private EditText password;

    private bookDatabase db;

    private ActivityCreateAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = bookDatabase.getInstance(getApplicationContext());
        db.populateInitialData();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button button = findViewById(R.id.create_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });



    }

    private void createAccount() {
        String username1 = username.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        logTransaction(username1);

        if (username1.isEmpty() || password1.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        bookDatabase database = bookDatabase.getInstance(getApplicationContext());

        if (username1.equalsIgnoreCase("!admin2")) {
            Toast.makeText(this, "Username is reserved and cannot be used", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUsernameExists = database.user().isUsernameExists(username1);
        if (isUsernameExists) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
            showMainMenu();
            return;
        }

        userPassTable user = new userPassTable(username1, password1);
        database.user().insertUser(user);

        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();



        TransactionTable transactions = new TransactionTable(username1, "New Account", null ,0);
        db.transaction_Dao().addTransaction(transactions);

        showMainMenu();
        finish();
    }

    private void logTransaction(String username) {
        Intent intent = new Intent(this, Transaction.class);
        intent.putExtra("transactionType", "New account");
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void showMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
