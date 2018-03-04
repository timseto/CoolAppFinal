package com.timboat.coolapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Timmy on 2018-03-03.
 */

public class adapterList extends ArrayAdapter<Item> {

    public adapterList(@NonNull Context context, ArrayList<Item> values) {
        super(context, R.layout.item_layout, values);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.item_layout,parent, false);

        Item current = getItem(position);
        String item_name = current.getName();
        int item_type = current.getType();
        int item_price = current.getPrice();
        String item_store = current.getStore();


        TextView name = (TextView) theView.findViewById(R.id.name);
        name.setText(item_name);
        TextView price = (TextView) theView.findViewById(R.id.price);
        price.setText("Date added: 03/04/18");
        TextView store = (TextView) theView.findViewById(R.id.store);
        store.setText(item_store);

        if(item_type == 0){
            FloatingActionButton itemPicture = (FloatingActionButton) theView.findViewById(R.id.bubble);
            itemPicture.setImageResource(R.drawable.ic_action_tshirt);
            return theView;
        }
        else if(item_type == 1){
            FloatingActionButton itemPicture = (FloatingActionButton) theView.findViewById(R.id.bubble);
            itemPicture.setImageResource(R.drawable.ic_action_chip);
            return theView;
        }
        else if(item_type == 2){
            FloatingActionButton itemPicture = (FloatingActionButton) theView.findViewById(R.id.bubble);
            itemPicture.setImageResource(R.drawable.ic_action_name);
            return theView;
        }
        return theView;
    }
    public void update()
    {
        this.notifyDataSetChanged();
    }
}
