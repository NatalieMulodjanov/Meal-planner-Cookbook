package com.example.meal_planner_cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.navigationBNB);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DiscoverFragment()).commit(); //set initial fragment
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