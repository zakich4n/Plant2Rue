package com.example.myfirstapp.activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;
import com.example.myfirstapp.database.DBManager;
import com.example.myfirstapp.object.Plante;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Log.d("debug", "MainActivity");


        Button btn_NewTest_go = (Button) findViewById(R.id.btn_newTest);
        btn_NewTest_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start_go();
            }
        });

        Button btn_go_notif = (Button) findViewById(R.id.btn_go_notif);
        btn_go_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification_go();
            }
        });

    }

    private void Start_go() {
        Intent intent_MaNouvellePlante0Activity = new Intent(this, MaNouvellePlante0Activity.class);
        Intent intent_MaPlanteActivity = new Intent(this, MaPlanteActivity.class);


        //Base de données
        Log.d("debug", "Pour la base de données des types de plantes");
        Log.d("database", "Ouverture de la base de données");
        dbManager = new DBManager(this);

        try {
            dbManager.open();
            Log.d("database", "Base de données ouverte");
        } catch (SQLException e){
            e.printStackTrace();
        }

        ArrayList<Plante> listExists = dbManager.checkIfExist();
        Log.d("debug", String.valueOf(listExists));



        if ( listExists.isEmpty()){
            Log.d("database", "pas enregistré");
            startActivity(intent_MaNouvellePlante0Activity);
        } else {
            Log.d("database", "enregistré");

            startActivity(intent_MaPlanteActivity);
        }

        dbManager.close();
    }

    void Notification_go(){
        Intent intent_ntotification_go = new Intent(this, ResetActivity.class);
        startActivity(intent_ntotification_go);
    }

}
