package com.example.etu26132.e_night;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Soiree;

/**
 * Created by Degraux on 27-11-15.
 */
public class SoireeAdapter extends BaseAdapter {

    private ArrayList<Soiree> listSoiree;
    private Context mContext;
    private LayoutInflater mInflater;

    public SoireeAdapter(Context context, ArrayList<Soiree> listSoiree) {
        mContext = context;
        this.listSoiree = listSoiree;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount(){
        return listSoiree.size();
    }

    public Object getItem(int position){
        return listSoiree.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.list_layout, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        //(2) : Récupération des TextView de notre layout
        TextView nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView lieu = (TextView)layoutItem.findViewById(R.id.lieu);

        //(3) : Renseignement des valeurs
        nom.setText(listSoiree.get(position).getNom());
        lieu.setText(listSoiree.get(position).getVille());

        //On retourne l'item créé.
        return layoutItem;
    }
}
