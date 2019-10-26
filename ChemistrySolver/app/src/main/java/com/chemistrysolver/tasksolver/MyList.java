package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// класс для установки каждому элементу адаптера нескольких значений
public class MyList {

    // номер химической величины; позиция в spinner; позиция в spinnerUnits; позиция в spinnerDensity
    Integer number, position, positionUnits, positionDensity;
    // вводимаое пользователем число
    String text;
    // адаптеры для Spinner
    ArrayAdapter<String> adapter, adapterUnitsOfMeasurement;
    // химическое вещество, которому принадлежит информация
    ArrayList<String> elements;
    // единицы измерения химической величины
    String[] units;

    public MyList(Context context, ArrayList<String> elements, String[] units, int number){
        this.elements = elements;
        this.units = units;
        this.number = number;
        // форматирование текста в spinner
        if (elements.size() != 0) {
            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, elements) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    // текст внутри spinner
                    ((TextView) v).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Candara.ttf"));
                    ((TextView) v).setTextSize(13f);
                    return v;
                }
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    // текст внутри spinner
                    ((TextView) v).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Candara.ttf"));
                    (v).setBackgroundColor(Color.parseColor("#C6F2F6"));
                    ((TextView) v).setTextSize(18f);
                    return v;
                }
            };
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

        if (units.length != 0) {
            adapterUnitsOfMeasurement = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, units) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    // текст внутри spinnerUnitsOfMeasurement
                    ((TextView) v).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Candara.ttf"));
                    ((TextView) v).setTextSize(13f);
                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    // текст внутри spinnerUnitsOfMeasurement
                    ((TextView) v).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Candara.ttf"));
                    (v).setBackgroundColor(Color.parseColor("#C6F2F6"));
                    ((TextView) v).setTextSize(18f);
                    return v;
                }
            };
            adapterUnitsOfMeasurement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

    }
}
