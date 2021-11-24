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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    // https://finalproject-35e77-default-rtdb.firebaseio.com/
    EditText regfullname, regusername, regemail, regphone, regpassword, regconfirmpassword;
    Button register, goLogin;

    User user;
    private FirebaseAuth mAuth; //firebase authenticator
    DatabaseReference rootReference, childReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //link to the view
        regfullname = findViewById(R.id.fullnameRegisterET);
        regusername = findViewById(R.id.usernameRegisterET);
        regemail = findViewById(R.id.emailRegisterET);
        regphone = findViewById(R.id.phoneRegisterET);
        regpassword = findViewById(R.id.passwordRegisterET);
        regconfirmpassword = findViewById(R.id.confirmpasswordRegisterET);
        register = findViewById(R.id.registerB);
        goLogin = findViewById(R.id.goLoginB);
        user = new User(); //new user
        mAuth = FirebaseAuth.getInstance(); //initialize firebase authenticator

        //firebase reference
        rootReference = FirebaseDatabase.getInstance().getReference(); //connect to firebase
        childReference = rootReference.child("user"); //create user table

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class)); //redirect to login
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        //get text box values
        String fullname = regfullname.getText().toString();
        String username = regusername.getText().toString();
        String email = regemail.getText().toString().trim();
        String phone = regphone.getText().toString().trim();
        String password = regpassword.getText().toString().trim();
        String confirm = regconfirmpassword.getText().toString().trim();
        //errors
        //TODO: handle other errors as well
        if(fullname.isEmpty()){
            regfullname.setError("Full name is required!");
            regfullname.requestFocus();
            return;
        }
        if(username.isEmpty()){
            regusername.setError("Username is required!");
            regusername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            regemail.setError("Email name is required!");
            regemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regemail.setError("Please provide a valid email!");
            regemail.requestFocus();
            return;
        }
        if(phone.isEmpty()) {
            regphone.setError("Phone number is required!");
            regphone.requestFocus();
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
        if(confirm.isEmpty()){
            regconfirmpassword.setError("Password confirmation is required!");
            regconfirmpassword.requestFocus();
            return;
        }
        if(!confirm.equals(password)){
            regconfirmpassword.setError("Passwords do not match!");
            regconfirmpassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user = new User(fullname, username, email, phone, password); //set user data
                    childReference.child(user.getFullname()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() { //insert user
                        @Override
                        public void onComplete(@NonNull Task<Void> task) { //checks
                            if(task.isSuccessful()){ //if successful register
                                Toast.makeText(getApplicationContext(),
                                    "User registered successfully", Toast.LENGTH_LONG).show(); //confirmation message
                                startActivity(new Intent(getApplicationContext(), Login.class)); //redirect to login
                            } else { //if non-successful register
                                Toast.makeText(getApplicationContext(),
                                    "Failed to register user!\nTry again!", Toast.LENGTH_LONG).show(); //error message
                            }
                        }
                    });
                } else { //if unsuccessful register
                    Toast.makeText(getApplicationContext(),
                            "Failed to register user!\nTry again!", Toast.LENGTH_LONG).show(); //error message
                }
            }
        });

    }
}