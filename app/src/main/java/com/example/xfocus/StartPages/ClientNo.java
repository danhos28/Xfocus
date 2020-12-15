package com.example.xfocus.StartPages;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xfocus.Client;
import com.example.xfocus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ClientNo extends AppCompatActivity {
    private static final String TAG = "TAG";
    ImageView xfocuslogo;
    EditText cl_no;
    boolean doubleBackToExitPressedOnce = false;
    Button btnCon;
    public static String Cookies;
    public static String[] cookiesKey;
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
        Toast.makeText(this, " Tekan BACK lagi untuk keluar ", Toast.LENGTH_SHORT).show();

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
        setContentView(R.layout.activity_clientno);
        xfocuslogo = findViewById(R.id.xfocus);
        cl_no = findViewById(R.id.cl_number);
        btnCon = findViewById(R.id.btnContinue);
        ((Animatable) xfocuslogo.getDrawable()).start();//start Animation
        getCookies(); //get cookies

        btnCon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ClientNumber();
            }
        });
    }
    public void ClientNumber(){
        String postUrl = "https://xfocus.id/login/cek_client";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("cl_no", cl_no.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Toast.makeText(getApplicationContext()," status : "+response, Toast.LENGTH_LONG).show();
                String Cl_no = response.optString("cl_no");
                String Cl_id = response.optString("cl_id");
                String Cl_email = response.optString("cl_email");
                String Cl_logo = response.optString("cl_logo");
                String Cl_name = response.optString("cl_name");
                String Cl_telepon = response.optString("cl_telepon");
                String Cl_alamat = response.optString("cl_alamat");

                Client client = new Client(Cl_alamat,Cl_email,Cl_id,Cl_name,Cl_no,Cl_telepon, Cl_logo);

                Intent intent = new Intent(ClientNo.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), " No client salah ", Toast.LENGTH_SHORT).show();
            }
            })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie", cookiesKey[0]);
                headers.put("Content-Type","application/json");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
    public void getCookies(){
        String postUrl = "https://xfocus.id";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest sr = new StringRequest(Request.Method.GET, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext()," status : "+response, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), " Cookies success ", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), " Cookies failed ", Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    Map<String, String> responseHeaders = response.headers;
                    Cookies = responseHeaders.get("Set-Cookie");
                    Log.e("getCookies: ", Cookies);
                    cookiesKey = ClientNo.Cookies.split(";");
                    Log.e("getCookiesKey: ", "xfocus_session=7a8d71de8b71a488850735fca5c0b23bc1a16771");
                    return Response.success(new String(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        requestQueue.add(sr);
    }
}