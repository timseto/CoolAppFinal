package com.timboat.coolapp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Home extends FragmentActivity{

    public final String LISTLINK = "http://walterraftus.ca-central-1.elasticbeanstalk.com";
    ViewPager viewPager;
    ArrayList<Item> list = new ArrayList<Item>();
    adapterDistance theAdapter;
    ListView theListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        URL wishListURL = null;
        try {
            wishListURL = new URL(LISTLINK);

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

        ArrayList<Item> have = new ArrayList<Item>();
        if(WebUtility.stores == null || WebUtility.wishList == null)
            new WebUtility.getListTask().execute(wishListURL);
        while(WebUtility.stores == null || WebUtility.wishList == null);
        for(WebUtility.Store store: WebUtility.stores)
            for(String[] item : store.items){
                boolean want = false;
                for(String desired[] : WebUtility.wishList)
                    if(desired[0].equals(item[0])) want = true;
                if(want)//change to want
                    have.add(new Item(item[0],Integer.parseInt(item[1]),10,store.name));
            }

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);

        theAdapter = new adapterDistance(this, have);
        theListView = (ListView) findViewById(R.id.location_list);
        theListView.setAdapter(theAdapter);

    }
}
