package com.example.xfocus.HomePages;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xfocus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    Spinner spinnerArea;
    ArrayList<String> list_area = new ArrayList<>();
    ArrayAdapter<String> areaAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        requestQueue = Volley.newRequestQueue(this);
        spinnerArea = findViewById(R.id.spinnerArea);
        comboarea();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
        public void comboarea(){
            String url = "https://xfocus.id/login/auth";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray = response.getJSONArray("list_area");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String area = jsonObject.getString("areaname");
                            list_area.add(area);
                            areaAdapter = new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_item, list_area);
                            areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerArea.setAdapter(areaAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), " Failed ", Toast.LENGTH_SHORT).show();

                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }
