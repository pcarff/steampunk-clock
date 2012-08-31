package net.carff.android.steampunkclock;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import net.carff.android.steampunkclock.weather.ForecastData;
import net.carff.android.steampunkclock.weather.WeatherData;
import net.carff.android.steampunkclock.weather.WeatherDataHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SteampunkclockActivity extends Activity {
	public static WeatherData myWData;
	public static ArrayList<ForecastData> myFData;
  private static final int[] DIM_LAYERS =
      new int[] { R.color.dim1, R.color.dim2, R.color.dim3, R.color.dim4, R.color.dim5 };
  private static final int[] DIM_CONTROLS =
      new int[]
          { R.id.dimbutton1, R.id.dimbutton2, R.id.dimbutton3, R.id.dimbutton4, R.id.dimbutton5 };
  private LocationManager myLocManager;
	private String provider;
	private Location myLocation;
	private Geocoder geocoder;
	private Address address;
	private String qLocation;
	private String weatherApiLocation;
	private WeatherDataHandler wdh;
	
	private RadioGroup mRadioGroup;
	private View dimLayout;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Thread myThread = null;

        Runnable runnable = new SteamPunkRunner();
        myThread= new Thread(runnable);   
        myThread.start();
        
        
        waitforfirstweatherdata(1000);
        
        //Set up fragments in display.
        FragmentManager fragMan = this.getFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        
        TimeFragment timeFrag = new TimeFragment();
        fragTrans.replace(R.id.time_fragment, timeFrag);
        DateFragment dateFrag = new DateFragment();
        fragTrans.replace(R.id.date_fragment, dateFrag);
        
        LocationFragment locationFrag = new LocationFragment();
        fragTrans.replace(R.id.location_fragment, locationFrag);
        TemperatureFragment temperatureFrag = new TemperatureFragment();
        fragTrans.replace(R.id.temperature_fragment, temperatureFrag);
        ForecastFragment forecastFrag = new ForecastFragment();
        fragTrans.replace(R.id.forecast_fragment, forecastFrag);

        fragTrans.commit();
        dimLayout = (View) findViewById(R.id.dimmer_overlay);
        
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				switch (checkedId) {
				case R.id.dimbutton1 : dimLayout.setBackgroundResource(R.color.dim1);
					break;
				case R.id.dimbutton2 : dimLayout.setBackgroundResource(R.color.dim2);
					break;
				case R.id.dimbutton3 : dimLayout.setBackgroundResource(R.color.dim3);
					break;
				case R.id.dimbutton4 : dimLayout.setBackgroundResource(R.color.dim4);
					break;
				case R.id.dimbutton5 : dimLayout.setBackgroundResource(R.color.dim5);
					break;
				}
				
			}
        	
        });

        checkIntentParameters();
    }

  private void checkIntentParameters() {
    Intent intent = getIntent();
    if (intent == null) {
      return;
    }

    Uri uri = intent.getData();
    if (uri == null) {
      return;
    }

    String dimLevel = uri.getQueryParameter("dimlevel");

    if (dimLevel != null) {
      try {
        int requested = Integer.parseInt(dimLevel);
        setDimLevel(requested - 1);
      } catch (NumberFormatException e) {
        // ignored
      }
    }
  }

  /**
   * @param level Dim level (0 - 4).
   */
  private void setDimLevel(int level) {
    if (level < 0 || level > DIM_CONTROLS.length) {
      return;
    }

    dimLayout.setBackgroundResource(DIM_LAYERS[level]);
    mRadioGroup.check(DIM_CONTROLS[level]);
  }

  class SteamPunkRunner implements Runnable {
    	public void run() {
    		while (!Thread.currentThread().isInterrupted()) {
          	  try {
          		  updateWeatherData();
          		  	Thread.sleep(600000);  
          	  } catch (Exception e) {}
            }
    	}
    }
    
    public void updateWeatherData() {
    	myLocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
		provider = myLocManager.getBestProvider(criteria, false);
		myLocation = myLocManager.getLastKnownLocation(provider);
	    geocoder = new Geocoder(this, Locale.getDefault());
	    
	    try {
	    	address = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1).get(0);
	    	qLocation = (String) address.getPostalCode();
	    	
	    } catch (Exception e){
	    	Log.e("MAIN", "Exception: "+ e);
	    }
	    weatherApiLocation = "http://www.google.com/ig/api?weather=" + qLocation;
	    //updateWeatherData(weatherApiLocation);
	    
	    try {
			
		    SAXParserFactory spf = SAXParserFactory.newInstance();
		    SAXParser sp = spf.newSAXParser();
		    XMLReader xr = sp.getXMLReader();
		    URL sourceUri = new URL(weatherApiLocation);
		
		    wdh = new WeatherDataHandler();
		    xr.setContentHandler(wdh);
		    xr.parse(new InputSource(sourceUri.openStream()));
		
	    }catch (Exception e) {
	    	Log.e("Util", "Parse Exception:"+ e);
		
	    }
	    myWData = wdh.getwData();
	    myFData = wdh.getfData();
	    Log.e("MAIN", "Data for Steampunk" + myWData.getCity());	    //return wData;
    }
    
    public void waitforfirstweatherdata(int i){
    	try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			Log.e("Activity", "Interrupted in weather data wait", e);
		}
    }
}
