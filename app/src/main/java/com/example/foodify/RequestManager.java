package com.example.foodify;

import android.content.Context;

import com.example.foodify.Listener.SearchRecipesByIngredientsResponseListener;
import com.example.foodify.models.SearchRecipesByIngredientsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }
    public void searchRecipesByIngredients(SearchRecipesByIngredientsResponseListener Listener){
        CallRecipesByIngredients callRecipesByIngredients = retrofit.create(CallRecipesByIngredients.class);
        Call<SearchRecipesByIngredientsResponse> call = callRecipesByIngredients.callRecipesByIngredients(context.getString(R.string.api_key),"1");
        call.enqueue(new Callback<SearchRecipesByIngredientsResponse>() {
            @Override
            public void onResponse(Call<SearchRecipesByIngredientsResponse> call, Response<SearchRecipesByIngredientsResponse> response) {
                
            }

            @Override
            public void onFailure(Call<SearchRecipesByIngredientsResponse> call, Throwable t) {

            }
        });
    }

    private interface CallRecipesByIngredients{
        @GET("recipes/findByIngredients")
        Call<SearchRecipesByIngredientsResponse> callRecipesByIngredients(
                @Query("apiKey") String apiKey,
                @Query("ranking") String number
        );
    }
}
