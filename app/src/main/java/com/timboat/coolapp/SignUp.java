package com.timboat.coolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    private Button signUp;
    private ImageButton backButton;
    private EditText email, username, password;
    private TextView alreadyHaveAccount;
    LinearLayout entire;
    Animation fadeOut, fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out_activity);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_activity);
        entire = (LinearLayout) findViewById(R.id.sign_up_layout);
        entire.startAnimation(fadeIn);

        signUp = (Button) findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });

        alreadyHaveAccount = (TextView) findViewById(R.id.already_have_account);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });

    }

    private void openLogin() {
        entire.startAnimation(fadeOut);
        Intent a = new Intent(this, Login.class);
        startActivity(a);
        overridePendingTransition(0, 0);
    }
}
