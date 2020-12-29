package com.example.xfocus.HomePages;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.xfocus.ClientLogin;
import com.example.xfocus.Header;
import com.example.xfocus.HutangPiutang;
import com.example.xfocus.LabaRugi;
import com.example.xfocus.PendapatanBiaya;
import com.example.xfocus.Penjualan;
import com.example.xfocus.Persediaan;
import com.example.xfocus.R;
import com.example.xfocus.Kasbank;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatePickerDialog.OnDateSetListener mDateSetListener,mDateSetListener2;

    TextView persediaanValue, kasdanbankValue, penjualanValue, pendapatanValue;

    List<String[]> ArrayDefaultFormat = new ArrayList<String[]>();

    Float totalPenjualan;

    LinearLayout progressDashboardBar, contentDashboard, persediaanDropdown, kasdanbankDropdown, penjualanDropdown, pendapatanDropdown;
    CardView persediaanCard, kasdanbankCard, penjualanCard, pendapatanCard;
    TextView first_date, last_date, username;
    Button submitDashboard;
    Spinner spinnerArea, spinnerTampilan, spinnerPeriode;
    ArrayList<String> list_area = new ArrayList<>();
    ArrayList<String> list_tampilan = new ArrayList<>();
    ArrayList<String> list_periode = new ArrayList<>();
    ArrayList<String> list_header = new ArrayList<>();
    ArrayList<String> list_kasbank = new ArrayList<>();
    ArrayList<String> list_persediaan = new ArrayList<>();
    ArrayList<String> list_penjualan = new ArrayList<>();
    ArrayList<String> list_pendapatan = new ArrayList<>();
    int[] diagramColors = {Color.rgb(229, 57, 53), Color.rgb(255, 204, 128  ),
                            Color.rgb(156, 39, 176), Color.rgb(234, 128, 252), Color.rgb(77, 208, 225),
                            Color.rgb(217, 80, 138), Color.rgb(254, 149, 7), Color.rgb(254, 247, 120),
                            Color.rgb(106, 167, 134), Color.rgb(53, 194, 209), Color.rgb(0, 96, 100),
                            Color.rgb(100, 255, 218), Color.rgb(100, 255, 218 ), Color.rgb(253, 216, 53 ),
                            Color.rgb(255, 171, 145)};

    Header header;
    Kasbank kasbank;
    Persediaan persediaan;
    Penjualan penjualan;
    HutangPiutang hutangPiutang;
    PendapatanBiaya pendapatanBiaya;
    LabaRugi labaRugi;

    ArrayAdapter<String> areaAdapter,tampilanAdapter,periodAdapter;
    RequestQueue requestQueue;
    ScrollView scrollDashboard;
    PieChart donutChartPersediaan, donutChartKasdanBank, donutChartPenjualan, donutChartPendapatan;
    ImageView persediaanDropImage, kasdanbankDropImage, penjualanDropImage, pendapatanDropImage;
    String area_id = "all", periode = "period";

    boolean doubleBackToExitPressedOnce = false;


    boolean tappedPersediaan = false, tappedKasdanbank = false, tappedPenjualan = false, tappedPendapatan = false;

    @Override
    protected void onStart() {
        super.onStart();
        submitDashboard.performClick();
    }

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
        setContentView(R.layout.activity_dashboard);

        //Toast.makeText(getApplicationContext(), "your cookies: "+ ClientNo.cookiesKey[0], Toast.LENGTH_LONG).show();
        //Scrollview hooks
        scrollDashboard = findViewById(R.id.scrollDashboard);

        //Hooks
        contentDashboard = findViewById(R.id.contentDashboard);

        //Progressbar hooks
        progressDashboardBar = findViewById(R.id.progressDashboardBar);

        //Dropdown Hooks
        persediaanDropdown = findViewById(R.id.persediaanDropdown);
        persediaanCard = findViewById(R.id.persediaanCard);
        kasdanbankDropdown = findViewById(R.id.kasdanbankDropdown);
        kasdanbankCard = findViewById(R.id.kasdanbankCard);
        penjualanDropdown = findViewById(R.id.penjualanDropdown);
        penjualanCard = findViewById(R.id.penjualanCard);
        pendapatanDropdown = findViewById(R.id.pendapatanDropdown);
        pendapatanCard = findViewById(R.id.pendapatanCard);

        //Value textview hooks
        persediaanValue = findViewById(R.id.persediaanValue);
        kasdanbankValue = findViewById(R.id.kasdanbankValue);
        penjualanValue = findViewById(R.id.penjualanValue);
        pendapatanValue = findViewById(R.id.pendapatanValue);

        //Logo dropdown
        persediaanDropImage = findViewById(R.id.persediaanDropImage);
        kasdanbankDropImage = findViewById(R.id.kasdanbankDropImage);
        penjualanDropImage = findViewById(R.id.penjualankDropImage);
        pendapatanDropImage = findViewById(R.id.pendapatanDropImage);


        persediaanDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedPersediaan == false){
                    setCardVisible(persediaanCard, donutChartPersediaan);
                    tappedPersediaan = true;
                    animateDropDownChart(90,0,persediaanDropImage,-90);
                }
                else if (tappedPersediaan == true){
                    persediaanCard.setVisibility(View.GONE);
                    tappedPersediaan = false;
                    animateDropDownChart(-90,0,persediaanDropImage, 90);
                }
            }
        });

        kasdanbankDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (tappedKasdanbank == false){
                    setCardVisible(kasdanbankCard, donutChartKasdanBank);
                    tappedKasdanbank = true;
                    animateDropDownChart(90,0,kasdanbankDropImage,-90);
                }
                else if (tappedKasdanbank == true){
                    kasdanbankCard.setVisibility(View.GONE);
                    tappedKasdanbank = false;
                    animateDropDownChart(-90,0,kasdanbankDropImage, 90);
                }
            }
        });

        penjualanDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedPenjualan == false){
                    setCardVisible(penjualanCard, donutChartPenjualan);
                    tappedPenjualan = true;
                    animateDropDownChart(90,0,penjualanDropImage,-90);
                }
                else if (tappedPenjualan == true){
                    penjualanCard.setVisibility(View.GONE);
                    tappedPenjualan = false;
                    animateDropDownChart(-90,0,penjualanDropImage, 90);
                }
            }
        });

        pendapatanDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedPendapatan == false){
                    setCardVisible(pendapatanCard, donutChartPendapatan);
                    tappedPendapatan = true;
                    animateDropDownChart(90,0,pendapatanDropImage,-90);
                }
                else if (tappedPendapatan == true){
                    pendapatanCard.setVisibility(View.GONE);
                    tappedPendapatan = false;
                    animateDropDownChart(-90,0,pendapatanDropImage, 90);
                }
            }
        });

        //Donut chart in cardview
        donutChartPersediaan = findViewById(R.id.DonutChart1);
        donutChartKasdanBank = findViewById(R.id.DonutChart2);
        donutChartPenjualan = findViewById(R.id.DonutChart3);
        donutChartPendapatan = findViewById(R.id.DonutChart4);

        //Toolbar dashboard
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        first_date = findViewById(R.id.first_date);
        last_date = findViewById(R.id.last_date);
        requestQueue = Volley.newRequestQueue(this);
        username = findViewById(R.id.NamaUser);
        //Spinner dashboard
        spinnerArea = findViewById(R.id.spinnerArea);
        spinnerPeriode = findViewById(R.id.spinnerPeriode);
        spinnerTampilan = findViewById(R.id.spinnerTampilan);
        setSpinner();
        setDate();
        first_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date();
            }
        });
        last_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date2();
            }
        });
        datelistener1();
        datelistener2();
        username.setText(ClientLogin.getUserName());
        //spinner
        spinnerArea.setOnItemSelectedListener(this);
        spinnerTampilan.setOnItemSelectedListener(this);
        spinnerPeriode.setOnItemSelectedListener(this);

        //submit btn
        submitDashboard = findViewById(R.id.submitDashboard);
        submitDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDefaultResult();
                getKasbank();
                getPersediaan();
                getPenjualan();
                getPendapatanBiaya();
            }
        });
    }

    //Setting the date
    private void datelistener2() {
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "-" + month + "-" + year;
                last_date.setText(date);
            }
        };
    }

    //Setting the date
    private void datelistener1() {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "-" + month + "-" + year;
                first_date.setText(date);
            }
        };
    }

    //Setting up the spinner for drop down view
    private void setSpinner() {
        list_tampilan.add("Standar");
        list_tampilan.add("Dalam Ribu");
        list_tampilan.add("Dalam Juta");
        list_periode.add("Periode");
        list_periode.add("Sampai Hari ini");
        list_periode.add("Sampai Bulan ini");
        list_periode.add("Sampai Tahun ini");
        list_area = ClientLogin.getListArea();
        //spinner list area
        areaAdapter = new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_item, list_area);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(areaAdapter);
        spinnerArea.setSelection(0);
        //spinner tampilan
        tampilanAdapter = new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_item, list_tampilan);
        tampilanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTampilan.setAdapter(tampilanAdapter);
        spinnerTampilan.setSelection(0);
        //spinner periode
        periodAdapter = new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_item, list_periode);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriode.setAdapter(periodAdapter);
        spinnerPeriode.setSelection(0);
    }

    //Setting the date
    private void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date1 = dateFormat.format(yesterday());
        String date2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        first_date.setText(date1);
        last_date.setText(date2);
    }

    //Setting up the option menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //Getting the default result for the diagrams
    public void GetDefaultResult(){
        //Controlling view
        progressDashboardBar.setVisibility(View.VISIBLE);
        contentDashboard.setVisibility(View.GONE);
        submitDashboard.setEnabled(false);

        //Clear arraylist
        list_header.clear();

        String url ="https://xfocus.id/dashboard/getHeader?area="+area_id+"&firstdate="+first_date.getText()+"&isPusat="+ClientLogin.getIsAreaPusat()+"&latedate="+last_date.getText()+"&showby="+periode;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            String label = jsonObject.optString("label");
                            String persen = jsonObject.optString("persen");
                            String urut = jsonObject.optString("urut");
                            String value = jsonObject.optString("value");

                            JSONArray listHeader = response.getJSONArray(1);
                            for(int i=0; i<listHeader.length();i++){
                                String header = listHeader.getString(i);
                                list_header.add(header);
                            }
                            Log.e("getHeader: ", "label: " + label +"persen: "+ persen+"urut: "+urut+"value: "+value
                            +"header: "+  list_header);
                            header = new Header(label,persen,urut,value,list_header);

                            if (!Header.getLabel().isEmpty()){
                                //Value update
                                if (spinnerTampilan.getSelectedItem().equals("Dalam Ribu")){
                                    formatString(persediaanValue, Header.getListHeader().get(4), 1000);
                                    formatString(kasdanbankValue, Header.getValue(),1000);
                                    formatString(penjualanValue, Penjualan.getListPenjualan().get(12),1000);
                                    formatString(pendapatanValue, Header.getListHeader().get(8),1000);
                                }
                                else if (spinnerTampilan.getSelectedItem().equals("Dalam Juta")){
                                    formatString(persediaanValue, Header.getListHeader().get(4), 1000000);
                                    formatString(kasdanbankValue, Header.getValue(),1000000);
                                    formatString(penjualanValue, Header.getListHeader().get(12),1000000);
                                    formatString(pendapatanValue, Header.getListHeader().get(8),1000000);
                                }
                                else{
                                    formatString(persediaanValue, Header.getListHeader().get(4), 1);
                                    formatString(kasdanbankValue, Header.getValue(),1);
                                    formatString(penjualanValue, Header.getListHeader().get(12),1);
                                    formatString(pendapatanValue, Header.getListHeader().get(8),1);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie","xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }
    public void getKasbank(){
        list_kasbank.clear();
        String url ="https://xfocus.id/dashboard/getkasbank?area="+area_id+"&firstdate="+first_date.getText()+"&isPusat="+ClientLogin.getIsAreaPusat()+"&latedate="+last_date.getText()+"&showby="+periode;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() != 0){
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            String label = jsonObject.optString("label");
                            String value = jsonObject.optString("value");

                            JSONArray listkasbank = response.getJSONArray(1);
                            for(int i=0; i<listkasbank.length();i++){
                                String kasdanbank = listkasbank.getString(i);
                                list_kasbank.add(kasdanbank);
                            }
                            Toast.makeText(getApplicationContext(), "success: "+label , Toast.LENGTH_LONG).show();
                            Log.e("getKasbank: ", " label: " + label +" value: "+ value + "listkasbank: "+  list_kasbank);
                            kasbank = new Kasbank(label,value,list_kasbank);

                            if (!Kasbank.getLabel().isEmpty()){
                                //Chart setup 2
                                ArrayList<PieEntry> kasB = new ArrayList<>();
                                kasB.add(new PieEntry(Float.parseFloat(Kasbank.getValue()), Kasbank.getLabel()));
                                for(int i = 1; i < Kasbank.getListKasbank().size(); i+=2){
                                    kasB.add(new PieEntry(Math.abs(Float.parseFloat(Kasbank.getListKasbank().get(i+1))), Kasbank.getListKasbank().get(i)));
                                }

                                setDonutCharts(kasB, diagramColors, donutChartKasdanBank, "KAS DAN BANK");

                                //Controlling view
                                submitDashboard.setEnabled(true);
                                progressDashboardBar.setVisibility(View.GONE);
                                contentDashboard.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        }
                        else{
                            //Controlling view
                            submitDashboard.setEnabled(true);
                            progressDashboardBar.setVisibility(View.GONE);
                            contentDashboard.setVisibility(View.VISIBLE);
                            donutChartKasdanBank.clear();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Kasbank failed", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie","xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    public void getPersediaan(){
        list_persediaan.clear();
        String url ="https://xfocus.id/dashboard/getpersediaan?area="+area_id+"&firstdate="+first_date.getText()+"&isPusat="+ClientLogin.getIsAreaPusat()+"&latedate="+last_date.getText()+"&showby="+periode;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() != 0){
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                String label = jsonObject.optString("label");
                                String value = jsonObject.optString("value");

                                JSONArray listpersediaan = response.getJSONArray(1);
                                for(int i=0; i<listpersediaan.length();i++){
                                    String persediaan = listpersediaan.getString(i);
                                    list_persediaan.add(persediaan);
                                }
                                Log.e("getPersediaan: ", " label: " + label +" value: "+ value + "listpersediaan: "+  list_persediaan);
                                persediaan = new Persediaan(label,value,list_persediaan);

                                if (!Persediaan.getLabel().isEmpty()){
                                    //Chart setup 1
                                    ArrayList<PieEntry> perse = new ArrayList<>();
                                    perse.add(new PieEntry(Float.parseFloat(Persediaan.getValue()), Persediaan.getLabel()));
                                    for(int i=1; i < Persediaan.getListPersediaan().size(); i+=2){
                                        perse.add(new PieEntry(Float.parseFloat(Persediaan.getListPersediaan().get(i+1)), Persediaan.getListPersediaan().get(i)));
                                    }

                                    setDonutCharts(perse, ColorTemplate.MATERIAL_COLORS, donutChartPersediaan, "PERSEDIAAN");

                                    //Controlling view
                                    submitDashboard.setEnabled(true);
                                    progressDashboardBar.setVisibility(View.GONE);
                                    contentDashboard.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            //Controlling view
                            submitDashboard.setEnabled(true);
                            progressDashboardBar.setVisibility(View.GONE);
                            contentDashboard.setVisibility(View.VISIBLE);
                            donutChartPersediaan.clear();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Persediaan failed", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie","xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    public void getPenjualan(){
        list_penjualan.clear();
        String url ="https://xfocus.id/dashboard/getpenjualan?area="+area_id+"&firstdate="+first_date.getText()+"&isPusat="+ClientLogin.getIsAreaPusat()+"&latedate="+last_date.getText()+"&showby="+periode;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() != 0) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                String label = jsonObject.optString("label");
                                String urut = jsonObject.optString("urut");
                                String value = jsonObject.optString("value");

                                JSONArray listpenjualan = response.getJSONArray(1);
                                for (int i = 0; i < listpenjualan.length(); i++) {
                                    String penjualan = listpenjualan.getString(i);
                                    list_penjualan.add(penjualan);
                                }
                                Log.e("getPenjualan: ", " label: " + label + " urut: " + urut + " value: " + value + "listpenjualan: " + list_penjualan);
                                penjualan = new Penjualan(label, urut, value, list_penjualan);

                                if (!Penjualan.getLabel().isEmpty()){
                                    //Chart setup 2
                                    ArrayList<PieEntry> penj = new ArrayList<>();
                                    penj.add(new PieEntry(Float.parseFloat(Penjualan.getValue()), Penjualan.getLabel()));
                                    totalPenjualan = Float.parseFloat(Penjualan.getValue());
                                    for(int i = 1; i < Penjualan.getListPenjualan().size(); i+=3){
                                        totalPenjualan += Float.parseFloat(Penjualan.getListPenjualan().get(i+2));
                                        penj.add(new PieEntry(Float.parseFloat(Penjualan.getListPenjualan().get(i+2)), Penjualan.getListPenjualan().get(i)));
                                    }

                                    setDonutCharts(penj, diagramColors, donutChartPenjualan, "PENJUALAN");

                                    if (spinnerTampilan.getSelectedItem().equals("Dalam Ribu")){
                                        formatString(penjualanValue, totalPenjualan.toString(),1000);
                                    }
                                    else if (spinnerTampilan.getSelectedItem().equals("Dalam Juta")){
                                        formatString(penjualanValue, totalPenjualan.toString(),1000000);
                                    }
                                    else{
                                        formatString(penjualanValue, totalPenjualan.toString(),1);
                                    }

                                    //Controlling view
                                    submitDashboard.setEnabled(true);
                                    progressDashboardBar.setVisibility(View.GONE);
                                    contentDashboard.setVisibility(View.VISIBLE);

                                    if (totalPenjualan == 0){
                                        donutChartPenjualan.clear();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            //Controlling view
                            submitDashboard.setEnabled(true);
                            progressDashboardBar.setVisibility(View.GONE);
                            contentDashboard.setVisibility(View.VISIBLE);
                            donutChartPenjualan.clear();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Penjualan failed", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie","xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    //Get pendapatan dan biaya
    public void getPendapatanBiaya(){
        list_pendapatan.clear();
        String url ="https://xfocus.id/dashboard/getpendapatanbiaya?area="+area_id+"&firstdate="+first_date.getText()+"&isPusat="+ClientLogin.getIsAreaPusat()+"&latedate="+last_date.getText()+"&showby="+periode;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() != 0) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                String label = jsonObject.optString("label");
                                String tipe = jsonObject.optString("tipe");
                                String value = jsonObject.optString("value");

                                JSONArray listpendapatanbiaya = response.getJSONArray(1);
                                for (int i = 0; i < listpendapatanbiaya.length(); i++) {
                                    String pendapatan = listpendapatanbiaya.getString(i);
                                    list_pendapatan.add(pendapatan);
                                }
                                Log.e("getPendapatanBiaya: ", " label: " + label + " tipe: " + tipe + " value: " + value + "listpendapatan: " + list_pendapatan);
                                pendapatanBiaya = new PendapatanBiaya(label, tipe, value, list_pendapatan);

                                if (!PendapatanBiaya.getLabel().isEmpty()){
                                    //Chart setup 2
                                    ArrayList<PieEntry> pend = new ArrayList<>();
                                    pend.add(new PieEntry(Math.abs(Float.parseFloat(PendapatanBiaya.getValue())), PendapatanBiaya.getLabel()));
                                    for(int i = 1; i < PendapatanBiaya.getListPendapatanBiaya().size(); i+=3){
                                        pend.add(new PieEntry(Math.abs(Float.parseFloat(PendapatanBiaya.getListPendapatanBiaya().get(i+2))), PendapatanBiaya.getListPendapatanBiaya().get(i)));
                                    }

                                    setDonutCharts(pend, diagramColors, donutChartPendapatan, "PENDAPATAN");

                                    //Controlling view
                                    submitDashboard.setEnabled(true);
                                    progressDashboardBar.setVisibility(View.GONE);
                                    contentDashboard.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            //Controlling view
                            submitDashboard.setEnabled(true);
                            progressDashboardBar.setVisibility(View.GONE);
                            contentDashboard.setVisibility(View.VISIBLE);
                            donutChartPendapatan.clear();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Penjualan failed", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie","xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    //Getting yesterday's date
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return cal.getTime();
    }

    //Setting up the datepick view
    private void Date() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                Dashboard.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void Date2() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                Dashboard.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener2,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    //Set the cardview to visible
    private void setCardVisible(final CardView cardDashboard, final PieChart donutChart){
        cardDashboard.setVisibility(View.VISIBLE);
        scrollDashboard.postDelayed(new Runnable() {
            public void run() {
                scrollDashboard.smoothScrollTo(0, (int) (cardDashboard.getY() + cardDashboard.getHeight() / 2));
                donutChart.animateXY(1000,1000);
            }
        }, 100);
    }

    //Set up the diagram for viewing all data
    private void setDonutCharts(ArrayList<PieEntry> pieEntries, int[] colorTemplate, PieChart pieChart, String chartName){
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        pieDataSet.setColors(colorTemplate);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        //pieData.setDrawValues(true);

        pieChart.setData(pieData);
        //Setting the diagram legend
        pieChart.getLegend().setFormSize(15f);
        pieChart.getLegend().setTextSize(15f);
        pieChart.getLegend().setXEntrySpace(20f);
        pieChart.getLegend().setWordWrapEnabled(true);
        //Using percentage
        pieChart.setUsePercentValues(true);
        //Setting the minimum angle
        pieChart.setMinAngleForSlices(15f);
        //Disable all info
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);

        pieChart.setCenterText(chartName);
    }

    //Set up the animation for drop down view of Charts
    private void animateDropDownChart(int angleA, int angleB, ImageView imageView, int rotationValue){
        RotateAnimation rotate = new RotateAnimation(angleA, angleB, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(rotate);
        imageView.setRotation(imageView.getRotation() + rotationValue);
    }

    //Formatting the currency of the number imported from database
    private void formatString(TextView textView, String value, int divided){
        textView.setText(String.format("%1$,.2f", Double.parseDouble(value) / divided));
    }

    //On spinner's item selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        switch (parent.getId()){
            case R.id.spinnerArea:
                String areaName = parent.getItemAtPosition(pos).toString();
                int index = list_area.indexOf(areaName);
                area_id = ClientLogin.getListAreaId().get(index);
                break;
            case R.id.spinnerPeriode:
                periode = parent.getItemAtPosition(pos).toString();
                switch (periode){
                    case "Periode":
                        periode = "period";
                        break;
                    case "Sampai Hari ini":
                        periode = "day";
                        break;
                    case "Sampai Bulan ini":
                        periode = "month";
                        break;
                    case "Sampai Tahun ini":
                        periode = "year";
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}



