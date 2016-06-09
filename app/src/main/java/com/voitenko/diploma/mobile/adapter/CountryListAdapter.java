package com.voitenko.diploma.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.voitenko.diploma.mobile.R;
import com.voitenko.diploma.mobile.model.Country;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CountryListAdapter extends ArrayAdapter<Country> {

    private final Context context;
    private final List<Country> objects;

    public CountryListAdapter(Context context, List<Country> objects) {
        super(context, R.layout.country_row, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.country_row, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        name.setText(objects.get(position).getName());
        return rowView;
    }

    public Country getItem(int position) {
        return objects.get(position);
    }

}
