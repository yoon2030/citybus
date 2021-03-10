package com.joeyfilm.citybus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BusChoose extends AppCompatActivity {
    private ODsayService odsayService;
    TextView back, stationName;
    private JSONObject jsonObject;
    String busStation,stationID;
    String[] busNum;
    String[] busID;
    myAdapter1 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buschoose);

        stationName =findViewById(R.id.stationName);
        Intent intent = getIntent();
        busStation = intent.getStringExtra("busStation");
        stationID = intent.getStringExtra("stationID");
        if(busStation!=null) {
            stationName.setText(busStation);
        }
        init();
        odsayService.requestBusStationInfo(stationID, onResultCallbackListener);

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    class myAdapter1 extends BaseAdapter {
        @Override
        public int getCount() {
            return busNum.length;
        }

        @Override
        public Object getItem(int position) {
            return busNum[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = new TextView(getApplicationContext());
            if(busNum[position]==null)return view;
            view.setText(busNum[position]);
            view.setTextSize(29.0f);
            return view;
        }
    }
    private void init(){
        odsayService = ODsayService.init(BusChoose.this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);
    }
    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            jsonObject = oDsayData.getJson();
            String jsonString = null;
            JSONArray result = oDsayData.getJson().optJSONObject("result").optJSONArray("lane");
            JSONObject station;
            busNum = new String[result.length()];
            busID = new String[result.length()];
            for(int i=0;i<result.length();i++) {
                station = result.optJSONObject(i);
                busNum[i] = station.optString("busNo");
                busID[i] = station.optString("busID");
            }
            ListView listview=(ListView)findViewById(R.id.listview);
            List<String> list = new ArrayList<>();
            adapter = new myAdapter1();
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String busNUM = busNum[position];
                    String busId = busID[position];
                    Intent intent=new Intent(getApplicationContext(), RouteChoice.class);
                    intent.putExtra("busNum",busNUM);
                    intent.putExtra("busID",busId);
                    intent.putExtra("busStaion",busStation);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                }
            });
        }

        @Override
        public void onError(int i, String errorMessage, API api) {
            if (api == API.BUS_STATION_INFO) {}
        }
    };
}
