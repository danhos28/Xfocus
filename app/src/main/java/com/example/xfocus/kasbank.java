package com.example.xfocus;

import java.util.ArrayList;

public class kasbank {
    private static String Label,Value;
    private static ArrayList<String> ListKasbank = new ArrayList();

    public kasbank (String label, String value, ArrayList<String>listKasbank){
        this.Label = label;
        this.Value = value;
        this.ListKasbank = listKasbank;
    }
    public static String getLabel(){ return Label; }
    public static void setLabel(String label){
        Label = label;
    }
    public static String getValue(){ return Value; }
    public static void setValue(String value){
        Value = value;
    }
    public static ArrayList<String> getListKasbank() { return ListKasbank; }
    public static void setListKasbank(ArrayList<String> listKasbank) { ListKasbank = listKasbank; }
}
