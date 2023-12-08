package com.example.otterlibaraysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import com.example.otterlibaraysystem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private bookDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = bookDatabase.getInstance(MainActivity.this);
        db.populateInitialData();

        Button create = findViewById(R.id.account);
        create.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this , CreateAccount.class);
            startActivity(i);
        });

        Button placeHold = findViewById(R.id.hold);
        placeHold.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this , placeHold.class);
            startActivity(i);
        });
        Button manage = findViewById(R.id.manage);
        manage.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this , manageSystem.class);
            startActivity(i);
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}