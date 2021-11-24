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

    UserHelper user;
    long maxid = 0;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

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
        //get all the values
        String fullname = regfullname.getText().toString();
        String username = regusername.getText().toString();
        String email = regemail.getText().toString();
        String password = regpassword.getText().toString();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    maxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance(); //connect to firebase
                reference = rootNode.getReference().child("User"); //set the path to correct node
                UserHelper user = new UserHelper(fullname, username, email, password); //new user
                reference.child(String.valueOf(maxid + 1)).setValue(user); //add the user with the title of username
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