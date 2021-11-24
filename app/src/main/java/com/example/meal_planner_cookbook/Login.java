package com.example.meal_planner_cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    EditText regemail, regpassword;
    Button login, goRegister;

    User user;
    private FirebaseAuth mAuth; //firebase authenticator
    DatabaseReference rootReference, childReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        regemail = findViewById(R.id.emailLoginET);
        regpassword = findViewById(R.id.passwordLoginET);
        login = findViewById(R.id.loginB);
        goRegister = findViewById(R.id.goRegisterB);
        user = new User(); //new user
        mAuth = FirebaseAuth.getInstance(); //initialize firebase authenticator

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterForm();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }
    private void loginUser() {
        //get text box values
        String email = regemail.getText().toString().trim();
        String password = regpassword.getText().toString().trim();
        //errors
        //TODO: handle other errors as well
        if(email.isEmpty()){
            regemail.setError("Email name is required!");
            regemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regemail.setError("Please provide valid email!");
            regemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            regpassword.setError("Password is required!");
            regpassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            regpassword.setError("Minimum password length should be 6 characters!");
            regpassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Failed to login user!\nCheck credentials!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void openRegisterForm() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}