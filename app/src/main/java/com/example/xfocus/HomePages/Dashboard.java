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
import com.example.xfocus.R;
import com.example.xfocus.StartPages.ClientNo;
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

    TextView persediaanValue, kasdanbankValue;

    List<String[]> ArrayDefaultFormat = new ArrayList<String[]>();

    LinearLayout progressDashboardBar, persediaanDropdown, kasdanbankDropdown, contentDashboard;
    CardView persediaanCard, kasdanbankCard;
    TextView first_date, last_date, username;
    Button submitDashboard;
    Spinner spinnerArea, spinnerTampilan, spinnerPeriode;
    ArrayList<String> list_area = new ArrayList<>();
    ArrayList<String> list_tampilan = new ArrayList<>();
    ArrayList<String> list_periode = new ArrayList<>();
    ArrayList<String> list_header = new ArrayList<>();

    Header header;

    ArrayAdapter<String> areaAdapter,tampilanAdapter,periodAdapter;
    RequestQueue requestQueue;
    ScrollView scrollDashboard;
    PieChart donutChartPersediaan, donutChartKasdanBank;
    ImageView persediaanDropImage, kasdanbankDropImage;
    String area_id, periode = "period";
    String label;

    boolean doubleBackToExitPressedOnce = false;


    boolean tappedPersediaan = false;
    boolean tappedKasdanbank = false;

    @Override
    protected void onResume() {
        super.onResume();
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

        //Value textview hooks
        persediaanValue = findViewById(R.id.persediaanValue);
        kasdanbankValue = findViewById(R.id.kasdanbankValue);

        //Logo
        persediaanDropImage = findViewById(R.id.persediaanDropImage);
        kasdanbankDropImage = findViewById(R.id.kasdanbankDropImage);


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

        //Donut chart in cardview
        donutChartPersediaan = findViewById(R.id.DonutChart1);
        donutChartKasdanBank = findViewById(R.id.DonutChart2);

        //Chart setup 1
        ArrayList<PieEntry> persediaan = new ArrayList<>();
        persediaan.add(new PieEntry(900, "Bahan Jadi"));
        persediaan.add(new PieEntry(4500, "Bahan Baku"));

        setDonutCharts(persediaan, ColorTemplate.MATERIAL_COLORS, donutChartPersediaan, "PERSEDIAAN");

        //-----------------

        //Chart setup 2
        ArrayList<PieEntry> kasDanBank = new ArrayList<>();
        kasDanBank.add(new PieEntry(100000, "Bank 2"));
        kasDanBank.add(new PieEntry(50000, "Coba Bank"));
        kasDanBank.add(new PieEntry(39300, "Kas Sales"));
        kasDanBank.add(new PieEntry(6000, "Bank 1"));
        kasDanBank.add(new PieEntry(100, "Kas Kecil"));
        kasDanBank.add(new PieEntry(50, "Bank 3"));

        setDonutCharts(kasDanBank, ColorTemplate.MATERIAL_COLORS, donutChartKasdanBank, "KAS DAN BANK");

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

                //Log.e("test2: ", ClientNo.cookiesKey[0]);
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
                            Toast.makeText(getApplicationContext(), "success: "+label , Toast.LENGTH_LONG).show();
                            Log.e("getHeader: ", "label: " + label +"persen: "+ persen+"urut: "+urut+"value: "+value
                            +"header: "+  list_header);
                            header = new Header(label,persen,urut,value,list_header);

                            if (!Header.getLabel().isEmpty()){
                                //Controlling view
                                submitDashboard.setEnabled(true);
                                progressDashboardBar.setVisibility(View.GONE);
                                contentDashboard.setVisibility(View.VISIBLE);

                                //Chart setup 1
                                ArrayList<PieEntry> persediaan = new ArrayList<>();
                                persediaan.add(new PieEntry(1200, "Bahan Jadi"));
                                persediaan.add(new PieEntry(3800, "Bahan Baku"));

                                setDonutCharts(persediaan, ColorTemplate.MATERIAL_COLORS, donutChartPersediaan, "PERSEDIAAN");

                                //Value update
                                persediaanValue.setText(String.format("%1$,.2f", Double.parseDouble(Header.getListHeader().get(4))));
                                kasdanbankValue.setText(String.format("%1$,.2f", Double.parseDouble(Header.getValue())));
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
                scrollDashboard.smoothScrollTo(0, (int)cardDashboard.getBottom());
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
    private void  animateDropDownChart(int angleA, int angleB, ImageView imageView, int rotationValue){
        RotateAnimation rotate = new RotateAnimation(angleA, angleB, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(rotate);
        imageView.setRotation(imageView.getRotation() + rotationValue);
    }
    //on spinner's item selected
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



