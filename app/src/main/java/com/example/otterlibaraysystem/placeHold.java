package com.example.otterlibaraysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otterlibaraysystem.databinding.ActivityPlaceHoldBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class placeHold extends AppCompatActivity {

    private ActivityPlaceHoldBinding binding;
    private EditText genreEditText;
    private EditText bookEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button placeHoldButton;
    private bookDatabase db;
    private Spinner genreSpinner;
    private Button exit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceHoldBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_place_hold);
        setContentView(binding.getRoot());

        db = bookDatabase.getInstance(this);
        db.populateInitialData();


        genreSpinner = findViewById(R.id.genreSpinner);
        bookEditText = findViewById(R.id.book_edittext);
        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        placeHoldButton = findViewById(R.id.place_hold_button);
        exit = findViewById(R.id.exit_button);


        List<String> allGenres = db.book().getAllGenres();

        Spinner genreSpinner = findViewById(R.id.genreSpinner);
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allGenres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedGenre = allGenres.get(i);
                displayBooksByGenre(selectedGenre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        placeHoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeHold();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMainMenu();
            }
        });

    }
    private void displayBooksByGenre(String selectedGenre) {
        List<bookTable> booksByGenre = db.book().getBooksByGenre(selectedGenre);

        if (booksByGenre.isEmpty()) {
            Toast.makeText(this, "No books available for the selected genre", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder bookInfoBuilder = new StringBuilder();
        for (bookTable book : booksByGenre) {
            if (book.getBookTitle().isEmpty()){
                String bookss = "";
                bookInfoBuilder.append(bookss);
                Toast.makeText(this, "No books available for the selected genre", Toast.LENGTH_LONG).show();
            }
            else {
                String bookInfo = "Title: " + book.getBookTitle() + ", Author: " + book.getAuthor() + "\n";
                bookInfoBuilder.append(bookInfo);
            }

        }
        TextView booksTextView = findViewById(R.id.booksTextView);
        booksTextView.setText(bookInfoBuilder.toString());
    }

     private void placeHold(){

         String bookTitle = bookEditText.getText().toString().trim();
         String username = usernameEditText.getText().toString().trim();
         String password = passwordEditText.getText().toString().trim();
         String selectedGenre = genreSpinner.getSelectedItem().toString();
         bookTable selectedBook = null;

        List<bookTable> booksByGenre = db.book().getBooksByGenre(selectedGenre);



         if (booksByGenre.isEmpty()) {
             Toast.makeText(this, "No books available for the selected genre", Toast.LENGTH_SHORT).show();
             showMainMenu();
             return;
         }

         if (selectedGenre.isEmpty() || bookTitle.isEmpty() || username.isEmpty() || password.isEmpty()) {
             Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
             return;
         }

         int loginVaild =  db.user().ValidLogin(username ,password);
         if (loginVaild == 0) {
             Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
             return;
         }


         for (bookTable book : booksByGenre) {
             if (book.getBookTitle().equals(bookTitle)) {
                 selectedBook = book;
                 break;
             }
         }
         if (selectedBook == null) {
             Toast.makeText(this, "Book not found", Toast.LENGTH_SHORT).show();
             return;
         }


         int reservationNumber = generateReservationNumber();

         TransactionTable transactions = new TransactionTable(username, "Rental", bookTitle ,reservationNumber);
         db.transaction_Dao().addTransaction(transactions);



         String confirmationMessage = "Username: " + username +
                 "\nBook Title: " + selectedBook.getBookTitle() +
                 "\nReservation Number: " + reservationNumber;
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         bookTable finalSelectedBook = selectedBook;
         builder.setTitle("Hold Confirmation")
                 .setMessage(confirmationMessage)
                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         dialog.dismiss();
                         db.book().deleteBookByTitle(finalSelectedBook.getBookTitle());
                         showMainMenu();
                     }
                 });
         AlertDialog dialog = builder.create();
         dialog.show();


         bookEditText.setText("");
         usernameEditText.setText("");
         passwordEditText.setText("");



     }

    private void showMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private int generateReservationNumber() {
        return new Random().nextInt(1000) + 1;
    }


}
