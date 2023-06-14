package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz.Models.QuestionModel;
import com.example.quiz.databinding.ActivityQuestionBinding;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    ActivityQuestionBinding binding;
    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int score = 0;
    private int position = 0;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        reserTime();
        timer.start();

        String setNmae = getIntent().getStringExtra("set");
        if(setNmae.equals("Quiz-1")){

            setOne();
        } else if (setNmae.equals("Quiz-2")) {
            setTwo();
        }
        for(int i =0; i<4;i++){
            binding.optionCont.getChildAt(i).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    checkAnswer((Button) view);
                }

                private void checkAnswer(Button selectedOption) {

                    if(timer !=null){
                        timer.cancel();
                    }

                    binding.btnNext.setEnabled(true);
                    binding.btnNext.setAlpha(1);
                    if(selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())){
                        score++;
                        selectedOption.setBackgroundResource(R.drawable.right_answ);
                    }
                    else{
                        selectedOption.setBackgroundResource((R.drawable.wrong_answ));
                        Button correctOption = (Button) binding.optionCont.findViewWithTag(list.get(position).getCorrectAnswer());
                        correctOption.setBackgroundResource(R.drawable.right_answ);
                    }
                }
            });
        }

        playAnimation(binding.question, 0,list.get(position).getQuetion());


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (timer !=null){
                    timer.cancel();
                }

                timer.start();

                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha((float)0.3);
                enableOption(true);

                position ++;
                if(position==list.size()){

                    Intent intent = new Intent(QuestionActivity.this,ScoreActivity.class);
                    intent.putExtra("Score",score);
                    intent.putExtra("total",list.size());
                    startActivity(intent);
                    finish();
                    return;
                }

                count=0;
                playAnimation(binding.question,0,list.get(position).getQuetion());
            }

        });
    }

    private void reserTime() {
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                binding.timer.setText(String.valueOf((l/1000)));
            }

            @Override
            public void onFinish() {

                Dialog dialog =new Dialog(QuestionActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionActivity.this,SetsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();

            }
        };
    }

    private void playAnimation(View view, int value, String data) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                        if(value ==0 && count <4){
                            String option = "";
                            if(count ==0){
                                option = list.get(position).getOptionA();
                            }else if(count ==1){
                                option = list.get(position).getOptionB();
                            }
                            else if(count ==2){
                                option = list.get(position).getOptionC();
                            }
                            else if(count ==3){
                                option = list.get(position).getOptionD();
                            }
                            playAnimation(binding.optionCont.getChildAt(count), 0,option);
                            count ++;
                        }

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {


                        if(value==0){

                            try {
                                ((TextView) view).setText(data);
                                binding.totalquestion.setText(position+1+"/"+list.size());
                            } catch (Exception e){
                                ((Button)view).setText(data);
                            }
                            view.setTag(data);
                            playAnimation(view, 1,data);

                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                });
    }

    private void enableOption(boolean enable) {

        for(int i=0;i<4;i++) {

            binding.optionCont.getChildAt(i).setEnabled(enable);
            if(enable){
                binding.optionCont.getChildAt(i).setBackgroundResource(R.drawable.button_opt);
            }

        }


        }

    private void setOne() {


        list.add(new QuestionModel(" Kepanjangan dari HTML adalah..","Hyper Text Markup Language","Hyper Link Mobile Language",
                "Hyper Text Markup Languenge","Hyper Tell Markup Language","Hyper Text Markup Language"));

        list.add(new QuestionModel("Tag HTML yang memiliki peran penting untuk menunjukan bagian halaman web adalah...","Body","Table",
                "Heading","Row","Heading"));

        list.add(new QuestionModel("Tag yang digunakan pada HTML untuk mengenter atau baris baru adalah...","<tr>","<br>",
                "<ul>","<li>","<br>"));

        list.add(new QuestionModel("Ordered list pada HTML dibuat dengan tag...","<li>","<p>",
                "<hr>","<ol>","<ol>"));

        list.add(new QuestionModel("Tabel tag <tr> pada HTML saat membuat tabel berfungsi untuk...","Membuat Kolom","Membuat Baris",
                "Membuat Judul","Membuat List","Membuat Baris"));

    }
    private void setTwo() {

        list.add(new QuestionModel("PHP Merupakan singkatan dari?","Private Home Page","Personal Hypertext Processor",
                "PHP: Hypertext Processor","Program Hypertext Processor","PHP: Hypertext Processor"));

        list.add(new QuestionModel("Kode PHP diawali dan di akhiri dengan tanda?","<?php … </?php>","<?php … ?>\n",
                "<php … /?>"," <script> … </script>","<?php … ?>"));

        list.add(new QuestionModel("Sintak untuk mencetak output ‘Hello World’ di PHP?","echo “Hello World”;"," document.write (“Hello World”)",
                "System.out.print(“Hello World”);","cout<<“Hello World”;","echo “Hello World”;"));

        list.add(new QuestionModel("Berikut ini contoh operator aritmatika, kecuali","+","%",
                ">=","/",">="));

        list.add(new QuestionModel("Tipe data yang hanya memiliki nilai true dan false adalah tipe data?","Varchar","int",
                "Decimal","Boolean","Bolean"));

    }


}