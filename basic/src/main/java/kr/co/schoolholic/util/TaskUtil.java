package kr.co.schoolholic.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import kr.co.schoolholic.core.Global;

/**
 * Created by kevin on 15. 2. 11..
 */
public enum TaskUtil implements Application.ActivityLifecycleCallbacks {
    instance;

    private boolean isApplicationInForeground = false;

    /**
     * Note that if you use this method, you shouldn't check in your Activity's onPause().
     * This is wrong place for checking Application is in the background.
     * You should check in your Activity's onStop method, after you call super.onStop().
     * @return true if application is running in foreground, false otherwise.
     */
    public boolean isApplicationInForeground() {
        return isApplicationInForeground;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//        Trace.i("onActivityCreated. activity name = " + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
//        Trace.i("onActivityStarted. activity name = " + activity.getLocalClassName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        Trace.i("onActivityResumed. activity name = " + activity.getLocalClassName());
        isApplicationInForeground = true;
        Global.instance.setTopActivityContext(activity);
    }

    @Override
    /**
     * This method will be called after super.onPause();
     */
    public void onActivityPaused(Activity activity) {
//        Trace.i("onActivityPaused. activity name = " + activity.getLocalClassName());
        isApplicationInForeground = false;
    }

    @Override
    /**
     * This method will be called after super.onStop();
     */
    public void onActivityStopped(Activity activity) {
//        Trace.i("onActivityStopped. activity name = " + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//        Trace.i("onActivitySaveInstanceState. activity name = " + activity.getLocalClassName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        Trace.i("onActivityDestroyed. activity name = " + activity.getLocalClassName());
    }

}
