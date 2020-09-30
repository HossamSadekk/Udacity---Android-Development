package com.example.booklibrary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booklibrary.classes.Book;
import com.example.booklibrary.classes.MyDatabaseHelper;

public class UpdateActivity extends AppCompatActivity {
EditText title,pages,author;
Button update_button,delete_button;
String Title,Author,ID;
MyDatabaseHelper myDatabaseHelper;
    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title = findViewById(R.id.title_update);
        pages = findViewById(R.id.pages_update);
        author = findViewById(R.id.Author_update);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        getIntentData();
        //set action bar
        ActionBar ab = getSupportActionBar();
        if(ab==null){ab.setTitle(Title);}

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Title = title.getText().toString() ;
                Author = author.getText().toString() ;
                int Pages = Integer.parseInt(pages.getText().toString());
                myDatabaseHelper = new MyDatabaseHelper(UpdateActivity.this);
                book = new Book(Integer.parseInt(ID),Title,Author,Pages);
                myDatabaseHelper.updateData(book);}
                catch (Exception e)
                {}

            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getIntentData()
    {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")){
            ID = getIntent().getStringExtra("id");
            Title = getIntent().getStringExtra("title"); //(((( to use it in confirmation dialog of delete function )))))
        }
        else
        {
            Toast.makeText(this, "there's NO Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+Title);
        builder.setMessage("Are you sure that you want to delete "+Title+"?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDatabaseHelper = new MyDatabaseHelper(UpdateActivity.this);
                book = new Book(Integer.parseInt(ID));
                myDatabaseHelper.deleteOneRow(book);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}