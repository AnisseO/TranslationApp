package com.example.translationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    List<Traductions> liste = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        String textInitial = intent.getStringExtra("textInitial");
        String textTranslate = intent.getStringExtra("textTranslate");

        System.out.println(textInitial + "Hist");
        System.out.println(textTranslate + "Hist");

        liste.add(new Traductions(textInitial, textTranslate));

        ListView myListView = findViewById(R.id.listTrad);
        myListView.setAdapter(new TradAdapter(this, liste));

    }
}