package com.example.xfocus.StartPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xfocus.R;

public class Login extends AppCompatActivity {
    ImageView xfocuslogo;

    boolean doubleBackToExitPressedOnce = false;

    //Controlling the back button
    @Override
    public void onBackPressed() {
        //For back pressed twice for quit
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please tap BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        xfocuslogo = findViewById(R.id.xfocus);
        ((Animatable) xfocuslogo.getDrawable()).start();//start Animation
    }

    public void AnotherAcc(View view) {
        Intent intent = new Intent(Login.this, ClientNo.class);
        startActivity(intent);
        finish();
    }
}