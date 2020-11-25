package com.example.xfocus.StartPages;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xfocus.Client;
import com.example.xfocus.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientNo extends AppCompatActivity {
    private static final String TAG = "TAG";
    ImageView xfocuslogo;
    EditText cl_no;
    Button btnCon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientno);
        xfocuslogo = findViewById(R.id.xfocus);
        cl_no = findViewById(R.id.cl_number);
        btnCon = findViewById(R.id.btnContinue);
        ((Animatable) xfocuslogo.getDrawable()).start();//start Animation

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
                    String Cl_name = response.optString("cl_name");
                    String Cl_telepon = response.optString("cl_telepon");
                    String Cl_alamat = response.optString("cl_alamat");

                    Client client = new Client(Cl_alamat,Cl_email,Cl_id,Cl_name,Cl_no,Cl_telepon);

                    Intent intent = new Intent(ClientNo.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext()," Incorrect client number ", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonObjectRequest);
        };
}