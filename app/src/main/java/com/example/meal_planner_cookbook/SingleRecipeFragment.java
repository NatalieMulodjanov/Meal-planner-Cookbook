package com.example.meal_planner_cookbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.ArrayList;
import java.util.List;

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

        Call<Recipe> recipeCall = api.getRecipeById(this.id);

        recipeCall.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Recipe recipe = response.body();
                for(Ingredient ingredient1 : recipe.getExtendedIngredients()){
                    ingredientList.add(ingredient1.getOriginalString());
                }

                title.setText(recipe.getTitle());
                Glide.with(getView()).asBitmap().load(new GlideUrl(recipe.getImage(), new LazyHeaders.Builder().addHeader("User-Agent", "your_user_agent").build())).into(image);
                instructions.setText(Html.fromHtml(recipe.getInstructions()));
                List<String> diets = getDiet(recipe);
                ArrayAdapter adapter = new ArrayAdapter(getContext(),
                        R.layout.ingredient_list_item, R.id.ingredient, ingredientList);
                ingredients.setAdapter(adapter);
                ArrayAdapter adapterDiet = new ArrayAdapter(getContext(),
                        R.layout.ingredient_list_item, R.id.ingredient, diets);
                diet.setAdapter(adapterDiet);
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
            }
        });
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