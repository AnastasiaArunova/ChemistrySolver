package com.chemistrysolver.tasksolver;

import java.util.ArrayList;
import java.util.HashMap;

import com.chemistrysolver.tableelement.TableElementActivity;

// класс, решающий задачи
public class Solve {

    // цифры для нахождения их в соединениях; переменная, обозначающая газ, по которому находится плотность газа по другому
    private String number = "1234567890", gas;
    // то, что надо найти
    public HashMap<String, ArrayList<Integer>> find;
    // то, что дано
    public HashMap<String, HashMap<Integer, Double>> given;
    // текст ответа по пунктам
    private ArrayList<ArrayList<String>> solutions;
    // реакция
    private String reaction = "";
    //хранит в себе коэффициенты из реакции
    private Integer[] coefficients;
    // хранит в себе соединения
    private ArrayList<String> leftPart, rightPart, agent;
    // Хранит в себе соединения, которые распределены на элемент и его количество
    private ArrayList<HashMap<String, Integer>> elements;
    // экзмпляр класса TableElementActivity для доступа к его полям
    private TableElementActivity tableElementActivity;
    // численное значение ответов
    private Double[] answers;
    // ответ, если задача не решена
    private String answer = "";
    // переменные для отслеживания того, что присутствует в задаче выход реакции
    private String b1 = "", b2 = "";
    private OnSolveEnd onSolveEnd;
    // если в задачи нет реакция
    private boolean isNotReaction;

    // интерфейс для передачи данных в SolveActivity
    public interface OnSolveEnd {
        void getAnswer(String answer);
        void getSolutions(ArrayList<ArrayList<String>> solutions);
    }

    // конструктор класса
    public Solve(HashMap<String, HashMap<Integer, Double>> given, HashMap<String, ArrayList<Integer>> find,
                 ArrayList<String> elements, String reaction, boolean isNotReaction, ArrayList<String> leftPart, ArrayList<String> rightPart,
                 String gas, OnSolveEnd onSolveEnd){
        agent = new ArrayList<>();
        this.find = new HashMap<>();
        this.given = new HashMap<>();
        this.elements = new ArrayList<>();
        this.leftPart = new ArrayList<>();
        this.rightPart = new ArrayList<>();
        tableElementActivity = new TableElementActivity();
        solutions = new ArrayList<>();
        answer = "";

        this.given = given;
        this.find = find;
        agent = elements;
        this.reaction = reaction;
        this.isNotReaction = isNotReaction;
        this.leftPart = leftPart;
        this.rightPart = rightPart;
        this.gas = gas;
        this.onSolveEnd = onSolveEnd;

        answers = new Double[getFindSize()];

        try {
            startAlgorithm();
        } catch (Exception e){
            if (answer.equals("")) answer = "Невозможно решить задачу";
            onSolveEnd.getAnswer(answer);
            onSolveEnd.getSolutions(solutions);
        }

    }

    // начало решения задач
    private void startAlgorithm() {

        for (String key : given.keySet()) {
            if (given.get(key).containsKey(6) || given.get(key).containsKey(7) || given.get(key).containsKey(8))
                b1 = key;
            if (given.get(key).containsKey(13) || given.get(key).containsKey(14) || given.get(key).containsKey(15))
                b2 = key;
        }
        if (!isNotReaction) {
            elements = new ArrayList<>();
            for (int i = 0; i < leftPart.size(); i++) {
                leftPart.set(i, parser(leftPart.get(i)));
                elements.add(getInformation(leftPart.get(i), 0));
            }
            for (int i = 0; i < rightPart.size(); i++) {
                rightPart.set(i, parser(rightPart.get(i)));
                elements.add(getInformation(rightPart.get(i), 0));
            }
            coefficients = new Integer[elements.size()];
            findCoefficients();
            answer += "Ответ: ";
            solveReaction();
            for (int i = 0; i < answers.length; i++){
                if (answers[i] > 0.0 && answers[i] != null)
                answer += answers[i] + "; ";
            }
            onSolveEnd.getAnswer(answer);
            onSolveEnd.getSolutions(solutions);
        } else {
            int j = 0;
            answer += "Ответ: ";
            for (int i = 0; i < agent.size(); i++) {
                agent.set(i, parser(agent.get(i)));
                elements.add(getInformation(agent.get(i), 0));
            }
            for (String key : find.keySet()) {
                for (int i = 0; i < find.get(key).size(); i++) {
                    if (find.get(key).get(i) == 28 || find.get(key).get(i) == 29) i++;
                    if (find.get(key).get(i) == 16) answers[j] = getMolarMass(false, key, 16);
                    else if (find.get(key).get(i) == 1){
                        if (given.get(key).containsKey(22)) {
                            if (given.get(key).containsKey(21)) {
                                ArrayList<String> tmp = new ArrayList<>();
                                tmp.add("По формуле n = C × mp-ля");
                                tmp.add(""); tmp.add("n(" + key + ") = C(" + key + ") × mp-ля\n" + given.get(key).get(21) + " × " + given.get(key).get(22) + " = " + round(given.get(key).get(21) * given.get(key).get(22)) + " моль");
                                tmp.add("");tmp.add(""); solutions.add(tmp);
                                given.get(key).put(1, round(given.get(key).get(21) * given.get(key).get(22)));
                                answers[j] = round(given.get(key).get(21) * given.get(key).get(22));
                            }
                        } else answers[j] = getAmount(key, -1);
                    }
                    else if (find.get(key).get(i) == 2) answers[j] = round(getMassOfAgent(key, -1));
                    else if (find.get(key).get(i) == 9) answers[j] = round(getVolume(key, -1));
                    else if (find.get(key).get(i) == 18)
                        answers[j] = round(getCountOfStructure(key, -1));
                    else if (find.get(key).get(i) == 0)
                        answers[j] = round(getMolarMass(false, key, -1));
                    else if (find.get(key).get(i) == 26) answers[j] = round(getDensity(key, -1));
                    else if (find.get(key).get(i) == 17) answers[j] = round(getMassOfAtom(key, -1));
                    else if (find.get(key).get(i) == 3) answers[j] = round(getMassOfSolution(key, -1));
                    else if (find.get(key).get(i) == 5) answers[j] = round(getMassFraction(key, -1));
                    else if (find.get(key).get(i) == 24) answers[j] = round(getPressure(key, -1));
                    else if (find.get(key).get(i) == 25) answers[j] = round(getTemperature(key, -1));
                    else if (find.get(key).get(i) == 27) answers[j] = getDensitySolution(key, -1);
                    else if (find.get(key).get(i) == 12) answers[j] = round(getVolumeFraction(key, -1));
                    else if (find.get(key).get(i) == 19){
                        answers[j] = -1.0;
                        getMoleFraction(key);
                    }
                    else if (find.get(key).get(i) == 20) answers[j] = getMolarConcentration(key, -1);
                    else if (find.get(key).get(i) == 11) answers[j] = getVolumeOfSolution(key, -1);
                    else if (find.get(key).get(i) == 21) answers[j] = getMassOfSolvent(key, -1);
                    else if (find.get(key).get(i) == 22) answers[j] = getMolalConcentration(key, -1);
                    else if (find.get(key).get(i) == 23) answers[j] = getTiter(key, -1);
                    else if (find.get(key).get(i) == 8) answers[j] = getMassPrak(key, -1);
                    else if (find.get(key).get(i) == 7) answers[j] = getMassTheor(key, -1);
                    else if (find.get(key).get(i) == 6) answers[j] = getMassFractionProduct(key, -1);
                    else if (find.get(key).get(i) == 15) answers[j] = getVolumePrak(key, -1);
                    else if (find.get(key).get(i) == 14) answers[j] = getVolumeTheor(key, -1);
                    else if (find.get(key).get(i) == 13) answers[j] = getVolumeFractionProduct(key, -1);
                }
                j++;
            }
            for (int i = 0; i < answers.length; i++) {
                if (answers[i] > 0.0 && answers[i] != null)
                answer += answers[i] + "; ";
            }
            onSolveEnd.getAnswer(answer);
            onSolveEnd.getSolutions(solutions);
        }
    }

