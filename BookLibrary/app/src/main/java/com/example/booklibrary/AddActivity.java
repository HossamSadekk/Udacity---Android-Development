package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.booklibrary.classes.Book;
import com.example.booklibrary.classes.MyDatabaseHelper;

public class AddActivity extends AppCompatActivity {
EditText title,pages,author;
Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title = findViewById(R.id.title_input);
        pages = findViewById(R.id.pages_input);
        author = findViewById(R.id.Author_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                String Title = title.getText().toString();
                String Author = author.getText().toString();
                int Pages = Integer.parseInt(pages.getText().toString());
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(AddActivity.this);
                Book book = new Book(Title,Author,Pages);
                myDatabaseHelper.addBook(book);
                }
                catch (Exception e)
                {}
            }
        });

    }
}