package com.example.myfirstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;

public class MaNouvellePlante0Activity extends AppCompatActivity {

    EditText editText_nomPlante;
    public static String NomPlante = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_nouvelle_plante0);
        getSupportActionBar().hide();


        Button btn_type_plante_go = (Button) findViewById(R.id.selection_type_go);
        editText_nomPlante = (EditText) findViewById(R.id.editText_nomPlante);


        btn_type_plante_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( editText_nomPlante.getText().toString().equals("")){
                    Log.d("debug", "La plante n'a pas de nom.");
                    erreur_nomPlante();
                } else {
                    Log.d("debug", "La plante a un nom.");
                    saveName_and_goToType(editText_nomPlante.getText().toString());
                }

            }
        });


    }

    public void saveName_and_goToType(String nomPlante){
        NomPlante = nomPlante;
        Intent intent_type_selection = new Intent(this, MaNouvellePlante1Activity.class);
        intent_type_selection.putExtra("NomPlante", NomPlante);
        Log.d("debug", "Le nom de la plante est "+nomPlante+".");

        startActivity(intent_type_selection);

    }

    public void erreur_nomPlante(){
        TextView textView_erreur_nomPlante = (TextView) findViewById(R.id.ereur_nomPlante);
        textView_erreur_nomPlante.setVisibility(View.VISIBLE);

    }
}