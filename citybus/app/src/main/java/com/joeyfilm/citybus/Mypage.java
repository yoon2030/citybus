package com.joeyfilm.citybus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Mypage extends AppCompatActivity {

    Button button5;
    Button button4;
    Button button6,setting;
    TextView button3,button12;
    ImageButton mypage,announcement,like,change,ticket;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mAuth = FirebaseAuth.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mypage);
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Paylist.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sign_up.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QNA.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settin_Main.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        button3 = (TextView) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ticket = (ImageButton)findViewById(R.id.ticket);
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

                finish();
            }
        });
        announcement = (ImageButton) findViewById(R.id.announcement);
        announcement.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity_event.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

                finish();
            }
        });

        like = (ImageButton) findViewById(R.id.like);
        like.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Favorites.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

                finish();
            }
        });

        change = (ImageButton) findViewById(R.id.chage);
        change.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),confirm.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

                finish();
            }
        });
        mypage = (ImageButton) findViewById(R.id.mypage);
        mypage.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Mypage.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

                finish();
            }
        });

        button12 = (TextView) findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finishAffinity();
                Intent intent=new Intent(getApplicationContext(),LoginMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

    }

}
