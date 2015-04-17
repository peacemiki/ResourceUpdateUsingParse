package kr.co.schoolholic.resourceupdater;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

import kr.co.schoolholic.core.MyApplication;

/**
 * Created by kevin on 15. 4. 16..
 */
public class ParseApplication extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Enable Crash Reporting
        ParseCrashReporting.enable(this);


        Parse.initialize(this, "OSLl1I5g4b1b1lH5QX3rvJDEJK204TKpBYvxCgrb", "ZBAHxoVsTslKZYGrGwp4WjvGR6ZrXq3s41WKDIA3");
    }
}