    // Нахождение количества искомого элемент. key - то, что ищем, key1 - то, по чему ищем
    private Double getAmountInReaction(String key, String key1) {
        Integer c1 = 0, c2 = 0;
        Double c3 = 0.0;
        for (int i = 0; i < leftPart.size(); i++) {
            if (leftPart.get(i).equals(parser(key))){
                c2 = coefficients[i];
            }
            if (leftPart.get(i).equals(parser(key1))) c1 = coefficients[i];
        }
        for (int i = 0; i < rightPart.size(); i++) {
            if (rightPart.get(i).equals(parser(key)))
                c2 = coefficients[i + leftPart.size()];
            if (rightPart.get(i).equals(parser(key1)))
                c1 = coefficients[i + leftPart.size()];
        }
        if (c2 == 0) if (given.containsKey("Q") && given.get("Q").containsKey(28)) c2 = (int) Math.round(given.get("Q").get(28));

        if (key.equals("Q") && given.containsKey("Q") && !given.get("Q").containsKey(28)){
            if (key.equals("Q") && given.get("Q").containsKey(29)) c3 = given.get("Q").get(29);
            else c3 = Double.valueOf(c2);
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("Составим пропорцию, чтобы найти количество " + key + " ");tmp.add("");tmp.add("");
            tmp.add(given.get(key1).get(1) + " / " + c1);tmp.add(" = ");
            tmp.add(c3 + " / " + "n(" + key + ")");tmp.add(" ⇒ n(" + key + ") = " + c1 / given.get(key1).get(1) * c3 + " моль");
            solutions.add(tmp);
            return c1 / given.get(key1).get(1) * c3;
        }

        if (key1.equals("Q")) {
            if (key.equals("Q") && given.get("Q").containsKey(28)) c3 = given.get("Q").get(28);
            else c3 = Double.valueOf(c2);
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("Составим пропорцию, чтобы найти количество " + key + "  "); tmp.add("");
            tmp.add(""); tmp.add(given.get("Q").get(29) + " / " + given.get("Q").get(28));
            tmp.add(" = "); tmp.add("n(" + key + ") / " + c3);
            tmp.add(" ⇒ n(" + key + ") = " + given.get("Q").get(29) * c3 / given.get("Q").get(28) + " моль");
            solutions.add(tmp);
            return given.get("Q").get(29) * c3 / given.get("Q").get(28);
        } else {
            if (!key.equals(key1)) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("Составим пропорцию, чтобы найти количество " + key + " ");tmp.add("");tmp.add("");
                tmp.add(given.get(key1).get(1) + " / " + c1);tmp.add(" = ");
                tmp.add("n(" + key + ") / " + c2);tmp.add(" ⇒ n(" + key + ") = " + given.get(key1).get(1) * c2 / c1 + " моль");
                solutions.add(tmp);
            }
            return given.get(key1).get(1) * c2 / c1;
        }
    }

    // Решение задачи с реакцией
    private void solveReaction() {
        String key1 = "";
        if (given.containsKey("Q")) {
            if (given.get("Q").containsKey(29)) {
                if (given.get("Q").containsKey(28)) {
                    key1 = "Q";
                }
            }
        }
        String key2 = "";
        if (key1.equals(""))
            for (String key : given.keySet()) {
                if (given.get(key).containsKey(6) && given.get(key).containsKey(8)) {
                    given.get(key).put(7, given.get(key).get(8) / given.get(key).get(6));
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mтеор = "); tmp.add("mпрак / η");
                    tmp.add("mтеор(" + key + ") = "); tmp.add("mпрак(" + key + ") / η(" + key + ")");
                    tmp.add(" = "); tmp.add(given.get(key).get(8) + " / " + given.get(key).get(6));
                    tmp.add(" = " + round(given.get(key).get(8) / given.get(key).get(6)) + "г");
                    solutions.add(tmp);
                } else if (given.get(key).containsKey(8)) key2 = key;
                if (given.get(key).containsKey(13) && given.get(key).containsKey(15)) {
                    given.get(key).put(14, given.get(key).get(15) / given.get(key).get(13));
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле Vтеор = "); tmp.add("Vпрак / η");
                    tmp.add("Vтеор(" + key + ") = "); tmp.add("Vпрак(" + key + ") / η(" + key + ")");
                    tmp.add(" = "); tmp.add(given.get(key).get(15) + " / " + given.get(key).get(13));
                    tmp.add(" = " + round(given.get(key).get(15) / given.get(key).get(13)) + "л");
                    solutions.add(tmp);
                } else if (given.get(key).containsKey(15)) key2 = key;
                if (given.get(key).containsKey(1)) {
                    key1 = key;
                    break;
                } else if (!key.equals("Q") && !key.equals(key2)) {
                    Double amount = getAmount(key, -1);
                    if (amount > 0.0) {
                        given.get(key).put(1, amount);
                        key1 = key;
                        break;
                    }
                }
            }
        int j = 0;
        if (!key1.equals("")) {
            for (String key : find.keySet()) {
                if (given.containsKey(key)) {
                    given.get(key).put(1, getAmountInReaction(key, key1));
                } else {
                    HashMap<Integer, Double> put = new HashMap<>();
                    put.put(1, getAmountInReaction(key, key1));
                    given.put(key, put);
                }
                for (int i = 0; i < find.get(key).size(); i++) {
                    if (find.get(key).get(i) == 0) answers[j] = round(getMolarMass(true, key, -1));
                    else if (find.get(key).get(i) == 1) answers[j] = given.get(key).get(1);
                    else if (find.get(key).get(i) == 2) answers[j] = round(getMassOfAgent(key, -1));
                    else if (find.get(key).get(i) == 3)
                        answers[j] = round(getMassOfSolution(key, -1));
                    else if (find.get(key).get(i) == 5)
                        answers[j] = round(getMassFraction(key, -1));
                    else if (find.get(key).get(i) == 6)
                        answers[j] = round(getMassFractionProduct(key, -1));
                    else if (find.get(key).get(i) == 7) answers[j] = round(getMassTheor(key, -1));
                    else if (find.get(key).get(i) == 8) answers[j] = round(getMassPrak(key, -1));
                    else if (find.get(key).get(i) == 9) answers[j] = round(getVolume(key, -1));
                    else if (find.get(key).get(i) == 11)
                        answers[j] = round(getVolumeFraction(key, -1));
                    else if (find.get(key).get(i) == 12)
                        answers[j] = round(getVolumeFraction(key, -1));
                    else if (find.get(key).get(i) == 13)
                        answers[j] = round(getVolumeFractionProduct(key, -1));
                    else if (find.get(key).get(i) == 14) answers[j] = round(getVolumePrak(key, -1));
                    else if (find.get(key).get(i) == 15)
                        answers[j] = round(getVolumeTheor(key, -1));
                    else if (find.get(key).get(i) == 16)
                        answers[j] = round(getMolarMass(true, key, 16));
                    else if (find.get(key).get(i) == 17) answers[j] = round(getMassOfAtom(key, -1));
                    else if (find.get(key).get(i) == 18)
                        answers[j] = round(getCountOfStructure(key, -1));
                    else if (find.get(key).get(i) == 19) {
                        answers[j] = -1.0;
                        getMoleFraction(key);
                    } else if (find.get(key).get(i) == 20)
                        answers[j] = getMolarConcentration(key, -1);
                    else if (find.get(key).get(i) == 21) answers[j] = getMassOfSolvent(key, -1);
                    else if (find.get(key).get(i) == 22)
                        answers[j] = getMolalConcentration(key, -1);
                    else if (find.get(key).get(i) == 23) answers[j] = getTiter(key, -1);
                    else if (find.get(key).get(i) == 24) answers[j] = round(getPressure(key, -1));
                    else if (find.get(key).get(i) == 25)
                        answers[j] = round(getTemperature(key, -1));
                    else if (find.get(key).get(i) == 26) answers[j] = round(getDensity(key, -1));
                    else if (find.get(key).get(i) == 27) answers[j] = getDensitySolution(key, -1);
                    else if (find.get(key).get(i) == 29) {
                        if (given.containsKey(key) && given.get(key).containsKey(1))
                            answers[j] = given.get(key).get(1);
                    }
                }
                j++;
            }
        } else {
            for (String key : find.keySet()) {
                for (int i = 0; i < find.get(key).size(); i++) {
                    if (find.get(key).get(i) == 0) answers[j] = round(getMolarMass(true, key, -1));
                    else if (find.get(key).get(i) == 16)
                        answers[j] = round(getMolarMass(true, key, 16));
                    else if (find.get(key).get(i) == 19) {
                        answers[j] = -1.0;
                        getMoleFraction(key);
                    }
                }
            }
        }
    }

    // округление ответа
    public Double round(Double value) {
        if (Double.valueOf(Math.round(value * 100)) / 100 > 0.0) {
            return Double.valueOf(Math.round(value * 100)) / 100;
        } else return value;
    }

    // количество величин, которые надо найти, для размера массива ответов
    private int getFindSize() {
        int i = 0;
        for (String key : find.keySet()) {
            i += find.get(key).size();
        }
        return i;
    }

    // Молярная масса (0)
    public Double getMolarMass(boolean f, String key, int n) { // f для обозначения неизвестного вещества
        tableElementActivity.fillElements();
        if (n == 16) { // Плотность одного газа по другому (16)
            Double molarMass = 0.0, molarMass1 = 0.0;
            int i = -1;
            i = agent.indexOf(parser(key));
            if (elements.size() == 1) i = 0;
            if (i >= 0) {
                for (String k : elements.get(i).keySet()) {
                    for (int j = 0; j < tableElementActivity.elements.size(); j++) {
                        if (tableElementActivity.elements.get(j).designation.equals(k)) {
                            molarMass += tableElementActivity.elements.get(j).atomicWeight * elements.get(i).get(k);
                        }
                    }
                }
            }
            if (key.equals("возд")){
                molarMass = 29.0;
                ArrayList<String> tmp1 = new ArrayList<>();
                tmp1.add("Условно молярная масса воздуха равна ");tmp1.add(""); tmp1.add("M(возд) = 29 г/моль");
                tmp1.add(""); tmp1.add(""); tmp1.add(""); tmp1.add(""); solutions.add(tmp1);
            }
            if (gas.equals("возд")) {
                molarMass1 = 29.0;
                ArrayList<String> tmp1 = new ArrayList<>();
                tmp1.add("Условно молярная масса воздуха равна ");tmp1.add(""); tmp1.add("M(возд) = 29 г/моль");
                tmp1.add(""); tmp1.add(""); tmp1.add(""); tmp1.add(""); solutions.add(tmp1);
            } else {
                elements.add(getInformation(parser(gas), 0));
                i = agent.indexOf(parser(gas));
                if (i >= 0) {
                    for (String k : elements.get(i).keySet()) {
                        for (int j = 0; j < tableElementActivity.elements.size(); j++) {
                            if (tableElementActivity.elements.get(j).designation.equals(k)) {
                                molarMass1 += tableElementActivity.elements.get(j).atomicWeight * elements.get(i).get(k);
                            }
                        }
                    }
                }

            }
            if (molarMass > 0.0 && molarMass1 > 0.0) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По таблице Менделеева найдём молярную массу " + key + " "); tmp.add("");
                tmp.add("M(" + key + ") = " + round(molarMass) + " г/моль"); tmp.add(""); tmp.add("");
                tmp.add(""); tmp.add(""); solutions.add(tmp);
                if (!gas.equals("возд")) {
                    ArrayList<String> tmp1 = new ArrayList<>();
                    tmp1.add("По таблице Менделеева найдём молярную массу " + gas + " ");
                    tmp1.add(""); tmp1.add("M(" + gas + ") = " + round(molarMass1) + " г/моль"); tmp1.add("");
                    tmp1.add(""); tmp1.add(""); tmp1.add(""); solutions.add(tmp1);
                }
                ArrayList<String> tmp1 = new ArrayList<>();
                tmp1.add("Найдём значение D по формуле "); tmp1.add("");
                tmp1.add("D" + gas + "(" + key + ") = "); tmp1.add("M(" + key + ") / M(" + gas + ")");
                tmp1.add(" = "); tmp1.add(molarMass + " / " + molarMass1);
                tmp1.add(" = " + round(molarMass / molarMass1)); solutions.add(tmp1);
                return round(molarMass / molarMass1);
            }
        }
        if (given.get(key).containsKey(0) && n != 0) return given.get(key).get(0);
        if (f) { // по таблице Менделеева
            Double molarMass = 0.0;
            int i = -1;
            if (leftPart.contains(parser(key))) i = leftPart.indexOf(parser(key));
            else if (rightPart.contains(parser(key)))
                i = rightPart.indexOf(parser(key)) + leftPart.size();
            if (elements.size() == 1) i = 0;
            if (i >= 0) {
                for (String k : elements.get(i).keySet()) {
                    for (int j = 0; j < tableElementActivity.elements.size(); j++) {
                        if (tableElementActivity.elements.get(j).designation.equals(k)) {
                            molarMass += tableElementActivity.elements.get(j).atomicWeight * elements.get(i).get(k);
                        }
                    }
                }
                if (molarMass > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По таблице Менделеева найдём молярную массу " + key + " "); tmp.add("");
                    tmp.add("M(" + key + ") = "); tmp.add("");
                    tmp.add(round(molarMass) + " г/моль"); tmp.add("");
                    tmp.add(""); solutions.add(tmp);
                    given.get(key).put(0, round(molarMass));
                    return round(molarMass);
                }
            }
        } else {
            if (given.get(key).containsKey(2) && n != 2) { // в задаче нужно найти молярную массу
                if (given.get(key).containsKey(1) && n != 1) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле  M = "); tmp.add("m / n");
                    tmp.add("M(" + key + ") = "); tmp.add("m(" + key + ") / n(" + key + ")");
                    tmp.add(" = "); tmp.add(given.get(key).get(2) + "/" + given.get(key).get(1));
                    tmp.add(" = " + round(given.get(key).get(2) / given.get(key).get(1)) + " г/моль"); solutions.add(tmp);
                    given.get(key).put(0, round(given.get(key).get(2) / given.get(key).get(1)));
                    return round(given.get(key).get(2) / given.get(key).get(1)); // M=m/n
                } else if (n != 1) {
                    Double amount = -1.0;
                    amount = getAmount(key, 0);
                    if (amount > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле  M = "); tmp.add("m / n");
                        tmp.add("M(" + key + ") = "); tmp.add("m(" + key + ") / n(" + key + ")");
                        tmp.add(" = "); tmp.add(given.get(key).get(2) + "/" + amount);
                        tmp.add(" = " + round(given.get(key).get(2) / amount) + " г/моль"); solutions.add(tmp);
                        given.get(key).put(1, amount);
                        given.get(key).put(0, round(given.get(key).get(2) / amount));
                        return round(given.get(key).get(2) / amount); // M=m/n
                    }
                }
            } else if (n != 2) {
                Double mass = -1.0;
                mass = getMassOfAgent(key, 0);
                if (mass > 0.0) {
                    if (given.get(key).containsKey(1) && n != 1) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле  M = "); tmp.add("m / n");
                        tmp.add("M(" + key + ") = "); tmp.add("m(" + key + ") / n(" + key + ")");
                        tmp.add(" = "); tmp.add(mass + " / " + given.get(key).get(1));
                        tmp.add(" = " + round(mass / given.get(key).get(1)) + " г/моль"); solutions.add(tmp);
                        given.get(key).put(2, mass);
                        given.get(key).put(0, round(mass / given.get(key).get(2)));
                        return round(mass / given.get(key).get(2)); // M=m/n
                    } else if (n != 1) {
                        Double amount = -1.0;
                        amount = getAmount(key, 0);
                        if (amount > 0.0) {
                            ArrayList<String> tmp = new ArrayList<>();
                            tmp.add("По формуле  M = "); tmp.add("m / n");
                            tmp.add("M(" + key + ") = "); tmp.add("m(" + key + ") / n(" + key + ")");
                            tmp.add(" = "); tmp.add(mass + " / " + amount);
                            tmp.add(" = " + round(mass / amount) + " г/моль"); solutions.add(tmp);
                            given.get(key).put(2, mass);
                            given.get(key).put(1, amount);
                            given.get(key).put(0, round(mass / amount));
                            return round(mass / amount);
                        }
                    }
                }
            }
            if (given.get(key).containsKey(17) && n != 17) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле M = m₀ × Nₐ"); tmp.add("");
                tmp.add("M = " + given.get(key).get(17) + " × (6,02 × 10²³) = " + round(given.get(key).get(17) * 6.02 * Math.pow(10, 23)) + " г/моль");
                tmp.add("");tmp.add(""); tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(0, round(given.get(key).get(17) * 6.02) * Math.pow(10, 23));
                return round(given.get(key).get(17) * 6.02) * Math.pow(10, 23);
            }
        }
        return -1.0;
    }

    // Количество вещества (1)
    public Double getAmount(String key, int n) {
        if (given.get(key).containsKey(1)) return given.get(key).get(1);
        if (b1.equals(key)) {
            if (given.get(key).containsKey(7) && n != 7) {
                if (given.get(key).containsKey(0) && n != 0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле n = "); tmp.add("mтеор / M"); tmp.add("n(" + key + ") = ");
                    tmp.add("mтеор(" + key + ") / M(" + key + ")"); tmp.add(" = ");
                    tmp.add(given.get(key).get(7) + " / " + given.get(key).get(0));
                    tmp.add(" = " + round(given.get(key).get(7) / given.get(key).get(0)) + " моль"); solutions.add(tmp);
                    given.get(key).put(1, round(given.get(key).get(7) / given.get(key).get(0)));
                    return round(given.get(key).get(7) / given.get(key).get(0)); // n = m / M
                } else if (n != 0) {
                    Double molarMass = -1.0;
                    molarMass = getMolarMass(true, key, 1);
                    if (molarMass > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле n = "); tmp.add("mтеор / M"); tmp.add("n(" + key + ") = ");
                        tmp.add("mтеор(" + key + ") / M(" + key + ")"); tmp.add(" = ");
                        tmp.add(given.get(key).get(7) + " / " + molarMass);
                        tmp.add(" = " + round(given.get(key).get(7) / molarMass) + " моль"); solutions.add(tmp);
                        given.get(key).put(0, molarMass);
                        given.get(key).put(1, round(given.get(key).get(7) / molarMass));
                        return round(given.get(key).get(7) / molarMass); // // n = m / M
                    }
                }
            } else if (n != 7) {
                Double massTheor = -1.0;
                massTheor = getMassTheor(key, 1);
                if (massTheor > 0.0) {
                    if (given.get(key).containsKey(0) && n != 0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле n = "); tmp.add("mтеор / M"); tmp.add("n(" + key + ") = ");
                        tmp.add("mтеор(" + key + ") / M(" + key + ")"); tmp.add(" = ");
                        tmp.add(massTheor + " / " + given.get(key).get(0));
                        tmp.add(" = " + round(massTheor / given.get(key).get(0)) + " моль"); solutions.add(tmp);
                        given.get(key).put(7, massTheor);
                        given.get(key).put(1, round(massTheor / given.get(key).get(0)));
                        return round(massTheor / given.get(key).get(0));

                    } else if (n != 0) {
                        Double molarMass = -1.0;
                        molarMass = getMolarMass(true, key, 1);
                        if (molarMass > 0.0) {
                            ArrayList<String> tmp = new ArrayList<>();
                            tmp.add("По формуле n = "); tmp.add("mтеор / M"); tmp.add("n(" + key + ") = ");
                            tmp.add("mтеор(" + key + ") / M(" + key + ")"); tmp.add(" = ");
                            tmp.add(massTheor + " / " + molarMass);
                            tmp.add(" = " + round(massTheor / molarMass) + " моль"); solutions.add(tmp);
                            given.get(key).put(7, massTheor);
                            given.get(key).put(0, molarMass);
                            given.get(key).put(1, round(massTheor / molarMass));
                            return round(massTheor / molarMass);
                        }
                    }
                }
            }
        }
        if (b2.equals(key)) {
            if (given.get(key).containsKey(14) && n != 14) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле n = "); tmp.add("Vтеор / Vₐ"); tmp.add("n(" + key + ") = ");
                tmp.add("Vтеор(" + key + ") / 22.4"); tmp.add(" = ");
                tmp.add(given.get(key).get(14) + " / 22.4");
                tmp.add(" = " + round(given.get(key).get(14) / 22.4) + " моль"); solutions.add(tmp);
                given.get(key).put(1, round(given.get(key).get(14) / 22.4));
                return round(given.get(key).get(14) / 22.4);
            } else if (n != 14) {
                Double volumeTheor = -1.0;
                volumeTheor = getVolumeTheor(key, 1);
                if (volumeTheor > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле n = "); tmp.add("Vтеор / Vₐ"); tmp.add("n(" + key + ") = ");
                    tmp.add("Vтеор(" + key + ") / 22.4"); tmp.add(" = ");
                    tmp.add(volumeTheor + " / 22.4");
                    tmp.add(" = " + round(volumeTheor / 22.4) + " моль"); solutions.add(tmp);
                    given.get(key).put(14, volumeTheor);
                    given.get(key).put(1, round(volumeTheor / 22.4));
                    return round(volumeTheor / 22.4);
                }
            }
        }
        if (given.get(key).containsKey(1)) return given.get(key).get(1); // n = n
        if (given.get(key).containsKey(18) && n != 18) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("По формуле n = "); tmp.add("N / Nₐ"); tmp.add("n(" + key + ") = ");
            tmp.add(given.get(key).get(18) + " / 6,02 × 10²³"); tmp.add(" = ");
            tmp.add(given.get(key).get(18) + " / 6,02 × 10²³");
            tmp.add(" = " + round(given.get(key).get(18) / 6.02 / Math.pow(10, 23)) + " моль"); solutions.add(tmp);
            given.get(key).put(1, round(given.get(key).get(18) / 6.02 / Math.pow(10, 23)));
            return round(given.get(key).get(18) / 6.02 / Math.pow(10, 23)); //* Math.pow(10, -23); // n = N / Na
        }
        if (given.get(key).containsKey(9) && n != 9) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("По формуле n = "); tmp.add("V / Vₐ"); tmp.add("n(" + key + ") = ");
            tmp.add("V(" + key + ") / 22.4"); tmp.add(" = ");
            tmp.add(given.get(key).get(9) + " / 22.4");
            tmp.add(" = " + round(given.get(key).get(9) / 22.4) + " моль"); solutions.add(tmp);
            given.get(key).put(1, round(given.get(key).get(9) / 22.4));
            return round(given.get(key).get(9) / 22.4); // n = V / Va
        } else if (n != 9) {
            Double volume = 0.0;
            volume = getVolume(key, 1);
            if (volume > 0.0) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле n = "); tmp.add("V / Vₐ"); tmp.add("n(" + key + ") = ");
                tmp.add("V(" + key + ") / 22.4"); tmp.add(" = ");
                tmp.add(volume + " / 22.4");
                tmp.add(" = " + round(volume / 22.4) + " моль"); solutions.add(tmp);
                given.get(key).put(1, round(volume / 22.4));
                return round(volume / 22.4);
            }
        }
        if (given.get(key).containsKey(2) && n != 2) {
            if (given.get(key).containsKey(0) && n != 0) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле n = "); tmp.add("m / M"); tmp.add("n(" + key + ") = ");
                tmp.add("m(" + key + ") / M(" + key + ")"); tmp.add(" = ");
                tmp.add(given.get(key).get(2) + " / " + given.get(key).get(0));
                tmp.add(" = " + round(given.get(key).get(2) / given.get(key).get(0)) + " моль"); solutions.add(tmp);
                given.get(key).put(1, round(given.get(key).get(2) / given.get(key).get(0)));
                return round(given.get(key).get(2) / given.get(key).get(0)); // n = m / M
            } else if (n != 0) {
                Double molarMass = -1.0;
                molarMass = getMolarMass(true, key, 1);
                if (molarMass > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле n = "); tmp.add("m / M"); tmp.add("n(" + key + ") = ");
                    tmp.add("m(" + key + ") / M(" + key + ")"); tmp.add(" = ");
                    tmp.add(given.get(key).get(2) + " / " + molarMass);
                    tmp.add(" = " + round(given.get(key).get(2) / molarMass) + " моль"); solutions.add(tmp);
                    given.get(key).put(0, molarMass);
                    given.get(key).put(1, round(given.get(key).get(2) / molarMass));
                    return round(given.get(key).get(2) / molarMass); // // n = m / M
                }
            }
        } else if (n != 2) {
            Double mass = -1.0;
            mass = getMassOfAgent(key, 1);
            if (mass > 0.0) {
                if (given.get(key).containsKey(0) && n != 0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле n = "); tmp.add("m / M"); tmp.add("n(" + key + ") = ");
                    tmp.add("m(" + key + ") / M(" + key + ")"); tmp.add(" = ");
                    tmp.add(mass + " / " + given.get(key).get(0));
                    tmp.add(" = " + round(mass / given.get(key).get(0)) + " моль"); solutions.add(tmp);
                    given.get(key).put(2, mass);
                    given.get(key).put(1, round(mass / given.get(key).get(0)));
                    return round(mass / given.get(key).get(0)); // n = m / M
                } else if (n != 0) {
                    Double molarMass = -1.0;
                    molarMass = getMolarMass(true, key, 1);
                    if (molarMass > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле n = "); tmp.add("m / M"); tmp.add("n(" + key + ") = ");
                        tmp.add("m(" + key + ") / M(" + key + ")"); tmp.add(" = ");
                        tmp.add(mass + " / " + molarMass);
                        tmp.add(" = " + round(mass / molarMass) + " моль"); solutions.add(tmp);
                        given.get(key).put(2, mass);
                        given.get(key).put(0, molarMass);
                        given.get(key).put(1, round(mass / molarMass));
                        return round(mass / molarMass); // n = m / M
                    }
                }
            }
        }
        if (given.get(key).containsKey(24) && n != 24 && given.get(key).containsKey(25) && n != 25) {
            if (given.get(key).containsKey(9) && n != 9) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По уравнению Медлелеева-Клапейрона n = "); tmp.add("p × V / R × T"); tmp.add("n(" + key + ") = ");
                tmp.add(given.get(key).get(24) + " × " + given.get(key).get(9) + " / 8.31 × " + given.get(key).get(25)); tmp.add(" = ");
                tmp.add("");
                tmp.add(" = " + round(given.get(key).get(24) * given.get(key).get(9) / 8.31 / given.get(key).get(25)) + " моль"); solutions.add(tmp);
                given.get(key).put(1, round(given.get(key).get(24) * given.get(key).get(9) / 8.31 / given.get(key).get(25)));
                return round(given.get(key).get(24) * given.get(key).get(9) / 8.31 / given.get(key).get(25));
            } else if (n != 9) {
                Double volume = -1.0;
                volume = getVolume(key, 1);
                if (volume > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По уравнению Медлелеева-Клапейрона n = "); tmp.add("p × V / R × T"); tmp.add("n(" + key + ") = ");
                    tmp.add(given.get(key).get(24) + " × " + volume + " / 8.31 × " + given.get(key).get(25)); tmp.add(" = ");
                    tmp.add("");
                    tmp.add(" = " + round(given.get(key).get(24) * volume / 8.31 / given.get(key).get(25)) + " моль"); solutions.add(tmp);
                    given.get(key).put(9, volume);
                    given.get(key).put(1, round(given.get(key).get(24) * volume / 8.31 / given.get(key).get(25)));
                    return round(given.get(key).get(24) * volume / 8.31 / given.get(key).get(25));
                }
            }
        }
        if (given.get(key).containsKey(20) && n != 20) {
            if (given.get(key).containsKey(11) && n != 11) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле n = C × Vр-ра");
                tmp.add(""); tmp.add("n(" + key + ") = C(" + key + ") × Vр-ра\n" + given.get(key).get(20) + " × " + given.get(key).get(11) + " = " + round(given.get(key).get(20) * given.get(key).get(11)) + " моль");
                tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(1, round(given.get(key).get(20) * given.get(key).get(11)));
                return round(given.get(key).get(20) * given.get(key).get(11));
            } else if (n != 11) {
                Double volumeOfSolution = 0.0;
                volumeOfSolution = getVolumeOfSolution(key, 1);
                if (volumeOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле n = C × Vр-ра");
                    tmp.add(""); tmp.add("n(" + key + ") = C(" + key + ") × Vр-ра\n" + given.get(key).get(20) + " × " + volumeOfSolution + " = " + round(given.get(key).get(20) * volumeOfSolution) + " моль");
                    tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(20, round(given.get(key).get(20) * volumeOfSolution));
                    return round(given.get(key).get(20) * volumeOfSolution);
                }
            }
        }
        return -1.0;
    }

    // Масса вещества (2)
    public Double getMassOfAgent(String key, int n) {
        if (given.get(key).containsKey(2)) return given.get(key).get(2);
        if (given.get(key).containsKey(23) && n != 23) {
            if (given.get(key).containsKey(11) && n != 11) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле m = T × Vр-ра");
                tmp.add(""); tmp.add("m(" + key + ") = T(" + key + ") × Vр-ра\n" + given.get(key).get(23) + " × " + given.get(key).get(11) + " = " + round(given.get(key).get(23) * given.get(key).get(11)) + " г");
                tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(2, round(given.get(key).get(23) * given.get(key).get(11)));
                return round(given.get(key).get(23) * given.get(key).get(11));
            } else if (n != 11) {
                Double volumeOfSolution = 0.0;
                volumeOfSolution = getVolumeOfSolution(key, 2);
                if (volumeOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле m = T × Vр-ра");
                    tmp.add(""); tmp.add("m(" + key + ") = T(" + key + ") × Vр-ра\n" + given.get(key).get(23) + " × " + volumeOfSolution + " = " + round(given.get(key).get(23) * volumeOfSolution) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(2, round(given.get(key).get(23) / volumeOfSolution));
                    return round(given.get(key).get(23) / volumeOfSolution);
                }
            }
        }
        if (given.get(key).containsKey(1) && n != 1) {
            if (given.get(key).containsKey(0) && n != 0) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле m = M × n");
                tmp.add(""); tmp.add("m(" + key + ") = M(" + key + ") × n(" + key + ")\n" + given.get(key).get(0) + " × " + given.get(key).get(1) + " = " + round(given.get(key).get(0) * given.get(key).get(1)) + " г");
                tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(2, round(given.get(key).get(1) * given.get(key).get(0)));
                return round(given.get(key).get(1) * given.get(key).get(0)); // m = n * M
            } else if (n != 0) {
                Double molarMass = -1.0;
                molarMass = getMolarMass(true, key, 2);
                if (molarMass > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле m = M × n");
                    tmp.add(""); tmp.add("m(" + key + ") = M(" + key + ") × n(" + key + ")\n" + molarMass + " × " + given.get(key).get(1) + " = " + round(molarMass * given.get(key).get(1)) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(0, molarMass);
                    given.get(key).put(2, round(given.get(key).get(1) * molarMass));
                    return round(given.get(key).get(1) * molarMass); // n = m / M
                }
            }
        } else if (n != 1) {
            Double amount = -1.0;
            amount = getAmount(key, 2);
            if (amount > 0.0) {
                if (given.get(key).containsKey(0) && n != 0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле m = M × n");
                    tmp.add(""); tmp.add("m(" + key + ") = M(" + key + ") × n(" + key + ")\n" + given.get(key).get(0) + " × " + amount + " = " + round(given.get(key).get(0) * amount) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(1, amount);
                    given.get(key).put(2, round(amount * given.get(key).get(0)));
                    return round(amount * given.get(key).get(0)); // m = n * M
                } else if (n != 0) {
                    Double molarMass = -1.0;
                    molarMass = getMolarMass(true, key, 2);
                    if (molarMass > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле m = M × n");
                        tmp.add(""); tmp.add("m(" + key + ") = M(" + key + ") × n(" + key + ")\n" + molarMass + " × " + amount + " = " + round(molarMass * amount) + " г");
                        tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                        given.get(key).put(1, amount);
                        given.get(key).put(0, molarMass);
                        given.get(key).put(2, round(amount * molarMass));
                        return round(amount * molarMass); // // n = m / M
                    }
                }
            }
        }

        if (given.get(key).containsKey(2)) return given.get(key).get(2); // m = m
        if (given.get(key).containsKey(26) && n != 26) { // m = V * p
            if (given.get(key).containsKey(9) && n != 9) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле m = V × ρ");
                tmp.add(""); tmp.add("m(" + key + ") = V(" + key + ") × ρ(" + key + ")\n" + given.get(key).get(26) + " × " + given.get(key).get(9) + " = " + round(given.get(key).get(26) * given.get(key).get(9)) + " г");
                tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(2, round(given.get(key).get(26) * given.get(key).get(9)));
                return round(given.get(key).get(26) * given.get(key).get(9));
            } else if (n != 9) {
                Double volume = -1.0;
                volume = getVolume(key, 2);
                if (volume > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле m = V × ρ");
                    tmp.add(""); tmp.add("m(" + key + ") = V(" + key + ") × ρ(" + key + ")\n" + given.get(key).get(26) + " × " + volume + " = " + round(given.get(key).get(26) * volume) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(9, volume);
                    given.get(key).put(2, round(volume * given.get(key).get(26)));
                    return round(volume * given.get(key).get(26));
                }
            }
        }
        if (given.get(key).containsKey(5) && n != 5) { // m = w * mр-ра
            if (given.get(key).containsKey(3) && n != 3) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле m = ω × mр-ра");
                tmp.add(""); tmp.add("m(" + key + ") = ω(" + key + ") × mр-ра(" + key + ")\n" + given.get(key).get(5) + " × " + given.get(key).get(3) + " = " + round(given.get(key).get(3) * given.get(key).get(5)) + " г");
                tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(2, round(given.get(key).get(3) * given.get(key).get(5)));
                return round(given.get(key).get(3) * given.get(key).get(5));
            } else if (n != 3) {
                Double massOfSolution = -1.0;
                massOfSolution = getMassOfSolution(key, 2);
                if (massOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле m = ω × mр-ра");
                    tmp.add(""); tmp.add("m(" + key + ") = ω(" + key + ") × mр-ра(" + key + ")\n" + given.get(key).get(5) + " × " + massOfSolution + " = " + round(given.get(key).get(5) * massOfSolution) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(3, massOfSolution);
                    given.get(key).put(2, round(massOfSolution * given.get(key).get(5)));
                    return round(massOfSolution * given.get(key).get(5));
                }
            }
        } else if (n != 5) {
            Double massFraction = -1.0;
            massFraction = getMassFraction(key, 2);
            if (massFraction > 0.0) {
                if (given.get(key).containsKey(3) && n != 3) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле m = ω × mр-ра");
                    tmp.add(""); tmp.add("m(" + key + ") = ω(" + key + ") × mр-ра(" + key + ")\n" + massFraction + " × " + given.get(key).get(3) + " = " + round(given.get(key).get(3) * massFraction) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(5, massFraction);
                    given.get(key).put(2, round(given.get(key).get(3) * massFraction));
                    return round(given.get(key).get(3) * massFraction);
                } else if (n != 3) {
                    Double massOfSolution = -1.0;
                    massOfSolution = getMassOfSolution(key, 2);
                    if (massOfSolution > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле m = ω × mр-ра");
                        tmp.add(""); tmp.add("m(" + key + ") = ω(" + key + ") × mр-ра(" + key + ")\n" + massFraction + " × " + massOfSolution + " = " + round(massOfSolution * massFraction) + " г");
                        tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                        given.get(key).put(5, massFraction);
                        given.get(key).put(3, massOfSolution);
                        given.get(key).put(2, round(massOfSolution * massFraction));
                        return round(massOfSolution * massFraction);
                    }
                }
            }
        }
        if (given.get(key).containsKey(21) && n != 21) { // m = mр-ра - mр-ля
            if (given.get(key).containsKey(3) && n != 3) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле m = mр-ра - mр-ля");
                tmp.add(""); tmp.add("m(" + key + ") = mр-ра(" + key + ") - mр-ля(" + key + ")\n" + given.get(key).get(3) + " - " + given.get(key).get(21) + " = " + round(given.get(key).get(3) - given.get(key).get(21)) + " г");
                tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(2, round(given.get(key).get(3) - given.get(key).get(21)));
                return round(given.get(key).get(3) - given.get(key).get(21));
            } else if (n != 3) {
                Double massOfSolution = -1.0;
                massOfSolution = getMassOfSolution(key, 2);
                if (massOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле m = mр-ра - mр-ля");
                    tmp.add(""); tmp.add("m(" + key + ") = mр-ра(" + key + ") - mр-ля(" + key + ")\n"  + massOfSolution + " - "+ given.get(key).get(21) + " = " + round(massOfSolution - given.get(key).get(21)) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(3, massOfSolution);
                    given.get(key).put(2, round(massOfSolution - given.get(key).get(21)));
                    return round(massOfSolution - given.get(key).get(21));
                }
            }
        } else if (n != 21) {
            Double massSolvent = -1.0;
            massSolvent = getMassOfSolvent(key, 2);
            if (massSolvent > 0.0) {
                if (given.get(key).containsKey(3) && n != 3) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле m = mр-ра - mр-ля");
                    tmp.add(""); tmp.add("m(" + key + ") = mр-ра(" + key + ") - mр-ля(" + key + ")\n" + given.get(key).get(3) + " - " + massSolvent + " = " + round(given.get(key).get(3) - massSolvent) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(2, round(given.get(key).get(3) - massSolvent));
                    return round(given.get(key).get(3) - massSolvent);
                } else if (n != 3) {
                    Double massOfSolution = -1.0;
                    massOfSolution = getMassOfSolution(key, 2);
                    if (massOfSolution > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле m = mр-ра - mр-ля");
                        tmp.add(""); tmp.add("m(" + key + ") = mр-ра(" + key + ") - mр-ля(" + key + ")\n" + massOfSolution + " - " + massSolvent + " = " + round(massOfSolution - massSolvent) + " г");
                        tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                        given.get(key).put(2, round(massOfSolution - massSolvent));
                        return round(massOfSolution - massSolvent);
                    }
                }
            }
        }
        if (b1.equals(key)) {
            if (given.get(key).containsKey(7) && n != 7) {
                given.get(key).put(2, given.get(key).get(7));
                return round(given.get(key).get(7));
            } else if (n != 7) {
                Double massTheor = 0.0;
                massTheor = getMassTheor(key, 2);
                if (massTheor > 0.0) {
                    given.get(key).put(2, round(massTheor));
                    return round(massTheor);
                }
            }
        }
        return -1.0;
    }

    // Масса раствора (3)
    public Double getMassOfSolution(String key, int n) {
        if (given.get(key).containsKey(3)) return given.get(key).get(3);
        if (given.get(key).containsKey(5) && n != 5) { // Массовая доля обязятельно должна быть
            if (given.get(key).containsKey(2) && n != 2) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле mр-ра = ");tmp.add("m / ω");
                tmp.add("mр-ра(" + key + ") = ");tmp.add("m(" + key + ") / ω(" + key + ")");
                tmp.add(" = "); tmp.add(given.get(key).get(2) + " / " + given.get(key).get(5));
                tmp.add(" = " + round(given.get(key).get(2) / given.get(key).get(5)) + " г"); solutions.add(tmp);
                given.get(key).put(3, round(given.get(key).get(2) / given.get(key).get(5)));
                return round(given.get(key).get(2) / given.get(key).get(5));
            } else if (n != 2) {
                Double mass = -1.0;
                mass = getMassOfAgent(key, 3);
                if (mass > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mр-ра = ");tmp.add("m / ω");
                    tmp.add("mр-ра(" + key + ") = ");tmp.add("m(" + key + ") / ω(" + key + ")");
                    tmp.add(" = "); tmp.add(mass + " / " + given.get(key).get(5));
                    tmp.add(" = " + round(mass / given.get(key).get(5)) + " г"); solutions.add(tmp);
                    given.get(key).put(2, mass);
                    given.get(key).put(3, round(mass / given.get(key).get(5)));
                    return round(mass / given.get(key).get(5));
                }
            }
        }
        if (given.get(key).containsKey(27) && n != 27) {
            if (given.get(key).containsKey(11) && n != 11) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле mр-ра = Vр-ра × ρр-ра");
                tmp.add(""); tmp.add("mр-ра(" + key + ") = Vр-ра(" + key + ") × ρр-ра(" + key + ")\n" + given.get(key).get(27) + " × " + given.get(key).get(11) + " = " + round(given.get(key).get(27) * given.get(key).get(11)) + " г");
                tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(3, round(given.get(key).get(27) * given.get(key).get(11)));
                return round(given.get(key).get(27) * given.get(key).get(11));
            } else if (n != 11) {
                Double volumeOfSolution = -1.0;
                volumeOfSolution = getVolumeOfSolution(key, 3);
                if (volumeOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mр-ра = Vр-ра × ρр-ра");
                    tmp.add(""); tmp.add("mр-ра(" + key + ") = Vр-ра(" + key + ") × ρр-ра(" + key + ")\n" + given.get(key).get(27) + " × " + volumeOfSolution + " = " + round(given.get(key).get(27) * volumeOfSolution) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(11, volumeOfSolution);
                    given.get(key).put(3, round(given.get(key).get(27) * volumeOfSolution));
                    return round(given.get(key).get(27) * volumeOfSolution);
                }
            }
        } else if (n != 27) {
            Double densityOfSolution = -1.0;
            densityOfSolution = getDensitySolution(key, 3);
            if (densityOfSolution > 0.0) {
                if (given.get(key).containsKey(11) && n != 11) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mр-ра = Vр-ра × ρр-ра");
                    tmp.add(""); tmp.add("mр-ра(" + key + ") = Vр-ра(" + key + ") × ρр-ра(" + key + ")\n" + densityOfSolution + " × " + given.get(key).get(11) + " = " + round(densityOfSolution * given.get(key).get(11)) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(27, densityOfSolution);
                    given.get(key).put(3, round(densityOfSolution * given.get(key).get(11)));
                    return round(densityOfSolution * given.get(key).get(11));
                } else if (n != 11) {
                    Double volumeOfSolution = -1.0;
                    volumeOfSolution = getVolumeOfSolution(key, 3);
                    if (volumeOfSolution > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле mр-ра = Vр-ра × ρр-ра");
                        tmp.add(""); tmp.add("mр-ра(" + key + ") = Vр-ра(" + key + ") × ρр-ра(" + key + ")\n" + densityOfSolution + " × " + volumeOfSolution + " = " + round(densityOfSolution * volumeOfSolution) + " г");
                        tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                        given.get(key).put(27, densityOfSolution);
                        given.get(key).put(11, volumeOfSolution);
                        given.get(key).put(3, round(densityOfSolution * volumeOfSolution));
                        return round(densityOfSolution * volumeOfSolution);
                    }
                }
            }
        }
        if (given.get(key).containsKey(2) && n != 2){
            if (given.get(key).containsKey(21) && n != 21){
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле mр-ра = m + mр-ля");
                tmp.add(""); tmp.add("mр-ра(" + key + ") = m(" + key + ") + mр-ля(" + key + ")\n" + given.get(key).get(2) + " + " + given.get(key).get(21) + " = " + round(given.get(key).get(2) + given.get(key).get(21)) + " г");
                tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(3, round(given.get(key).get(21) + given.get(key).get(2)));
                return round(given.get(key).get(21) + given.get(key).get(2));
            } else if (n != 21) {
                Double massOfSolvent = -1.0;
                massOfSolvent = getMassOfSolvent(key, 3);
                if (massOfSolvent > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mр-ра = m + mр-ля");
                    tmp.add(""); tmp.add("mр-ра(" + key + ") = m(" + key + ") + mр-ля(" + key + ")\n" + given.get(key).get(2) + " + " + massOfSolvent + " = " + round(given.get(key).get(2) + massOfSolvent) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(3, round(massOfSolvent + given.get(key).get(2)));
                    return round(massOfSolvent + given.get(key).get(2));
                }
            }
        } else if (n != 2) {
            Double mass = -1.0;
            mass = getMassOfAgent(key, 3);
            if (mass > 0.0) {
                if (given.get(key).containsKey(21) && n != 21) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mр-ра = m + mр-ля");
                    tmp.add(""); tmp.add("mр-ра(" + key + ") = m(" + key + ") + mр-ля(" + key + ")\n" + mass + " + " + given.get(key).get(21) + " = " + round(mass + given.get(key).get(21)) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(3, round(given.get(key).get(21) + mass));
                    return round(given.get(key).get(21) + mass);
                } else if (n != 21) {
                    Double massOfSolvent = -1.0;
                    massOfSolvent = getMassOfSolvent(key, 3);
                    if (massOfSolvent > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле mр-ра = m + mр-ля");
                        tmp.add(""); tmp.add("mр-ра(" + key + ") = m(" + key + ") + mр-ля(" + key + ")\n" + mass + " + " + massOfSolvent + " = " + round(mass + massOfSolvent) + " г");
                        tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                        given.get(key).put(3, round(massOfSolvent + mass));
                        return round(massOfSolvent + mass);
                    }
                }
            }
        }
        return -1.0;
    }

    // Массовая доля (5)
    public Double getMassFraction(String key, int n) {
        if (given.get(key).containsKey(5)) return given.get(key).get(5);
        if (given.get(key).containsKey(20) && n != 20) {
            if (given.get(key).containsKey(27) && n != 27) {
                if (given.get(key).containsKey(0) && n != 0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле ω = ");tmp.add("C × M / ρр-ра × 10");
                    tmp.add("ω(" + key + ") = ");
                    tmp.add("C(" + key + ") × M(" + key + ") / (ρр-ра(" + key + ") × 10)"); tmp.add(" = ");
                    tmp.add(given.get(key).get(20) + " × " + given.get(key).get(0) + " / (" + given.get(key).get(27) + " × 10)");
                    tmp.add(" = " + round(given.get(key).get(0) * given.get(key).get(20) / given.get(key).get(27) / 10)); solutions.add(tmp);
                    given.get(key).put(5, round(given.get(key).get(0) * given.get(key).get(20) / given.get(key).get(27) / 10));
                    return round(given.get(key).get(0) * given.get(key).get(20) / given.get(key).get(27) / 10);
                } else if (n != 0) {
                    Double molarMass = 0.0;
                    molarMass = getMolarMass(true, key, 5);
                    if (molarMass > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле ω = ");tmp.add("C × M / ρр-ра × 10");
                        tmp.add("ω(" + key + ") = ");
                        tmp.add("C(" + key + ") × M(" + key + ") / (ρр-ра(" + key + ") × 10)"); tmp.add(" = ");
                        tmp.add(given.get(key).get(20) + " × " + molarMass + " / (" + given.get(key).get(27) + " × 10)");
                        tmp.add(" = " + round(molarMass * given.get(key).get(20) / given.get(key).get(27) / 10)); solutions.add(tmp);
                        given.get(key).put(5, round(molarMass * given.get(key).get(20) / given.get(key).get(27) / 10));
                        return round(molarMass * given.get(key).get(20) / given.get(key).get(27) / 10);
                    }
                }
            }
            if (n != 27) {
                Double densityOfSolution = 0.0;
                densityOfSolution = getDensitySolution(key, 5);
                if (densityOfSolution > 0.0) {
                    if (given.get(key).containsKey(0) && n != 0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле ω = ");tmp.add("C × M / ρр-ра × 10");
                        tmp.add("ω(" + key + ") = ");
                        tmp.add("C(" + key + ") × M(" + key + ") / (ρр-ра(" + key + ") × 10)"); tmp.add(" = ");
                        tmp.add(given.get(key).get(20) + " × " + given.get(key).get(0) + " / (" + densityOfSolution + " × 10)");
                        tmp.add(" = " + round(given.get(key).get(0) * given.get(key).get(20) / densityOfSolution / 10)); solutions.add(tmp);
                        given.get(key).put(5, round(given.get(key).get(0) * given.get(key).get(20) / densityOfSolution));
                        return round(given.get(key).get(0) * given.get(key).get(20) / densityOfSolution);
                    } else if (n != 0) {
                        Double molarMass = 0.0;
                        molarMass = getMolarMass(true, key, 5);
                        if (molarMass > 0.0) {
                            ArrayList<String> tmp = new ArrayList<>();
                            tmp.add("По формуле ω = ");tmp.add("C × M / ρр-ра × 10");
                            tmp.add("ω(" + key + ") = ");
                            tmp.add("C(" + key + ") × M(" + key + ") / (ρр-ра(" + key + ") × 10)"); tmp.add(" = ");
                            tmp.add(given.get(key).get(20) + " × " + molarMass + " / (" + densityOfSolution + " × 10)");
                            tmp.add(" = " + round(molarMass * given.get(key).get(20) / densityOfSolution / 10)); solutions.add(tmp);
                            given.get(key).put(5, round(molarMass * given.get(key).get(20) / densityOfSolution));
                            return round(molarMass * given.get(key).get(20) / densityOfSolution);
                        }
                    }
                }
            }
        }
        if (given.get(key).containsKey(3) && n != 3) {
            if (given.get(key).containsKey(2) && n != 2) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле ω = ");tmp.add("m / mр-ра");
                tmp.add("ω(" + key + ") = ");
                tmp.add("m(" + key + ") / mр-ра(" + key + ")"); tmp.add(" = ");
                tmp.add(given.get(key).get(2) + " / " + given.get(key).get(3));
                tmp.add(" = " + round(given.get(key).get(2) / given.get(key).get(3))); solutions.add(tmp);
                given.get(key).put(5, round(given.get(key).get(2) / given.get(key).get(3)));
                return round(given.get(key).get(2) / given.get(key).get(3));
            } else if (n != 2) {
                Double mass = -1.0;
                mass = getMassOfAgent(key, 5);
                if (mass > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле ω = ");tmp.add("m / mр-ра");
                    tmp.add("ω(" + key + ") = ");
                    tmp.add("m(" + key + ") / mр-ра(" + key + ")"); tmp.add(" = ");
                    tmp.add(mass + " / " + given.get(key).get(3));
                    tmp.add(" = " + round(mass / given.get(key).get(3))); solutions.add(tmp);
                    given.get(key).put(2, mass);
                    given.get(key).put(5, round(mass / given.get(key).get(3)));
                    return round(mass / given.get(key).get(3));
                }
            }
        } else if (n != 3) {
            Double massOfSolution = -1.0;
            massOfSolution = getMassOfSolution(key, 5);
            if (massOfSolution > 0.0) {
                if (given.get(key).containsKey(2) && n != 2) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле ω = ");tmp.add("m / mр-ра");
                    tmp.add("ω(" + key + ") = ");
                    tmp.add("m(" + key + ") / mр-ра(" + key + ")"); tmp.add(" = ");
                    tmp.add(given.get(key).get(2) + " / " + massOfSolution);
                    tmp.add(" = " + round(given.get(key).get(2) / massOfSolution)); solutions.add(tmp);
                    given.get(key).put(3, massOfSolution);
                    given.get(key).put(5, round(given.get(key).get(2) / given.get(key).get(3)));
                    return round(given.get(key).get(2) / massOfSolution);
                } else if (n != 2) {
                    Double mass = -1.0;
                    mass = getMassOfAgent(key, 5);
                    if (mass > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле ω = ");tmp.add("m / mр-ра");
                        tmp.add("ω(" + key + ") = ");
                        tmp.add("m(" + key + ") / mр-ра(" + key + ")"); tmp.add(" = ");
                        tmp.add(mass + " / " + massOfSolution);
                        tmp.add(" = " + round(mass / massOfSolution)); solutions.add(tmp);
                        given.get(key).put(2, mass);
                        given.get(key).put(3, massOfSolution);
                        given.get(key).put(5, round(mass / massOfSolution));
                        return round(mass / massOfSolution);
                    }
                }
            }
        }
        return -1.0;
    }

    // Массовая доля выхода продукта (6)
    public Double getMassFractionProduct(String key, int n) {
        if (given.get(key).containsKey(6)) return given.get(key).get(6);
        if (given.get(key).containsKey(8) && n != 8) {
            if (given.get(key).containsKey(7) && n != 7) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле η = ");tmp.add("mпрак / mтеор");
                tmp.add("η(" + key + ") = ");
                tmp.add("m(" + key + ") / mр-ра(" + key + ")"); tmp.add(" = ");
                tmp.add(given.get(key).get(8) + " / " + given.get(key).get(7));
                tmp.add(" = " + round(given.get(key).get(8) / given.get(key).get(7))); solutions.add(tmp);
                given.get(key).put(6, round(given.get(key).get(8) / given.get(key).get(7)));
                return round(given.get(key).get(8) / given.get(key).get(7));
            } else if (n != 7) {
                Double massOfTheor = 0.0;
                massOfTheor = getMassTheor(key, 6);
                if (massOfTheor > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле η = ");tmp.add("mпрак / mтеор");
                    tmp.add("η(" + key + ") = ");
                    tmp.add("m(" + key + ") / mр-ра(" + key + ")"); tmp.add(" = ");
                    tmp.add(given.get(key).get(8) + " / " + massOfTheor);
                    tmp.add(" = " + round(given.get(key).get(8) / massOfTheor)); solutions.add(tmp);
                    given.get(key).put(6, round(given.get(key).get(8) / massOfTheor));
                    return round(given.get(key).get(8) / massOfTheor);
                }
            }
        }
        return -1.0;
    }

    // Теоретически полученная масса (7)
    public Double getMassTheor(String key, int n) {
        if (given.get(key).containsKey(7)) return given.get(key).get(7);
        if (given.get(key).containsKey(8) && n != 8) {
            if (given.get(key).containsKey(6) && n != 6) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле mтеор = ");tmp.add("mпрак / η");
                tmp.add("mтеор(" + key + ") = ");
                tmp.add("mпрак(" + key + ") / η(" + key + ")"); tmp.add(" = ");
                tmp.add(given.get(key).get(8) + " / " + given.get(key).get(6));
                tmp.add(" = " + round(given.get(key).get(8) / given.get(key).get(6)) + " г"); solutions.add(tmp);
                given.get(key).put(7, round(given.get(key).get(8) / given.get(key).get(6)));
                return round(given.get(key).get(8) / given.get(key).get(6));
            }
        }
        if (given.get(key).containsKey(1) && n != 1) {
            if (given.get(key).containsKey(0) && n != 0) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле mтеор = M × n");tmp.add("");
                tmp.add("mтеор(" + key + ") = M(" + key + ") × n(" + key + ")\n" + given.get(key).get(0) + " × " + given.get(key).get(1) + " = " + round(given.get(key).get(0) * given.get(key).get(1)) + " г");
                tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(7, given.get(key).get(0) * given.get(key).get(1));
                return given.get(key).get(0) * given.get(key).get(1);
            } else if (n != 0) {
                Double molarMass = 0.0;
                molarMass = getMolarMass(true, key, 7);
                if (molarMass > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mтеор = M × n");tmp.add("");
                    tmp.add("mтеор(" + key + ") = M(" + key + ") × n(" + key + ")\n" + molarMass + " × " + given.get(key).get(1) + " = " + round(molarMass * given.get(key).get(1)) + " г");
                    tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(7, round(molarMass * given.get(key).get(1)));
                    return round(molarMass * given.get(key).get(1));
                }
            }
        } else if (n != 1) {
            Double amount = 0.0;
            amount = getAmount(key, 7);
            if (amount > 0.0) {
                if (given.get(key).containsKey(0) && n != 0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mтеор = M × n");tmp.add("");
                    tmp.add("mтеор(" + key + ") = M(" + key + ") × n(" + key + ")\n" + given.get(key).get(0) + " × " + amount + " = " + round(given.get(key).get(0) * amount) + " г");
                    tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(7, given.get(key).get(0) * amount);
                    return given.get(key).get(0) * amount;
                } else if (n != 0) {
                    Double molarMass = 0.0;
                    molarMass = getMolarMass(true, key, 7);
                    if (molarMass > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле mтеор = M × n");tmp.add("");
                        tmp.add("mтеор(" + key + ") = M(" + key + ") × n(" + key + ")\n" + molarMass + " × " + amount + " = " + round(molarMass * amount) + " г");
                        tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                        given.get(key).put(7, round(molarMass * amount));
                        return round(molarMass * amount);
                    }
                }
            }
        }
        return -1.0;
    }

    // Практически полученная масса (8)
    public Double getMassPrak(String key, int n) {
        if (given.get(key).containsKey(8)) return given.get(key).get(8);
        if (given.get(key).containsKey(6) && n != 6) {
            if (given.get(key).containsKey(7) && n != 7) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле mпрак = mтеор × η");tmp.add("");
                tmp.add("mпрак(" + key + ") = mтеор(" + key + ") × η(" + key + ")\n" + given.get(key).get(7) + " × " + given.get(key).get(6) + " = " + round(given.get(key).get(7) * given.get(key).get(6)) + " г");
                tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(8, round(given.get(key).get(7) * given.get(key).get(6)));
                return round(given.get(key).get(7) * given.get(key).get(6));
            } else if (n != 7) {
                Double massOfTheor = 0.0;
                massOfTheor = getMassTheor(key, 8);
                if (massOfTheor > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mпрак = mтеор × η");tmp.add("");
                    tmp.add("mпрак(" + key + ") = mтеор(" + key + ") × η(" + key + ")\n" + massOfTheor + " × " + given.get(key).get(6) + " = " + round(massOfTheor * given.get(key).get(6)) + " г");
                    tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(8, round(massOfTheor * given.get(key).get(6)));
                    return round(massOfTheor * given.get(key).get(6));
                }
            }
        }
        return -1.0;
    }

    // Объём вещества (9)
    public Double getVolume(String key, int n) {
        if (given.get(key).containsKey(9)) return given.get(key).get(9); // V = V
        if (given.get(key).containsKey(25) && n != 25 && given.get(key).containsKey(24) && n != 24) {// MKT
            if (given.get(key).containsKey(1) && n != 1) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По уравнению Медлелеева-Клапейрона V =");tmp.add("n × R × T / p");
                tmp.add("V(" + key + ") = ");tmp.add(given.get(key).get(1) + " × " + given.get(key).get(25) + " × 8.31 /  " + given.get(key).get(24));
                tmp.add(" = " + round(given.get(key).get(25) / given.get(key).get(24) * 8.31 * given.get(key).get(1)) + " л");
                tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(9, round(given.get(key).get(25) / given.get(key).get(24) * 8.31 * given.get(key).get(1)));
                return round(given.get(key).get(25) / given.get(key).get(24) * 8.31 * given.get(key).get(1));
            } else if (n != 1) {
                Double amount = -1.0;
                amount = getAmount(key, 9);
                if (amount > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По уравнению Медлелеева-Клапейрона V =");tmp.add("n × R × T / p");
                    tmp.add("V(" + key + ") = ");tmp.add(amount + " × " + given.get(key).get(25) + " × 8.31 /  " + given.get(key).get(24));
                    tmp.add(" = " + round(given.get(key).get(25) / given.get(key).get(24) * 8.31 * amount) + " л");
                    tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(1, amount);
                    given.get(key).put(9, round(given.get(key).get(25) / given.get(key).get(24) * 8.31 * amount));
                    return round(given.get(key).get(25) / given.get(key).get(24) * 8.31 * amount);
                }
            }
        }
        if (given.get(key).containsKey(1) && n != 1) {// V = n * Va
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("По формуле V = n × Vₐ");tmp.add("");
            tmp.add("V(" + key + ") = n(" + key + ") × 22.4\n" + given.get(key).get(1) + " × 22.4 = " + round(given.get(key).get(1) * 22.4) + " л");
            tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
            given.get(key).put(9, given.get(key).get(1) * 22.4);
            return given.get(key).get(1) * 22.4;
        } else if (n != 1) {
            Double amount = -1.0;
            amount = getAmount(key, 9);
            if (amount > 0.0) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле V = n × Vₐ");tmp.add("");
                tmp.add("V(" + key + ") = n(" + key + ") × 22.4\n" + amount + " × 22.4 = " + round(amount * 22.4) + " л");
                tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(1, amount);
                given.get(key).put(9, round(amount * 22.4));
                return round(amount * 22.4);
            }
        }
        if (given.get(key).containsKey(26) && n != 26) { // V = m / p
            if (given.get(key).containsKey(2) && n != 2) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле V = ");tmp.add("m / ρ");
                tmp.add("V(" + key + ") = ");tmp.add("m(" + key + ") / ρ(" + key + ")");tmp.add(" = ");
                tmp.add(given.get(key).get(2) + " / " + given.get(key).get(26));tmp.add(" = " + round(given.get(key).get(2) / given.get(key).get(26)) + " л"); solutions.add(tmp);
                given.get(key).put(9, round(given.get(key).get(2) / given.get(key).get(26)));
                return round(given.get(key).get(2) / given.get(key).get(26));
            } else if (n != 2) {
                Double mass = -1.0;
                mass = getMassOfAgent(key, 9);
                if (mass > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле V = ");tmp.add("m / ρ");
                    tmp.add("V(" + key + ") = ");tmp.add("m(" + key + ") / ρ(" + key + ")");tmp.add(" = ");
                    tmp.add(mass + " / " + given.get(key).get(26));tmp.add(" = " + round(mass / given.get(key).get(26)) + " л"); solutions.add(tmp);
                    given.get(key).put(2, mass);
                    given.get(key).put(9, round(mass / given.get(key).get(26)));
                    return round(mass / given.get(key).get(26));
                }
            }
        }
        if (given.get(key).containsKey(12) && n != 12) { // V = w * Vр-ра
            if (given.get(key).containsKey(11) && n != 11) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле V = ω × Vр-ра");tmp.add("");
                tmp.add("V(" + key + ") = ω(" + key + ") × Vр-ра(" + key + ")\n" + given.get(key).get(12) + " × " + given.get(key).get(11) + " = " + round(given.get(key).get(12) * given.get(key).get(11)) + " л");
                tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(9, round(given.get(key).get(12) * given.get(key).get(11)));
                return round(given.get(key).get(12) * given.get(key).get(11));
            } else if (n != 11) {
                Double volumeOfSolution = -1.0;
                volumeOfSolution = getVolumeOfSolution(key, 9);
                if (volumeOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле V = ω × Vр-ра");tmp.add("");
                    tmp.add("V(" + key + ") = ω(" + key + ") × Vр-ра(" + key + ")\n" + given.get(key).get(12) + " × " + volumeOfSolution + " = " + round(given.get(key).get(12) * volumeOfSolution) + " л");
                    tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(11, volumeOfSolution);
                    given.get(key).put(9, round(volumeOfSolution * given.get(key).get(11)));
                    return round(volumeOfSolution * given.get(key).get(11));
                }
            }
        } else if (n != 12) {
            Double volumeFraction = -1.0;
            volumeFraction = getVolumeFraction(key, 9);
            if (volumeFraction > 0.0) {
                if (given.get(key).containsKey(11) && n != 11) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле V = ω × Vр-ра");tmp.add("");
                    tmp.add("V(" + key + ") = ω(" + key + ") × Vр-ра(" + key + ")\n" + volumeFraction+ " × " + given.get(key).get(11) + " = " + round(volumeFraction * given.get(key).get(11)) + " л");
                    tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(12, volumeFraction);
                    given.get(key).put(9, round(given.get(key).get(11) * volumeFraction));
                    return round(given.get(key).get(11) * volumeFraction);
                } else if (n != 11) {
                    Double volumeOfSolution = -1.0;
                    volumeOfSolution = getVolumeOfSolution(key, 9);
                    if (volumeOfSolution > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле V = ω × Vр-ра");tmp.add("");
                        tmp.add("V(" + key + ") = ω(" + key + ") × Vр-ра(" + key + ")\n" + volumeFraction+ " × " + volumeOfSolution + " = " + round(volumeFraction * volumeOfSolution) + " л");
                        tmp.add(""); tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                        given.get(key).put(12, volumeFraction);
                        given.get(key).put(11, volumeOfSolution);
                        given.get(key).put(9, round(volumeOfSolution * volumeFraction));
                        return round(volumeOfSolution * volumeFraction);
                    }
                }
            }
        }
        if (b2.equals(key)) {
            if (given.get(key).containsKey(14) && n != 14) {
                given.get(key).put(9, given.get(key).get(14));
                return given.get(key).get(14);
            } else if (n != 14) {
                Double volumeTheor = 0.0;
                volumeTheor = getVolumeTheor(key, 9);
                if (volumeTheor > 0.0) {
                    given.get(key).put(9, round(volumeTheor));
                    return round(volumeTheor);
                }
            }
        }
        return -1.0;
    }

    // Объём раствора (11)
    public Double getVolumeOfSolution(String key, int n) {
        if (given.get(key).containsKey(11)) return given.get(key).get(11);
        if (given.get(key).containsKey(23) && n != 23) {
            if (given.get(key).containsKey(2) && n != 2) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле Vр-ра = m / T");tmp.add("");
                tmp.add("Vр-ра(" + key + ") = ");
                tmp.add("m(" + key + ") / T(" + key + ")"); tmp.add(" = ");tmp.add(given.get(key).get(2) + " / " + given.get(key).get(23));
                tmp.add(" = " + round(given.get(key).get(2) / given.get(key).get(23)) + " л"); solutions.add(tmp);
                given.get(key).put(11, round(given.get(key).get(2) / given.get(key).get(23)));
                return round(given.get(key).get(2) / given.get(key).get(23));
            } else if (n != 2) {
                Double mass = 0.0;
                mass = getMassOfAgent(key, 11);
                if (mass > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле Vр-ра = m / T");tmp.add("");
                    tmp.add("Vр-ра(" + key + ") = ");
                    tmp.add("m(" + key + ") / T(" + key + ")"); tmp.add(" = ");tmp.add(mass + " / " + given.get(key).get(23));
                    tmp.add(" = " + round(mass / given.get(key).get(23)) + " л"); solutions.add(tmp);
                    given.get(key).put(11, round(mass / given.get(key).get(23)));
                    return round(mass / given.get(key).get(23));
                }
            }
        }
        if (given.get(key).containsKey(20) && n != 20) {
            if (given.get(key).containsKey(1) && n != 1) {
                given.get(key).put(11, round(given.get(key).get(1) / given.get(key).get(20)));
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле Vр-ра = n / C");tmp.add("");
                tmp.add("Vр-ра(" + key + ") = ");
                tmp.add("n(" + key + ") / C(" + key + ")"); tmp.add(" = ");tmp.add(given.get(key).get(1) + " / " + given.get(key).get(20));
                tmp.add(" = " + round(given.get(key).get(1) / given.get(key).get(20)) + " л"); solutions.add(tmp);
                return round(given.get(key).get(1) / given.get(key).get(20));
            } else if (n != 1) {
                Double amount = 0.0;
                amount = getAmount(key, 11);
                if (amount > 0.0) {ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле Vр-ра = n / C");tmp.add("");
                    tmp.add("Vр-ра(" + key + ") = ");
                    tmp.add("n(" + key + ") / C(" + key + ")"); tmp.add(" = ");tmp.add(amount + " / " + given.get(key).get(20));
                    tmp.add(" = " + round(amount / given.get(key).get(20)) + " л"); solutions.add(tmp);
                    given.get(key).put(11, round(amount / given.get(key).get(20)));
                    return round(amount / given.get(key).get(20));
                }
            }
        }
        if (given.get(key).containsKey(12) && n != 12) { // Объёмная доля обязятельно должна быть
            if (given.get(key).containsKey(9) && n != 9) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле Vр-ра = ");tmp.add("V / ω");
                tmp.add("Vр-ра(" + key + ") = ");
                tmp.add("V(" + key + ") / ω(" + key + ")"); tmp.add(" = ");tmp.add(given.get(key).get(9) + " / " + given.get(key).get(12));
                tmp.add(" = " + round(given.get(key).get(9) / given.get(key).get(12)) + " л"); solutions.add(tmp);
                given.get(key).put(11, round(given.get(key).get(9) / given.get(key).get(12)));
                return round(given.get(key).get(9) / given.get(key).get(12));
            } else if (n != 9) {
                Double volume = -1.0;
                volume = getVolume(key, 11);
                if (volume > 0.0) {
                    given.get(key).put(9, volume);
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле Vр-ра = ");tmp.add("V / ω");
                    tmp.add("Vр-ра(" + key + ") = ");
                    tmp.add("V(" + key + ") / ω(" + key + ")"); tmp.add(" = ");tmp.add(volume + " / " + given.get(key).get(12));
                    tmp.add(" = " + round(volume / given.get(key).get(12)) + " л"); solutions.add(tmp);
                    given.get(key).put(11, round(volume / given.get(key).get(12)));
                    return round(volume / given.get(key).get(12));
                }
            }
        }
        if (given.get(key).containsKey(27) && n != 27) { // V = m / p
            if (given.get(key).containsKey(3) && n != 3) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле Vр-ра = ");tmp.add("mр-ра / ρр-ра");
                tmp.add("Vр-ра(" + key + ") = ");
                tmp.add("mр-ра(" + key + ") / ρр-ра(" + key + ")"); tmp.add(" = ");tmp.add(given.get(key).get(3) + " / " + given.get(key).get(27));
                tmp.add(" = " + round(given.get(key).get(3) / given.get(key).get(27)) + " л"); solutions.add(tmp);
                given.get(key).put(11, round(given.get(key).get(3) / given.get(key).get(27)));
                return round(given.get(key).get(3) / given.get(key).get(27));
            } else if (n != 3) {
                Double massOfSolution = -1.0;
                massOfSolution = getMassOfSolution(key, 11);
                if (massOfSolution > 0.0) {
                    given.get(key).put(3, massOfSolution);
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле Vр-ра = ");tmp.add("mр-ра / ρр-ра");
                    tmp.add("Vр-ра(" + key + ") = ");
                    tmp.add("mр-ра(" + key + ") / ρр-ра(" + key + ")"); tmp.add(" = ");tmp.add(massOfSolution + " / " + given.get(key).get(27));
                    tmp.add(" = " + round(massOfSolution / given.get(key).get(27)) + " л"); solutions.add(tmp);
                    given.get(key).put(11, round(massOfSolution / given.get(key).get(27)));
                    return round(massOfSolution / given.get(key).get(27));
                }
            }
        } else if (n != 27) {
            Double densityOfSolution = -1.0;
            densityOfSolution = getDensitySolution(key, 11);
            if (densityOfSolution > 0.0) {
                if (given.get(key).containsKey(3) && n != 3) {
                    given.get(key).put(27, densityOfSolution);
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле Vр-ра = ");tmp.add("mр-ра / ρр-ра");
                    tmp.add("Vр-ра(" + key + ") = ");
                    tmp.add("mр-ра(" + key + ") / ρр-ра(" + key + ")"); tmp.add(" = ");tmp.add(given.get(key).get(3) + " / " + densityOfSolution);
                    tmp.add(" = " + round(given.get(key).get(3) / densityOfSolution) + " л"); solutions.add(tmp);
                    given.get(key).put(11, round(given.get(key).get(3) / densityOfSolution));
                    return round(given.get(key).get(3) / densityOfSolution);
                } else if (n != 3) {
                    Double massOfSolution = -1.0;
                    massOfSolution = getMassOfSolution(key, 11);
                    if (massOfSolution > 0.0) {
                        given.get(key).put(27, densityOfSolution);
                        given.get(key).put(3, massOfSolution);
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле Vр-ра = ");tmp.add("mр-ра / ρр-ра");
                        tmp.add("Vр-ра(" + key + ") = ");
                        tmp.add("mр-ра(" + key + ") / ρр-ра(" + key + ")"); tmp.add(" = ");tmp.add(massOfSolution + " / " + densityOfSolution);
                        tmp.add(" = " + round(massOfSolution / densityOfSolution) + " л"); solutions.add(tmp);
                        given.get(key).put(11, round(massOfSolution / densityOfSolution));
                        return round(massOfSolution / densityOfSolution);
                    }
                }
            }
        }

        return -1.0;
    }

    // Объёмная доля (12)
    public Double getVolumeFraction(String key, int n) {
        if (given.get(key).containsKey(12)) return given.get(key).get(12);
        if (given.get(key).containsKey(9) && n != 9) {
            if (given.get(key).containsKey(11) && n != 11) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле ω = ");tmp.add("V / Vр-ра");
                tmp.add("ω(" + key + ") = ");tmp.add("V(" + key + ") / Vр-ра(" + key + ")");
                tmp.add(" = "); tmp.add(given.get(key).get(9) + " / " + given.get(key).get(11));
                tmp.add(" = " + round(given.get(key).get(9) / given.get(key).get(11))); solutions.add(tmp);
                given.get(key).put(12, round(given.get(key).get(9) / given.get(key).get(11)));
                return round(given.get(key).get(9) / given.get(key).get(11));
            } else if (n != 11) {
                Double volumeOfSolution = -1.0;
                volumeOfSolution = getVolumeOfSolution(key, 12);
                if (volumeOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле ω = ");tmp.add("V / Vр-ра");
                    tmp.add("ω(" + key + ") = ");tmp.add("V(" + key + ") / Vр-ра(" + key + ")");
                    tmp.add(" = "); tmp.add(given.get(key).get(9) + " / " + given.get(key).get(11));
                    tmp.add(" = " + round(given.get(key).get(9) / given.get(key).get(11))); solutions.add(tmp);
                    given.get(key).put(11, volumeOfSolution);
                    given.get(key).put(12, round(given.get(key).get(9) / volumeOfSolution));
                    return round(given.get(key).get(9) / volumeOfSolution);
                }
            }
        } else if (n != 9) {
            Double volume = -1.0;
            volume = getVolume(key, 12);
            if (volume > 0.0) {
                if (given.get(key).containsKey(11) && n != 11) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле ω = ");tmp.add("V / Vр-ра");
                    tmp.add("ω(" + key + ") = ");tmp.add("V(" + key + ") / Vр-ра(" + key + ")");
                    tmp.add(" = "); tmp.add(volume + " / " + given.get(key).get(11));
                    tmp.add(" = " + round(volume / given.get(key).get(11))); solutions.add(tmp);
                    given.get(key).put(9, volume);
                    given.get(key).put(12, round(given.get(key).get(9) / given.get(key).get(11)));
                    return round(volume / given.get(key).get(11));
                } else if (n != 11) {
                    Double volumeOfSolution = -1.0;
                    volumeOfSolution = getVolumeOfSolution(key, 12);
                    if (volumeOfSolution > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле ω = ");tmp.add("V / Vр-ра");
                        tmp.add("ω(" + key + ") = ");tmp.add("V(" + key + ") / Vр-ра(" + key + ")");
                        tmp.add(" = "); tmp.add(volume + " / " + volumeOfSolution);
                        tmp.add(" = " + round(volume / volumeOfSolution)); solutions.add(tmp);
                        given.get(key).put(11, volumeOfSolution);
                        given.get(key).put(9, volume);
                        given.get(key).put(12, round(volume / volumeOfSolution));
                        return round(volume / volumeOfSolution);
                    }
                }
            }
        }
        return -1.0;
    }

    // Объёмная доля выхода продукта (13)
    public Double getVolumeFractionProduct(String key, int n) {
        if (given.get(key).containsKey(13)) return given.get(key).get(13);
        if (given.get(key).containsKey(15) && n != 15) {
            if (given.get(key).containsKey(14) && n != 14) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле η = ");tmp.add("Vпрак / Vтеор");
                tmp.add("η(" + key + ") = ");tmp.add("Vпрак(" + key + ") / Vтеор(" + key + ")");
                tmp.add(" = "); tmp.add(given.get(key).get(15) + " / " + given.get(key).get(14));
                tmp.add(" = " + round(given.get(key).get(15) / given.get(key).get(14))); solutions.add(tmp);
                given.get(key).put(13, round(given.get(key).get(15) / given.get(key).get(14)));
                return round(given.get(key).get(15) / given.get(key).get(14));
            } else if (n != 7) {
                Double volumeOfTheor = 0.0;
                volumeOfTheor = getVolumeTheor(key, 13);
                if (volumeOfTheor > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле η = ");tmp.add("Vпрак / Vтеор");
                    tmp.add("η(" + key + ") = ");tmp.add("Vпрак(" + key + ") / Vтеор(" + key + ")");
                    tmp.add(" = "); tmp.add(given.get(key).get(15) + " / " + volumeOfTheor);
                    tmp.add(" = " + round(given.get(key).get(15) / volumeOfTheor)); solutions.add(tmp);
                    given.get(key).put(13, round(given.get(key).get(15) / volumeOfTheor));
                    return round(given.get(key).get(15) / volumeOfTheor);
                }
            }
        }
        return -1.0;
    }

    // Теоретически полученный объём (14)
    public Double getVolumeTheor(String key, int n) {
        if (given.get(key).containsKey(14)) return given.get(key).get(14);
        if (given.get(key).containsKey(15) && n != 15) {
            if (given.get(key).containsKey(13) && n != 13) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле Vтеор = ");tmp.add("Vпрак / η");
                tmp.add("Vтеор(" + key + ") = ");tmp.add("Vпрак(" + key + ") / η(" + key + ")");
                tmp.add(" = "); tmp.add(given.get(key).get(15) + " / " + given.get(key).get(13));
                tmp.add(" = " + round(given.get(key).get(15) / given.get(key).get(13)) + " л"); solutions.add(tmp);
                given.get(key).put(14, round(given.get(key).get(15) / given.get(key).get(13)));
                return round(given.get(key).get(15) / given.get(key).get(13));
            }
        }
        if (given.get(key).containsKey(1) && n != 1) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("По формуле Vтеор = n × Vₐ");tmp.add("");
            tmp.add("Vтеор(" + key + ") = n(" + key + ") × 22.4\n" + given.get(key).get(1) + " × 22.4 = " + round(22.4 * given.get(key).get(1)) + " л");
            tmp.add(""); tmp.add(""); tmp.add("");tmp.add(""); solutions.add(tmp);
            given.get(key).put(14, 22.4 * given.get(key).get(1));
            return round(22.4 * given.get(key).get(1));
        } else if (n != 1) {
            Double amount = 0.0;
            amount = getAmount(key, 14);
            if (amount > 0.0) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле Vтеор = n × Vₐ");tmp.add("");
                tmp.add("Vтеор(" + key + ") = n(" + key + ") × 22.4 =\n" + amount + " × 22.4 =\n" + round(22.4 * amount) + " л");
                tmp.add(""); tmp.add(""); tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(14, 22.4 * amount);
                return round(22.4 * amount);
            }
        }
        return -1.0;
    }

    // Практически полученный объём (15)
    public Double getVolumePrak(String key, int n) {
        if (given.get(key).containsKey(15)) return given.get(key).get(15);
        if (given.get(key).containsKey(13) && n != 13) {
            if (given.get(key).containsKey(14) && n != 14) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле Vпрак = η × Vтеор");tmp.add("");
                tmp.add("Vпрак(" + key + ") = η(" + key + ") × Vтеор(" + key + ")\n" + given.get(key).get(14) + " × " + given.get(key).get(13) + " = " + round(given.get(key).get(14) * given.get(key).get(13)));
                tmp.add(""); tmp.add(""); tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(15, round(given.get(key).get(14) * given.get(key).get(13)));
                return round(given.get(key).get(14) * given.get(key).get(13));
            } else if (n != 14) {
                Double volumeOfTheor = 0.0;
                volumeOfTheor = getVolumeTheor(key, 15);
                if (volumeOfTheor > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле Vпрак = η × Vтеор");tmp.add("");
                    tmp.add("Vпрак(" + key + ") = η(" + key + ") × Vтеор(" + key + ")\n" + volumeOfTheor + " × " + given.get(key).get(13) + " = " + round(volumeOfTheor * given.get(key).get(13)));
                    tmp.add(""); tmp.add(""); tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(15, round(volumeOfTheor * given.get(key).get(13)));
                    return round(volumeOfTheor * given.get(key).get(13));
                }
            }
        }
        return -1.0;
    }

    // Масса атома (17)
    public Double getMassOfAtom(String key, int n) {
        if (given.get(key).containsKey(17)) return given.get(key).get(17);
        if (n != 17) {
            Double molarMass = 0.0;
            molarMass = getMolarMass(true, key, 17);
            if (molarMass > 0.0) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле m₀ = ");tmp.add("M / Nₐ");
                tmp.add("m₀ = ");tmp.add(molarMass + " / (6,02 × 10²³)");
                tmp.add(" = " + round(molarMass / 6.02) / Math.pow(10, 23) + " г"); tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(0, molarMass);
                given.get(key).put(17, round(molarMass / 6.02));
                return round(molarMass / 6.02) / Math.pow(10, 23);
            }
        }
        return -1.0;
    }

    // Число структурных единиц (18)
    public Double getCountOfStructure(String key, int n) {
        if (given.get(key).containsKey(18)) return given.get(key).get(18);
        if (given.get(key).containsKey(1) && n != 1) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("По формуле N = n × Nₐ");tmp.add("");
            tmp.add("N = " + given.get(key).get(1) + " × (6,02 × 10²³) = " + round(given.get(key).get(1) * 6.02) * Math.pow(10, 23));
            tmp.add(""); tmp.add("");tmp.add(""); tmp.add(""); solutions.add(tmp);
            given.get(key).put(18, round(given.get(key).get(1) * 6.02));
            return round(given.get(key).get(1) * 6.02) * Math.pow(10, 23); // N=n*Na
        } else if (n != 1) {
            Double amount = -1.0;
            amount = getAmount(key, 18);
            if (amount > 0.0) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле N = n × Nₐ");tmp.add("");
                tmp.add("N = " + amount + " × (6,02 × 10²³) = " + round(amount * 6.02) * Math.pow(10, 23));
                tmp.add(""); tmp.add("");tmp.add(""); tmp.add(""); solutions.add(tmp);
                given.get(key).put(1, amount);
                given.get(key).put(18, round(amount * 6.02));
                return round(amount * 6.02);
            }
        }
        return -1.0;
    }

    // Мольные доли элемента в соединении (19)
    public void getMoleFraction(String key) {
        Double n = 0.0, n1 = 0.0;
        int i = agent.indexOf(parser(key));
        for (String k : elements.get(i).keySet()) {
            n1 += elements.get(i).get(k);
        }
        for (String k: elements.get(i).keySet()) {
            n = Double.valueOf(elements.get(i).get(k));
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("По формуле χ = ");tmp.add("ν / νобщ");
            tmp.add("χ(" + k + ") = ");tmp.add("ν(" + k + ") / νобщ");
            tmp.add(" = ");tmp.add(n + " / " + n1);tmp.add(" = " + round(n / n1)); solutions.add(tmp);
            answer += round(n / n1) + "; ";
        }
    }

    // Молярная концентрация (20)
    public Double getMolarConcentration(String key, int n) {
        if (given.get(key).containsKey(20)) return given.get(key).get(20);
        if (given.get(key).containsKey(1) && n != 1) {
            if (given.get(key).containsKey(11) && n != 11) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле C = ");tmp.add("n / Vр-ра");
                tmp.add("C(" + key + ") = ");tmp.add("n(" + key + ") / Vp-pa");
                tmp.add(" = ");tmp.add(given.get(key).get(1) + " / " + given.get(key).get(11));
                tmp.add(" = " + round(given.get(key).get(1) / given.get(key).get(11)) + " моль/л"); solutions.add(tmp);
                given.get(key).put(20, round(given.get(key).get(1) / given.get(key).get(11)));
                return round(given.get(key).get(1) / given.get(key).get(11));
            } else if (n != 11) {
                Double volumeOfSolution = 0.0;
                volumeOfSolution = getVolumeOfSolution(key, 20);
                if (volumeOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле C = ");tmp.add("n / Vр-ра");
                    tmp.add("C(" + key + ") = ");tmp.add("n(" + key + ") / Vp-pa");
                    tmp.add(" = ");tmp.add(given.get(key).get(1) + " / " + volumeOfSolution);
                    tmp.add(" = " + round(given.get(key).get(1) / volumeOfSolution) + " моль/л"); solutions.add(tmp);
                    given.get(key).put(20, round(given.get(key).get(1) / volumeOfSolution));
                    return round(given.get(key).get(1) / volumeOfSolution);
                }
            }
        } else if (n != 1) {
            Double amount = 0.0;
            amount = getAmount(key, 20);
            if (amount > 0.0) {
                if (given.get(key).containsKey(11) && n != 11) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле C = ");tmp.add("n / Vр-ра");
                    tmp.add("C(" + key + ") = ");tmp.add("n(" + key + ") / Vp-pa");
                    tmp.add(" = ");tmp.add(amount + " / " + given.get(key).get(11));
                    tmp.add(" = " + round(amount / given.get(key).get(11)) + " моль/л"); solutions.add(tmp);
                    given.get(key).put(20, round(amount / given.get(key).get(11)));
                    return round(amount / given.get(key).get(11));
                } else if (n != 11) {
                    Double volumeOfSolution = 0.0;
                    volumeOfSolution = getVolumeOfSolution(key, 20);
                    if (volumeOfSolution > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле C = ");tmp.add("n / Vр-ра");
                        tmp.add("C(" + key + ") = ");tmp.add("n(" + key + ") / Vp-pa");
                        tmp.add(" = ");tmp.add(amount + " / " + volumeOfSolution);
                        tmp.add(" = " + round(amount / volumeOfSolution) + " моль/л"); solutions.add(tmp);
                        given.get(key).put(20, round(amount / volumeOfSolution));
                        return round(amount / volumeOfSolution);
                    }
                }
            }
        }
        if (given.get(key).containsKey(5) && n != 5) { // C = 10 * w * p / M
            if (given.get(key).containsKey(27) && n != 27) {
                if (given.get(key).containsKey(0) && n != 0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле C = ");tmp.add("ω × ρр-рра × 10 / M");
                    tmp.add("C(" + key + ") = ");tmp.add("ω(" + key + ") × ρр-ра(" + key + ") × 10 / M(" + key + ")");
                    tmp.add(" = ");tmp.add(given.get(key).get(5) + " × " + given.get(key).get(27) + "× 10 / " + given.get(key).get(0));
                    tmp.add(" = " + round(given.get(key).get(27) * given.get(key).get(5) / given.get(key).get(0) * 10) + " моль/л"); solutions.add(tmp);
                    given.get(key).put(20, round(given.get(key).get(27) * given.get(key).get(5) / given.get(key).get(0) * 10));
                    return round(given.get(key).get(27) * given.get(key).get(5) / given.get(key).get(0) * 10);
                } else if (n != 0) {
                    Double molarMass = 0.0;
                    molarMass = getMolarMass(true, key, 20);
                    if (molarMass > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле C = ");tmp.add("ω × ρр-рра × 10 / M");
                        tmp.add("C(" + key + ") = ");tmp.add("ω(" + key + ") × ρр-ра(" + key + ") × 10 / M(" + key + ")");
                        tmp.add(" = ");tmp.add(given.get(key).get(5) + " × " + given.get(key).get(27) + "× 10 / " + molarMass);
                        tmp.add(" = " + round(given.get(key).get(27) * given.get(key).get(5) / molarMass * 10) + " моль/л"); solutions.add(tmp);
                        given.get(key).put(20, round(given.get(key).get(27) * given.get(key).get(5) / molarMass * 10));
                        return round(given.get(key).get(27) * given.get(key).get(5) / molarMass * 10);
                    }
                }
            }
            if (n != 27) {
                Double densityOfSolution = 0.0;
                densityOfSolution = getDensitySolution(key, 20);
                if (densityOfSolution > 0.0) {
                    if (given.get(key).containsKey(0) && n != 0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле C = ");tmp.add("ω × ρр-рра × 10 / M");
                        tmp.add("C(" + key + ") = ");tmp.add("ω(" + key + ") × ρр-ра(" + key + ") × 10 / M(" + key + ")");
                        tmp.add(" = ");tmp.add(given.get(key).get(5) + " × " + densityOfSolution + "× 10 / " + given.get(key).get(0));
                        tmp.add(" = " + round(densityOfSolution * given.get(key).get(5) / given.get(key).get(0) * 10) + " моль/л"); solutions.add(tmp);
                        given.get(key).put(20, round(densityOfSolution * given.get(key).get(20) / given.get(key).get(0)));
                        return round(densityOfSolution * given.get(key).get(20) / given.get(key).get(0));
                    } else if (n != 0) {
                        Double molarMass = 0.0;
                        molarMass = getMolarMass(true, key, 5);
                        if (molarMass > 0.0) {
                            ArrayList<String> tmp = new ArrayList<>();
                            tmp.add("По формуле C = ");tmp.add("ω × ρр-рра × 10 / M");
                            tmp.add("C(" + key + ") = ");tmp.add("ω(" + key + ") × ρр-ра(" + key + ") × 10 / M(" + key + ")");
                            tmp.add(" = ");tmp.add(given.get(key).get(5) + " × " + densityOfSolution + "× 10 / " + molarMass);
                            tmp.add(" = " + round(densityOfSolution * given.get(key).get(5) / molarMass * 10) + " моль/л"); solutions.add(tmp);
                            given.get(key).put(5, round(densityOfSolution * given.get(key).get(20) / molarMass));
                            return round(densityOfSolution * given.get(key).get(20) / molarMass);
                        }
                    }
                }
            }
        }
        return -1.0;
    }

    // Масса растворителя (21)
    public Double getMassOfSolvent(String key, int n) {
        if (given.get(key).containsKey(21)) return given.get(key).get(21);
        if (given.get(key).containsKey(22) && n != 22) {
            if (given.get(key).containsKey(1) && n != 1) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле mp-ля = ");tmp.add("n / C");
                tmp.add("mp-ля(" + key + ") = ");tmp.add("n(" + key + ") / C(" + key + ")");
                tmp.add(" = ");tmp.add(given.get(key).get(1) + " / " + given.get(key).get(22));
                tmp.add(" = " + round(given.get(key).get(1) / given.get(key).get(22)) + " г"); solutions.add(tmp);
                given.get(key).put(21, round(given.get(key).get(1) / given.get(key).get(22)));
                return round(given.get(key).get(1) / given.get(key).get(22));
            } else if (n != 1) {
                Double amount = 0.0;
                amount = getAmount(key, 21);
                if (amount > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mp-ля = ");tmp.add("n / C");
                    tmp.add("mp-ля(" + key + ") = ");tmp.add("n(" + key + ") / C(" + key + ")");
                    tmp.add(" = ");tmp.add(amount + " / " + given.get(key).get(22));
                    tmp.add(" = " + round(amount / given.get(key).get(22)) + " г"); solutions.add(tmp);
                    given.get(key).put(21, round(amount / given.get(key).get(22)));
                    return round(amount / given.get(key).get(22));
                }
            }
        }

        if (given.get(key).containsKey(2) && n != 2) { // m = mр-ра - mр-ля
            if (given.get(key).containsKey(3) && n != 3) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле mр-ля = mр-ра - m");
                tmp.add(""); tmp.add("mр-ля(" + key + ") = mр-ра(" + key + ") - m(" + key + ")\n" + given.get(key).get(3) + " - " + given.get(key).get(2) + " = " + round(given.get(key).get(3) - given.get(key).get(2)) + " г");
                tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                given.get(key).put(21, round(given.get(key).get(3) - given.get(key).get(2)));
                return round(given.get(key).get(3) - given.get(key).get(2));
            } else if (n != 3) {
                Double massOfSolution = -1.0;
                massOfSolution = getMassOfSolution(key, 21);
                if (massOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mр-ля = mр-ра - m");
                    tmp.add(""); tmp.add("mр-ля(" + key + ") = mр-ра(" + key + ") - m(" + key + ")\n"  + massOfSolution + " - "+ given.get(key).get(2) + " = " + round(massOfSolution - given.get(key).get(2)) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(3, massOfSolution);
                    given.get(key).put(21, round(massOfSolution - given.get(key).get(2)));
                    return round(massOfSolution - given.get(key).get(2));
                }
            }
        } else if (n != 2) {
            Double mass = -1.0;
            mass = getMassOfAgent(key, 21);
            if (mass > 0.0) {
                if (given.get(key).containsKey(3) && n != 3) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле mр-ля = mр-ра - m");
                    tmp.add(""); tmp.add("mр-ля(" + key + ") = mр-ра(" + key + ") - m(" + key + ")\n" + given.get(key).get(3) + " - " + mass + " = " + round(given.get(key).get(3) - mass) + " г");
                    tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                    given.get(key).put(21, round(given.get(key).get(3) - mass));
                    return round(given.get(key).get(3) - mass);
                } else if (n != 3) {
                    Double massOfSolution = -1.0;
                    massOfSolution = getMassOfSolution(key, 2);
                    if (massOfSolution > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле mр-ля = mр-ра - m");
                        tmp.add(""); tmp.add("mр-ля(" + key + ") = mр-ра(" + key + ") - m(" + key + ")\n" + massOfSolution + " - " + mass + " = " + round(massOfSolution - mass) + " г");
                        tmp.add("");tmp.add("");tmp.add("");tmp.add(""); solutions.add(tmp);
                        given.get(key).put(21, round(massOfSolution - mass));
                        return round(massOfSolution - mass);
                    }
                }
            }
        }
        return -1.0;
    }

    // Моляльная концентрация (22)
    public Double getMolalConcentration(String key, int n) {
        if (given.get(key).containsKey(22)) return given.get(key).get(22);
        if (given.get(key).containsKey(21) && n != 21) {
            if (given.get(key).containsKey(1) && n != 1) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле C = ");tmp.add("n / mp-ля");
                tmp.add("C(" + key + ") = ");tmp.add("n(" + key + ") / mp-ля(" + key + ")");
                tmp.add(" = ");tmp.add(given.get(key).get(1) + " / " + given.get(key).get(21));
                tmp.add(" = " + round(given.get(key).get(1) / given.get(key).get(21)) + " моль/г"); solutions.add(tmp);
                given.get(key).put(22, round(given.get(key).get(1) / given.get(key).get(21)));
                return round(given.get(key).get(1) / given.get(key).get(21));
            } else if (n != 1) {
                Double amount = 0.0;
                amount = getAmount(key, 22);
                if (amount > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле C = ");tmp.add("n / mp-ля");
                    tmp.add("C(" + key + ") = ");tmp.add("n(" + key + ") / mp-ля(" + key + ")");
                    tmp.add(" = ");tmp.add(amount + " / " + given.get(key).get(21));
                    tmp.add(" = " + round(amount / given.get(key).get(21)) + " моль/г"); solutions.add(tmp);
                    given.get(key).put(22, round(amount / given.get(key).get(21)));
                    return round(amount / given.get(key).get(21));
                }
            }
        }
        return -1.0;
    }

    // Титр (23)
    public Double getTiter(String key, int n) {
        if (given.get(key).containsKey(23)) return given.get(key).get(23);
        if (given.get(key).containsKey(2) && n != 2) {
            if (given.get(key).containsKey(11) && n != 11) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле T = ");tmp.add("m / Vр-ра");
                tmp.add("T(" + key + ") = ");tmp.add("m(" + key + ") / Vр-ра");
                tmp.add(" = ");tmp.add(given.get(key).get(2) + " / " + given.get(key).get(11));
                tmp.add(" = " + round(given.get(key).get(2) / given.get(key).get(11)) + " г/л"); solutions.add(tmp);
                given.get(key).put(23, round(given.get(key).get(2) / given.get(key).get(11)));
                return round(given.get(key).get(2) / given.get(key).get(11));
            } else if (n != 11) {
                Double volumeOfSolution = 0.0;
                volumeOfSolution = getVolumeOfSolution(key, 23);
                if (volumeOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле T = ");tmp.add("m / Vр-ра");
                    tmp.add("T(" + key + ") = ");tmp.add("m(" + key + ") / Vр-ра");
                    tmp.add(" = ");tmp.add(given.get(key).get(2) + " / " + volumeOfSolution);
                    tmp.add(" = " + round(given.get(key).get(2) / volumeOfSolution) + " г/л"); solutions.add(tmp);
                    given.get(key).put(23, round(given.get(key).get(2) / volumeOfSolution));
                    return round(given.get(key).get(2) / volumeOfSolution);
                }
            }
        } else if (n != 2) {
            Double mass = 0.0;
            mass = getMassOfAgent(key, 23);
            if (mass > 0.0) {
                if (given.get(key).containsKey(11) && n != 11) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле T = ");tmp.add("m / Vр-ра");
                    tmp.add("T(" + key + ") = ");tmp.add("m(" + key + ") / Vр-ра");
                    tmp.add(" = ");tmp.add(mass + " / " + given.get(key).get(11));
                    tmp.add(" = " + round(mass / given.get(key).get(11)) + " г/л"); solutions.add(tmp);
                    given.get(key).put(23, round(mass / given.get(key).get(11)));
                    return round(mass / given.get(key).get(11));
                } else if (n != 11) {
                    Double volumeOfSolution = 0.0;
                    volumeOfSolution = getVolumeOfSolution(key, 23);
                    if (volumeOfSolution > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле T = ");tmp.add("m / Vр-ра");
                        tmp.add("T(" + key + ") = ");tmp.add("m(" + key + ") / Vр-ра");
                        tmp.add(" = ");tmp.add(mass + " / " + volumeOfSolution);
                        tmp.add(" = " + round(mass / volumeOfSolution) + " г/л"); solutions.add(tmp);
                        given.get(key).put(23, round(mass / volumeOfSolution));
                        return round(mass / volumeOfSolution);
                    }
                }
            }
        }
        return -1.0;
    }

    // Давление газа (24)
    public Double getPressure(String key, int n) {
        if (given.get(key).containsKey(24)) return given.get(key).get(24);
        if (!given.get(key).containsKey(9) && n != 9) {
            Double volume = -1.0;
            volume = getVolume(key, 24);
            if (volume > 0.0) given.get(key).put(9, volume);
            else return -1.0;
        }
        if (!given.get(key).containsKey(1) && n != 1) {
            Double amount = -1.0;
            amount = getAmount(key, 24);
            if (amount > 0.0) given.get(key).put(1, amount);
            else return -1.0;
        }
        if (given.get(key).containsKey(25) && n != 25) {
            if (given.get(key).containsKey(9) && n != 9) {
                if (given.get(key).containsKey(1) && n != 1) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По уравнению Медлелеева-Клапейрона p = ");tmp.add("T × R × n / V");
                    tmp.add("p(" + key + ") = ");tmp.add(given.get(key).get(25) + " × " + given.get(key).get(1) + " × 8.31 / " + given.get(key).get(9));
                    tmp.add(" = " + round(given.get(key).get(24) * given.get(key).get(9) / 8.31 / given.get(key).get(1)) + " атм");tmp.add("");
                    tmp.add(""); solutions.add(tmp);
                    given.get(key).put(24, round(given.get(key).get(25) / given.get(key).get(9) * 8.31 * given.get(key).get(1)));
                    return round(given.get(key).get(25) / given.get(key).get(9) * 8.31 * given.get(key).get(1));
                }
            }
        }
        return -1.0;
    }

    // Температура газа (25)
    public Double getTemperature(String key, int n) {
        if (given.get(key).containsKey(25)) return given.get(key).get(25);
        if (!given.get(key).containsKey(9) && n != 9) {
            Double volume = -1.0;
            volume = getVolume(key, 25);
            if (volume > 0.0) given.get(key).put(9, volume);
            else return -1.0;
        }
        if (!given.get(key).containsKey(1) && n != 1) {
            Double amount = -1.0;
            amount = getAmount(key, 25);
            if (amount > 0.0) given.get(key).put(1, amount);
            else return -1.0;
        }
        if (given.get(key).containsKey(24) && n != 24) {
            if (given.get(key).containsKey(9) && n != 9) {
                if (given.get(key).containsKey(1) && n != 1) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По уравнению Медлелеева-Клапейрона T = ");tmp.add("p × V / R × n");
                    tmp.add("T(" + key + ") = ");tmp.add(given.get(key).get(24) + " × " + given.get(key).get(9) + " / 8.31 × " + given.get(key).get(1));
                    tmp.add(" = " + round(given.get(key).get(24) * given.get(key).get(9) / 8.31 / given.get(key).get(1)) + " K");tmp.add("");
                    tmp.add(""); solutions.add(tmp);
                    given.get(key).put(25, round(given.get(key).get(24) * given.get(key).get(9) / 8.31 / given.get(key).get(1)));
                    return round(given.get(key).get(24) * given.get(key).get(9) / 8.31 / given.get(key).get(1));
                }
            }
        }
        return -1.0;
    }

    // Плотность (26)
    public Double getDensity(String key, int n) {
        if (given.get(key).containsKey(26)) return given.get(key).get(26);
        if (given.get(key).containsKey(2) && n != 2) { // p = V / m
            if (given.get(key).containsKey(9) && n != 9) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле ρ = ");tmp.add("m / V");
                tmp.add("ρ(" + key + ") = ");tmp.add("m(" + key + ") / V(" + key + ")");
                tmp.add(" = ");tmp.add(given.get(key).get(2) + " / " + given.get(key).get(9));
                tmp.add(" = " + round(given.get(key).get(2) / given.get(key).get(9)) + " г/см³"); solutions.add(tmp);
                given.get(key).put(26, round(given.get(key).get(2) / given.get(key).get(9)));
                return round(given.get(key).get(2) / given.get(key).get(9));
            } else if (n != 9) {
                Double volume = -1.0;
                volume = getVolume(key, 26);
                if (volume > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле ρ = ");tmp.add("m / V");
                    tmp.add("ρ(" + key + ") = ");tmp.add("m(" + key + ") / V(" + key + ")");
                    tmp.add(" = ");tmp.add(given.get(key).get(2) + " / " + volume);
                    tmp.add(" = " + round(given.get(key).get(2) / volume) + " г/см³"); solutions.add(tmp);
                    given.get(key).put(9, volume);
                    given.get(key).put(26, round(given.get(key).get(2) / volume));
                    return round(given.get(key).get(2) / volume);
                }
            }
        } else if (n != 2) {
            Double mass = -1.0;
            mass = getMassOfAgent(key, 26);
            if (mass > 0.0) {
                if (given.get(key).containsKey(9) && n != 9) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле ρ = ");tmp.add("m / V");
                    tmp.add("ρ(" + key + ") = ");tmp.add("m(" + key + ") / V(" + key + ")");
                    tmp.add(" = ");tmp.add(mass + " / " + given.get(key).get(9));
                    tmp.add(" = " + round(mass / given.get(key).get(9)) + " г/см³"); solutions.add(tmp);
                    given.get(key).put(2, mass);
                    given.get(key).put(26, round(mass / given.get(key).get(9)));
                    return round(mass / given.get(key).get(9));
                } else if (n != 9) {
                    Double volume = -1.0;
                    volume = getVolume(key, 26);
                    if (volume > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле ρ = ");tmp.add("m / V");
                        tmp.add("ρ(" + key + ") = ");tmp.add("m(" + key + ") / V(" + key + ")");
                        tmp.add(" = ");tmp.add(mass + " / " + volume);
                        tmp.add(" = " + round(mass / volume) + " г/см³"); solutions.add(tmp);
                        given.get(key).put(2, mass);
                        given.get(key).put(9, volume);
                        given.get(key).put(26, round(mass / volume));
                        return round(mass / volume);
                    }
                }
            }
        }
        return -1.0;
    }

    // Плотность раствора (27)
    public Double getDensitySolution(String key, int n) {
        if (given.get(key).containsKey(27)) return given.get(key).get(27);
        if (given.get(key).containsKey(2) && n != 2) {
            if (given.get(key).containsKey(11) && n != 11) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("По формуле ρр-ра = ");tmp.add("mр-ра / Vр-ра");
                tmp.add("ρр-ра(" + key + ") = ");tmp.add("mр-ра(" + key + ") / Vр-ра(" + key + ")");
                tmp.add(" = ");tmp.add(given.get(key).get(2) + "/" + given.get(key).get(11));
                tmp.add(" = " + round(given.get(key).get(2) / given.get(key).get(11)) + " г/см³"); solutions.add(tmp);
                given.get(key).put(27, round(given.get(key).get(2) / given.get(key).get(11)));
                return round(given.get(key).get(2) / given.get(key).get(11));
            } else if (n != 11) {
                Double volumeOfSolution = -1.0;
                volumeOfSolution = getVolumeOfSolution(key, 27);
                if (volumeOfSolution > 0.0) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле ρр-ра = ");tmp.add("mр-ра / Vр-ра");
                    tmp.add("ρр-ра(" + key + ") = ");tmp.add("mр-ра(" + key + ") / Vр-ра(" + key + ")");
                    tmp.add(" = ");tmp.add(given.get(key).get(2) + "/" + volumeOfSolution);
                    tmp.add(" = " + round(given.get(key).get(2) / volumeOfSolution) + " г/см³"); solutions.add(tmp);
                    given.get(key).put(11, volumeOfSolution);
                    given.get(key).put(27, round(given.get(key).get(2) / volumeOfSolution));
                    return round(given.get(key).get(2) / volumeOfSolution);
                }
            }
        } else if (n != 2) {
            Double mass = -1.0;
            mass = getMassOfSolution(key, 27);
            if (mass > 0.0) {
                if (given.get(key).containsKey(11) && n != 11) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add("По формуле ρр-ра = ");tmp.add("mр-ра / Vр-ра");
                    tmp.add("ρр-ра(" + key + ") = ");tmp.add("mр-ра(" + key + ") / Vр-ра(" + key + ")");
                    tmp.add(" = ");tmp.add(mass + "/" + given.get(key).get(11));
                    tmp.add(" = " + round(mass / given.get(key).get(11)) + " г/см³"); solutions.add(tmp);
                    given.get(key).put(2, mass);
                    given.get(key).put(27, round(mass / given.get(key).get(11)));
                    return round(mass / given.get(key).get(11));
                } else if (n != 11) {
                    Double volumeOfSolution = -1.0;
                    volumeOfSolution = getVolumeOfSolution(key, 27);
                    if (volumeOfSolution > 0.0) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add("По формуле ρр-ра = ");tmp.add("mр-ра / Vр-ра");
                        tmp.add("ρр-ра(" + key + ") = ");tmp.add("mр-ра(" + key + ") / Vр-ра(" + key + ")");
                        tmp.add(" = ");tmp.add(mass + "/" + volumeOfSolution);
                        tmp.add(" = " + round(mass / volumeOfSolution) + " г/см³"); solutions.add(tmp);
                        given.get(key).put(11, volumeOfSolution);
                        given.get(key).put(2, mass);
                        given.get(key).put(27, round(mass / volumeOfSolution));
                        return round(mass / volumeOfSolution);
                    }
                }
            }
        }
        return -1.0;
    }

    // для задач с реакциями
    public void findCoefficients() {
        int k = 0;
        for (int i = 0; i < reaction.length() - 1; i++) {
            if (i == 0 && !number.contains(String.valueOf(reaction.charAt(i)))) {
                coefficients[k] = 1;
                k++;
            }
            if (number.contains(String.valueOf(reaction.charAt(i))) && (i == 0 || (i > 0 && (reaction.charAt(i - 1) == '+' || reaction.charAt(i - 1) == '=')))) {
                String str = "";
                for (int j = i; j < reaction.length(); j++) {
                    if (number.contains(String.valueOf(reaction.charAt(j))))
                        str += reaction.charAt(i);
                    else {
                        coefficients[k] = Integer.valueOf(str);
                        k++;
                        i = j;
                        break;
                    }
                }
            } else if ((reaction.charAt(i) == '+' || reaction.charAt(i) == '=') && !number.contains(String.valueOf(reaction.charAt(i + 1)))) {
                coefficients[k] = 1;
                k++;
                i++;
            }
        }
    }

    // метод, возвращающий строку с раскрытыми скобками
    private String parser(String element) {
        // a - число, на которое нужно умножать количество элемента в скобках; b - индекс отрывающей скобки
        Integer a = 0, b = 0;
        // str - преобразованная после раскрытия скобок часть строки (искомая), part - часть строки, которую нужно преобразовать
        String str = "", part = "";
        // число после скобок или количество элемента
        String num = "";
        // переменная, чтобы знать какая скобка: ']' или ')'
        int flag = 0;

        for (int i = 0; i < element.length(); i++) {
            if (element.charAt(i) == ')' || element.charAt(i) == ']') {
                if (element.charAt(i) == ')') flag = 1;
                else flag = 0;
                // Если нашли закрывающую скобку, то находим число после неё
                if (i + 1 < element.length()) {
                    if (number.contains(String.valueOf(element.charAt(i + 1)))) {
                        for (int j = i + 1; j < element.length(); j++) {
                            if (number.contains(String.valueOf(element.charAt(j)))) {
                                num += element.charAt(j);
                                a = Integer.valueOf(num);
                            } else break;
                        }
                    } else a = 1; // Если число не стоит, то таких скобок всего 1, значит, a = 1
                } else
                    a = 1; // Если строка оканчивается на скобку, то таких скобок всего 1, значит, a = 1

                // Находим индекс ближайшей открывающей скобки
                for (int j = i; j >= 0; j--) {
                    if (element.charAt(j) == '(' || element.charAt(j) == '[') {
                        b = j;
                        break;
                    }
                }

                // Находим часть строки, которая находится в нужных собках
                if (flag == 1) part = "(";
                else part = "[";
                for (int j = b + 1; j <= i; j++) {
                    part += element.charAt(j);
                }

                for (int j = b + 1; j <= i; j++) {
                    // Если нашли цифру, то ищем рядом стоящие цифры и потом преобразовываем в число
                    if (number.contains(String.valueOf(element.charAt(j)))) {
                        num = "";
                        for (int k = j; k <= i; k++) {
                            if (number.contains(String.valueOf(element.charAt(k)))) {
                                // Формируем строку с числом
                                num += element.charAt(k);
                            } else {
                                // преобразовываем в число и умножаем его на a. Добавляем к искомой строке
                                str += Integer.valueOf(num) * a;
                                j = k - 1;
                                break;
                            }
                        }
                        // Если число не найдено, то проверяем, что стоит следующий элемент, а перед ним не скобки и не число
                    } else if (String.valueOf(element.charAt(j)).toUpperCase().equals(String.valueOf(element.charAt(j)))
                            && !number.contains(String.valueOf(element.charAt(j - 1))) && element.charAt(j - 1) != '(' && element.charAt(j - 1) != '[') {
                        str += a; // Если число не стоит, то индекс у элемента равен 1, значит, a * 1 = a
                        if (element.charAt(j) != ')' && element.charAt(j) != ']')
                            str += element.charAt(j); // Добавляем символ, если он не скобка
                    } else if (element.charAt(j) != ')' && element.charAt(j) != ']')
                        str += element.charAt(j); // Добавляем символ, если он не скобка
                }
                // К части строки, которую нужно заменить добавляем число, если оно стоит
                if (a != 1)
                    part += String.valueOf(a);

                element = element.replace(part, str); // Заменяем в строке, подстроку на искомую
                break;
            }
        }
        return element;
    }

    // Метод, который возвращает HashMap<String, Integer>, в котором записаны элементы и их индексы
    private HashMap<String, Integer> getInformation(String element, int f) {
        // Ключ - название элемента, значение - его количество в веществе
        HashMap<String, Integer> hashMap = new HashMap<>();
        // Количество элемента в веществе
        String num = "";
        // Название вещества
        String name = "";

        for (int j = 0; j < element.length(); j++) {
            if (!number.contains(String.valueOf(element.charAt(j)))) {
                if (j + 1 < element.length()) {
                    if (number.contains(String.valueOf(element.charAt(j + 1)))) {
                        // название элемента с одной буквой
                        if (String.valueOf(element.charAt(j)).toUpperCase().equals(String.valueOf(element.charAt(j)))) {
                            name = "";
                            name += element.charAt(j);
                        }
                        num = "";
                        for (int k = j + 1; k < element.length(); k++) {
                            if (number.contains(String.valueOf(element.charAt(k)))) {
                                num += element.charAt(k);
                                if (k == element.length() - 1) {
                                    if (f == 1)
                                        hashMap = check(hashMap, name, -1 * Integer.valueOf(num));
                                    else hashMap = check(hashMap, name, Integer.valueOf(num));
                                    name = "";
                                }
                            } else {
                                if (f == 1)
                                    hashMap = check(hashMap, name, -1 * Integer.valueOf(num));
                                else hashMap = check(hashMap, name, Integer.valueOf(num));
                                j = k - 1;
                                break;
                            }
                        }

                    } else {
                        // не стоит цифра, написан следущий элемент
                        if (String.valueOf(element.charAt(j + 1)).toUpperCase().equals(String.valueOf(element.charAt(j + 1)))) {
                            if (String.valueOf(element.charAt(j)).toUpperCase().equals(String.valueOf(element.charAt(j)))) {
                                name = "";
                                name += element.charAt(j);
                            }
                            if (f == 1) hashMap = check(hashMap, name, -1);
                            else hashMap = check(hashMap, name, 1);
                        }
                        //две буквы в названии
                        if (String.valueOf(element.charAt(j + 1)).toLowerCase().equals(String.valueOf(element.charAt(j + 1)))) {
                            name = "";
                            name += element.charAt(j);
                            name += element.charAt(j + 1);
                        }
                    }
                } else {
                    name = "";
                    if (String.valueOf(element.charAt(j)).toUpperCase().equals(String.valueOf(element.charAt(j))))
                        name += element.charAt(j);
                    else {
                        name += element.charAt(j - 1);
                        name += element.charAt(j);
                    }
                    if (f == 1) hashMap = check(hashMap, name, -1);
                    else hashMap = check(hashMap, name, 1);
                }
            }
        }
        return hashMap;
    }

    // Если ключ существует, то его значение меняется на сумму нового и прошлого значения
    private HashMap<String, Integer> check(HashMap<String, Integer> hashMap, String key, Integer value) {
        if (hashMap.containsKey(key)) {
            Integer tmp = hashMap.get(key);
            hashMap.remove(String.valueOf(key));
            hashMap.put(String.valueOf(key), tmp + value);
        } else hashMap.put(String.valueOf(key), value);
        return hashMap;
    }

}
