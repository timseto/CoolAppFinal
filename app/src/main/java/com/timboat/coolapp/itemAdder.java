package com.timboat.coolapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class itemAdder extends AppCompatActivity {

    String type;
    FloatingActionButton bubbleClothes,bubbleTech,bubbleGrocery;
    Animation select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_adder);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        type = getIntent().getStringExtra("typeString");
        select = AnimationUtils.loadAnimation(this,R.anim.select);

        bubbleClothes = (FloatingActionButton) findViewById(R.id.bubble_clothes);
        bubbleTech = (FloatingActionButton) findViewById(R.id.bubble_tech);
        bubbleGrocery = (FloatingActionButton)findViewById(R.id.bubble_grocery);

        if(type == "clothes")
        {
            bubbleClothes.setVisibility(View.INVISIBLE);
        }
        else if(type == "tech")
        {
            bubbleTech.startAnimation(select);
        }
        else if(type == "grocery")
        {
            bubbleGrocery.startAnimation(select);
        }
    }
}
