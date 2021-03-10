package com.joeyfilm.citybus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class confirm extends AppCompatActivity {
    ImageButton mypage,announcement,like,change,ticket;
    TextView back;
    private ListView listView;
    private conListViewAdapter listViewAdapter;

    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        listView = (ListView)findViewById(R.id.list3);
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
    }

    public void addItem() {
        mReference.child("user").child("001").child("confirm").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int a = 1; //
                String str = "confirm"; // 바뀌는 숫자를 제외한 이름
                listViewAdapter.clearItem();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String start = dataSnapshot.child(str + a).child("start").getValue(String.class);
                    String stop = dataSnapshot.child(str + a).child("stop").getValue(String.class);
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
