package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chemistrysolver.R;

// класс отображающий дробь
public class FractionView extends LinearLayout {

    // алфавит и цифры для форматирования строк в дробях
    String alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZzρνχ", numbers = "1234567890";
    Context context;

    // конструктор
    public FractionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.fraction_view, this);
        this.context = context;
    }

    // установка дроби, по параметру строка, которую нужно преобразовать, и цифра, которая указывает на шрифт
    public void setFraction(@NonNull String fraction, int n, int a) {
        // числитель и знаменатель
        String nominator = "", denominator = "";
        // разделяет строку на "до знака деления" и "после знака деления"
        boolean f = true;
        for (int i = 0; i < fraction.length(); i++) {
            if (fraction.charAt(i) == '/') {
                f = false;
                i++;
            }
            if (f) {
                if (fraction.charAt(i) != ' ')
                    nominator += fraction.charAt(i);
            } else if (fraction.charAt(i) != ' ') denominator += fraction.charAt(i);
        }
        // числитель
        TextView nominatorTV = findViewById(R.id.nominator);
        nominatorTV.setText(getSsbText(nominator));
        nominatorTV.setTextSize(n);
        // знаменатель
        TextView denominatorTV = findViewById(R.id.denominator);
        denominatorTV.setText(getSsbText(denominator));
        denominatorTV.setTextSize(n);
        // для задач устанавливается определённый шрифт
        if (a == 1) {
            nominatorTV.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf"));
            denominatorTV.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf"));
        }
    }

    // форматирование строки
    private SpannableStringBuilder getSsbText(String text) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') break;
            if (alphabet.contains(String.valueOf(text.charAt(i)))) {
                for (int j = i + 1; j < text.length(); j++) {
                    if (numbers.contains(String.valueOf(text.charAt(j)))) {
                        if (text.charAt(j - 1) == '×' && text.charAt(j) == '1') j++;
                        else {
                            ssb.setSpan(new SubscriptSpan(), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            ssb.setSpan(new RelativeSizeSpan(0.75f), j, j + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else if (text.charAt(j) == 'р' || text.charAt(j) == 'я' || text.charAt(j) == '-' || text.charAt(j) == 'л'
                            || text.charAt(j) == 'а' || text.charAt(j) == 'с' || text.charAt(j) == 'м' || text.charAt(j) == 'б'
                            || text.charAt(j) == 'е' || text.charAt(j) == 'и' || text.charAt(j) == 'т' || text.charAt(j) == 'щ'
                            || text.charAt(j) == 'о' || text.charAt(j) == 'п' || text.charAt(j) == 'к') {
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
                    if (text.charAt(j) == ' ' || text.charAt(j) == '\n' || j == text.length() - 1) {
                        i = j;
                        break;
                    }
                }

            }
        }
        return ssb;
    }
}
