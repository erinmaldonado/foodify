package com.example.foodify.Models;

import java.util.ArrayList;

public class RandomRecipeApiResponse {
   private ArrayList<Recipe> recipes;

    public ArrayList<Recipe> getRecipes() {
         return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
         this.recipes = recipes;
    }
}
