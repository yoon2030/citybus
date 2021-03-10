package com.joeyfilm.citybus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Paylist extends AppCompatActivity {
    TextView back;

    private ListView listView;
    private conListViewAdapter listViewAdapter;

    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paylist);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_paylist);

        listView = (ListView)findViewById(R.id.list4);
        listViewAdapter = new conListViewAdapter();
        listView.setAdapter(listViewAdapter);

        addItem();

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addItem() {
        mReference.child("user").child("001").child("paylist").addValueEventListener(new ValueEventListener() { // 차일드 변경
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int a = 1; //그대로
                String str = "paylist"; //바뀌는 숫자를 제외한 이름
                listViewAdapter.clearItem();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String start = dataSnapshot.child(str + a).child("start").getValue(String.class); //이줄을 필요한 만큼
                    String stop = dataSnapshot.child(str + a).child("stop").getValue(String.class); //밑에 들어가는 개수만
                    String date = dataSnapshot.child(str + a).child("date").getValue(String.class);
                    String charge = dataSnapshot.child(str + a).child("charge").getValue(String.class);
                    listViewAdapter.addItem(start,stop,date,charge);
                    a++;
                }

                listViewAdapter.notifyDataSetChanged(); //리스트뷰 갱신
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
