package com.joeyfilm.citybus;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class LoginMain extends AppCompatActivity {
    TextView text;
    EditText editText1,editText2;
    Button button1;
    TextView id_search;
    TextView pw_search;
    TextView sign_up;

    EditText email,pw;
    FirebaseAuth mAuth;

    public static ArrayList<Activity> actList = new ArrayList<Activity>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        button1=(Button)findViewById(R.id.button1);
        button1.setEnabled(false);
       editText1=(EditText)findViewById(R.id.Sample1);
       editText2=(EditText)findViewById(R.id.Sample2);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.Sample1);
        pw = (EditText) findViewById(R.id.Sample2);

        editText2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());


       editText2.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(editText1.getText().toString().equals("")||editText2.getText().toString().equals("")) {
                   button1.setBackgroundColor(Color.parseColor("#d6d5d5"));
                   button1.setTextColor(Color.parseColor("#000000"));
                   button1.setEnabled(false);
               }
           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(editText1.getText().toString().equals("")||editText2.getText().toString().equals("")) {
                   button1.setBackgroundColor(Color.parseColor("#d6d5d5"));
                   button1.setTextColor(Color.parseColor("#000000"));
                   button1.setEnabled(false);

               }
               else{
                   button1.setEnabled(true);
                   button1.setBackgroundColor(Color.parseColor("#FF5A86E8"));
                   button1.setTextColor(Color.parseColor("#FFFFFF"));
               }
           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });

        id_search=(TextView) findViewById(R.id.id_search);
        id_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginMain.this, "미구현", Toast.LENGTH_SHORT).show();
            }
        });
        pw_search=(TextView)findViewById(R.id.pw_search);
        pw_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),pw_search.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        sign_up=(TextView)findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), com.joeyfilm.citybus.sign_up_4.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        text =(TextView) findViewById(R.id.textView4);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString().trim();
                String PW = pw.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(Email,PW)
                        .addOnCompleteListener(LoginMain.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(0, 0);

                                } else {
                                    Toast.makeText(LoginMain.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
}
