package com.joeyfilm.citybus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class payment extends AppCompatActivity {

    private TextView day, back;
    String startName,endName;
    TextView start,end;
    private String str;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyy/MM/dd");
    Button card1,card2,card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        day = (TextView) findViewById(R.id.day);
        back = (TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Date date = new Date();
        str = mFormat.format(date);
        startName = intent.getStringExtra("startName");
        endName =intent.getStringExtra("endName");
        start =(TextView)findViewById(R.id.startName);
        end = (TextView)findViewById(R.id.endName);
        start.setText(startName);
        end.setText(endName);

        day.setText(str);

        card1 = (Button)findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),payment2.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

                finish();
            }
        });
        card2 = (Button)findViewById(R.id.card2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"아직 미구현되었습니다.",Toast.LENGTH_LONG).show();
            }
        });
        card3 = (Button)findViewById(R.id.card3);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"아직 미구현되었습니다.",Toast.LENGTH_LONG).show();
            }
        });
    }

}
