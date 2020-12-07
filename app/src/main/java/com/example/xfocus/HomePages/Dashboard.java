package com.example.xfocus.HomePages;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

        persediaanDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedPersediaan == false){
                    persediaanCard.setVisibility(View.VISIBLE);
                    scrollDashboard.postDelayed(new Runnable() {
                        public void run() {
                            scrollDashboard.scrollTo(0, (int)persediaanCard.getY());
                        }
                    }, 100);
                    tappedPersediaan = true;
                }
                else if (tappedPersediaan == true){
                    persediaanCard.setVisibility(View.GONE);
                    tappedPersediaan = false;
                }
            }
        });

        kasdanbankDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedKasdanbank == false){
                    kasdanbankCard.setVisibility(View.VISIBLE);
                    scrollDashboard.postDelayed(new Runnable() {
                        public void run() {
                            scrollDashboard.scrollTo(0, (int)kasdanbankCard.getY());
                        }
                    }, 100);
                    tappedKasdanbank = true;
                }
                else if (tappedKasdanbank == true){
                    kasdanbankCard.setVisibility(View.GONE);
                    tappedKasdanbank = false;
                }
            }
        });

        //Donut chart in cardview
        PieChart donutChart1 = findViewById(R.id.DonutChart1);
        PieChart donutChart2 = findViewById(R.id.DonutChart2);

        //Chart setup 1
        ArrayList<PieEntry> persediaan = new ArrayList<>();
        persediaan.add(new PieEntry(500, "Barang Jadi"));
        persediaan.add(new PieEntry(4500, "Bahan Baku"));

        PieDataSet pieDataSet = new PieDataSet(persediaan, "");

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        donutChart1.setData(pieData);
        donutChart1.setMinAngleForSlices(15f);
        donutChart1.setDrawEntryLabels(false);
        donutChart1.getDescription().setEnabled(false);
        donutChart1.setCenterText("PERSEDIAAN");
        donutChart1.animateXY(1000,1000);

        //-----------------

        //Chart setup 2
        ArrayList<PieEntry> kasDanBank = new ArrayList<>();
        kasDanBank.add(new PieEntry(100000, "Bank 2"));
        kasDanBank.add(new PieEntry(50000, "Coba Bank"));
        kasDanBank.add(new PieEntry(39300, "Kas Sales"));
        kasDanBank.add(new PieEntry(6000, "Bank 1"));
        kasDanBank.add(new PieEntry(100, "Kas Kecil"));
        kasDanBank.add(new PieEntry(50, "Bank 3"));

        pieDataSet = new PieDataSet(kasDanBank, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueTextSize(16f);

        pieData = new PieData(pieDataSet);

        donutChart2.setData(pieData);
        donutChart2.setMinAngleForSlices(15f);
        donutChart2.setDrawEntryLabels(false);
        donutChart2.getDescription().setEnabled(false);
        donutChart2.setCenterText("KAS DAN BANK");
        donutChart2.animateXY(1000,1000);

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

    private void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date1 = dateFormat.format(yesterday());
        String date2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        first_date.setText(date1);
        last_date.setText(date2);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

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

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return cal.getTime();
    }

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

}



