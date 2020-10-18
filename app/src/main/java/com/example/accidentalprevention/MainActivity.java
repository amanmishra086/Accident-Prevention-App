package com.example.accidentalprevention;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    SensorManager sensorManager;
    Sensor Acc;
    SmsManager smsManager;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    Double lat,longi;
    TextView textView;
    String link="http://www.google.com/maps/place/";



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);

        smsManager = SmsManager.getDefault();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
         Acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        sensorManager.registerListener(acc, Acc, SensorManager.SENSOR_DELAY_FASTEST);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,0,this);
    }

    SensorEventListener acc=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                           if(event.values[0]>10.0){
                            Toast.makeText(MainActivity.this, "Hello 10.0", Toast.LENGTH_SHORT).show();
                               String loc="";
                               loc =link+lat+","+longi;
                             smsManager.sendTextMessage("+919636730565",null,
                                     "Had bakchodi hai"+loc, null,null);
                               textView.setText(""+lat+" "+longi);
                           }else if(event.values[1]>15.0){
                               String loc="";
                               loc =link+lat+","+longi;

                             smsManager.sendTextMessage("+919636730565",null,
                                      "Had bakchodi hai"+loc, null,null);
                               Toast.makeText(MainActivity.this, "Hello 15.0", Toast.LENGTH_SHORT).show();
                              textView.setText(""+lat+" "+longi);
                           }

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onLocationChanged(Location location) {
           lat=location.getLatitude();
           longi=location.getLongitude();
        textView.setText(""+lat+" "+longi);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
