package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.chemistrysolver.R;

import java.util.ArrayList;

// класс диалогового окна, отображающего химические переменные
public class CheckItemDialog {

    // названия химических переменных
    private CheckBox[] designations;
    // кнопка, по которой закрывается окно и повляются в главном поле отмеченные пункты
    private Button done;
    // характеристики выбранных пунктов
    private ArrayList<MyList> myLists;
    // массив единиц измерения
    private String[] units;
    // все соединения, которые есть в задаче
    private ArrayList<String> elements;
    private Context context;
    // отслеживает то, что в задаче говорится о реакции; то, что открыта активность "Дано"; то, было введено дано
    private boolean isNotReaction, isGivenActivity, hasGiven;
    private View view;
    private OnDialogDismissed onDialogDismissed;

    // интерфейс, передающий при закрытии окна, информацию об отмеченных пунктах
    public interface OnDialogDismissed{
        void getMyList(ArrayList<MyList> myLists);
        void getElements(ArrayList<String> elements);
    }

    // конструктор окна
    public CheckItemDialog(Context context, View view, ArrayList<String> elements, boolean isNotReaction,
                           ArrayList<MyList> myLists, boolean isGivenActivity, boolean hasGiven, OnDialogDismissed onDialogDismissed){
        this.context = context;
        this.view = view;
        this.elements = elements;
        this.isNotReaction = isNotReaction;
        this.myLists = myLists;
        this.isGivenActivity = isGivenActivity;
        this.onDialogDismissed = onDialogDismissed;
        this.hasGiven = hasGiven;
    }

    // отображение диалогового окна
    public void createDialog(){
        units = new String[]{};

        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_items, viewGroup, false);
        designations = new CheckBox[30];
        designations[0] = dialogView.findViewById(R.id.des1); designations[1] = dialogView.findViewById(R.id.des2);
        designations[2] = dialogView.findViewById(R.id.des3); designations[3] = dialogView.findViewById(R.id.des4);
        designations[4] = dialogView.findViewById(R.id.des5); designations[5] = dialogView.findViewById(R.id.des6);
        designations[6] = dialogView.findViewById(R.id.des7); designations[7] = dialogView.findViewById(R.id.des8);
        designations[8] = dialogView.findViewById(R.id.des9); designations[9] = dialogView.findViewById(R.id.des10);
        designations[10] = dialogView.findViewById(R.id.des11); designations[11] = dialogView.findViewById(R.id.des12);
        designations[12] = dialogView.findViewById(R.id.des13); designations[13] = dialogView.findViewById(R.id.des14);
        designations[14] = dialogView.findViewById(R.id.des15); designations[15] = dialogView.findViewById(R.id.des16);
        designations[16] = dialogView.findViewById(R.id.des17); designations[17] = dialogView.findViewById(R.id.des18);
        designations[18] = dialogView.findViewById(R.id.des19); designations[19] = dialogView.findViewById(R.id.des20);
        designations[20] = dialogView.findViewById(R.id.des21); designations[21] = dialogView.findViewById(R.id.des22);
        designations[22] = dialogView.findViewById(R.id.des23); designations[23] = dialogView.findViewById(R.id.des24);
        designations[24] = dialogView.findViewById(R.id.des25); designations[25] = dialogView.findViewById(R.id.des26);
        designations[26] = dialogView.findViewById(R.id.des27); designations[27] = dialogView.findViewById(R.id.des28);
        designations[28] = dialogView.findViewById(R.id.des29); designations[29] = dialogView.findViewById(R.id.des30);

        designations[4].setEnabled(false); designations[10].setEnabled(false);

        // если окно было вызвано из GivenActivity, то некоторые пункты не должны быть доступны
        if (isGivenActivity){
            designations[16].setEnabled(false);
            designations[19].setEnabled(false);
        }

        // если не было введено дано, доступны только 16, 19, 0 пункты, которые можно искать
        if (!hasGiven){
            for (int i = 0; i < designations.length; i++) {
                if (i != 16 && i != 19 && i != 0){
                    designations[i].setEnabled(false);
                }
            }
        }

        // если элементов нет, запрещаем пользователю отмечать некоторые пункты
        if (elements.size() == 0){
            designations[6].setEnabled(false);
            designations[7].setEnabled(false);
            designations[8].setEnabled(false);
            designations[13].setEnabled(false);
            designations[14].setEnabled(false);
            designations[15].setEnabled(false);
            designations[16].setEnabled(false);
            designations[19].setEnabled(false);
            designations[28].setEnabled(false);
            designations[29].setEnabled(false);
        } else if (isNotReaction){ // если в задаче нет реакции, запрещаем пользователю отмечать некоторые пункты
            designations[6].setEnabled(false);
            designations[7].setEnabled(false);
            designations[8].setEnabled(false);
            designations[13].setEnabled(false);
            designations[14].setEnabled(false);
            designations[15].setEnabled(false);
            designations[28].setEnabled(false);
            designations[29].setEnabled(false);
        }

        // кнопка для закрытия окна
        done = dialogView.findViewById(R.id.done);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder
                .setView(dialogView)
                .setCancelable(true)
                .create();

        for (int i = 0; i < designations.length; i++) designations[i].setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Candara.ttf"));

        // при нажатии на кнопку "ОК" окно закрывается
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < designations.length; i++) {
                    if (designations[i].isChecked()) {
                        getUnits(i);
                        MyList myList = new MyList(context, elements, units, i);
                        myList.positionUnits = 0;
                        myList.position = 0;
                        if (i == 28 || i == 29) {
                            if (!elements.contains("Q")) {
                                elements.add("Q");
                                onDialogDismissed.getElements(elements);
                            }
                            myList.position = elements.size() - 1;
                        }
                        myList.positionDensity = 0;
                        myList.text = "";
                        myLists.add(myList);
                    }
                }
                onDialogDismissed.getMyList(myLists);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    // заполнение массива единиц измерения
    private void getUnits(int j){
        if (j == 0){
            units = new String[]{"-", "г/моль", "кг/моль", "мг/моль"};
        } else if (j == 1){
            units = new String[]{"-", "моль", "кмоль", "ммоль"};
        } else if (j == 2 || j == 3 || j == 4 || j == 7 || j == 8 || j == 17 || j == 21){
            units = new String[]{"-", "г", "кг", "мг"};
        } else if (j == 5 || j == 6 || j == 12 || j == 13){
            units = new String[]{"-", "%", "доля"};
        } else if (j == 9 || j == 10 || j == 11 || j == 14 || j == 15){
            units = new String[]{"-", "л", "мл", "дм³", "м³", "см³"};
        } else if (j == 20){
            units = new String[]{"-", "моль/л", "моль/мл", "моль/дм³", "моль/м³", "моль/см³"};
        } else if (j == 22){
            units = new String[]{"-", "моль/кг", "моль/г", "моль/мг"};
        } else if (j == 23){
            units = new String[]{"-", "г/мл", "г/л"};
        } else if (j == 24){
            units = new String[]{"-", "атм", "Па"};
        } else if (j == 27 || j == 26){
            units = new String[]{"-", "г/мл", "г/см³", "г/л", "кг/м³"};
        } else if (j == 25){
            units = new String[]{"-", "K", "°C", "°F"};
        } else if (j == 28 || j == 29){
            units = new String[]{"-", "кДж", "Дж"};
        } else if (j == 16 || j == 18 || j == 19){
            units = new String[]{" "};
        }
    }

}
