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

import java.util.List;
import java.util.Map;

public class CommentsRvAdapter extends RecyclerView.Adapter <CommentsRvAdapter.ViewHolder>{
    private List<Comment> data;
    private LayoutInflater inflator;

    public CommentsRvAdapter(Context context, List<Comment> data) {
        this.data = data;
        this.inflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CommentsRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.comments_rv_child, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsRvAdapter.ViewHolder holder, int position) {
        holder.username.setText(data.get(position).getUsername());
        holder.comment.setText(data.get(position).getComment());
        holder.timestamp.setText(data.get(position).getTimestamp());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView username;
        TextView comment;
        TextView timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.commentsRvName);
            comment = itemView.findViewById(R.id.commentsRvComment);
            timestamp = itemView.findViewById(R.id.commentsRvTimestamp);
        }
    }

}
