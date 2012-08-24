package net.carff.android.steampunkclock;


import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocationFragment extends Fragment {
	private String font = "duvall.ttf";
	private TextView locationText;
	private TextView cityText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Thread myThread = null;
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
		cityText.setTypeface(tf);
		
	    return view;

	}
	
	class LocationRunner implements Runnable  {
    	//@Override  
        public void run() {  
          while (!Thread.currentThread().isInterrupted()) {
        	  try {
        		  updateDisplay();
        		  	Thread.sleep(1200000);  
        	  } catch (Exception e) {}
          }
    	}
    	
    }
	
	public void updateDisplay () {
		
    	getActivity().runOnUiThread(new Runnable() {
    		
			public void run() {
    			try {
    				cityText.setText(SteampunkclockActivity.myWData.getCity());
    				Log.e("Location", "Location Data for Steampunk " + SteampunkclockActivity.myWData.getCity());

    			} catch (Exception e) {
    				//Toast toast = Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG);
    		        //toast.show();
    		        
    			}
    		}
    	});
                
    }
	
}