package com.example.myfirstapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myfirstapp.JsonParcer;
import com.example.myfirstapp.NotificationHelper;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        Button btnNotif = findViewById( R.id.btn_notif );
        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotificationHelper notificationHelper = new NotificationHelper(NotificationActivity.this);
                notificationHelper.notify(1, true, "J'ai soif !", "Votre plante manque d'eau !" );
                Log.i("NotificationActivity", "Notification launched");

            }
        });
    }

}