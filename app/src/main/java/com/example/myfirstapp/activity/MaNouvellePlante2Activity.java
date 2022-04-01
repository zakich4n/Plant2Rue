package com.example.myfirstapp.activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.LesPlantesSingleton;
import com.example.myfirstapp.R;
import com.example.myfirstapp.database.DBManager;
import com.example.myfirstapp.object.Plante;

public class MaNouvellePlante2Activity extends AppCompatActivity {

    Plante typePlanteClic;
    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_nouvelle_plante2);

        getSupportActionBar().hide();

        int position = getIntent().getIntExtra("position", 0);
        if (position >= 0 && position < LesPlantesSingleton.getInstance().listPlantes.size()) {
            typePlanteClic = LesPlantesSingleton.getInstance().listPlantes.get(position);
            Log.d("debug", "Confirmation Activity");

            String NomPlante = getIntent().getExtras().getString("NomPlante");
            Log.d("debug", "après confirmation, le nom de la plante est "+NomPlante);
            Plante maPlante = new Plante(typePlanteClic.nomType, typePlanteClic.cover, typePlanteClic.temperature, typePlanteClic.humidite, typePlanteClic.uv, typePlanteClic.luminosite);
            maPlante.setNom(NomPlante);

            dbManager = new DBManager(this);

            try {
                dbManager.open();
                Log.d("database", "Base de données ouverte");
            } catch (SQLException e){
                e.printStackTrace();
            }
            dbManager.insertPlante(maPlante);
            dbManager.close();
            Log.d("debug", maPlante.toStringInfotmation());
            Log.d("debug", "La plante a été ajoutée");

            Button btn_mainActivity_go = (Button) findViewById(R.id.btn_mainActivity_go);
            btn_mainActivity_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity_go();
                }
            });

        }
    }

    void mainActivity_go(){
        Intent intent_mainActivity = new Intent(this, MainActivity.class);
        startActivity(intent_mainActivity);
    }


}
