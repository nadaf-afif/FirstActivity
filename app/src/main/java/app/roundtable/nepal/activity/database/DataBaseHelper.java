package app.roundtable.nepal.activity.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by afif on 23/6/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "round_table.db";
    private static final int DATABASE_VERSION = 1;
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
