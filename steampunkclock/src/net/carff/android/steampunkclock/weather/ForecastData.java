package net.carff.android.steampunkclock.weather;

import java.io.IOException;
import java.net.URL;

public class ForecastData {
	//** Variable  **//
	private String dayOfWeek = null;
	private int low = 0;
	private int high = 0;
	private String icon = null; 
	private String condition = null;
	
	//Setters
	public void setDayOfWeek(String dow) {
		this.dayOfWeek=dow;
	}
	
	public void setLow (int low) {
		this.low = low;
	}
	
	public void setHigh (int high) {
		this.high = high;
	}
	
	public void setIcon (String icon) {
		String filename = null;
		String url = null;
		url = "http://www.google.com" + icon;
		filename = url.substring(url.lastIndexOf('/')+1, url.lastIndexOf('.'));		
		this.icon = filename;
	}
	
	public void setCondition (String condition) {
		this.condition = condition;
	}
	
	
	
	//Getters
	public String getDayOfWeek() {
		return this.dayOfWeek;
	}
	
	public int getLow() {
		return this.low;
	}
	
	public int getHigh() {
		return this.high;
	}
	
	public String getIcon() {
		return this.icon;
	}
	
	
	public String getCondition() {
		return this.condition;
	}
}
