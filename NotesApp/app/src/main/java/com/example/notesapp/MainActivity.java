package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.classes.NoteAdapter;
import com.example.notesapp.classes.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
FloatingActionButton floating_add;
FirebaseDatabase firebaseDatabase;
DatabaseReference ref;
ArrayList<Notes> NotesList;
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.note_list_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Notes");
        floating_add = findViewById(R.id.floating_add_button);
        NotesList = new ArrayList<>();
        floating_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogNote();
            }
        });

        readData();

        //// click on item to view the details of note
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Notes notes = NotesList.get(i);
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                intent.putExtra("title",notes.getTitle());
                intent.putExtra("note",notes.getNote());
                startActivity(intent);
            }
        });
        //// click on note long click to update or delete the note
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Declaration the AlertDialog
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.update_delete_layout,null);
                builder1.setView(v);
                builder1.create().show();

                final TextView Title_update =  v.findViewById(R.id.title_up);
                final TextView Note_update = v.findViewById(R.id.note_up);
                Button Update =  v.findViewById(R.id.update_note);
                Button Delete = v.findViewById(R.id.delete_note);
                final Notes note = NotesList.get(i);

               // update button
                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Title = Title_update.getText().toString();
                        String Note = Note_update.getText().toString();
                        if(!Title.isEmpty() && !Note.isEmpty())
                        {
                            DatabaseReference mref = ref.child(note.getId());
                            mref.setValue(new Notes(note.getId(),Title,Note,note.getTime_note()));
                        }
                    }
                });

                //delete button
                Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference mref = ref.child(note.getId());
                        mref.removeValue();
                    }
                });
                return false;
            }
        });
    }

// reading data from firebase and sent data to listview
    public void readData() {

        Toast.makeText(this, "Onstart", Toast.LENGTH_SHORT).show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NotesList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Notes n = ds.getValue(Notes.class);
                    if(n!=null)
                    {
                    NotesList.add(0,n);
                    }
                }

                NoteAdapter noteAdapter = new NoteAdapter(MainActivity.this,NotesList);
                ListView listView = findViewById(R.id.note_list_view);
                listView.setAdapter(noteAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Showing Dialog of add note
    public void showDialogNote()
    {
        //Declaretion the AlertDialog
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_note,null);

        final EditText title_et,note_et;
        Button add;

        title_et = v.findViewById(R.id.title_et);
        note_et = v.findViewById(R.id.note_et);
        add = v.findViewById(R.id.add_note);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = title_et.getText().toString();
                String Note = note_et.getText().toString();
                if(!Title.isEmpty() && !Note.isEmpty())
                {
                    String id = ref.push().getKey();
                    ref.child(id).setValue(new Notes(id,Title,Note,getCurrentDate()));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Empty Info..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder1.setView(v);
        builder1.create().show();
    }




    //get current date
    public String getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = dateFormat.format(calendar.getTime());
        return date;
    }
}