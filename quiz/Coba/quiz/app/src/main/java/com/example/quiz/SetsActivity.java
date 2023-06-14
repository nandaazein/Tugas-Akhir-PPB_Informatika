package com.example.quiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.quiz.Adapters.SetAdapter;
import com.example.quiz.Models.SetModel;
import com.example.quiz.databinding.ActivitySetsBinding;

import java.util.ArrayList;

public class SetsActivity extends AppCompatActivity {
ActivitySetsBinding binding;
ArrayList<SetModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.setsRecy.setLayoutManager(linearLayoutManager);

        list.add(new SetModel("Quiz-1"));
        list.add(new SetModel("Quiz-2"));
        list.add(new SetModel("Quiz-3"));
        list.add(new SetModel("Quiz-4"));
        list.add(new SetModel("Quiz-5"));
        list.add(new SetModel("Quiz-6"));
        list.add(new SetModel("Quiz-7"));
        list.add(new SetModel("Quiz-8"));
        list.add(new SetModel("Quiz-9"));
        list.add(new SetModel("Quiz-10"));

        SetAdapter adapter = new SetAdapter(this, list);
        binding.setsRecy.setAdapter(adapter);
    }
}