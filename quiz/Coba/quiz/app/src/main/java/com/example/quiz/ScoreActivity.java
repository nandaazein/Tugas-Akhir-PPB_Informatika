package com.example.quiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quiz.databinding.ActivityScoreBinding;

public class ScoreActivity extends AppCompatActivity {

    ActivityScoreBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        int totalScore = getIntent().getIntExtra("total",0);
        int correctAnsw = getIntent().getIntExtra("Score",0);

        int wrong = totalScore - correctAnsw;
        binding.totalQuestion.setText(String.valueOf(totalScore));
        binding.rightAnsw.setText(String.valueOf(correctAnsw));

        binding.wrongAnsw.setText(String.valueOf(wrong));
        binding.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ScoreActivity.this,SetsActivity.class);
                startActivity(intent);
                finish();

            }
        });
binding.btnQuit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});

    }
}