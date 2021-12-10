package com.example.meal_planner_cookbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    Button edit, save, logout;
    EditText accfullname, accusername, accemail, accphone, accpassword;
    ProgressBar progressBar;
    boolean editVis, saveVis;

    FirebaseUser user;
    DatabaseReference reference;
    DatabaseReference rootReference, childReference;
    String userID;
    Intent intent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
            //find views
            accfullname = getView().findViewById(R.id.fullnameAccountET);
            accusername = getView().findViewById(R.id.usernameAccountET);
            accemail = getView().findViewById(R.id.emailAccountET);
            accphone = getView().findViewById(R.id.phoneAccountET);
            accpassword = getView().findViewById(R.id.passwordAccountET);
            edit = getView().findViewById(R.id.editAccountB);
            edit.setClickable(true); //set edit to un-clickable
            edit.setVisibility(View.VISIBLE); //set edit to visible
            save = getView().findViewById(R.id.saveAccountB);
            save.setClickable(false); // set save to un-clickable
            save.setVisibility(View.INVISIBLE); //set edit to invisible
            logout = getView().findViewById(R.id.logoutAccountB);
            progressBar = getView().findViewById(R.id.accountPB);
            //get user
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference();
            //preset user's values
            showData();

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accfullname.setClickable(true);
                    accusername.setClickable(true);
                    accemail.setClickable(true);
                    accphone.setClickable(true);
                    accpassword.setClickable(true);
                    edit.setClickable(false); //set edit to un-clickable
                    edit.setVisibility(View.INVISIBLE); //set edit to visible
                    save.setClickable(true); // set save to un-clickable
                    save.setVisibility(View.VISIBLE); //set edit to invisible
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(getContext(), Login.class));
                }
            });
        }
    }

    private void showData() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("user");
        userID = user.getUid();
        String userName = (String) user.getDisplayName();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(user.getUid())){
                        //get values of user
                        String fullname = dataSnapshot.child("fullname").getValue(String.class);
                        String username = dataSnapshot.child("username").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String phone = dataSnapshot.child("phone").getValue(String.class);
                        String password = dataSnapshot.child("password").getValue(String.class);

                        //insert values in view
                        accfullname.setText(fullname);
                        accusername.setText(username);
                        accemail.setText(email);
                        accphone.setText(phone);
                        accpassword.setText(password);
                    }
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),
                        "Something went wrong!", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}