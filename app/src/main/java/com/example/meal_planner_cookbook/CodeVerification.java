package com.example.meal_planner_cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CodeVerification extends AppCompatActivity {

    EditText verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        verificationCode = findViewById(R.id.codeET);

        Intent intent = getIntent();
        intent.putExtra("code", verificationCode.getText().toString());
        setResult(RESULT_OK, intent);
        finish();

    }
}
