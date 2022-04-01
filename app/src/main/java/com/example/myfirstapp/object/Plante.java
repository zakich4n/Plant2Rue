package com.example.myfirstapp.object;

public class Plante {
    public int id;
    public String nom;
    public String nomType;
    public String cover;


    public int temperature;
    public int humidite;
    public int uv;
    public int luminosite;


    public Plante(){}

    public Plante(String nomType, String cover, int tempe, int hum, int uv, int lum){
        this.nomType = nomType;
        this.cover = cover;
        this.temperature = tempe;
        this.humidite = hum;
        this.uv = uv;
        this.luminosite = lum;

    }


    public void setNom (String nom){
        this.nom = nom;
    }
    public void setNomType (String nomType) {
        this.nomType = nomType;
    }
    public void setCover (String cover) {
        this.cover = cover;
    }
    public void setTemperature (int t){
        this.temperature = t;
    }
    public void setHumidite (int h) {
        this.humidite = h;
    }
    public void setUv (int u){
        this.uv = u;
    }
    public void setLuminosite (int l) {
        this.luminosite = l;
    }


    public String toString(){
        return "La plante est --> "+nomType+" avec une cover --> "+cover;
    }
    public String toStringInfotmation(){
        return "La plante du nom de "+nom+" est --> "+nomType+" avec une cover --> "+cover;
    }
}
