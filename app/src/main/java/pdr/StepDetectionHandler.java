package pdr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Vector;

/**
 * Created by xin on 8/25/2016.
 */
public class StepDetectionHandler implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Vector<Float> HistoryOfY = new Vector<Float>();
    private StepDetectionListener listenerStemps;
    private float DistanceTraveled;
    private float[] acceleration = new float[3];



    public StepDetectionHandler(SensorManager sensorM ){
        this.mSensorManager = sensorM ;
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    public void setStepDetectionListener(StepDetectionListener ls ){
        listenerStemps = ls ;
    }

    public void start(){
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        mSensorManager.unregisterListener(this);
    }

    /* each movement Y value is recovered and is evaluated to see if the user has moved or not */
    @Override
    public void onSensorChanged(SensorEvent event) {

        /* The value of Y */
        float positionY = event.values[1];

        /* calculate the average */
        float average    = CalculateAverage( positionY ) ;

        /* if the average value is greater than 1.1, the value observed during the test, then it is
         considered that this is a movement */
        if ( average > 1.2 ){

            listenerStemps.onNewStep((float) 0.8);
        }

    }

    /* Calculates the average of last 5 values */
    public float CalculateAverage(float value ){

        /* sum of the last 5 values*/
        float tmpValue = 0 ;
        /* average value */
        float average   = 0 ;
        HistoryOfY.add(value);
        if( HistoryOfY.size() >= 4 ){
            HistoryOfY.removeElementAt(0);

        }

        for(float lastValue : HistoryOfY) {
            tmpValue += lastValue;
        }

        average = tmpValue / HistoryOfY.size() ;
        return average ;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}