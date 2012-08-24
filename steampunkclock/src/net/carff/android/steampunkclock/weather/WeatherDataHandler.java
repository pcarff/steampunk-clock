package net.carff.android.steampunkclock.weather;


import java.util.ArrayList;


import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class WeatherDataHandler extends DefaultHandler {
	private boolean in_weather = false;
	private boolean in_forecast_information = false;
	private boolean in_city = false;
	private boolean in_postal_code = false;
	
	WeatherData wData = new WeatherData();
	WeatherDataHandler wdh;
	private boolean in_current_conditions;
	private boolean in_condition = false;
	private boolean in_forecast_conditions = false;
	private boolean in_day_of_week = false;
	private boolean in_low = false;
	private boolean in_high = false;
	private boolean in_icon = false;

	ArrayList<ForecastData> forecastArray = new ArrayList<ForecastData>();
	private Toast toast;
	private Context mContext;
	
	
	public WeatherData getwData() {		
		return this.wData;
	}
	
	public ArrayList<ForecastData> getfData() {
		return this.forecastArray;
	}
	

	@Override 
	public void startDocument() throws SAXException {
		this.wData = new WeatherData();
	}
	
	@Override 
	public void endDocument() throws SAXException {
		//do nothing
	}
	
	
	/**  Called when tag starts (ex:- <weather> --- </weather>   <weather>)*/
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		super.startElement(uri, localName, qName, atts);
		if(localName.equals("weather"))
		{
			this.in_weather = true;
		}
		if(localName.equals("forecast_information"))
		{
			this.in_forecast_information = true;
		}
		if(localName.equals("city")) {
			//** Get attribute data**//
			String attr = atts.getValue("data");
			wData.setCity(attr);
			this.in_city = true;
		}
		if(localName.equals("postal_code")) {
			//** Get attribute data**//
			String attr = atts.getValue("data");
			wData.setZip(attr);
			this.in_postal_code = true;
		}
		if (localName.equals("current_conditions"))
		{
			this.in_current_conditions = true;
		}
		
		//if (localName.equals("condition") && this.in_current_conditions) {
		//	String attr = atts.getValue("data");
		//	wData.setCurrentIcon(attr);
		//	this.in_condition = true;
		//}
		
		if (localName.equals("icon") && this.in_current_conditions) {
			String attr = atts.getValue("data");
			wData.setIcon(attr);
			this.in_icon = true;
		}
		
		if(localName.equals("temp_f")) {
			//** Get attribute data**//
			String attr = atts.getValue("data");
			wData.setTempF(Integer.parseInt(attr));
			this.in_condition = true;
		}
		
		if (localName.equals("forecast_conditions")) {
			this.in_forecast_conditions = true;
			forecastArray.add(new ForecastData());
		}
		if (localName.equals("day_of_week")) {
			String attr = atts.getValue("data");
			forecastArray.get(wData.getForecastCounter()).setDayOfWeek(attr);
			this.in_day_of_week = true;
		}
		if (localName.equals("low")) {
			int attr = Integer.parseInt(atts.getValue("data"));
			forecastArray.get(wData.getForecastCounter()).setLow(attr);
			this.in_low = true;
		}
		if (localName.equals("high")) {
			int attr = Integer.parseInt(atts.getValue("data"));
			forecastArray.get(wData.getForecastCounter()).setHigh(attr);
			this.in_high = true;
		}
		
		if (localName.equals("icon") && this.in_forecast_conditions) {
			String attr = atts.getValue("data");
			forecastArray.get(wData.getForecastCounter()).setIcon(attr);
			this.in_icon = true;
		}
		if (localName.equals("condition") && this.in_forecast_conditions) {
			String attr = atts.getValue("data");
			forecastArray.get(wData.getForecastCounter()).setCondition(attr);
			this.in_high = true;
		}
		
		

	}
	
	/**  Called when tag ends (ex:- <weather> --- </weather>   </weather>)*/
	@Override
	public void endElement(String uri,String localName, String qName) throws SAXException {
		if(localName.equals("weather"))
		{
			this.in_weather = false;
		} 
		if(localName.equals("forecast_information")) {
			this.in_forecast_information = false;
		}
		if(localName.equals("city")) {
			this.in_city = false;
		}
		if(localName.equals("postal_code")) {
			this.in_postal_code = false;
		}
		if(localName.equals("current_conditions")){
			this.in_current_conditions = false;
			wData.setForecastCounter(0);
		}
		if (localName.equals("condition")){
			this.in_condition = false;
		}
		if (localName.equals("forecast_conditions")) {
			this.in_forecast_conditions = false;
			wData.incrementCounter();
		}
		if (localName.equals("day_of_week")) {
			this.in_day_of_week = false;
		}

		if (localName.equals("low")) {
			this.in_low = false;
		} 
		if (localName.equals("high")) {
			this.in_high = false;
		}
		if (localName.equals("icon")) {
			this.in_icon = false;
		}
		
	}
	
	
	
}

