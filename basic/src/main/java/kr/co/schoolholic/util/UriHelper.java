package kr.co.schoolholic.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import kr.co.schoolholic.core.Global;


public class UriHelper {
    public static Uri fromResource (int resID) {
        Context context = Global.instance.getApplicationContext();
        Resources resources = context.getResources();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(resID) + '/' +
                resources.getResourceTypeName(resID) + '/' +
                resources.getResourceEntryName(resID) );
    }

    public static Uri fromRawResource (String name) {
        Context context = Global.instance.getApplicationContext();
        Resources resources = context.getResources();
        int resId = resources.getIdentifier(name, "raw", context.getPackageName());
        return fromResource(resId);
    }
}
