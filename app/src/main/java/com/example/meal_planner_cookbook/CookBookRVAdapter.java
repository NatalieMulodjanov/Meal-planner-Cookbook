package com.example.meal_planner_cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.HashMap;
import java.util.Map;

public class CookBookRVAdapter extends RecyclerView.Adapter <CookBookRVAdapter.ViewHolder>{
    private Map<String, Recipe> data;
    private LayoutInflater inflator;

    public CookBookRVAdapter(Context context, Map<String, Recipe> data) {
        this.data = data;
        this.inflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CookBookRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.child_view, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CookBookRVAdapter.ViewHolder holder, int position) {
        Recipe currentRecipe = data.get(data.keySet().toArray()[position]);
        holder.title.setText(currentRecipe.getTitle());
        Glide.with(inflator.getContext()).asBitmap().load(new GlideUrl(currentRecipe.getImage(), new LazyHeaders.Builder().addHeader("User-Agent", "your_user_agent").build())).into(holder.image);
        int cookedUnder = (int)currentRecipe.getReadyInMinutes();
        holder.cookedUnder.setText("Cooked in " + cookedUnder + " Minutes");
        holder.itemView.setId((int)currentRecipe.getId());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView image;
        TextView cookedUnder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            cookedUnder = itemView.findViewById(R.id.cookedUnderText);
        }


        @Override
        public void onClick(View view) {
            String id = String.valueOf(view.getId());
            Recipe currentRecipe = data.get(id);
            Fragment fragment = new SingleRecipeFragment(currentRecipe);
            Home.fm.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("my_fragment").commit();
        }
    }

}
