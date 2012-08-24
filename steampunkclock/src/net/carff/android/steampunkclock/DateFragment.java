package net.carff.android.steampunkclock;

import java.util.Calendar;

import net.carff.android.steampunkclock.TimeFragment.TimeRunner;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DateFragment extends Fragment {

	private TextView dateText;
	private String font = "duvall.ttf";
	private int mMonth;
	private int mDay;
	private int mYear;
	private String mDayOfWeek;
	
	private ImageView highMonthImage;
	private ImageView lowMonthImage;
	private int highMonthResource;
	private int lowMonthResource;
	private ImageView highDayImage;
	private ImageView lowDayImage;
	private int highDayResource;
	private int lowDayResource;
	private ImageView highYearImage;
	private ImageView lowYearImage;
	private int highYearResource;
	private int lowYearResource;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread myThread = null;

        Runnable runnable = new DateRunner();
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
		View view = inflater.inflate(R.layout.date, container, false);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), font);
		
		dateText = (TextView) view.findViewById(R.id.date_title);
		dateText.setTextSize((float)20.0);
		dateText.setTypeface(tf);

		highMonthImage = (ImageView) view.findViewById(R.id.high_month);
        lowMonthImage = (ImageView) view.findViewById(R.id.low_month);        
        highMonthImage.setImageResource(highMonthResource);
        lowMonthImage.setImageResource(lowMonthResource);
        highDayImage = (ImageView) view.findViewById(R.id.high_day);
        lowDayImage = (ImageView) view.findViewById(R.id.low_day);        
        highDayImage.setImageResource(highDayResource);
        lowDayImage.setImageResource(lowDayResource);        
        highYearImage = (ImageView) view.findViewById(R.id.high_year);
        lowYearImage = (ImageView) view.findViewById(R.id.low_year);        
        highYearImage.setImageResource(highYearResource);
        lowYearImage.setImageResource(lowYearResource);
		
		return view;
	}

	public void setText(String item) {
		TextView view = (TextView) getView().findViewById(R.id.date_title);
		view.setText(item);
	}
	
	public void updateDisplay () {
		
    	getActivity().runOnUiThread(new Runnable() {
    		
    		
			private Context mContext;

			public void run() {
    			try {
    				final Calendar cal = Calendar.getInstance();
    				mMonth = cal.get(Calendar.MONTH)+1;
    		        mDay = cal.get(Calendar.DATE);
    		        mDayOfWeek = getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
    		        mYear = cal.get(Calendar.YEAR);   		        
   		       
    				//SET UP THE MONTH
    		        int highMonth = (int) ( mMonth / 10);
    				int lowMonth = (int)(mMonth % 10);
    				highMonthResource = Util.getNumberResource(highMonth);
    				lowMonthResource = Util.getNumberResource(lowMonth);
    				highMonthImage.setImageResource(highMonthResource);
    		        lowMonthImage.setImageResource(lowMonthResource);
    		        
    				//SET UP THE DAY
    		        int highDay =(int) ( mDay / 10);
    				int lowDay = (int)(mDay % 10);
    				//locationText.setText("mDay= " + mDay);
    				highDayResource = Util.getNumberResource(highDay);
    				lowDayResource = Util.getNumberResource(lowDay);
    				highDayImage.setImageResource(highDayResource);
    		        lowDayImage.setImageResource(lowDayResource);
    		        
    		        //SET UP THE YEAR
    		        int highYear = (int)((mYear-2000) / 10);
    		        //locationText.setText("mYear= " + mYear);
    		        int lowYear = (int)(mYear % 10);
    				highYearResource = Util.getNumberResource(highYear);
    				lowYearResource = Util.getNumberResource(lowYear);
    				highYearImage.setImageResource(highYearResource);
    		        lowYearImage.setImageResource(lowYearResource);

    			} catch (Exception e) {
    				//Toast toast = Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG);
    		        //toast.show();
    		        
    			}
    		}
    	});
                
    }
	
	class DateRunner implements Runnable  {
    	//@Override  
        public void run() {  
          while (!Thread.currentThread().isInterrupted()) {
        	  try {
        		  updateDisplay();
        		  	Thread.sleep(144000);  
        	  } catch (Exception e) {}
          }
    	}
    	
    }
	
	protected String getDayOfWeek(int day){
    	String dayOfWeek ="";
    	switch (day){
    	case 1: dayOfWeek = "Sunday"; break;
    	case 2: dayOfWeek = "Monday"; break;
    	case 3: dayOfWeek = "Tuesday"; break;
    	case 4: dayOfWeek = "Wednesday"; break;
    	case 5: dayOfWeek = "Thursday"; break;
    	case 6: dayOfWeek = "Friday"; break;
    	case 7: dayOfWeek = "Saturday"; break;
    	
    	}
    	return dayOfWeek;
    }
}
