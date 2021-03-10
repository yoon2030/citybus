package com.joeyfilm.citybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class stationchoice extends AppCompatActivity {
    double mLatitude;
    double mLongitude;
    private ODsayService odsayService;
    private JSONObject jsonObject;
    TextView back;
    String[] busStop;
    String[] stationID;
    double[] x;
    double[] y;
    public static Context context_map;
    public double Curx=0;
    public double Cury=0;
    public static String s="ss";
    myAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationchoice);
        context_map=this;
        Intent intent = getIntent();
        mLatitude = intent.getExtras().getDouble("mLatitude");
        mLongitude = intent.getExtras().getDouble("mLongitude");
        init();
        odsayService.requestPointSearch(Double.toString(mLongitude), Double.toString(mLatitude), "250", "1", onResultCallbackListener);

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    class myAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return busStop.length;
        }

        @Override
        public Object getItem(int position) {
            return busStop[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = new TextView(getApplicationContext());
            view.setText(busStop[position]);
            view.setTextSize(29.0f);
            return view;
        }
    }
    private void init(){
        odsayService = ODsayService.init(stationchoice.this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);
    }
    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            jsonObject = oDsayData.getJson();
            String jsonString = null;
                JSONArray result = oDsayData.getJson().optJSONObject("result").optJSONArray("station");
                JSONObject station;
                busStop = new String[result.length()];
                stationID = new String[result.length()];
                x = new double[result.length()];
                y = new double[result.length()];
                for(int i=0;i<result.length();i++) {
                    station = result.optJSONObject(i);
                    busStop[i] = station.optString("stationName");
                    stationID[i] = station.optString("stationID");
                    x[i] = station.optDouble("x");
                    y[i] = station.optDouble("y");
                }
            ListView listview=(ListView)findViewById(R.id.listview);
            List<String> list = new ArrayList<>();
            adapter = new myAdapter();
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = busStop[position];
                    String ID =stationID[position];
                    Curx = x[position];
                    Cury = y[position];
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("busStation",item);
                    intent.putExtra("stationID",ID);
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onError(int i, String errorMessage, API api) {
            if (api == API.BUS_STATION_INFO) {}
        }
    };
}
