package kr.co.schoolholic.resourceupdater;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import kr.co.schoolholic.util.Trace;


public class MainActivity extends ActionBarActivity implements ResourceUpdater.onUpdateListener {

    private ResourceUpdater resourceUpdater;
    private int updatedItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resourceUpdater = new ResourceUpdater();
        resourceUpdater.setOnUpdateListener(this);
        resourceUpdater.checkResources("ImageResource");
    }

    @Override
    public void checkDone(int updatedCount) {
        updatedItemCount = updatedCount;
        if(updatedItemCount > 0)
            resourceUpdater.download(0);
    }

    @Override
    public void progress(int itemIndex, int downloadProgress) {
        Trace.e("Download progress. item index = " + itemIndex + ", progress = " + downloadProgress);
    }

    @Override
    public void downloadCompleted(int itemIndex) {
        Trace.e("Download completed. item index = " + itemIndex);

        if(itemIndex+1 < updatedItemCount)
            resourceUpdater.download(itemIndex + 1);
    }
}
