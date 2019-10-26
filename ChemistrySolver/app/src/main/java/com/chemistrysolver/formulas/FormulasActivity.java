package com.chemistrysolver.formulas;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chemistrysolver.R;
import com.chemistrysolver.tasksolver.FractionView;

// активность, отображающая формулы для решения задач
public class FormulasActivity extends AppCompatActivity implements View.OnClickListener{

    // поля, в которых отображена формула
    LinearLayout[] layouts;
    // названия тем, к которым относятся формулы
    TextView[] textViews;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula);

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString("Формулы");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        // заполнение
        fillFractions();
    }

    // заполнение дробей, установка возможности нажатия на поле формулы
    private void fillFractions(){

        // поля, в которых отображена формула
        layouts = new LinearLayout[20];
        layouts[0] = findViewById(R.id.molar);
        layouts[1] = findViewById(R.id.molar1);
        layouts[2] = findViewById(R.id.mass);
        layouts[3] = findViewById(R.id.mass1);
        layouts[4] = findViewById(R.id.mass2);
        layouts[5] = findViewById(R.id.mass3);
        layouts[6] = findViewById(R.id.amount);
        layouts[7] = findViewById(R.id.amount1);
        layouts[8] = findViewById(R.id.amount2);
        layouts[9] = findViewById(R.id.amount3);
        layouts[10] = findViewById(R.id.fraction);
        layouts[11] = findViewById(R.id.fraction1);
        layouts[12] = findViewById(R.id.fraction2);
        layouts[13] = findViewById(R.id.fraction3);
        layouts[14] = findViewById(R.id.volume);
        layouts[15] = findViewById(R.id.concentration);
        layouts[16] = findViewById(R.id.concentration1);
        layouts[17] = findViewById(R.id.concentration2);
        layouts[18] = findViewById(R.id.titer);
        layouts[19] = findViewById(R.id.density);

        for (int i = 0; i < layouts.length; i++) {
            layouts[i].setOnClickListener(this);
        }

        // названия тем, к которым относятся формулы
        textViews = new TextView[10];
        textViews[0] = findViewById(R.id.tv1); textViews[1] = findViewById(R.id.tv2); textViews[2] = findViewById(R.id.tv3);
        textViews[3] = findViewById(R.id.tv4); textViews[4] = findViewById(R.id.tv5); textViews[5] = findViewById(R.id.tv6);
        textViews[6] = findViewById(R.id.tv7); textViews[7] = findViewById(R.id.tv8); textViews[8] = findViewById(R.id.tv9);
        textViews[9] = findViewById(R.id.tv10);

        for (int i = 0; i < textViews.length; i++)
            textViews[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Candara.ttf"));

        // дроби, которые есть в некоторых формулвх
        FractionView fractionView1 = findViewById(R.id.fr1);
        FractionView fractionView2 = findViewById(R.id.fr2);
        FractionView fractionView3 = findViewById(R.id.fr3);
        FractionView fractionView4 = findViewById(R.id.fr4);
        FractionView fractionView5 = findViewById(R.id.fr5);
        FractionView fractionView6 = findViewById(R.id.fr6);
        FractionView fractionView7 = findViewById(R.id.fr7);
        FractionView fractionView8 = findViewById(R.id.fr8);
        FractionView fractionView9 = findViewById(R.id.fr9);
        FractionView fractionView10 = findViewById(R.id.fr10);
        FractionView fractionView11 = findViewById(R.id.fr11);
        FractionView fractionView12 = findViewById(R.id.fr12);
        FractionView fractionView13 = findViewById(R.id.fr13);
        FractionView fractionView14 = findViewById(R.id.fr14);
        FractionView fractionView15 = findViewById(R.id.fr15);
        FractionView fractionView16 = findViewById(R.id.fr16);
        FractionView fractionView17 = findViewById(R.id.fr17);

        // установка текста формул
        fractionView1.setFraction("m / M", 20, 0);
        fractionView2.setFraction("m / n", 20, 0);
        fractionView3.setFraction("N / Nₐ", 20, 0);
        fractionView4.setFraction("M × N / Nₐ", 20, 0);
        fractionView5.setFraction("k × Ar(X) / M(XₖYₗ)", 20, 0);
        fractionView6.setFraction("V / Vₐ", 20, 0);
        fractionView7.setFraction("M × V / Vₐ", 20, 0);
        fractionView8.setFraction("m(X) / mр-ра", 20, 0);
        fractionView9.setFraction("n(X) / Vр-ра", 20, 0);
        fractionView10.setFraction("nэ / mр-ра", 20, 0);
        fractionView11.setFraction("n(X) / mр-ля", 20, 0);
        fractionView12.setFraction("m(X) / Vр-ра", 20, 0);
        fractionView13.setFraction("V × p / T × R", 20, 0);
        fractionView14.setFraction("M × V × p / T × R", 20, 0);
        fractionView15.setFraction("M(Y) / M(X)", 20, 0);
        fractionView16.setFraction("V / Vсмеси", 20, 0);
        fractionView17.setFraction("Vпрак / Vтеор", 20, 0);
    }

    // диалоговое окно, отображающее название и единицы измерения химиеской величины
    private void showCustomDialog(int i) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        // вид диалогового окна
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.formula_dialog, viewGroup, false);
        // установка параметров диалогового окна
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder
                .setView(dialogView)
                .setCancelable(true)
                .create();

        // название химической величины
        TextView title = dialogView.findViewById(R.id.formulatitle);
        // единицы измерения
        TextView units = dialogView.findViewById(R.id.formulaunits);
        // надпись "Единицы измерения"
        TextView titleUnits = dialogView.findViewById(R.id.units);

        title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/gothic.ttf"));
        units.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/gothic.ttf"));
        titleUnits.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/gothic.ttf"));

        // единицы измерения
        if (i == 0 || i == 1){
            title.setText("Молярная масса");
            units.setText("г/моль");
        } else if (i == 2 || i == 3 || i == 4 || i == 5){
            title.setText("Масса");
            units.setText("г");
        } else if (i == 6 || i == 7 || i == 8 || i == 9){
            title.setText("Количество");
            units.setText("моль");
        } else if (i == 10){
            title.setText("Массовая доля элемента в веществе");
            units.setText("%");
        } else if (i == 11){
            title.setText("Массовая доля вещества в растворе");
            units.setText("%");
        } else if (i == 12){
            title.setText("Объёмная доля вещества в смеси");
            units.setText("%");
        } else if (i == 13){
            title.setText("Объёмная доля выхода продукта реакции");
            units.setText("%");
        } else if (i == 14){
            title.setText("Объём");
            units.setText("л");
        } else if (i == 15){
            title.setText("Молярная концентрация");
            units.setText("моль/л");
        } else if (i == 16){
            title.setText("Эквивалентная концентрация");
            units.setText("моль/г");
        } else if (i == 17){
            title.setText("Моляльная концентрация");
            units.setText("моль/г");
        } else if (i == 18){
            title.setText("Титр");
            units.setText("г/л");
        } else if (i == 19){
            title.setText("Относительная плотность газов");
            units.setVisibility(View.INVISIBLE);
            titleUnits.setVisibility(View.INVISIBLE);
        }

        alertDialog.show();
    }

    // меню выбора сортировки химических величин
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // меняет сортировку химических величин
        if (item.getItemId() == R.id.action_theme){
            setContentView(R.layout.activity_formula);
            fillFractions();
        }
        // меняет сортировку химических величин
        if (item.getItemId() == R.id.action_value){
            setContentView(R.layout.activity_formulas1);
            fillFractions();
        }
        // закрытие активности при нажатии стрелки "Назад"
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // показывается диалоговое окно при нажатии на поле формулы
        for (int i = 0; i < layouts.length; i++) {
            if (v.getId() == layouts[i].getId()){
                showCustomDialog(i);
            }
        }
    }

}
