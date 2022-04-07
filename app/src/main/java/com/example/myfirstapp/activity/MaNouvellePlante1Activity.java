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

import com.example.myfirstapp.singleton_and_adapter.LesPlantesAdapater;
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

        Plante plante1 = new Plante("Monstera", "https://cdn.shopify.com/s/files/1/1752/4567/products/monstera_sm_10_eva_latte_1200x1200.png?v=1539265671",24, 45,670,800);
        Plante plante2 = new Plante("Mint", "https://mosfresh.co.za/images/thumbs/0000235_mint-30g.png",25, 60, 695, 900);
        Plante plante3 = new Plante("Bonsai", "https://bonsaiarteviva.com.br/wp-content/uploads/2020/08/shimpaku-2-962x1024.png", 23, 50, 690, 850);
        Plante plante4 = new Plante("Dracena", "https://cdn.shopify.com/s/files/1/1752/4567/products/dracaena_mass_stump_branched_10_eva_white_1200x1200.png?v=1537195984", 22, 40, 685, 700);
        Plante plante6 = new Plante("Aloe Vera", "https://images.nieuwkoop-europe.com/images/5ALVBKK24.png",  24, 45, 680, 850);
        Plante plante7 = new Plante("Calathea", "https://undergreen.be/wp-content/uploads/sites/3/2020/02/calathea-1.png", 24, 35, 700, 870);
        Plante plante8 = new Plante("Yucca", "https://fleurenflower.nl/wp-content/uploads/2020/05/Canva-Potted-Yucca-Plant-1-790x1024.png", 25, 60, 675, 855);
        Plante plante9 = new Plante("Kentia", "https://jngl.nl/images/products/kentia-palm-b9b7.png", 25, 65,685, 905 );
        Plante plante10 = new Plante("Zamiocuclcas", "https://i.pinimg.com/originals/e9/bb/fd/e9bbfd17c2039ad0c0249c3a4e368623.png", 25, 50, 687, 887);
        Plante plante11 = new Plante("Pothos", "https://www.pngkit.com/png/full/6-64377_low-light-interior-plant-jade-pothos-small-indoor.png", 24, 60, 678, 860);
        Plante plante12 = new Plante("Elastica", "https://www.thegardenstore.sg/image/cache/catalog/products/Plant/Ficus%20Elastica%20Green%20Rubber%20Plant-910x1155.png", 26, 55, 700, 830);

        Log.d("temp", String.valueOf(plante2));

        //Insertion des plantes
        dbManager.insertPlante(plante1);
        dbManager.insertPlante(plante2);
        dbManager.insertPlante(plante3);
        dbManager.insertPlante(plante4);
        dbManager.insertPlante(plante6);
        dbManager.insertPlante(plante7);
        dbManager.insertPlante(plante8);
        dbManager.insertPlante(plante9);

        dbManager.insertPlante(plante11);
        dbManager.insertPlante(plante12);


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