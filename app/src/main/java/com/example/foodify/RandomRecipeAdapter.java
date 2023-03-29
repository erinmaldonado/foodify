package com.example.foodify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder>{
    Context context;
    List<Recipe> list;

public RandomRecipeAdapter(Context context, List<Recipe> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.recepies_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.recipesDisplayName.setText(list.get(position).getTitle());
        holder.recipesDisplayName.setSelected(true);
        holder.textView_Likes.setText(list.get(position).getAggregateLikes()+" Likes");
        holder.textView_servings.setText(list.get(position).getServings()+" Servings");
        holder.textView_time.setText(list.get(position).getReadyInMinutes()+" Minutes");
        Picasso.get().load(list.get(position).getImage()).into(holder.recipesImage);
    }

    @Override
    public int getItemCount() {
      return list.size();
    }
}

class RandomRecipeViewHolder extends RecyclerView.ViewHolder {
    CardView randomRecipesCardView;
    TextView recipesDisplayName, textView_time, textView_servings,textView_Likes;
    ImageView recipesImage;

    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        randomRecipesCardView = itemView.findViewById(R.id.randomRecipesCardView);
        recipesDisplayName = itemView.findViewById(R.id.recipesDisplayName);
        textView_time = itemView.findViewById(R.id.textView_time);
        textView_servings = itemView.findViewById(R.id.textView_servings);
        textView_Likes = itemView.findViewById(R.id.textView_Likes);
        recipesImage = itemView.findViewById(R.id.recipesImgView);
    }
}
