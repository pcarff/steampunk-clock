package net.carff.android.steampunkclock.widget;

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

public final class SteampunkClockDateWidgetProvider extends AppWidgetProvider {

private static String TAG = "SteampunkClockDateWidgetProvider";
    
    private static String DATE_WIDGET_UPDATE = "net.carff.android.STEAMPUNKCLOCK_DATE_WIDGET_UPDATE";
    
    private static int mMonth;
    private static int mDay;
    private static int mYear;
    private static String mDayOfWeek;
    
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
    
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);

    }
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        Log.i(TAG, "Updating widgets " + Arrays.asList(appWidgetIds));
        for (int i = 0 ; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, SteampunkClockDateWidgetProvider.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.date_widget);
            updateDateDisplay(views);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        ComponentName thisWidget = new ComponentName(context, SteampunkClockDateWidgetProvider.class);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "Received intent " + intent);
        if (DATE_WIDGET_UPDATE.equals(intent.getAction())) {
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
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.date_widget);
        updateDateDisplay(updateViews);
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }
    
    
    public static void updateDateDisplay(RemoteViews rv) {
        
        Calendar cal = Calendar.getInstance();
        mMonth = cal.get(Calendar.MONTH)+1;
        mDay = cal.get(Calendar.DATE);
        mDayOfWeek = UIUtils.getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
        mYear = cal.get(Calendar.YEAR);                 
   
        //SET UP THE MONTH
        int highMonth = (int) ( mMonth / 10);
        int lowMonth = (int)(mMonth % 10);
        rv.setImageViewResource(R.id.high_month, UIUtils.getNumberResource(highMonth));
        rv.setImageViewResource(R.id.low_month, UIUtils.getNumberResource(lowMonth));
        
        //SET UP THE DAY
        int highDay =(int) ( mDay / 10);
        int lowDay = (int)(mDay % 10);
        //locationText.setText("mDay= " + mDay);
        rv.setImageViewResource(R.id.high_day, UIUtils.getNumberResource(highDay));
        rv.setImageViewResource(R.id.low_day, UIUtils.getNumberResource(lowDay));
        
        //SET UP THE YEAR
        int highYear = (int)((mYear-2000) / 10);
        //locationText.setText("mYear= " + mYear);
        int lowYear = (int)(mYear % 10);
        rv.setImageViewResource(R.id.high_year, UIUtils.getNumberResource(highYear));
        rv.setImageViewResource(R.id.low_year, UIUtils.getNumberResource(lowYear));
    }
    
    
}
