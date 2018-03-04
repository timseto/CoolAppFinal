package com.timboat.coolapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Timmy on 2018-03-04.
 */

public class adapterDistance extends ArrayAdapter<Item> {

    public adapterDistance(@NonNull Context context, ArrayList<Item> values) {
        super(context, R.layout.distance_layout, values);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.distance_layout,parent, false);

        Item current = getItem(position);
        String item_name = current.getName();
        int item_type = current.getType();
        int item_price = current.getPrice();
        String item_store = current.getStore();


        TextView name = (TextView) theView.findViewById(R.id.store_name);
        name.setText(item_name);

        return theView;
    }
    public void update()
    {
        this.notifyDataSetChanged();
    }
}
