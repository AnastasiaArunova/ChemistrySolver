package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.chemistrysolver.R;

import java.util.ArrayList;

// адаптер для отображения "Дано" или "Найти"
public class MyListAdapter extends ArrayAdapter<MyList> {

    private Context context;
    private Typeface typeface;
    // значения, выбранные пользователем, с установленными для них параметрами
    private ArrayList<MyList> myLists;
    // XML-разметка, отображающаяся в item списка
    private int resource;

    // конструктор адаптера
    public MyListAdapter(Context context, int resource, ArrayList<MyList> myLists) {
        super(context, resource, myLists);
        this.context = context;
        this.myLists = myLists;
        this.resource = resource;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Candara.ttf");
    }

    @Override
    public int getCount() {
        return myLists.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int j, View view, ViewGroup viewGroup) {
        // химическая величина, для которой устанавливаются значения
        final MyList myList = myLists.get(j);
        // индекс химической величины, для которой устанавливаются значения
        final int k = j;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (myList.elements.size() != 0)
            if (myList.number == 16 && resource == R.layout.find_adapter_elements){
                view = inflater.inflate(R.layout.find_for_density, null);
                // поле отображающее "("
                TextView designation1 = view.findViewById(R.id.designation1);
                designation1.setTypeface(typeface);
                // Spinner в котором находятся вещества
                Spinner spinnerForDensity = view.findViewById(R.id.densityspin);
                spinnerForDensity.setAdapter(myList.adapter);
                spinnerForDensity.setSelection(myLists.get(j).positionDensity);
                spinnerForDensity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        myLists.get(k).positionDensity = i;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        myLists.get(k).positionDensity = myLists.get(k).positionDensity;
                    }
                });
            } else view = inflater.inflate(resource, null);
        else view = inflater.inflate(resource, null);


        // поле отображающее ")=?"
        TextView text = view.findViewById(R.id.text);
        // изменяем текст в зависимости от химической величина, для которой устанавливаются значения
        if (myLists.get(k).number >= 28) text.setText(" = ");
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setAdapter(myList.adapter);
        spinner.setSelection(myLists.get(j).position);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myLists.get(k).position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                myLists.get(k).position = myLists.get(k).position;
            }
        });
        if (myLists.get(j).number == 28 || myLists.get(j).number == 29)
            spinner.setVisibility(View.INVISIBLE);


        if (resource == R.layout.given_adapter_elements) {

            if (myLists.get(k).number >= 28) text.setText(" = ");

            final Spinner spinnerUnits = view.findViewById(R.id.spinnerunits);
            spinnerUnits.setAdapter(myList.adapterUnitsOfMeasurement);
            spinnerUnits.setSelection(myLists.get(j).positionUnits);

            spinnerUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    myLists.get(k).positionUnits = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    myLists.get(k).positionUnits = myLists.get(k).positionUnits;
                }
            });

            if (myLists.get(j).number == 16 || myLists.get(j).number == 18 || myLists.get(j).number == 19)
                spinnerUnits.setVisibility(View.INVISIBLE);

            final EditText num = view.findViewById(R.id.editnum);
            num.setText(myLists.get(j).text);
            myLists.get(j).text = num.getText().toString();
            num.setTypeface(typeface);
            num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    myLists.get(k).text = num.getText().toString();
                }
            });
        }

        // кнопка удаления item
        final Button remove = view.findViewById(R.id.remove);
        remove.setTag(j);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = (int) view.getTag();
                myLists.remove(i);
                MyListAdapter.this.notifyDataSetChanged();
            }
        });

        // поле текста, зависящее от номера химической величины
        TextView designation = view.findViewById(R.id.designation);
        text.setTypeface(typeface);
        designation.setTypeface(typeface);

        // установка текста в зависимости от номера химической величины
        if (myList.elements.size() != 0) {
            if (myLists.get(j).number == 0) designation.setText("M(");
            else if (myLists.get(j).number == 1) designation.setText("n(");
            else if (myLists.get(j).number == 2) designation.setText("m(");
            else if (myLists.get(j).number == 3) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mр-ра(");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 4) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mсмеси(");
                ssb.setSpan(new SubscriptSpan(), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 5) designation.setText("ω(");
            else if (myLists.get(j).number == 6) designation.setText("η(");
            else if (myLists.get(j).number == 7) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mтеор(");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 8) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mпракт(");
                ssb.setSpan(new SubscriptSpan(), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 9) designation.setText("V(");
            else if (myLists.get(j).number == 10) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("Vсмеси(");
                ssb.setSpan(new SubscriptSpan(), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 11) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("Vр-ра(");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 12) designation.setText("ω(");
            else if (myLists.get(j).number == 13) designation.setText("η(");
            else if (myLists.get(j).number == 14) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("Vтеор(");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 15) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("Vпракт(");
                ssb.setSpan(new SubscriptSpan(), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 16) designation.setText("D");
            else if (myLists.get(j).number == 17) designation.setText("m₀(");
            else if (myLists.get(j).number == 18) designation.setText("N(");
            else if (myLists.get(j).number == 19) designation.setText("χ(");
            else if (myLists.get(j).number == 20) designation.setText("C(");
            else if (myLists.get(j).number == 21) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mр-ля(");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 22) designation.setText("C(");
            else if (myLists.get(j).number == 23) designation.setText("T(");
            else if (myLists.get(j).number == 24) designation.setText("p(");
            else if (myLists.get(j).number == 25) designation.setText("t(");
            else if (myLists.get(j).number == 26) designation.setText("ρ(");
            else if (myLists.get(j).number == 27) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("ρр-ра(");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 28) designation.setText("Q");
            else if (myLists.get(j).number == 29) designation.setText("Q₁");
        } else {
            if (myLists.get(j).number == 0) designation.setText("M");
            else if (myLists.get(j).number == 1) designation.setText("n");
            else if (myLists.get(j).number == 2) designation.setText("m");
            else if (myLists.get(j).number == 3) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mр-ра");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 4) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mсмеси");
                ssb.setSpan(new SubscriptSpan(), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 5) designation.setText("ω");
            else if (myLists.get(j).number == 6) designation.setText("η");
            else if (myLists.get(j).number == 7) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mтеор");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 8) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mпракт");
                ssb.setSpan(new SubscriptSpan(), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 9) designation.setText("V");
            else if (myLists.get(j).number == 10) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("Vсмеси");
                ssb.setSpan(new SubscriptSpan(), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 11) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("Vр-ра");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 12) designation.setText("ω");
            else if (myLists.get(j).number == 13) designation.setText("η");
            else if (myLists.get(j).number == 14) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("Vтеор");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 15) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("Vпракт");
                ssb.setSpan(new SubscriptSpan(), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 16) designation.setText("D");
            else if (myLists.get(j).number == 17) designation.setText("m₀");
            else if (myLists.get(j).number == 18) designation.setText("N");
            else if (myLists.get(j).number == 19) designation.setText("χ");
            else if (myLists.get(j).number == 20) designation.setText("C");
            else if (myLists.get(j).number == 21) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("mр-ля");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 22) designation.setText("C");
            else if (myLists.get(j).number == 23) designation.setText("T");
            else if (myLists.get(j).number == 24) designation.setText("p");
            else if (myLists.get(j).number == 25) designation.setText("t");
            else if (myLists.get(j).number == 26) designation.setText("ρ");
            else if (myLists.get(j).number == 27) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("ρр-ра");
                ssb.setSpan(new SubscriptSpan(), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                designation.setText(ssb);
            } else if (myLists.get(j).number == 28) designation.setText("Q");
            else if (myLists.get(j).number == 29) designation.setText("Q₁");
        }

        return view;
    }

}
