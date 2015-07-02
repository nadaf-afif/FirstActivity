package app.roundtable.nepal.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.roundtable.nepal.activity.network.ApiClient;
import app.roundtable.nepal.activity.network.ApiUrls;

/**
 * Created by afif on 24/6/15.
 */
public class RTNTablesManager extends Manager implements Tables.RTNTables{


    private Context mContext;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mSqlSqLiteDatabase;
    private ApiClient mApiClient;

    public RTNTablesManager(Context context) {
        this.mContext = context;
        mDataBaseHelper = new DataBaseHelper(context);
        mSqlSqLiteDatabase = mDataBaseHelper.getSqLiteDatabase();
        mApiClient = new ApiClient();
    }



    public String getRtnTables() throws IOException {

        String response = mApiClient.executeHttpGetWithHeader(ApiUrls.RTN_TABLES_API_PATH);

        return response;
    }


    public void saveDataInDataBase(JSONObject response){

        try {
            JSONArray jsonArray = response.getJSONArray("table_list");

            for(int i = 0; i<jsonArray.length() ; i++){

                ContentValues contentValues = new ContentValues();

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                contentValues.put(TABLE_ID , jsonObject.getString(TABLE_ID));
                contentValues.put(TABLE_NAME, jsonObject.getString(TABLE_NAME));
                contentValues.put(TABLE_CODE, jsonObject.getString(TABLE_CODE));
                contentValues.put(TABLE_DESCRIPTION, jsonObject.getString(TABLE_DESCRIPTION));
                contentValues.put(TABLE_BIG_URL, jsonObject.getString(TABLE_BIG_URL));
                contentValues.put(TABLE_THUMB_URL, jsonObject.getString(TABLE_THUMB_URL));
                contentValues.put(TABLE_MEMBERS_COUNT, jsonObject.getString(TABLE_MEMBERS_COUNT));


                mSqlSqLiteDatabase.insert(RTN_TABLES_TABLE, null, contentValues);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void ClearTable (){

        mSqlSqLiteDatabase.delete(RTN_TABLES_TABLE,null,null);

    }


    public Cursor getTables(){

        Cursor cursor = mSqlSqLiteDatabase.rawQuery("SELECT rowid _id, * FROM "+RTN_TABLES_TABLE , null);

        return cursor;
    }

}
