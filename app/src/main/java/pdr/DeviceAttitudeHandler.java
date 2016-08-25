package pdr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by xin on 8/25/2016.
 */
public class DeviceAttitudeHandler implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float[] mRotationMatrixFromVector = new float[16] ;
    private float[] mRotationMatrix = new float[16];
    private float[] orientationVals = new float[3];

    public DeviceAttitudeHandler(SensorManager sensorM){
        mSensorManager   = sensorM ;
        mSensor          = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void start(){
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        // StackOverFlow
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            mSensorManager.getRotationMatrixFromVector(mRotationMatrixFromVector, event.values);

            mSensorManager.remapCoordinateSystem(mRotationMatrixFromVector,
                    SensorManager.AXIS_X, SensorManager.AXIS_Z,
                    mRotationMatrix);
            mSensorManager.getOrientation(mRotationMatrix, orientationVals);

            orientationVals[0] = (float) Math.toDegrees(orientationVals[0]);
            orientationVals[1] = (float) Math.toDegrees(orientationVals[1]);
            orientationVals[2] = (float) Math.toDegrees(orientationVals[2]);

        }

    }

    // getter yaw
    public float getOrientationYaw(){
        return orientationVals[0] ;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

