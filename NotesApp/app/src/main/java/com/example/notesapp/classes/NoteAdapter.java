package com.example.notesapp.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notesapp.R;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {
    Context context;
    ArrayList<Notes> noteList;

    public NoteAdapter(Context context, ArrayList<Notes> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int i) {
        return noteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_layout,viewGroup,false);
        Notes notes = (Notes) getItem(i);
       TextView Title =  v.findViewById(R.id.title_txt);
       TextView Time =  v.findViewById(R.id.time_txt);
       Title.setText(notes.getTitle());
       Time.setText(notes.getTime_note().toString());
        return v;
    }
}
