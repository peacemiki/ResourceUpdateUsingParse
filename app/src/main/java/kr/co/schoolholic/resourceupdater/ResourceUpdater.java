package kr.co.schoolholic.resourceupdater;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.co.schoolholic.core.Global;
import kr.co.schoolholic.resourceupdater.ResourceContract.*;
import kr.co.schoolholic.util.FileUtil;

/**
 * Created by kevin on 15. 4. 16..
 */
public class ResourceUpdater {
    public interface onUpdateListener {
        public void checkDone(int updatedCount);
        public void progress(int itemIndex, int downloadProgress);
        public void downloadCompleted(int itemIndex);
    }

    private enum ResourceStatus {
        NewResource,
        UpdatedResource,
        NothingChanged
    }

    private onUpdateListener listener;
    private ResourceDBHelper dbHelper = new ResourceDBHelper();
    private ArrayList<ParseObject> updatedResources = new ArrayList<>();

    public void setOnUpdateListener(onUpdateListener listener) {
        this.listener = listener;
    }

    public void downloadAll() {

    }

    public void download(final int index) {
        if(updatedResources.size() < index)
            return;

        final ParseObject object = updatedResources.get(index);
        final ParseFile parseFile = object.getParseFile(ImageResource.COLUMN_NAME_FILENAME);
        parseFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if(e == null) {
                    String filepath = ResourceContract.ResourceFolderPath + parseFile.getName();
                    File file = new File(filepath);
                    FileUtil.write(file, bytes);

                    dbHelper.update(ImageResource.builder(object));

                    if (listener != null) {
                        listener.downloadCompleted(index);
                    }
                }
                else {
                    //TODO::
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer progress) {
                if (listener != null) {
                    listener.progress(index, progress);
                }
            }
        });
    }

    public void checkResources(String className) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        query.findInBackground(findCallback);
    }

    public void checkResources(String className, String nickname) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        query.whereEqualTo(ImageResource.COLUMN_NAME_NICKNAME, nickname);
        query.findInBackground(findCallback);
    }

    private FindCallback<ParseObject> findCallback = new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> resources, ParseException e) {
            if (e == null) {
                ContentValues old[] = getOldResources();

                for(ParseObject obj : resources) {
                    ContentValues values = ImageResource.builder(obj);
                    ResourceStatus status = checkResourceStatus(values, old);

                    if(status == ResourceStatus.NewResource) {
                        dbHelper.insert(values);
                    }
                    else if(status == ResourceStatus.UpdatedResource) {
                        updatedResources.add(obj);
                    }
                }

                if(listener != null) {
                    listener.checkDone(updatedResources.size());
                }

            } else {
                Log.d("Kevin", "Error: " + e.getMessage());
            }
        }
    };

    private ContentValues[] getOldResources() {
        Cursor c = dbHelper.getAll();
        ContentValues old[] = new ContentValues[0];
        if(c.getCount() > 0) {
            old = new ContentValues[c.getCount()];
            c.moveToFirst();
            for(int i=0; i<c.getCount(); i++) {
                old[i] = ImageResource.builder(c);
                Log.e("Kevin", "old value[" + i + "] = " + old[i].toString());
                c.moveToNext();
            }
        }

        return old;
    }

    private ResourceStatus checkResourceStatus(ContentValues values, ContentValues[] old) {
        ResourceStatus status = ResourceStatus.NewResource;

        for(int i=0; i<old.length; i++) {
            String oldNick = old[i].getAsString(ImageResource.COLUMN_NAME_NICKNAME);
            String newNick = values.getAsString(ImageResource.COLUMN_NAME_NICKNAME);
            int oldRevision = old[i].getAsInteger(ImageResource.COLUMN_NAME_REVISION);
            int newRevision = values.getAsInteger(ImageResource.COLUMN_NAME_REVISION);

            if(oldNick.equals(newNick)) {
                status = ResourceStatus.NothingChanged;

                if(oldRevision < newRevision) {
                    status = ResourceStatus.UpdatedResource;
                }

                break;
            }
        }

        return status;
    }
}
