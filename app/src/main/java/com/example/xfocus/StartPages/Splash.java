package com.example.xfocus.StartPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xfocus.R;

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
                Intent intent = new Intent(Splash.this, ClientNo.class);
                startActivity(intent);
                finish();
            }
        }
    };
    timer.start();
    }
}