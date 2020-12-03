package com.example.xfocus;

public class Client {
    private static String Cl_alamat, Cl_email, Cl_id,Cl_name,Cl_no,Cl_telepon;
    private static Byte Cl_logo;

    public Client( String Cl_alamat, String Cl_email, String Cl_id, String Cl_name, String Cl_no, String Cl_telepon
                   //Byte Cl_logo
    ){
        this.Cl_alamat = Cl_alamat;
        this.Cl_email = Cl_email;
        this.Cl_id = Cl_id;
        this.Cl_name = Cl_name;
        this.Cl_no = Cl_no;
        this.Cl_telepon = Cl_telepon;
        this.Cl_logo = Cl_logo;
    }

    public static String getCl_alamat()
    {
        return Cl_alamat;
    }
    public static String getCl_email()
    {
        return Cl_email;
    }
    public static String getCl_id()
    {
        return Cl_id;
    }
    public static String getCl_name()
    {
        return Cl_name;
    }
    public static String getCl_no()
    {
        return Cl_no;
    }
    public static String getCl_telepon()
    {
        return Cl_telepon;
    }
    public static Byte getCl_logo(){
        return Cl_logo;
    }
    public static void setCl_alamat(String cl_alamat) {
        Cl_alamat = cl_alamat;
    }
    public static void setCl_email(String cl_email) {
        Cl_email = cl_email;
    }
    public static void setCl_id(String cl_id) {
        Cl_id = cl_id;
    }
    public static void setCl_name(String cl_name) {
        Cl_name = cl_name;
    }
    public static void setCl_no(String cl_no) {
        Cl_no = cl_no;
    }
    public static void setCl_telepon(String cl_telepon) {
        Cl_telepon = cl_telepon;
    }
    public static void setCl_logo(Byte cl_logo) {
        Cl_logo = cl_logo;
    }
}