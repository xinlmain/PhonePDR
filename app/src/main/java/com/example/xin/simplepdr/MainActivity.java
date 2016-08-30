package com.example.xin.simplepdr;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import pdr.CalorieInfo;
import pdr.DeviceAttitudeHandler;
import pdr.NewStepDetectionHandler;
import pdr.StepDetectionHandler;
import pdr.StepDetectionListener;

public class MainActivity extends AppCompatActivity implements StepDetectionListener {
    private CanvasView customCanvas;
    private boolean firstLocation = true;

    private int xMax;
    private int yMax;
    private int x0;
    private int y0;

    private SensorManager sm;
    private DeviceAttitudeHandler dah;
    //private StepDetectionHandler sdh;
    private NewStepDetectionHandler sdh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customCanvas = (CanvasView) findViewById(R.id.Canvas1);

        xMax = 600;//customCanvas.width;
        yMax = 1000;//customCanvas.height;
        Toast.makeText(this, "Size: " + xMax + ", " + yMax,
                Toast.LENGTH_SHORT).show();
        x0 = xMax/2;
        y0 = yMax/2;

        customCanvas.drawPoint(x0, y0);

        sm      = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sdh = new NewStepDetectionHandler();
        //sdh     = new StepDetectionHandler(sm);
        sdh.setStepListener(this);
        sdh.SensorChangeListener(new CalorieInfo());
        sm.registerListener(sdh,
                sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        //sdh.setStepDetectionListener(this);

        dah     = new DeviceAttitudeHandler(sm);

    }

    @Override
    public void onNewStep(float stepSize) {
        float yaw = dah.getOrientationYaw();
        double distanceX = 0.8 * Math.sin(Math.toRadians(yaw));
        double distanceY = 0.8 * Math.cos(Math.toRadians(yaw));
        Toast.makeText(this, "New Step: " + distanceX + ", " + distanceY,
                Toast.LENGTH_SHORT).show();
        x0 = x0 + (int)(distanceX * 50);
        y0 = y0 - (int)(distanceY * 50);

        customCanvas.drawPoint(x0, y0);
    }

    @Override
    protected void onPause() {
        //sdh.stop();
        dah.stop();
        super.onPause();
    }
    @Override
    protected void onResume() {
        //sdh.start();
        dah.start();
        super.onResume();
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }
}
