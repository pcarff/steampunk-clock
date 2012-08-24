package net.carff.android.steampunkclock;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TemperatureFragment extends Fragment {
	
	private TextView currentText;
	private TextView temperatureText;
	private String font = "duvall.ttf";
	private TextView currentDayText;
	private String  currentDay;
	
	private ImageView tempHundImage;
	private ImageView tempTenImage;
	private ImageView tempOneImage;
	private int tempHundResource;
	private int tempTenResource;
	private int tempOneResource;

	private int currentTempF;
	private ImageView currentForecastIcon;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread myThread = null;
        Runnable runnable = new TemperatureRunner();
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
		
		View view = inflater.inflate(R.layout.temperature, container, false);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), font);
		
		currentText = (TextView) view.findViewById(R.id.current_title); 
		currentText.setTextSize((float)20.0);
		currentText.setTypeface(tf);
		
		currentDayText = (TextView) view.findViewById(R.id.today_title);
		currentDayText.setTextSize((float)15.0);
		currentDayText.setTypeface(tf);
		
		temperatureText = (TextView) view.findViewById(R.id.temperature_title);
		temperatureText.setTypeface(tf);
		
		tempHundImage = (ImageView) view.findViewById(R.id.temp_hund);
        tempTenImage = (ImageView) view.findViewById(R.id.temp_ten); 
	    tempOneImage = (ImageView) view.findViewById(R.id.temp_one);
	    tempHundImage.setImageResource(tempHundResource);
	    tempTenImage.setImageResource(tempTenResource);
	    tempOneImage.setImageResource(tempOneResource);
		
		
	    //SET UP WEATHER ICON
	    currentForecastIcon = (ImageView) view.findViewById(R.id.current_forecast);
	    String currentDayIcon = SteampunkclockActivity.myWData.getCurrentIcon();
	    currentForecastIcon.setImageResource(Util.getWeatherResource(currentDayIcon));
	    
		return view;

	}
		
	
	class TemperatureRunner implements Runnable  {
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
	
    				//SETUP the Day
    				currentDay = SteampunkclockActivity.myFData.get(0).getDayOfWeek();
    				currentDayText.setText(currentDay);
    				
					//SET UP THE TEMP
					currentTempF = SteampunkclockActivity.myWData.getTempF();
					int tempHund = 0;
			        if (currentTempF > 99){
			        	tempHund = 1;
			        	currentTempF = currentTempF - 100;
			        }  
			        
			        int tempTen = currentTempF / 10;
			        int tempOne = currentTempF % 10 ;
			        
				    tempHundResource = Util.getTemperatureNumberResource(tempHund);
					tempTenResource = Util.getTemperatureNumberResource(tempTen);
					tempOneResource = Util.getTemperatureNumberResource(tempOne);
					tempHundImage.setImageResource(tempHundResource);
					tempTenImage.setImageResource(tempTenResource);
				    tempOneImage.setImageResource(tempOneResource);
				    
				    String currentWeather = SteampunkclockActivity.myWData.getCurrentIcon();
				    currentForecastIcon.setImageResource(Util.getWeatherResource(currentWeather));
				    //Log.d("TempFrag", "currentWeather " + currentWeather);
    			
    			} catch (Exception e) {
    				//Toast toast = Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG);
    		        //toast.show();
    		        
    			}
    		}
    	});
				    
	}
}
