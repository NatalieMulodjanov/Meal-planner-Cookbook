package com.example.meal_planner_cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    // https://finalproject-35e77-default-rtdb.firebaseio.com/
    EditText regfullname, regusername, regemail, regpassword;
    Button register, goLogin;

    User user;

    DatabaseReference rootReference, childReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //link to the view
        regfullname = findViewById(R.id.fullnameRegisterET);
        regusername = findViewById(R.id.usernameRegisterET);
        regemail = findViewById(R.id.emailRegisterET);
        regpassword = findViewById(R.id.passwordRegisterET);
        register = findViewById(R.id.registerB);
        goLogin = findViewById(R.id.goLoginB);
        user = new User(); //new user

        //firebase reference
        rootReference = FirebaseDatabase.getInstance().getReference(); //connect to firebase
        childReference = rootReference.child("user"); //create user table

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get text box values
                String fullname = regfullname.getText().toString();
                String username = regusername.getText().toString();
                String email = regemail.getText().toString();
                String password = regpassword.getText().toString();
                //set user data
                user.setFullname(fullname);
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                childReference.child(user.getFullname()).setValue(user);
                Toast.makeText(getApplicationContext(),
                        "data entered successfully", Toast.LENGTH_SHORT).show();
            }
        });
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginForm();
            }
        });
    }

    private void openLoginForm() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}