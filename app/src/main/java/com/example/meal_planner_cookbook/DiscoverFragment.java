package com.example.meal_planner_cookbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {
    RecipeRVAdapter adapter;
    SearchView searchBar;
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecipeAPI api;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
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
        retrofit = new Retrofit.Builder()
                .baseUrl(RecipeAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RecipeAPI.class);
        //TODO
        getRandomRecipes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchBar = view.findViewById(R.id.searchBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
    }

    private void search(){

        String query = searchBar.getQuery().toString();

        Call<GetSearchResultsResponse> call = api.getSearchResults(query);

        call.enqueue(new Callback<GetSearchResultsResponse>() {
            @Override
            public void onResponse(Call<GetSearchResultsResponse> call, Response<GetSearchResultsResponse> response) {
                GetSearchResultsResponse results = response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new RecipeRVAdapter(getContext(), results);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetSearchResultsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getRandomRecipes(){
        Call<GetRandomRecipesResponse> call = api.getRandomRecipes();

        call.enqueue(new Callback<GetRandomRecipesResponse>() {
            @Override
            public void onResponse(Call<GetRandomRecipesResponse> call, Response<GetRandomRecipesResponse> response) {

                GetRandomRecipesResponse recipes = response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new RecipeRVAdapter(getContext(), recipes);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetRandomRecipesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}

