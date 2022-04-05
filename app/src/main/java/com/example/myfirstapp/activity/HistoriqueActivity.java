package com.example.myfirstapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.JsonParcer;
import com.example.myfirstapp.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoriqueActivity extends AppCompatActivity {

    LineChart lineChartTemp;
    LineData lineDataTemp;
    List<Entry> entryListTemp = new ArrayList<>();

    LineChart lineChartLum;
    LineData lineDataLum;
    List<Entry> entryListLum = new ArrayList<>();

    LineChart lineChartHum1;
    LineData lineDataHum1;
    List<Entry> entryListHum1 = new ArrayList<>();

    LineChart lineChartHum2;
    LineData lineDataHum2;
    List<Entry> entryListHum2 = new ArrayList<>();

    LinkedList<Integer> tempList = new LinkedList<>();
    LinkedList<Integer> humList = new LinkedList<>();
    LinkedList<Integer> lumpList = new LinkedList<>();
    LinkedList<Integer> hum2List = new LinkedList<>();

    LinkedList<Float> timeList = new LinkedList<Float>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        getSupportActionBar().hide();


        Button btn_actualiser = (Button) findViewById(R.id.btn_actualiser);
        btn_actualiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddTime();

                entryListTemp.clear();
                entryListHum1.clear();
                entryListLum.clear();
                entryListHum2.clear();

                try {
                    Log.d("debug", "request sent");

                    get_data("https://thingspeak.com/channels/1679025/field/1.json", 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Log.d("debug", "request sent");

                    get_data("https://thingspeak.com/channels/1679025/field/2.json", 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Log.d("debug", "request sent");

                    get_data("https://thingspeak.com/channels/1679025/field/3.json", 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Log.d("debug", "request sent");

                    get_data("https://thingspeak.com/channels/1679025/field/4.json", 4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException exception) {
                    exception.printStackTrace();
                }

                PlotGraphTemp();
                PlotGraphHum1();
                PlotGraphLum();
                PlotGraphHum2();


            }
        });


    }

    private void PlotGraphHum2() {
        Log.d("debug", "début du plot");
        lineChartHum2 = findViewById(R.id.lineHum2);
        lineChartHum2.animateX(1000, Easing.EaseOutBounce);


        //cacher la grille
        lineChartHum2.getXAxis().setDrawGridLines(false);
        lineChartHum2.getAxisLeft().setDrawGridLines(false);
        lineChartHum2.getAxisRight().setDrawGridLines(false);

        //cacher l'ordonnée de droite
        lineChartHum2.getAxisRight().setDrawLabels(false);
        lineChartHum2.getAxisLeft().setDrawLabels(false);

        //positionner les données de l'abscisse en bas
        XAxis xAxis = lineChartHum2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        //cacher la description
        lineChartHum2.getDescription().setEnabled(false);

        Legend legend = lineChartHum2.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        //ligne 1
        for (int i=0; i<10; i++){
            entryListHum2.add(new Entry(timeList.get(9-i), hum2List.get(i)));
        }


        LineDataSet lineDataSet = new LineDataSet(entryListHum2,"Humdité de la terre durant ces dernières minutes ...");
        lineDataSet.setColors(Color.parseColor("#0A91A8"));
        lineDataSet.setLineWidth(3);
        lineDataSet.setFillAlpha(110);

        List<ILineDataSet> dataSets = new ArrayList<>(); // for adding multiple plots
        dataSets.add(lineDataSet);

        //Plot
        lineDataHum2 = new LineData(dataSets);
        lineChartHum2.setData(lineDataHum2);
        lineChartHum2.invalidate();
    }

    private void PlotGraphHum1() {

        Log.d("debug", "début du plot");
        lineChartHum1 = findViewById(R.id.lineHum1);
        lineChartHum1.animateX(1000, Easing.EaseOutBounce);


        //cacher la grille
        lineChartHum1.getXAxis().setDrawGridLines(false);
        lineChartHum1.getAxisLeft().setDrawGridLines(false);
        lineChartHum1.getAxisRight().setDrawGridLines(false);

        //cacher l'ordonnée de droite
        lineChartHum1.getAxisRight().setDrawLabels(false);
        lineChartHum1.getAxisLeft().setDrawLabels(false);

        //positionner les données de l'abscisse en bas
        XAxis xAxis = lineChartHum1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        //cacher la description
        lineChartHum1.getDescription().setEnabled(false);

        Legend legend = lineChartHum1.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        //ligne 1
        for (int i=0; i<10; i++){
            entryListHum1.add(new Entry(timeList.get(9-i), humList.get(i)));
        }

        LineDataSet lineDataSet = new LineDataSet(entryListHum1,"Humidité de l'air durant ces dernières minutes ...");
        lineDataSet.setColors(Color.parseColor("#2C6AAC"));
        lineDataSet.setLineWidth(3);
        lineDataSet.setFillAlpha(110);

        List<ILineDataSet> dataSets = new ArrayList<>(); // for adding multiple plots
        dataSets.add(lineDataSet);

        //Plot
        lineDataHum1 = new LineData(dataSets);
        lineChartHum1.setData(lineDataHum1);
        lineChartHum1.invalidate();
    }

    private void PlotGraphTemp() {
        Log.d("debug", "début du plot");
        lineChartTemp = findViewById(R.id.lineTemp);
        lineChartTemp.animateX(1000, Easing.EaseOutBounce);


        //cacher la grille
        lineChartTemp.getXAxis().setDrawGridLines(false);
        lineChartTemp.getAxisLeft().setDrawGridLines(false);
        lineChartTemp.getAxisRight().setDrawGridLines(false);

        //cacher l'ordonnée de droite
        lineChartTemp.getAxisRight().setDrawLabels(false);
        lineChartTemp.getAxisLeft().setDrawLabels(false);

        //positionner les données de l'abscisse en bas
        XAxis xAxis = lineChartTemp.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        //cacher la description
        lineChartTemp.getDescription().setEnabled(false);

        Legend legend = lineChartTemp.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        //ligne 1
        for (int i=0; i<10; i++){
            entryListTemp.add(new Entry(timeList.get(9-i), tempList.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(entryListTemp,"Température durant ces dernières minutes ...");
        lineDataSet.setColors(Color.parseColor("#E42E0E"));
        lineDataSet.setLineWidth(3);
        lineDataSet.setFillAlpha(110);

        List<ILineDataSet> dataSets = new ArrayList<>(); // for adding multiple plots
        dataSets.add(lineDataSet);

        //Plot
        lineDataTemp = new LineData(dataSets);
        lineChartTemp.setData(lineDataTemp);
        lineChartTemp.invalidate();
    }

    private void PlotGraphLum() {
        Log.d("debug", "début du plot");
        lineChartLum = findViewById(R.id.lineLum);
        lineChartLum.animateX(1000, Easing.EaseOutBounce);


        //cacher la grille
        lineChartLum.getXAxis().setDrawGridLines(false);
        lineChartLum.getAxisLeft().setDrawGridLines(false);
        lineChartLum.getAxisRight().setDrawGridLines(false);

        //cacher l'ordonnée de droite
        lineChartLum.getAxisRight().setDrawLabels(false);
        lineChartLum.getAxisLeft().setDrawLabels(false);

        //positionner les données de l'abscisse en bas
        XAxis xAxis = lineChartLum.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        //cacher la description
        lineChartLum.getDescription().setEnabled(false);

        Legend legend = lineChartLum.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        //ligne 1
        for (int i=0; i<10; i++){
            entryListLum.add(new Entry(timeList.get(9-i), lumpList.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(entryListLum,"Luminosité durant ces dernières minutes ...");
        lineDataSet.setColors(Color.parseColor("#ED9804"));
        lineDataSet.setLineWidth(3);
        lineDataSet.setFillAlpha(110);

        List<ILineDataSet> dataSets = new ArrayList<>(); // for adding multiple plots
        dataSets.add(lineDataSet);

        //Plot
        lineDataLum = new LineData(dataSets);
        lineChartLum.setData(lineDataLum);
        lineChartLum.invalidate();
    }


    public void get_data(String url, int id) throws InterruptedException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    try {
                        final String result = response.body().string();
                        if (!TextUtils.isEmpty(result)) {

                            if (id == 1) {
                                tempList = JsonParcer.getInstance().parseJson(result, id);
                                Log.d("debug", "Temp -> "+String.valueOf(tempList));
                            } else {
                                if (id ==2) {
                                    humList = JsonParcer.getInstance().parseJson(result, id);
                                    Log.d("debug", "Hum -> "+String.valueOf(humList));
                                } else {
                                    if (id ==3 ){
                                        lumpList = JsonParcer.getInstance().parseJson(result, id);
                                        Log.d("debug", "Lum -> "+String.valueOf(lumpList));
                                    } else {
                                        hum2List = JsonParcer.getInstance().parseJson(result, id);
                                        Log.d("debug", "Hum2 -> "+String.valueOf(hum2List));
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.d("debug", "erreur de requête");
                    }
                }

            }
        });
    }

    public void AddTime(){
        timeList.clear();
        Timestamp timestamp = new Timestamp(new Date().getTime());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());

        for (int i = 0; i<10; i++) {

            // subtract 15 seconds
            cal.add(Calendar.SECOND, -15);
            timestamp = new Timestamp(cal.getTime().getTime());

            int minutes = timestamp.getMinutes();
            int seconds = timestamp.getSeconds();
            Float time = Float.valueOf(minutes+"."+seconds);

            timeList.add(time);
        }
        Log.d("date", String.valueOf(timeList));


    }

}