package com.example.myfirstapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myfirstapp.object.Plante;

import java.util.ArrayList;


public class DBManager {

    private SQLiteDatabase database;
    private AppDbHelper dbHelper;


    public DBManager (Context context) { dbHelper = new AppDbHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() { dbHelper.close();}

    //Retourner la totalité du contenu de la base de données
    public ArrayList<Plante> getAllPlantes(){
        Log.d("database", "Obtention du contenu de la base de données");
        ArrayList<Plante> plantelist = new ArrayList<>();

        Cursor cursor = database.rawQuery("Select * from Plantes", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plante plante = cursorToPlante(cursor);
            plantelist.add(plante);
            cursor.moveToNext();
        }

        cursor.close();
        return plantelist;
    }

    //Convertir un curseur en Objet Plante
    @SuppressLint("Range")
    private Plante cursorToPlante (Cursor cursor){
        Plante plante = new Plante();
        plante.setNom(cursor.getString(cursor.getColumnIndex("nom")));
        plante.setNomType(cursor.getString(cursor.getColumnIndex("nomType")));
        plante.setCover(cursor.getString(cursor.getColumnIndex("cover")));
        plante.setTemperature(cursor.getInt(cursor.getColumnIndex("temperature")));
        plante.setHumidite(cursor.getInt(cursor.getColumnIndex("humidite")));
        plante.setUv(cursor.getInt(cursor.getColumnIndex("uv")));
        plante.setLuminosite(cursor.getInt(cursor.getColumnIndex("luminosite")));

        return plante;
    }

    //Insérer une plante dans la base de données
    public void insertPlante(Plante plante){
        Log.d("database", "Début de l'insertion");
        ContentValues values = new ContentValues();
        values.put("nom", plante.nom);
        values.put("nomType", plante.nomType);
        values.put("cover", plante.cover);
        values.put("temperature", plante.temperature);
        values.put("humidite", plante.humidite);
        values.put("uv", plante.uv);
        values.put("luminosite", plante.luminosite);
        Log.d("database", String.valueOf(values));

        database.insertWithOnConflict("Plantes", "null", values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("database", "Fin de l'insertion");
    }

    //Checker si l'utilisateur a déjà enregistré une plante

    public ArrayList<Plante> checkIfExist(){
        Log.d("database", "Début du check");
        ArrayList<Plante> plantExists = new ArrayList<>();

        Cursor cursor = database.rawQuery("Select * from Plantes where nom is not null", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plante plante = cursorToPlante(cursor);
            plantExists.add(plante);
            cursor.moveToNext();
        }

        cursor.close();

        Log.d("database", "fin du check --> "+plantExists);
        return plantExists;
    }


    public void Delete(){
        database.delete("Plantes", "nom is not null", null );

    }


}
