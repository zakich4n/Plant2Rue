package com.example.myfirstapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDbHelper extends SQLiteOpenHelper {



    //Création de la base de données
    public AppDbHelper(Context context) {
        super(context, "PlantesDB.db", null, 1);
        Log.d("database", "Création de la base de données");
    }

    //Création de la table principale
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("database", "Création de la table");

        String strSql = "create table Plantes ("
                + "    id integer primary key autoincrement,"
                + "    nom text,"
                + "    nomType text not null,"
                + "    cover text not null,"
                + "    temperature integer not null,"
                + "    humidite integer not null,"
                + "    uv integer not null,"
                + "    luminosite integer not null"
                + ")";

        db.execSQL( strSql );
        Log.d( "database", "onCreate invoked" );
    }

    //Mise à jour de la base de données
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String strSql = "drop table Plantes";
        db.execSQL( strSql );
        this.onCreate( db );
        Log.i( "database", "onUpgrade invoked" );

    }
}