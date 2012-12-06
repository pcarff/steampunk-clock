package net.carff.android.steampunkclock.util;

import android.content.Context;
import android.util.Log;

import net.carff.android.steampunkclock.R;

import java.util.Calendar;

public class UIUtils {
    
    private static String TAG = "UIUtils";

    public UIUtils() {
    }
    
    

    public static int getNumberResource(int number) {
        int mResource = R.drawable.zeroam; 
        
        switch (number) {
        case 0: mResource = R.drawable.zeroam; break;
        case 1: mResource = R.drawable.oneam; break;
        case 2: mResource = R.drawable.twoam; break;
        case 3: mResource = R.drawable.threeam; break;
        case 4: mResource = R.drawable.fouram; break;
        case 5: mResource = R.drawable.fiveam; break;
        case 6: mResource = R.drawable.sixam; break;
        case 7: mResource = R.drawable.sevenam; break;
        case 8: mResource = R.drawable.eightam; break;
        case 9: mResource = R.drawable.nineam; break;
        }
        return mResource;
    }
    
    public static String getDayOfWeek(int day){
        String dayOfWeek ="";
        switch (day){
        case 1: dayOfWeek = "Sunday"; break;
        case 2: dayOfWeek = "Monday"; break;
        case 3: dayOfWeek = "Tuesday"; break;
        case 4: dayOfWeek = "Wednesday"; break;
        case 5: dayOfWeek = "Thursday"; break;
        case 6: dayOfWeek = "Friday"; break;
        case 7: dayOfWeek = "Saturday"; break;
        
        }
        return dayOfWeek;
    }

    public static boolean isGoogleTV(Context context) {
        return context.getPackageManager().hasSystemFeature("com.google.android.tv");
    
    }
    
    public static boolean isDay() {
        boolean day = true;
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.HOUR_OF_DAY) >= 16 || 
                (cal.get(Calendar.HOUR_OF_DAY) <= 5)) {
            day = false;
        }
        return day;
    }
    
    
    public static int getWeatherIconResource(int weatherCode) {
        int mResource = R.drawable.day_113;
        //Log.d(TAG, "weather resource code: " + weatherCode);
        
        if (isDay()) {
            switch (weatherCode) {
                case 395: mResource = R.drawable.day_395; break;
                case 392: mResource = R.drawable.day_392; break;
                case 389: mResource = R.drawable.day_389; break;
                case 386: mResource = R.drawable.day_386; break;
                case 377: mResource = R.drawable.day_377; break;
                case 374: mResource = R.drawable.day_374; break;
                case 371: mResource = R.drawable.day_371; break;
                case 368: mResource = R.drawable.day_368; break;
                case 365: mResource = R.drawable.day_365; break;
                case 362: mResource = R.drawable.day_362; break;
                case 359: mResource = R.drawable.day_359; break;
                case 356: mResource = R.drawable.day_356; break;
                case 353: mResource = R.drawable.day_353; break;
                case 350: mResource = R.drawable.day_350; break;
                case 338: mResource = R.drawable.day_338; break;
                case 335: mResource = R.drawable.day_335; break;
                case 332: mResource = R.drawable.day_332; break;
                case 329: mResource = R.drawable.day_329; break;
                case 326: mResource = R.drawable.day_326; break;
                case 323: mResource = R.drawable.day_323; break;
                case 320: mResource = R.drawable.day_320; break;
                case 317: mResource = R.drawable.day_317; break;
                case 314: mResource = R.drawable.day_314; break;
                case 311: mResource = R.drawable.day_311; break;
                case 308: mResource = R.drawable.day_308; break;
                case 305: mResource = R.drawable.day_305; break;
                case 302: mResource = R.drawable.day_302; break;
                case 299: mResource = R.drawable.day_299; break;
                case 296: mResource = R.drawable.day_296; break;
                case 293: mResource = R.drawable.day_293; break;
                case 284: mResource = R.drawable.day_284; break;
                case 281: mResource = R.drawable.day_281; break;
                case 266: mResource = R.drawable.day_266; break;
                case 263: mResource = R.drawable.day_263; break;
                case 260: mResource = R.drawable.day_260; break;
                case 248: mResource = R.drawable.day_248; break;
                case 230: mResource = R.drawable.day_230; break;
                case 227: mResource = R.drawable.day_227; break;
                case 200: mResource = R.drawable.day_200; break;
                case 185: mResource = R.drawable.day_185; break;
                case 182: mResource = R.drawable.day_182; break;
                case 179: mResource = R.drawable.day_179; break;
                case 176: mResource = R.drawable.day_176; break;
                case 143: mResource = R.drawable.day_143; break;
                case 122: mResource = R.drawable.day_122; break;
                case 119: mResource = R.drawable.day_119; break;
                case 116: mResource = R.drawable.day_116; break;
                case 113: mResource = R.drawable.day_113; break;
            }
        } else {
            switch (weatherCode) {
                case 395: mResource = R.drawable.night_395; break;
                case 392: mResource = R.drawable.night_392; break;
                case 389: mResource = R.drawable.night_389; break;
                case 386: mResource = R.drawable.night_386; break;
                case 377: mResource = R.drawable.night_377; break;
                case 374: mResource = R.drawable.night_374; break;
                case 371: mResource = R.drawable.night_371; break;
                case 368: mResource = R.drawable.night_368; break;
                case 365: mResource = R.drawable.night_365; break;
                case 362: mResource = R.drawable.night_362; break;
                case 359: mResource = R.drawable.night_359; break;
                case 356: mResource = R.drawable.night_356; break;
                case 353: mResource = R.drawable.night_353; break;
                case 350: mResource = R.drawable.night_350; break;
                case 338: mResource = R.drawable.night_338; break;
                case 335: mResource = R.drawable.night_335; break;
                case 332: mResource = R.drawable.night_332; break;
                case 329: mResource = R.drawable.night_329; break;
                case 326: mResource = R.drawable.night_326; break;
                case 323: mResource = R.drawable.night_323; break;
                case 320: mResource = R.drawable.night_320; break;
                case 317: mResource = R.drawable.night_317; break;
                case 314: mResource = R.drawable.night_314; break;
                case 311: mResource = R.drawable.night_311; break;
                case 308: mResource = R.drawable.night_308; break;
                case 305: mResource = R.drawable.night_305; break;
                case 302: mResource = R.drawable.night_302; break;
                case 299: mResource = R.drawable.night_299; break;
                case 296: mResource = R.drawable.night_296; break;
                case 293: mResource = R.drawable.night_293; break;
                case 284: mResource = R.drawable.night_284; break;
                case 281: mResource = R.drawable.night_281; break;
                case 266: mResource = R.drawable.night_266; break;
                case 263: mResource = R.drawable.night_263; break;
                case 260: mResource = R.drawable.night_260; break;
                case 248: mResource = R.drawable.night_248; break;
                case 230: mResource = R.drawable.night_230; break;
                case 227: mResource = R.drawable.night_227; break;
                case 200: mResource = R.drawable.night_200; break;
                case 185: mResource = R.drawable.night_185; break;
                case 182: mResource = R.drawable.night_182; break;
                case 179: mResource = R.drawable.night_179; break;
                case 176: mResource = R.drawable.night_176; break;
                case 143: mResource = R.drawable.night_143; break;
                case 122: mResource = R.drawable.night_122; break;
                case 119: mResource = R.drawable.night_119; break;
                case 116: mResource = R.drawable.night_116; break;
                case 113: mResource = R.drawable.night_113; break;
            }
        }
        return mResource;
    }

}
