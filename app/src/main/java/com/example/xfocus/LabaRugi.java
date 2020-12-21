package com.example.xfocus;

import java.util.ArrayList;

public class LabaRugi {
    private static String Label,ValueA,ValueB;
    private static ArrayList<String> ListLabaRugi = new ArrayList();
    public LabaRugi(String label, String valueA, String valueB, ArrayList<String>listLabaRugi){
        this.Label = label;
        this.ValueA = valueA;
        this.ValueB = valueB;
        this.ListLabaRugi = listLabaRugi;
    }
    public static String getLabel(){ return Label; }
    public static void setLabel(String label){
        Label = label;
    }
    public static String getValueA(){ return ValueA; }
    public static void setValueA(String valueA){
        ValueA = valueA;
    }
    public static String getValueB(){ return ValueB; }
    public static void setValueB(String valueB){
        ValueB = valueB;
    }
    public static ArrayList<String> getListLabaRugi() { return ListLabaRugi; }
    public static void setListLabaRugi(ArrayList<String> listLabaRugi) { ListLabaRugi = listLabaRugi; }
}
