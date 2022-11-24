package com.example.foodify;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class JsonResult extends AppCompatActivity {
    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_result);
        mTextViewResult = findViewById(R.id.text_view_result);
        String upc = "041631000564";
        OkHttpClient client = new OkHttpClient();
        String url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/products/upc/"+upc;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", "7b0220d6ebmshc069b9ec4a6e87bp1dff99jsnddb59891d88d")
                .addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse = response.body().string();
                    JsonResult.this.runOnUiThread(() -> mTextViewResult.setText(myResponse));
                }
            }
        });
    }
}