package com.example.translationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.example.translationapp.Language;

// build.gradle -> compileSdk/targetSdk to 33
// settings.gradle -> ajouter jcenter() pour AndroidNetworking
// https://www.baeldung.com/java-org-json
// https://processing.org/reference/JSONObject_getJSONArray_.html
// https://www.javatpoint.com/android-spinner-example


public class MainActivity extends AppCompatActivity {

    Spinner spinnToTrad;
    EditText textToTrad;
    TextView textTranslate;
    TextView language_detected;
    String myKey = "e2a9d3b1-f105-9da4-d1a3-7509e1f35000:fx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(this);

        ImageButton btn_history = (ImageButton) findViewById(R.id.imageButtonHistory);
        ImageButton btn_key = (ImageButton) findViewById(R.id.imageButtonKey);

        Intent intent = getIntent();
        String keyAdd = intent.getStringExtra("key");

        //Verifie si changement de clé
        if(keyAdd == null){
            System.out.println(myKey);
        }
        else {
            myKey = keyAdd;
            System.out.println(myKey);
        }

        textToTrad = (EditText) findViewById(R.id.editTextToTranslat);
        textTranslate = (TextView) findViewById(R.id.textView);
        spinnToTrad = (Spinner) findViewById(R.id.spinToTranslate);
        language_detected = findViewById(R.id.TVlanguage_detected);
        Button save = (Button) findViewById(R.id.btn_save);

        getLanguage();

        // Traduction du texte au fur et à mesure que l'on tape
        textToTrad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                translate();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(textToTrad == null){
                    textTranslate.setText("");
                    language_detected.setText(" ");
                }

            }
        });

        //Bouton sauvegarde de la traduction
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                intent.putExtra("textInitial", textToTrad.getText().toString());
                intent.putExtra("textTranslate", textTranslate.getText().toString());

                System.out.println(textToTrad.getText().toString());
                System.out.println(textTranslate.getText().toString());
                startActivity(intent);
            }
        });

        //Bouton ouvrant une nouvelle vue permettant de changer de clé
        btn_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), KeyActivity.class );
                i.putExtra("key", myKey);
                startActivity(i);
            }
        });

        //Bouton ouvrant la vue de l'historique
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });
    }


    // Recuperation de la liste des languages disponibles
    public void getLanguage(){

        // Requete HTTP GET recuperant la liste des langues disponibles
        AndroidNetworking.get("https://api-free.deepl.com/v2/languages")
                .addHeaders("Authorization", "DeepL-Auth-Key " + myKey)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //On crée une liste de language (avec nom et langue)
                            ArrayList<Language> lang = new ArrayList<>();

                            ArrayList country = new ArrayList<>();

                            //On les ajoute un par un dans cette liste
                            for (int i = 0; i < response.length(); i++){

                                JSONObject langue = response.getJSONObject(i);
                                lang.add(new Language(langue.getString("language"),langue.getString("name")));

                                System.out.println(lang);
                            }
                            // On convertit la liste en ArrayAdapter
                            ArrayAdapter<Language> listeLangue = new ArrayAdapter<Language>(MainActivity.this, android.R.layout.simple_spinner_item,lang);

                            //Puis on l'affiche dans le spinner
                            spinnToTrad.setAdapter(listeLangue);

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("ça marche PAS !");
                    }

                });
    }

    // Traduction de notre texte
    public void translate(){

        //Recuperation de la langue de traduction choisie et du texte à traduire
        Language langue = spinnToTrad.getCount() == 0 ? new Language("", "") : (Language) spinnToTrad.getSelectedItem();
        String langue_choisie = langue.getLanguage();
        String texte = textToTrad.getText().toString();

        // Requete HTTP POST
        AndroidNetworking.post("https://api-free.deepl.com/v2/translate")
                .addHeaders("Authorization", "DeepL-Auth-Key " + myKey)
                .addBodyParameter("text", texte)
                .addBodyParameter("target_lang", langue_choisie)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray traduction = response.getJSONArray("translations");
                            JSONObject trad = traduction.getJSONObject(0);

                            textTranslate.setText(trad.getString("text"));

                            //Toast.makeText(getApplicationContext(), "Langue détectée:" + trad.getString("detected_source_language"), Toast.LENGTH_SHORT).show();
                            language_detected.setText(trad.getString("detected_source_language"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

}