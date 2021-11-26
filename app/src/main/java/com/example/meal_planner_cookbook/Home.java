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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {


    public static FragmentManager fm;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fm =getSupportFragmentManager();

//        if (findViewById(R.id.fragment_container )!= null){
//            FragmentTransaction ft = fm.beginTransaction();
//            RecipesFragment recipesFragment = new RecipesFragment();
//            ft.add(R.id.fragment_container,  recipesFragment, null);
//            ft.commit();
//        }

        navigationView = findViewById(R.id.navigationBNB);

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DiscoverFragment()).commit(); //set initial fragment
        navigationView.setSelectedItemId(R.id.nav_discover); //set initial id to discover fragment
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit(); //commit the fragment
                return true;
            }
        });
    }


}