package com.chemistrysolver.theory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.TreeSet;

import com.chemistrysolver.R;

// класс отображения списка избранного
public class FavoritesField  extends Fragment {

    // экземпляр класса TheoryActivity для доступа к элементам этого класса
    TheoryActivity theoryActivity;
    // ключ, по которому сохраняются элементы в SharedPreferences
    final String FAVOR = "Favor";
    // пременная, хранящая выбранные темы
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favoritesField = inflater.inflate(R.layout.theory, container, false);
        // Список тем, которые пользователь выбрал
        ListView listView = favoritesField.findViewById(R.id.theory_list);

        theoryActivity = new TheoryActivity();
        // получение значений по нужному ключу
        sharedPreferences = favoritesField.getContext().getSharedPreferences(FAVOR, Context.MODE_PRIVATE);
        // присвоение Set<String> содержащего в себе номера выбранных тем (записаны в качестве String)
        theoryActivity.favours = this.sharedPreferences.getStringSet(FAVOR, new TreeSet<String>());
        // массив, содержащий в себе темы, которые выбрал пользователь
        final String[] strings = new String[theoryActivity.favours.size()];
        // массив, содержащий в себе список всех тем, которые есть в приложении
        final String[] theme = getResources().getStringArray(R.array.theme);
        // индекс, используемый при переборе foreach
        int j = 0;
        // присвоение элементам массива название темы
        for (String s: theoryActivity.favours) {
            strings[j] = theme[Integer.valueOf(s)];
            j++;
        }
        // адаптер для красивого отображения тем
        ThemeAdapter themeAdapter = new ThemeAdapter(favoritesField.getContext(), R.layout.theory_items, strings);
        listView.setAdapter(themeAdapter);
        // обработка нажатия на тему списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // номер темы, на которую нажали
                int j = 0;
                // поиск номера темы
                for (int k = 0; k < theme.length; k++) {
                    if (strings[i].equals(theme[k])){
                        j = k; break;
                    }
                }
                // открытие активности с текстом темы, на которую нажали
                Intent intent = new Intent(view.getContext(), ThemeActivity.class);
                intent.putExtra("NUMBER", j);
                startActivity(intent);
            }
        });
        return favoritesField;
    }
}