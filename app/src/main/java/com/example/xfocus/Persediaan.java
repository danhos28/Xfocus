package com.example.xfocus;

import java.util.ArrayList;

public class Persediaan {
    private static String Label,Value;
    private static ArrayList<String> ListPersediaan = new ArrayList();
    public Persediaan (String label, String value, ArrayList<String>listPersediaan){
        this.Label = label;
        this.Value = value;
        this.ListPersediaan = listPersediaan;
    }
    public static String getLabel(){ return Label; }
    public static void setLabel(String label){
        Label = label;
    }
    public static String getValue(){ return Value; }
    public static void setValue(String value){
        Value = value;
    }
    public static ArrayList<String> getListPersediaan() { return ListPersediaan; }
    public static void setListPersediaan(ArrayList<String> listPersediaan) { ListPersediaan = listPersediaan; }
}
