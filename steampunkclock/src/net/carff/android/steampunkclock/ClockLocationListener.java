package net.carff.android.steampunkclock;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class ClockLocationListener implements LocationListener {
	public static double latitude;
	public static double longitude;
	
	public void onLocationChanged(Location loc) {
		loc.getLatitude();
		loc.getLongitude();
		latitude = loc.getLatitude();
		longitude = loc.getLongitude();
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
