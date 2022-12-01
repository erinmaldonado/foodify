package com.example.foodify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Inventory> inventoryArrayList;
    private String[] foodName;
    private int[] imageResource;
    private RecyclerView recyclerview;

    private Button scanBtn;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();

        recyclerview = view.findViewById(R.id.inventoryRecView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerview.setHasFixedSize(true);
        FoodDisplayAdapter adapter = new FoodDisplayAdapter(getContext(),inventoryArrayList);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void dataInitialize() {

        inventoryArrayList = new ArrayList<>();

        foodName = new String[]{
                getString(R.string.food_1),
                getString(R.string.food_2),
                getString(R.string.food_3),
                getString(R.string.food_4),
                getString(R.string.food_5),
                getString(R.string.food_6),
                getString(R.string.food_7)
        };

        imageResource = new int[]{
                R.drawable.apple,
                R.drawable.tomato,
                R.drawable.carrot,
                R.drawable.bacon,
                R.drawable.white_bread,
                R.drawable.lettuce,
                R.drawable.mayo
        };

        for (int i= 0; i<foodName.length;i++){
            Inventory inventory = new Inventory(foodName[i],imageResource[i]);
            inventoryArrayList.add(inventory);
        }
    }
}
