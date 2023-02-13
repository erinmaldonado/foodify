package com.example.foodify;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Listener.SearchRecipesByIngredientsResponseListener;
import com.example.foodify.models.SearchRecipesAdapter;
import com.example.foodify.models.SearchRecipesByIngredientsResponse;

public class RecipesFragment extends Fragment {
    ProgressDialog dialog;
    RequestManager manager;
    SearchRecipesAdapter adapter;
    RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading...");

        manager = new RequestManager(getContext());
        manager.searchRecipesByIngredients(searchRecipesByIngredientsResponseListener);
        dialog.show();
    }
    private final SearchRecipesByIngredientsResponseListener searchRecipesByIngredientsResponseListener = new SearchRecipesByIngredientsResponseListener() {
        @Override
        public void didFetch(SearchRecipesByIngredientsResponse response, String message) {
            recyclerView.findViewById(R.id.searchRecipesByIngredientRecycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
            adapter = new SearchRecipesAdapter(getContext(),response.recipes);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }
}
