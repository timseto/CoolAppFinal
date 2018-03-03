package com.timboat.coolapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends android.support.v4.app.Fragment {

    TextView textView;
    ListView listView;
    TextView wishListTitle;


    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.page_fragment_layout, container,false);
        textView =(TextView) view.findViewById(R.id.text_view);
        listView = (ListView) view.findViewById(R.id.item_list);
        wishListTitle = (TextView) view.findViewById(R.id.wish_list_title);

        if(bundle.getInt("count") == 1)
        {
            textView.setVisibility(View.VISIBLE);
            String message = Integer.toString(bundle.getInt("count"));
            textView.setText("This is the " + message +"Swipe View..");
        }
        else if(bundle.getInt("count") == 2)
        {
            listView.setVisibility(View.VISIBLE);
            wishListTitle.setVisibility(View.VISIBLE);
        }
        return view;
    }

}
