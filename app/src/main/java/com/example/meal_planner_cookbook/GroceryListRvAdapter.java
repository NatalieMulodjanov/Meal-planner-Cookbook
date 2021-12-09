package com.example.meal_planner_cookbook;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.List;
import java.util.Map;

public class GroceryListRvAdapter extends RecyclerView.Adapter <GroceryListRvAdapter.ViewHolder>{
    private Map<String, Recipe> data;
    private LayoutInflater inflator;
    private Context context;

    public GroceryListRvAdapter(Context context, Map<String, Recipe> data) {
        this.data = data;
        this.inflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public GroceryListRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.grocery_list_rv_child, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryListRvAdapter.ViewHolder holder, int position) {
        Recipe currentRecipe = data.get(data.keySet().toArray()[position]);
        holder.textView.setText(currentRecipe.getTitle());
        Glide.with(inflator.getContext()).asBitmap().load(new GlideUrl(currentRecipe.getImage(), new LazyHeaders.Builder().addHeader("User-Agent", "your_user_agent").build())).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SingleRecipeFragment(currentRecipe);
                Home.fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        for (Ingredient ingredient: currentRecipe.getExtendedIngredients()) {
            LinearLayout horizontalLayout = new LinearLayout(holder.itemView.getContext());
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            horizontalLayout.setLayoutParams(p);

            TextView toDoText = new TextView(holder.itemView.getContext());
            toDoText.setTextSize(20);
            toDoText.setText(ingredient.getOriginalString());
            toDoText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            CheckBox checkBox = new CheckBox(holder.itemView.getContext());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checkBox.isChecked()) {
                        toDoText.setPaintFlags(toDoText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                        toDoText.setPaintFlags(0);
                    }
                }
            });

            toDoText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                        toDoText.setPaintFlags(0);
                    } else {
                        checkBox.setChecked(true);
                        toDoText.setPaintFlags(toDoText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            });

            LinearLayout.LayoutParams radioParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            radioParams.setMargins(20,0,0,0);
            checkBox.setLayoutParams(radioParams);

            horizontalLayout.addView(checkBox);
            horizontalLayout.addView(toDoText);
//            horizontalLayout.addView(quantityText);

            holder.linearLayout.addView(horizontalLayout);
        }

        holder.itemView.setId((int)currentRecipe.getId());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
//        TextView toDoText;
//        RadioButton radioButton;
        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            toDoText = itemView.findViewById(R.id.toDoText);
            linearLayout = itemView.findViewById(R.id.groceryListLinearLayout);
            imageView = itemView.findViewById(R.id.recipeImage);
            textView = itemView.findViewById(R.id.recipeTitle);
        }
    }

}
