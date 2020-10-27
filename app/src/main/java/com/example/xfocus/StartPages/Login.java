package com.example.xfocus.StartPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.xfocus.R;

public class Login extends AppCompatActivity {
    ImageView xfocuslogo;
    int aaa;
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