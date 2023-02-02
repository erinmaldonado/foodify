package com.example.foodify.Listener;

import com.example.foodify.models.SearchRecipesByIngredientsResponse;

public interface SearchRecipesByIngredientsResponseListener {
    void didFetch(SearchRecipesByIngredientsResponse response,String message);
    void didError(String message);
}
