package com.example.sortify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Button btnBasic = findViewById(R.id.btn_basic);
        Button btnIntermediate = findViewById(R.id.btn_intermediate);
        Button btnAdvanced = findViewById(R.id.btn_advanced);

        btnBasic.setOnClickListener(view -> startActivity(new Intent(Quiz.this, BasicQuizActivity.class)));
        btnIntermediate.setOnClickListener(view -> startActivity(new Intent(Quiz.this, IntermediateQuizActivity.class)));
        btnAdvanced.setOnClickListener(view -> startActivity(new Intent(Quiz.this, AdvancedQuizActivity.class)));
    }
}
