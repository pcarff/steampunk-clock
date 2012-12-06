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

public class ForecastFragment extends Fragment {
    
    private static String TAG = "ForecastFragment";
    
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
        forecastText.setTextSize((float)20.0);
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
                    Thread.sleep(10000);  
              } catch (Exception e) {}
          }
        }
        
    }
    
public void updateDisplay () {
        
        getActivity().runOnUiThread(new Runnable() {

            public void run() {
                try {
                    int day1LoTemp = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getLow(1);         
                    int day2LoTemp = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getLow(2);
                    int day3LoTemp = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getLow(3);
                    
                    int day1HiTemp = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getHigh(1);
                    int day2HiTemp = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getHigh(2);
                    int day3HiTemp = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getHigh(3);

                    String oneDayTitle = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getDayOfWeek(1);
                    dayOneTitle.setText(oneDayTitle);
                    String twoDayTitle = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getDayOfWeek(2);
                    dayTwoTitle.setText(twoDayTitle);
                    String threeDayTitle = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getDayOfWeek(3);
                    dayThreeTitle.setText(threeDayTitle);
                    
                    dayOneTemp.setText(day1LoTemp+"/"+day1HiTemp);
                    dayTwoTemp.setText(day2LoTemp+"/"+day2HiTemp);
                    dayThreeTemp.setText(day3LoTemp+"/"+day3HiTemp);
                    
                    
                    int oneDay = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getCondition(1);
                    int twoDay = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getCondition(2);
                    int threeDay = ((SteampunkClockApplication) 
                            getActivity().getApplication()).getCondition(3);
                
                    oneDayForecastIcon.setImageResource(UIUtils.getWeatherIconResource(oneDay));
                    twoDayForecastIcon.setImageResource(UIUtils.getWeatherIconResource(twoDay));
                    threeDayForecastIcon.setImageResource(UIUtils.getWeatherIconResource(threeDay));
                    
                    //Log.d("Forecast", "ForecastData for Steampunk Updated");

                } catch (Exception e) {
                    //Toast toast = Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG);
                    //toast.show();
                    
                }
            }
        });
                
    }

}
