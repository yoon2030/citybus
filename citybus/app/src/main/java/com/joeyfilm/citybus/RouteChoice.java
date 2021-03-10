package com.joeyfilm.citybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

public class RouteChoice extends AppCompatActivity {
    private ODsayService odsayService;
    private JSONObject jsonObject;
    String busNum,busID,busStaion;
    TextView stationName,busid;
    String[] busRoute;
    myAdapter2 adapter;
    TextView back;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_choice);

        Intent intent = getIntent();
        busNum = intent.getStringExtra("busNum");
        busID = intent.getStringExtra("busID");
        busStaion = intent.getStringExtra("busStaion");
        stationName = (TextView)findViewById(R.id.stationName);
        busid = (TextView)findViewById(R.id.busid);
        stationName.setText(busStaion);
        busid.setText(busNum);
        init();
        odsayService.requestBusLaneDetail(busID, onResultCallbackListener);
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void init(){
        odsayService = ODsayService.init(RouteChoice.this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);
    }
    class myAdapter2 extends BaseAdapter {
        @Override
        public int getCount() {
            return busRoute.length;
        }

        @Override
        public Object getItem(int position) {
            return busRoute[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = new TextView(getApplicationContext());
                view.setText(busRoute[position]);
                view.setTextSize(29.0f);
                return view;
        }
    }
    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            jsonObject = oDsayData.getJson();
            String jsonString = null;
            JSONArray result = oDsayData.getJson().optJSONObject("result").optJSONArray("station");
            JSONObject station;
            String search;
            int num=0;
            for(int i=0;i<result.length();i++) {
                station = result.optJSONObject(i);
                search = station.optString("stationName");
                if(search.equals(busStaion)){
                    num =i;
                    break;
                }
            }
            int j=0;
            busRoute=new String[result.length()-num];
            for(int i=num+1;i<result.length();i++){
                station = result.optJSONObject(i);
                busRoute[j]= station.optString("stationName");
                j++;
            }
            listview=(ListView)findViewById(R.id.listview);
            List<String> list = new ArrayList<>();
            adapter = new myAdapter2();
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getApplicationContext(), payment.class);
                    String endName=busRoute[position];
                    intent.putExtra("startName",busStaion);
                    intent.putExtra("endName",endName);
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
