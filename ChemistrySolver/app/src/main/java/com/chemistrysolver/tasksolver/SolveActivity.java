package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chemistrysolver.R;

import java.util.ArrayList;
import java.util.HashMap;

// активность, показывающая пользователю решение
public class SolveActivity extends AppCompatActivity implements Solve.OnSolveEnd {

    // текст того, что дано, что нужно найти, и ответ
    protected TextView givenTextView, findTextView, answerTextView;
    Typeface typeface;
    // Хранит для каждого соединения, то что нужно найти
    protected HashMap<String, ArrayList<Integer>> find;
    // Хранит для каждого соединения, то что про него известно (химическая величина и её значение)
    protected HashMap<String, HashMap<Integer, Double>> given;
    // Хранит в себе химические соединения
    protected ArrayList<String> elements;
    // вещества правой и левой части уравнения реакции
    protected ArrayList<String> leftPart, rightPart;
    // ответ; реакция или вещества; то, что дано, написанное в виде строки; то, что нужно найти, написанное в виде строки; газ для определённой задачи
    protected String answer = "", text = "", givenText = "", findText = "", gas = "";
    // переменная, с помощью которой осуществляется решение
    Solve solve;
    Solve.OnSolveEnd onSolveEnd;
    // решения
    ArrayList<ArrayList<String>> solutions;
    // непрокручиваемый список ответов
    MyListView answerList;
    // адаптер для ответов
    AnswerAdapter answerAdapter;
    // проверка на то, что говориться ли в задаче о реакции
    boolean isNotReaction;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        context = this;

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString("Решение");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/gothic.ttf");
        onSolveEnd = this;
        solutions = new ArrayList<>();

        answerTextView = findViewById(R.id.answertext);
        answerTextView.setTypeface(typeface);
        gas = "";
        answer = "";

        isNotReaction = getIntent().getBooleanExtra("IS_NOT_REACTION", false);
        givenText = getIntent().getStringExtra("GIVEN_TEXT");
        findText = getIntent().getStringExtra("FIND_TEXT");
        text = getIntent().getStringExtra("REACTION");
        find = new HashMap<>();
        find = (HashMap<String, ArrayList<Integer>>) getIntent().getSerializableExtra("MAP_FIND");
        given = new HashMap<>();
        given =(HashMap<String, HashMap<Integer,Double>>) getIntent().getSerializableExtra("MAP_GIVEN");
        elements = new ArrayList<>();
        elements = getIntent().getStringArrayListExtra("ELEMENTS");
        leftPart = new ArrayList<>();
        leftPart = getIntent().getStringArrayListExtra("LEFT_PART");
        rightPart = new ArrayList<>();
        rightPart = getIntent().getStringArrayListExtra("RIGHT_PART");
        gas = getIntent().getStringExtra("GAS");

        answerList = findViewById(R.id.answerlist);

        answerAdapter = new AnswerAdapter(this, R.layout.adapter_solution, solutions);
        answerList.setAdapter(answerAdapter);

        givenTextView = findViewById(R.id.giventv);
        givenTextView.setTypeface(typeface);
        givenTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilledDialog filledDialog = new FilledDialog(context, view, answerAdapter.getSsbText(givenText, 1));
                filledDialog.createDialog();
            }
        });

        findTextView = findViewById(R.id.findtv);
        findTextView.setTypeface(typeface);
        findTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilledDialog filledDialog = new FilledDialog(context, view, answerAdapter.getSsbText(findText, 1));
                filledDialog.createDialog();
            }
        });

        solve = new Solve(given, find, elements, text, isNotReaction,  leftPart, rightPart, gas, onSolveEnd);
    }

    // получение ответов
    @Override
    public void getAnswer(String answer) {
        this.answer = answer;
        answerTextView.setText(answer);
        if (solutions.size() != 0) {
            answerAdapter.notifyDataSetChanged();
            answerAdapter = new AnswerAdapter(this, R.layout.adapter_solution, solutions);
            answerList.setAdapter(answerAdapter);
        }
    }

    // получение решений
    @Override
    public void getSolutions(ArrayList<ArrayList<String>> solutions) {
        this.solutions = solutions;
        if (solutions.size() != 0) {
            answerAdapter.notifyDataSetChanged();
            answerAdapter = new AnswerAdapter(this, R.layout.adapter_solution, solutions);
            answerList.setAdapter(answerAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // закрытие активности при нажатии стрелки "Назад"
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
