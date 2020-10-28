package com.example.finalproject.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finalproject.database.MyCarDatabase;

import java.security.PublicKey;
import java.util.ArrayList;

public class DatabaseAccess {

    private SQLiteDatabase db;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private static DatabaseAccess instance;
    private DatabaseAccess(Context context)
    {
        this.sqLiteOpenHelper = new MyCarDatabase(context);
    }

    public static DatabaseAccess getInstance(Context context)
    {
        if(instance==null)
        {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open()
    {
        this.db = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close()
    {
        if(db != null)//if database is open
        {
            db.close();
        }
    }


    public boolean insertCar(Car car)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyCarDatabase.CAR_CLN_MODEL,car.getModel());
        contentValues.put(MyCarDatabase.CAR_CLN_COLOR,car.getColor());
        contentValues.put(MyCarDatabase.CAR_CLN_DPL,car.getDpl());
        contentValues.put(MyCarDatabase.CAR_CLN_IMAGE,car.getImage());
        contentValues.put(MyCarDatabase.CAR_CLN_DESCRIPTION,car.getDescription());

        long Res = db.insert(MyCarDatabase.CAR_TB_NAME,null,contentValues);
        return Res != -1;
    }

    public boolean updatetCar(Car car)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyCarDatabase.CAR_CLN_MODEL,car.getModel());
        contentValues.put(MyCarDatabase.CAR_CLN_COLOR,car.getColor());
        contentValues.put(MyCarDatabase.CAR_CLN_DPL,car.getDpl());
        contentValues.put(MyCarDatabase.CAR_CLN_IMAGE,car.getImage());
        contentValues.put(MyCarDatabase.CAR_CLN_DESCRIPTION,car.getDescription());

        int Res = db.update(MyCarDatabase.CAR_TB_NAME,contentValues,"id=?",new String []{String.valueOf(car.getId())});
        return Res >0;
    }
    // return the number of rows in database
    public long getCarsCount()
    {
        return DatabaseUtils.queryNumEntries(db,MyCarDatabase.CAR_TB_NAME);
    }

    public boolean deleteCar(Car car)
    {
        int Res = db.delete(MyCarDatabase.CAR_TB_NAME,"id=?",new String []{String.valueOf(car.getId())});
        return Res > 0;
    }

    public ArrayList<Car> getAllCars()
    {
        ArrayList<Car> cars = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+MyCarDatabase.CAR_TB_NAME,null);
        if(cursor != null && cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_ID));
                String  model = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_MODEL));
                String  color = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_COLOR));
                double  dpl = cursor.getDouble(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_DPL));
                String  image = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_IMAGE));
                String  description = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_DESCRIPTION));
                Car car = new Car(id,model,color,dpl,image,description);
                cars.add(car);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }
    public Car getCar(int carId)
    {

        Cursor cursor = db.rawQuery("SELECT * FROM "+MyCarDatabase.CAR_TB_NAME+" WHERE "+MyCarDatabase.CAR_CLN_ID+" =?",new String []{String.valueOf(carId)});
        if(cursor != null && cursor.moveToFirst())
        {
                int id = cursor.getInt(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_ID));
                String  model = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_MODEL));
                String  color = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_COLOR));
                double  dpl = cursor.getDouble(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_DPL));
                String  image = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_IMAGE));
                String  description = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_DESCRIPTION));
                Car car = new Car(id,model,color,dpl,image,description);
                cursor.close();
                return car;
        }
        return null;
    }

    public ArrayList<Car> getSearchCars(String model)
    {
        ArrayList<Car> cars = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + MyCarDatabase.CAR_CLN_MODEL + " where " + model + " like ?", new String[] { "%" + model + "%" });
        if(cursor != null && cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_ID));
                String  Model = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_MODEL));
                String  color = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_COLOR));
                double  dpl = cursor.getDouble(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_DPL));
                String  image = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_IMAGE));
                String  description = cursor.getString(cursor.getColumnIndex(MyCarDatabase.CAR_CLN_DESCRIPTION));
                Car car = new Car(id,Model,color,dpl,image,description);
                cars.add(car);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }
}
