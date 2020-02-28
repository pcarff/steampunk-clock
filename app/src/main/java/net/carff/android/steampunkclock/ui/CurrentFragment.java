package net.carff.android.steampunkclock.ui;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.carff.android.steampunkclock.R;
import net.carff.android.steampunkclock.SteampunkClockApplication;
import net.carff.android.steampunkclock.util.UIUtils;

public class CurrentFragment extends Fragment {

    private String TAG = "CurrentFragment";
    private TextView currentText;
    private TextView dayText;
    private String font = "duvall.ttf";
    
    private ImageView tempHundImage;
    private ImageView tempTenImage;
    private ImageView tempOneImage;
    
    private int tempHundResource;
    private int tempTenResource;
    private int tempOneResource;
    
    private int currentTempF;
    private int currentCondition;
    
    private ImageView currentForecastIcon;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread myThread = null;
        
        updateDisplay();

        System.out.println("Starting Location Runner");
        Runnable runnable = new CurrentRunner();
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
        
        View view = inflater.inflate(R.layout.current, container, false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), font);
        
        currentText = (TextView) view.findViewById(R.id.current_title);
        currentText.setTypeface(tf);
        
        dayText = (TextView) view.findViewById(R.id.day_title);
        dayText.setTextSize((float)20.0);
        dayText.setTypeface(tf);
        
        currentForecastIcon = (ImageView) view.findViewById(R.id.current_forecast);
        
        
        tempHundImage = (ImageView) view.findViewById(R.id.temp_hund);
        tempTenImage = (ImageView) view.findViewById(R.id.temp_ten); 
        tempOneImage = (ImageView) view.findViewById(R.id.temp_one);
        tempHundImage.setImageResource(tempHundResource);
        tempTenImage.setImageResource(tempTenResource);
        tempOneImage.setImageResource(tempOneResource);
        
        return view;
        
    }
    
    
    
    public void updateDisplay () {
        
        getActivity().runOnUiThread(new Runnable() {

            public void run() {
                try {
                    //Log.d(TAG, "Updating Day: " + getDay());
                    dayText.setText(getDay());
                    
                    currentTempF = getTempF();
                   // Log.d(TAG, "tempF " + currentTempF);
                    int tempHund = 0;
                    if (currentTempF > 99){
                        tempHund = 1;
                        currentTempF = currentTempF - 100;
                    }  
                    
                    int tempTen = currentTempF / 10;
                    int tempOne = currentTempF % 10 ;
                    
                    tempHundResource = UIUtils.getNumberResource(tempHund);
                    tempTenResource = UIUtils.getNumberResource(tempTen);
                    tempOneResource = UIUtils.getNumberResource(tempOne);
                    tempHundImage.setImageResource(tempHundResource);
                    tempTenImage.setImageResource(tempTenResource);
                    tempOneImage.setImageResource(tempOneResource);
                    
                    currentCondition = getCurrentCondition();
                    //Log.d(TAG, "CurrentCondition: " + currentCondition);
                    currentForecastIcon.setImageResource(UIUtils.getWeatherIconResource(currentCondition));
                    
                    
                    //checkData();
                    
                } catch (Exception e) {
                    //Toast toast = Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG);
                    //toast.show();
                    
                }
            }
        });
                
    }
    
    class CurrentRunner implements Runnable  {
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
    
    
    private int getTempF() {
        return ((SteampunkClockApplication) getActivity().getApplication()).getTempF();
    }
    
    private String getDay() {
        //return "TODAY";
        return ((SteampunkClockApplication) getActivity().getApplication()).getDay();
    }
    
    private int getCurrentCondition() {
        return((SteampunkClockApplication) getActivity().getApplication()).getCurrentCondition();
    }
    
    private void checkData() {
        for (int i = 0; i<4 ; i++) {
            Log.d(TAG, "Day: " + ((SteampunkClockApplication) getActivity().getApplication()).getDayOfWeek(i));
            Log.d(TAG, "High: " + ((SteampunkClockApplication) getActivity().getApplication()).getHigh(i));
            Log.d(TAG, "Low: " + ((SteampunkClockApplication) getActivity().getApplication()).getLow(i));
            Log.d(TAG, "Condition: " + ((SteampunkClockApplication) getActivity().getApplication()).getCondition(i));
            
        }
    }
    

}
