package com.chemistrysolver;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// класс, нужный для отображения трёх пунктов выдвижного меню: "Помощь", "Информация", "Разработчики"
public class InfoHelpField extends Fragment {

    // текст, который преобразуется в данном классе
    public int stringResource;
    // XML-разметка, которая будет отображаться в данном фрагменте
    public int resource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View informationField = inflater.inflate(resource, container, false);

        // Текстовое поле, отображающее текст в данном фрагменте
        TextView textView = informationField.findViewById(R.id.information);
        textView.setText(stringResource);
        textView.setTypeface(Typeface.createFromAsset(informationField.getContext().getAssets(), "fonts/gothic.ttf"));

        return informationField;
    }
}
