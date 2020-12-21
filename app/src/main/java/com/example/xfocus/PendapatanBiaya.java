package com.example.xfocus;

import java.util.ArrayList;

public class PendapatanBiaya {
    private static String Label,Tipe,Value;
    private static ArrayList<String> ListPendapatanBiaya = new ArrayList();
    public PendapatanBiaya(String label, String tipe, String value, ArrayList<String>listPendapatanBiaya){
        this.Label = label;
        this.Tipe = tipe;
        this.Value = value;
        this.ListPendapatanBiaya = listPendapatanBiaya;
    }
    public static String getLabel(){ return Label; }
    public static void setLabel(String label){
        Label = label;
    }
    public static String getTipe(){ return Tipe; }
    public static void setTipe(String tipe){
        Tipe = tipe;
    }
    public static String getValue(){ return Value; }
    public static void setValue(String value){
        Value = value;
    }
    public static ArrayList<String> getListPendapatanBiaya() { return ListPendapatanBiaya; }
    public static void setListPendapatanBiaya(ArrayList<String> listPendapatanBiaya) { ListPendapatanBiaya = listPendapatanBiaya; }
}

