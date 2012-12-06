package net.carff.android.steampunkclock.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import net.carff.android.steampunkclock.R;
import net.carff.android.steampunkclock.SteampunkClockApplication;
import net.carff.android.steampunkclock.service.WeatherDataFetchService;
import net.carff.android.steampunkclock.util.UIUtils;

import java.util.Calendar;
import java.util.Locale;


public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    
    LocationManager mLocMgr;
    private String provider;
    private Location myLocation;
    private Geocoder geocoder;
    private Address address;
    
    private String zipCode = null;
    private String city = null;
    
    private ViewPager mViewPager;  //For Phones
    private RadioGroup dimmerRadioGroup;  //For TV
    private View dimLayout;  //For TV
    private static final int[] DIM_LAYERS =
            new int[] { R.color.dim1, R.color.dim2, R.color.dim3, R.color.dim4, R.color.dim5 };
    private static final int[] DIM_CONTROLS =
            new int[]
                { R.id.dimbutton1, R.id.dimbutton2, R.id.dimbutton3, R.id.dimbutton4, R.id.dimbutton5 };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFinishing()) {
            return;
        }
        
        getApplicationContext();
        mLocMgr = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = mLocMgr.getBestProvider(criteria, false);
        Log.d(TAG, "Got Provider " + provider);
        myLocation = mLocMgr.getLastKnownLocation(provider);
        
        geocoder = new Geocoder(this, Locale.getDefault());
        
        zipCode = getZipCode();
        Log.d(TAG, "Zip = " + zipCode);

        String fetchUrl = "";
        if (!zipCode.equals("00000")) {
            fetchUrl = getString(R.string.api_url_front) + getString(R.string.api_url_key)
                     + getString(R.string.api_url_middle) + zipCode 
                    + getString(R.string.api_url_end);
        }
       
        
        //Start data fetch service
        Log.d(TAG, "Starting Fetch Service");
        Intent intent = new Intent(this.getApplicationContext(), WeatherDataFetchService.class);
        intent.setData(Uri.parse(fetchUrl));
        PendingIntent pintent = PendingIntent.getService(this.getApplicationContext(), 0, intent, 0);
        
        startService(intent);
        
        AlarmManager am=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.SECOND, (3 * 60 * 1000));
        //After after 3 hours
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 3 * 60 * 1000 , 
                pintent);
        
        
        setContentView(R.layout.clock);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        if (mViewPager !=null) {
            mViewPager.setAdapter(new SteampunkPagerAdapter());
            mViewPager.setCurrentItem(0);
        }
          
        if (UIUtils.isGoogleTV(getBaseContext())){
            
            dimLayout = (View) findViewById(R.id.dimmer_overlay);
            dimLayout.setVisibility(View.VISIBLE);
            dimmerRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
            dimmerRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
        dimmerRadioGroup.check(DIM_CONTROLS[level]);
      }
    
    private String getZipCode() {
        String zipCode;
        try {
            Log.d(TAG, "LAT: " + myLocation.getLatitude() );
            Log.d(TAG, "LONG: " + myLocation.getLongitude() );
            
            address = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1).get(0);
            
            zipCode = (String) address.getPostalCode();
            
        } catch (Exception e){
            Log.e(TAG, "Exception: "+ e);
            return "00000";
        }
        
        return zipCode;
        
    }
    

    private class SteampunkPagerAdapter extends PagerAdapter {
        
        public int getCount() {
            return 2;
        }
 
        public Object instantiateItem(View collection, int position) {
 
            LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int resId = 0;
            switch (position) {
            case 0:
                resId = R.layout.time_date_page;

                break;
            case 1:
                resId = R.layout.location_page;  

                break;
            //case 2:
            //    resId = R.layout.forecast_page; 
            //    break;
            }
 
            View view = inflater.inflate(resId, null);
            ((ViewPager) collection).addView(view, 0);
            return view;
        }
 
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2); 
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }
 
        @Override
        public Parcelable saveState() {
            return null;
        }
}
    
}
