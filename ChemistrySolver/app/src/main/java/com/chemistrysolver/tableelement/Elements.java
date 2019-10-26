package com.chemistrysolver.tableelement;

//  Класс характеристик элемента
public class Elements {

    // Название, латинское название, группа, электронная конфигурация, степени окисления атома,
    // агрегатное состояние, ион, радиоактивность, синтезированный элемент
    protected String name, latName, group, electronConfiguration, oxidationState, condition, ion, radioactive, made;

    // Обозначение
    public String designation;

    // Атомная масса
    public Double atomicWeight;
    // Температура, плавления и кипения, плотность атома, электроотрицательность
    protected Double meltingPoint, boilingPoint, density, electronegativity;

    // Номер атома, период
    protected Integer number, period;

    // Конструктор, в который будет передаваться вся информация об элементах
    public Elements(String designation, String name, String latName, Integer number, Integer period, String group, Double atomicWeight,
                    String electronConfiguration, String oxidationState, Double meltingPoint, Double boilingPoint, Double density,
                    String condition, Double electronegativity, String ion, String radioactive, String made){

        this.designation = designation;
        this.name = name;
        this.latName = latName;
        this.number = number;
        this.period = period;
        this.group = group;
        this.atomicWeight = atomicWeight;
        this.electronConfiguration = electronConfiguration;
        this.oxidationState = oxidationState;
        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
        this.density = density;
        this.condition = condition;
        this.electronegativity = electronegativity;
        this.ion = ion;
        this.radioactive = radioactive;
        this.made = made;
    }

}
