package com.example.xfocus;

import java.util.ArrayList;

public class Header {
    private static String Label,Persen,Urut,Value;
    private static ArrayList<String> ListHeader = new ArrayList();

    public Header (String label, String persen, String urut, String value, ArrayList<String>listHeader){
        this.Label = label;
        this.Persen = persen;
        this.Urut = urut;
        this.Value = value;
        this.ListHeader = listHeader;
    }

    public static String getLabel(){
        return Label;
    }
    public static void setLabel(String label){
        Label = label;
    }
    public static String getPersen(){
        return Persen;
    }
    public static void setPersen(String persen){
        Persen = persen;
    }
    public static String getUrut() {
        return Urut;
    }
    public static void setUrut(String urut){
        Urut = urut;
    }
    public static String getValue() {
        return Value;
    }
    public static void setValue(String value){
        Value = value;
    }
    public static ArrayList<String> getListHeader() {
        return ListHeader;
    }
    public static void setListHeader(ArrayList<String> listHeader) {
        ListHeader = listHeader;
    }
}
