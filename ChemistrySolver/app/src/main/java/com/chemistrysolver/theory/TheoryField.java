package com.chemistrysolver.theory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chemistrysolver.R;

// класс отображения списка теории
public class TheoryField extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View theoryField = inflater.inflate(R.layout.theory, container, false);
        // Список всех тем
        ListView listView = theoryField.findViewById(R.id.theory_list);
        // адаптер для красивого отображения тем
        ThemeAdapter themeAdapter = new ThemeAdapter(theoryField.getContext(), R.layout.theory_items, getResources().getStringArray(R.array.theme));
        listView.setAdapter(themeAdapter);
        // обработка нажатия на тему списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // открытие активности с текстом темы, на которую нажали
                Intent intent = new Intent(view.getContext(), ThemeActivity.class);
                intent.putExtra("NUMBER", i);
                startActivity(intent);
            }
        });
        return theoryField;
    }
}
