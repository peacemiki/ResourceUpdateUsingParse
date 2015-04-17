package kr.co.schoolholic.core;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Kevin on 2015. 2. 12..
 * Global instance offer the constant variables while application cycle.
 */
public enum Global {
    instance;

    private Context mApplicationContext;
    private Context mTopActivityContext;
    private DisplayMetrics mDisplayMetrics;

    public void setApplicationContext(Context context) {
        mApplicationContext = context;
    }

    public Context getApplicationContext() {
        return mApplicationContext;
    }

    public void setTopActivityContext(Context context) {
        mTopActivityContext = context;
    }

    public Context getTopActivityContext() {
        return mTopActivityContext;
    }

    public DisplayMetrics getDisplayMetrics() {
        if(mDisplayMetrics == null) {
            mDisplayMetrics = new DisplayMetrics();
            WindowManager wm = (WindowManager)mApplicationContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        }

        return mDisplayMetrics;
    }

    public String getApplicationName() {
        int stringId = mApplicationContext.getApplicationInfo().labelRes;
        return mApplicationContext.getString(stringId);
    }
}
