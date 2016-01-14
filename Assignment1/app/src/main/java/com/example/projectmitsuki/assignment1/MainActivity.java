package com.example.projectmitsuki.assignment1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements SensorEventListener, OnClickListener {

    TextView acceleration;
    private SensorManager senseMan;
    private Sensor senseAccelerometer;
    private Button Start , Stop, Send;
    private ArrayList<String> timeStampedData;
    private boolean Trigger = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senseMan = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senseAccelerometer = senseMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senseMan.registerListener(this,senseAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        timeStampedData = new ArrayList();

        Start = (Button) findViewById(R.id.button);
        Stop = (Button) findViewById(R.id.button2);
        Send = (Button) findViewById(R.id.button3);
        Start.setOnClickListener(this);
        Stop.setOnClickListener(this);
        Send.setOnClickListener(this);
        acceleration = (TextView)findViewById(R.id.acceleration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sense = event.sensor;
        if(sense.getType() == Sensor.TYPE_ACCELEROMETER && (Trigger == true)){
            acceleration.setText("x: "+event.values[0]+"\n y: "+ event.values[1]+                                                           "\n z: "+ event.values[2]);
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];
            long timeStamp = System.currentTimeMillis();
            String csData;
            csData = Float.toString(xValue) + ","  + Float.toString(yValue) + "," + Float.toString(zValue) + "," + Long.toString(timeStamp);
            timeStampedData.add(csData);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            timeStampedData = new <String>ArrayList();
            Context context = getApplicationContext();
            Trigger = true;

            Toast.makeText(getApplicationContext(), "Data Collection Started",
                    Toast.LENGTH_LONG).show();



        }
        else if(v.getId() == R.id.button2){
            Context context = getApplicationContext();
            Toast.makeText(getApplicationContext(), "Data Written to CSV File",
                    Toast.LENGTH_LONG).show();
            Trigger = false;
            String path = this.getFilesDir().getPath().toString() + "/data.csv";
            Log.v("myAPP",path);

            try {
                OutputStream writer = new FileOutputStream(path);
                for(String s : timeStampedData){
                    writer.write(s.getBytes());
                }
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else {
            if (v.getId() == R.id.button3) {
                Context context = getApplicationContext();
                Toast.makeText(getApplicationContext(), "Data Sent to Server", Toast.LENGTH_LONG).show();

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
                Socket sock;
                sock = null;
                try {
                    sock = new Socket("192.168.53.134", 8888);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                //File f = new File("/data/data/com.example.projectmitsuki.assignment1/files/data.csv");
                //long length = f.length();
                byte[] buffer = new byte[2048];
                InputStream i = null;
                try {
                    i = new FileInputStream("/data/data/com.example.projectmitsuki.assignment1/files/data.csv");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                try {
                    OutputStream o =sock.getOutputStream();
                    DataOutputStream d = new DataOutputStream(o);
                    while (i.read(buffer) > 0) {
                        d.write(buffer, 0, buffer.length);
                    }
                    o.close();
                    i.close();
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

    }
}
