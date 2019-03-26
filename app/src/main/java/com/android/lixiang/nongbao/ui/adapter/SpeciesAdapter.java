package com.android.lixiang.nongbao.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.lixiang.nongbao.R;
import com.android.lixiang.nongbao.ui.Species;

import java.util.List;

public class SpeciesAdapter extends ArrayAdapter<Species> {

    private  int resourceId;

    public SpeciesAdapter(Context context, int textViewResourceId,List<Species> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Species species=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        TextView speciesname=(TextView) view.findViewById(R.id.species_name);

        speciesname.setText(species.getSpecies_name());
        return view;

    }
}
