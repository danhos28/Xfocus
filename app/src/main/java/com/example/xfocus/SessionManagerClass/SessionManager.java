package com.example.xfocus.SessionManagerClass;

import android.content.Context;
import android.content.SharedPreferences;


import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SessionManager {

    //Variables
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String loggedIn = "loggedIn";

    public static final String Key_userStats = "status";
    public static final String Key_userAreaID = "areaid";
    public static final String Key_userAreaName = "areaname";
    public static final String Key_isAreaPusat = "areapusat";
    public static final String Key_userID = "userid";
    public static final String Key_userName = "username";
    public static final String Key_clientID = "clientid";
    public static final String Key_clientS = "client";
    public static final String Key_clientLogo = "clientlogo";
    public static final String Key_pegawaiID = "pegawaiid";
    public static final String Key_pegawaiName = "pegawainame";
    public static final String Key_pegawaiAlias = "pegawaialias";

    //Session creation
    public SessionManager(Context _context){
        context = _context;
        //Shared preference name
        userSession = context.getSharedPreferences("userLoginStats",Context.MODE_PRIVATE);

        //Edit data in session
        editor = userSession.edit();
    }

    public void UserLoggedIn(){
        editor.putBoolean(loggedIn, true);
        editor.commit();
    }

    //Put userdata to the session userLoginStats
    public void createLoginSession(String status, String areaid, String areaname, String ispusat, String userid, String username,
                                   String clientid, String client, String clientlogo, String pegawaiid, String pegawainame,
                                   String pegawaialias){

        editor.putString(Key_userStats, status);
        editor.putString(Key_userAreaID, areaid);
        editor.putString(Key_userAreaName, areaname);
        editor.putString(Key_isAreaPusat, ispusat);
        editor.putString(Key_userID, userid);
        editor.putString(Key_userName, username);
        editor.putString(Key_clientID, clientid);
        editor.putString(Key_clientS, client);
        editor.putString(Key_clientLogo, clientlogo);
        editor.putString(Key_pegawaiID, pegawaiid);
        editor.putString(Key_pegawaiName, pegawainame);
        editor.putString(Key_pegawaiAlias, pegawaialias);

        editor.commit();
    }

    //To get all saved userdata in session
    public HashMap<String, String> getUserDetailFromSession(){
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(Key_userStats, userSession.getString(Key_userStats, null));
        userData.put(Key_userAreaID, userSession.getString(Key_userAreaID, null));
        userData.put(Key_userAreaName, userSession.getString(Key_userAreaName, null));
        userData.put(Key_isAreaPusat, userSession.getString(Key_isAreaPusat, null));
        userData.put(Key_userID, userSession.getString(Key_userID, null));
        userData.put(Key_userName, userSession.getString(Key_userName, null));
        userData.put(Key_clientID, userSession.getString(Key_clientID, null));
        userData.put(Key_clientS, userSession.getString(Key_clientS, null));
        userData.put(Key_clientLogo, userSession.getString(Key_clientLogo, null));
        userData.put(Key_pegawaiID, userSession.getString(Key_pegawaiID, null));
        userData.put(Key_pegawaiName, userSession.getString(Key_pegawaiName, null));
        userData.put(Key_pegawaiAlias, userSession.getString(Key_pegawaiAlias, null));

        return userData;
    }


    //Check whether the user already logged in or not
    public boolean loginStatus(){
        if (userSession.getBoolean(loggedIn, false)){ //Already login
            return true;
        }
        else { //Not yet login
            return false;
        }
    }

    //Clear session when logged out
    public void logoutUser(){
        editor.clear();
        editor.commit();
    }
}
