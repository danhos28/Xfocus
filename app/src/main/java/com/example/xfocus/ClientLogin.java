package com.example.xfocus;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClientLogin {
    private static String Status, AreaId, AreaName, isAreaPusat, UserId, UserName, ClientId, Client, ClientLogo, PegawaiId, PegawaiName, PegawaiAlias;
    private static ArrayList<String> listArea = new ArrayList();

    public ClientLogin (String Status, String AreaId, String AreaName, String isAreaPusat, String UserId, String UserName, String ClientId, String Client, String ClientLogo, String PegawaiId, String PegawaiName, String PegawaiAlias, ArrayList<String> listArea)
    {
        this.Status = Status;
        this.AreaId = AreaId;
        this.AreaName = AreaName;
        this.isAreaPusat = isAreaPusat;
        this.UserId = UserId;
        this.UserName = UserName;
        this.ClientId = ClientId;
        this.Client = Client;
        this.ClientLogo = ClientLogo;
        this.PegawaiId = PegawaiId;
        this.PegawaiName = PegawaiName;
        this.PegawaiAlias = PegawaiAlias;
        this.listArea = listArea;
    }

    public static String getStatus() {
        return Status;
    }

    public static void setStatus(String status) {
        Status = status;
    }

    public static String getAreaId() {
        return AreaId;
    }

    public static void setAreaId(String areaId) {
        AreaId = areaId;
    }

    public static String getAreaName() {
        return AreaName;
    }

    public static void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public static String getIsAreaPusat() {
        return isAreaPusat;
    }

    public static void setIsAreaPusat(String isAreaPusat) {
        ClientLogin.isAreaPusat = isAreaPusat;
    }

    public static String getUserId() {
        return UserId;
    }

    public static void setUserId(String userId) {
        UserId = userId;
    }

    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String userName) {
        UserName = userName;
    }

    public static String getClientId() {
        return ClientId;
    }

    public static void setClientId(String clientId) {
        ClientId = clientId;
    }

    public static String getClient() {
        return Client;
    }

    public static void setClient(String client) {
        Client = client;
    }

    public static String getClientLogo() {
        return ClientLogo;
    }

    public static void setClientLogo(String clientLogo) {
        ClientLogo = clientLogo;
    }

    public static String getPegawaiId() {
        return PegawaiId;
    }

    public static void setPegawaiId(String pegawaiId) {
        PegawaiId = pegawaiId;
    }

    public static String getPegawaiName() {
        return PegawaiName;
    }

    public static void setPegawaiName(String pegawaiName) {
        PegawaiName = pegawaiName;
    }

    public static String getPegawaiAlias() {
        return PegawaiAlias;
    }

    public static void setPegawaiAlias(String pegawaiAlias) {
        PegawaiAlias = pegawaiAlias;
    }

    public static ArrayList<String> getListArea() {
        return listArea;
    }

    public static void setListArea(ArrayList<String> listArea) {
        ClientLogin.listArea = listArea;
    }
}