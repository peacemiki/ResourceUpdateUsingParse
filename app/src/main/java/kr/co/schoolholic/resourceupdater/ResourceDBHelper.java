package kr.co.schoolholic.resourceupdater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kr.co.schoolholic.core.Global;
import kr.co.schoolholic.resourceupdater.ResourceContract.*;

/**
 * Created by kevin on 15. 4. 16..
 */
public class ResourceDBHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String LONG_TYPE = " LONG";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ImageResource.TABLE_NAME + " (" +
                    ImageResource._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    ImageResource.COLUMN_NAME_OBJECTID + TEXT_TYPE + COMMA_SEP +
                    ImageResource.COLUMN_NAME_CREATEDAT + LONG_TYPE + COMMA_SEP +
                    ImageResource.COLUMN_NAME_UPDATEDAT + LONG_TYPE + COMMA_SEP +
                    ImageResource.COLUMN_NAME_ACL + TEXT_TYPE + COMMA_SEP +
                    ImageResource.COLUMN_NAME_NICKNAME + TEXT_TYPE + COMMA_SEP +
                    ImageResource.COLUMN_NAME_REVISION + INTEGER_TYPE + COMMA_SEP +
                    ImageResource.COLUMN_NAME_FILENAME + TEXT_TYPE + COMMA_SEP +
                    ImageResource.COLUMN_NAME_COMMENT + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ImageResource.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "resource.db";

    public ResourceDBHelper() {
        super(Global.instance.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();

        long newRowId = db.insert(ImageResource.TABLE_NAME, null, values);

        return newRowId;
    }

    public Cursor getAll() {
        SQLiteDatabase db = getReadableDatabase();

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ImageResource.COLUMN_NAME_REVISION + " DESC";

        Cursor c = db.query(
                ImageResource.TABLE_NAME,  // The table to query
                ImageResource.projection,       // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return c;
    }

    public Cursor get(String nickname) {
        SQLiteDatabase db = getReadableDatabase();

        // How you want the results sorted in the resulting Cursor
        String sortOrder = ImageResource.COLUMN_NAME_REVISION + " DESC";

        // Which row to update, based on the nickname
        String selection = ImageResource.COLUMN_NAME_NICKNAME + " = '" + nickname + "'";

        Cursor c = db.query(
                ImageResource.TABLE_NAME,  // The table to query
                ImageResource.projection,       // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return c;
    }

    public int update(ContentValues values) {
        SQLiteDatabase db = getReadableDatabase();

        // Which row to update, based on the nickname
        String selection = ImageResource.COLUMN_NAME_NICKNAME + " = '" + values.getAsString(ImageResource.COLUMN_NAME_NICKNAME) + "'";

        int count = db.update(
                ImageResource.TABLE_NAME,
                values,
                selection,
                null);

        return count;
    }
}
