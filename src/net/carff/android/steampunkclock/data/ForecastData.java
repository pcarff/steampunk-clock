package net.carff.android.steampunkclock.data;


public class ForecastData {
	//** Variable  **//
	private String dayOfWeek = null;
	private int low = 0;
	private int high = 0;
	private int condition;
	
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
	
	public void setCondition (int condition) {
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
	
	public int getCondition() {
		return this.condition;
	}
}
