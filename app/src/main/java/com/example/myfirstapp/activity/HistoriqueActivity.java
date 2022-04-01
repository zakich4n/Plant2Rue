package com.example.myfirstapp.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;

public class HistoriqueActivity extends AppCompatActivity {

    WebView webview_temperature;
    WebView webview_humidite;
    WebView webview_luminosite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        getSupportActionBar().hide();

        chartTemperature();
        chartHumidite();
        chartLuminosite();

    }

    void chartTemperature(){

        webview_temperature = (WebView) findViewById(R.id.webview_temperature);
        webview_temperature.getSettings().setJavaScriptEnabled(true);
        webview_temperature.setVerticalScrollBarEnabled(true);
        webview_temperature.setHorizontalScrollBarEnabled(true);
        webview_temperature.loadUrl("https://thingspeak.com/channels/1679025/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");

    }

    void chartHumidite(){

        webview_luminosite = (WebView) findViewById(R.id.webview_luminosite);
        webview_luminosite.getSettings().setJavaScriptEnabled(true);
        webview_luminosite.setVerticalScrollBarEnabled(true);
        webview_luminosite.setHorizontalScrollBarEnabled(true);
        webview_luminosite.loadUrl("https://thingspeak.com/channels/1679025/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");

    }

    void chartLuminosite(){

        webview_humidite = (WebView) findViewById(R.id.webview_humidite);
        webview_humidite.getSettings().setJavaScriptEnabled(true);
        webview_humidite.setVerticalScrollBarEnabled(true);
        webview_humidite.setHorizontalScrollBarEnabled(true);
        webview_humidite.loadUrl("https://thingspeak.com/channels/1679025/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");

    }
}