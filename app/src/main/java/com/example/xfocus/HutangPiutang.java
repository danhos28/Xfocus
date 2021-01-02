package com.example.xfocus;

import java.util.ArrayList;

public class HutangPiutang {
    private static String Label, Tipe, Urut, Value;
    private static ArrayList<String> ListHutangPiutang = new ArrayList();

    public HutangPiutang(String label, String tipe, String urut, String value, ArrayList<String> listHutangPiutang) {
        this.Label = label;
        this.Tipe = tipe;
        this.Urut = urut;
        this.Value = value;
        this.ListHutangPiutang = listHutangPiutang;
    }

    public static String getLabel() {
        return Label;
    }

    public static void setLabel(String label) {
        Label = label;
    }

    public static String getTipe() {
        return Tipe;
    }

    public static void setTipe(String tipe) {
        Tipe = tipe;
    }

    public static String getUrut() {
        return Urut;
    }

    public static void setUrut(String urut) {
        Urut = urut;
    }

    public static String getValue() {
        return Value;
    }

    public static void setValue(String value) {
        Value = value;
    }

    public static ArrayList<String> getListHutangPiutang() {
        return ListHutangPiutang;
    }

    public static void setListHutangPiutang(ArrayList<String> listHutangPiutang) {
        ListHutangPiutang = listHutangPiutang;
    }
}