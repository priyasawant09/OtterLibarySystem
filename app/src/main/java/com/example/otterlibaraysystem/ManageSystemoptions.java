package com.example.otterlibaraysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.otterlibaraysystem.databinding.ActivityManageSystemBinding;
import com.example.otterlibaraysystem.databinding.ActivityManageSystemoptionsBinding;

public class ManageSystemoptions extends AppCompatActivity {
    private ActivityManageSystemoptionsBinding binding;
    private bookDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageSystemoptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = bookDatabase.getInstance(getApplicationContext());
        db.populateInitialData();

        Button transactionLogButton = findViewById(R.id.btnTransactionLog);
        Button addBookButton = findViewById(R.id.btnAddBook);

        transactionLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageSystemoptions.this, Transaction.class);
                startActivity(intent);
            }
        });

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAddBookDialog();

            }
        });

    }


    private void showAddBookDialog() {
        AlertDialog.Builder confirmationBuilder = new AlertDialog.Builder(this);
        confirmationBuilder.setTitle("Confirmation");
        confirmationBuilder.setMessage("Librarian, do you want to add a book?");

        confirmationBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageSystemoptions.this);
                builder.setTitle("Add New Book");
                builder.setMessage("Enter the book's title, author, and genre");

                final EditText title = new EditText(ManageSystemoptions.this);
                final EditText author = new EditText(ManageSystemoptions.this);
                final EditText genre = new EditText(ManageSystemoptions.this);

                title.setHint("Title");
                author.setHint("Author");
                genre.setHint("Genre");

                LinearLayout layout = new LinearLayout(ManageSystemoptions.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(title);
                layout.addView(author);
                layout.addView(genre);

                builder.setView(layout);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String bookTitle = title.getText().toString().trim();
                        String bookAuthor = author.getText().toString().trim();
                        String bookGenre = genre.getText().toString().trim();


                        if (!bookTitle.isEmpty() && !bookAuthor.isEmpty() && !bookGenre.isEmpty()) {
                            bookTable newBook = new bookTable(bookTitle, bookAuthor, bookGenre);
                            db.book().insertBook(newBook);

                            AlertDialog.Builder successBuilder = new AlertDialog.Builder(ManageSystemoptions.this);
                            successBuilder.setTitle("Book Added");
                            successBuilder.setMessage("The book has been added successfully!");
                            successBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    showMainMenu();
                                }
                            });
                            successBuilder.show();
                        } else {
                            AlertDialog.Builder errorBuilder = new AlertDialog.Builder(ManageSystemoptions.this);
                            errorBuilder.setTitle("Error");
                            errorBuilder.setMessage("Please enter all book information");
                            errorBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            errorBuilder.show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

        confirmationBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        confirmationBuilder.show();
    }

    private void showMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

