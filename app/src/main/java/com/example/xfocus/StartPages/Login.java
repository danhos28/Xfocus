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
import com.example.xfocus.ClientLogin;
import com.example.xfocus.HomePages.Dashboard;
import com.example.xfocus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    ImageView xfocuslogo;
    EditText username,password;
    TextView client_nama,client_telp,client_alamat;
    Button btnLogin;
    ArrayList<String> listArea = new ArrayList<String>();
    ArrayList<String> listAreaId = new ArrayList<String>();
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
        String postUrl = "https://xfocus.id/login/auth_app";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("cl_no",  Client.getCl_no());
            postData.put("username", username.getText().toString());
            postData.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                      String Status = response.optString("status");
                      String AreaId = response.optString("AreaId");
                      String AreaName = response.optString("AreaName");
                      String isAreaPusat = response.optString("isAreaPusat");
                      String UserId = response.optString("UserId");
                      String UserName = response.optString("UserName");
                      String ClientId = response.optString("ClientId");
                      String Client = response.optString("Client");
                      String ClientLogo = response.optString("ClientLogo");
                      String PegawaiId = response.optString("PegawaiId");
                      String PegawaiName = response.optString("PegawaiName");
                      String PegawaiAlias = response.optString("PegawaiAlias");
                      try {
                        JSONArray jsonArray = response.getJSONArray("list_area");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String areaName = jsonObject.optString("area_name");
                        String areaId = jsonObject.optString("area_id");
                        listArea.add("All Cabang");
                        listArea.add(areaName);
                        listAreaId.add("all");
                        listAreaId.add(areaId);
                        JSONArray areaList = jsonArray.getJSONArray(1);
                            for(int j = 1; j < areaList.length();j++){
                                   if(j % 2 == 0) {
                                       String areaName2 = areaList.getString(j);
                                       listArea.add(areaName2);
                                   }
                                   else{
                                       String areaId2 = areaList.getString(j);
                                       listAreaId.add(areaId2);
                                   }
                        }
                      } catch (JSONException e) {
                    e.printStackTrace();
                    }
                      ClientLogin clientLogin = new ClientLogin(Status,AreaId,AreaName,isAreaPusat,UserId,UserName,ClientId,Client,ClientLogo,PegawaiId,PegawaiName,PegawaiAlias,listArea,listAreaId);

                      if (Status.equals("success") && !AreaId.equals("null")){
                          //Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_LONG).show();
                          Intent intent = new Intent(Login.this, Dashboard.class);
                          startActivity(intent);
                          finish();
                      }
                      else{
                          Toast.makeText(getApplicationContext(),"username or password is incorrect", Toast.LENGTH_SHORT).show();
                      }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext()," Username or Password is incorrect", Toast.LENGTH_LONG).show();
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