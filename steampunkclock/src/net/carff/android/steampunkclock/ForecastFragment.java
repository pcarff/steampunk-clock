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

public class ForecastFragment extends Fragment {
	
	private String font="duvall.ttf";
	private TextView forecastText;
	private TextView dayOneTemp;
	private TextView dayTwoTemp;
	private TextView dayThreeTemp;
	private ImageView oneDayForecastIcon;
	private ImageView twoDayForecastIcon;
	private ImageView threeDayForecastIcon;
	private TextView dayOneTitle;
	private TextView dayTwoTitle;
	private TextView dayThreeTitle;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread myThread = null;
        Runnable runnable = new ForecastRunner();
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
		View view = inflater.inflate(R.layout.forecast, container, false);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), font);
		
		forecastText = (TextView) view.findViewById(R.id.forecast_title);
		//forecastText.setTextSize((float)20.0);
		forecastText.setTypeface(tf);
		
		dayOneTemp = (TextView) view.findViewById(R.id.day_one_temp);
		dayTwoTemp = (TextView) view.findViewById(R.id.day_two_temp);
		dayThreeTemp = (TextView) view.findViewById(R.id.day_three_temp);
		
		dayOneTemp.setTypeface(tf);
		dayTwoTemp.setTypeface(tf);
		dayThreeTemp.setTypeface(tf);
		
		dayOneTitle = (TextView) view.findViewById(R.id.one_day_title);
		dayTwoTitle = (TextView) view.findViewById(R.id.two_day_title);
		dayThreeTitle = (TextView) view.findViewById(R.id.three_day_title);
		
		dayOneTitle.setTypeface(tf);
		dayTwoTitle.setTypeface(tf);
		dayThreeTitle.setTypeface(tf);
		
		//SET UP WEATHER ICON
	    oneDayForecastIcon = (ImageView) view.findViewById(R.id.one_day_forecast);
	    twoDayForecastIcon = (ImageView) view.findViewById(R.id.two_day_forecast);
	    threeDayForecastIcon = (ImageView) view.findViewById(R.id.three_day_forecast);

		return view;
	}
	
	class ForecastRunner implements Runnable  {
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
    				String day1LoTemp = Integer.toString(SteampunkclockActivity.myFData.get(1).getLow());
    		        String day2LoTemp = Integer.toString(SteampunkclockActivity.myFData.get(2).getLow());
    		        String day3LoTemp = Integer.toString(SteampunkclockActivity.myFData.get(3).getLow());
    		        
    				String day1HiTemp = Integer.toString(SteampunkclockActivity.myFData.get(1).getHigh());
    		        String day2HiTemp = Integer.toString(SteampunkclockActivity.myFData.get(2).getHigh());
    		        String day3HiTemp = Integer.toString(SteampunkclockActivity.myFData.get(3).getHigh());

    		        String oneDayTitle = SteampunkclockActivity.myFData.get(1).getDayOfWeek();
    		        dayOneTitle.setText(oneDayTitle);
    		        String twoDayTitle = SteampunkclockActivity.myFData.get(2).getDayOfWeek();
    		        dayTwoTitle.setText(twoDayTitle);
    		        String threeDayTitle = SteampunkclockActivity.myFData.get(3).getDayOfWeek();
    		        dayThreeTitle.setText(threeDayTitle);
    		        
    		        dayOneTemp.setText(day1LoTemp+"/"+day1HiTemp);
    		        dayTwoTemp.setText(day2LoTemp+"/"+day2HiTemp);
    		        dayThreeTemp.setText(day3LoTemp+"/"+day3HiTemp);
    		        
    		        
    			    String oneDay = SteampunkclockActivity.myFData.get(1).getIcon();
    			    String twoDay = SteampunkclockActivity.myFData.get(2).getIcon();
    			    String threeDay = SteampunkclockActivity.myFData.get(3).getIcon();
 			    
    			    oneDayForecastIcon.setImageResource(Util.getWeatherResource(oneDay));
    			    twoDayForecastIcon.setImageResource(Util.getWeatherResource(twoDay));
    			    threeDayForecastIcon.setImageResource(Util.getWeatherResource(threeDay));
    				
    				Log.e("Forecast", "ForecastData for Steampunk Updated");

    			} catch (Exception e) {
    				//Toast toast = Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG);
    		        //toast.show();
    		        
    			}
    		}
    	});
                
    }

	public void setText(String item) {
		TextView view = (TextView) getView().findViewById(R.id.forecast_title);
		view.setText(item);
	}
	
}
