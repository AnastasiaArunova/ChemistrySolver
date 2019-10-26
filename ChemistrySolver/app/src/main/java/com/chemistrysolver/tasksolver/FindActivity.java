package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.chemistrysolver.R;

import java.util.ArrayList;
import java.util.HashMap;

// активность, в которой пользователь заполняет информацию о том, что нужно найти
public class FindActivity extends AppCompatActivity implements CheckItemDialog.OnDialogDismissed {

    // заголовок "Найти"; стрелка "Далее"
    protected TextView findTV, nextt;
    // кнопка добавления того, что дано
    protected Button add;
    // поле в низу экрана, при нажатии на которое, появляется новая активность
    protected RelativeLayout next;
    // список химических величин, которые нужно найти
    private MyListView findItems;
    // адаптер для отображения списка того, что двно
    private MyListAdapter myListAdapter;
    // химичексие величины, их ед. измерения, вещество
    private ArrayList<MyList> myLists;
    private Context context;
    // проверка на то, что говориться ли в задаче о реакции
    private boolean isNotReaction = false;
    // Хранит в себе химические соединения
    private ArrayList<String> elements;
    // вещества правой и левой части уравнения реакции
    private ArrayList<String> leftPart, rightPart;
    // Хранит для каждого соединения, то что нужно найти
    private HashMap<String, ArrayList<Integer>> find;
    // Хранит для каждого соединения, то что про него известно (химическая величина и её значение)
    private HashMap<String, HashMap<Integer, Double>> given;
    // реакция или вещества; то, что дано, написанное в виде строки; то, что нужно найти, написанное в виде строки; газ для определённой задачи
    private String text = "", givenText = "", findText = "", gas = "";
    private CheckItemDialog.OnDialogDismissed onDialogDismissed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling);

        context = this;
        onDialogDismissed = this;

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString("Найти");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        elements = new ArrayList<>();
        elements = getIntent().getStringArrayListExtra("ELEMENTS");
        leftPart = new ArrayList<>();
        leftPart = getIntent().getStringArrayListExtra("LEFT_PART");
        rightPart = new ArrayList<>();
        rightPart = getIntent().getStringArrayListExtra("RIGHT_PART");
        text = getIntent().getStringExtra("REACTION");
        givenText = getIntent().getStringExtra("GIVEN_TEXT");
        isNotReaction = getIntent().getBooleanExtra("IS_NOT_REACTION", false);
        given = new HashMap<>();
        given =(HashMap<String, HashMap<Integer,Double>>) getIntent().getSerializableExtra("MAP_GIVEN");
        find = new HashMap<>();
        myLists = new ArrayList<>();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Candara.ttf");
        findTV = findViewById(R.id.given);
        findTV.setText("Найти");
        findTV.setTypeface(typeface);
        add = findViewById(R.id.add);
        findItems = findViewById(R.id.filleditems);
        next = findViewById(R.id.next_fill);
        nextt = findViewById(R.id.nextt_fill);
        if (elements.size() == 0) isNotReaction = true;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // диалоговое окно, содержащее список химических величин
                if (given.size() == 0) {
                    CheckItemDialog checkItemDialog = new CheckItemDialog(context, view, elements, isNotReaction, myLists, false, false, onDialogDismissed);
                    checkItemDialog.createDialog();
                } else {
                    CheckItemDialog checkItemDialog = new CheckItemDialog(context, view, elements, isNotReaction, myLists, false, true, onDialogDismissed);
                    checkItemDialog.createDialog();
                }
            }
        });

        // адаптер, отображающий химическую величину, её значение и ед. измерения
        myListAdapter = new MyListAdapter(this, R.layout.find_adapter_elements, myLists);
        findItems.setAdapter(myListAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFind();
                // переход в активность решения с передачей данных
                Intent intent = new Intent(context, SolveActivity.class);
                intent.putExtra("ELEMENTS", elements);
                intent.putExtra("LEFT_PART", leftPart);
                intent.putExtra("RIGHT_PART", rightPart);
                intent.putExtra("MAP_FIND", find);
                intent.putExtra("MAP_GIVEN", given);
                intent.putExtra("REACTION", text);
                intent.putExtra("GIVEN_TEXT", givenText);
                intent.putExtra("FIND_TEXT", findText);
                intent.putExtra("IS_NOT_REACTION", isNotReaction);
                intent.putExtra("GAS", gas);
                startActivity(intent);
            }
        });

        nextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFind();
                // переход в активность решения с передачей данных
                Intent intent = new Intent(context, SolveActivity.class);
                intent.putExtra("ELEMENTS", elements);
                intent.putExtra("LEFT_PART", leftPart);
                intent.putExtra("RIGHT_PART", rightPart);
                intent.putExtra("MAP_FIND", find);
                intent.putExtra("MAP_GIVEN", given);
                intent.putExtra("REACTION", text);
                intent.putExtra("GIVEN_TEXT", givenText);
                intent.putExtra("FIND_TEXT", findText);
                intent.putExtra("IS_NOT_REACTION", isNotReaction);
                intent.putExtra("GAS", gas);
                startActivity(intent);
            }
        });
    }

    // создание строки того, что нужно найти, для отображения этого в решении
    private void getFind(){
        if (elements.size() != 0) {
            for (int i = 0; i < findItems.getCount(); i++) {
                View view = findItems.getChildAt(i);
                TextView designation = view.findViewById(R.id.designation);
                findText += designation.getText().toString();
                if (myLists.get(i).number == 16){
                    Spinner spinnerForDensity = view.findViewById(R.id.densityspin);
                    gas = spinnerForDensity.getSelectedItem().toString();
                    findText += gas;
                    findText += "(";
                }
                Spinner spinner = view.findViewById(R.id.spinner);
                if (!designation.getText().toString().contains("Q"))
                    findText += spinner.getSelectedItem().toString();
                if (spinner.getVisibility() == View.VISIBLE) {
                    findText += ") = ? ";
                } else findText += " = ? ";
                if (find.containsKey(spinner.getSelectedItem().toString())) {
                    ArrayList<Integer> arrayList1 = find.get(spinner.getSelectedItem().toString());
                    find.remove(spinner.getSelectedItem().toString());
                    find.put(spinner.getSelectedItem().toString(), addArrayList(arrayList1, myLists.get(i).number));
                } else {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    arrayList.add(myLists.get(i).number);
                    find.put(spinner.getSelectedItem().toString(), arrayList);
                }
                findText += '\n';
            }
        } else {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = 0; i < findItems.getCount(); i++) {
                View view = findItems.getChildAt(i);
                TextView designation = view.findViewById(R.id.designation);
                findText += designation.getText().toString();
                findText += " = ? ";
                arrayList.add(myLists.get(i).number);
                findText += '\n';
            }
            find.put("", arrayList);
        }
    }

    // добавление того, что нужно найти
    private ArrayList<Integer> addArrayList(ArrayList<Integer> arrayList1, Integer v){
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(v);
        for (int i = 0; i < arrayList1.size(); i++) arrayList.add(arrayList1.get(i));
        return arrayList;
    }

    // получаем химические величины, отмеченные в диалоговом окне, вместе с параметрами
    @Override
    public void getMyList(ArrayList<MyList> myLists) {
        this.myLists = myLists;
        // обновление адаптера
        myListAdapter.notifyDataSetChanged();
        // адаптер, отображающий химическую величину, её значение и ед. измерения
        myListAdapter = new MyListAdapter(this, R.layout.find_adapter_elements, myLists);
        findItems.setAdapter(myListAdapter);

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
