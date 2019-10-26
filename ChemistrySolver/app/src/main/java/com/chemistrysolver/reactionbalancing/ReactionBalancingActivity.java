package com.chemistrysolver.reactionbalancing;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chemistrysolver.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

// активность, в которой расставляются коэффициенты в реакции
public class ReactionBalancingActivity extends AppCompatActivity implements View.OnClickListener{

    // поле ввода реакции
    EditText editText;
    // кнопка, по которой реакция вводится
    Button button;
    // поле отображения реакции без коэффициентов и с коэффициентами
    TextView textView, reaction;

    // левая часть уравнения
    public ArrayList<String> leftPart;
    // правая часть уравнения
    public ArrayList<String> rightPart;

    // arrayList хранит в себе все вещества в преобразованном виде (элемент из вещества и его количество)
    public ArrayList<HashMap<String, Integer>> arrayList;

    // вещества, которые поменяли местами
    public ArrayList<Integer> sw;

    // Хранит в себе все элементы входящие в реакцию
    public TreeSet<String> treeSet;
    // Хранит в себе все элементы входящие в реакцию в левой части уравнения
    public TreeSet<String> treeSet1;
    // Хранит в себе все элементы входящие в реакцию в правой части уравнения
    public TreeSet<String> treeSet2;

    // Матрица, в которой строки - элементы, столбцы - вещества
    public double[][] matrix;

    // Уравнение реакции
    public String text;

    // строка с цифрами для того, чтобы узнать является ли символ цифрой
    String number = "1234567890";

    // список всех элементов таблицы Менделеева
    public String elements = "H He Li Be B C N O F Ne Na Mg Al Si P S Cl Ar K Ca Sc Ti V Cr Mn Fe Co Ni Cu Zn Ga Ge As Se Br Kr" +
            "Rb Sr Y Zr Nb Mo Tc Ru Rh Pd Ag Cd In Sn Sb Te I Xe Cs Ba La Ce Pr Nd Pm Sm Eu Gd Tb Dy Ho Er Tm Yb Lu Hf Ta W" +
            "Re Os Ir Pt Au Hg Tl Pb Bi Po At Rn Fr Ra Ac Th Pa U Np Pu Am Cm Bk Cf Es Fm Md No Lr Rf Db Sg Bh Hn Mt Ds Rg Cn Nh Fl Mc Lv Ts Og";

