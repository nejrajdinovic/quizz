package com.example.quizz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizz.R;
import com.example.quizz.database.QuizModal;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView question, questionNumber;
    private Button opt1, opt2, opt3, opt4;
    private ArrayList<QuizModal> quizModalList;
    Random random;
    int currentScore =0, questionAttempted =1, currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getItemById();
        random = new Random();
        getQuizQuestion();
        currentPosition = random.nextInt(quizModalList.size());
        setDataToViews(currentPosition);
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quizModalList.get(currentPosition).getAnswer().trim().toLowerCase().equals(opt1.getText().toString().trim().toLowerCase())){
                    currentScore++;
                }
                questionAttempted++;
                currentPosition = random.nextInt(quizModalList.size());
                setDataToViews(currentPosition);

            }
        });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quizModalList.get(currentPosition).getAnswer().trim().toLowerCase().equals(opt2.getText().toString().trim().toLowerCase())){
                    currentScore++;
                }
                questionAttempted++;
                currentPosition = random.nextInt(quizModalList.size());
                setDataToViews(currentPosition);

            }
        });



        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(currentPosition, opt3);
            }
        });

        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(currentPosition, opt4);
            }
        });
    }

    private void getItemById(){
        question = findViewById(R.id.question);
        questionNumber = findViewById(R.id.number_of_question);
        opt1 = findViewById(R.id.option_one);
        opt2 = findViewById(R.id.option_two);
        opt3 = findViewById(R.id.option_three);
        opt4 = findViewById(R.id.option_four);
    }

    private void getQuizQuestion() {
        quizModalList = new ArrayList<>();
        quizModalList.add(new QuizModal("Pitanje 1","da","mozda","ne", "ne znam", "da"));
        quizModalList.add(new QuizModal("Pitanje 2","da","mozda","ne", "ne znam", "da"));
        quizModalList.add(new QuizModal("Pitanje 3","da","mozda","ne", "ne znam", "da"));

    }

    @SuppressLint("SetTextI18n")
    private void setDataToViews(int currentPosition) {
       if(questionAttempted == quizModalList.size()+1){
           showBottomSheet();
       }else {
           questionNumber.setText("Questions Attempted:  "+ questionAttempted +"/"+ quizModalList.size());
           question.setText(quizModalList.get(currentPosition).getQuestion());
           opt1.setText(quizModalList.get(currentPosition).getOption1());
           opt2.setText(quizModalList.get(currentPosition).getOption2());
           opt3.setText(quizModalList.get(currentPosition).getOption3());
           opt4.setText(quizModalList.get(currentPosition).getOption4());
       }
    }

    private void checkAnswer(int currentPosition, Button pressButton){
        if (quizModalList.get(currentPosition).getAnswer().trim().toLowerCase().equals(pressButton.getText().toString().trim().toLowerCase())){
            currentScore++;
        }
        questionAttempted++;
        currentPosition = random.nextInt(quizModalList.size());
        setDataToViews(currentPosition);
    }

    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        View bottomSheetView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet, (LinearLayout)findViewById(R.id.score_layout));
        TextView score = bottomSheetView.findViewById(R.id.score);
        Button restartQuizBtn = bottomSheetView.findViewById(R.id.restart_button);
        score.setText("Your Score is \n "+currentScore+ "/" + quizModalList.size());
        restartQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataToViews(currentPosition);
                questionAttempted = 1;
                currentScore = 0;
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

}