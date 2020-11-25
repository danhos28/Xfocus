package com.example.xfocus.StartPages;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xfocus.Client;
import com.example.xfocus.HomePages.Dashboard;
import com.example.xfocus.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    ImageView xfocuslogo;
    EditText username,password;
    TextView client_nama,client_telp,client_alamat;
    Button btnLogin;
    int aaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        xfocuslogo = findViewById(R.id.xfocus);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        client_nama = findViewById(R.id.client_name);
        client_alamat = findViewById(R.id.client_alamat);
        client_telp = findViewById(R.id.client_telp);
        btnLogin = findViewById(R.id.btnLogin);
        ((Animatable) xfocuslogo.getDrawable()).start();//start Animation
        client_nama.setText(Client.getCl_name());
        client_alamat.setText(Client.getCl_alamat());
        client_telp.setText(Client.getCl_telepon());
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login(){
        String postUrl = "https://xfocus.id/login/auth";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("cl_no",  Client.getCl_no());
            postData.put("username", username.getText().toString());
            postData.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                      String Status = response.optString("status");
                      String Message = response.optString("message");
                      if (Status.equals("already") && !Message.contains("Telah login pada perangkat lain")){
                          Toast.makeText(getApplicationContext()," Login Successful " , Toast.LENGTH_LONG).show();
                          //Toast.makeText(getApplicationContext(),"stats: " + response, Toast.LENGTH_SHORT).show();

                          Intent intent = new Intent(Login.this, Dashboard.class);
                          startActivity(intent);
                          finish();
                      }
                      else if(Status.equals("already") && Message.contains("Telah login pada perangkat lain")){
                          Toast.makeText(getApplicationContext()," This account is already logged in on another device, please try again in 1 minute ", Toast.LENGTH_LONG).show();
                      }
                      else{
                          Toast.makeText(getApplicationContext()," username or password is incorrect ", Toast.LENGTH_SHORT).show();
                      }
                  /*try {
                        JSONArray jsonArray = response.getJSONArray("client");
                        JSONObject client = jsonArray.getJSONObject(0);
                        String cl_name = client.getString("cl_name");

                        Log.i(TAG, cl_name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext()," Username or Password is incorrect ", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    };


    public void AnotherAcc(View view) {
        Intent intent = new Intent(Login.this, ClientNo.class);
        startActivity(intent);
        finish();
    }
}