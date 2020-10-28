package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalproject.classes.Car;
import com.example.finalproject.classes.DatabaseAccess;
import com.google.android.material.textfield.TextInputEditText;

public class ViewCarActivity extends AppCompatActivity {
private Toolbar toolbar;
private TextInputEditText et_model , et_color , et_dpl , et_description;
private ImageView car_image;
private Uri ImageUri;
boolean res;//result of insert car in database is inserted or not.

private int carId=-1;
public static final int PICK_IMAGE_REQ_CODE=1;
public static final int ADD_CAR_RESULT_CODE=2;
public static final int EDIT_CAR_RESULT_CODE=3;
private DatabaseAccess db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);
        toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        car_image = findViewById(R.id.imageview);
        et_model = findViewById(R.id.details_model);
        et_color = findViewById(R.id.details_color);
        et_dpl = findViewById(R.id.details_dpl);
        et_description = findViewById(R.id.details_description);
        //database access
        db = DatabaseAccess.getInstance(this);

        Intent intent = getIntent();
        carId = intent.getIntExtra(MainActivity.CAR_KEY,-1);

        if(carId == -1)
        {
            // add item
        }
        else
        {
            // show item
            disableFields();
            db.open();
            Car c = db.getCar(carId);
            db.close();
            if(c != null)
            {
                fillCarToFields(c);
            }

        }

        car_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_IMAGE_REQ_CODE);
            }
        });

    }

    private void fillCarToFields(Car car)
    {
        if(car.getImage() !=null && !car.getImage().equals("")){
        car_image.setImageURI(Uri.parse(car.getImage()));}
        et_model.setText(car.getModel());
        et_color.setText(car.getColor());
        et_dpl.setText(car.getDpl()+"");
        et_description.setText(car.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);

        MenuItem save = menu.findItem(R.id.details_menu_check);
        MenuItem edit = menu.findItem(R.id.details_menu_edit);
        MenuItem delete = menu.findItem(R.id.details_menu_delete);

        if(carId == -1)
        {
            // add item
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);

        }
        else
        {
            // edit item
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }

    private void disableFields()
    {
        car_image.setEnabled(false);
        et_model.setEnabled(false);
        et_color.setEnabled(false);
        et_dpl.setEnabled(false);
        et_description.setEnabled(false);
    }

    private void enableFields()
    {
        car_image.setEnabled(true);
        et_model.setEnabled(true);
        et_color.setEnabled(true);
        et_dpl.setEnabled(true);
        et_description.setEnabled(true);
    }

    private void clearFields()
    {
        car_image.setImageURI(null);
        et_model.setText("true");
        et_color.setText("true");
        et_dpl.setText("true");
        et_description.setText("true");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String model , color , description ;
        String image="";
        double dpl;
        db.open();
        switch (item.getItemId())
        {
            case R.id.details_menu_check:
                model = et_model.getText().toString();
                color = et_color.getText().toString();
                description = et_description.getText().toString();
                dpl =Double.parseDouble( et_dpl.getText().toString());
                if(ImageUri!=null)
                image = ImageUri.toString();




                Car car = new Car(carId,model,color,dpl,image,description);

                if(carId==-1)
                {
                  res = db.insertCar(car);
                    Toast.makeText(this, "Car Added Successfully", Toast.LENGTH_SHORT).show();
                    setResult(ADD_CAR_RESULT_CODE);
                    finish();
                }
                else
                {
                  res = db.updatetCar(car);
                    Toast.makeText(this, "Car Modified Successfully", Toast.LENGTH_SHORT).show();
                    setResult(EDIT_CAR_RESULT_CODE);
                    finish();
                }
                return true;
            case R.id.details_menu_edit:
                enableFields();
                MenuItem save = toolbar.getMenu().findItem(R.id.details_menu_check);
                MenuItem edit = toolbar.getMenu().findItem(R.id.details_menu_edit);
                MenuItem delete = toolbar.getMenu().findItem(R.id.details_menu_delete);

                delete.setVisible(false);
                edit.setVisible(false);
                save.setVisible(true);
                return true;
            case R.id.details_menu_delete:

                 car = new Car(carId);
                 res = db.deleteCar(car);
                 if(res)
                 {
                     Toast.makeText(this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
                     setResult(EDIT_CAR_RESULT_CODE);
                     finish();
                 }
                return true;
        }
        db.close();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQ_CODE && resultCode==RESULT_OK)
        {
            if(data != null)
            {
                ImageUri = data.getData();
                car_image.setImageURI(ImageUri);
            }
        }
    }
}