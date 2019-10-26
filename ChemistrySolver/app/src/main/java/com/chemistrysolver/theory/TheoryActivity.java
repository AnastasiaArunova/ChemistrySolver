package com.chemistrysolver.theory;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;

import java.util.Set;
import java.util.TreeSet;

import com.chemistrysolver.R;

// активность, отображающая список тем и список избранных тем
public class TheoryActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // пременная, хранящая выбранные темы
    SharedPreferences sharedPreferences;
    // темы, которые пользователь занёс в "Избранное"
    public Set<String> favours;
    // ключ, по которому получается массив избранного
    final String FAVOR = "Favor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString("Краткая теория");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        // получение значений по нужному ключу
        sharedPreferences = this.getSharedPreferences(FAVOR, Context.MODE_PRIVATE);
        // получение из sharedPreferences сохранённого значения
        favours = this.sharedPreferences.getStringSet(FAVOR, new TreeSet<String>());

        // нижнее навигационное меню
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.action_theory);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Фрагмент, который будет отображаться после нажатия
        Fragment fragment = null;
        // фрагмент принимает значение в зависимости от того, на что нажали. Либо "Избранное", либо "Теория"
        if (item.getItemId() == R.id.action_theory){
            fragment = new TheoryField();
            item.setChecked(true);
        }
        if (item.getItemId() == R.id.action_favorites) {
            fragment = new FavoritesField();
            item.setChecked(true);
        }
        // смена фрагмента после нажатия
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.field, fragment).commit();
        }
        return false;
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
