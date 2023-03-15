package com.example.translationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class KeyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);

        Button saveKey = (Button) findViewById(R.id.btn_saveKey);
        EditText key = (EditText) findViewById(R.id.editTextKey);
        TextView usage = (TextView) findViewById(R.id.usageKey);

        Intent intent = getIntent();
        String myKey = intent.getStringExtra("key");

        key.setText(myKey);

        saveKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("key", key.getText().toString());

                startActivity(i);
            }
        });

        // Requete HTTP GET recuperant les données
        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization", "DeepL-Auth-Key " + myKey)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    usage.setText("Nombre de caractères utilisés : " + response.getString("character_count") + "\n" + "Nombre de caractères maximum : " + response.getString("character_limit"));
                    System.out.println(response.getString("character_count") + "\n" + "Nombre de caractères maximum : " + response.getString("character_limit"));
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