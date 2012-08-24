package net.carff.android.steampunkclock;

import java.net.URL;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import net.carff.android.steampunkclock.weather.WeatherData;
import net.carff.android.steampunkclock.weather.WeatherDataHandler;

public class Util {

	public static WeatherDataHandler wdh;
	public static WeatherData wData;
	private static LocationManager myLocManager;
	private static String provider;
	private static Location myLocation;
	private static Geocoder geocoder;
	private static Address address;
	private static String qLocation;
	private static String weatherApiLocation;
	
	public Util() {
		wData = new WeatherData();
	}
	
	protected static int getNumberResource(int number) {
		int mResource = R.drawable.zeroam; 
		
    	switch (number) {
    	case 0: mResource = R.drawable.zeroam; break;
    	case 1: mResource = R.drawable.oneam; break;
    	case 2: mResource = R.drawable.twoam; break;
    	case 3: mResource = R.drawable.threeam; break;
    	case 4: mResource = R.drawable.fouram; break;
    	case 5: mResource = R.drawable.fiveam; break;
    	case 6: mResource = R.drawable.sixam; break;
    	case 7: mResource = R.drawable.sevenam; break;
    	case 8: mResource = R.drawable.eightam; break;
    	case 9: mResource = R.drawable.nineam; break;	    	
    	}
		return mResource;
	}
	protected static int getTemperatureNumberResource(int number) {
		int mResource = R.drawable.smzeroam; 
		
    	switch (number) {
    	case 0: mResource = R.drawable.smzeroam; break;
    	case 1: mResource = R.drawable.smoneam; break;
    	case 2: mResource = R.drawable.smtwoam; break;
    	case 3: mResource = R.drawable.smthreeam; break;
    	case 4: mResource = R.drawable.smfouram; break;
    	case 5: mResource = R.drawable.smfiveam; break;
    	case 6: mResource = R.drawable.smsixam; break;
    	case 7: mResource = R.drawable.smsevenam; break;
    	case 8: mResource = R.drawable.smeightam; break;
    	case 9: mResource = R.drawable.smnineam; break;	    	
    	}
		return mResource;
	}
	
	protected static int getWeatherResource(String mweather) {
    	int mResource = R.drawable.sunny;
    	Log.d("Util", "mweather early " + mweather);
    	if (mweather!= null) {
	    	int weather = mweather.toLowerCase().hashCode();
	    	Log.d("Util", "wweather lowecase " + mweather.toLowerCase());
	   	 
	    	Log.d("Util", "wweather lowecase " + weather);
	 
	    	if (weather == "mostly_sunny".hashCode()) {
	    		mResource = R.drawable.mostly_sunny;
	    	} else if (weather == "partly_cloudy".hashCode()) {
	    		mResource = R.drawable.partly_cloudy;
	    		
	    	} else if (weather == "mostly_cloudy".hashCode()) {
	    		mResource = R.drawable.mostly_cloudy;
	    	} else if (weather == "chance_of_storm".hashCode()) {
	    		mResource = R.drawable.chance_of_storm;
	    	} else if (weather == "rain".hashCode()) {
	    		mResource = R.drawable.rain;
	    	} else if (weather == "Light rain".hashCode()) {
	    		mResource = R.drawable.light_rain;
	    	} else if (weather == "chance_of_rain".hashCode()) {
	    		mResource = R.drawable.chance_of_rain;
	    	} else if (weather == "chance_of_snow".hashCode()) {
	    		mResource = R.drawable.chance_of_snow;
	    	} else if (weather == "cloudy".hashCode()) {
	    		mResource = R.drawable.cloudy;
	    	} else if (weather == "mist".hashCode()) {
	    		mResource = R.drawable.mist;
	    	} else if (weather == "storm".hashCode()) {
	    		mResource = R.drawable.storm;
	    	} else if (weather == "thunderstorm".hashCode()) {
	    		mResource = R.drawable.thunderstorm;
	    	} else if (weather == "chance_of_storm".hashCode()) {
	    		mResource = R.drawable.chance_of_storm;
	    	} else if (weather == "sleet".hashCode()) {
	    		mResource = R.drawable.sleet;
	    	} else if (weather == "snow".hashCode()) {
	    		mResource = R.drawable.snow;
	    	} else if (weather == "icy".hashCode()) {
	    		mResource = R.drawable.icy;
	    	} else if (weather == "dust".hashCode()) {
	    		mResource = R.drawable.dust;
	    	} else if (weather == "fog".hashCode()) {
	    		mResource = R.drawable.fog;
	    	} else if (weather == "smoke".hashCode()) {
	    		mResource = R.drawable.smoke;
	    	} else if (weather == "haze".hashCode()) {
	    		mResource = R.drawable.haze;
	    	} else if (weather == "flurries".hashCode()) {
	    		mResource = R.drawable.flurries;
	    	} 
    	}
    	return mResource;
    }
	public static void updateWeatherData(Activity act) {
		
		myLocManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
		provider = myLocManager.getBestProvider(criteria, false);
		myLocation = myLocManager.getLastKnownLocation(provider);
	    geocoder = new Geocoder(act, Locale.getDefault());
	    
	    try {
	    	address = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1).get(0);
	    	qLocation = (String) address.getPostalCode();
	    	
	    } catch (Exception e){
	    	Log.e("Util", "Exception: "+ e);
	    }
	    weatherApiLocation = "http://www.google.com/ig/api?weather=" + qLocation;
	    updateData(weatherApiLocation);
		
	}
	
	public static void updateData(String url) {
		ParseGoogleData task = new ParseGoogleData();
		task.execute(url);
	}
	
	private static class ParseGoogleData extends AsyncTask<String, Void, WeatherData> {

		@Override
		protected WeatherData doInBackground(String... urls) {
			try {
				
			    SAXParserFactory spf = SAXParserFactory.newInstance();
			    SAXParser sp = spf.newSAXParser();
			    XMLReader xr = sp.getXMLReader();
			    URL sourceUri = new URL(urls[0]);
			
			    wdh = new WeatherDataHandler();
			    xr.setContentHandler(wdh);
			    xr.parse(new InputSource(sourceUri.openStream()));
			
		    }catch (Exception e) {
		    	Log.e("Util", "Parse Exception:"+ e);
			
		    }
		    SteampunkclockActivity.myWData = wdh.getwData(); 
			return wData;
		}
		
		protected void onPostExecute(WeatherData wData) {
			//Log.e("Util", "Have weather data for" + wData.getCity());

		}
		
	}

	
}
