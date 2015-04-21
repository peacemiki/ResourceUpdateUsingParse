package kr.co.schoolholic.resourceupdater;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.File;

/**
 * Created by kevin on 15. 4. 16..
 */
public class ResourceFinder {
    ResourceDBHelper helper = new ResourceDBHelper();

    public File find(String nickname) {
        File f;

        ContentValues values = search(nickname);
        if(values != null) {
            String filepath = ResourceContract.ResourceFolderPath + values.getAsString(ResourceContract.ImageResource.COLUMN_NAME_FILENAME);
            f = new File(filepath);

            if(f.exists())
                return f;
        }


        return null;
    }

    private ContentValues search(String nickname) {
        Cursor c = helper.get(nickname);

        if(c != null) {
            c.moveToFirst();
            ContentValues values = ResourceContract.ImageResource.builder(c);
            return  values;
        }

        return null;
    }
}
