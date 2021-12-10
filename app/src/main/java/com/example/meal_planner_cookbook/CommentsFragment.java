package com.example.meal_planner_cookbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentsFragment extends Fragment {

    RecyclerView recyclerView;
    CommentsRvAdapter adapter;
    Recipe recipe;
    Button submitComment;
    TextView commentTextView;
    FirebaseDatabase rootNode;
    DatabaseReference commentsReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommentsFragment() {
        // Required empty public constructor
    }

    public CommentsFragment(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentsFragment newInstance(String param1, String param2) {
        CommentsFragment fragment = new CommentsFragment();
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
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.commentsRv);
        submitComment = view.findViewById(R.id.submitComment);
        commentTextView = view.findViewById(R.id.comment);
        rootNode = FirebaseDatabase.getInstance();
        commentsReference = rootNode.getReference("recipeComments");
        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new Comment("TestUser", commentTextView.getText().toString(), new Date().toString());
                commentsReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            CommentRecipe currentCommentRecipe = dataSnapshot.getValue(CommentRecipe.class);
                            if (currentCommentRecipe.getRecipeId().equals(String.valueOf(recipe.getId()))) {
                                if (currentCommentRecipe.getComments().contains(comment)) {
                                    return;
                                }
                                List<Comment> comments = currentCommentRecipe.getComments();
                                comments.add(comment);
                                commentsReference.child(String.valueOf(recipe.getId())).setValue(currentCommentRecipe);
                                return;
                            }
                        }


                        List<Comment> comments = new ArrayList<Comment>();
                        comments.add(comment);
                        commentsReference.child(String.valueOf(recipe.getId())).setValue(new CommentRecipe(String.valueOf(recipe.getId()), comments));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        commentsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    CommentRecipe currentCommentRecipe = dataSnapshot.getValue(CommentRecipe.class);
                    if (currentCommentRecipe.getRecipeId().equals(String.valueOf(recipe.getId()))) {
                        List<Comment> comments = currentCommentRecipe.getComments();
                        commentsReference.child(String.valueOf(recipe.getId())).setValue(currentCommentRecipe);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new CommentsRvAdapter(getContext(), comments);
                        recyclerView.setAdapter(adapter);
                        commentTextView.setText("");
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}