package com.example.meal_planner_cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CodeVerification extends AppCompatActivity {

    EditText verificationCode;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        verificationCode = findViewById(R.id.codeET);
        verify = findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("code", verificationCode.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
