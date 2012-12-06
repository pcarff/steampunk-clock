package net.carff.android.steampunkclock;

import android.app.Application;

import net.carff.android.steampunkclock.data.ForecastData;
import net.carff.android.steampunkclock.data.WeatherData;

import java.util.ArrayList;


public class SteampunkClockApplication extends Application {
    
    private WeatherData mWeatherData;
    private ArrayList<ForecastData> mForecastData;
    
    private boolean twelveHourMode = true;
    
    @Override
    public void onCreate() {
        super.onCreate();
        mWeatherData = new WeatherData();
        mForecastData = new ArrayList<ForecastData>();
        //Pull data from config file and retore..
        mWeatherData.setCity("pending");
    }
    
    public void setCity(String c) {
        mWeatherData.setCity(c);
    }
    
    public String getCity() {
        return mWeatherData.getCity();
    }
    
    public void setTempF(int t) {
        mWeatherData.setTempF(t);
    }
    
    public int getTempF(){
        return mWeatherData.getTempF();
    }
    
    public void setDay(String d) {
        mWeatherData.setDay(d);
    }
    
    public String getDay() {
        return mWeatherData.getDay();
    }
    
    public void setCurrentCondition(int cc) {
        mWeatherData.setCurrentCondition(cc);
    }
    
    public int getCurrentCondition() {
        return mWeatherData.getCurrentCondition();
    }
    
    public void addForecast() {
        mForecastData.add(new ForecastData());
    }
    
    public void setHigh(int counter, int th) {
        mForecastData.get(counter).setHigh(th);
    }
    public void setLow(int counter, int tl) {
        mForecastData.get(counter).setLow(tl);
    }
    
    public void setCondition(int counter, int c) {
        mForecastData.get(counter).setCondition(c);
    }
    
    public int getHigh(int counter) {
        return mForecastData.get(counter).getHigh();
    }
    
    public int getLow(int counter) {
        return mForecastData.get(counter).getLow();
    }
    public int getCondition(int counter) {
        return mForecastData.get(counter).getCondition();
    }
    
    public void setDayOfWeek(int counter, String d) {
        mForecastData.get(counter).setDayOfWeek(d);
    }
    
    public String getDayOfWeek(int counter) {
        return mForecastData.get(counter).getDayOfWeek();
    }
    
    public void setTwelveHour(boolean t) {
        twelveHourMode = t;
    }
    
    public boolean getTwelveHour() {
        return twelveHourMode;
    }

}
