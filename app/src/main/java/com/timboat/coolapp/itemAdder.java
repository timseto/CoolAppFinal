package com.timboat.coolapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class itemAdder extends AppCompatActivity {

    public final String LISTLINK = "http://walterraftus.ca-central-1.elasticbeanstalk.com";
    int type;
    FloatingActionButton bubbleClothes,bubbleTech,bubbleGrocery;
    Animation select,reselect;
    Button add, done;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_adder);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        type = getIntent().getIntExtra("typeInt",0);
        select = AnimationUtils.loadAnimation(this,R.anim.select);
        reselect = AnimationUtils.loadAnimation(this,R.anim.reselect);
        add = (Button) findViewById(R.id.add);
        done = (Button) findViewById(R.id.done);
        name = (EditText) findViewById(R.id.edit1);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String another = name.getText().toString();
                if (another == null || another.equals(""))
                    return;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, LISTLINK,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                name.setText("");
                                try {
                                    new WebUtility.getListTask().execute(new URL(LISTLINK));
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(itemAdder.this, "Error...", Toast.LENGTH_SHORT);
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", another);
                        params.put("type", type + "");
                        return params;
                    }
                };

                Singleton.getInstance(itemAdder.this).addToRequestQueue(stringRequest);
            }

        });

        done.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                openHome();
            }
        });



        bubbleClothes = (FloatingActionButton) findViewById(R.id.bubble_clothes);
        bubbleTech = (FloatingActionButton) findViewById(R.id.bubble_tech);
        bubbleGrocery = (FloatingActionButton)findViewById(R.id.bubble_grocery);

        if(type == 0)
        {
            bubbleGrocery.startAnimation(select);
            bubbleTech.startAnimation(select);
        }
        else if(type == 1)
        {
            bubbleGrocery.startAnimation(select);
            bubbleClothes.startAnimation(select);
        }
        else if(type == 2)
        {
            bubbleClothes.startAnimation(select);
            bubbleTech.startAnimation(select);
        }


        bubbleClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == 0)return;
                else if(type == 1){
                    bubbleTech.startAnimation(select);
                    bubbleClothes.startAnimation(reselect);
                    type = 0;
                    return;
                }
                else {
                    bubbleGrocery.startAnimation(select);
                    bubbleClothes.startAnimation(reselect);
                    type = 0;
                }
            }
        });

        bubbleTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == 1)return;
                else if(type == 2){
                    bubbleGrocery.startAnimation(select);
                    bubbleTech.startAnimation(reselect);
                    type = 1;
                    return;
                }
                else {
                    bubbleClothes.startAnimation(select);
                    bubbleTech.startAnimation(reselect);
                    type = 1;
                }
            }
        });

        bubbleGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == 2)return;
                else if(type == 1){
                    bubbleTech.startAnimation(select);
                    bubbleGrocery.startAnimation(reselect);
                    type = 2;
                    return;
                }
                else {
                    bubbleClothes.startAnimation(select);
                    bubbleGrocery.startAnimation(reselect);
                    type = 2;
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void openHome() {
        Intent a = new Intent(this, Home.class);
        startActivity(a);
    }
}
