package kr.co.schoolholic.resourceupdater;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;

import kr.co.schoolholic.util.Trace;

/**
 * Created by kevin on 15. 4. 21..
 */
public class UpdateActivity extends Activity implements ResourceUpdater.onUpdateListener {
    private ProgressDialog progressDialog;

    private ResourceUpdater resourceUpdater;
    private int updatedItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Resource Updating");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        resourceUpdater = new ResourceUpdater();
        resourceUpdater.setOnUpdateListener(this);
        resourceUpdater.checkResources("ImageResource");
    }

    @Override
    public void checkDone(int updatedCount) {
        updatedItemCount = updatedCount;
        if(updatedItemCount > 0)
            resourceUpdater.download(0);
        else {
            finishUpdate();
        }
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
        else {
            finishUpdate();
        }
    }

    private void finishUpdate() {
        progressDialog.dismiss();
        finish();
    }
}
