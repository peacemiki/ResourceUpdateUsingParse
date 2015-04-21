package kr.co.schoolholic.resourceupdater;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.parse.ParseObject;

import kr.co.schoolholic.core.Global;

/**
 * Created by kevin on 15. 4. 16..
 */
public class ResourceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ResourceContract() {}

    public static final String ResourceFolderPath = Global.instance.getApplicationContext().getFilesDir().getPath() + "/";

    /* Inner class that defines the table contents */
    public static class ImageResource implements BaseColumns {
        public static final String TABLE_NAME = "imageResource";
        public static final String COLUMN_NAME_OBJECTID = "objectId";
        public static final String COLUMN_NAME_CREATEDAT = "createdAt";
        public static final String COLUMN_NAME_UPDATEDAT = "updatedAt";
        public static final String COLUMN_NAME_ACL = "ACL";
        public static final String COLUMN_NAME_NICKNAME = "nickname";
        public static final String COLUMN_NAME_REVISION = "revision";
        public static final String COLUMN_NAME_FILENAME = "file";
        public static final String COLUMN_NAME_COMMENT = "comment";

        // Define a projection that specifies which columns from the database you will actually use after this query.
        public static String[] projection = {
                _ID,
                COLUMN_NAME_OBJECTID,
                COLUMN_NAME_CREATEDAT,
                COLUMN_NAME_UPDATEDAT,
                COLUMN_NAME_ACL,
                COLUMN_NAME_NICKNAME,
                COLUMN_NAME_REVISION,
                COLUMN_NAME_FILENAME,
                COLUMN_NAME_COMMENT
        };

        public static ContentValues builder(ParseObject object) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_OBJECTID, object.getObjectId());
            values.put(COLUMN_NAME_CREATEDAT, object.getCreatedAt().getTime());
            values.put(COLUMN_NAME_UPDATEDAT, object.getUpdatedAt().getTime());
//            values.put(ImageResource.COLUMN_NAME_ACL, object.getACL().toString());
            values.put(COLUMN_NAME_NICKNAME, object.getString(COLUMN_NAME_NICKNAME));
            values.put(COLUMN_NAME_REVISION, object.getInt(COLUMN_NAME_REVISION));
            values.put(COLUMN_NAME_FILENAME, object.getParseFile(COLUMN_NAME_FILENAME).getName());
            values.put(COLUMN_NAME_COMMENT, object.getString(COLUMN_NAME_COMMENT));

            return values;
        }

        public static ContentValues builder(Cursor c) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_OBJECTID, c.getString(c.getColumnIndex(COLUMN_NAME_OBJECTID)));
            values.put(COLUMN_NAME_CREATEDAT, c.getLong(c.getColumnIndex(COLUMN_NAME_CREATEDAT)));
            values.put(COLUMN_NAME_UPDATEDAT, c.getLong(c.getColumnIndex(COLUMN_NAME_UPDATEDAT)));
//            values.put(ImageResource.COLUMN_NAME_ACL, c.getString(c.getColumnIndex(COLUMN_NAME_OBJECTID)));
            values.put(COLUMN_NAME_NICKNAME, c.getString(c.getColumnIndex(COLUMN_NAME_NICKNAME)));
            values.put(COLUMN_NAME_REVISION, c.getInt(c.getColumnIndex(COLUMN_NAME_REVISION)));
            values.put(COLUMN_NAME_FILENAME, c.getString(c.getColumnIndex(COLUMN_NAME_FILENAME)));
            values.put(COLUMN_NAME_COMMENT, c.getString(c.getColumnIndex(COLUMN_NAME_COMMENT)));

            return values;
        }
    }

}
