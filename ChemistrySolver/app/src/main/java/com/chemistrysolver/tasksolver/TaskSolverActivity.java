package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chemistrysolver.R;

// активность ввода пользователем реакции или веществ
public class TaskSolverActivity extends AppCompatActivity implements View.OnClickListener {

    // переменная, отслеживающая, какое из диалоговых окон должно быть
    boolean f;
    // поле ввода реакции или веществ
    EditText input;
    // заголовок, зависимый от того, что вводится; введённый текст; подзаголовок, зависимый от того; переход в новую активность
    TextView inputTV, entered, enteredText, nextt;
    Typeface typeface;
    // кнопка ввода
    Button enter;
    // переход в следующую активность
    RelativeLayout next;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_solver);

        context = this;

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString("Решение задач");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        f = true;
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Candara.ttf");
        input = findViewById(R.id.react);
        input.setTypeface(typeface);
        inputTV = findViewById(R.id.input);
        inputTV.setTypeface(typeface);
        entered = findViewById(R.id.entered);
        entered.setTypeface(typeface);
        entered.setVisibility(View.INVISIBLE);
        enteredText = findViewById(R.id.enteredtext);
        enteredText.setTypeface(typeface);
        enteredText.setVisibility(View.INVISIBLE);
        enter = findViewById(R.id.enter);
        enter.setTypeface(typeface);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(enter.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (f) {
                    if (!input.getText().toString().equals("") && input.getText().toString().contains("+") &&
                            input.getText().toString().contains("=")) {
                        entered.setText(getSsb(getNewText(String.valueOf(input.getText()))));
                        entered.setVisibility(View.VISIBLE);
                        enteredText.setText("Реакция");
                        enteredText.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                    } else {
                        entered.setText("В уравнении реакции есть ошибки");
                        entered.setVisibility(View.VISIBLE);
                        enteredText.setText("Реакция");
                        enteredText.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (!input.getText().toString().equals("")) {
                        entered.setText(input.getText());
                        entered.setVisibility(View.VISIBLE);
                        enteredText.setText("Вещества");
                        enteredText.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
        next = findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        next.setOnClickListener(this);
        nextt = findViewById(R.id.nextt);
        nextt.setOnClickListener(this);

        showCustomDialog();

    }

    // создание диалогового окна с вопросами
    private void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder
                .setView(dialogView)
                .setCancelable(false)
                .create();

        final TextView question = dialogView.findViewById(R.id.question);
        question.setTypeface(typeface);
        if (f) {
            inputTV.setText("Введите реакцию");
            input.setHint("Введите реакцию");
            input.setCompoundDrawablesWithIntrinsicBounds( R.drawable.circle_edittext_reaction, 0, 0, 0);
            question.setText("В задаче говорится о проходящей реакциии?");
        } else {
            inputTV.setText("Введите вещества");
            input.setHint("Введите вещества");
            input.setCompoundDrawablesWithIntrinsicBounds( R.drawable.circle_edittext_atom, 0, 0, 0);
            question.setText("Известно ли вещество?");
        }
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button ok = dialogView.findViewById(R.id.ok);
                ok.setTypeface(typeface);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                Button no = dialogView.findViewById(R.id.no);
                no.setTypeface(typeface);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (f) {
                            f = false;
                            alertDialog.dismiss();
                            showCustomDialog();
                        } else if (!f){
                            Intent intent = new Intent(context, GivenActivity.class);
                            intent.putExtra("REACTION", "X");
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }

    // форматирование отображаемого текста
    public String getNewText(String text){
        int count = text.length();
        String newText  = text;
        if (count > 20){
            for (int k = 1; k <= count / 20 ; k++) {
                for (int i = 20 * k; i >= 0; i--) {
                    if (newText.charAt(i) == '+'){
                        String string = newText.substring(0, i) + "+\n+" + newText.substring(i + 1, newText.length());
                        newText = string;
                        break;
                    } else if (newText.charAt(i) == '='){
                        String string = newText.substring(0, i) + "=\n=" + newText.substring(i + 1, newText.length());
                        newText = string;
                        break;
                    }
                }
            }
            String string = "\n" + newText + "\n";
            newText = string;
        } else newText = "\n" + text + "\n";
        return newText;
    }

    // форматирование отображаемого текста
    public SpannableStringBuilder getSsb(String newText){
        String number = "1234567890";
        SpannableStringBuilder ssb = new SpannableStringBuilder(newText);
        for (int i = 0; i < newText.length(); i++) {
            if (number.contains(String.valueOf(newText.charAt(i))) && i == 1){
                for (int k = i; k < newText.length(); k++) {
                    if (number.contains(String.valueOf(newText.charAt(k)))) i++;
                    else break;
                }
                ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#6068F0")), 0, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (newText.charAt(i) == '+' || newText.charAt(i) == '='){
                i++; if (number.contains(String.valueOf(newText.charAt(i)))){
                    int l = 0;
                    for (int k = i; k < newText.length(); k++) {
                        if (number.contains(String.valueOf(newText.charAt(k)))) l++;
                        else break;
                    }
                    ssb.setSpan(new StyleSpan(Typeface.BOLD), i, i + l, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#6068F0")), i, i + l, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    i += l;
                }
            } else if (number.contains(String.valueOf(newText.charAt(i)))){
                ssb.setSpan(new SubscriptSpan(), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ssb;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == next.getId() || view.getId() == nextt.getId()){
            if (next.getVisibility() == View.VISIBLE){
                Intent intent = new Intent(this, GivenActivity.class);
                intent.putExtra("REACTION", input.getText().toString());
                startActivity(intent);
            }
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
