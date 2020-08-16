package com.example.har;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";



    private SensorManager sensorManager;
    private Sensor accelerometer, mGyro;

    TextView xValue, yValue, zValue, xgyro, ygyro, zgyro, read;
    Button Savebttn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      xValue = (TextView) findViewById(R.id.xValue);
      yValue = (TextView) findViewById(R.id.yValue);
      zValue = (TextView) findViewById(R.id.zValue);

      xgyro = (TextView) findViewById(R.id.xgyro);
      ygyro = (TextView) findViewById(R.id.ygyro);
      zgyro = (TextView) findViewById(R.id.zgyro);

      Savebttn = findViewById(R.id.Savebutton);
      Savebttn.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view){
              writeFile();

          }
      });

      Log.d(TAG, "onCreate: onCreate: Initializing senosr services");
      sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

      accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
      sensorManager.registerListener(MainActivity.this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
      Log.d(TAG, "onCreate: registered accelerometer");

      mGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
      sensorManager.registerListener(MainActivity.this,mGyro, SensorManager.SENSOR_DELAY_NORMAL);
      Log.d(TAG, "onCreate: registered Gyro");
        
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType()== sensor.TYPE_ACCELEROMETER){
            Log.d(TAG, "onSensorChanged: x: " + sensorEvent.values[0] +"y :"+ sensorEvent.values[1] + "z :" + sensorEvent.values[2]);

            xValue.setText("ax" + sensorEvent.values[0]);
            yValue.setText("ay" + sensorEvent.values[1]);
            zValue.setText("az" + sensorEvent.values[2]);

        }
        else if(sensor.getType()== sensor.TYPE_GYROSCOPE) {
            xgyro.setText("gx" + sensorEvent.values[0]);
            ygyro.setText("gy" + sensorEvent.values[1]);
            zgyro.setText("gz" + sensorEvent.values[2]);
        }

    }



    public void writeFile(){
        String filename = "test.txt";
        String textToSave = xValue.getText().toString()+','+yValue.getText().toString()+','+zValue.getText().toString()+','+xgyro.getText().toString()+','+ygyro.getText().toString()+','+zgyro.getText().toString();
        try{
            FileOutputStream out = openFileOutput(filename, Context.MODE_APPEND);
            out.write(textToSave.getBytes());
            out.close();
            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
