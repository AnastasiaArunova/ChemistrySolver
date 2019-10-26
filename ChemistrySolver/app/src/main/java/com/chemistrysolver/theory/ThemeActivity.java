package com.chemistrysolver.theory;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.TreeSet;

import com.chemistrysolver.R;

// класс отображения скписка тем
public class ThemeActivity extends AppCompatActivity {

    // номер темы
    private int number = 0;
    TheoryActivity theoryActivity;
    // пременная для сохранения выбранных тем
    SharedPreferences sharedPreferences;
    // ключ, по которому сохраняются элементы в SharedPreferences
    final String FAVOR = "Favor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        theoryActivity = new TheoryActivity();
        number = getIntent().getIntExtra("NUMBER", 0);

        // получение значений по нужному ключу
        sharedPreferences = this.getSharedPreferences(FAVOR, Context.MODE_PRIVATE);
        // присвоение полю класса, содержащего в себе номера выбранных тем (записаны в качестве String) сохранённое значение
        theoryActivity.favours = this.sharedPreferences.getStringSet(FAVOR, new TreeSet<String>());

        // массив с темами
        String[] array = getResources().getStringArray(R.array.theme);

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString(array[number]);
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        // массив для того, чтобы получить из него массив пунктов
        TypedArray typedArray = getResources().obtainTypedArray(R.array.themes);
        // Список тем
        ListView listView = findViewById(R.id.theory_list);
        // адаптер для красивого отображения тем
        ThemeAdapter themeAdapter = new ThemeAdapter(this, R.layout.theme_text,
                getResources().getStringArray(typedArray.getResourceId(number, 0)));
        listView.setAdapter(themeAdapter);
        typedArray.recycle();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // сохранение данных
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(FAVOR, theoryActivity.favours);
        editor.clear();
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // сохранение данных
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putStringSet(FAVOR, theoryActivity.favours);
        editor.apply();
    }

    // отображение звёздочки в нажатой теме
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.theme_menu, menu);
        if (theoryActivity.favours.contains(String.valueOf(number))){
            menu.getItem(0).setIcon(R.drawable.ic_star);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // закрытие активности при нажатии стрелки "Назад"
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        // изменеие иконки звёздочки и сохранения в "Избранном" темы в зависимости от нажатия
        if (item.getItemId() == R.id.star){
            if (!theoryActivity.favours.contains(String.valueOf(number))) {
                item.setIcon(R.drawable.ic_star);
                // добавление темы в "Избранное"
                theoryActivity.favours.add(String.valueOf(number));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putStringSet(FAVOR, theoryActivity.favours);
                editor.apply();
            } else {
                item.setIcon(R.drawable.ic_star1);
                // удаление темы из "Избранного"
                if (theoryActivity.favours.contains(String.valueOf(number))) {
                    theoryActivity.favours.remove(String.valueOf(number));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.putStringSet(FAVOR, theoryActivity.favours);
                    editor.apply();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
