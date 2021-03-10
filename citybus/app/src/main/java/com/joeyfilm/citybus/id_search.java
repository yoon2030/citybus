package com.joeyfilm.citybus;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class id_search extends AppCompatActivity {
    Button button1;
    TextView back;

    EditText editText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search);
        editText1 = (EditText)findViewById(R.id.Sample1);
        button1=(Button)findViewById(R.id.button1);
        button1.setEnabled(false);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                button1.setBackgroundColor(Color.parseColor("#d6d5d5"));
                button1.setTextColor(Color.parseColor("#000000"));
                button1.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText1.getText().toString().equals("")){
                    button1.setBackgroundColor(Color.parseColor("#d6d5d5"));
                    button1.setTextColor(Color.parseColor("#000000"));
                    button1.setEnabled(false);
                }
                else {
                    button1.setBackgroundColor(Color.parseColor("#513ba1"));
                    button1.setTextColor(Color.parseColor("#FFFFFF"));
                    button1.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), com.joeyfilm.citybus.id_search_result.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        back=(TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
