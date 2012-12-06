package net.carff.android.steampunkclock.ui;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import net.carff.android.steampunkclock.R;
import net.carff.android.steampunkclock.SteampunkClockApplication;
import net.carff.android.steampunkclock.R.id;
import net.carff.android.steampunkclock.R.layout;
import net.carff.android.steampunkclock.util.UIUtils;

import java.util.Calendar;

public class TimeFragment extends Fragment {

    private TextView timeText;
    private TextView hourText;
    private TextView minuteText;
    private TextView secondText;
    private String font = "duvall.ttf";
    private ImageView highHourImage;
    private ImageView lowHourImage;
    private int highHourResource;
    private int lowHourResource;
    private ImageView highMinuteImage;
    private ImageView lowMinuteImage;
    private int highMinuteResource;
    private int lowMinuteResource;
    private ImageView highSecondImage;
    private ImageView lowSecondImage;
    private int highSecondResource;
    private int lowSecondResource;
    
    private RadioButton hr12Button;
    private RadioButton hr24Button;
    
    private int mHour;
    private int mMinute;
    private int mSecond;
    private int mAmPm;
    
    private RadioGroup hourRadioGroup;
    private static final int[] HOUR_CONTROLS =
            new int[] { R.id.hr12, R.id.hr24};
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread myThread = null;
        
       

        Runnable runnable = new TimeRunner();
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
        
        View view = inflater.inflate(R.layout.time, container, false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), font);
        
        hourRadioGroup = (RadioGroup) view.findViewById(R.id.hour_group);
        hr12Button = (RadioButton) view.findViewById(R.id.hr12);
        hr24Button = (RadioButton) view.findViewById(R.id.hr24);
        
        hourRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hr12: 
                        ((SteampunkClockApplication) getActivity().getApplication()).setTwelveHour(true);
                        break;
                    case R.id.hr24: 
                        ((SteampunkClockApplication) getActivity().getApplication()).setTwelveHour(false);
                    break;

                }
            }
        });
       
        
        timeText = (TextView) view.findViewById(R.id.time_title);
        timeText.setTextSize((float)20.0);
        timeText.setTypeface(tf);
        
        hourText = (TextView) view.findViewById(R.id.hour_title);
        hourText.setTypeface(tf);
        
        minuteText = (TextView) view.findViewById(R.id.minute_title);
        minuteText.setTypeface(tf);
        
        secondText = (TextView) view.findViewById(R.id.second_title);
        secondText.setTypeface(tf);
        
        highHourImage = (ImageView) view.findViewById(R.id.high_hour);
        lowHourImage = (ImageView) view.findViewById(R.id.low_hour);        
        highHourImage.setImageResource(highHourResource);
        lowHourImage.setImageResource(lowHourResource);
        highMinuteImage = (ImageView) view.findViewById(R.id.high_minute);
        lowMinuteImage = (ImageView) view.findViewById(R.id.low_minute);        
        highMinuteImage.setImageResource(highMinuteResource);
        lowMinuteImage.setImageResource(lowMinuteResource);        
        highSecondImage = (ImageView) view.findViewById(R.id.high_second);
        lowSecondImage = (ImageView) view.findViewById(R.id.low_second);        
        highSecondImage.setImageResource(highSecondResource);
        lowSecondImage.setImageResource(lowSecondResource);
        //highDividerImage = (ImageView) view.findViewById(R.id.time_divider_high);
        //lowDividerImage = (ImageView) view.findViewById(R.id.time_divider_low);        
        //highDividerImage.setImageResource(highDividerResource);
        //lowDividerImage.setImageResource(lowDividerResource);
        return view;
    }

    public void setText(String item) {
        TextView view = (TextView) getView().findViewById(R.id.time_title);
        view.setText(item);
    }
    
    public void updateDisplay () {
    
        getActivity().runOnUiThread(new Runnable() {

            public void run() {
                try {
                    final Calendar cal = Calendar.getInstance();
                    mHour = cal.get(Calendar.HOUR_OF_DAY);
                    mMinute = cal.get(Calendar.MINUTE);
                    mSecond = cal.get(Calendar.SECOND);
                    
                    mAmPm = cal.get(Calendar.AM_PM);
                    
                    
                    if (((SteampunkClockApplication) getActivity().getApplication()).getTwelveHour()) {
                        hr12Button.setChecked(true);
                        if ((mAmPm == Calendar.PM)) {
                            mHour -= 12;
                        }
                    } else {
                        hr24Button.setChecked(true);
                    }
                  
               
                    //Update THE HOURS
                    int highHour= (int) ( mHour / 10);
                    int lowHour = (int)(mHour % 10);

                    highHourResource = UIUtils.getNumberResource(highHour);
                    lowHourResource = UIUtils.getNumberResource(lowHour);
                    highHourImage.setImageResource(highHourResource);
                    lowHourImage.setImageResource(lowHourResource);
                    
                    //Update THE MINUTES
                    int highMinute =(int) ( mMinute / 10);
                    int lowMinute = (int)(mMinute % 10);
                    highMinuteResource = UIUtils.getNumberResource(highMinute);
                    lowMinuteResource = UIUtils.getNumberResource(lowMinute);
                    highMinuteImage.setImageResource(highMinuteResource);
                    lowMinuteImage.setImageResource(lowMinuteResource);
                    
                    //Update THE SECONDS
                    int highSecond= (int) (mSecond / 10);
                    int lowSecond = (int)(mSecond % 10);
                    highSecondResource = UIUtils.getNumberResource(highSecond);
                    lowSecondResource = UIUtils.getNumberResource(lowSecond);
                    highSecondImage.setImageResource(highSecondResource);
                    lowSecondImage.setImageResource(lowSecondResource);


                } catch (Exception e) {
                    //Toast toast = Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG);
                    //toast.show();
                    
                }
            }
        });
                
    }
    
    class TimeRunner implements Runnable  {
        //@Override  
        public void run() {  
          while (!Thread.currentThread().isInterrupted()) {
              try {
                  updateDisplay();
                    Thread.sleep(1000);  
              } catch (Exception e) {}
          }
        }
        
    }
}
