package com.chemistrysolver.theory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chemistrysolver.R;

// адаптер для красивого отображения тем
public class ThemeAdapter extends ArrayAdapter<String> {

    private Context context;
    // XML-разметка, отображаемая в теме
    private int resource;
    // массив пунктов теории темы
    private String[] array;

    // конструктор адаптера
    public ThemeAdapter(Context context, int resource, String[] array) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) convertView = inflater.inflate(resource, null);
        // Текстовое поле, отображающее часть теории
        TextView textView = convertView.findViewById(R.id.theme);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf"));
        textView.setTextSize(18f);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(15, 15, 5, 15);
        textView.setText(array[position]);

        return convertView;
    }
}
