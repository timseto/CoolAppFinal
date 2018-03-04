package com.timboat.coolapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by walterraftus on 2018-03-03.
 */

public class WebUtility {

    public static class Store{
        String name;
        String [][] items;

        public Store(String name0, String[][]items0){
            name = name0;
            items = items0;
        }
    }

    public static String[][] wishList = null;
    public static Store[] stores = null;


    public static class getListTask extends AsyncTask<URL, Void, Void> {

        @Override
        protected Void doInBackground(URL... urls) {
            String[][] list = null;

            if(urls[0] == null)
                return null;

            try {

                String raw = getResultFromHTTPRequest(urls[0]);
                parseJSON(raw);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

    }

    public static class editWishListTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {

            String urlString = params[0];
            String data = params[1];

            OutputStream out = null;
            try {

                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            } catch (Exception e) {

                System.out.println(e.getMessage());

            }

            return null;

        }
    }

    public static String getResultFromHTTPRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            InputStream in = connection.getInputStream();
            Scanner sc = new Scanner(in);

            String results = "";

            while(sc.hasNext())
                results += sc.next();

            sc.close();
            return results;

        } finally{
            connection.disconnect();
        }
    }

    public static void parseJSON(String raw) throws JSONException {

        try {
            wishList = parseWishlistJSON(raw);
            stores = parseStoresJSON(raw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String[][] parseWishlistJSON(String raw) throws JSONException {

        JSONObject full = new JSONObject(raw);
        JSONArray list = full.getJSONArray("wishlist");
        String[][] parsed = new String[list.length()][2];

        for(int i = 0; i < parsed.length; i++) {
            parsed[i][0] = list.getJSONObject(i).getString("name");
            parsed[i][1] = list.getJSONObject(i).getString("type");
        }

        return parsed;
    }

    public static Store[] parseStoresJSON(String raw) throws JSONException {

        JSONObject full = new JSONObject(raw);
        JSONArray list = full.getJSONArray("stores");
        Store[] parsed = new Store[list.length()];

        for(int i = 0; i < parsed.length; i++) {
            JSONObject store = list.getJSONObject(i);
            JSONArray items = store.getJSONArray("items");
            String[][] pItems = new String[items.length()][2];
            for(int j = 0; j < pItems.length; j++) {
                pItems[j][0] = items.getJSONObject(j).getString("name");
                pItems[j][1] = items.getJSONObject(j).getString("type");
            }
            parsed[i] = new Store(store.getString("name"),pItems);
        }

        return parsed;
    }

}