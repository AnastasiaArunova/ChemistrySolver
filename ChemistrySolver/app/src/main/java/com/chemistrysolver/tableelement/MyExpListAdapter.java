package com.chemistrysolver.tableelement;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chemistrysolver.R;

import java.util.ArrayList;

// Адаптер для удобной работы с данными об элементах и их красивого отображения
public class MyExpListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ArrayList<MyList>> groups;
    private Typeface typeface;

    // Конструктор, в который будет передаваться массив групп
    public MyExpListAdapter(Context context, ArrayList<ArrayList<MyList>> groups){
        this.context = context;
        this.groups = groups;

        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf");
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.groups, null);
        }

        // Заголовок группы
        TextView textViewGroups;
        textViewGroups = convertView.findViewById(R.id.adapterGroups);
        textViewGroups.setTypeface(typeface);

        // Установка заголовка группы в зависимости от её номера
        if (groupPosition == 0){
            textViewGroups.setText("Свойства атома");
        } else if (groupPosition == 1){
            textViewGroups.setText("Химические свойства");
        } else if (groupPosition == 2){
            textViewGroups.setText("Термодинамические свойства");
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child, null);
        }

        // изображение, расположенное рядом с информацией об элементе
        ImageView imageView = convertView.findViewById(R.id.imageView);
        // значение свойства
        TextView textCharacteristics = convertView.findViewById(R.id.tvCharacteristics);
        // название свойства
        TextView textDescription = convertView.findViewById(R.id.tvDescription);

        textCharacteristics.setTypeface(typeface);
        textDescription.setTypeface(typeface);

        if (groups.get(groupPosition).get(childPosition).ssb == null) {
            textCharacteristics.setText(groups.get(groupPosition).get(childPosition).strCharacteristics);
        } else textCharacteristics.setText(groups.get(groupPosition).get(childPosition).ssb);
        textDescription.setText(groups.get(groupPosition).get(childPosition).strDescription);

        // установка изображения в зависимости от характеристики
        if (groups.get(groupPosition).get(childPosition).strDescription.equals("Символ")){
            imageView.setBackgroundResource(R.drawable.periodic_table);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Название") ){
            imageView.setBackgroundResource(R.drawable.periodic_table);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Латинское название")){
            imageView.setBackgroundResource(R.drawable.periodic_table);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Номер")){
            imageView.setBackgroundResource(R.drawable.periodic_table);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Протоны")){
            imageView.setBackgroundResource(R.drawable.proton);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Электроны")){
            imageView.setBackgroundResource(R.drawable.electron);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Нейтроны")){
            imageView.setBackgroundResource(R.drawable.neutron);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Период")){
            imageView.setBackgroundResource(R.drawable.group_period);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Группа")){
            imageView.setBackgroundResource(R.drawable.group_period);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Атомная масса")){
            imageView.setBackgroundResource(R.drawable.weight);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Электронная конфигурация")){
            imageView.setBackgroundResource(R.drawable.configuration);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Радиоактивность")){
            imageView.setBackgroundResource(R.drawable.radioactive);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Синтезированный элемент")){
            imageView.setBackgroundResource(R.drawable.solve);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Степени окисления")){
            imageView.setBackgroundResource(R.drawable.ion);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Ионы")){
            imageView.setBackgroundResource(R.drawable.ion);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Электроотрицательность")){
            imageView.setBackgroundResource(R.drawable.electronegativity);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Температура плавления")){
            imageView.setBackgroundResource(R.drawable.temperature);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Температура кипения")){
            imageView.setBackgroundResource(R.drawable.temperature);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Плотность")){
            imageView.setBackgroundResource(R.drawable.density);
        } else  if (groups.get(groupPosition).get(childPosition).strDescription.equals("Агрегатное состояние")) {
            if (groups.get(groupPosition).get(childPosition).strCharacteristics.equals("Жидкое")) {
                imageView.setBackgroundResource(R.drawable.liquid);
            } else if (groups.get(groupPosition).get(childPosition).strCharacteristics.equals("Газообразное")) {
                imageView.setBackgroundResource(R.drawable.gas);
            } else if (groups.get(groupPosition).get(childPosition).strCharacteristics.equals("Твёрдое")) {
                imageView.setBackgroundResource(R.drawable.solid);
            }
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
