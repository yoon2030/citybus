package com.joeyfilm.citybus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Settin_Main extends AppCompatActivity {

    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);

        Button button7 = (Button)findViewById(R.id.button7); /*페이지 전환버튼*/
        Button button8 = (Button)findViewById(R.id.button8);
        Button button4 = (Button)findViewById(R.id.button4);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button5 = (Button)findViewById(R.id.button5);
        Button button6 = (Button)findViewById(R.id.button6);

        TextView userEmail = (TextView) findViewById(R.id.textView);
        Button signout = (Button)findViewById(R.id.button9);
        mAuth = FirebaseAuth.getInstance();
        userEmail.setText(mAuth.getCurrentUser().getEmail());

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),setting3.class);
                startActivityForResult(intent,sub);//액티비티 띄우기
                overridePendingTransition(0, 0);

            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),setting5.class);
                startActivityForResult(intent,sub);//액티비티 띄우기
                overridePendingTransition(0, 0);

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),setting2.class);
                startActivityForResult(intent,sub);//액티비티 띄우기
                overridePendingTransition(0, 0);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getCurrentUser().delete();
                finishAffinity();
                Intent intent=new Intent(getApplicationContext(),LoginMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"아직 미구현되었습니다.",Toast.LENGTH_LONG).show();
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
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