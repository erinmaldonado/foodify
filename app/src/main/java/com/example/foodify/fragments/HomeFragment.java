package com.example.foodify.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.FoodItem;
import com.example.foodify.FoodItemAdapter;
import com.example.foodify.R;
import com.example.foodify.Scan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Button scanBtn;
    private Button receiptBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth auth;
    FirebaseUser user;

    ArrayList<FoodItem> list;
    FoodItemAdapter myAdapter;

    private RecyclerView recyclerView;
    private ValueEventListener valueEventListener;
    private static final int REQUEST_CODE_PICK_FILE = 1;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        scanBtn = view.findViewById(R.id.scanBtn);
        receiptBtn = view.findViewById(R.id.receiptBtn);

        scanBtn.setOnClickListener(v -> {
            Scan scan = new Scan();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, scan);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        receiptBtn.setOnClickListener(v -> {
          openFilePicker();
        });

        return view;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Set the type to accept any file
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select a file"), REQUEST_CODE_PICK_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();
            // Open ReceiptFragment using the selected file
            ReceiptFragment receiptFragment = ReceiptFragment.newInstance(selectedFileUri);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, receiptFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addPostEventListener();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (valueEventListener != null) {
            databaseReference.removeEventListener(valueEventListener);
        }
    }

    private void addPostEventListener() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String userId = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(userId+"/foodList");

        recyclerView = getView().findViewById(R.id.inventoryRecView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));

        list = new ArrayList<>();
        myAdapter = new FoodItemAdapter(getContext(), list);
        recyclerView.setAdapter(myAdapter);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        FoodItem foodItem = dataSnapshot.getValue(FoodItem.class);
                        list.add(foodItem);
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);

    }

}