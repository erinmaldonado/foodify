package com.example.foodify;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeScreen extends AppCompatActivity {
    Button scanBtn;
    Button signOutBtn;
    Button add;
    Button subtract;

    Button addToInventory;

    JsonResponse jsonResponse;
    String upc;
    TextView upcText;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static ArrayList<Type> mArrayList = new ArrayList<>();
    Gson gson = new Gson();
    int minteger = 0;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        scanBtn = findViewById(R.id.scan);
        scanBtn.setOnClickListener(view-> {
            scan();
        });

        signOutBtn = findViewById(R.id.signOut);
        signOutBtn.setOnClickListener(view ->{
            signOut();
        });

        add = findViewById(R.id.add);
        add.setOnClickListener(view ->{
            increaseInteger();
        });

        subtract = findViewById(R.id.subtract);
        subtract.setOnClickListener(view ->{
            decreaseInteger();
        });

        addToInventory = findViewById(R.id.add_to_inventory);
        addToInventory.setOnClickListener(view->{
            saveUPCToDatabase(upc, minteger);
        });

    }

    private void getResults(){

    }

    void signOut(){
        AuthUI.getInstance()
                .signOut(HomeScreen.this)
                .addOnCompleteListener(task -> {
                    // do something here
                    Intent intent = new Intent(HomeScreen.this, MainActivity.class);
                    startActivity(intent);
                });
    }

    void scan(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a barcode");
        options.setCameraId(0);
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(BarcodeScannerActivity.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
            upc = result.getContents();
            sendUserToBarCodeInfo(upc);
        }
    });

    private void saveUPCToDatabase(String upc, int total){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> foodUPC = new HashMap<>();
        foodUPC.put("UPC", upc);
        foodUPC.put("total", total);
        if(total > 0){
            foodUPC.put("available", true);
        } else {
            foodUPC.put("available", false);
        }
        foodUPC.put("title", jsonResponse.getTitle());
        foodUPC.put("uid", mAuth.getCurrentUser().getUid());

        db.collection("foodItem").document(upc+"_"+mAuth.getCurrentUser().getUid()).set(foodUPC, SetOptions.merge());
        DocumentReference foodRef = db.collection("foodItem").document(upc+"_"+mAuth.getCurrentUser().getUid());
        foodRef.update("total", minteger)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));

    }

    private void sendUserToBarCodeInfo(String upc){
        TextView textViewResult = findViewById(R.id.text_view_result);
        String Key = BuildConfig.API_KEY;

        OkHttpClient client = new OkHttpClient();
        String url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/products/upc/"+upc;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", Key)
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

                    jsonResponse =gson.fromJson(myResponse, JsonResponse.class);

                    HomeScreen.this.runOnUiThread(() ->{
                        textViewResult.setText(jsonResponse.getTitle() + " id: " + jsonResponse.getId());
                    });
                }
            }

        });
    }

    public void increaseInteger() {
        minteger = minteger + 1;
        display(minteger);
    }

    public void decreaseInteger() {
        minteger = minteger - 1;
        display(minteger);
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.total);
        displayInteger.setText("" + number);
    }
}