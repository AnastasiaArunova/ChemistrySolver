package com.chemistrysolver.tableelement;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;

import com.chemistrysolver.R;

// активность, отображающая таблицу Менделеева
public class TableElementActivity extends AppCompatActivity implements View.OnClickListener {


    public ArrayList<Elements> elements;
    private TextView[] textViews;

    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_table_element);

        SpannableString s = new SpannableString("Периодическая система химических элементов Д.И.Менделеева");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Candara.ttf");

        // переменная измерения размеров экрана для установки размеров ячеек таблицы
        DisplayMetrics displayMetrics  = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        // массив строк таблицы
        TableRow[] tableRows = new TableRow[19];

        tableRows[0] = findViewById(R.id.groups);
        tableRows[0].setMinimumHeight(height / 33);

        tableRows[1] = findViewById(R.id.period1);
        tableRows[1].setMinimumHeight(height * 2 / 33);
        tableRows[2] = findViewById(R.id.period10);
        tableRows[2].setMinimumHeight(height / 33);

        tableRows[3] = findViewById(R.id.period2);
        tableRows[3].setMinimumHeight(height * 2 / 33);
        tableRows[4] = findViewById(R.id.period20);
        tableRows[4].setMinimumHeight(height / 33);

        tableRows[5] = findViewById(R.id.period3);
        tableRows[5].setMinimumHeight(height * 2 / 33);
        tableRows[6] = findViewById(R.id.period30);
        tableRows[6].setMinimumHeight(height / 33);

        tableRows[7] = findViewById(R.id.period4);
        tableRows[7].setMinimumHeight(height * 2 / 33);
        tableRows[8] = findViewById(R.id.period40);
        tableRows[8].setMinimumHeight(height / 33);

        tableRows[9] = findViewById(R.id.period5);
        tableRows[9].setMinimumHeight(height * 2 / 33);
        tableRows[10] = findViewById(R.id.period50);
        tableRows[10].setMinimumHeight(height / 33);

        tableRows[11] = findViewById(R.id.period6);
        tableRows[11].setMinimumHeight(height * 2 / 33);
        tableRows[12] = findViewById(R.id.period60);
        tableRows[12].setMinimumHeight(height / 33);

        tableRows[13] = findViewById(R.id.period7);
        tableRows[13].setMinimumHeight(height * 2 / 33);
        tableRows[14] = findViewById(R.id.period70);
        tableRows[14].setMinimumHeight(height / 33);

        tableRows[15] = findViewById(R.id.lantan);
        tableRows[15].setMinimumHeight(height * 2 / 33);
        tableRows[16] = findViewById(R.id.lantan1);
        tableRows[16].setMinimumHeight(height / 33);

        tableRows[17] = findViewById(R.id.actin);
        tableRows[17].setMinimumHeight(height * 2 / 33);
        tableRows[18] = findViewById(R.id.actin1);
        tableRows[18].setMinimumHeight(height / 33);

        textViews = new TextView[]{findViewById(R.id.periods), findViewById(R.id.periods1), findViewById(R.id.periods2), findViewById(R.id.periods3),
                findViewById(R.id.periods4), findViewById(R.id.periods5), findViewById(R.id.periods6), findViewById(R.id.periods7),
                findViewById(R.id.Ia), findViewById(R.id.IIa), findViewById(R.id.IIIb), findViewById(R.id.IVb), findViewById(R.id.Vb),
                findViewById(R.id.VIb), findViewById(R.id.VIIb), findViewById(R.id.VIIIb), findViewById(R.id.VIIIb1), findViewById(R.id.VIIIb2),
                findViewById(R.id.Ib), findViewById(R.id.IIb), findViewById(R.id.IIIa), findViewById(R.id.IVa), findViewById(R.id.Va),
                findViewById(R.id.VIa), findViewById(R.id.VIIa), findViewById(R.id.tv0), findViewById(R.id.wH), findViewById(R.id.wHe),
                findViewById(R.id.wLi), findViewById(R.id.wBe), findViewById(R.id.wB), findViewById(R.id.wC), findViewById(R.id.wN),
                findViewById(R.id.wO), findViewById(R.id.wF), findViewById(R.id.wNe), findViewById(R.id.wNa), findViewById(R.id.wMg),
                findViewById(R.id.wAl), findViewById(R.id.wSi), findViewById(R.id.wP), findViewById(R.id.wS), findViewById(R.id.wCl),
                findViewById(R.id.wAr), findViewById(R.id.wK), findViewById(R.id.wCa), findViewById(R.id.wSc), findViewById(R.id.wTi),
                findViewById(R.id.wV), findViewById(R.id.wCr), findViewById(R.id.wMn), findViewById(R.id.wFe), findViewById(R.id.wCo),
                findViewById(R.id.wNi), findViewById(R.id.wCu), findViewById(R.id.wZn), findViewById(R.id.wGa), findViewById(R.id.wGe),
                findViewById(R.id.wAs), findViewById(R.id.wSe), findViewById(R.id.wBr), findViewById(R.id.wKr), findViewById(R.id.wRb),
                findViewById(R.id.wSr), findViewById(R.id.wY), findViewById(R.id.wZr), findViewById(R.id.wNb), findViewById(R.id.wMo),
                findViewById(R.id.wTc), findViewById(R.id.wRu), findViewById(R.id.wRh), findViewById(R.id.wPd), findViewById(R.id.wAg),
                findViewById(R.id.wCd), findViewById(R.id.wIn), findViewById(R.id.wSn), findViewById(R.id.wSb), findViewById(R.id.wTe),
                findViewById(R.id.wI), findViewById(R.id.wXe), findViewById(R.id.wCs), findViewById(R.id.wBa),
                findViewById(R.id.wLa), findViewById(R.id.wCe), findViewById(R.id.wPr), findViewById(R.id.wNd), findViewById(R.id.wPm),
                findViewById(R.id.wSm), findViewById(R.id.wEu), findViewById(R.id.wGd), findViewById(R.id.wTb), findViewById(R.id.wDy),
                findViewById(R.id.wHo), findViewById(R.id.wEr), findViewById(R.id.wTm), findViewById(R.id.wYb), findViewById(R.id.wLu),
                findViewById(R.id.wHf), findViewById(R.id.wTa), findViewById(R.id.wW), findViewById(R.id.wRe), findViewById(R.id.wOs),
                findViewById(R.id.wIr), findViewById(R.id.wPt), findViewById(R.id.wAu), findViewById(R.id.wHg), findViewById(R.id.wTl),
                findViewById(R.id.wPb), findViewById(R.id.wBi), findViewById(R.id.wPo), findViewById(R.id.wAt), findViewById(R.id.wRn),
                findViewById(R.id.wFr), findViewById(R.id.wRa), findViewById(R.id.wAc), findViewById(R.id.wTh),
                findViewById(R.id.wPa), findViewById(R.id.wU), findViewById(R.id.wNp), findViewById(R.id.wPu), findViewById(R.id.wAm),
                findViewById(R.id.wCm), findViewById(R.id.wBk), findViewById(R.id.wCf), findViewById(R.id.wEs), findViewById(R.id.wFm),
                findViewById(R.id.wMd), findViewById(R.id.wNo), findViewById(R.id.wLr), findViewById(R.id.wRf), findViewById(R.id.wDb),
                findViewById(R.id.wSg), findViewById(R.id.wBh), findViewById(R.id.wHs), findViewById(R.id.wMt), findViewById(R.id.wDs),
                findViewById(R.id.wRg), findViewById(R.id.wCn), findViewById(R.id.wUut), findViewById(R.id.wFl), findViewById(R.id.wUup),
                findViewById(R.id.wLv), findViewById(R.id.wUus), findViewById(R.id.wUuo), findViewById(R.id.wLa1), findViewById(R.id.wAc1), findViewById(R.id.La1), findViewById(R.id.La3), findViewById(R.id.La2), findViewById(R.id.La11), findViewById(R.id.Ac1),
                findViewById(R.id.Lu1), findViewById(R.id.H), findViewById(R.id.He),
                findViewById(R.id.Li), findViewById(R.id.Be), findViewById(R.id.B), findViewById(R.id.C), findViewById(R.id.N),
                findViewById(R.id.O), findViewById(R.id.F), findViewById(R.id.Ne), findViewById(R.id.Na), findViewById(R.id.Mg),
                findViewById(R.id.Al), findViewById(R.id.Si), findViewById(R.id.P), findViewById(R.id.S), findViewById(R.id.Cl),
                findViewById(R.id.Ar), findViewById(R.id.K), findViewById(R.id.Ca), findViewById(R.id.Sc), findViewById(R.id.Ti),
                findViewById(R.id.V), findViewById(R.id.Cr), findViewById(R.id.Mn), findViewById(R.id.Fe), findViewById(R.id.Co),
                findViewById(R.id.Ni), findViewById(R.id.Cu), findViewById(R.id.Zn), findViewById(R.id.Ga), findViewById(R.id.Ge),
                findViewById(R.id.As), findViewById(R.id.Se), findViewById(R.id.Br), findViewById(R.id.Kr), findViewById(R.id.Rb),
                findViewById(R.id.Sr), findViewById(R.id.Y), findViewById(R.id.Zr), findViewById(R.id.Nb), findViewById(R.id.Mo),
                findViewById(R.id.Tc), findViewById(R.id.Ru), findViewById(R.id.Rh), findViewById(R.id.Pd), findViewById(R.id.Ag),
                findViewById(R.id.Cd), findViewById(R.id.In), findViewById(R.id.Sn), findViewById(R.id.Sb), findViewById(R.id.Te),
                findViewById(R.id.I), findViewById(R.id.Xe), findViewById(R.id.Cs), findViewById(R.id.Ba),
                findViewById(R.id.La), findViewById(R.id.Ce),
                findViewById(R.id.Pr), findViewById(R.id.Nd), findViewById(R.id.Pm), findViewById(R.id.Sm), findViewById(R.id.Eu),
                findViewById(R.id.Gd), findViewById(R.id.Tb), findViewById(R.id.Dy), findViewById(R.id.Ho), findViewById(R.id.Er),
                findViewById(R.id.Tm), findViewById(R.id.Yb), findViewById(R.id.Lu), findViewById(R.id.Hf),
                findViewById(R.id.Ta), findViewById(R.id.W), findViewById(R.id.Re), findViewById(R.id.Os), findViewById(R.id.Ir),
                findViewById(R.id.Pt), findViewById(R.id.Au), findViewById(R.id.Hg), findViewById(R.id.Tl), findViewById(R.id.Pb),
                findViewById(R.id.Bi), findViewById(R.id.Po), findViewById(R.id.At), findViewById(R.id.Rn), findViewById(R.id.Fr),
                findViewById(R.id.Ra), findViewById(R.id.Ac), findViewById(R.id.Th), findViewById(R.id.Pa),
                findViewById(R.id.U), findViewById(R.id.Np), findViewById(R.id.Pu), findViewById(R.id.Am), findViewById(R.id.Cm),
                findViewById(R.id.Bk), findViewById(R.id.Cf), findViewById(R.id.Es), findViewById(R.id.Fm), findViewById(R.id.Md),
                findViewById(R.id.No), findViewById(R.id.Lr), findViewById(R.id.Rf), findViewById(R.id.Db), findViewById(R.id.Sg),
                findViewById(R.id.Bh), findViewById(R.id.Hs), findViewById(R.id.Mt), findViewById(R.id.Ds), findViewById(R.id.Rg),
                findViewById(R.id.Cn), findViewById(R.id.Uut), findViewById(R.id.Fl), findViewById(R.id.Uup), findViewById(R.id.Lv),
                findViewById(R.id.Uus), findViewById(R.id.Uuo)};


        //Для некоторых элементов в массиве определяем ширину и размер текста
        for (int i = 0; i < textViews.length; i++) {
            if ((i > 8) && (textViews[i] != findViewById(R.id.La3)) && (textViews[i] != null)) {
                textViews[i].setWidth(width / 20);
            }
            if (i <= 145) {
                textViews[i].setTextSize(width / 110);
            } else {
                textViews[i].setTextSize(width / 60);
            }
            textViews[i].setOnClickListener(this);

        }

        fillElements();
    }

    // метод для заполнения информации об элементах
    public void fillElements(){

        elements = new ArrayList<>();

        elements.add(new Elements("H", "Водород", "Hydrogenium", 1, 1, "Ia", 1.0079,
                "1s1", "-1, 0, +1", -259.1, -252.9, 0.0000899,
                "Газообразное", 2.2, "H+, H-", " ", " "));

        elements.add(new Elements("He", "Гелий", "Helium", 2, 1, "0", 4.0026,
                "1s2", "0", -272.2, -268.94, 0.00017846,
                "Газообразное", 4.5, " ", " ", " "));

        elements.add(new Elements("Li", "Литий", "Lithium", 3, 2, "Ia", 6.941,
                "1s2 2s1", "0, +1", 180.5, 1339.85, 0.53,
                "Твёрдое", 0.98, "Li+", " ", " "));

        elements.add(new Elements("Be", "Бериллий", "Berillium", 4, 2, "IIa", 9.012,
                "1s2 2s2", "0, +1, +2", 1287.0, 2970.0, 1.85,
                "Твёрдое", 1.57, "Be2+", " ", " "));

        elements.add(new Elements("B", "Бор", "Borum", 5, 2, "IIIa", 10.811,
                "1s2 2s22p1", "-3, -1, 0, +1, +2, +3", 2075.0, 3865.0, 2.34,
                "Твёрдое", 2.04, " ", " ", " "));

        elements.add(new Elements("C", "Углерод", "Carboneum", 6, 2, "IVa", 12.012,
                "1s2 2s22p2", "-4, -3, -2, -1, 0, +1, +2, +3, +4", 3527.0, 4027.0, 2.26,
                "Твёрдое", 2.55, "C2+, C4-", " ", " "));

        elements.add(new Elements("N", "Азот", "Nitrogenium", 7, 2, "Va", 14.007,
                "1s2 2s22p3", "-3, -2, -1, 0, +1, +2, +3, +4, +5", -209.86, -195.75, 0.001251,
                "Газообразное", 3.04, "N3-", " ", " "));

        elements.add(new Elements("O", "Кислород", "Oxygenium", 8, 2, "VIa", 15.999,
                "1s2 2s22p4", "-2, -1, 0, +1, +2", -218.35, -182.96, 0.00142897,
                "Газообразное", 3.44, "O2-", " ", " "));

        elements.add(new Elements("F", "Фтор", "Flourum", 9, 2, "VIIa", 18.998,
                "1s2 2s22p5", "-1, 0", -219.7, -188.12, 0.001696,
                "Газообразное", 3.98, "F-", " ", " "));

        elements.add(new Elements("Ne", "Неон", "Neon", 10, 2, "0", 20.179,
                "1s2 2s22p6", "0", -248.6, -246.05, 0.0089999,
                "Газообразное", 4.4, " ", " ", " "));

        elements.add(new Elements("Na", "Натрий", " Natrium", 11, 3, "Ia", 22.990,
                "1s2 2s22p6 3s1", "0, +1", 97.81, 882.95, 0.971,
                "Твёрдое", 0.93, "Na+", " ", " "));

        elements.add(new Elements("Mg", "Магний", "Magnesium", 12, 3, "IIa", 24.305,
                "1s2 2s22p6 3s2", "0, +2", 650.0, 1090.0, 1.738,
                "Твёрдое", 1.31, "Mg2+", " ", " "));

        elements.add(new Elements("Al", "Алюминий", "Aluminium", 13, 3, "IIIA", 26.982,
                "1s2 2s22p6 3s23p1", "0, +3", 660.0, 2518.82, 2.6989,
                "Твёрдое", 1.61, "Al3+", " ", " "));

        elements.add(new Elements("Si", "Кремний", "Silicium", 14, 3, "IVa", 28.086,
                "1s2 2s22p6 3s23p2", "-4,  0, +2, +4", 1414.85, 2349.85,
                2.33, "Твёрдое", 1.9, "Si2+, Si4+", " ", " "));

        elements.add(new Elements("P", "Фосфор", "Phosphorus", 15, 3, "Va", 30.974,
                "1s2 2s22p6 3s23p3", "-3, -1, 0, +1, +3, +5", 44.15, 279.85,
                1.82, "Твёрдое", 2.19, "P3-", " ", " "));

        elements.add(new Elements("S", "Сера", "Sulphur", 16, 3, "VIa", 32.066,
                "1s2 2s22p6 3s23p4", "-2, -1, 0, +1, +2, +4, +6", 112.85, 444.67,
                2.07, "Твёрдое", 2.58, "S2-", " ", " "));

        elements.add(new Elements("Cl", "Хлор", "Clorum", 17, 3, "VIIa", 35.453,
                "1s2 2s22p6 3s23p5", "-1, 0, +1, +3, +4, +5, +6, +7", -100.95, -34.55,
                0.00321, "Газообразное", 3.16, "Cl-", " ", " "));

        elements.add(new Elements("Ar", "Аргон", "Argon", 18, 3, "0", 39.948,
                "1s2 2s22p6 3s23p6", "0", -189.35, -185.85, 0.001784,
                "Газообразное", 4.3, " ", " ", " "));

        elements.add(new Elements("K", "Калий", "Kalium", 19, 4, "Ia", 39.098,
                "1s2 2s22p6 3s23p6 4s1", "0, +1", 63.65, 773.85, 0.856,
                "Твёрдое", 0.82, "K+", " ", " "));

        elements.add(new Elements("Ca", "Кальций", "Calcium", 20, 4, "IIa", 40.078,
                "1s2 2s22p6 3s23p6 4s2", "0, +2", 838.85, 1483.85, 1.55,
                "Твёрдое", 1.0, "Ca2+", " ", " "));

        elements.add(new Elements("Sc", "Скандий", "Scandium", 21, 4, "IIIb", 44.956,
                "1s2 2s22p6 3s23p63d1 4s2", "0, +3", 1539.0, 2835.0, 2.99,
                "Твёрдое", 1.36, "Sc3+", " ", " "));

        elements.add(new Elements("Ti", "Титан", "Titanium", 22, 4, "IVb", 47.867,
                "1s2 2s22p6 3s23p63d2 4s2", "0, +2, +3, +4", 1670.0, 3285.0, 4.54,
                "Твёрдое", 1.54, "Ti4+, Ti3+", " ", " "));

        elements.add(new Elements("V", "Ванадий", "Vanadium", 23, 4, "Vb", 50.942,
                "1s2 2s22p6 3s23p63d3 4s2", "0, +2, +3, +4, +5", 1887.0, 3377.0,
                6.11, "Твёрдое", 1.63, "V3+, V5+", " ", " "));

        elements.add(new Elements("Cr", "Хром", "Chromium", 24, 4, "VIb", 51.996,
                "1s2 2s22p6 3s23p63d5 4s1", "0, +2, +3, +6", 1855.0, 2670.0,
                7.19, "Твёрдое", 1.66, "Cr2+, Cr3+", " ", " "));

        elements.add(new Elements("Mn", "Марганец", "Marganum", 25, 4, "VIIb", 54.939,
                "1s2 2s22p6 3s23p63d5 4s2", "0, +1, +2, +3, +4, +5, +6, +7", 1242.0, 1960.0,
                7.21, "Твёрдое", 1.55, "Mn2+, Mn4+", " ", " "));

        elements.add(new Elements("Fe", "Железо", "Ferrum", 26, 4, "VIIIb", 55.845,
                "1s2 2s22p6 3s23p63d6 4s2", "0, +2, +3, +6", 1535.85, 2861.0,
                7.874, "Твёрдое", 1.83, "Fe2+, Fe3+", " ", " "));

        elements.add(new Elements("Co", "Кобальт", "Cobaltum", 27, 4, "VIIIb", 58.933,
                "1s2 2s22p6 3s23p63d7 4s2", "-1, 0, +2, +3", 1493.0, 2868.0,
                8.9, "Твёрдое", 1.88, "Co2+, Co3+", " ", " "));

        elements.add(new Elements("Ni", "Никель", "Niccolum", 28, 4, "VIIIb", 58.693,
                "1s2 2s22p6 3s23p63d8 4s2", "0, +2, +3", 1453.0, 2732.0,
                8.902, "Твёрдое", 1.91, "Ni2+, Ni3+", " ", " "));

        elements.add(new Elements("Cu", "Медь", "Cuprum", 29, 4, "Ib", 63.564,
                "1s2 2s22p6 3s23p63d10 4s1", "0, +1, +2, +3", 1084.4, 2567.0, 8.92,
                "Твёрдое", 1.9, "Cu2+, Cu+", " ", " "));

        elements.add(new Elements("Zn", "Цинк", "Zincum", 30, 4, "IIb", 65.38,
                "1s2 2s22p6 3s23p63d10 4s2", "0, +2", 419.6, 906.2, 7.133,
                "Твёрдое", 1.65, "Zn2+", " ", " "));

        elements.add(new Elements("Ga", "Галлий", "Gallium", 31, 4, "IIIa", 69.723,
                "1s2 2s22p6 3s23p63d10 4s24p1", "0, +3", 29.8, 2204.0, 5.91,
                "Твёрдое", 1.81, "Ga3+", " ", " "));

        elements.add(new Elements("Ge", "Германий", "Germanium", 32, 4, "IVa", 72.63,
                "1s2 2s22p6 3s23p63d10 4s24p2", "0, +2, +4", 935.6, 2828.0,
                5.323, "Твёрдое", 2.01, "Ge4+", " ", " "));

        elements.add(new Elements("As", "Мышьяк", "Arsenium", 33, 4, "Va", 74.922,
                "1s2 2s22p6 3s23p63d10 4s24p3", "-3, 0, +3, +5", 817.0, 613.0, 5.73,
                "Твёрдое", 2.18, "As3-", " ", " "));

        elements.add(new Elements("Se", "Селен", "Selenium", 34, 4, "VIa", 78.96,
                "1s2 2s22p6 3s23p63d10 4s24p4", "-2, 0, +4, +6", 251.0, 683.0, 4.79,
                "Твёрдое", 2.55, "Se2-", " ", " "));

        elements.add(new Elements("Br", "Бром", "Bromum", 35, 4, "VIIa", 79.904,
                "1s2 2s22p6 3s23p63d10 4s24p5", "-1, 0, +1, +3, +5, +7", -7.25, 58.6,
                3.102, "Жидкое", 2.96, "Br-", " ", " "));

        elements.add(new Elements("Kr", "Криптон", "Krypton", 36, 4, "0", 83.798,
                "1s2 2s22p6 3s23p63d10 4s24p6", "0, +2", -157.37, -153.415, 0.003749,
                "Газообразное", 3.0, " ", " ", " "));

        elements.add(new Elements("Rb", "Рубидий", "Rubidium", 37, 5, "Ia", 85.468,
                "1s2 2s22p6 3s23p63d10 4s24p6 5s1", "0, +1", 39.05, 688.0, 1.532,
                "Твёрдое", 0.82, "Rb+", " ", " "));

        elements.add(new Elements("Sr", "Стронций", "Strontium", 38, 5, "IIa", 87.62,
                "1s2 2s22p6 3s23p63d10 4s24p6 5s2", "0, +2", 777.0, 1382.0, 2.54,
                "Твёрдое", 0.95, "Sr2+", " ", " "));

        elements.add(new Elements("Y", "Иттрий", "Yttrium", 39, 5, "IIIb", 88.906,
                "1s2 2s22p6 3s23p63d10 4s24p64d1 5s2", "0, +3", 1523.0, 3337.0,
                4.47, "Твёрдое", 1.22, "Y3+", " ", " "));

        elements.add(new Elements("Zr", "Цирконий", "Zirconium", 40, 5, "IVb", 91.224,
                "1s2 2s22p6 3s23p63d10 4s24p64d2 5s2", "0, +1, +2, +3, +4", 1855.0, 4477.0,
                6.52, "Твёрдое", 1.33, "Zr4+", " ", " "));

        elements.add(new Elements("Nb", "Ниобий", "Niobium", 41, 5, "Vb", 92.906,
                "1s2 2s22p6 3s23p63d10 4s24p64d4 5s1", "0, +1, +2, +3, +4 +5", 2468.0, 4742.0,
                8.57, "Твёрдое", 1.6, "Nb3+, Nb5+", " ", " "));

        elements.add(new Elements("Mo", "Молибден", "Molibdaenum", 42, 5, "VIb", 95.96,
                "1s2 2s22p6 3s23p63d10 4s24p64d5 5s1", "+2, +3, +4, +5, +6", 2623.0,
                4639.0, 10.22, "Твёрдое", 2.16, "Mo6+", " ", " "));

        elements.add(new Elements("Tc", "Технеций", "Technetium", 43, 5, "VIIb", 98.906,
                "1s2 2s22p6 3s23p63d10 4s24p64d5 5s2", "-1, 0, +1, +2, +3, +4, +5, +6, +7 ", 2157.0,
                4265.0, 11.5, "Твёрдое", 1.9, "Te7+", " ", "Искуственно созданный элемент"));

        elements.add(new Elements("Ru", "Рутений", "Ruthenium", 44, 5, "VIIIb", 101.07,
                "1s2 2s22p6 3s23p63d10 4s24p64d7 5s1", "0, +3, +4, +6, +8", 2334.0, 4077.0,
                12.41, "Твёрдое", 2.2, "Ru3+, Ru4+", " ", " "));

        elements.add(new Elements("Rh", "Родий", "Rhodium", 45, 5, "VIIIb", 102.906,
                "1s2 2s22p6 3s23p63d10 4s24p64d8 5s1", "0, +1, +2, +3, +4, +5", 1963.0,
                3727.0, 12.41, "Твёрдое", 2.28, "Rh3+", " ", " "));

        elements.add(new Elements("Pd", "Палладий", "Palladium", 46, 5, "VIIIb", 106.42,
                "1s2 2s22p6 3s23p63d10 4s24p64d10", "0, +1, +2, +3, +4, +5, +6", 1554.0, 2666.85,
                12.02, "Твёрдое", 2.2, "Pd2+, Pd4+", " ", " "));

        elements.add(new Elements("Ag", "Серебро", "Argentum", 47, 5, "Ib", 107.868,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s1", "0, +1, +2", 962.0, 2162.0,
                10.5, "Твёрдое", 1.93, "Ag+", " ", " "));

        elements.add(new Elements("Cd", "Кадмий", "Cadmium ", 48, 5, "IIb", 112.41,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s2", "0, +2", 320.95, 764.85,
                8.65, "Твёрдое", 1.69, "Cd2+", " ", " "));

        elements.add(new Elements("In", "Индий", "Indium", 49, 5, "IIIa", 114.82,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s25p1", "0, +1, +3", 156.6, 2072.0,
                7.31, "Твёрдое", 1.78, "In3+", " ", " "));

        elements.add(new Elements("Sn", "Олово", "Stannum", 50, 5, "IVa", 118.71,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s25p2", "0, +2, +4", 231.91, 2602.0,
                7.31, "Твёрдое", 1.96, "Sn2+, Sn4+", " ", " "));

        elements.add(new Elements("Sb", "Сурьма", "Stibium", 51, 5, "Va", 121.76,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s25p3", "-3, 0, +3, +5", 630.75, 1634.85,
                6.691, "Твёрдое", 2.05, "Sb3+, Sb5+", " ", " "));

        elements.add(new Elements("Te", "Теллур", "Tellurium", 52, 5, "VIa", 127.60,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s25p4", "-2, 0, +2, +4, +6", 449.55, 989.85,
                6.24, "Твёрдое", 2.1, "Te2-", " ", " "));

        elements.add(new Elements("I", "Йод", "Iodum", 53, 5, "VIIa", 126.9,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s25p5", "-1, 0, +1, +3, +5, +7", 113.5, 184.35,
                4.93, "Твёрдое", 2.66, "I-", " ", " "));

        elements.add(new Elements("Xe", "Ксенон", "Xenon", 54, 5, "0", 131.29,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s25p6", "0, +1, +2, +4, +6, +8", -111.85, -107.05,
                0.005894, "Газообразное", 2.6, " ", " ", " "));

        elements.add(new Elements("Cs", "Цезий", "Caesium", 55, 6, "Ia", 132.91,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s25p6 6s1", "0, +1", 28.44, 669.2,
                1.873, "Твёрдое", 0.79, "Cs+", " ", " "));

        elements.add(new Elements("Ba", "Барий", "Barium", 56, 6, "IIa", 137.33,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s25p6 6s2", "0, +2", 728.85, 1636.85,
                3.5, "Твёрдое", 0.89, "  Ba2+", " ", " "));

        elements.add(new Elements("La", "Лантан", "Lanthanum", 57, 6, " ", 138.91,
                "1s2 2s22p6 3s23p63d10 4s24p64d10 5s25p65d1 6s2", "0, +3", 919.85, 3173.85,
                6.18, "Твёрдое", 1.1, "  La3+", " ", " "));

        elements.add(new Elements("Ce", "Церий", "Cerium ", 58, 6, " ", 140.12,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f2 5s25p6 6s2", "0, +3, +4", 798.85, 3425.85,
                6.757, "Твёрдое", 1.12, "Ce3+", " ", " "));

        elements.add(new Elements("Pr", "Празеодим", "Praseodymium", 59, 6, " ", 140.91,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f3 5s25p6 6s2", "0, +3, +4", 930.85, 3511.85,
                6.773, "Твёрдое", 1.13, "Pr3+", " ", " "));

        elements.add(new Elements("Nd", "Неодим", "Neodymium", 60, 6, " ", 144.24,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f4 5s25p6 6s2", "0, +2, +3", 1020.85, 3067.85,
                7.007, "Твёрдое", 1.14, "Nd2+, Nd3+", " ", " "));

        elements.add(new Elements("Pm", "Прометий", "Promethium", 61, 6, " ", 145.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f5 5s25p6 6s2", "0, +3", 1167.85, 2999.85,
                7.26, "Твёрдое", 1.1, "  Pm3+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Sm", "Самарий", "Promethium", 62, 6, " ", 150.36,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f6 5s25p6 6s2", "0, +2, +3", 1076.85, 1790.85,
                7.52, "Твёрдое", 1.17, "Sm2+, Sm3+", " ", " "));

        elements.add(new Elements("Eu", "Европий", "Europium", 63, 6, " ", 151.96,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f7 5s25p6 6s2", "0, +2, +3", 826.0, 1529.0,
                5.243, "Твёрдое", 1.2, "Eu2+, Eu3+", " ", " "));

        elements.add(new Elements("Gd", "Гадолиний", "Gadolinium", 64, 6, " ", 157.25,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f7 5s25p65d1 6s2", "0, +3", 1312.85, 3265.85,
                7.9, "Твёрдое", 1.2, "  Gd3+", " ", " "));

        elements.add(new Elements("Tb", "Тербий", "Terbium", 65, 6, " ", 158.93,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f9 5s25p6 6s2", "0, +3, +4", 1355.85, 3022.85,
                8.229, "Твёрдое", 1.2, "Tb3+", " ", " "));

        elements.add(new Elements("Dy", "Диспрозий", "Dysprosium", 66, 6, " ", 162.5,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f10 5s25p6 6s2", "0, +2, +3", 1411.85, 2561.85,
                8.55, "Твёрдое", 1.2, "Dy2+, Dy3+", " ", " "));

        elements.add(new Elements("Ho", "Гольмий", "Holmium", 67, 6, " ", 164.93,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f11 5s25p6 6s2", "0, +3", 1473.85, 2694.85,
                8.795, "Твёрдое", 1.23, "Ho3+", " ", " "));

        elements.add(new Elements("Er", "Эрбий", "Erbium ", 68, 6, " ", 167.26,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f12 5s25p6 6s2", "0, +3", 1528.85, 2862.85,
                9.06, "Твёрдое", 1.24, "Er3+", " ", " "));

        elements.add(new Elements("Tm", "Тулий", "Thulium", 69, 6, " ", 168.93,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f13 5s25p6 6s2", "0, +2, +3", 1544.85, 1946.85,
                9.321, "Твёрдое", 1.25, "Tm2+, Tm3+", " ", " "));

        elements.add(new Elements("Yb", "Иттербий", "Ytterbium", 70, 6, " ", 173.05,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p6 6s2", "0, +2, +3", 823.85, 1192.85,
                6.9654, "Твёрдое", 1.1, "Yb3+, Yb2+", " ", " "));

        elements.add(new Elements("Lu", "Лютеций", "Lutetium", 71, 6, " ", 174.97,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d1 6s2", "0, +3", 1662.85, 3394.85,
                9.8404, "Твёрдое", 1.27, "Lu3+", " ", " "));

        elements.add(new Elements("Hf", "Гафний", "Hafnium", 72, 6, "IVb", 178.49,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d2 6s2", "0, +2, +3, +4", 2233.0, 4603.0,
                13.31, "Твёрдое", 1.3, "Hf4+", " ", " "));

        elements.add(new Elements("Ta", "Тантал", "Tantalum", 73, 6, "Vb", 180.95,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d3 6s2", "0, +5", 3017.0, 5458.0,
                16.65, "Твёрдое", 1.5, "Ta5+", " ", " "));

        elements.add(new Elements("W", "Вольфрам", "Wolframium", 74, 6, "VIb", 183.84,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d4 6s2", "0, +2, +3, +4, +5, +6", 3422.0, 5555.0,
                19.25, "Твёрдое", 2.3, "W3+, W6+", " ", " "));

        elements.add(new Elements("Re", "Рений", "Rhenium", 75, 6, "VIIb", 186.21,
                "1s2 2s22p6 3s23p63d10 4s24p46d104f14 5s25p65d5 6s2", "-1, 0, +2, +3, +4, +5, +6, +7", 3186.0, 5596.0,
                21.02, "Твёрдое", 1.9, "Re3+", " ", " "));

        elements.add(new Elements("Os", "Осмий", "Osmium", 76, 6, "VIIIb", 190.23,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d6 6s2", "0, +2, +3, +4, +6, +8", 3033.0, 5012.0,
                22.59, "Твёрдое", 2.2, "Os4+", " ", " "));

        elements.add(new Elements("Ir", "Иридий", "Iridium", 77, 6, "VIIIb", 192.22,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d7 6s2", "-3, -1, 0, +1, +2, +3, +4, +6, +7, +8, +9", 2466.0, 4428.0,
                22.65, "Твёрдое", 2.2, "Ir3+", " ", " "));

        elements.add(new Elements("Pt", "Платина", "Platinum", 78, 6, "VIIIb", 195.08,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d9 6s1", "0, +2, +4", 1768.3, 3825.0,
                21.5, "Твёрдое", 2.28, "Pt2+", " ", " "));

        elements.add(new Elements("Au", "Золото", "Aurum", 79, 6, "Ib", 196.97,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s1", "-1, 0, +1, +3, +5", 1064.18, 2856.0,
                19.3, "Твёрдое", 2.54, "Au3+, Au+", " ", " "));

        elements.add(new Elements("Hg", "Ртуть", "Hydrargyrum", 80, 6, "IIb", 200.59,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s2", "0, +1, +2", -38.83, 356.73,
                13.546, "Жидкое", 2.0, "Hg2+", " ", " "));

        elements.add(new Elements("Tl", "Таллий", "Thallium", 81, 6, "IIIa", 204.38,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p1", "0, +1, +3", 304.0, 1473.0,
                11.8, "Твёрдое", 1.62, "Tl+, Tl3+", " ", " "));

        elements.add(new Elements("Pb", "Свинец", "Plumbum", 82, 6, "IVa", 207.2,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p2", "0, +2, +4", 327.46, 1749.0,
                11.3, "Твёрдое", 2.33, "Pb2+, Pb4+", " ", " "));

        elements.add(new Elements("Bi", "Висмут", "Bismuthum", 83, 6, "Va", 208.98,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p3", "0, +3, +5", 271.44, 1564.0,
                9.79, "Твёрдое", 2.02, "Bi3+", " ", " "));

        elements.add(new Elements("Po", "Полоний", "Polonium", 84, 6, "VIa", 209.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p4", "-2, 0, +2, +4, +6", 254.0, 962.0,
                9.2, "Твёрдое", 2.0, "  Po2+", "Радиоактивный элемент", " "));

        elements.add(new Elements("At", "Астат", "Astatium", 85, 6, "VIIa", 210.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p5", "-1, 0, +1, +3, +5, +7", 230.0, 302.0,
                6.4, "Твёрдое", 2.5, "At-", "Радиоактивный элемент", " "));

        elements.add(new Elements("Rn", "Радон", "Radon", 86, 6, "0", 222.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p6", "0, +2, +4, +6, +8", -71.15, -61.75,
                9.81, "Твёрдое", 0.0, " ", "Радиоактивный элемент", " "));

        elements.add(new Elements("Fr", "Франций", "Francium", 87, 7, "Ia", 223.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p6 7s1", "0, +1", 19.5, 650.0,
                2.4, "Твёрдое", 0.7, "Fr+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Ra", "Радий", "Radium", 88, 7, "IIa", 226.02,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p6 7s2", "0, +2", 700.0, 1140.0,
                5.5, "Твёрдое", 0.9, "Rn2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Ac", "Актиний", "Actinium", 89, 7, " ", 227.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p66d1 7s2", "0, +3", 1047.0, 3197.0,
                10.07, "Твёрдое", 1.1, "Ac3+, Ac2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Th", "Торий", "Thorium", 90, 7, " ", 232.04,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d10 6s26p66d2 7s2", "0, +2, +3, +4", 1750.0, 4787.0,
                11.72, "Твёрдое", 1.3, "Th4+, Th2+", "Радиоактивный элемент", " "));

        elements.add(new Elements("Pa", "Протактиний", "Protactinium", 91, 7, " ", 231.04,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f2 6s26p66d1 7s2", "0, +3, +4, +5", 1554.0, 4030.0,
                15.37, "Твёрдое", 1.5, "Pr5+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("U", "Уран", "Uranium", 92, 7, " ", 238.03,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f3 6s26p66d1 7s2", "0, +2, +3, +4, +5, +6", 1132.4, 3818.0,
                19.05, "Твёрдое", 1.38, "U2+, U3+, U4+", "Радиоактивный элемент", " "));

        elements.add(new Elements("Np", "Нептуний", "Neptunium", 93, 7, " ", 237.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f4 6s26p66d1 7s2", "0, +3, +4, +5, +6, +7", 644.0, 3904.0,
                20.25, "Твёрдое", 1.36, "Np4+, Np3+, Np2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Pu", "Плутоний", "Plutonium", 94, 7, " ", 244.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f6 6s26p6 7s2", "0, +2, +3, +4, +5, +6, +7", 639.7, 3235.0,
                19.84, "Твёрдое", 1.28, "Pu3+, Pu4+, Pu5+, Pu6+", "Радиоактивный элемент", " "));

        elements.add(new Elements("Am", "Америций", "Americium", 95, 7, " ", 243.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f7 6s26p6 7s2", "0, +2, +3, +4, +5, +6", 1176.0, 2607.0,
                13.67, "Твёрдое", 1.3, "Am4+, Am3+, Am2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Cm", "Кюрий", "Curium", 96, 7, " ", 247.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f7 6s26p6d1 7s2", "0, +3, +4", 1340.0, 3109.85,
                13.51, "Твёрдое", 1.3, "Cm3+, Cm2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Bk", "Берклий", "Berkelium", 97, 7, " ", 247.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f9 6s26p6 7s2", "0, +2 +3, +4", 986.0, 2627.0,
                13.25, "Твёрдое", 1.3, "Bk4+, Bk3+, Bk2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Cf", "Калифорний", "Californium", 98, 7, " ", 251.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f10 6s26p1 7s2", "0, +2, +3, +4", 900.0, 1470.0,
                15.1, "Твёрдое", 1.3, "Cf3+, Cf2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Es", "Ейнштейний", "Einsteinium", 99, 7, " ", 252.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f11 6s26p6 7s2", "0, +2, +3, +4", 860.0, -1000.0,
                13.5, "Твёрдое", 1.3, "Es3+, Es2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Fm", "Фермий", "Fermium", 100, 7, " ", 257.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f12 6s26p6 7s2", "0, +2, +3", 1527.0, -1000.0,
                0.0, "Твёрдое", 1.3, "Fm3+, Fm2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Md", "Менделевий", "Mendelevium", 101, 7, " ", 258.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f13 6s26p6 7s2", "0, +2, +3", 827.0, -1000.0,
                0.0, "Твёрдое", 1.3, "Md3+, Md2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("No", "Нобелий", "Nobelium", 102, 7, " ", 259.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p6 7s2", "0, +2, +3", 827.0, -1000.0,
                0.0, "Твёрдое", 1.3, "No3+, No2+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Lr", "Лоуренсий", "Lawrencium", 103, 7, " ", 262.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p6 7s27p1", "0, +3", 1627.0, -1000.0,
                0.0, "Твёрдое", 1.3, "Lr3+", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Rf", "Резерфордий", "Rutherfordium", 104, 7, "IVb", 261.11,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d2 7s2", "0, +4", -1000.0, -1000.0,
                23.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Db", "Дубний", "Dubnium", 105, 7, "Vb", 268.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d3 7s2", "0, +3, +4, +5", -1000.0, -1000.0,
                21.6, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Sg", "Сиборгий", "Seaborgium", 106, 7, "VIb", 271.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d4 7s2", "0", -1000.0, -1000.0,
                35.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Bh", "Борий", "Bohrium", 107, 7, "VIIb", 270.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d5 7s2", "0", -1000.0, -1000.0,
                37.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Hs", "Гасий", "Hassium", 108, 7, "VIIIb", 269.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d6 7s2", "0", -1000.0, -1000.0,
                0.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Mt", "Мейтнерий", "Meitnerium", 109, 7, "VIIIb", 278.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d7 7s2", "0, +1, +3, +4, +6, +8, +9", -1000.0, -1000.0,
                37.4, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Ds", "Драмштадтий", "Darmstadtium", 110, 7, "VIIIb", 281.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d8 7s2", "0", -1000.0, -1000.0,
                0.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Rg", "Рентгений", "Roentgenium", 111, 7, "Ib", 281.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d10 7s1", "0", -1000.0, -1000.0,
                0.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Cn", "Коперниций", "Copernicium", 112, 7, "IIb", 285.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d10 7s2", "0", -1000.0, -1000.0,
                0.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Uut", "Унунтрий", "Ununtrium", 113, 7, "IIIa", 286.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d10 7s27p1", "0", 427.0, 1157.0,
                16.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Fl", "Флеровий", "Flerovium", 114, 7, "IVa", 289.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d10 7s27p2", "0", -1000.0, -1000.0,
                0.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Uup", "Унунпентий", "Ununpentium", 115, 7, "Va", 289.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d10 7s27p3", "0", -1000.0, -1000.0,
                0.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Lv", "Ливерморий", "Livermorium", 116, 7, "VIa", 293.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d10 7s27p4", "0", -1000.0, -1000.0,
                0.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Uus", "Унунсептий", "Ununseptium", 117, 7, "VIIa", 294.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d10 7s27p5", "0", -1000.0, -1000.0,
                0.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

        elements.add(new Elements("Uuo", "Унуноктий", "Ununoctium", 118, 7, "0", 294.0,
                "1s2 2s22p6 3s23p63d10 4s24p64d104f14 5s25p65d105f14 6s26p66d10 7s27p6", "−1, 0, +1, +2, +4, +6", -1000.0, 95.0,
                5.0, " ", 0.0, " ", "Радиоактивный элемент", "Искуственно созданный элемент"));

    }

    @Override
    public void onClick(View v) {
        //Запускаем цикл, в котором ищем, на какую textView нажали
        for (int i = 26; i < 144; i++) {
            /*Открываем новую активность, передавая в неё индекс textView, на которую нажали, чтобы потом правильно
                отобразить информацию об элементе*/
            if (v.getId() == textViews[i].getId()){
                Intent intent = new Intent(this, ElementActivity.class);
                intent.putExtra("INDEX", i - 26);
                startActivity(intent);
            }
        }
        for (int i = 152; i < textViews.length; i++) {
            if (v.getId() == textViews[i].getId()) {
                /*Открываем новую активность, передавая в неё индекс textView, на которую нажали, чтобы потом правильно
                отобразить информацию об элементе*/
                Intent intent = new Intent(this, ElementActivity.class);
                intent.putExtra("INDEX", i - 152);
                startActivity(intent);
            }
        }
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
