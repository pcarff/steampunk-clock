package net.carff.android.steampunkclock.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import android.widget.Toast;

import net.carff.android.steampunkclock.R;
import net.carff.android.steampunkclock.SteampunkClockApplication;
import net.carff.android.steampunkclock.data.ForecastData;
import net.carff.android.steampunkclock.data.WeatherData;
import net.carff.android.steampunkclock.util.UIUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeatherDataFetchService extends Service {

    private static final String TAG = WeatherDataFetchService.class.getSimpleName();
    
    LocationManager mLocMgr;
    private String provider;
    private Location myLocation;
    private Geocoder geocoder;
    private Address address;
    private String zipCode;
    
    private AlarmManager alarm;
    
    private static final String ns = null;
   
    private DataFetcherTask mDFT;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        URL downloadPath;
        getApplicationContext();
        
        zipCode = getZipCode();
        Log.d(TAG, "Zip = " + zipCode);
        String fetchUrl = "";
        if (!zipCode.equals("00000")) {
            fetchUrl = getString(R.string.api_url_front) + getString(R.string.api_url_key)
                     + getString(R.string.api_url_middle) + zipCode 
                    + getString(R.string.api_url_end);
        }
        
        try {
            String url = intent.getDataString();
            url = fetchUrl;
            Log.d(TAG,"url " + url);
            if (url != null && (url.length() > 0)) {
                downloadPath = new URL(url);
                mDFT = new DataFetcherTask();
                mDFT.execute(downloadPath);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Bad URL ", e);
        } catch (Exception e) {
            Log.e(TAG, "Problem fetching data " + e);
        }
        
        return Service.START_FLAG_REDELIVERY;
     
    }

    @Override
    public IBinder onBind(Intent intent) {
       
        return null;
    }
    
    

    private class DataFetcherTask extends AsyncTask<URL, Void, Boolean> {
        
        private static final String DEBUG_TAG = "WearherDataFetcherService$DataFetcherTask";
        
        @Override
        protected Boolean doInBackground(URL... params) {
            boolean succeeded = false;

            URL downloadPath = params[0];
            Log.d(TAG, "URL: " + downloadPath);
            if (downloadPath != null) {
                succeeded = xmlParse(downloadPath);
            }
            
            return succeeded;
        }
 
        private boolean xmlParse(URL downloadPath) {
            boolean succeeded = false;
            boolean currentCondition = false;
            boolean inWeather = false;

            XmlPullParser weatherStream;
            Log.d(TAG, "Starting to Parse");
            try {
                weatherStream = XmlPullParserFactory.newInstance().newPullParser();
                weatherStream.setInput(downloadPath.openStream(),null);
                int eventType = -1;
                int forecastCounter = 0;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    
                    switch(eventType) {
                        //at start of document: START_DOCUMENT
                        case XmlPullParser.START_DOCUMENT:
                            Log.d(TAG, "START_DOCUMENT");
                            break;
                            
                        case XmlPullParser.START_TAG:
                            //get tag name
                            String tagName = weatherStream.getName();
                            //Log.d(TAG, "tagName: " + tagName);
                            // if <areaName> get city
                            if (tagName.equals("areaName")) {
                                
                                weatherStream.next();
                                ((SteampunkClockApplication) getApplication()).setCity(weatherStream.getText());
                                Log.d(TAG, "City from app: " + 
                                        ((SteampunkClockApplication) getApplication()).getCity());
                            }else if (tagName.equals("current_condition")) {
                                currentCondition = true;
                            }else if (tagName.equals("weather")) {
                                inWeather = true;
                                Log.d(TAG, "Setting inWeather");
                                ((SteampunkClockApplication) getApplication()).addForecast();
  
                            }else if (tagName.equals("temp_F")) {
                                weatherStream.next();
                                ((SteampunkClockApplication) getApplication()).setTempF(
                                        Integer.valueOf(weatherStream.getText()));
                                Log.d(TAG, "temp f " + 
                                        ((SteampunkClockApplication) getApplication()).getTempF());
                            }else if(tagName.equals("weatherCode") && currentCondition) {
                                weatherStream.next();
                                ((SteampunkClockApplication) getApplication()).setCurrentCondition(
                                        Integer.valueOf(weatherStream.getText()));
                                Log.d(TAG, "Current Weather Code " + 
                                        ((SteampunkClockApplication) getApplication()).getCurrentCondition());
                                currentCondition = false;  // Will need to move this the last item in current condition if add more.
                            }else if (tagName.equals("date") ) {
                                weatherStream.next();
                                Date date = null;
                                SimpleDateFormat df = null;
                                try {
                                    Log.d(TAG, "trying to get date");
                                    Log.d(TAG, "forecastCounter: " + forecastCounter);
                                    date = new SimpleDateFormat("yyyy-MM-dd").parse(weatherStream.getText());
                                     df = new SimpleDateFormat("E");
                                } catch (ParseException e) {
                                  
                                    e.printStackTrace();
                                }
                                Log.d(TAG, "date: " + date.toString());
                                Log.d(TAG, "dateFormat: " + df.format(date));
                                if (forecastCounter == 0) {
                                    ((SteampunkClockApplication) getApplication()).setDay(df.format(date));
                                }
                                ((SteampunkClockApplication) getApplication()).setDayOfWeek(forecastCounter, df.format(date));
                            }else if (tagName.equals("tempMaxF") ) {
                                weatherStream.next();
                                ((SteampunkClockApplication) getApplication()).setHigh(forecastCounter,Integer.valueOf(weatherStream.getText()));
                            }else if (tagName.equals("tempMinF") ) {
                                weatherStream.next();
                                ((SteampunkClockApplication) getApplication()).setLow(forecastCounter,Integer.valueOf(weatherStream.getText()));
                            }else if (tagName.equals("weatherCode") && inWeather ) {
                                weatherStream.next();
                                ((SteampunkClockApplication) getApplication()).setCondition(forecastCounter,Integer.valueOf(weatherStream.getText()));
                                Log.d(TAG, "incrementing counter");
                                forecastCounter++;  // Will need to move this the last item in current condition if add more.
                                Log.d(TAG, "Clearing inWeather");
                                inWeather = false;
                            }
                            break;
                            
                    }
                    //jump to next event
                    eventType = weatherStream.next();
                }
                
            }catch (XmlPullParserException e) {
                Log.e(TAG, "Error during parsing ", e);
            } catch (IOException e) {
                Log.e(TAG, "IO Error during parsing ", e);
            }

            return succeeded;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            // Send data to the right place
            
        }
 
    }
    
    private String getZipCode() {
        String zipCode;
        
        try {
            mLocMgr = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provider = mLocMgr.getBestProvider(criteria, false);
            Log.d(TAG, "Got Provider " + provider);
            Log.d(TAG, "Is Google TV? " +  UIUtils.isGoogleTV(this));
            if (UIUtils.isGoogleTV(this)) {
                //Get Static ZipCode
                myLocation = mLocMgr.getLastKnownLocation("static");
                
            } else {
                myLocation = mLocMgr.getLastKnownLocation(provider);
            }
            geocoder = new Geocoder(this);
            address = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1).get(0);
            
            zipCode = (String) address.getPostalCode();
            
        } catch (Exception e){
            Log.e(TAG, "Exception: "+ e);
            
            return "00000";
        }
        
        return zipCode;
        
    }
    
            
        
}
