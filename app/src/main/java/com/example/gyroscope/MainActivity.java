package com.example.gyroscope;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView text;
    private TextView text1;
    private TextView text2;
    private SensorManager sysmanager;
    private Sensor sensor;
    private SensorEventListener sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);

        sysmanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sysmanager != null)
            sensor = sysmanager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sv = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
                float[] remmappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, remmappedRotationMatrix);
                float[] orientations = new float[3];
                SensorManager.getOrientation(remmappedRotationMatrix, orientations);
                for (int i = 0; i < 3; i++) {
                    orientations[i] = (float) (Math.toDegrees(orientations[i]));
                }
                text.setText(String.valueOf((int) orientations[2]));
                SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_Y,SensorManager.AXIS_Y, remmappedRotationMatrix);
                float[] orientations3 = new float[3];
                SensorManager.getOrientation(remmappedRotationMatrix, orientations3);
                for (int i = 0; i < 3; i++) {
                    orientations3[i] = (float) (Math.toDegrees(orientations3[i]));
                }
                text2.setText(String.valueOf((int) orientations3[0]));
                SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_Z, SensorManager.AXIS_X, remmappedRotationMatrix);
                float[] orientations2 = new float[3];
                SensorManager.getOrientation(remmappedRotationMatrix, orientations2);
                for (int i = 0; i < 3; i++) {
                    orientations2[i] = (float) (Math.toDegrees(orientations2[i]));
                }
                text1.setText(String.valueOf((int) orientations2[2]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        sysmanager.registerListener(sv, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sysmanager.unregisterListener(sv);
    }
}