package com.example.booklibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklibrary.classes.Book;
import com.example.booklibrary.classes.CustomAdapter;
import com.example.booklibrary.classes.MyDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

RecyclerView recyclerView;
FloatingActionButton add_button;
ArrayList<Book>  books;
MyDatabaseHelper myDatabaseHelper;
CustomAdapter customAdapter;
ImageView empty;
TextView empty_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.floatingbutton);
        empty = findViewById(R.id.empty);
        empty_text = findViewById(R.id.empty_text);
        books = new ArrayList<>();

        //Floating add button
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);

            }
        });


        myDatabaseHelper = new MyDatabaseHelper(this);
        displayData();
        customAdapter = new CustomAdapter(this,books);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    //Check if there's data , and set it into book list
    void displayData()
    {
        Cursor cursor = myDatabaseHelper.readAllData();
        if(cursor.getCount() == 0)
        {
            empty.setVisibility(View.VISIBLE);
            empty_text.setVisibility(View.VISIBLE);
        }
        else
        {
            while(cursor.moveToNext())
            {
                String id = cursor.getString(0);
                String titles = cursor.getString(1);
                String author = cursor.getString(2);
                String pages = cursor.getString(3);
                Book book = new Book(Integer.parseInt(id),titles,author,Integer.parseInt(pages));
                books.add(book);
            }
            empty.setVisibility(View.GONE);
            empty_text.setVisibility(View.GONE);
            cursor.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_all)
        {
            confirmDeleteDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    // Delete All items Confirmation
    void confirmDeleteDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure that you want to delete all?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDatabaseHelper = new MyDatabaseHelper(MainActivity.this);
                myDatabaseHelper.deleteAllData();
                // recreate();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();}
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}