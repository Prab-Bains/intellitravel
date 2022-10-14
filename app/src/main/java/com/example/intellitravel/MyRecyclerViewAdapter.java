package com.example.intellitravel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    Context c;
    String[] countries, descriptions;
    int[] images;
    private ItemClickListener clickListener;

    public MyRecyclerViewAdapter(Context c, String[] countries, String[] descriptions, int[] images) {
        this.c = c;
        this.countries = countries;
        this.descriptions = descriptions;
        this.images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text1.setText(countries[position]);
        holder.text2.setText(descriptions[position]);
        holder.image.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text1, text2;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.countryName);
            text2 = itemView.findViewById(R.id.countryDescription);
            image = itemView.findViewById(R.id.flagImage);
            itemView.setOnClickListener(this);
//            image.setOnClickListener(this);
        }

        public void onClick(View itemView) {
            if (clickListener != null) clickListener.onClick(itemView, getAdapterPosition());

        }
    }
}
