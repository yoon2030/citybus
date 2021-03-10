package com.joeyfilm.citybus;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class sign_up_1 extends AppCompatActivity {
    TextView back;
    Button button1;
    CheckBox checkBox1=null;
    CheckBox checkBox2=null;
    CheckBox checkBox3=null;
    CheckBox checkBox4=null;
    CheckBox checkBoxAll=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_1);
        button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), com.joeyfilm.citybus.sign_up_2.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

                overridePendingTransition(0, 0);

            }
        });
        checkBox1=(CheckBox)findViewById(R.id.check1);
        checkBox2=(CheckBox)findViewById(R.id.check2);
        checkBox3=(CheckBox)findViewById(R.id.check3);
        checkBox4=(CheckBox)findViewById(R.id.check4);
        checkBoxAll=(CheckBox)findViewById(R.id.checkAll);
        checkBoxAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxAll.isChecked()) {
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(true);
                    checkBox4.setChecked(true);
                }else{
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                }
            }
        });
        checkBox1.setOnClickListener(onCheckBoxClickListener);
        checkBox2.setOnClickListener(onCheckBoxClickListener);
        checkBox3.setOnClickListener(onCheckBoxClickListener);
        checkBox4.setOnClickListener(onCheckBoxClickListener);
        back=(TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private View.OnClickListener onCheckBoxClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isAllChecked()){
                checkBoxAll.setChecked(true);
            }else{
                checkBoxAll.setChecked(false);
            }
        }
    };
    private boolean isAllChecked(){
        return (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked()&&checkBox4.isChecked()) ?  true :  false;
    }
}

