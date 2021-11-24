package com.example.meal_planner_cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login, goRegister;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.usernameLoginET);
        password = findViewById(R.id.passwordLoginET);
        login = findViewById(R.id.loginB);
        goRegister = findViewById(R.id.goRegisterB);

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterForm();
            }
        });

    }

    //TODO: all other validations

    private boolean validateUsername() {
        String input = username.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(input.isEmpty()){
            username.setError("Field cannot be empty");
            return false;
        } else if(!input.matches(noWhiteSpace)){
            username.setError("White Spaces are not allowed");
            return false;
        }
        return true;
    }

    private void openRegisterForm() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}