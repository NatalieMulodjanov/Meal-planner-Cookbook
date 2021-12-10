package com.example.meal_planner_cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {


    public static FragmentManager fm;
    BottomNavigationView navigationView;
    ImageView imageView;
    FirebaseUser user;
    DatabaseReference reference;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fm = getSupportFragmentManager();

//        if (findViewById(R.id.fragment_container )!= null){
//            FragmentTransaction ft = fm.beginTransaction();
//            RecipesFragment recipesFragment = new RecipesFragment();
//            ft.add(R.id.fragment_container,  recipesFragment, null);
//            ft.commit();
//        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        String userID = user.getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(userID)) {
                        currentUser = dataSnapshot.getValue(User.class);
                        currentUser.setId(userID);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        navigationView = findViewById(R.id.navigationBNB);
        imageView = findViewById(R.id.backImageButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                    fm.popBackStack();
                }
            }
        });

        fm.beginTransaction().replace(R.id.fragment_container, new RecipesFragment()).commit(); //set initial fragment
        navigationView.setSelectedItemId(R.id.nav_recipes); //set initial id to discover fragment
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //navigation listener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //set fragment to which ever of these depending on which tab is selected
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_discover:
                        fragment = new DiscoverFragment();
                        break;
                    case R.id.nav_guides:
                        fragment = new GuidesFragment();
                        break;
                    case R.id.nav_recipes:
                        fragment = new RecipesFragment();
                        break;
                    case R.id.nav_account:
                        fragment = new AccountFragment();
                        break;
                }
                fm.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("my_fragment2").commit(); //commit the fragment
                return true;
            }
        });
    }


}