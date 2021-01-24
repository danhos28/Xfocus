package com.example.xfocus.HomePages;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.ListView;
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
import com.example.xfocus.AxisDateFormatter;
import com.example.xfocus.ClientLogin;
import com.example.xfocus.Header;
import com.example.xfocus.HutangPiutang;
import com.example.xfocus.Kasbank;
import com.example.xfocus.LabaRugi;
import com.example.xfocus.ListHutangAdapter;
import com.example.xfocus.PendapatanBiaya;
import com.example.xfocus.Penjualan;
import com.example.xfocus.Persediaan;
import com.example.xfocus.R;
import com.example.xfocus.SessionManagerClass.SessionManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener2;
    public static String SliceValue, SliceLabel;
    public static int red, green, blue;
    TextView persediaanValue, kasdanbankValue, penjualanValue, pendapatanValue;
    List<String[]> ArrayDefaultFormat = new ArrayList<String[]>();
    ListHutangAdapter listHutangAdapter;
    Float totalPenjualan;
    Float totalPendapatan;
    ListView listhutang;
    LinearLayout progressDashboardBar, contentDashboard, persediaanDropdown, kasdanbankDropdown, penjualanDropdown, pendapatanDropdown, hutangpiutangDropdown, labarugiDropdown;
    CardView persediaanCard, kasdanbankCard, penjualanCard, pendapatanCard, hutangpiutangCard, labarugiCard;
    TextView first_date, last_date, username, areaname;
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
    ArrayList<String> list_hutangPiutang = new ArrayList<>();
    ArrayList<String> list_labarugi = new ArrayList<>();
    ArrayList<String> labelHutangPiutang = new ArrayList<>();
    ArrayList<String> valuePiutang = new ArrayList<>();
    ArrayList<String> valueHutang = new ArrayList<>();
    ArrayList<String> list_penj = new ArrayList<>();
    ArrayList<String> list_perse = new ArrayList<>();
    ArrayList<String> list_kasB = new ArrayList<>();
    ArrayList<String> list_pend = new ArrayList<>();
    int[] diagramColors = {Color.rgb(229, 57, 53), Color.rgb(255, 204, 128),
            Color.rgb(156, 39, 176), Color.rgb(234, 128, 252), Color.rgb(77, 208, 225),
            Color.rgb(217, 80, 138), Color.rgb(254, 149, 7), Color.rgb(254, 247, 120),
            Color.rgb(106, 167, 134), Color.rgb(53, 194, 209), Color.rgb(0, 96, 100),
            Color.rgb(100, 255, 218), Color.rgb(100, 255, 218), Color.rgb(253, 216, 53),
            Color.rgb(255, 171, 145)};

    Header header;
    Kasbank kasbank;
    Persediaan persediaan;
    Penjualan penjualan;
    HutangPiutang hutangPiutang;
    PendapatanBiaya pendapatanBiaya;
    LabaRugi labaRugi;
    int labaRugiMonth = 1;

    SessionManager sessionManager;

    ArrayAdapter<String> areaAdapter, tampilanAdapter, periodAdapter;
    RequestQueue requestQueue;
    ScrollView scrollDashboard;
    PieChart donutChartPersediaan, donutChartKasdanBank, donutChartPenjualan, donutChartPendapatan;
    LineChart lineChartLabaRugi;
    ImageView persediaanDropImage, kasdanbankDropImage, penjualanDropImage, pendapatanDropImage, hutangpiutangDropImage, labarugiDropImage;
    String area_id = "all", periode = "period";

    boolean doubleBackToExitPressedOnce = false;


    boolean tappedPersediaan = false, tappedKasdanbank = false, tappedPenjualan = false, tappedPendapatan = false, tappedHutang = false, tappedLaba = false;

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
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);


        Log.e("List Area", ClientLogin.getListArea().toString());


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
        hutangpiutangDropdown = findViewById(R.id.hutangpiutangDropdown);
        hutangpiutangCard = findViewById(R.id.hutangpiutangCard);
        labarugiDropdown = findViewById(R.id.labaDropdown);
        labarugiCard = findViewById(R.id.LabaRugiCard);

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
        hutangpiutangDropImage = findViewById(R.id.hutangpiutangkDropImage);
        labarugiDropImage = findViewById(R.id.labaDropImage);


        //ListView hutang
        listhutang = findViewById(R.id.listHutang);

        persediaanDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedPersediaan == false) {
                    setCardVisible(persediaanCard, donutChartPersediaan);
                    tappedPersediaan = true;
                    animateDropDownChart(90, 0, persediaanDropImage, -90);
                } else if (tappedPersediaan == true) {
                    persediaanCard.setVisibility(View.GONE);
                    tappedPersediaan = false;
                    animateDropDownChart(-90, 0, persediaanDropImage, 90);
                }
            }
        });

        kasdanbankDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (tappedKasdanbank == false) {
                    setCardVisible(kasdanbankCard, donutChartKasdanBank);
                    tappedKasdanbank = true;
                    animateDropDownChart(90, 0, kasdanbankDropImage, -90);
                } else if (tappedKasdanbank == true) {
                    kasdanbankCard.setVisibility(View.GONE);
                    tappedKasdanbank = false;
                    animateDropDownChart(-90, 0, kasdanbankDropImage, 90);
                }
            }
        });

        penjualanDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedPenjualan == false) {
                    setCardVisible(penjualanCard, donutChartPenjualan);
                    tappedPenjualan = true;
                    animateDropDownChart(90, 0, penjualanDropImage, -90);
                } else if (tappedPenjualan == true) {
                    penjualanCard.setVisibility(View.GONE);
                    tappedPenjualan = false;
                    animateDropDownChart(-90, 0, penjualanDropImage, 90);
                }
            }
        });

        pendapatanDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedPendapatan == false) {
                    setCardVisible(pendapatanCard, donutChartPendapatan);
                    tappedPendapatan = true;
                    animateDropDownChart(90, 0, pendapatanDropImage, -90);
                } else if (tappedPendapatan == true) {
                    pendapatanCard.setVisibility(View.GONE);
                    tappedPendapatan = false;
                    animateDropDownChart(-90, 0, pendapatanDropImage, 90);
                }
            }
        });

        hutangpiutangDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedHutang == false) {
                    setCardVisible(hutangpiutangCard, donutChartPersediaan);
                    tappedHutang = true;
                    animateDropDownChart(90, 0, hutangpiutangDropImage, -90);
                } else if (tappedHutang == true) {
                    hutangpiutangCard.setVisibility(View.GONE);
                    tappedHutang = false;
                    animateDropDownChart(-90, 0, hutangpiutangDropImage, 90);
                }
            }
        });

        labarugiDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tappedLaba == false) {
                    setCardVisible(labarugiCard, lineChartLabaRugi);
                    tappedLaba = true;
                    animateDropDownChart(90, 0, labarugiDropImage, -90);
                } else if (tappedLaba == true) {
                    labarugiCard.setVisibility(View.GONE);
                    tappedLaba = false;
                    animateDropDownChart(-90, 0, labarugiDropImage, 90);
                }
            }
        });

        //Donut chart in cardview
        donutChartPersediaan = findViewById(R.id.DonutChart1);
        donutChartKasdanBank = findViewById(R.id.DonutChart2);
        donutChartPenjualan = findViewById(R.id.DonutChart3);
        donutChartPendapatan = findViewById(R.id.DonutChart4);

        //Line chart in cardview
        lineChartLabaRugi = findViewById(R.id.LineChart1);

        //Toolbar dashboard
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        first_date = findViewById(R.id.first_date);
        last_date = findViewById(R.id.last_date);
        requestQueue = Volley.newRequestQueue(this);
        username = findViewById(R.id.NamaUser);
        areaname = findViewById(R.id.NamaArea);
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
        areaname.setText(ClientLogin.getAreaName());
        //spinner
        spinnerArea.setOnItemSelectedListener(this);
        spinnerTampilan.setOnItemSelectedListener(this);
        spinnerPeriode.setOnItemSelectedListener(this);


        //submit btn
        submitDashboard = findViewById(R.id.submitDashboard);
        submitDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list_pend.clear();
                list_perse.clear();
                list_penj.clear();
                list_kasB.clear();

                getPersediaan();
                getKasbank();
                getPenjualan();
                getPendapatanBiaya();
                getLabaRugi();
                getHutangPiutang();
                GetDefaultResult();
            }
        });

        donutChartPenjualan.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pe = (PieEntry) e;
                SliceValue = String.valueOf(pe.getValue());
                SliceLabel = pe.getLabel();
                int idx = list_penj.indexOf(pe.getLabel());
                red=Color.red(diagramColors[idx]);
                green=Color.green(diagramColors[idx]);
                blue=Color.blue(diagramColors[idx]);
                Log.e("slice label ", pe.getLabel());
                Log.e("index label ", String.valueOf(idx));
                Log.e("slice value ", String.valueOf(pe.getValue()));
                Log.e("slice color red ", String.valueOf(red));
                Log.e("slice color green ", String.valueOf(green));
                Log.e("slice color blue ", String.valueOf(blue));
                openPopup();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        donutChartKasdanBank.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pe = (PieEntry) e;
                SliceValue = String.valueOf(pe.getValue());
                SliceLabel = pe.getLabel();
                int idx = list_kasB.indexOf(pe.getLabel());
                red=Color.red(diagramColors[idx]);
                green=Color.green(diagramColors[idx]);
                blue=Color.blue(diagramColors[idx]);
                Log.e("slice label ", pe.getLabel());
                Log.e("index label ", String.valueOf(idx));
                Log.e("slice value ", String.valueOf(pe.getValue()));
                Log.e("slice color red ", String.valueOf(red));
                Log.e("slice color green ", String.valueOf(green));
                Log.e("slice color blue ", String.valueOf(blue));
                openPopup();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        donutChartPersediaan.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pe = (PieEntry) e;
                SliceValue = String.valueOf(pe.getValue());
                SliceLabel = pe.getLabel();
                int idx=list_perse.indexOf(pe.getLabel());
                red=Color.red(diagramColors[idx]);
                green=Color.green(diagramColors[idx]);
                blue=Color.blue(diagramColors[idx]);
                Log.e("slice label ", pe.getLabel());
                Log.e("index label ", String.valueOf(idx));
                Log.e("slice value ", String.valueOf(pe.getValue()));
                Log.e("slice color red ", String.valueOf(red));
                Log.e("slice color green ", String.valueOf(green));
                Log.e("slice color blue ", String.valueOf(blue));
                openPopup();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        donutChartPendapatan.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pe = (PieEntry) e;
                SliceValue = String.valueOf(pe.getValue());
                SliceLabel = pe.getLabel();
                int idx=list_pend.indexOf(pe.getLabel());
                red=Color.red(diagramColors[idx]);
                green=Color.green(diagramColors[idx]);
                blue=Color.blue(diagramColors[idx]);
                Log.e("slice label ", pe.getLabel());
                Log.e("index label ", String.valueOf(idx));
                Log.e("slice value ", String.valueOf(pe.getValue()));
                Log.e("slice color red ", String.valueOf(red));
                Log.e("slice color green ", String.valueOf(green));
                Log.e("slice color blue ", String.valueOf(blue));
                openPopup();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
    void openPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.slice_dialog, null);
        LinearLayout Colorslice = view.findViewById(R.id.sliceColor);
        TextView label=view.findViewById(R.id.labelslice);
        TextView value=view.findViewById(R.id.valueslice);
        Button ok=view.findViewById(R.id.buttonOk);
        Colorslice.setBackgroundColor(Color.rgb(red,green,blue));
        builder.setView(view);
        builder.create();
        label.setText(Dashboard.SliceLabel);

        if (Dashboard.SliceValue.equals("1.0")){
            value.setText("Value : " + 0);
        }
        else{
            value.setText("Value : " + NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(Dashboard.SliceValue)));
        }
        final AlertDialog ad = builder.show();
       ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                ad.dismiss();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Getting the default result for the diagrams
    public void GetDefaultResult() {

        //Controlling view
        progressDashboardBar.setVisibility(View.VISIBLE);
        contentDashboard.setVisibility(View.GONE);
        submitDashboard.setEnabled(false);

        //Clear arraylist
        list_header.clear();

        String url = "https://xfocus.id/dashboard/getHeader?area=" + area_id + "&firstdate=" + first_date.getText() + "&isPusat=" + ClientLogin.getIsAreaPusat() + "&latedate=" + last_date.getText() + "&showby=" + periode;
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
                            for (int i = 0; i < listHeader.length(); i++) {
                                String header = listHeader.getString(i);
                                list_header.add(header);
                            }
                            Log.e("getHeader: ", "label: " + label + "persen: " + persen + "urut: " + urut + "value: " + value
                                    + "header: " + list_header);
                            header = new Header(label, persen, urut, value, list_header);

                            if (!Header.getLabel().isEmpty()) {
                                //Value update
                                if (spinnerTampilan.getSelectedItem().equals("Dalam Ribu")) {
                                    formatString(persediaanValue, Header.getListHeader().get(4), 1000);
                                    formatString(kasdanbankValue, Header.getValue(), 1000);
                                    //formatString(penjualanValue, Penjualan.getListPenjualan().get(12), 1000);
                                    //formatString(pendapatanValue, Header.getListHeader().get(8), 1000);
                                } else if (spinnerTampilan.getSelectedItem().equals("Dalam Juta")) {
                                    formatString(persediaanValue, Header.getListHeader().get(4), 1000000);
                                    formatString(kasdanbankValue, Header.getValue(), 1000000);
                                    //formatString(penjualanValue, Header.getListHeader().get(12), 1000000);
                                    //formatString(pendapatanValue, Header.getListHeader().get(8), 1000000);
                                } else {
                                    formatString(persediaanValue, Header.getListHeader().get(4), 1);
                                    formatString(kasdanbankValue, Header.getValue(), 1);
                                    //formatString(penjualanValue, Header.getListHeader().get(12), 1);
                                    //formatString(pendapatanValue, Header.getListHeader().get(8), 1);
                                }

                                //Controlling view
                                submitDashboard.setEnabled(true);
                                progressDashboardBar.setVisibility(View.GONE);
                                contentDashboard.setVisibility(View.VISIBLE);
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
                headers.put("Cookie", "xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    public void getKasbank() {
        list_kasbank.clear();
        String url = "https://xfocus.id/dashboard/getkasbank?area=" + area_id + "&firstdate=" + first_date.getText() + "&isPusat=" + ClientLogin.getIsAreaPusat() + "&latedate=" + last_date.getText() + "&showby=" + periode;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() != 0) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                String label = jsonObject.optString("label");
                                String value = jsonObject.optString("value");

                                JSONArray listkasbank = response.getJSONArray(1);
                                for (int i = 0; i < listkasbank.length(); i++) {
                                    String kasdanbank = listkasbank.getString(i);
                                    list_kasbank.add(kasdanbank);
                                }
                                Log.e("getKasbank: ", " label: " + label + " value: " + value + "listkasbank: " + list_kasbank);
                                kasbank = new Kasbank(label, value, list_kasbank);

                                if (!Kasbank.getLabel().isEmpty()) {
                                    //Chart setup 2
                                    ArrayList<PieEntry> kasB = new ArrayList<>();
                                    if (Float.parseFloat(value)==0){
                                        kasB.add(new PieEntry(1, Kasbank.getLabel().toUpperCase()));
                                        list_kasB.add(Kasbank.getLabel().toUpperCase());
                                    }
                                    else{
                                        kasB.add(new PieEntry(Float.parseFloat(Kasbank.getValue()), Kasbank.getLabel().toUpperCase()));
                                        list_kasB.add(Kasbank.getLabel().toUpperCase());
                                    }
                                    for (int i = 1; i < Kasbank.getListKasbank().size(); i += 2) {
                                        if (Float.parseFloat(Kasbank.getListKasbank().get(i+1))==0){
                                            kasB.add(new PieEntry(1, Kasbank.getListKasbank().get(i).toUpperCase()));
                                            list_kasB.add(Kasbank.getListKasbank().get(i).toUpperCase());
                                        }
                                        else
                                            kasB.add(new PieEntry(Math.abs(Float.parseFloat(Kasbank.getListKasbank().get(i + 1))), Kasbank.getListKasbank().get(i).toUpperCase()));
                                            list_kasB.add(Kasbank.getListKasbank().get(i).toUpperCase());
                                    }

                                    setDonutCharts(kasB, diagramColors, donutChartKasdanBank, "KAS DAN BANK");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Controlling view
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
                headers.put("Cookie", "xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    public void getPersediaan() {
        list_persediaan.clear();
        String url = "https://xfocus.id/dashboard/getpersediaan?area=" + area_id + "&firstdate=" + first_date.getText() + "&isPusat=" + ClientLogin.getIsAreaPusat() + "&latedate=" + last_date.getText() + "&showby=" + periode;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() != 0) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                String label = jsonObject.optString("label");
                                String value = jsonObject.optString("value");

                                JSONArray listpersediaan = response.getJSONArray(1);
                                for (int i = 0; i < listpersediaan.length(); i++) {
                                    String persediaan = listpersediaan.getString(i);
                                    list_persediaan.add(persediaan);
                                }
                                Log.e("getPersediaan: ", " label: " + label + " value: " + value + "listpersediaan: " + list_persediaan);
                                persediaan = new Persediaan(label, value, list_persediaan);

                                if (!Persediaan.getLabel().isEmpty()) {
                                    //Chart setup 1
                                    ArrayList<PieEntry> perse = new ArrayList<>();
                                    if (Float.parseFloat(value)==0){
                                        perse.add(new PieEntry(1, Persediaan.getLabel().toUpperCase()));
                                        list_perse.add(Persediaan.getLabel().toUpperCase());
                                    }
                                    else{
                                        perse.add(new PieEntry(Float.parseFloat(Persediaan.getValue()), Persediaan.getLabel().toUpperCase()));
                                        list_perse.add(Persediaan.getLabel().toUpperCase());
                                    }
                                    for (int i = 1; i < Persediaan.getListPersediaan().size(); i += 2) {
                                        if(Float.parseFloat(Persediaan.getListPersediaan().get(i + 1)) == 0){
                                            perse.add(new PieEntry(1, Persediaan.getListPersediaan().get(i).toUpperCase()));
                                            list_perse.add(Persediaan.getListPersediaan().get(i).toUpperCase());
                                        }
                                        else
                                            perse.add(new PieEntry(Float.parseFloat(Persediaan.getListPersediaan().get(i + 1)), Persediaan.getListPersediaan().get(i).toUpperCase()));
                                            list_perse.add(Persediaan.getListPersediaan().get(i).toUpperCase());
                                    }

                                    setDonutCharts(perse, diagramColors, donutChartPersediaan, "PERSEDIAAN");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
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
                headers.put("Cookie", "xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    public void getPenjualan() {
        list_penjualan.clear();
        String url = "https://xfocus.id/dashboard/getpenjualan?area=" + area_id + "&firstdate=" + first_date.getText() + "&isPusat=" + ClientLogin.getIsAreaPusat() + "&latedate=" + last_date.getText() + "&showby=" + periode;
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

                                if (!Penjualan.getLabel().isEmpty()) {
                                    //Chart setup 2
                                    ArrayList<PieEntry> penj = new ArrayList<>();
                                    if(Float.parseFloat(value) == 0){
                                        penj.add(new PieEntry(1, Penjualan.getLabel()));
                                        list_penj.add(Penjualan.getLabel());
                                    }
                                    else
                                    {
                                        penj.add(new PieEntry(Float.parseFloat(Penjualan.getValue()), Penjualan.getLabel()));
                                        list_penj.add(Penjualan.getLabel());
                                    }
                                    totalPenjualan = Float.parseFloat(Penjualan.getValue());
                                    for (int i = 1; i < Penjualan.getListPenjualan().size(); i += 3) {
                                        if (Float.parseFloat(Penjualan.getListPenjualan().get(i+2))==0) {
                                            penj.add(new PieEntry(1, Penjualan.getListPenjualan().get(i)));
                                            list_penj.add(Penjualan.getListPenjualan().get(i));
                                        }
                                        else{
                                            totalPenjualan += Float.parseFloat(Penjualan.getListPenjualan().get(i + 2));
                                            penj.add(new PieEntry(Float.parseFloat(Penjualan.getListPenjualan().get(i + 2)), Penjualan.getListPenjualan().get(i)));
                                            list_penj.add(Penjualan.getListPenjualan().get(i));
                                        }
                                    }

                                    setDonutCharts(penj, diagramColors, donutChartPenjualan, "PENJUALAN");

                                    if (spinnerTampilan.getSelectedItem().equals("Dalam Ribu")) {
                                        formatString(penjualanValue, totalPenjualan.toString(), 1000);
                                    } else if (spinnerTampilan.getSelectedItem().equals("Dalam Juta")) {
                                        formatString(penjualanValue, totalPenjualan.toString(), 1000000);
                                    } else {
                                        formatString(penjualanValue, totalPenjualan.toString(), 1);
                                    }

                                    if (totalPenjualan == 0) {
                                        donutChartPenjualan.clear();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Controlling view
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
                headers.put("Cookie", "xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    //Get pendapatan dan biaya
    public void getPendapatanBiaya() {
        list_pendapatan.clear();
        String url = "https://xfocus.id/dashboard/getpendapatanbiaya?area=" + area_id + "&firstdate=" + first_date.getText() + "&isPusat=" + ClientLogin.getIsAreaPusat() + "&latedate=" + last_date.getText() + "&showby=" + periode;
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

                                if (!PendapatanBiaya.getLabel().isEmpty()) {
                                    //Chart setup 2
                                    ArrayList<PieEntry> pend = new ArrayList<>();
                                    if (Float.parseFloat(value)==0){
                                        pend.add(new PieEntry(1, PendapatanBiaya.getLabel()));
                                        list_pend.add(PendapatanBiaya.getLabel());
                                    }
                                    else {
                                        pend.add(new PieEntry(Math.abs(Float.parseFloat(PendapatanBiaya.getValue())), PendapatanBiaya.getLabel()));
                                        list_pend.add(PendapatanBiaya.getLabel());
                                    }
                                    totalPendapatan = Float.parseFloat(PendapatanBiaya.getValue());
                                    for (int i = 1; i < PendapatanBiaya.getListPendapatanBiaya().size(); i += 3) {
                                        if(Float.parseFloat(PendapatanBiaya.getListPendapatanBiaya().get(i+2))==0){
                                            pend.add(new PieEntry(1,PendapatanBiaya.getListPendapatanBiaya().get(i)));
                                            list_pend.add(PendapatanBiaya.getListPendapatanBiaya().get(i));
                                        }
                                        else{
                                            totalPendapatan += Float.parseFloat(PendapatanBiaya.getListPendapatanBiaya().get(i + 2));
                                            pend.add(new PieEntry(Math.abs(Float.parseFloat(PendapatanBiaya.getListPendapatanBiaya().get(i + 2))), PendapatanBiaya.getListPendapatanBiaya().get(i)));
                                            list_pend.add(PendapatanBiaya.getListPendapatanBiaya().get(i));
                                        }
                                    }

                                    setDonutCharts(pend, diagramColors, donutChartPendapatan, "PENDAPATAN");

                                    if (spinnerTampilan.getSelectedItem().equals("Dalam Ribu")) {
                                        formatString(pendapatanValue, totalPendapatan.toString(), 1000);
                                    } else if (spinnerTampilan.getSelectedItem().equals("Dalam Juta")) {
                                        formatString(pendapatanValue, totalPendapatan.toString(), 1000000);
                                    } else {
                                        formatString(pendapatanValue, totalPendapatan.toString(), 1);
                                    }

                                    if (totalPendapatan == 0) {
                                        donutChartPersediaan.clear();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Controlling view
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
                headers.put("Cookie", "xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    //Get hutang dan piutang
    public void getHutangPiutang() {
        labelHutangPiutang.clear();
        valueHutang.clear();
        valuePiutang.clear();
        list_hutangPiutang.clear();
        String url = "https://xfocus.id/dashboard/gethutangpiutang?area=" + area_id + "&firstdate=" + first_date.getText() + "&isPusat=" + ClientLogin.getIsAreaPusat() + "&latedate=" + last_date.getText() + "&showby=" + periode;
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
                                String urut = jsonObject.optString("urut");
                                String value = jsonObject.optString("value");

                                JSONArray listhutangpiutang = response.getJSONArray(1);
                                for (int i = 0; i < listhutangpiutang.length(); i++) {
                                    String hutangpiutang = listhutangpiutang.getString(i);
                                    list_hutangPiutang.add(hutangpiutang);
                                }
                                Log.e("getHutangpiutang: ", " label: " + label + "tipe" + tipe + "urut" + urut + " value: " + value + "listhutangpiutang: " + list_hutangPiutang);
                                hutangPiutang = new HutangPiutang(label, tipe, urut, value, list_hutangPiutang);
                                if (!HutangPiutang.getLabel().isEmpty()) {
                                    labelHutangPiutang.add(list_hutangPiutang.get(1));
                                    labelHutangPiutang.add(list_hutangPiutang.get(9));
                                    labelHutangPiutang.add(list_hutangPiutang.get(13));
                                    labelHutangPiutang.add(list_hutangPiutang.get(25));
                                    labelHutangPiutang.add(list_hutangPiutang.get(33));
                                    //-------------------------------------------------
                                    //formatString(persediaanValue, Header.getListHeader().get(4), 1000);
                                    if (tipe.equals("Hutang")) {
                                        valueHutang.add(NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(value)));
                                    } else {
                                        valuePiutang.add(NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(value)));
                                    }
                                    for (int i = 2; i < list_hutangPiutang.size(); i += 4) {
                                        if (list_hutangPiutang.get(i).equals("Hutang")) {
                                            valueHutang.add(NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(list_hutangPiutang.get(i + 2))));
                                        } else {
                                            valuePiutang.add(NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(list_hutangPiutang.get(i + 2))));
                                        }
                                    }

                                    Log.e("HutangPiutangLabel : ", labelHutangPiutang.get(0) + labelHutangPiutang.get(1) + labelHutangPiutang.get(2)
                                            + labelHutangPiutang.get(3) + labelHutangPiutang.get(4));
                                    Log.e("PiutangValue : ", valuePiutang.get(0) + " " + valuePiutang.get(1) + " " + valuePiutang.get(2) +
                                            " " + valuePiutang.get(3) + " " + valuePiutang.get(4));
                                    Log.e("HutangValue : ", valueHutang.get(0) + " " + valueHutang.get(1) + " " + valueHutang.get(2) +
                                            " " + valueHutang.get(3) + " " + valueHutang.get(4));
                                    listHutangAdapter = new ListHutangAdapter(Dashboard.this, labelHutangPiutang, valueHutang, valuePiutang);

                                    listhutang.setNestedScrollingEnabled(true);
                                    listhutang.setAdapter(listHutangAdapter);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            labelHutangPiutang.add("Error No Data");
                            listHutangAdapter = new ListHutangAdapter(Dashboard.this, labelHutangPiutang, valueHutang, valuePiutang);
                            listhutang.setAdapter(listHutangAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "HutangPiutang failed", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie", "xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(arrayRequest);
    }

    //Get laba rugi
    public void getLabaRugi() {
        list_labarugi.clear();
        String url = "https://xfocus.id/dashboard/getLabaRugi?area=" + area_id + "&firstdate=" + first_date.getText() + "&isPusat=" + ClientLogin.getIsAreaPusat() + "&latedate=" + last_date.getText() + "&showby=" + periode;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() != 0) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                String label = jsonObject.optString("label");
                                String valueA = jsonObject.optString("valueA");
                                String valueB = jsonObject.optString("valueB");

                                JSONArray listlabarugi = response.getJSONArray(1);
                                for (int i = 0; i < listlabarugi.length(); i++) {
                                    String labarugi = listlabarugi.getString(i);
                                    list_labarugi.add(labarugi);
                                }
                                Log.e("getLabaRugi: ", " label: " + label + " urut: " + valueA + " value: " + valueB + "listpenjualan: " + list_penjualan);
                                labaRugi = new LabaRugi(label, valueA, valueB, list_labarugi);

                                if (!LabaRugi.getLabel().isEmpty()) {
                                    //Chart setup
                                    ArrayList<Entry> caseLastYear = new ArrayList<>();
                                    ArrayList<Entry> caseThisYear = new ArrayList<>();
                                    caseLastYear.add(new Entry(0, Float.parseFloat(LabaRugi.getValueA())));
                                    caseThisYear.add(new Entry(0, Float.parseFloat(LabaRugi.getValueB())));
                                    for (int i = 2; i < LabaRugi.getListLabaRugi().size(); i += 3) {
                                        caseLastYear.add(new Entry(labaRugiMonth, Float.parseFloat( LabaRugi.getListLabaRugi().get(i))));
                                        caseThisYear.add(new Entry(labaRugiMonth, Float.parseFloat( LabaRugi.getListLabaRugi().get(i+1))));
                                        labaRugiMonth++;
                                    }

                                    labaRugiMonth = 1;

                                    LineChartSetup(caseLastYear, caseThisYear);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Controlling view
                            lineChartLabaRugi.clear();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Laba rugi failed", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie", "xfocus_session=352824d5b2a203bf173993f5407fbb3b2ded40cc");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
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
    private void setCardVisible(final CardView cardDashboard, final PieChart donutChart) {
        cardDashboard.setVisibility(View.VISIBLE);
        scrollDashboard.postDelayed(new Runnable() {
            public void run() {
                scrollDashboard.smoothScrollTo(0, (int) (cardDashboard.getY() + cardDashboard.getHeight() / 2));
                donutChart.animateXY(1000, 1000);
            }
        }, 100);
    }

    //Set the cardview to visible
    private void setCardVisible(final CardView cardDashboard, final LineChart lineChart) {
        cardDashboard.setVisibility(View.VISIBLE);
        scrollDashboard.postDelayed(new Runnable() {
            public void run() {
                scrollDashboard.smoothScrollTo(0, (int) (cardDashboard.getY() + cardDashboard.getHeight() / 2));
                lineChart.animateXY(1000, 1000);
            }
        }, 100);
    }

    public void LineChartSetup(ArrayList<Entry> lastYear, ArrayList<Entry> thisYear) {

        lineChartLabaRugi.setTouchEnabled(true);
        lineChartLabaRugi.setDragEnabled(true);
        lineChartLabaRugi.setPinchZoom(true);

        //Legend setup
        lineChartLabaRugi.getDescription().setEnabled(false);
        lineChartLabaRugi.getLegend().setTextSize(14f);
        lineChartLabaRugi.getLegend().setFormSize(14f);
        lineChartLabaRugi.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        lineChartLabaRugi.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        lineChartLabaRugi.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lineChartLabaRugi.getLegend().setDrawInside(false);

        //Axis label
        lineChartLabaRugi.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        lineChartLabaRugi.getAxisRight().setEnabled(false);

        lineChartLabaRugi.getAxisLeft().setTextSize(13f);
        lineChartLabaRugi.getXAxis().setTextSize(13f);
        lineChartLabaRugi.setExtraOffsets(0, 0, 0, 10f);


        ArrayList<String> dates = new ArrayList<>();
        dates.add("2020-01-01");
        dates.add("2020-02-01");
        dates.add("2020-03-01");
        dates.add("2020-04-01");
        dates.add("2020-05-01");
        dates.add("2020-06-01");
        dates.add("2020-07-01");
        dates.add("2020-08-01");
        dates.add("2020-09-01");
        dates.add("2020-10-01");
        dates.add("2020-11-01");
        dates.add("2020-12-01");
        lineChartLabaRugi.getXAxis().setValueFormatter(new AxisDateFormatter());
        lineChartLabaRugi.getXAxis().setGranularity(1);

        LineDataSet set1 = new LineDataSet(lastYear, getYear(-1));
        set1.setMode(LineDataSet.Mode.LINEAR);
        set1.setColor(Color.RED);
        set1.setCircleRadius(5f);
        set1.setCircleColor(Color.RED);
        set1.setDrawValues(false);
        set1.setLineWidth(3f);

        LineDataSet set2 = new LineDataSet(thisYear, getYear(-0));
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setColor(Color.BLUE);
        set2.setCircleRadius(5f);
        set2.setCircleColor(Color.BLUE);
        set2.setDrawValues(false);
        set2.setLineWidth(3f);

        LineData data = new LineData(set1,set2);

        lineChartLabaRugi.setData(data);
    }

    private static String getYear(int a) {
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, a);
        String year = String.valueOf(prevYear.get(Calendar.YEAR));
        return year;
    }

    //Set up the diagram for viewing all data
    private void setDonutCharts(ArrayList<PieEntry> pieEntries, int[] colorTemplate, PieChart pieChart, String chartName) {
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        pieDataSet.setColors(colorTemplate);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        //pieData.setValueFormatter(new PercentFormatter(pieChart));
        //pieData.setDrawValues(true);
        pieChart.setData(pieData);
        //Setting the diagram legend
        pieChart.getLegend().setFormSize(15f);
        pieChart.getLegend().setTextSize(14f);
        pieChart.getLegend().setXEntrySpace(20f);
        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.getLegend().setEnabled(false);
        //Using percentage
        //pieChart.setUsePercentValues(true);
        //Setting the minimum angle
        pieChart.setMinAngleForSlices(15f);
        //Disable all info
        pieChart.setDrawEntryLabels(false);
        pieChart.getData().setDrawValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText(chartName);
    }

    //Set up the animation for drop down view of Charts
    private void animateDropDownChart(int angleA, int angleB, ImageView imageView, int rotationValue) {
        RotateAnimation rotate = new RotateAnimation(angleA, angleB, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(rotate);
        imageView.setRotation(imageView.getRotation() + rotationValue);
    }

    //Formatting the currency of the number imported from database
    private void formatString(TextView textView, String value, int divided) {
        textView.setText(String.format("%1$,.2f", Double.parseDouble(value) / divided));
    }

    //On spinner's item selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        switch (parent.getId()) {
            case R.id.spinnerArea:
                String areaName = parent.getItemAtPosition(pos).toString();
                int index = list_area.indexOf(areaName);
                area_id = ClientLogin.getListAreaId().get(index);
                break;
            case R.id.spinnerPeriode:
                periode = parent.getItemAtPosition(pos).toString();
                switch (periode) {
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






