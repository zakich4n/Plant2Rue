package com.example.myfirstapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myfirstapp.R;
import com.example.myfirstapp.database.DBManager;

import org.w3c.dom.Document;

import java.io.File;
import java.nio.file.Path;

public class ResetActivity extends AppCompatActivity {

    DBManager dbManager;


    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        getSupportActionBar().hide();

        Button btn_oui = (Button) findViewById(R.id.btn_reset_oui);
        Button btn_non = (Button) findViewById(R.id.btn_reset_non);

        btn_non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back();
            }
        });

        btn_oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reset_go();
            }
        });

    }

    private void Reset_go() {

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

        dbManager.Delete();


        Intent intent_reset = new Intent(this, MainActivity.class);
        startActivity(intent_reset);

    }

    private void go_back() {
        Intent intent_go_back = new Intent(this, MainActivity.class);
        startActivity(intent_go_back);
    }


}