package com.example.foodify.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchRecipesAdapter extends RecyclerView.Adapter<SearchRecipesByIngredientsViewHolder>{
    Context context;
    List<Recipe> list;

    public SearchRecipesAdapter(Context context, List<Recipe> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchRecipesByIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchRecipesByIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.recepies_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecipesByIngredientsViewHolder holder, int position) {
        holder.recipesDisplayName.setText(list.get(position).title);
//      holder.recipesDisplayName.setSelected(true);
        // TODO: 2/12/2023 if recipe is saved make it say already saved else it says save
        holder.textView_servings.setText(list.get(position).servings+" Servings");
        holder.textView_time.setText(list.get(position).readyInMinutes+ " Minutes");
        Picasso.get().load(list.get(position).image).into(holder.recipesImgView);
    }

    @Override
    public int getItemCount() {
       return list.size();
    }
}
class SearchRecipesByIngredientsViewHolder extends RecyclerView.ViewHolder{
    CardView searchRecipesByIngredientsContainer;
    TextView recipesDisplayName,textView_servings,textView_time,textView_Favorite;
    ImageView recipesImgView;

    public SearchRecipesByIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        searchRecipesByIngredientsContainer=itemView.findViewById(R.id.searchRecipesByIngredientsContainer);
        recipesDisplayName=itemView.findViewById(R.id.recipesDisplayName);
        textView_servings=itemView.findViewById(R.id.textView_servings);
        recipesImgView=itemView.findViewById(R.id.recipesImgView);
        textView_time=itemView.findViewById(R.id.textView_time);
        textView_Favorite=itemView.findViewById(R.id.textView_Favorite);

    }
}
