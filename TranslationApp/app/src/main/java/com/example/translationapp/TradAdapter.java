package com.example.translationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TradAdapter extends BaseAdapter {

    private Context context;
    private List<Traductions> listTrad;
    private LayoutInflater inflater;


    public  TradAdapter(Context context, List<Traductions> listTrad){
        this.context = context;
        this.listTrad = listTrad;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listTrad.size();
    }

    @Override
    public Traductions getItem(int position) {
        return listTrad.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_item, null);

        //Recuperation à propos de l'item
        Traductions currentTrad = getItem(position);
        String itemTradInitial = currentTrad.getTextToTrad();
        String itemTranslate = currentTrad.getTextTranslate();

        TextView itemTrad = convertView.findViewById(R.id.TextToTrad);
        itemTrad.setText(itemTradInitial);

        TextView itemTraduit = convertView.findViewById(R.id.TextTrad);
        itemTraduit.setText(itemTranslate);

        return convertView;
    }
}
