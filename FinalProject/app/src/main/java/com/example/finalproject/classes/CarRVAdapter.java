package com.example.finalproject.classes;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.ArrayList;

public class CarRVAdapter extends RecyclerView.Adapter<CarRVAdapter.ViewHolder> {
    private ArrayList<Car> car_list;
    private OnRecyclerViewItemClickListener listener;

    public CarRVAdapter(ArrayList<Car> car_list, OnRecyclerViewItemClickListener listener) {
        this.car_list = car_list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_car_layout,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarRVAdapter.ViewHolder holder, int position) {
        Car c = car_list.get(position);
        if(c.getImage()!=null && !c.getImage().isEmpty()){
        holder.car_image.setImageURI(Uri.parse(c.getImage()));}
        else
        {
            holder.car_image.setImageResource(R.drawable.gt);
        }
        holder.car_model.setText(c.getModel());
        holder.car_color.setText(c.getColor());
        holder.car_dpl.setText(String.valueOf(c.getDpl()));
        /////// just to send it to itemView Listener
        holder.car_image.setTag(c.getId());


    }


    public void setCar_list(ArrayList<Car> car_list) {
        this.car_list = car_list;
    }

    @Override
    public int getItemCount() {
        return car_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView car_image;
        TextView car_model , car_color , car_dpl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_image = itemView.findViewById(R.id.custom_car);
            car_model = itemView.findViewById(R.id.custom_car_model_txt);
            car_color = itemView.findViewById(R.id.custom_car_color_txt);
            car_dpl = itemView.findViewById(R.id.custom_car_dpl_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) car_image.getTag();
                    listener.onItemClick(id);
                }
            });
        }
    }
}
