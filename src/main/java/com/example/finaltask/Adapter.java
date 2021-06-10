package com.example.finaltask;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    Activity activity;
    List<Model> foodList;

    public Adapter(Context context, Activity activity, List<Model> foodList) {
        this.context = context;
        this.activity = activity;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {

        holder.image.setImageBitmap(foodList.get(position).getImage());
        holder.title.setText(foodList.get(position).getTitle());
        holder.description.setText(foodList.get(position).getDescription());
        holder.date.setText(foodList.get(position).getDate());
        holder.time.setText(foodList.get(position).getTime());
        holder.quantity.setText(foodList.get(position).getQuantity());
        holder.location.setText(foodList.get(position).getLocation());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = foodList.get(position).getTitle();
                String description = foodList.get(position).getDescription();
                String quantity = foodList.get(position).getQuantity();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,title);
                intent.putExtra(Intent.EXTRA_TEXT,description);

                Intent shareIntent = Intent.createChooser(intent,"Share using");
                activity.startActivity(shareIntent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,date,time,quantity,location;
        ImageView image;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            quantity = itemView.findViewById(R.id.quantity);
            location = itemView.findViewById(R.id.location);
            layout = itemView.findViewById(R.id.note_layout);
        }
    }
}
