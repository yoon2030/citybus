package com.joeyfilm.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends FragmentActivity implements AutoPermissionsListener {

    private String html = "";
    private Handler mHandler;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String ip = "192.168.0.4";            // IP 번호
    private int port = 9999;
    //////////////////////////////
    LocationManager locationManager;
    private SensorManager mySensorManager;
    private Sensor myGyroscope;
    private Sensor myAcceleromter;
    private double roll; // x
    private double pitch; // y
    private double yaw; // z
    private  double timestamp = 0.0;
    private double dt;
    private double rad_to_dgr = 180 / Math.PI;
    private static final float NS2S = 1.0f/1000000000.0f;
    double curmLatitude=0;  //현재 본인 위도
    double curmLongitude=0; //현재 본인 경도
    public double resultX = 0;
    public double resultY = 0;
    double time = 0;
    String s,s1,s2,s3,s4,s5;
    String ns,ns1,ns2,ns3,ns4,ns5;
    int num;
    int count=0;
    int[] arr=new int[200];
    int number;
    ImageButton peoplebtn,mypage,announcement,like,change;
    LinearLayout people;
    String busStation,stationID;
    String check;
    TextView startname;
    int updown = 0;
    Button lookup;
    double restime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        myGyroscope = mySensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        myAcceleromter = mySensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        startname =(TextView)findViewById(R.id.startname);
        peoplebtn = (ImageButton) findViewById(R.id.peoplebtn);
        people = (LinearLayout) findViewById(R.id.people);

        Intent intent = getIntent();
        busStation = intent.getStringExtra("busStation");
        stationID = intent.getStringExtra("stationID");
        if(busStation!=null) {
            startname.setText(busStation);
        }
        check =intent.getStringExtra("check");
        if(check!=null){
            time =3;
            resultX = ((stationchoice)stationchoice.context_map).Cury;
            resultY = ((stationchoice)stationchoice.context_map).Curx;
            Handler mHandler2 = new Handler();
            mHandler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mHandler1.sendEmptyMessage(0);
                }
            },5000);

        }
        peoplebtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updown == 0) {
                    people.setVisibility(View.VISIBLE);
                    peoplebtn.setRotation(180);
                    updown = 1;
                }
                else {
                    people.setVisibility(View.GONE);
                    peoplebtn.setRotation(0);
                    updown = 0;
                }
            }
        });
        startname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        mypage = (ImageButton) findViewById(R.id.mypage);
        mypage.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Mypage.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        announcement = (ImageButton) findViewById(R.id.announcement);
        announcement.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity_event.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        like = (ImageButton) findViewById(R.id.like);
        like.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Favorites.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        change = (ImageButton) findViewById(R.id.chage);
        change.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),confirm.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        lookup = (Button)findViewById(R.id.lookup);
        lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BusChoose.class);
                intent.putExtra("busStation",busStation);
                intent.putExtra("stationID",stationID);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        //////////////////////////////////////////////////////
        }
    Handler mHandler1 = new Handler(){
        public void handleMessage(Message msg){
            if(count==150 &&(resultX!=0)&&(resultY!=0)){
                time=time-0.5;
                Location locationA =new Location("point A");
                locationA.setLatitude(curmLatitude);
                locationA.setLongitude(curmLongitude);
                Location locationB = new Location("pointB");
                locationB.setLatitude(resultX);
                locationB.setLongitude(resultY);
                double distance = locationA.distanceTo(locationB);
                if(distance<10)return;
                if(time<0)return;
                distance = distance/1000;
                check();
                LayoutInflater inflater = getLayoutInflater();
                View toastDesign = inflater.inflate(R.layout.toastdesign, (ViewGroup)findViewById(R.id.toast_design_root));
                TextView text = toastDesign.findViewById(R.id.TextView_toast_design);

                if(number==1){
                    restime = distance/10;
                    restime = restime*60;
                    if(restime>time){
                        text.setText("시간 안에 정류장에 도착하지 못합니다. 다음 버스를 예매하세요.");
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(toastDesign);
                        toast.show();
                        return;

                    }
                    else if(restime<time){
                        double restime1 = distance/4;
                        restime1 = restime1*60;
                        if(restime1>time){
                            text.setText("계속 뛰어가셔야 시간안에 도착합니다.");
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(toastDesign);
                            toast.show();
                        }
                        else {
                            text.setText("걸어가셔도 시간 안에 도착합니다.");
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(toastDesign);
                            toast.show();
                        }
                    }
                }
                else if(number==0){
                    restime = distance/4;
                    restime = restime*60;
                    if(restime>time){
                        double restime2 = distance/10;
                        restime2 = restime2*60;
                        if(restime2<time){
                            text.setText("지금부터 뛰어가셔야 시간 안에 도착합니다.");
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(toastDesign);
                            toast.show();
                        }
                        else{
                            text.setText("시간 안에 정류장에 도착하지 못합니다. 다음 버스를 예매하세요.");
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(toastDesign);
                            toast.show();
                            return;
                        }
                    }
                    else {
                        text.setText("시간 안에 여유롭게 도착 하실 수 있습니다.");
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(toastDesign);
                        toast.show();
                    }
                }
                init();
            }
            else if(count==150)init();
            startLocationService();
            connect();
            mHandler1.sendEmptyMessageDelayed(0,200);
        }
    };
    void init(){
        for(int i=0;i<151;i++)arr[i]=0;
        count=0;
    }
    void check(){
        int a=0, b=0; //a 는 0의 갯수 b는 1의 갯수
        for(int i=0;i<150;i++){
            if(arr[i]==0)a++;
            else if(arr[i]==1)b++;
        }
        if(a<10)number=0;
        else if(a>=10)number=1;

    }
    SensorEventListener accListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            double AccX = sensorEvent.values[0];
            double AccY = sensorEvent.values[1];
            double AccZ = sensorEvent.values[2];
            String st1="0";
            if(AccX < 0){
                s =String.format("%.4f", AccX);
            } else {
                s =String.format("%.5f", AccX);
            }
            if(AccY < 0){
                s1 =String.format("%.4f", AccY*10);

            } else {
                s1 =String.format("%.5f", AccY);
            }
            if(AccZ < 0){
                s2 =String.format("%.4f", AccZ);

            } else {
                s2 =String.format("%.5f", AccZ);
            }
            if(s.length()!=7){
                if(s.length()>=7)ns=s.substring(0,7);
                else {
                    num = 7-s.length();
                    for(int i=0;i<num;i++){
                        s=s.concat(st1);
                    }
                    ns=s;
                }
            }
            else ns=s;
            if(s1.length()!=7){
                if(s1.length()>=7)ns1=s1.substring(0,7);
                else {
                    num = 7-s1.length();
                    for(int i=0;i<num;i++){
                        s1=s1.concat(st1);
                    }
                    ns1=s1;
                }
            }
            else ns1=s1;
            if(s2.length()!=7){
                if(s2.length()>=7)ns2=s2.substring(0,7);
                else {
                    num = 7-s2.length();
                    for(int i=0;i<num;i++){
                        s2=s2.concat(st1);
                    }
                    ns2=s2;
                }
            }
            else ns2=s2;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float gyroX = sensorEvent.values[0];
            float gyroY = sensorEvent.values[1];
            float gyroZ = sensorEvent.values[2];

            dt = (sensorEvent.timestamp - timestamp) * NS2S;
            timestamp = sensorEvent.timestamp;
            String st="0";
            if (dt - timestamp * NS2S != 0) {
                pitch = pitch + gyroY * dt;
                roll = roll + gyroX * dt;
                yaw = yaw + gyroZ * dt;

                if(roll * rad_to_dgr < 0){
                    s3 = String.format("%.4f", roll * rad_to_dgr);
                } else {
                    s3 =  String.format("%.5f", roll * rad_to_dgr);
                }
                if(pitch * rad_to_dgr < 0){
                    s4 =String.format("%.4f", pitch * rad_to_dgr);

                } else {
                    s4 = String.format("%.5f", pitch * rad_to_dgr);

                }

                if(yaw * rad_to_dgr < 0){
                    s5 =String.format("%.4f", yaw * rad_to_dgr);

                } else {
                    s5 =String.format("%.5f", yaw * rad_to_dgr);

                }
                if(s3.length()!=7){
                    if(s3.length()>=7)ns3=s3.substring(0,7);
                    else {
                        num = 7-s3.length();
                        for(int i=0;i<num;i++){
                            s3=s3.concat(st);
                        }
                        ns3=s3;
                    }
                }
                else ns3=s3;
                if(s4.length()!=7){
                    if(s4.length()>=7)ns4=s4.substring(0,7);
                    else {
                        num = 7-s4.length();
                        for(int i=0;i<num;i++){
                            s4=s4.concat(st);
                        }
                        ns4=s4;
                    }
                }
                else ns4=s4;
                if(s5.length()!=7){
                    if(s5.length()>=7)ns5=s5.substring(0,7);
                    else {
                        num = 7-s5.length();
                        for(int i=0;i<num;i++){
                            s5=s5.concat(st);
                        }
                        ns5=s5;
                    }
                }
                else ns5=s5;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    void connect(){
        mHandler = new Handler();
        Log.w("connect","연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
                // ip받기
                String newip = ip;          //사용자 ip 적기

                // 서버 접속
                try {
                    socket = new Socket(newip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 : ","안드로이드에서 서버로 연결요청");

                try {
                    dos = new DataOutputStream(socket.getOutputStream());   // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream());     // input에 받을꺼 넣어짐
                    ns=ns.concat(ns1);
                    ns=ns.concat(ns2);
                    ns=ns.concat(ns3);
                    ns=ns.concat(ns4);
                    String str=ns.concat(ns5);

                    dos.write(str.getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

                // 서버에서 계속 받아옴 - 한번은 문자, 한번은 숫자를 읽음. 순서 맞춰줘야 함.
                try {
                    int line;

                    line = (int)dis.read();
                    arr[count]=line;
                    count++;
                    Log.w("서버에서 받아온 값 ",""+line);
                }catch (Exception e){

                }
            }
        };
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mySensorManager.registerListener(gyroListener, myGyroscope,SensorManager.SENSOR_DELAY_UI);
        mySensorManager.registerListener(accListener, myAcceleromter,SensorManager.SENSOR_DELAY_UI);

    }
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(gyroListener);
        mySensorManager.unregisterListener(accListener);

    }
    protected void onStop(){
        super.onStop();
    }
    @Override
    public void onDenied(int i, String[] strings) {
    }

    @Override
    public void onGranted(int i, String[] strings) {
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }
    private void startLocationService() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            int chk1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int chk2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

            Location location = null;
            if (chk1 == PackageManager.PERMISSION_GRANTED && chk2 == PackageManager.PERMISSION_GRANTED) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else {
                return;
            }
            if (location != null) {
                curmLatitude = location.getLatitude();
                curmLongitude = location.getLongitude();
            }
            else {
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }


}
