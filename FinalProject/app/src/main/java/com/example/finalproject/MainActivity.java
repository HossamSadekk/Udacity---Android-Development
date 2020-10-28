package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.finalproject.classes.Car;
import com.example.finalproject.classes.CarRVAdapter;
import com.example.finalproject.classes.DatabaseAccess;
import com.example.finalproject.classes.OnRecyclerViewItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    Toolbar toolbar;
    CarRVAdapter carRVAdapter;
    SearchView searchView;
    DatabaseAccess db;

    public static final int ADD_CAR_REQ_CODE = 1;
    public static final int EDIT_CAR_REQ_CODE = 2;
    public static final int PERMISSION_REQ_CODE = 5;
    public static final String CAR_KEY = "car_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PERMISSIONS
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQ_CODE);
        }

searchView = findViewById(R.id.search);
        toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.main_recycler);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        // Database Access
        db = DatabaseAccess.getInstance(this);
        db.open();
        ArrayList<Car> cars = db.getAllCars();
        db.close();

        //adapter
        carRVAdapter = new CarRVAdapter(cars, new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int carId) {
                Intent intent = new Intent(getBaseContext() , ViewCarActivity.class);
                intent.putExtra(CAR_KEY,carId);
                startActivityForResult(intent,EDIT_CAR_REQ_CODE);
            }
        });
        recyclerView.setAdapter(carRVAdapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);



        // Floating Action Button Listener
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext() , ViewCarActivity.class);
                startActivityForResult(intent,ADD_CAR_REQ_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                db.open();
                ArrayList<Car> cars = db.getSearchCars(newText);
                db.close();
                carRVAdapter.setCar_list(cars);
                carRVAdapter.notifyDataSetChanged();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                db.open();
                ArrayList<Car> cars = db.getAllCars() ;
                db.close();
                carRVAdapter.setCar_list(cars);
                carRVAdapter.notifyDataSetChanged();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_CAR_REQ_CODE && resultCode==ViewCarActivity.ADD_CAR_RESULT_CODE)
        {
            db.open();
            ArrayList<Car> cars = db.getAllCars();
            db.close();
            carRVAdapter.setCar_list(cars);
            carRVAdapter.notifyDataSetChanged();
        }
        else if(requestCode==EDIT_CAR_REQ_CODE && resultCode==ViewCarActivity.EDIT_CAR_RESULT_CODE)
        {
            db.open();
            ArrayList<Car> cars = db.getAllCars();
            db.close();
            carRVAdapter.setCar_list(cars);
            carRVAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_REQ_CODE:
                if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED)
                {

                }
        }
    }
}