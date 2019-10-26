package com.chemistrysolver.tablesolubility;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chemistrysolver.R;

// активность, отображающая таблицу растворимости или таблицу индикаторов
public class TableSolubilityActivity extends AppCompatActivity implements View.OnClickListener {

    /* Массивы значений растворимости из таблицы, катионов, анионов, информации о растворимости,
    названий катионов и анионов, названий соединений */
    private TextView[] textViewsValues, textViewsKations, textViewsAnions, textViewsInformation;
    // названия анионов
    private String[] anions;
    // названия катионов
    private String[] kations;
    // названия соединений
    private String[] compound;

    private Typeface typeface;

    //Длина и ширина экрана
    int width, height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_table_solubility);

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString("Таблица растворимости некоторых веществ в воде");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Candara.ttf");

        //Для измерения экрана создаём экземпляр класса DisplayMetrics
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //Даём значения ширине и длине экрана
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        fillTextViews();

    }

    // метод, заполняющий массивы с информацией
    private void fillTextViews(){

        textViewsValues = new TextView[]{findViewById(R.id.value1), findViewById(R.id.value2), findViewById(R.id.value3), findViewById(R.id.value4), findViewById(R.id.value5), findViewById(R.id.value6), findViewById(R.id.value7), findViewById(R.id.value8),
                findViewById(R.id.value9), findViewById(R.id.value10), findViewById(R.id.value11), findViewById(R.id.value12), findViewById(R.id.value13), findViewById(R.id.value14), findViewById(R.id.value15), findViewById(R.id.value16), findViewById(R.id.value17),
                findViewById(R.id.value18), findViewById(R.id.value19), findViewById(R.id.value20), findViewById(R.id.value21), findViewById(R.id.value22), findViewById(R.id.value23), findViewById(R.id.value24), findViewById(R.id.value25), findViewById(R.id.value26),
                findViewById(R.id.value27), findViewById(R.id.value28), findViewById(R.id.value29), findViewById(R.id.value30), findViewById(R.id.value31), findViewById(R.id.value32), findViewById(R.id.value33), findViewById(R.id.value34), findViewById(R.id.value35),
                findViewById(R.id.value36), findViewById(R.id.value37), findViewById(R.id.value38), findViewById(R.id.value39), findViewById(R.id.value40), findViewById(R.id.value41), findViewById(R.id.value42), findViewById(R.id.value43), findViewById(R.id.value44),
                findViewById(R.id.value45), findViewById(R.id.value46), findViewById(R.id.value47), findViewById(R.id.value48), findViewById(R.id.value49), findViewById(R.id.value50), findViewById(R.id.value51), findViewById(R.id.value52), findViewById(R.id.value53),
                findViewById(R.id.value54), findViewById(R.id.value55), findViewById(R.id.value56), findViewById(R.id.value57), findViewById(R.id.value58), findViewById(R.id.value59), findViewById(R.id.value60), findViewById(R.id.value61), findViewById(R.id.value62),
                findViewById(R.id.value63), findViewById(R.id.value64), findViewById(R.id.value65), findViewById(R.id.value66), findViewById(R.id.value67), findViewById(R.id.value68), findViewById(R.id.value69), findViewById(R.id.value70), findViewById(R.id.value71),
                findViewById(R.id.value72), findViewById(R.id.value73), findViewById(R.id.value74), findViewById(R.id.value75), findViewById(R.id.value76), findViewById(R.id.value77), findViewById(R.id.value78), findViewById(R.id.value79), findViewById(R.id.value80),
                findViewById(R.id.value81), findViewById(R.id.value82), findViewById(R.id.value83), findViewById(R.id.value84), findViewById(R.id.value85), findViewById(R.id.value86), findViewById(R.id.value87), findViewById(R.id.value88), findViewById(R.id.value89),
                findViewById(R.id.value90), findViewById(R.id.value91), findViewById(R.id.value92), findViewById(R.id.value93), findViewById(R.id.value94), findViewById(R.id.value95), findViewById(R.id.value96), findViewById(R.id.value97), findViewById(R.id.value98),
                findViewById(R.id.value99), findViewById(R.id.value100), findViewById(R.id.value101), findViewById(R.id.value102), findViewById(R.id.value103), findViewById(R.id.value104), findViewById(R.id.value105), findViewById(R.id.value106), findViewById(R.id.value107),
                findViewById(R.id.value108), findViewById(R.id.value109), findViewById(R.id.value110), findViewById(R.id.value111), findViewById(R.id.value112), findViewById(R.id.value113), findViewById(R.id.value114), findViewById(R.id.value115), findViewById(R.id.value116),
                findViewById(R.id.value117), findViewById(R.id.value118), findViewById(R.id.value119), findViewById(R.id.value120), findViewById(R.id.value121), findViewById(R.id.value122), findViewById(R.id.value123), findViewById(R.id.value124), findViewById(R.id.value125),
                findViewById(R.id.value126), findViewById(R.id.value127), findViewById(R.id.value128), findViewById(R.id.value129), findViewById(R.id.value130), findViewById(R.id.value131), findViewById(R.id.value132), findViewById(R.id.value133), findViewById(R.id.value134),
                findViewById(R.id.value135), findViewById(R.id.value136), findViewById(R.id.value137), findViewById(R.id.value138), findViewById(R.id.value139), findViewById(R.id.value140), findViewById(R.id.value141), findViewById(R.id.value142), findViewById(R.id.value143),
                findViewById(R.id.value144), findViewById(R.id.value145), findViewById(R.id.value146), findViewById(R.id.value147), findViewById(R.id.value148), findViewById(R.id.value149), findViewById(R.id.value150), findViewById(R.id.value151), findViewById(R.id.value152),
                findViewById(R.id.value153), findViewById(R.id.value154), findViewById(R.id.value155), findViewById(R.id.value156), findViewById(R.id.value157), findViewById(R.id.value158), findViewById(R.id.value159), findViewById(R.id.value160), findViewById(R.id.value161),
                findViewById(R.id.value162), findViewById(R.id.value163), findViewById(R.id.value164), findViewById(R.id.value165), findViewById(R.id.value166), findViewById(R.id.value167), findViewById(R.id.value168), findViewById(R.id.value169), findViewById(R.id.value170),
                findViewById(R.id.value171), findViewById(R.id.value172), findViewById(R.id.value173), findViewById(R.id.value174), findViewById(R.id.value175), findViewById(R.id.value176), findViewById(R.id.value177), findViewById(R.id.value178), findViewById(R.id.value179),
                findViewById(R.id.value180), findViewById(R.id.value181), findViewById(R.id.value182), findViewById(R.id.value183), findViewById(R.id.value184), findViewById(R.id.value185), findViewById(R.id.value186), findViewById(R.id.value187), findViewById(R.id.value188),
                findViewById(R.id.value189), findViewById(R.id.value190), findViewById(R.id.value191), findViewById(R.id.value192), findViewById(R.id.value193), findViewById(R.id.value194), findViewById(R.id.value195), findViewById(R.id.value196), findViewById(R.id.value197),
                findViewById(R.id.value198), findViewById(R.id.value199), findViewById(R.id.value200), findViewById(R.id.value201), findViewById(R.id.value202), findViewById(R.id.value203), findViewById(R.id.value204), findViewById(R.id.value205), findViewById(R.id.value206),
                findViewById(R.id.value207), findViewById(R.id.value208), findViewById(R.id.value209), findViewById(R.id.value210), findViewById(R.id.value211), findViewById(R.id.value212), findViewById(R.id.value213), findViewById(R.id.value214), findViewById(R.id.value215),
                findViewById(R.id.value216), findViewById(R.id.value217), findViewById(R.id.value218), findViewById(R.id.value219), findViewById(R.id.value220), findViewById(R.id.value221), findViewById(R.id.value222), findViewById(R.id.value223), findViewById(R.id.value224),
                findViewById(R.id.value225), findViewById(R.id.value226), findViewById(R.id.value227), findViewById(R.id.value228), findViewById(R.id.value229), findViewById(R.id.value230), findViewById(R.id.value231), findViewById(R.id.value232), findViewById(R.id.value233),
                findViewById(R.id.value234), findViewById(R.id.value235), findViewById(R.id.value236), findViewById(R.id.value237), findViewById(R.id.value238), findViewById(R.id.value239), findViewById(R.id.value240), findViewById(R.id.value241), findViewById(R.id.value242),
                findViewById(R.id.value243), findViewById(R.id.value244), findViewById(R.id.value245), findViewById(R.id.value246), findViewById(R.id.value247), findViewById(R.id.value248), findViewById(R.id.value249), findViewById(R.id.value250), findViewById(R.id.value251),
                findViewById(R.id.value252), findViewById(R.id.value253), findViewById(R.id.value254), findViewById(R.id.value255), findViewById(R.id.value256), findViewById(R.id.value257), findViewById(R.id.value258), findViewById(R.id.value259), findViewById(R.id.value260),
                findViewById(R.id.value261), findViewById(R.id.value262), findViewById(R.id.value263), findViewById(R.id.value264), findViewById(R.id.value265), findViewById(R.id.value266), findViewById(R.id.value267), findViewById(R.id.value268), findViewById(R.id.value269),
                findViewById(R.id.value270), findViewById(R.id.value271), findViewById(R.id.value272), findViewById(R.id.value273), findViewById(R.id.value274), findViewById(R.id.value275), findViewById(R.id.value276), findViewById(R.id.value277), findViewById(R.id.value278),
                findViewById(R.id.value279), findViewById(R.id.value280), findViewById(R.id.value281), findViewById(R.id.value282), findViewById(R.id.value283), findViewById(R.id.value284), findViewById(R.id.value285), findViewById(R.id.value286), findViewById(R.id.value287),
                findViewById(R.id.value288), findViewById(R.id.value289), findViewById(R.id.value290), findViewById(R.id.value291), findViewById(R.id.value292), findViewById(R.id.value293), findViewById(R.id.value294), findViewById(R.id.value295), findViewById(R.id.value296),
                findViewById(R.id.value297), findViewById(R.id.value298), findViewById(R.id.value299), findViewById(R.id.value300), findViewById(R.id.value301), findViewById(R.id.value302), findViewById(R.id.value303), findViewById(R.id.value304), findViewById(R.id.value305),
                findViewById(R.id.value306), findViewById(R.id.value307), findViewById(R.id.value308), findViewById(R.id.value309), findViewById(R.id.value310), findViewById(R.id.value311), findViewById(R.id.value312), findViewById(R.id.value313), findViewById(R.id.value314),
                findViewById(R.id.value315), findViewById(R.id.value316), findViewById(R.id.value317), findViewById(R.id.value318), findViewById(R.id.value319), findViewById(R.id.value320), findViewById(R.id.value321), findViewById(R.id.value322), findViewById(R.id.value323),
                findViewById(R.id.value324), findViewById(R.id.value325), findViewById(R.id.value326), findViewById(R.id.value327), findViewById(R.id.value328), findViewById(R.id.value329), findViewById(R.id.value330), findViewById(R.id.value331), findViewById(R.id.value332),
                findViewById(R.id.value333), findViewById(R.id.value334), findViewById(R.id.value335), findViewById(R.id.value336), findViewById(R.id.value337), findViewById(R.id.value338), findViewById(R.id.value339), findViewById(R.id.value340), findViewById(R.id.value341),
                findViewById(R.id.value342), findViewById(R.id.value343), findViewById(R.id.value344), findViewById(R.id.value345), findViewById(R.id.value346), findViewById(R.id.value347), findViewById(R.id.value348), findViewById(R.id.value349), findViewById(R.id.value350),
                findViewById(R.id.value351), findViewById(R.id.value352), findViewById(R.id.value353), findViewById(R.id.value354), findViewById(R.id.value355), findViewById(R.id.value356), findViewById(R.id.value357), findViewById(R.id.value358), findViewById(R.id.value359),
                findViewById(R.id.value360), findViewById(R.id.value361), findViewById(R.id.value362), findViewById(R.id.value363), findViewById(R.id.value364), findViewById(R.id.value365), findViewById(R.id.value366), findViewById(R.id.value367), findViewById(R.id.value368),
                findViewById(R.id.value369), findViewById(R.id.value370), findViewById(R.id.value371), findViewById(R.id.value372), findViewById(R.id.value373), findViewById(R.id.value374), findViewById(R.id.value375), findViewById(R.id.value376), findViewById(R.id.value377),
                findViewById(R.id.value378), findViewById(R.id.value379), findViewById(R.id.value380), findViewById(R.id.value381), findViewById(R.id.value382), findViewById(R.id.value383), findViewById(R.id.value384), findViewById(R.id.value385), findViewById(R.id.value386),
                findViewById(R.id.value387), findViewById(R.id.value388), findViewById(R.id.value389), findViewById(R.id.value390), findViewById(R.id.value391), findViewById(R.id.value392), findViewById(R.id.value393), findViewById(R.id.value394), findViewById(R.id.value395),
                findViewById(R.id.value396), findViewById(R.id.value397), findViewById(R.id.value398), findViewById(R.id.value399), findViewById(R.id.value400), findViewById(R.id.value401), findViewById(R.id.value402), findViewById(R.id.value403), findViewById(R.id.value404),
                findViewById(R.id.value405), findViewById(R.id.value406), findViewById(R.id.value407), findViewById(R.id.value408), findViewById(R.id.value409), findViewById(R.id.value410), findViewById(R.id.value411), findViewById(R.id.value412), findViewById(R.id.value413),
                findViewById(R.id.value414), findViewById(R.id.value415), findViewById(R.id.value416), findViewById(R.id.value417), findViewById(R.id.value418), findViewById(R.id.value419), findViewById(R.id.value420), findViewById(R.id.value421), findViewById(R.id.value422),
                findViewById(R.id.value423), findViewById(R.id.value424), findViewById(R.id.value425), findViewById(R.id.value426), findViewById(R.id.value427), findViewById(R.id.value428), findViewById(R.id.value429), findViewById(R.id.value430), findViewById(R.id.value431),
                findViewById(R.id.value432), findViewById(R.id.value433), findViewById(R.id.value434), findViewById(R.id.value435), findViewById(R.id.value436), findViewById(R.id.value437), findViewById(R.id.value438), findViewById(R.id.value439), findViewById(R.id.value440),
                findViewById(R.id.value441), findViewById(R.id.value442), findViewById(R.id.value443), findViewById(R.id.value444), findViewById(R.id.value445), findViewById(R.id.value446), findViewById(R.id.value447), findViewById(R.id.value448), findViewById(R.id.value449),
                findViewById(R.id.value450), findViewById(R.id.value451), findViewById(R.id.value452), findViewById(R.id.value453), findViewById(R.id.value454), findViewById(R.id.value455), findViewById(R.id.value456), findViewById(R.id.value457), findViewById(R.id.value458),
                findViewById(R.id.value459), findViewById(R.id.value460), findViewById(R.id.value461), findViewById(R.id.value462), findViewById(R.id.value463), findViewById(R.id.value464), findViewById(R.id.value465), findViewById(R.id.value466), findViewById(R.id.value467),
                findViewById(R.id.value468), findViewById(R.id.value469), findViewById(R.id.value470), findViewById(R.id.value471), findViewById(R.id.value472), findViewById(R.id.value473), findViewById(R.id.value474), findViewById(R.id.value475), findViewById(R.id.value476),
                findViewById(R.id.value477), findViewById(R.id.value478), findViewById(R.id.value479), findViewById(R.id.value480), findViewById(R.id.value481), findViewById(R.id.value482), findViewById(R.id.value483), findViewById(R.id.value484), findViewById(R.id.value485),
                findViewById(R.id.value486), findViewById(R.id.value487), findViewById(R.id.value488), findViewById(R.id.value489), findViewById(R.id.value490), findViewById(R.id.value491), findViewById(R.id.value492), findViewById(R.id.value493), findViewById(R.id.value494),
                findViewById(R.id.value495), findViewById(R.id.value496), findViewById(R.id.value497), findViewById(R.id.value498), findViewById(R.id.value499), findViewById(R.id.value500), findViewById(R.id.value501), findViewById(R.id.value502), findViewById(R.id.value503),
                findViewById(R.id.value504), findViewById(R.id.value505), findViewById(R.id.value506), findViewById(R.id.value507), findViewById(R.id.value508), findViewById(R.id.value509), findViewById(R.id.value510), findViewById(R.id.value511), findViewById(R.id.value512),
                findViewById(R.id.value513), findViewById(R.id.value514), findViewById(R.id.value515), findViewById(R.id.value516), findViewById(R.id.value517), findViewById(R.id.value518), findViewById(R.id.value519), findViewById(R.id.value520), findViewById(R.id.value521),
                findViewById(R.id.value522), findViewById(R.id.value523), findViewById(R.id.value524), findViewById(R.id.value525), findViewById(R.id.value526), findViewById(R.id.value527), findViewById(R.id.value528), findViewById(R.id.value529), findViewById(R.id.value530),
                findViewById(R.id.value531), findViewById(R.id.value532), findViewById(R.id.value533), findViewById(R.id.value534), findViewById(R.id.value535), findViewById(R.id.value536), findViewById(R.id.value537), findViewById(R.id.value538), findViewById(R.id.value539),
                findViewById(R.id.value540), findViewById(R.id.value541), findViewById(R.id.value542), findViewById(R.id.value543), findViewById(R.id.value544), findViewById(R.id.value545), findViewById(R.id.value546), findViewById(R.id.value547), findViewById(R.id.value548),
                findViewById(R.id.value549), findViewById(R.id.value550), findViewById(R.id.value551), findViewById(R.id.value552), findViewById(R.id.value553), findViewById(R.id.value554), findViewById(R.id.value555), findViewById(R.id.value556), findViewById(R.id.value557),
                findViewById(R.id.value558), findViewById(R.id.value559), findViewById(R.id.value560), findViewById(R.id.value561), findViewById(R.id.value562), findViewById(R.id.value563), findViewById(R.id.value564), findViewById(R.id.value565), findViewById(R.id.value566),
                findViewById(R.id.value567), findViewById(R.id.value568), findViewById(R.id.value569), findViewById(R.id.value570), findViewById(R.id.value571), findViewById(R.id.value572), findViewById(R.id.value573), findViewById(R.id.value574), findViewById(R.id.value575),
                findViewById(R.id.value576), findViewById(R.id.value577), findViewById(R.id.value578), findViewById(R.id.value579), findViewById(R.id.value580), findViewById(R.id.value581), findViewById(R.id.value582), findViewById(R.id.value583), findViewById(R.id.value584),
                findViewById(R.id.value585), findViewById(R.id.value586), findViewById(R.id.value587), findViewById(R.id.value588), findViewById(R.id.value589), findViewById(R.id.value590), findViewById(R.id.value591), findViewById(R.id.value592), findViewById(R.id.value593),
                findViewById(R.id.value594), findViewById(R.id.value595), findViewById(R.id.value596), findViewById(R.id.value597), findViewById(R.id.value598), findViewById(R.id.value599), findViewById(R.id.value600), findViewById(R.id.value601), findViewById(R.id.value602),
                findViewById(R.id.value603), findViewById(R.id.value604), findViewById(R.id.value605), findViewById(R.id.value606), findViewById(R.id.value607), findViewById(R.id.value608), findViewById(R.id.value609), findViewById(R.id.value610), findViewById(R.id.value611),
                findViewById(R.id.value612), findViewById(R.id.value613), findViewById(R.id.value614), findViewById(R.id.value615), findViewById(R.id.value616), findViewById(R.id.value617), findViewById(R.id.value618), findViewById(R.id.value619), findViewById(R.id.value620),
                findViewById(R.id.value621)};


        textViewsKations = new TextView[]{findViewById(R.id.kationH), findViewById(R.id.kationNH4), findViewById(R.id.kationLi), findViewById(R.id.kationRb),
                findViewById(R.id.kationK), findViewById(R.id.kationBa), findViewById(R.id.kationSr), findViewById(R.id.kationCa), findViewById(R.id.kationNa),
                findViewById(R.id.kationMg), findViewById(R.id.kationBe), findViewById(R.id.kationAl), findViewById(R.id.kationMn), findViewById(R.id.kationZn),
                findViewById(R.id.kationCr1), findViewById(R.id.kationCr2), findViewById(R.id.kationFe1), findViewById(R.id.kationFe2),
                findViewById(R.id.kationCd), findViewById(R.id.kationCo1), findViewById(R.id.kationCo2), findViewById(R.id.kationNi), findViewById(R.id.kationSn),
                findViewById(R.id.kationPb), findViewById(R.id.kationCu), findViewById(R.id.kationAg), findViewById(R.id.kationHg)};


        textViewsAnions = new TextView[]{findViewById(R.id.anionOH), findViewById(R.id.anionF), findViewById(R.id.anionCl), findViewById(R.id.anionBr), findViewById(R.id.anionI),
                findViewById(R.id.anionS), findViewById(R.id.anionSO4), findViewById(R.id.anionHSO), findViewById(R.id.anionSO3), findViewById(R.id.anionClO4), findViewById(R.id.anionClO3), findViewById(R.id.anionNO3),
                findViewById(R.id.anionNO2), findViewById(R.id.anionPO4), findViewById(R.id.anionHPO4), findViewById(R.id.anionH2PO4), findViewById(R.id.anionCH3COO),
                findViewById(R.id.anionCr2O7), findViewById(R.id.anionCrO4), findViewById(R.id.anionMnO4), findViewById(R.id.anionCO3),findViewById(R.id.anionHCO3),findViewById(R.id.anionSiO3)};


        textViewsInformation = new TextView[]{findViewById(R.id.tvinfo1), findViewById(R.id.tvinfo2), findViewById(R.id.tvinfo3), findViewById(R.id.tvinfo4), findViewById(R.id.tvinfo5), findViewById(R.id.tvinfo6)};


        kations = new String[]{"Катион водорода", "Катион аммония", "Катион лития", "Катион рубидия", "Катион калия", "Катион бария", "Катион стронция", "Катион кальция",
                "Катион натрия", "Катион магния", "Катион бериллия", "Катион алюминия", "Катион марганца", "Катион цинка", "Катион хрома(II)", "Катион хрома(III)", "Катион железа(II)",
                "Катион железа(III)", "Катион кадмия", "Катион кобальта(II)", "Катион кобальта(III)", "Катион никеля", "Катион олова", "Катион свинца", "Катион меди", "Катион серебра",
                "Катион ртути"};


        anions = new String[]{"Гидроксид", "Фторид", "Хлорид", "Бромид", "Йодид", "Сульфид", "Сульфат", "Гидросульфат", "Сульфит", "Перхлорат", "Хлорат", "Нитрат", "Нитрит", "(Орто)фосфат",
                "Гидрофосфат", "Дигидрофосфат", "Ацетат", "Дихромат", "Хромат", "Перманганат", "Карбонат", "Гидрокарбонат", "Метасиликат"};


        //позиция названия соединения
        int n = 0;

        compound = new String[621];

        for (int i = 0; i < anions.length; i++) {
            for (int j = 0; j < kations.length; j++) {
                if (n < 621)
                compound[n] = anions[i] + " " + kations[j].substring(7);
                n++;
            }
        }

        //Даём длину и ширину textViewsValues[i] и устанавливаем размер текста
        for (int i = 0; i < textViewsValues.length; i++) {
            textViewsValues[i].setWidth(width / 30);
            textViewsValues[i].setHeight(height / 25);
            textViewsValues[i].setTextSize(width / 95);
            textViewsValues[i].setTextColor(Color.BLACK);
            textViewsValues[i].setGravity(Gravity.CENTER);
            textViewsValues[i].setOnClickListener(this);
            textViewsValues[i].setTypeface(typeface);
        }

        //Даём длину и ширину textViewsKations[i] и устанавливаем размер текста
        for (int i = 0; i < textViewsKations.length; i++) {
            textViewsKations[i].setWidth(width / 30);
            textViewsKations[i].setHeight(height / 25);
            textViewsKations[i].setOnClickListener(this);
            textViewsKations[i].setTextColor(Color.BLACK);
            textViewsKations[i].setGravity(Gravity.CENTER);
            textViewsKations[i].setTypeface(typeface);

            if ((i == 1) || (i == 9) || (i == 12)){
                textViewsKations[i].setTextSize(width / 98);
            } else {
                textViewsKations[i].setTextSize(width / 95);
            }
        }

        //Даём длину и ширину textViewsAnions[i] и устанавливаем размер текста
        for (int i = 0; i < textViewsAnions.length; i++) {
            textViewsAnions[i].setTextSize(width / 95);
            textViewsAnions[i].setHeight(height / 25);
            textViewsAnions[i].setTextColor(Color.BLACK);
            textViewsAnions[i].setGravity(Gravity.CENTER);
            textViewsAnions[i].setOnClickListener(this);
            textViewsAnions[i].setTypeface(typeface);

        }

        //Даём длину и ширину textViewsInformation[i] и устанавливаем размер текста
        for (int i = 0; i < textViewsInformation.length; i++) {
            textViewsInformation[i].setWidth(width / 30);
            textViewsInformation[i].setHeight(height / 25);
            textViewsInformation[i].setTextSize(width / 95);
            textViewsInformation[i].setOnClickListener(this);
            textViewsInformation[i].setTypeface(typeface);

        }

    }

    @Override
    public void onClick(View v) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.my_toast, (ViewGroup) findViewById(R.id.toastContainer));

        // Делаем тост красивым
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);

        // текст в тосте
        TextView textViewToast = layout.findViewById(R.id.toastText);
        textViewToast.setTextSize(width / 60);
        textViewToast.setHeight(height / 12);
        textViewToast.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/gothic.ttf"));

        //Просматриваем нажимал ли пользователь на textViewsAnions[i]
        for (int i = 0; i < textViewsAnions.length; i++) {
            if (v.getId() == textViewsAnions[i].getId()){
                if (anions[i] == null){
                    fillTextViews();
                }
                //Устанавливаем текст и показыаем тост
                textViewToast.setText(anions[i]);
                toast.setView(layout);
                toast.show();
            }
        }

        //Чтобы избежать ошибки NullPointerException делаем следующее
        if (textViewsKations == null || kations == null) {
            fillTextViews();
        }
        for (int i = 0; i < textViewsKations.length; i++) {
            if (v.getId() == textViewsKations[i].getId()){
                if (kations[i] == null){
                    fillTextViews();
                }
                //Устанавливаем текст и показыаем тост
                textViewToast.setText(kations[i]);
                toast.setView(layout);
                toast.show();
            }
        }

        for (int i = 0; i < textViewsValues.length; i++) {
            if (v.getId() == textViewsValues[i].getId()){
                if (textViewsValues[i].getText().equals(" ? ")){
                    //Устанавливаем текст и показыаем тост
                    textViewToast.setText("Нет данных о существовании вещества");
                    toast.setView(layout);
                    toast.show();
                } else {
                    if (i == 0){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Вода");
                        toast.setView(layout);
                        toast.show();
                    } else if (i == 27){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Фтороводородная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 54){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Хлороводородная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 81){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Бромоводородная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 108){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Йодоводородная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 135){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Сероводородная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 162){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Гидросульфат");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 189){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Серная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 216){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Сернистая кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 243){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Хлорная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 270){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Хлорноватая кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 297){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Азотная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 324){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Азотистая кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 351){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Гидрофосфат");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 378){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Дигидрофосфат");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 405){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Ортофосфорная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 432){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Уксусная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 459){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Дихромовая кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 486){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Хромовая кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 513){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Марганцовая кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 540){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Гидрокарбонат");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 567){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Угольная кислота");
                        toast.setView(layout);
                        toast.show();
                    } else  if (i == 594){
                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText("Кремневая кислота");
                        toast.setView(layout);
                        toast.show();
                    } else {
                        //Чтобы избежать ошибки NullPointerException делаем следующее
                        if (compound[i] == null){
                            fillTextViews();
                        }

                        //Устанавливаем текст и показыаем тост
                        textViewToast.setText(compound[i]);
                        toast.setView(layout);
                        toast.show();
                    }
                }
            }
        }

        for (int i = 0; i < textViewsInformation.length; i++) {
            if (v.getId() == textViewsInformation[i].getId()){
                if (i == 0){
                    //Устанавливаем текст и показыаем тост
                    textViewToast.setText("(>1 г на 100 г воды)");
                    toast.setView(layout);
                    toast.show();
                } else if (i == 3){
                    //Устанавливаем текст и показыаем тост
                    textViewToast.setText("(от 0.1 г до 1 г на 100 г воды)");
                    toast.setView(layout);
                    toast.show();
                } else if (i == 1){
                    //Устанавливаем текст и показыаем тост
                    textViewToast.setText("(<0.1 г на 100 г воды)");
                    toast.setView(layout);
                    toast.show();
                }
            }
        }
    }

    // метод, заполняющий информацию об индикаторах
    private void fillIndicators(){
        TextView[] indicators = new TextView[14];
        indicators[0] = findViewById(R.id.indic);
        indicators[1] = findViewById(R.id.acid);
        indicators[2] = findViewById(R.id.neutral);
        indicators[3] = findViewById(R.id.alkali);
        indicators[4] = findViewById(R.id.lakm);
        indicators[5] = findViewById(R.id.lakm1);
        indicators[6] = findViewById(R.id.lakm2);
        indicators[7] = findViewById(R.id.lakm3);
        indicators[8] = findViewById(R.id.metil);
        indicators[9] = findViewById(R.id.metil1);
        indicators[10] = findViewById(R.id.metil2);
        indicators[11] = findViewById(R.id.metil3);
        indicators[12] = findViewById(R.id.fenol);
        indicators[13] = findViewById(R.id.fenol1);

        for (int i = 0; i < indicators.length; i++){
            indicators[i].setTypeface(typeface);
            indicators[i].setTextSize(20f);
        }
    }

    // меню выбора таблиц
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.table_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // меняет таблицу растворимости на таблицу индикаторов
        if (item.getItemId() == R.id.action_ind){
            setContentView(R.layout.indicator);
            fillIndicators();
        }
        // меняет таблицу индикаторов на таблицу растворимости
        if (item.getItemId() == R.id.action_sol){
            setContentView(R.layout.activity_table_solubility);
            fillTextViews();
        }
        // закрытие активности при нажатии стрелки "Назад"
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
