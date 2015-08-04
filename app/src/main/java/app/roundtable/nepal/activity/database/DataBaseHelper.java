package app.roundtable.nepal.activity.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by afif on 23/6/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "round_table.db";
    private static final int DATABASE_VERSION = 2;
    private SQLiteDatabase mSqliteSqLiteDatabase;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public SQLiteDatabase getSqLiteDatabase(){

        if(mSqliteSqLiteDatabase == null){
            mSqliteSqLiteDatabase = getWritableDatabase();
        }

        return mSqliteSqLiteDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Tables.RTNTables.RTN_TABLE_SCHEMA);
        db.execSQL(Tables.Events.EVENTS_SCHEMA);
        db.execSQL(Tables.News.NEWS_TABLE_SCHEMA);
        db.execSQL(Tables.Members.MEMBERS_TABLE_SCHEMA);
        db.execSQL(Tables.Favorites.FAVORITE_TABLE_SCHEME);
        db.execSQL(Tables.Conveners.CONVENERS_TABLE_SCHEMA);
        db.execSQL(Tables.Members.SEARCH_MEMBERS_TABLE_SCHEMA);
        db.execSQL(Tables.Events.MEETING_SCHEMA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        upgradeToNewVersion(db);
        onCreate(db);

    }

    private void upgradeToNewVersion(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS "+Tables.Events.EVENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.Events.MEETING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.RTNTables.RTN_TABLES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.Members.MEMBERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.Members.SEARCH_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.Conveners.CONVENERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.Favorites.FAVORITE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.News.NEWS_TABLE);

    }
}
