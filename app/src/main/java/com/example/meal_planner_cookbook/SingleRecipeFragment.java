package com.example.meal_planner_cookbook;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleRecipeFragment extends Fragment {
    TextView title, instructions, ingredient;
    ImageView image;
    ListView diet, ingredients;
    RecipeAPI api;
    private int id;
    List<String> ingredientList;
    Button saveRecipeButton, addToList, share;
    FirebaseDatabase rootNode;
    DatabaseReference cookBookReference;
    DatabaseReference groceryListReference;
    Recipe recipe;
    Button commentsButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SingleRecipeFragment() {
        // Required empty public constructor
    }

    public SingleRecipeFragment(int id) {
        this();
        this.id = id;
    }

    public SingleRecipeFragment(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleRecipeFragment newInstance(String param1, String param2) {
        SingleRecipeFragment fragment = new SingleRecipeFragment();
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipeAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RecipeAPI.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.title);
        image = view.findViewById(R.id.image);
        instructions = view.findViewById(R.id.instructions);
        diet = view.findViewById(R.id.diet);
        ingredients = view.findViewById(R.id.ingredients);
        ingredient = view.findViewById(R.id.ingredient);
        ingredientList = new ArrayList<String>();
        saveRecipeButton = view.findViewById(R.id.saveRecipeButton);
        addToList = view.findViewById(R.id.addToList);
        share = view.findViewById(R.id.share);
        commentsButton = view.findViewById(R.id.commentsButton);
        rootNode = FirebaseDatabase.getInstance();
        cookBookReference = rootNode.getReference("cookbook");
        groceryListReference = rootNode.getReference("groceryList");

        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookBookReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (saveRecipeButton.getText().equals("Save")) {
                            addCurrentRecipeToDatabase(snapshot, cookBookReference, Home.currentUser.getId());
                        } else {
                            removeCurrentRecipeFromDatabase(snapshot, cookBookReference, Home.currentUser.getId());
                        }
                        cookBookReference.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groceryListReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (addToList.getText().equals("Add to list")) {
                            addCurrentRecipeToDatabase(snapshot, groceryListReference, Home.currentUser.getId());
                        } else {
                            removeCurrentRecipeFromDatabase(snapshot, groceryListReference, Home.currentUser.getId());
                        }
                        groceryListReference.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        if (recipe == null) {
            Call<Recipe> recipeCall = api.getRecipeById(this.id);

            recipeCall.enqueue(new Callback<Recipe>() {
                @Override
                public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                    recipe = response.body();
                    populateView();
                }

                @Override
                public void onFailure(Call<Recipe> call, Throwable t) {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            populateView();
        }
    }

    private void addCurrentRecipeToDatabase(DataSnapshot snapshot, DatabaseReference databaseToAddTo, String username) {
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            if (dataSnapshot.getKey().equals(username)) {
                Map<String, Recipe> listToUpdate = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Recipe>>() {});
                listToUpdate.put(String.valueOf(recipe.getId()), recipe);
                databaseToAddTo.child(username).setValue(listToUpdate);
                return;
            }
        }
        Map<String, Recipe> newList = new HashMap<String, Recipe>();
        newList.put(String.valueOf(recipe.getId()), recipe);
        databaseToAddTo.child(username).setValue(newList);
    }

    private void removeCurrentRecipeFromDatabase(DataSnapshot snapshot, DatabaseReference databaseToAddTo, String username) {
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            if (dataSnapshot.getKey().equals(username)) {
                Map<String, Recipe> listToUpdate = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Recipe>>() {});
                listToUpdate.remove(String.valueOf(recipe.getId()));
                databaseToAddTo.child(username).setValue(listToUpdate);
                return;
            }
        }
    }

    private void populateView() {
        for(Ingredient ingredient1 : recipe.getExtendedIngredients()){
            ingredientList.add(ingredient1.getOriginalString());
        }

        title.setText(recipe.getTitle());
        if (recipe.getImage() != null) {
            Glide.with(getView()).asBitmap().load(new GlideUrl(recipe.getImage(), new LazyHeaders.Builder().addHeader("User-Agent", "your_user_agent").build())).into(image);
        }
        instructions.setText(Html.fromHtml(recipe.getInstructions()));
        instructions.setNestedScrollingEnabled(true);
        List<String> diets = getDiet(recipe);
        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                R.layout.ingredient_list_item, R.id.ingredient, ingredientList);
        ingredients.setAdapter(adapter);
        setListViewHeightBasedOnChildren(ingredients);
        ArrayAdapter adapterDiet = new ArrayAdapter(getContext(),
                R.layout.ingredient_list_item, R.id.ingredient, diets);
        diet.setAdapter(adapterDiet);
        diet.setNestedScrollingEnabled(true);
        setListViewHeightBasedOnChildren(diet);

        cookBookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(Home.currentUser.getId())) {
                        Map<String, Recipe> listToUpdate = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Recipe>>() {});
                        if (listToUpdate.containsKey(String.valueOf(recipe.getId()))) {
                            saveRecipeButton.setText("Unsave");
                            saveRecipeButton.setBackgroundColor(Color.GRAY);
                            return;
                        }
                    }
                }
                saveRecipeButton.setText("Save");
                saveRecipeButton.setBackgroundColor(Color.parseColor("#3a9ffd"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        groceryListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(Home.currentUser.getId())) {
                        Map<String, Recipe> listToUpdate = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Recipe>>() {});
                        if (listToUpdate.containsKey(String.valueOf(recipe.getId()))) {
                            addToList.setText("Remove from list");
                            addToList.setBackgroundColor(Color.GRAY);
                            return;
                        }
                    }
                }
                addToList.setText("Add to list");
                addToList.setBackgroundColor(Color.parseColor("#3a9ffd"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "Check out this amazing recipe I found!\nYou can go on: " + recipe.getSourceUrl() + " to find more!";
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Share recipe..."));
            }
        });
        commentsButton.setVisibility(View.VISIBLE);
        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CommentsFragment(recipe);
                Home.fm.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("my_fragment").commit();
            }
        });

    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(
                listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;

        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private List getDiet(Recipe recipe) {
        List<String> diet = new ArrayList<String>();
        if (recipe.isVegetarian()){
            diet.add("vegetarian");
        }
        if (recipe.isVegan()){
            diet.add("vegan");
        }
        if (recipe.isDairyFree()){
            diet.add("dairy free");
        }
        if (recipe.isGlutenFree()){
            diet.add("gluten free");
        }
        if (recipe.isKetogenic()){
            diet.add("ketogenic");
        }
        if (recipe.isCheap()){
            diet.add("cheap");
        }
        return diet;
    }
}