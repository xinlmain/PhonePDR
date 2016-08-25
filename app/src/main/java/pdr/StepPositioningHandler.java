package pdr;

import android.location.Location;
import android.util.Log;

/**
 * Created by xin on 8/25/2016.
 */
public class StepPositioningHandler {

    private Location mCurrentLocation = new Location("") ;

    public Location getMCurrentLocation(){
        return mCurrentLocation ;
    }

    public void setMCurrentLocation( Location loc ){

        this.mCurrentLocation.setLatitude(loc.getLatitude());
        this.mCurrentLocation.setLongitude(loc.getLongitude());

    }

    public Location computeNextStep(float stepSize, float bearing){

        // Le lien
        Location newLocation = new Location("");
        int earthRadius     = 6378100 ;
        double pasEnRadian  = Math.toRadians(bearing);
        double latitude    =  Math.toRadians(mCurrentLocation.getLatitude()) ;
        double longitude    = Math.toRadians(mCurrentLocation.getLongitude());

        double newLatitude = Math.asin( Math.sin(latitude)*Math.cos(stepSize/earthRadius) + Math.cos(latitude)*Math.sin(stepSize/earthRadius)*Math.cos(pasEnRadian));
        double newLongitude = longitude + Math.atan2(Math.sin(pasEnRadian)*Math.sin(stepSize/earthRadius)*Math.cos(latitude), Math.cos(stepSize/earthRadius)-Math.sin(latitude)*Math.sin(newLatitude));

        newLocation.setLatitude( Math.toDegrees(newLatitude));
        newLocation.setLongitude(Math.toDegrees(newLongitude));

        Log.e("v" , " s-latitude" + Math.toDegrees(newLatitude) );
        Log.e("v", " s-longitude" + Math.toDegrees(newLongitude) );

        return newLocation ;
    }

}

