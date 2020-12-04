package com.example.xfocus.HomePages;

import android.graphics.Color;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    TextView first_date, last_date;
    Spinner spinnerArea, spinnerTampilan, spinnerPeriode;
    ArrayList<String> list_area = new ArrayList<>();
    ArrayList<String> list_tampilan = new ArrayList<>();
    ArrayList<String> list_periode = new ArrayList<>();
    ArrayAdapter<String> areaAdapter,tampilanAdapter,periodAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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
        //comboarea();
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
        list_area.add("All Cabang");
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
        areaAdapter = new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_item, list_area);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tampilanAdapter = new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_item, list_tampilan);
        tampilanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodAdapter = new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_item, list_periode);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(areaAdapter);
        spinnerArea.setSelection(0);
        spinnerTampilan.setAdapter(tampilanAdapter);
        spinnerTampilan.setSelection(0);
        spinnerPeriode.setAdapter(periodAdapter);
        spinnerPeriode.setSelection(0);
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

