package com.example.meal_planner_cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.List;

public class RandomRecipesRVAdapter extends RecyclerView.Adapter <RandomRecipesRVAdapter.ViewHolder>{
    private GetRandomRecipesResponse data;
    private LayoutInflater inflator;

    public RandomRecipesRVAdapter(Context context, GetRandomRecipesResponse data) {
        this.data = data;
        this.inflator = LayoutInflater.from(context);;
    }

    @NonNull
    @Override
    public RandomRecipesRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.child_view, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipesRVAdapter.ViewHolder holder, int position) {
        String title = data.getRecipes().get(position).getTitle();
        Glide.with(inflator.getContext()).asBitmap().load(new GlideUrl(data.getRecipes().get(position).getImage(), new LazyHeaders.Builder().addHeader("User-Agent", "your_user_agent").build())).into(holder.image);

        holder.title.setText(holder.title.getText() + title);
    }

    @Override
    public int getItemCount() {
        return data.getRecipes().size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }

    }
}
