package com.example.foodify.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodify.FoodDisplayAdapter;
import com.example.foodify.Inventory;
import com.example.foodify.R;

import java.sql.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_home, container, false);
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