package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chemistrysolver.R;

import java.util.ArrayList;

// адаптер для красивого отображения ответа
public class AnswerAdapter extends ArrayAdapter<String> {

    private Context context;
    // XML-разметка, в которой отображается пункт решения
    private int resource;
    // решения
    private ArrayList<ArrayList<String>> solutions;

    // конструктор адаптера
    public AnswerAdapter(@NonNull Context context, int resource, ArrayList<ArrayList<String>> solutions) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.solutions = solutions;
    }

    @Override
    public int getCount() {
        return solutions.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int j, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resource, null);

        // заголовок пункта решения
        TextView step = view.findViewById(R.id.step);
        step.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf"));
        step.setText("ШАГ " + (j + 1));

        // текстовое поле, в котором отображается объяснение
        TextView paragraphText = view.findViewById(R.id.paragraphtext);
        paragraphText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf"));

        // дробь, отображающая формулу
        FractionView paragraphFraction = view.findViewById(R.id.paragraphfraction);

        TextView ansText = view.findViewById(R.id.anstext);
        ansText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf"));

        // дробь, отображающая соединения, подставленные в формулу
        FractionView ansFraction = view.findViewById(R.id.ansfraction);

        // текстовое поле, отображающее, то что стоит между двумя дробями
        TextView ansText1 = view.findViewById(R.id.anstext1);
        ansText1.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf"));

        // дробь, отображающая числа
        FractionView ansFraction1 = view.findViewById(R.id.ansfraction1);

        // текстовое поле, отображающее ответ и единицы измерения данного действия
        TextView ansText2 = view.findViewById(R.id.anstext2);
        ansText2.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf"));

        // установка текста и дробей
        if (j < getCount())
        if (solutions.get(j).get(0).equals("")) paragraphText.setVisibility(View.INVISIBLE);
        else paragraphText.setText(getSsbText(solutions.get(j).get(0), 0));
        if (solutions.get(j).get(1).equals("")) paragraphFraction.setVisibility(View.INVISIBLE);
        else paragraphFraction.setFraction(solutions.get(j).get(1), 15, 1);
        if (solutions.get(j).get(2).equals("")) ansText.setVisibility(View.INVISIBLE);
        else ansText.setText(getSsbText(solutions.get(j).get(2), 0));
        if (solutions.get(j).get(3).equals("")) ansFraction.setVisibility(View.INVISIBLE);
        else ansFraction.setFraction(solutions.get(j).get(3), 15, 1);
        if (solutions.get(j).get(4).equals("")) ansText1.setVisibility(View.INVISIBLE);
        else ansText1.setText(getSsbText(solutions.get(j).get(4), 0));
        try {
            if (solutions.get(j).get(5).equals("")) ansFraction1.setVisibility(View.INVISIBLE);
            else ansFraction1.setFraction(solutions.get(j).get(5), 15, 1);
            if (solutions.get(j).get(6).equals("")) ansText2.setVisibility(View.INVISIBLE);
            else ansText2.setText(getSsbText(solutions.get(j).get(6), 0));
        } catch (Exception e){}

        return view;
    }

    // преобразование индексов в соединении, формулах
    public SpannableStringBuilder getSsbText(String text, int b){
        String alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz", numbers = "1234567890", gas = "возд";
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        int k = 0;
        if (text.length() > 0 && text.charAt(0) == 'D'){
            for (int i = 1; i < text.length(); i++) {
                if (text.charAt(i) == '('){
                    k = i;
                    break;
                }
                if (numbers.contains(String.valueOf(text.charAt(i)))){
                    ssb.setSpan(new SubscriptSpan(), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.setSpan(new RelativeSizeSpan(0.5f), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.setSpan(new SubscriptSpan(), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (alphabet.contains(String.valueOf(text.charAt(i))) || gas.contains(String.valueOf(text.charAt(i)))){
                    ssb.setSpan(new SubscriptSpan(), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.setSpan(new RelativeSizeSpan(0.75f), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        for (int i = k; i < text.length(); i++) {
            if (text.charAt(i) == '\n' && b == 0) break;
            if (text.charAt(i) == '('){
                for (int j = i + 1; j < text.length(); j++) {
                    if (numbers.contains(String.valueOf(text.charAt(j)))){
                        ssb.setSpan(new SubscriptSpan(), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ssb.setSpan(new RelativeSizeSpan(0.75f), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (text.charAt(j) == ' ' || text.charAt(j) == '\n' || i == text.length() - 1){
                        i = j;
                        break;
                    }
                }
            } else if (alphabet.contains(String.valueOf(text.charAt(i))) || text.charAt(i) == 'ρ' || text.charAt(i) == 'ν' || text.charAt(i) == 'χ'){
                for (int j = i + 1; j < text.length(); j++) {
                    if (numbers.contains(String.valueOf(text.charAt(j)))){
                        if (text.charAt(j - 1) == '×' && text.charAt(j) == '1') j++;
                        else {
                            ssb.setSpan(new SubscriptSpan(), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            ssb.setSpan(new RelativeSizeSpan(0.75f), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else if (text.charAt(j) == 'р'|| text.charAt(j) == 'я' || text.charAt(j) == '-' || text.charAt(j) == 'л'
                            || text.charAt(j) == 'а' || text.charAt(j) == 'с' || text.charAt(j) == 'м' || text.charAt(j) == 'б'
                            || text.charAt(j) == 'е' || text.charAt(j) == 'и' || text.charAt(j) == 'т' || text.charAt(j) == 'щ'
                            || text.charAt(j) == 'о' || text.charAt(j) == 'п' || text.charAt(j) == 'к'){
                        if (i + 1 < text.length()){
                            if (text.charAt(i + 1) != 'з'){
                                ssb.setSpan(new SubscriptSpan(), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                ssb.setSpan(new RelativeSizeSpan(0.75f), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        } else {
                            ssb.setSpan(new SubscriptSpan(), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            ssb.setSpan(new RelativeSizeSpan(0.75f), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                    if (text.charAt(j) == ' ' || text.charAt(j) == '\n' || j == text.length() - 1){
                        i = j;
                        break;
                    }
                }

            }
        }
        return ssb;
    }

}
