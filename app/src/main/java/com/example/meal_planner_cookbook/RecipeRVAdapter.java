package com.example.meal_planner_cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

public class RecipeRVAdapter extends RecyclerView.Adapter <RecipeRVAdapter.ViewHolder>{
    private ApiResults data;
    private LayoutInflater inflator;

    public RecipeRVAdapter(Context context, ApiResults data) {
        this.data = data;
        this.inflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.child_view, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeRVAdapter.ViewHolder holder, int position) {
        if (data != null) {
            if (data instanceof GetRandomRecipesResponse){
                GetRandomRecipesResponse response = (GetRandomRecipesResponse)data;
                String title = response.getRecipes().get(position).getTitle();
                if (response.getRecipes().get(position).getImage() != null && !response.getRecipes().get(position).getImage().equals("")) {
                    Glide.with(inflator.getContext()).asBitmap().load(new GlideUrl(response.getRecipes().get(position).getImage(), new LazyHeaders.Builder().addHeader("User-Agent", "your_user_agent").build())).into(holder.image);
                }
                holder.title.setText(holder.title.getText() + title);
                holder.itemView.setId((int)response.getRecipes().get(position).getId());
            } else if (data instanceof GetSearchResultsResponse){
                GetSearchResultsResponse response = (GetSearchResultsResponse)data;
                String title = response.getResults().get(position).getTitle();
                if (response.getResults().get(position).getImage() != null && response.getBaseUri() != null) {
                    String imageUrl = response.getBaseUri() + response.getResults().get(position).getImage();
                    Glide.with(inflator.getContext()).asBitmap().load(new GlideUrl(imageUrl, new LazyHeaders.Builder().addHeader("User-Agent", "your_user_agent").build())).into(holder.image);
                }
                holder.title.setText(holder.title.getText() + title);
                holder.itemView.setId((int)response.getResults().get(position).getId());
            }
        }
    }


    @Override
    public int getItemCount() {
        if (data instanceof GetSearchResultsResponse){
            return ((GetSearchResultsResponse)data).getResults().size();
        } else if (data instanceof GetRandomRecipesResponse){
            return ((GetRandomRecipesResponse)data).getRecipes().size();
        }
        return 0;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }


        @Override
        public void onClick(View view) {
            int id = view.getId();
            Fragment fragment = new SingleRecipeFragment(id);
            Home.fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }

}
