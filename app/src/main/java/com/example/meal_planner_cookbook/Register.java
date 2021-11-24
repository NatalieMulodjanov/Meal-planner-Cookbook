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
    EditText regfullname, regusername, regemail, regpassword, regconfirmation;
    Button register, goLogin;

    User user;
    long maxid = 0;

    FirebaseDatabase rootNode;
    DatabaseReference rootReference;
    DatabaseReference childReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //link to the view
        regfullname = findViewById(R.id.fullnameRegisterET);
        regusername = findViewById(R.id.usernameRegisterET);
        regemail = findViewById(R.id.emailRegisterET);
        regpassword = findViewById(R.id.passwordRegisterET);
        regconfirmation = findViewById(R.id.passwordConfirmRegisterET);
        register = findViewById(R.id.registerB);
        goLogin = findViewById(R.id.goLoginB);

        //
        rootReference = FirebaseDatabase.getInstance().getReference();
        childReference = rootReference.child("user");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = regfullname.getText().toString();
                String username = regusername.getText().toString();
                String email = regemail.getText().toString();
                String password = regpassword.getText().toString();
                User user = new User(fullname, username, email, password); //new user
                childReference.setValue(user);
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