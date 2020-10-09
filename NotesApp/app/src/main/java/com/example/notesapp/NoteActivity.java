package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {
TextView title,note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        title = findViewById(R.id.Title_note);
        note = findViewById(R.id.Note_details);

        Intent i = getIntent();
        title.setText(i.getStringExtra("title"));
        note.setText(i.getStringExtra("note"));
        
    }
}