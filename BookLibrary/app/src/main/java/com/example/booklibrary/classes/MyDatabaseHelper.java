package com.example.booklibrary.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    public static final String DATABASE_NAME = "BookLibrary";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "my_library";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "book_title";
    public static final String COLUMN_AUTHOR = "book_author";
    public static final String COLUMN_PAGES = "book_pages";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+ TABLE_NAME +" ("+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE +" TEXT, "
                + COLUMN_AUTHOR +" TEXT, "
                + COLUMN_PAGES +" INTEGER); ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
       onCreate(sqLiteDatabase);
    }
    // ADD FUNCTION
    public void addBook(Book book)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,book.getTitle());
        contentValues.put(COLUMN_AUTHOR,book.getAuthor());
        contentValues.put(COLUMN_PAGES,book.getPages());
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully !", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData()
    {
        String query = "SELECT * FROM "+ TABLE_NAME;
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        if(sqLiteDatabase != null)
        {
             cursor = sqLiteDatabase.rawQuery(query,null);
        }
        return cursor;
    }
    // UPDATE FUNCTION
    public void updateData(Book book)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,book.getTitle());
        contentValues.put(COLUMN_PAGES,book.getPages());
        contentValues.put(COLUMN_AUTHOR,book.getAuthor());


        int res = sqLiteDatabase.update(TABLE_NAME , contentValues , "id=?",new String[]{String.valueOf(book.getId())});
        if(res == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
            {
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        sqLiteDatabase.close();
    }


   // DELETE FUNCTIONS
   // DELETE FUNCTIONS
   // DELETE FUNCTIONS
    public void deleteOneRow(Book book)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long res = sqLiteDatabase.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(book.getId())});
        if(res==-1)
        {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
