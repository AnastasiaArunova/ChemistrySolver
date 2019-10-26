package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import com.chemistrysolver.R;

// активность, в которой пользователь заполняет информацию о том, что дано в задаче
public class GivenActivity extends AppCompatActivity implements CheckItemDialog.OnDialogDismissed {

    // заголовок "Дано"; стрелка "Далее"
    protected TextView give, nextt;
    private Typeface typeface;
    // кнопка добавления того, что дано
    protected Button add;
    // поле в низу экрана, при нажатии на которое, появляется новая активность
    protected RelativeLayout next;
    // список химических величин, которые даны
    private MyListView givenItems;
    // химичексие величины, их ед. измерения, вещество
    private ArrayList<MyList> myLists;
    // адаптер для отображения списка того, что двно
    private MyListAdapter myListAdapter;
    // Хранит в себе химические соединения
    private ArrayList<String> elements;
    // реакция или вещества; то, что дано, написанное в виде строки
    protected String text, givenText = "";
    // переменная, проверяющая, что все поля заполненны корректно; проверка на то, что говориться ли в задаче о реакции
    private boolean f = false, isNotReaction = false;
    private Context context;
    // вещества правой и левой части уравнения реакции
    private ArrayList<String> leftPart, rightPart;
    // Хранит для каждого соединения, то что про него известно (химическая величина и её значение)
    private HashMap<String, HashMap<Integer, Double>> given;
    private CheckItemDialog.OnDialogDismissed onDialogDismissed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling);

        context = this;
        onDialogDismissed = this;

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString("Дано");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        given = new HashMap<>();
        myLists = new ArrayList<>();
        elements = new ArrayList<>();
        text = getIntent().getStringExtra("REACTION");
        if (!text.equals("")){
            textRecognizing(text);
            leftPart = deleteCoefficients(leftPart);
            rightPart = deleteCoefficients(rightPart);
            for (int i = 0; i < leftPart.size(); i++) {
                elements.add(leftPart.get(i));
            }
            for (int i = 0; i < rightPart.size(); i++) {
                elements.add(rightPart.get(i));
            }
            if (elements.size() == 0){
                textRecognizing1(text);
                for (int i = 0; i < leftPart.size(); i++) {
                    elements.add(leftPart.get(i));
                }
                isNotReaction = true;
            }
        }

        typeface = Typeface.createFromAsset(getAssets(), "fonts/Candara.ttf");
        give = findViewById(R.id.given);
        give.setTypeface(typeface);
        add = findViewById(R.id.add);
        givenItems = findViewById(R.id.filleditems);
        next = findViewById(R.id.next_fill);
        nextt = findViewById(R.id.nextt_fill);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // диалоговое окно, содержащее список химических величин
                CheckItemDialog myDialog = new CheckItemDialog(context, view, elements, isNotReaction, myLists, true, true, onDialogDismissed);
                myDialog.createDialog();
            }
        });

        // адаптер, отображающий химическую величину, её значение и ед. измерения
        myListAdapter = new MyListAdapter(this, R.layout.given_adapter_elements, myLists);
        givenItems.setAdapter(myListAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
                if (!f){
                    // тост сообщающий о том, что надо заполнять поля корректно
                    Toast toast = Toast.makeText(context, "Заполните все поля корректно", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View customToast = toast.getView();
                    customToast.setBackgroundColor(Color.parseColor("#057081"));
                    TextView text = customToast.findViewById(android.R.id.message);
                    text.setTypeface(typeface);
                    text.setTextSize(20f);
                    text.setPadding(15, 0, 15, 0);
                    toast.show();
                } else{
                    getGiven();
                    // переход в активность того, что нужно найти, с передачей данных
                    Intent intent = new Intent(context, FindActivity.class);
                    intent.putExtra("ELEMENTS", elements);
                    intent.putExtra("REACTION", text);
                    intent.putExtra("LEFT_PART", leftPart);
                    intent.putExtra("RIGHT_PART", rightPart);
                    intent.putExtra("MAP_GIVEN", given);
                    intent.putExtra("GIVEN_TEXT", givenText);
                    intent.putExtra("IS_NOT_REACTION", isNotReaction);
                    startActivity(intent);
                }
            }
        });

        nextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
                if (!f){
                    // тост сообщающий о том, что надо заполнять поля корректно
                    Toast toast = Toast.makeText(context, "Заполните все поля корректно", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View customToast = toast.getView();
                    customToast.setBackgroundColor(Color.parseColor("#057081"));
                    TextView text = customToast.findViewById(android.R.id.message);
                    text.setTypeface(typeface);
                    text.setTextSize(20f);
                    text.setPadding(15, 0, 15, 0);
                    toast.show();
                } else{
                    getGiven();
                    // переход в активность того, что нужно найти, с передачей данных
                    Intent intent = new Intent(context, FindActivity.class);
                    intent.putExtra("ELEMENTS", elements);
                    intent.putExtra("REACTION", text);
                    intent.putExtra("LEFT_PART", leftPart);
                    intent.putExtra("RIGHT_PART", rightPart);
                    intent.putExtra("MAP_GIVEN", given);
                    intent.putExtra("GIVEN_TEXT", givenText);
                    intent.putExtra("IS_NOT_REACTION", isNotReaction);
                    startActivity(intent);
                }
            }
        });

    }

    // распознавание веществ в ведённом тексте
    private void textRecognizing1(String text){
        leftPart = new ArrayList<>();
        String element = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ') {
                for (int j = i; j < text.length(); j++) {
                    if (text.charAt(j) != ' ') {
                        element += text.charAt(j);
                    } else {
                        leftPart.add(element);
                        element = "";
                        i = j;
                        break;
                    }
                    if (j == text.length() - 1){
                        leftPart.add(element);
                        i = j;
                        break;
                    }
                }
            }
        }
    }

    // распознавание веществ в правой и левой части уравнения реакции
    private void textRecognizing(String text) {
        leftPart = new ArrayList<>();
        rightPart = new ArrayList<>();
        int flag = 0;
        String element = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != '+' && text.charAt(i) != '=') {
                for (int j = i; j < text.length(); j++) {
                    if (text.charAt(j) != '+' && text.charAt(j) != '=') {
                        element += text.charAt(j);
                    } else {
                        if (flag == 0) leftPart.add(element);
                        if (flag == 1) rightPart.add(element);
                        if (text.charAt(j) == '=') flag = 1;
                        element = "";
                        i = j;
                        break;
                    }
                    if (j == text.length() - 1 && flag == 1) {
                        rightPart.add(element);
                        element = "";
                        i = text.length();
                    }
                }
            }
        }
    }

    // удаление коэффициентов в реакции
    private ArrayList<String> deleteCoefficients(ArrayList<String> arrayList) {
        String numbers = "1234567890";
        for (int i = 0; i < arrayList.size(); i++) {
            if (numbers.contains(String.valueOf(arrayList.get(i).charAt(0)))) {
                arrayList.set(i, arrayList.get(i).substring(1));
                i--;
            }
        }
        return arrayList;
    }

    // проверка на корректность заполнения полей списка
    private void check(){
        for (int i = 0; i < givenItems.getCount(); i++) {
            View view = givenItems.getChildAt(i);
            EditText num = view.findViewById(R.id.editnum);
            Spinner spinnerUnits = view.findViewById(R.id.spinnerunits);
            if (!num.getText().toString().equals("") && Double.valueOf(num.getText().toString()) > 0.0
                    && (spinnerUnits.getSelectedItemPosition() != 0 || spinnerUnits.getVisibility() == View.INVISIBLE)) {
                f = true;
            } else {
                f = false;
                break;
            }
        }
        if (givenItems.getCount() == 0) f = true;
    }

    // создание строки того, что дано, для отображения этого в решении
    private void getGiven(){
        if (elements.size() != 0) {
            for (int i = 0; i < givenItems.getCount(); i++) {
                View view = givenItems.getChildAt(i);
                TextView designation = view.findViewById(R.id.designation);
                givenText += designation.getText().toString();
                Spinner spinner = view.findViewById(R.id.spinner);
                if (!designation.getText().toString().contains("Q")) {
                    givenText += spinner.getSelectedItem().toString();
                    givenText += ") = ";
                } else givenText += " = ";
                EditText num = view.findViewById(R.id.editnum);
                givenText += num.getText().toString();
                Spinner spinnerUnits = view.findViewById(R.id.spinnerunits);
                givenText += " ";
                givenText += spinnerUnits.getSelectedItem().toString();
                Double number = 0.0;
                if (spinnerUnits.getVisibility() == View.INVISIBLE)
                    number = Double.valueOf(num.getText().toString());
                else
                    number = converter(myLists.get(i).number, spinnerUnits.getSelectedItemPosition(), Double.valueOf(num.getText().toString()));

                if (given.containsKey(spinner.getSelectedItem().toString())) {
                    HashMap<Integer, Double> hashMap1 = given.get(spinner.getSelectedItem().toString());
                    given.remove(spinner.getSelectedItem().toString());
                    given.put(spinner.getSelectedItem().toString(),
                            addHashMap(hashMap1, myLists.get(i).number, number));
                } else {
                    HashMap<Integer, Double> hashMap = new HashMap<>();
                    hashMap.put(myLists.get(i).number, number);
                    given.put(spinner.getSelectedItem().toString(), hashMap);
                }
                givenText += "\n";
            }
        } else {
            HashMap<Integer, Double> hashMap = new HashMap<>();
            for (int i = 0; i < givenItems.getCount(); i++) {
                View view = givenItems.getChildAt(i);
                TextView designation = view.findViewById(R.id.designation);
                givenText += designation.getText().toString();
                EditText num = view.findViewById(R.id.editnum);
                givenText += " = ";
                givenText += num.getText().toString();
                Spinner spinnerUnits = view.findViewById(R.id.spinnerunits);
                givenText += spinnerUnits.getSelectedItem().toString();
                Double number = 0.0;
                if (spinnerUnits.getVisibility() == View.INVISIBLE)
                    number = Double.valueOf(num.getText().toString());
                else
                    number = converter(myLists.get(i).number, spinnerUnits.getSelectedItemPosition(), Double.valueOf(num.getText().toString()));
                hashMap.put(myLists.get(i).number, number);
                given.put("", hashMap);
                givenText += "\n";
            }
        }
    }

    // конвёртер величин
    private Double converter(int n, int unit, Double num){
        if (n == 0 || n == 1 || n == 2 || n == 3 || n == 4 || n == 7 || n == 8 || n == 17 || n == 21){
            if (unit == 1) return num;
            else if (unit == 2){
                givenText += " = ";
                givenText += num * 1000;
                givenText += " г";
                return num * 1000;
            }
            else if (unit == 3){
                givenText += " = ";
                givenText += num / 10;
                givenText += " г";
                return num / 10;
            }
        }
        if (n == 5 || n == 6){
            if (unit == 1){
                givenText += " = ";
                givenText += num / 100;
                givenText += " доля";
                return num / 100;
            }
            else if (unit == 2) return num;
        }
        if (n == 9 || n == 10 || n == 11 || n == 15 || n == 14){
            if (unit == 1 || unit == 3) return num;
            else if (unit == 2 || unit == 5){
                givenText += " = ";
                givenText += num / 1000;
                givenText += " л";
                return num / 1000;
            }
            else if (unit == 4){
                givenText += " = ";
                givenText += num * 1000;
                givenText += " л";
                return num * 1000;
            }
        }
        if (n == 20){
            if (unit == 1 || unit == 3) return num;
            else if (unit == 2 || unit == 5){
                givenText += " = ";
                givenText += num * 1000;
                givenText += " моль/л";
                return num * 1000;
            }
            else if (unit == 4){
                givenText += " = ";
                givenText += num * 1000;
                givenText += " моль/л";
                return num / 1000;
            }
        }
        if (n == 22){
            if (unit == 2) return num;
            else if (unit == 1){
                givenText += " = ";
                givenText += num / 1000;
                givenText += " моль/г";
                return num / 1000;
            }
            else if (unit == 3){
                givenText += " = ";
                givenText += num * 1000;
                givenText += " моль/г";
                return num * 1000;
            }
        }
        if (n == 23){
            if (unit == 2) return num;
            else if (unit == 1){
                givenText += " = ";
                givenText += num * 1000;
                givenText += " г/л";
                return num * 1000;
            }
        }
        if (n == 24){
            if (unit == 1) return num;
            else if (unit == 2){
                givenText += " = ";
                givenText += num / 101325;
                givenText += " атм";
                return num / 101325;
            }
        }
        if (n == 25){
            if (unit == 1) return num;
            else if (unit == 2){
                givenText += " = ";
                givenText += num + 273;
                givenText += " K";
                return num + 273;
            }
            else if (unit == 3){
                givenText += " = ";
                givenText += (num - 32) * 5 / 9 + 273;
                givenText += " K";
                return (num - 32) * 5 / 9 + 273;
            }
        }
        if (n == 26 || n == 27){
            if (unit == 3 || unit == 4) return num;
            else if (unit == 1 || unit == 2){
                givenText += " = ";
                givenText += num * 1000;
                givenText += " г/л";
                return num * 1000;
            }
        }
        if (n == 28 || n == 29){
            if (unit == 1) return num;
            else if (unit == 2){
                givenText += " = ";
                givenText += num / 1000;
                givenText += " кДж";
                return num / 1000;
            }
        }
        return 0.0;
    }

    // добавление того, что дано
    private HashMap<Integer, Double> addHashMap(HashMap<Integer, Double> hashMap1, Integer k, Double v){
        HashMap<Integer, Double> hashMap = new HashMap<>();
        hashMap.put(k, v);
        for (Integer key: hashMap1.keySet()) {
            hashMap.put(key, hashMap1.get(key));
        }
        return hashMap;
    }

    // получаем химические величины, отмеченные в диалоговом окне, вместе с параметрами
    @Override
    public void getMyList(ArrayList<MyList> myLists) {
        this.myLists = myLists;
        // обновление адаптера
        myListAdapter.notifyDataSetChanged();
        // адаптер, отображающий химическую величину, её значение и ед. измерения
        myListAdapter = new MyListAdapter(this, R.layout.given_adapter_elements, myLists);
        givenItems.setAdapter(myListAdapter);

    }

    // получаем химические элементы
    @Override
    public void getElements(ArrayList<String> elements) {
        this.elements = elements;
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