    // переменные для отслеживания неправильного ввода
    public boolean bool1 = true, bool2 = true, bool3 = false, bool4 = false, bool5 = true;
    // массив коэффициентов
    public double[] ans;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_balancing);

        // переменная для красивого отображения текста в заголовке
        SpannableString s = new SpannableString("Балансировка реакций");
        s.setSpan(new TypefaceSpan("fonts/Candara.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // установка стрелки "Назад" и заголовка активности
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(s);

        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Candara.ttf");

        editText = findViewById(R.id.reaction);
        editText.setTypeface(typeface);
        button = findViewById(R.id.reactionOK);
        button.setOnClickListener(this);
        button.setTypeface(typeface);
        textView = findViewById(R.id.reactionAnswer);
        textView.setTypeface(typeface);
        reaction = findViewById(R.id.startreaction);
        reaction.setTypeface(typeface);
        textView.setVisibility(View.INVISIBLE);
        reaction.setVisibility(View.INVISIBLE);
    }

    // начало процесса расстановки коэффициентов
    public void startAlgorithm(){
        bool2 = true; bool1 = true; bool3 = false; bool4 = false;

        textRecognizing(text);

        treeSet = new TreeSet<>();
        treeSet1 = new TreeSet<>();
        treeSet2 = new TreeSet<>();

        if (bool5) {
            // проверка на правильное написание
            for (int i = 0; i < leftPart.size(); i++) {
                String element = leftPart.get(i);
                int count1 = 0, count2 = 0, count3 = 0, count4 = 0;
                for (int j = 0; j < leftPart.get(i).length(); j++) {
                    if (leftPart.get(i).charAt(j) == '(') count1++;
                    if (leftPart.get(i).charAt(j) == ')') count2++;
                    if (leftPart.get(i).charAt(j) == '[') count3++;
                    if (leftPart.get(i).charAt(j) == ']') count4++;
                    if (count1 < count2 || count3 < count4) bool1 = false;
                }
                // раскрытие скобок, если написано правильно
                if (count1 != count2 || count3 != count4) bool1 = false;
                else if (count1 != 0 || count3 != 0) {
                    for (int j = 0; j < count1 + count3; j++) {
                        element = parser(element);
                    }
                    leftPart.set(i, element);
                }
            }

            // проверка на правильное написание
            for (int i = 0; i < rightPart.size(); i++) {
                String element = rightPart.get(i);
                int count1 = 0, count2 = 0, count3 = 0, count4 = 0;
                for (int j = 0; j < rightPart.get(i).length(); j++) {
                    if (rightPart.get(i).charAt(j) == '(') count1++;
                    if (rightPart.get(i).charAt(j) == ')') count2++;
                    if (rightPart.get(i).charAt(j) == '[') count3++;
                    if (rightPart.get(i).charAt(j) == ']') count4++;
                    if (count1 < count2 || count3 < count4) bool1 = false;
                }
                // раскрытие скобок, если написано правильно
                if (count1 != count2 || count3 != count4) bool1 = false;
                else if (count1 != 0 || count3 != 0) {
                    for (int j = 0; j < count1 + count3; j++) {
                        element = parser(element);
                    }
                    rightPart.set(i, element);
                }
            }

            String newText = getNewText(text);
            SpannableStringBuilder ssb = getSsb(newText);
            reaction.setVisibility(View.VISIBLE);
            reaction.setText(ssb);

            if (rightPart.size() == 0) {
                textView.setVisibility(View.VISIBLE);
                reaction.setVisibility(View.INVISIBLE);
                textView.setText("Уравнение должно быть полным. Допишите правую часть уравнения");
                bool2 = false;
            } else if (leftPart.size() == 0) {
                textView.setVisibility(View.VISIBLE);
                reaction.setVisibility(View.INVISIBLE);
                textView.setText("Уравнение должно быть полным. Допишите левую часть уравнения");
                bool2 = false;
            }
            if (bool2) {
                if (bool1) setCoefficients();
                else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("В записи уравнения реакции есть ошибки");
                }
            }
        } else reaction.setVisibility(View.INVISIBLE);

    }

    // текст для отображения в reaction или textView
    public String getNewText(String text){
        int count = text.length();
        String newText  = text;
        if (count > 20){
            for (int k = 1; k <= count / 20 ; k++) {
                for (int i = 20 * k; i >= 0; i--) {
                    if (newText.charAt(i) == '+'){
                        String string = newText.substring(0, i) + "+\n+" + newText.substring(i + 1, newText.length());
                        newText = string;
                        break;
                    } else if (newText.charAt(i) == '='){
                        String string = newText.substring(0, i) + "=\n=" + newText.substring(i + 1, newText.length());
                        newText = string;
                        break;
                    }
                }
            }
            String string = "\n" + newText + "\n";
            newText = string;
        } else newText = "\n" + text + "\n";
        return newText;
    }

    public SpannableStringBuilder getSsb(String newText){
        SpannableStringBuilder ssb = new SpannableStringBuilder(newText);
        for (int i = 0; i < newText.length(); i++) {
            if (number.contains(String.valueOf(newText.charAt(i))) && i == 1){
                for (int k = i; k < newText.length(); k++) {
                    if (number.contains(String.valueOf(newText.charAt(k)))) i++;
                    else break;
                }
                ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#6068F0")), 0, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (newText.charAt(i) == '+' || newText.charAt(i) == '='){
                i++; if (number.contains(String.valueOf(newText.charAt(i)))){
                    int l = 0;
                    for (int k = i; k < newText.length(); k++) {
                        if (number.contains(String.valueOf(newText.charAt(k)))) l++;
                        else break;
                    }
                    ssb.setSpan(new StyleSpan(Typeface.BOLD), i, i + l, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#6068F0")), i, i + l, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    i += l;
                }
            } else if (number.contains(String.valueOf(newText.charAt(i)))){
                ssb.setSpan(new SubscriptSpan(), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new RelativeSizeSpan(0.75f), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ssb;
    }

    public void setCoefficients(){
        arrayList = new ArrayList<>();
        for (int i = 0; i < leftPart.size(); i++) {
            arrayList.add(getInformation(leftPart.get(i), 0));

        }
        for (int i = 0; i < rightPart.size(); i++) {
            arrayList.add(getInformation(rightPart.get(i), 1));
        }

        // перестановка веществ для удобного решения СЛАУ
        swapArrayList();

        // проверяем на правильность написания реакции
        String e = "";
        for (String s: treeSet) {
            s += " ";
            if (!elements.contains(s)){
                e += s;
                e += " ";
                bool3 = true;
            }
        }

        if (bool3){
            textView.setVisibility(View.VISIBLE);
            reaction.setVisibility(View.INVISIBLE);
            textView.setText("В уравнении реакции есть несуществующие элементы: " + e);
        }
        else {
            if (treeSet1.size() == treeSet2.size()) {
                for (String s: treeSet1) treeSet2.remove(s);
                if (treeSet2.size() != 0){
                    String string = "В левой части уравнения нет элементов из правой части: ";
                    for (String s: treeSet2){
                        string += s;
                        string += " ";
                    }
                    textView.setVisibility(View.VISIBLE);
                    reaction.setVisibility(View.INVISIBLE);
                    textView.setText(string);
                } else {
                    getMatrix(); // Создаём матрицу
                    // Метод Гаусса для решения СЛАУ. Из СЛАУ находим коэффициенты
                    ans = Gauss(matrix);
                    // Получаем строку с ответом
                    getStroke(ans);
                }
            } else if (treeSet2.size() < treeSet1.size()) {
                for (String s : treeSet2) treeSet1.remove(s);
                String string = "В правой части уравнения нет элементов из левой части уравнения: ";
                for (String s : treeSet1){
                    string += s;
                    string += " ";
                }
                textView.setVisibility(View.VISIBLE);
                textView.setText(string);

            } else {
                for (String s : treeSet1) treeSet2.remove(s);
                String string = "В левой части уравнения нет элементов из правой части уравнения: ";
                for (String s : treeSet2){
                    string += s;
                    string += " ";
                }
                textView.setVisibility(View.VISIBLE);
                reaction.setVisibility(View.INVISIBLE);
                textView.setText(string);
            }
        }
    }

    // Метод распознавания веществ левой и правой чати уравнения
    public void textRecognizing(String text) {
        leftPart = new ArrayList<>();
        rightPart = new ArrayList<>();
        int flag = 0;
        String element = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != '+' && text.charAt(i) != '=') {
                for (int j = i; j < text.length(); j++) {
                    if (text.charAt(j) != '+' && text.charAt(j) != '=') {
                        element += text.charAt(j);
                    } else {
                        if (flag == 0) leftPart.add(element);
                        if (flag == 1) rightPart.add(element);
                        if (text.charAt(j) == '=') flag = 1;
                        element = "";
                        i = j;
                        break;
                    }
                    if (j == text.length() - 1 && flag == 1) {
                        rightPart.add(element);
                        element = "";
                        i = text.length();
                    }
                }
            }
        }
        if(text.charAt(text.length() - 1) == '+' || text.charAt(text.length() - 1) == '='
                || text.charAt(0) == '+' || text.charAt(0) == '='){
            bool5 = false;
            textView.setVisibility(View.VISIBLE);
            textView.setText("В записи уравнения реакции есть ошибки");
        } else {
            for (int i = 0; i < text.length() - 1; i++) {
                if (i == 0 && number.contains(String.valueOf(text.charAt(i)))){
                    bool5 = false;
                    textView.setVisibility(View.VISIBLE);
                    reaction.setVisibility(View.INVISIBLE);
                    textView.setText("Уравнение реакции должно быть написано без коэффициентов");
                    break;
                } else if ((text.charAt(i) == '+' || text.charAt(i) == '=') && number.contains(String.valueOf(text.charAt(i + 1)))){
                    bool5 = false;
                    textView.setVisibility(View.VISIBLE);
                    reaction.setVisibility(View.INVISIBLE);
                    textView.setText("Уравнение реакции должно быть написано без коэффициентов");
                    break;
                }
            }
        }

    }

    // метод, возвращающий строку с раскрытыми скобками
    public String parser(String element) {
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
                } else a = 1; // Если строка оканчивается на скобку, то таких скобок всего 1, значит, a = 1

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
                        // Если число не стоит, то индекс у элемента равен 1, значит, a * 1 = a
                        str += a;
                        if (element.charAt(j) != ')' && element.charAt(j) != ']')
                            // Добавляем символ, если он не скобка
                            str += element.charAt(j);
                    } else if (element.charAt(j) != ')' && element.charAt(j) != ']')
                        // Добавляем символ, если он не скобка
                        str += element.charAt(j);
                }
                // К части строки, которую нужно заменить добавляем число, если оно стоит
                if (a != 1)
                    part += String.valueOf(a);
                // Заменяем в строке, подстроку на искомую
                element = element.replace(part, str);
                break;
            }
        }
        return element;
    }

    // Метод, который возвращает HashMap<String, Integer>, в котором записаны элементы и их количество
    public HashMap<String, Integer> getInformation(String element, int f) {
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
                                    if (f == 1) hashMap = check(hashMap, name, -1 * Integer.valueOf(num));
                                    else hashMap = check(hashMap, name, Integer.valueOf(num));
                                    treeSet.add(name);
                                    if (f == 0) treeSet1.add(name);
                                    else if (f == 1) treeSet2.add(name);
                                    name = "";
                                }
                            } else {
                                if (f == 1) hashMap = check(hashMap, name, -1 * Integer.valueOf(num));
                                else hashMap = check(hashMap, name, Integer.valueOf(num));
                                treeSet.add(name);
                                if (f == 0) treeSet1.add(name);
                                else if (f == 1) treeSet2.add(name);
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
                            treeSet.add(name);
                            if (f == 0) treeSet1.add(name);
                            else if (f == 1) treeSet2.add(name);
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
                    treeSet.add(name);
                    if (f == 0) treeSet1.add(name);
                    else if (f == 1) treeSet2.add(name);
                }
            }
        }
        return hashMap;
    }

    // Если ключ существует, то его значение меняется на сумму нового и прошлого значения
    public HashMap<String, Integer> check(HashMap<String, Integer> hashMap, String key, Integer value) {
        if (hashMap.containsKey(key)) {
            Integer tmp = hashMap.get(key);
            hashMap.remove(String.valueOf(key));
            hashMap.put(String.valueOf(key), tmp + value);
        } else hashMap.put(String.valueOf(key), value);
        return hashMap;
    }

    // вещество, состояшее из одного элемента передвигаем на первую позицию
    public void swapArrayList(){
        int k = 0;
        sw = new ArrayList<>();
        for (int i = 0; i < leftPart.size(); i++) {
            if (arrayList.get(i).size() == 1 && arrayList.get(i).containsValue(1)){
                HashMap<String, Integer> tmp;
                tmp = arrayList.get(k);
                arrayList.set(k, arrayList.get(i));
                arrayList.set(i, tmp);
                sw.add(i);
                sw.add(k);
                k++;
            }
        }
    }

    // Создание матрицы
    public void getMatrix() {
        int j = 0;
        matrix = new double[treeSet.size()][arrayList.size() + 1];
        for (String key : treeSet) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).containsKey(key)) {
                    matrix[j][i] = Double.valueOf(arrayList.get(i).get(key));
                } else matrix[j][i] = 0;
                matrix[j][arrayList.size()] = 0;
            }
            j++;
        }
    }

    // нахождение нулевых строк
    public double[][] findNull(double[][] matrix) {
        boolean flag;
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            flag = true;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) positions.add(i);
        }
        return delete(matrix, positions);
    }

    // нахождение пропорциональности в строке
    public double[][] findProportionality(double[][] matrix) {
        int k;
        for (int i = 0; i < matrix.length; i++) {
            k = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                k = gcd(k, (int) Math.abs(matrix[i][j]));
            }
            if (k != 1) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] /= k;
                }
            }
        }
        return matrix;
    }

    // алгоритм Евклида
    public Integer gcd(Integer a, Integer b) {
        if (a == 0 || b == 0)
            return a + b;
        if (a > b)
            return gcd(a % b, b);
        else
            return gcd(a, b % a);
    }

    // удаляет строки
    public double[][] delete(double[][] matrix, ArrayList<Integer> positions) {
        int count = 0;
        double[][] arr = new double[matrix.length - positions.size()][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            if (!positions.contains(i)) {
                arr[i - count] = matrix[i];
            } else {
                count++;
            }
        }
        return arr;
    }

    // упрощение матрицы
    public double[][] simplification(double[][] matrix) {
        matrix = findNull(matrix);
        matrix = findProportionality(matrix);

        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                if (i != j) {
                    boolean flag = true;
                    for (int k = 0; k < matrix[i].length; k++) {
                        if (matrix[i][k] != matrix[j][k]) {
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) {
                        flag = true;
                        for (int k = 0; k < matrix[i].length; k++) {
                            if (matrix[i][k] != -matrix[j][k]) {
                                flag = false;
                                break;
                            }
                        }
                    }

                    if (flag) {
                        if (!positions.contains(j))
                            positions.add(j);
                    }

                }
            }
        }
        return delete(matrix, positions);
    }

    // меняет строки местами
    public double[][] changeLines(double[][] matrix, int position) {
        double[] line = matrix[0];
        matrix[0] = matrix[position];
        matrix[position] = line;
        return matrix;
    }

    // перезаполнение исходной матрицы для конечного числа решения
    public double[][] refillMatrix(double[][] matrix) {
        double[][] newMatrix = matrix;
        if (matrix.length <= matrix[0].length - 2) {
            newMatrix = new double[matrix.length + matrix[0].length - matrix.length - 1][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                newMatrix[i] = matrix[i];
            }
            for (int i = matrix.length; i < newMatrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    newMatrix[i][j] = matrix[i - matrix.length][j] + matrix[i - matrix.length + 1][j];
                }

            }
            matrix = newMatrix;
        }
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][i] == 0) {
                for (int j = 0; j < newMatrix.length; j++) {
                    if (newMatrix[j][i] != 0) {
                        for (int k = 0; k < matrix.length; k++) {
                            for (int l = 0; l < newMatrix[0].length; l++) {
                                matrix[k][l] += newMatrix[j][l];
                            }
                        }

                        break;
                    }
                }
            }
        }
        int max = Integer.MIN_VALUE, position = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] > max) {
                position = i;
                max = (int) matrix[i][0];
            }
        }
        changeLines(matrix, position);
        return matrix;
    }

    // дешифратор решений уравнения
    public double[] decode(double[] x){
        int k = 0;
        double[] answers = new double[x.length-1];
        for (int j = 0; j < answers.length; j++) {
            k = gcd(k, (int) Math.abs(x[j]));
        }
        if (k != 1) {
            for (int j = 0; j < answers.length; j++) {
                answers[j] = x[j]/k;
            }
        }
        return answers;
    }

    // Метод Гаусса
    public double[] Gauss(double[][] matrix) {
        double l;

        matrix = simplification(matrix);
        matrix = refillMatrix(matrix);

        //приведение к виду трапеции
        for (int k = 0; k < matrix.length; k++) {
            l = matrix[k][k];
            for (int i = k + 1; i < matrix.length; i++) {
                for (int j = matrix[i].length - 1; j >= 0; j--) {
                    matrix[i][j] = matrix[i][j] * l - matrix[i][k] * matrix[k][j];
                }
            }
        }

        //обратный ход
        double[] x = new double[matrix[0].length];

        for (int i = matrix.length - 1; i >= 0; i--) {
            if (matrix[i][i] > 0.000000001 || matrix[i][i] < -0.000000001) {
                matrix[i][matrix[0].length - 1] /= matrix[i][i];
            } else {
                matrix[i][matrix[0].length - 1] = 232792560;
            }
            matrix[i][i] = 1;
            x[i] = matrix[i][matrix[0].length - 1];
            for (int j = 0; j < i; j++) {
                for (int k = matrix[0].length - 1; k >= i; k--) {
                    matrix[j][k] -= matrix[j][i] * matrix[i][k];
                }
            }
        }

        x = decode(x);
        return x;
    }

    public void getStroke(double[] answers) {
        String newText = "";
        int j = 1;
        // Если меняли вещества местами, то для правильного вывода меняем местами ответы
        Double tmp = 0.0;
        for (int i = 0; i < sw.size(); i++) {
            tmp = answers[sw.get(i)];
            answers[sw.get(i)] = answers[sw.get(i + 1)];
            answers[sw.get(i + 1)] = tmp;
            i++;
        }
        if (answers[0] != 1.0) newText += Math.round(answers[0]);
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != '+' && text.charAt(i) != '='){
                newText += text.charAt(i);
            } else {
                newText += text.charAt(i);
                // проверка полученных коэффициентов на то, что они целые и больше нуля
                if (answers[j] == Math.round(answers[j]) && answers[j] != 1.0 && answers[j] > 0.0){
                    newText += Math.round(answers[j]);
                } else if (answers[j] != Math.round(answers[j]) || answers[j] <= 0.0) {
                    bool4 = true;
                    break;
                }
                j++;
            }
        }
        textView.setVisibility(View.VISIBLE);
        if (bool4) textView.setText("Невозможно расставить коэффициеты. Возможно, реакция не существует");
        else{
            newText = getNewText(newText);
            SpannableStringBuilder ssb = getSsb(newText);
            textView.setText(ssb);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == button.getId()){
            // опускаем клавиатуру
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            // Запускаем алгоритм, если введённая строка не пустая
            if (!editText.getText().toString().equals("")){
                text = "";
                text = editText.getText().toString();
                // Если в алгоритме произошла ошибка, то выводим в поле текст об ошибке в реакции
                try {
                    startAlgorithm();
                } catch (Exception e){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("В уравнении реакции есть ошибки");
                }
            } else {
                reaction.setVisibility(View.INVISIBLE);
                textView.setText("Введите уравнение реакции");
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
