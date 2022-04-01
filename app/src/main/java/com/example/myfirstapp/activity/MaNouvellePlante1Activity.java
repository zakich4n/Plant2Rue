package com.example.myfirstapp.activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.LesPlantesAdapater;
import com.example.myfirstapp.R;
import com.example.myfirstapp.database.DBManager;
import com.example.myfirstapp.object.Plante;
import com.example.myfirstapp.singleton_and_adapter.OnRecyclerItemClickListener;

import java.util.ArrayList;

public class MaNouvellePlante1Activity extends AppCompatActivity implements OnRecyclerItemClickListener {



    RecyclerView recyclerView;
    private DBManager dbManager;
    public static ArrayList listPlantesActivity;
    public static String NomPlante = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug", "OnCreate de MaNouvelleActivity1");
        setContentView(R.layout.activity_ma_nouvelle_plante1);

        //Récupérer le nom de la plante
        NomPlante = getIntent().getExtras().getString("NomPlante");
        Log.d("debug", "le nom de la plante est 1 --> "+NomPlante);

        //Afficher le nom de la plante
        TextView TextViewTypePlante = (TextView) findViewById(R.id.textView_typePlante);
        TextViewTypePlante.setText("Quel est le type de "+NomPlante);


        getSupportActionBar().hide();

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


        Plante plante1 = new Plante("Monstera", "https://cdn.shopify.com/s/files/1/1752/4567/products/monstera_sm_10_eva_latte_1200x1200.png?v=1539265671",50, 20,2,3);
        Plante plante2 = new Plante("Mint", "https://mosfresh.co.za/images/thumbs/0000235_mint-30g.png",50, 20, 2, 3 );
        Plante plante3 = new Plante("Bonsai", "https://bonsaiarteviva.com.br/wp-content/uploads/2020/08/shimpaku-2-962x1024.png", 50, 20, 2, 3);
        Plante plante4 = new Plante("Dracena", "https://cdn.shopify.com/s/files/1/1752/4567/products/dracaena_mass_stump_branched_10_eva_white_1200x1200.png?v=1537195984", 50, 20, 2, 3);
        Plante plante5 = new Plante("Orchid", "https://cdn.kreezalid.com/kreezalid/544992/catalog/8080/54/1000x1000_5can-505-1_hp6hf_207995003.png", 50, 20, 3, 2);
        Plante plante6 = new Plante("Aloe Vera", "https://images.nieuwkoop-europe.com/images/5ALVBKK24.png",  50, 20, 3, 2);
        Plante plante7 = new Plante("Geranium", "https://nanasbloomers.com/wp-content/uploads/2017/11/deluxe-Regal-Geranium-Candy-flowers-peach-cloud3.png", 50, 20, 3, 2);
        Plante plante8 = new Plante("Rose", "https://pngimg.com/uploads/rose/rose_PNG66776.png",50, 20, 3, 2);


        //Insertion des plantes
        dbManager.insertPlante(plante1);
        dbManager.insertPlante(plante2);
        dbManager.insertPlante(plante3);
        dbManager.insertPlante(plante4);
        dbManager.insertPlante(plante5);
        dbManager.insertPlante(plante6);
        dbManager.insertPlante(plante7);
        dbManager.insertPlante(plante8);




        Log.d("database", "Les insertions dans la base de données sont terminées");

        listPlantesActivity = dbManager.getAllPlantes();

        //Pour la RecyclerView
        recyclerView = findViewById(R.id.recyclerview_typePlante);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LesPlantesAdapater(this));
    }

    @Override
    public void OnRecyclerItemClickListener_function(View view, int position) {
        Intent intent = new Intent(this, MaNouvellePlante2Activity.class);
        intent.putExtra("position", position);
        intent.putExtra("NomPlante", NomPlante);
        startActivity(intent);

    }


    /*
    @Override
    protected void onPause() {
        super.onPause();
        dbManager.close();
    }

    @Override
    protected void onResume(){
        super .onResume();
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     try {
        dbManager.open();
        Log.d("debug", "la base de données est ouverte");
    }catch (SQLException e) {
        e.printStackTrace();
    }

     */
}