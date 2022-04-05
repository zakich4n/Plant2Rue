package com.example.myfirstapp.singleton_and_adapter;

import android.util.Log;

import com.example.myfirstapp.activity.MaNouvellePlante1Activity;
import com.example.myfirstapp.object.Plante;

import java.util.ArrayList;


public class LesPlantesSingleton {


    private static final LesPlantesSingleton instance = new LesPlantesSingleton();

    public ArrayList<Plante> listPlantes;

    private LesPlantesSingleton(){
        Log.d("Debug", "Singleton instancié");
        listPlantes = MaNouvellePlante1Activity.listPlantesActivity;
    }

    public static final LesPlantesSingleton getInstance() { return instance; }


}

