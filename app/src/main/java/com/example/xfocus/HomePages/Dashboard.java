package com.example.xfocus.HomePages;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xfocus.ClientLogin;
import com.example.xfocus.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    List<String[]> ArrayDefaultFormat = new ArrayList<String[]>();

    LinearLayout persediaanDropdown, kasdanbankDropdown;
    CardView persediaanCard, kasdanbankCard;
    TextView first_date, last_date, username;
    Button submitDashboard;
    Spinner spinnerArea, spinnerTampilan, spinnerPeriode;
    ArrayList<String> list_area = new ArrayList<>();
    ArrayAdapter<String> areaAdapter,tampilanAdapter,periodAdapter;
    RequestQueue requestQueue;
    ScrollView scrollDashboard;
    PieChart donutChartPersediaan, donutChartKasdanBank;
    ImageView persediaanDropImage, kasdanbankDropImage;

    boolean tappedPersediaan = false;
    boolean tappedKasdanbank = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Scrollview hooks
        scrollDashboard = findViewById(R.id.scrollDashboard);

        //Dropdown Hooks
        persediaanDropdown = findViewById(R.id.persediaanDropdown);
        persediaanCard = findViewById(R.id.persediaanCard);
        kasdanbankDropdown = findViewById(R.id.kasdanbankDropdown);
        kasdanbankCard = findViewById(R.id.kasdanbankCard);

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
        persediaan.add(new PieEntry(500, "Barang Jadi"));
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
                Date();
            }
        });
        datelistener1();
        datelistener2();
        username.setText(ClientLogin.getUserName());
        submitDashboard = findViewById(R.id.submitDashboard);
        submitDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDefaultResult();
            }
        });
    }

    //Setting the date
    private void datelistener2() {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
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
        /*list_area.add("All Cabang");
        list_area.add("Cabang 1");
        list_area.add("Cabang 2");
        list_area.add("Cabang 3");
        list_tampilan.add("Standar");
        list_tampilan.add("Dalam Ribu");
        list_tampilan.add("Dalam Juta");
        list_periode.add("Periode");
        list_periode.add("Sampai Hari ini");
        list_periode.add("Sampai Bulan ini");
        list_periode.add("Sampai Tahun ini");
        */
        list_area = ClientLogin.getListArea();
        areaAdapter = new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_item, list_area);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(areaAdapter);
        spinnerArea.setSelection(0);
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
        String url = "https://xfocus.id/dashboard/getHeader_app";
        final List<String> jsonResponses = new ArrayList<>();

        JSONObject getData = new JSONObject();
        try {
            getData.put("area", ClientLogin.getAreaId());
            getData.put("firstdate", "2020-12-01");
            getData.put("isPusat", ClientLogin.getIsAreaPusat());
            getData.put("latedate", "2020-12-01");
            getData.put("showby","period");
            getData.put("app","1");
            getData.put("clid", ClientLogin.getClientId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, getData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    //Getting yesterday's date
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return cal.getTime();
    }

    //Setting up the date view
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

    //Set the cardview to visible
    private void setCardVisible(final CardView cardDashboard, final PieChart donutChart){
        cardDashboard.setVisibility(View.VISIBLE);
        scrollDashboard.postDelayed(new Runnable() {
            public void run() {
                scrollDashboard.smoothScrollTo(0, (int)cardDashboard.getY());
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
        pieChart.setUsePercentValues(true);
        pieChart.setMinAngleForSlices(15f);
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
}



