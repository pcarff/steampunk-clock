package net.carff.android.steampunkclock.ui;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.carff.android.steampunkclock.R;
import net.carff.android.steampunkclock.SteampunkClockApplication;

public class LocationFragment extends Fragment {
    
    private String TAG = "LocationFragment";
    private TextView locationText;
    private TextView cityText;
    private String font = "duvall.ttf";
    private String city;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread myThread = null;
        
        updateDisplay();

        System.out.println("Starting Location Runner");
        Runnable runnable = new LocationRunner();
        myThread= new Thread(runnable);   
        myThread.start();
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.location, container, false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), font);
        
        locationText = (TextView) view.findViewById(R.id.location_title);
        locationText.setTextSize((float)20.0);
        locationText.setTypeface(tf);
        
        cityText = (TextView) view.findViewById(R.id.city_title);
        cityText.setTextSize((float)20.0);
        cityText.setTypeface(tf);
        cityText.setText(getCity());
        
        return view;
        
    }
    
    
    
    public void updateDisplay () {
        
        getActivity().runOnUiThread(new Runnable() {

            public void run() {
                try {
                    //Log.d(TAG, "Updating City" + getCity());
                    cityText.setText(getCity());
                } catch (Exception e) {
                    //Toast toast = Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG);
                    //toast.show();
                    
                }
            }
        });
                
    }
    
    class LocationRunner implements Runnable  {
        //@Override  
        public void run() {  
          while (!Thread.currentThread().isInterrupted()) {
              try {
                  updateDisplay();
                    Thread.sleep(5000);  
              } catch (Exception e) {}
          }
        }
        
    }
    
    
    private String getCity() {
        return ((SteampunkClockApplication) getActivity().getApplication()).getCity();
    }
    
    
    

}
