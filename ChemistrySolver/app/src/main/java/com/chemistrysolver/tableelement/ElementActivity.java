package com.chemistrysolver.tableelement;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.chemistrysolver.R;

import java.util.ArrayList;

// активность, в которой отображается информауия об элементе
public class ElementActivity extends AppCompatActivity {

    // Переменная для доступа к полям класса TableElementActivity
    TableElementActivity tableElementActivity;

    // Характеристики атома и описание этих характеристик
    ArrayList<String> characteristics;

    // название характеристики
    ArrayList<String> description;

    // Расширяемый список
    ExpandableListView expandableListView;

    // Группы, которые будут отображаться в списке
    ArrayList<ArrayList<MyList>> groups;

    // Индекс, получаемый из класса TableElementActivity
    Integer i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_element);

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString("Информация об элементе");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        // Инициализация
        characteristics = new ArrayList<>();
        description = new ArrayList<>();
        groups = new ArrayList<>();

        // Получение индекса из класса MainActivity
        i = getIntent().getIntExtra("INDEX", -1);

        tableElementActivity = new TableElementActivity();

        // Проверка на пустое значение
        if (tableElementActivity.elements == null) {
            tableElementActivity.fillElements();
        }

        // вызов заполнения характеристик выбранного атома
        fillCharacteristic();

        expandableListView = findViewById(R.id.myExpListView);
        // Создание экземпляра класса адаптера
        MyExpListAdapter adapter = new MyExpListAdapter(this, makeList());

        // Подключение адаптера
        expandableListView.setAdapter(adapter);

    }

    // Метод присваивания значений для групп и детей расширяемого списка
    public ArrayList<ArrayList<MyList>> makeList() {

        // Создание массива детей первой группы
        ArrayList<MyList> myLists0 = new ArrayList<>();
        // Создание массива детей второй группы
        ArrayList<MyList> myLists1 = new ArrayList<>();
        // Создание массива детей третьей группы
        ArrayList<MyList> myLists2 = new ArrayList<>();

        // Проверка на пустое значение
        if ((characteristics == null) || (description == null)) {
            fillCharacteristic();
        }

        for (int i = 0; i < 20; i++) {

            // Создание экземпляра класса MyList для доступа к его полям
            MyList myList = new MyList();

            myList.ssb = null;
            myList.strCharacteristics = characteristics.get(i);
            myList.strDescription = description.get(i);

            //Заполнение массивов детей разных групп
            switch (i) {
                case 0: {
                    myLists0.add(myList);
                    break;
                }
                case 1: {
                    myLists0.add(myList);
                    break;
                }
                case 2: {
                    myLists0.add(myList);
                    break;
                }
                case 3: {
                    myLists0.add(myList);
                    break;
                }
                case 4: {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(characteristics.get(i) + " p+");
                    spannableStringBuilder.setSpan(new SuperscriptSpan(), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableStringBuilder.setSpan(new RelativeSizeSpan(0.75f), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    myList.ssb = spannableStringBuilder;
                    myLists0.add(myList);
                    break;
                }
                case 5: {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(characteristics.get(i) + " e-");
                    spannableStringBuilder.setSpan(new SuperscriptSpan(), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableStringBuilder.setSpan(new RelativeSizeSpan(0.75f), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    myList.ssb = spannableStringBuilder;
                    myLists0.add(myList);
                    break;
                }
                case 6: {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(characteristics.get(i) + " n0");
                    spannableStringBuilder.setSpan(new SuperscriptSpan(), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableStringBuilder.setSpan(new RelativeSizeSpan(0.75f), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    myList.ssb = spannableStringBuilder;
                    myLists0.add(myList);
                    break;
                }
                case 7: {
                    myLists0.add(myList);
                    break;
                }
                case 8: {
                    myLists0.add(myList);
                    if (characteristics.get(i) == " ") {
                        myLists0.remove(i - (i - myLists0.size()) - 1);
                    }
                    break;
                }
                case 9: {
                    myLists0.add(myList);
                    if (Double.valueOf(characteristics.get(i)) % 1 == 0.0) {
                        myList.strCharacteristics = "[" + characteristics.get(i) + "]";
                        myLists0.set(i - (i - myLists0.size()) - 1, myList);
                    }
                    break;
                }
                case 10: {
                    myLists0.add(myList);
                    //Создание spannableStringBuilder для красивого отображения строки
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(characteristics.get(i));
                    spannableStringBuilder.setSpan(new SuperscriptSpan(), characteristics.get(i).length() - 1, characteristics.get(i).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableStringBuilder.setSpan(new RelativeSizeSpan(0.75f), characteristics.get(i).length() - 1, characteristics.get(i).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    for (int k = 0; k < characteristics.get(i).length() - 1; k++) {
                        if ((characteristics.get(i).charAt(k) == '1') || (characteristics.get(i).charAt(k) == '2') ||
                                (characteristics.get(i).charAt(k) == '3') || (characteristics.get(i).charAt(k) == '4') ||
                                (characteristics.get(i).charAt(k) == '5') || (characteristics.get(i).charAt(k) == '6') ||
                                (characteristics.get(i).charAt(k) == '7') || (characteristics.get(i).charAt(k) == '8') ||
                                (characteristics.get(i).charAt(k) == '9') || (characteristics.get(i).charAt(k) == '0')) {
                            if ((characteristics.get(i).charAt(k + 1) == '1') || (characteristics.get(i).charAt(k + 1) == '2') ||
                                    (characteristics.get(i).charAt(k + 1) == '3') || (characteristics.get(i).charAt(k + 1) == '4') ||
                                    (characteristics.get(i).charAt(k + 1) == '5') || (characteristics.get(i).charAt(k + 1) == '6') ||
                                    (characteristics.get(i).charAt(k + 1) == '7') || (characteristics.get(i).charAt(k + 1) == '8') ||
                                    (characteristics.get(i).charAt(k + 1) == '9') || (characteristics.get(i).charAt(k + 1) == '0') ||
                                    characteristics.get(i).charAt(k + 1) == ' ') {
                                spannableStringBuilder.setSpan(new SuperscriptSpan(), k, k + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                spannableStringBuilder.setSpan(new RelativeSizeSpan(0.75f), k, k + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    }
                    myList.ssb = spannableStringBuilder;
                    myLists0.set(i - (i - myLists0.size()) - 1, myList);
                    break;
                }
                case 11: {
                    myLists0.add(myList);
                    if (characteristics.get(i) == " ") {
                        myLists0.remove(i - (i - myLists0.size()) - 1);
                    }
                    break;
                }
                case 12: {
                    myLists0.add(myList);
                    if (characteristics.get(i) == " ") {
                        myLists0.remove(i - (i - myLists0.size()) - 1);
                    }
                    break;
                }
                case 13: {
                    myLists1.add(myList);
                    break;
                }
                case 14: {
                    myLists1.add(myList);
                    if (characteristics.get(i) != " ") {
                        //Создание spannableStringBuilder для красивого отображения строки
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(characteristics.get(i));
                        for (int k = 0; k < characteristics.get(i).length(); k++) {
                            if ((characteristics.get(i).charAt(k) == '1') || (characteristics.get(i).charAt(k) == '2') ||
                                    (characteristics.get(i).charAt(k) == '3') || (characteristics.get(i).charAt(k) == '4') ||
                                    (characteristics.get(i).charAt(k) == '5') || (characteristics.get(i).charAt(k) == '6') ||
                                    (characteristics.get(i).charAt(k) == '7') || (characteristics.get(i).charAt(k) == '8') ||
                                    (characteristics.get(i).charAt(k) == '9')
                                    || (characteristics.get(i).charAt(k) == '+') || (characteristics.get(i).charAt(k) == '-')) {
                                spannableStringBuilder.setSpan(new SuperscriptSpan(), k, k + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                spannableStringBuilder.setSpan(new RelativeSizeSpan(0.75f), k, k + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                        myList.ssb = spannableStringBuilder;
                        myLists1.set(i - (i - myLists1.size()) - 1, myList);
                    } else {
                        myLists1.remove(i - (i - myLists1.size()) - 1);
                    }
                    break;
                }
                case 15: {
                    myLists1.add(myList);
                    if (Double.valueOf(characteristics.get(i)) == 0.0) {
                        myLists1.remove(i - (i - myLists1.size()) - 1);
                    }
                    break;
                }
                case 16: {
                    myLists2.add(myList);
                    if (Double.valueOf(characteristics.get(i)) == -1000.0) {
                        myLists2.remove(i - (i - myLists2.size()) - 1);
                    } else {
                        myList.strCharacteristics = characteristics.get(i) + " °C";
                        myLists2.set(i - (i - myLists2.size()) - 1, myList);
                    }
                    break;
                }
                case 17: {
                    myLists2.add(myList);
                    if (Double.valueOf(characteristics.get(i)) == -1000.0) {
                        myLists2.remove(i - (i - myLists2.size()) - 1);
                    } else {
                        myList.strCharacteristics = characteristics.get(i) + " °C";
                        myLists2.set(i - (i - myLists2.size()) - 1, myList);

                    }
                    break;
                }
                case 18: {
                    myLists2.add(myList);
                    if (Double.valueOf(characteristics.get(i)) == 0.0) {
                        myLists2.remove(i - (i - myLists2.size()) - 1);
                    } else {
                        myList.strCharacteristics = characteristics.get(i) + "  г/см³";
                        myLists2.set(i - (i - myLists2.size()) - 1, myList);

                    }
                    break;
                }
                case 19: {
                    myLists2.add(myList);
                    if (characteristics.get(i) != " ") {
                        if (characteristics.get(i) == "Жидкое") {
                            myLists2.set(i - (i - myLists2.size()) - 1, myList);
                        } else if (characteristics.get(i) == "Газообразное") {
                            myLists2.set(i - (i - myLists2.size()) - 1, myList);
                        } else if (characteristics.get(i) == "Твёрдое") {
                            myLists2.set(i - (i - myLists2.size()) - 1, myList);
                        }
                    } else {
                        myLists2.remove(i - (i - myLists2.size()) - 1);
                    }
                    break;
                }
            }

        }

        groups.add(myLists0);

        groups.add(myLists1);

        //Чтобы не отображать группу без детей, надо проверить размер масива детей этой группы
        if (myLists2.size() != 0) {
            groups.add(myLists2);
        }

        return groups;
    }

    // заполнение характеристик выбранного атома
    private void fillCharacteristic() {
        characteristics = new ArrayList<>();
        description = new ArrayList<>();

        characteristics.add(tableElementActivity.elements.get(i).designation);
        characteristics.add(tableElementActivity.elements.get(i).name);
        characteristics.add(tableElementActivity.elements.get(i).latName);
        characteristics.add(tableElementActivity.elements.get(i).number.toString());
        characteristics.add(tableElementActivity.elements.get(i).number.toString());
        characteristics.add(tableElementActivity.elements.get(i).number.toString());
        int a = (int) (tableElementActivity.elements.get(i).atomicWeight - tableElementActivity.elements.get(i).number);
        characteristics.add(String.valueOf(a));
        characteristics.add(tableElementActivity.elements.get(i).period.toString());
        characteristics.add(tableElementActivity.elements.get(i).group);
        characteristics.add(tableElementActivity.elements.get(i).atomicWeight.toString());
        characteristics.add(tableElementActivity.elements.get(i).electronConfiguration);
        characteristics.add(tableElementActivity.elements.get(i).radioactive);
        characteristics.add(tableElementActivity.elements.get(i).made);

        characteristics.add(tableElementActivity.elements.get(i).oxidationState);
        characteristics.add(tableElementActivity.elements.get(i).ion);
        characteristics.add(tableElementActivity.elements.get(i).electronegativity.toString());

        characteristics.add(tableElementActivity.elements.get(i).meltingPoint.toString());
        characteristics.add(tableElementActivity.elements.get(i).boilingPoint.toString());
        characteristics.add(tableElementActivity.elements.get(i).density.toString());
        characteristics.add(tableElementActivity.elements.get(i).condition);


        description.add("Символ");
        description.add("Название");
        description.add("Латинское название");
        description.add("Номер");
        description.add("Протоны");
        description.add("Электроны");
        description.add("Нейтроны");
        description.add("Период");
        description.add("Группа");
        description.add("Атомная масса");
        description.add("Электронная конфигурация");
        description.add("Радиоактивность");
        description.add("Синтезированный элемент");
        description.add("Степени окисления");
        description.add("Ионы");
        description.add("Электроотрицательность");
        description.add("Температура плавления");
        description.add("Температура кипения");
        description.add("Плотность");
        description.add("Агрегатное состояние");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
