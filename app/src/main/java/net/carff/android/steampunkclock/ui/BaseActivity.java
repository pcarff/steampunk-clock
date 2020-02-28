package net.carff.android.steampunkclock.ui;

import android.app.Activity;
import android.os.Bundle;

import net.carff.android.steampunkclock.util.UIUtils;

public abstract class BaseActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (!UIUtils.isGoogleTV(this)) {
            /*<TODO> (pcarff) add adjustments here.*/
            
        }
    }

}
