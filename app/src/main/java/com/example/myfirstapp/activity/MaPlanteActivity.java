package com.example.myfirstapp.activity;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.JsonParcer;
import com.example.myfirstapp.R;
import com.example.myfirstapp.database.DBManager;
import com.example.myfirstapp.object.Plante;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MaPlanteActivity extends AppCompatActivity {

    DBManager dbManager;
    TextView textview_progress;
    ImageView humeur_Plante;

    Drawable emoji_coeur;
    Drawable emoji_neutre;
    Drawable emoji_malade;


    public static double temp_current;
    public static double hum1_current;
    public static double lum_current;
    public static double hum2_current;

    TextView textView_temperature_R;
    TextView textView_humidite_R;
    TextView textView_luminosite_R;
    TextView textView_humidite2_R;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_plante);
        getSupportActionBar().hide();
        textview_progress= findViewById(R.id.textview_progress);


        Log.d("debug", "MaPlanteActivity");


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

        TextView textView_temperature_I = (TextView) findViewById(R.id.température_ideal);
        TextView textView_humidite_I = (TextView) findViewById(R.id.humidité_ideal);
        TextView textView_luminosite_I = (TextView) findViewById(R.id.luminosité_ideal);
        TextView textView_humidite2_I = (TextView) findViewById(R.id.humidité2_ideal);

        textView_temperature_R = (TextView) findViewById(R.id.température_reelle);
        textView_humidite_R = (TextView) findViewById(R.id.humidité_reelle);
        textView_luminosite_R = (TextView) findViewById(R.id.luminosité_reelle);
        textView_humidite2_R = (TextView) findViewById(R.id.humidité2_reelle);


        TextView textView_NomPlanteActivity = (TextView) findViewById(R.id.textView_NomPlanteActivity);

        textView_temperature_I.setText(LaPlante.temperature+" °C ");
        textView_humidite_I.setText(LaPlante.humidite+" % ");
        textView_luminosite_I.setText(LaPlante.luminosite+" lx ");
        textView_humidite2_I.setText(LaPlante.uv+" %");

        textView_NomPlanteActivity.setText(LaPlante.nom);

        new AsynchroneTask().execute();

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




    public class AsynchroneTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            Log.d("ordre", "onPreExecute");
            textview_progress.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            Log.d("ordre", "doInBackground");

            try {
                get_data("https://thingspeak.com/channels/1679025/field/1.json", 1);
                get_data("https://thingspeak.com/channels/1679025/field/2.json", 2);
                get_data("https://thingspeak.com/channels/1679025/field/3.json", 3);
                get_data("https://thingspeak.com/channels/1679025/field/4.json", 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "test";
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("ordre", "onPost -> "+temp_current);

            emoji_coeur = getResources().getDrawable(R.drawable.emoji_coeur);
            emoji_neutre = getResources().getDrawable(R.drawable.emoji_neutre);
            emoji_malade = getResources().getDrawable(R.drawable.emoji_malade);

            humeur_Plante = (ImageView) findViewById(R.id.humeur_plante);
            textview_progress.setVisibility(View.INVISIBLE);

            textView_temperature_R.setText(temp_current+" /");
            textView_humidite_R.setText(hum1_current+" /");
            textView_luminosite_R.setText(lum_current+" /");
            textView_humidite2_R.setText(hum2_current+" /");

            Plante LaPlante = dbManager.checkIfExist().get(0);

            double eval =  Math.abs(temp_current- LaPlante.temperature) + Math.abs(hum1_current-LaPlante.humidite)/5 + Math.abs(lum_current-LaPlante.luminosite)/100 + Math.abs(hum2_current-LaPlante.uv)/8;

            Log.d("humeur", String.valueOf(eval));

            if (eval >= 35 ) {
                humeur_Plante.setImageDrawable(emoji_malade);
            } else {
                if (eval >= 20) {
                    humeur_Plante.setImageDrawable(emoji_neutre);
                } else {
                    humeur_Plante.setImageDrawable(emoji_coeur);
                }
            }


        }
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
                                temp_current = JsonParcer.getInstance().parseJson_current_settings(result, id);
                                Log.d("debug", "Temp -> "+String.valueOf(temp_current));
                            } else {
                                if (id ==2) {
                                    hum1_current = JsonParcer.getInstance().parseJson_current_settings(result, id);
                                    Log.d("debug", "Hum -> "+String.valueOf(hum1_current));
                                } else {
                                    if (id ==3 ){
                                        lum_current = JsonParcer.getInstance().parseJson_current_settings(result, id);
                                        Log.d("debug", "Lum -> "+String.valueOf(lum_current));
                                    } else {
                                        hum2_current = JsonParcer.getInstance().parseJson_current_settings(result, id);
                                        Log.d("debug", "Hum2 -> "+String.valueOf(hum2_current));
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

}

