package com.example.otterlibaraysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.otterlibaraysystem.databinding.ActivityMainBinding;
import com.example.otterlibaraysystem.databinding.ActivityManageSystemBinding;

public class manageSystem extends AppCompatActivity {
    private ActivityManageSystemBinding binding;
    private bookDatabase db;
    private EditText username;
    private EditText password;
    private Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageSystemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = bookDatabase.getInstance(getApplicationContext());
        db.populateInitialData();

        username = findViewById(R.id.username_edittext);
        password = findViewById(R.id.password_edittext);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manage_System();
            }
        });


    }

    private void manage_System(){
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();


        if (isValidLogin(user, pass)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Login Successful")
                    .setMessage("You have successfully logged in.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Intent intent = new Intent(manageSystem.this, ManageSystemoptions.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Login Failed")
                    .setMessage("Invalid username or password.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        username.setText("");
        password.setText("");
    }

    private boolean isValidLogin(String username, String password) {
        return username.equals("!admin2") && password.equals("!admin2");
    }
}


