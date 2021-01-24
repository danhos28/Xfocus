package com.example.xfocus.StartPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xfocus.ClientLogin;
import com.example.xfocus.Header;
import com.example.xfocus.HomePages.Dashboard;
import com.example.xfocus.R;
import com.example.xfocus.SessionManagerClass.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Splash extends AppCompatActivity {
    ImageView xfocuslogo;
    Thread timer;

    //Controlling back button
    @Override
    public void onBackPressed() {
        //Disable go back
        Toast.makeText(Splash.this, "Tidak bisa keluar pada fase ini", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final SessionManager session = new SessionManager(Splash.this);

        xfocuslogo = findViewById(R.id.xfocus);
        ((Animatable) xfocuslogo.getDrawable()).start();//start Animation

    timer = new Thread(){
        @Override
       public void run(){
            try{
                synchronized (this){
                    wait(3000);
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            } finally {
                Intent intent1 = new Intent(Splash.this, ClientNo.class);
                Intent intent2 = new Intent(Splash.this, Dashboard.class);
                if (!session.loginStatus()){
                    session.logoutUser();

                    startActivity(intent1);
                }
                else{
                    HashMap<String, String> userDetails = session.getUserDetailFromSession();

                    //Taking the saved arealist and arealistid value
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Splash.this);
                    Gson gson = new Gson();
                    String areaListUser = sharedPrefs.getString("areaList", "");
                    String areaListIDUser = sharedPrefs.getString("areaidList", "");
                    Type type = new TypeToken<ArrayList<String>>() {}.getType();
                    ArrayList<String> getListArea = gson.fromJson(areaListUser, type);
                    ArrayList<String> getListAreaID = gson.fromJson(areaListIDUser, type);


                    ClientLogin clientLogin = new ClientLogin(userDetails.get(session.Key_userStats), userDetails.get(session.Key_userAreaID), userDetails.get(session.Key_userAreaName),
                            userDetails.get(session.Key_isAreaPusat), userDetails.get(session.Key_userID), userDetails.get(session.Key_userName), userDetails.get(session.Key_clientID),
                            userDetails.get(session.Key_clientS), userDetails.get(session.Key_clientLogo), userDetails.get(session.Key_pegawaiID), userDetails.get(session.Key_pegawaiName),
                            userDetails.get(session.Key_pegawaiAlias), getListArea, getListAreaID);

                    startActivity(intent2);
                }
                finish();
            }
        }
    };
    timer.start();
    }
}