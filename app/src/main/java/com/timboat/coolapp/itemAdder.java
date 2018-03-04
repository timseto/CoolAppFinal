package com.timboat.coolapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class itemAdder extends AppCompatActivity {

    int type;
    FloatingActionButton bubbleClothes,bubbleTech,bubbleGrocery;
    Animation select,reselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_adder);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        type = getIntent().getIntExtra("typeInt",0);
        select = AnimationUtils.loadAnimation(this,R.anim.select);
        reselect = AnimationUtils.loadAnimation(this,R.anim.reselect);

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
}
