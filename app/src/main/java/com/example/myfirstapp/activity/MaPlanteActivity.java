package com.example.myfirstapp.activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;
import com.example.myfirstapp.database.DBManager;
import com.example.myfirstapp.object.Plante;
import com.squareup.picasso.Picasso;

public class MaPlanteActivity extends AppCompatActivity {

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_plante);
        getSupportActionBar().hide();

        dbManager = new DBManager(this);

        try {
            dbManager.open();
            Log.d("database", "Base de données ouverte");
        } catch (SQLException e){
            e.printStackTrace();
        }

        Plante LaPlante = dbManager.checkIfExist().get(0);


        ImageView imageView_Plante = (ImageView) findViewById(R.id.imageView_planteActivity);
        Picasso.get().load(LaPlante.cover).into(imageView_Plante);

        TextView textView_temperature = (TextView) findViewById(R.id.température_ideal);
        TextView textView_humidite = (TextView) findViewById(R.id.humidité_ideal);
        TextView textView_luminosite = (TextView) findViewById(R.id.luminosité_ideal);
        TextView textView_NomPlanteActivity = (TextView) findViewById(R.id.textView_NomPlanteActivity);

        textView_temperature.setText(LaPlante.temperature+" °C");
        textView_humidite.setText(LaPlante.humidite+" %");
        textView_luminosite.setText(LaPlante.luminosite+" lx");
        textView_NomPlanteActivity.setText(LaPlante.nom);

        Button btn_historic = (Button) findViewById(R.id.btn_historic);
        btn_historic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historicActivity_go();
            }
        });

    }

    void historicActivity_go(){
        Intent intent_historic_go = new Intent(this, HistoriqueActivity.class);
        startActivity(intent_historic_go);
    }
}