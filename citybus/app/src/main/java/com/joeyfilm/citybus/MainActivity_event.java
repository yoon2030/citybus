package com.joeyfilm.citybus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

public class MainActivity_event extends AppCompatActivity {
    TextView textView4;
    TextView back;
    ImageButton mypage,announcement,like,change,ticket;
    //public static Activity _Main_Activity;


//    private RecyclerView recyclerView;
//    private List<Person> personList = new ArrayList<>();
//    private Myadapter adapter;
    private ListView listView;
    private eventListViewAdapter listViewAdapter;

    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        listView = (ListView)findViewById(R.id.eventlist1);
        listViewAdapter = new eventListViewAdapter();
        listView.setAdapter(listViewAdapter);

        addItem();

//        // 액션바 뒤로가기 버튼
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        textView4 = (TextView)findViewById(R.id.textView4);

        textView4.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //여기에 이벤트를 적어주세요
                        Intent intent = new Intent(getApplicationContext(), Main2Activity_Event.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        overridePendingTransition(0, 0);
                        finish();

                    }
                }
        );

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
        mReference.child("notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int a = 1; // 그대로
                String str = "notice"; // 바뀌는 숫자를 제외한 이름
                listViewAdapter.clearItem();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String date = dataSnapshot.child(str + a).child("date").getValue(String.class);
                    String title = dataSnapshot.child(str + a).child("title").getValue(String.class); // 밑에 들어가는 개수만
                    listViewAdapter.addItem(date,title);
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
