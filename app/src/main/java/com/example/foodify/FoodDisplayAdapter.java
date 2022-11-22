package com.example.foodify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodDisplayAdapter extends RecyclerView.Adapter<FoodDisplayAdapter.ViewHolder> {

    Context context;
    ArrayList<Inventory> inventoryArrayList;

    public FoodDisplayAdapter(Context context, ArrayList<Inventory> inventoryArrayList) {
        this.context = context;
        this.inventoryArrayList = inventoryArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.inventory_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Inventory inventory  = inventoryArrayList.get(position);
        holder.foodName.setText(inventory.foodNames);
        holder.foodImage.setImageResource(inventory.foodImages);

    }

    @Override
    public int getItemCount() {
        return inventoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView foodImage;
        private TextView foodName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
        }
    }
}
