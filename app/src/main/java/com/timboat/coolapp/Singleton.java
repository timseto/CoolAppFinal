package com.timboat.coolapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class Singleton {
    public static Singleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private Singleton(Context c){
        context = c;
        requestQueue = getRequestQueue();
    }

    public static synchronized Singleton getInstance(Context c){
        if(instance == null) instance = new Singleton(c);
        return  instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null) requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    public void addToRequestQueue(Request request){
        requestQueue.add(request);
    }

}