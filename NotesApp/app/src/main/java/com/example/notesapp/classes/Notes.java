package com.example.notesapp.classes;

import java.util.Date;
import java.util.Map;

public class Notes {
    private  String id;
    private String title;
    private  String note;
    private String time_note;

    public Notes() {
    }

    public Notes(String id, String title, String note, String time_note) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.time_note = time_note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime_note() {
        return time_note;
    }

    public void setTime_note(String time_note) {
        this.time_note = time_note;
    }
}
