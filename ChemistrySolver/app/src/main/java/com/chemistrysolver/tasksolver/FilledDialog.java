package com.chemistrysolver.tasksolver;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chemistrysolver.R;

// класс отображения диалогового окна, в котором показывается то, что дано в задаче и то, что нужно найти
public class FilledDialog {

    private Context context;
    private View view;
    // текст, который нужно разместить в диалоговом окне
    private SpannableStringBuilder string;

    public FilledDialog(Context context, View view, SpannableStringBuilder string){
        this.context = context;
        this.view = view;
        this.string = string;
    }

    public void createDialog(){
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.filled_dialog, viewGroup, false);

        // текст, находящийся в диалоговом окне
        TextView textView = dialogView.findViewById(R.id.filled);
        textView.setText(string);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf"));

        // параметры диалогового окна
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder
                .setView(dialogView)
                .setCancelable(true)
                .create();

        alertDialog.show();
    }

}
