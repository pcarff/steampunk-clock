package net.carff.android.steampunkclock.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import net.carff.android.steampunkclock.R;
import net.carff.android.steampunkclock.util.UIUtils;

import java.util.Arrays;
import java.util.Calendar;

public class SteampunkClockTimeWidgetProvider extends AppWidgetProvider {
    
    private static String TAG = "SteampunkTimeidgetProvider";
    
    private static int mHour;
    private static int mMinute;
    private static int mSecond;
    private static int mAmPm;
    
    private static String TIME_WIDGET_UPDATE = "net.carff.android.STEAMPUNKCLOCK_TIME_WIDGET_UPDATE";
    
    
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "Widget Provider enabled.  Starting timer to update widger every second");
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.SECOND, 1);
        //After after 3 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000 , 
                createClockTickIntent(context));
    }
    
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG, "Widget Provider disabled.  Turner off timer");
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(createClockTickIntent(context));
    }
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        Log.i(TAG, "Updating widgets " + Arrays.asList(appWidgetIds));
        for (int i = 0 ; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, SteampunkClockTimeWidgetProvider.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.time_widget);
            updateTimeDisplay(views);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        ComponentName thisWidget = new ComponentName(context, SteampunkClockTimeWidgetProvider.class);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    private PendingIntent createClockTickIntent(Context context) {
        Intent intent = new Intent(TIME_WIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 
                PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "Received intent " + intent);
        if (TIME_WIDGET_UPDATE.equals(intent.getAction())) {
            Log.d(TAG, "Clock Update");
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int appWidgetID: ids) {
                updateAppWidget(context, appWidgetManager, appWidgetID);
            }
        }
    }
    
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "Updating widgetId " + appWidgetId);
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.time_widget);
        updateTimeDisplay(updateViews);
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }
    
    public static void updateTimeDisplay (RemoteViews rv) {
        //Log.d(TAG, "Updating Time Diplay");
        
        Calendar cal = Calendar.getInstance();
        mHour = cal.get(Calendar.HOUR);
        mMinute = cal.get(Calendar.MINUTE);
        mSecond = cal.get(Calendar.SECOND);
        mAmPm = cal.get(Calendar.AM_PM);
        if (mAmPm == 1) {
            mHour += 12;
        }   
        
        //Log.d(TAG, "Got time data.");
   
        //Update THE HOURS
        int highHour= (int) ( mHour / 10);
        int lowHour = (int)(mHour % 10);
        
        rv.setImageViewResource(R.id.high_hour, UIUtils.getNumberResource(highHour));
        rv.setImageViewResource(R.id.low_hour, UIUtils.getNumberResource(lowHour));
        //Log.d(TAG, "Updating Hours");
        
        //Update THE MINUTES
        int highMinute =(int) ( mMinute / 10);
        int lowMinute = (int)(mMinute % 10);
        rv.setImageViewResource(R.id.high_minute, UIUtils.getNumberResource(highMinute));
        rv.setImageViewResource(R.id.low_minute, UIUtils.getNumberResource(lowMinute));
        //Log.d(TAG, "Updating Minutes");
        
        //Update THE SECONDS
        int highSecond= (int) (mSecond / 10);
        int lowSecond = (int)(mSecond % 10);
        rv.setImageViewResource(R.id.high_second, UIUtils.getNumberResource(highSecond));
        rv.setImageViewResource(R.id.low_second, UIUtils.getNumberResource(lowSecond));
        //Log.d(TAG, "Updating Seconds");
        
        
    } 
}
