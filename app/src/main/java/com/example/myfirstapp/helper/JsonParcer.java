package com.example.myfirstapp.helper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class JsonParcer {

    private static final JsonParcer instance = new JsonParcer();

    public static final JsonParcer getInstance() { return instance; }

    public LinkedList<Integer> parseJsonList(String jsonresponse, int id) throws JSONException {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonresponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray JsonArray = null;
        JsonArray = (JSONArray) jsonObject.get("feeds");

        LinkedList<Integer> ValTab = new LinkedList<>();

        for (int i = JsonArray.length()-10; i < JsonArray.length(); i++) {
            JSONObject jsontpm = JsonArray.getJSONObject(i);
            int Valtmp = jsontpm.getInt("field"+id);
            ValTab.add(Valtmp);
        }

        return ValTab;
    }

    public double parseJson_current_settings(String jsonresponse, int id) throws JSONException {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsonresponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray JsonArray = null;
        JsonArray = (JSONArray) jsonObject.get("feeds");
        Log.d("debug", String.valueOf(JsonArray));

        int length = JsonArray.length();
        Log.d("debug", "longueur du tableau est de -> "+length);

        JSONObject jsontpm = JsonArray.getJSONObject(length-1);
        double Val = jsontpm.getInt("field"+id);

        return Val;
    }


}
