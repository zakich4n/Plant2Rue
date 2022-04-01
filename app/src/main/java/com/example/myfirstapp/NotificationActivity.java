package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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