package com.chemistrysolver;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chemistrysolver.formulas.FormulasActivity;
import com.chemistrysolver.reactionbalancing.ReactionBalancingActivity;
import com.chemistrysolver.tableelement.TableElementActivity;
import com.chemistrysolver.tablesolubility.TableSolubilityActivity;
import com.chemistrysolver.tasksolver.TaskSolverActivity;
import com.chemistrysolver.theory.TheoryActivity;

// класс отображения главного меню, являющимся стартовым
public class MainField extends Fragment implements View.OnClickListener {

    // отображаемые в главном меню LinearLayout, при нажатии на которые пользователь меняет режимы работы
    private LinearLayout tableElement, tableSolubility, solver, coefficients, theory, formulas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainField = inflater.inflate(R.layout.main_field, container, false);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Candara.ttf");

        // переменные для изменения вида текста в LinearLayout
        TextView tableElementt, tableSolubilityt, solvert, coefficientst, theoryt, formulast;
        tableElementt = mainField.findViewById(R.id.tableelementt);
        tableElementt.setTypeface(typeface);
        tableSolubilityt = mainField.findViewById(R.id.tablesolubilityt);
        tableSolubilityt.setTypeface(typeface);
        solvert = mainField.findViewById(R.id.solvert);
        solvert.setTypeface(typeface);
        coefficientst = mainField.findViewById(R.id.coefficientst);
        coefficientst.setTypeface(typeface);
        theoryt = mainField.findViewById(R.id.theoryt);
        theoryt.setTypeface(typeface);
        formulast = mainField.findViewById(R.id.formulast);
        formulast.setTypeface(typeface);

        tableElement = mainField.findViewById(R.id.tableelement);
        tableSolubility = mainField.findViewById(R.id.tablesolubility);
        solver = mainField.findViewById(R.id.solver);
        coefficients = mainField.findViewById(R.id.coefficients);
        theory = mainField.findViewById(R.id.theory);
        formulas = mainField.findViewById(R.id.formulas);

        tableSolubility.setOnClickListener(this);
        tableElement.setOnClickListener(this);
        solver.setOnClickListener(this);
        coefficients.setOnClickListener(this);
        theory.setOnClickListener(this);
        formulas.setOnClickListener(this);

        return mainField;
    }

    @Override
    public void onClick(View view) {
        // меняем режим работы при нажатии на LinearLayout
        if (view.getId() == tableElement.getId()){
            Intent intent = new Intent(getContext(), TableElementActivity.class);
            startActivity(intent);
        }
        if (view.getId() == tableSolubility.getId()){
            Intent intent = new Intent(getContext(), TableSolubilityActivity.class);
            startActivity(intent);
        }
        if (view.getId() == coefficients.getId()){
            Intent intent = new Intent(getContext(), ReactionBalancingActivity.class);
            startActivity(intent);
        }
        if (view.getId() == solver.getId()){
            Intent intent = new Intent(getContext(), TaskSolverActivity.class);
            startActivity(intent);
        }
        if (view.getId() == formulas.getId()){
            Intent intent = new Intent(getContext(), FormulasActivity.class);
            startActivity(intent);
        }
        if (view.getId() == theory.getId()){
            Intent intent = new Intent(getContext(), TheoryActivity.class);
            startActivity(intent);
        }
    }
}
