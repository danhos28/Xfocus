package com.example.xfocus;

import java.util.ArrayList;

public class Penjualan {
    private static String Label,Urut,Value;
    private static ArrayList<String> ListPenjualan = new ArrayList();

    public Penjualan (String label,String urut, String value, ArrayList<String>listpenjualan){
        this.Label = label;
        this.Urut = urut;
        this.Value = value;
        this.ListPenjualan = listpenjualan;
    }
    public static String getLabel(){ return Label; }
    public static void setLabel(String label){
        Label = label;
    }
    public static String getUrut(){ return Urut; }
    public static void setUrut(String urut){
        Urut = urut;
    }
    public static String getValue(){ return Value; }
    public static void setValue(String value){
        Value = value;
    }
    public static ArrayList<String> getListPenjualan() { return ListPenjualan; }
    public static void setListPenjualan(ArrayList<String> listPenjualan) { ListPenjualan = listPenjualan; }
}
