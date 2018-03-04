package com.timboat.coolapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Login extends AppCompatActivity {

    private Button loginButton, signUpButton;
    private EditText username, password;
    private LinearLayout entireLayout;
    private Animation fadeIn, fadeOut;
    private ImageView logoPicture;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setEnterTransition(null);

        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out_activity);
        entireLayout = (LinearLayout) findViewById(R.id.login_layout);
        entireLayout.setVisibility(View.INVISIBLE);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_activity);
        entireLayout.startAnimation(fadeIn);
        entireLayout.setVisibility(View.VISIBLE);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome();
            }
        });

        signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });

        username = (EditText) findViewById(R.id.username_textbox);
        username.setTextColor(Color.BLACK);

        password = (EditText) findViewById(R.id.password_textbox);
        password.setTextColor(Color.BLACK);

    }

    private void openSignUp() {
        entireLayout.startAnimation(fadeOut);
        Intent a = new Intent(this,SignUp.class);
        startActivity(a);
        overridePendingTransition(0,0);
    }

    private void openHome() {
        entireLayout.startAnimation(fadeOut);
        Intent a = new Intent(this, Home.class);
        startActivity(a);
        overridePendingTransition(0, 0);
    }
}
