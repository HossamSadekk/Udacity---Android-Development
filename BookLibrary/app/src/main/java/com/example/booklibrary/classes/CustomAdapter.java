package com.example.booklibrary.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booklibrary.R;
import com.example.booklibrary.UpdateActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Book> Booklist;
    Animation translate_anim;

    public CustomAdapter(Context context, ArrayList<Book> booklist) {
        this.context = context;
        Booklist = booklist;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.my_layout_recycler, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {
        holder.Book_id.setText(String.valueOf(Booklist.get(position).getId()));
        holder.Book_title.setText(String.valueOf(Booklist.get(position).getTitle()));
        holder.Book_author.setText(String.valueOf(Booklist.get(position).getAuthor()));
        holder.Book_pages.setText(String.valueOf(Booklist.get(position).getPages()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, UpdateActivity.class);
               intent.putExtra("id",String.valueOf(Booklist.get(position).getId()));
               intent.putExtra("title",String.valueOf(Booklist.get(position).getTitle()));
               intent.putExtra("pages",String.valueOf(Booklist.get(position).getPages()));
               intent.putExtra("author",String.valueOf(Booklist.get(position).getAuthor()));
               ((Activity)context).startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Booklist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Book_title, Book_id, Book_pages, Book_author;
        LinearLayout linearLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Book_id = itemView.findViewById(R.id.Book_id_text);
            Book_title = itemView.findViewById(R.id.Book_title);
            Book_pages = itemView.findViewById(R.id.Book_pages);
            Book_author = itemView.findViewById(R.id.Book_Author);
            linearLayout = itemView.findViewById(R.id.layout_of_recycler);

            //Animation Recycler View
            translate_anim = AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            linearLayout.setAnimation(translate_anim);
        }


    }
}