package net.carff.android.steampunkclock.weather;

public class WeatherData {
	//**Variables**/
	private String city = null;
	private String zip = null;
	//current weather
	private String currentCondition = null;
	private int tempF = 0;
	private String tempC = null;
	private String humidity = null;
 	private String currentIcon = null;
 	private String wind = null;
 	private int forecast_counter = 0;
 	//private ForecastData fData;
 	
 	
 	//Setters
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}
	
	public void setTempF(int tempF) {
		this.tempF = tempF;
	}
	
	public void setTempC(String tempC) {
		this.tempC = tempC;
	}
	
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	//public void setCurrentIcon(String currentIcon) {		
		//this.currentIcon = currentIcon;
	//}
	
	public void setIcon (String icon) {
		String filename = null;
		String url = null;
		url = "http://www.google.com" + icon;
		filename = url.substring(url.lastIndexOf('/')+1, url.lastIndexOf('.'));		
		this.currentIcon = filename;
	}
	
	public void setWind(String wind) {
		this.wind = wind;
	}
	
	public void setForecastCounter(int counter) {
		this.forecast_counter = counter;
		
	}
	
	//Getters
	public String getCity() {
		return this.city;
	}
	
	public String getZip() {
		return this.zip;
	}

	public String getCurrentCondition() {
		return this.currentCondition;
	}
	
	public int getTempF() {
		return this.tempF;
	}
	
	public String getTempC() {
		return this.tempC;
	}
	
	public String getHumidity() {
		return this.humidity;
	}
	
	public String getCurrentIcon() {
		return this.currentIcon;
	}
	
	public String getWind() {
		return this.wind;
	}
	
	public int getForecastCounter() {
		return this.forecast_counter;
	}
	
	public void incrementCounter() {
		this.forecast_counter +=1;
	}
	
}
