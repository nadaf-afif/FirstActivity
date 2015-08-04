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
 * Created by afif on 7/7/15.
 */
public class ConvenersManager extends Manager implements Tables.Conveners{

    private Context mContext;
    private DataBaseHelper mDatabDataBaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    private ApiClient mApiClient;


    public ConvenersManager(Context context) {
        this.mContext = context;
        mDatabDataBaseHelper = new DataBaseHelper(mContext);
        mSqLiteDatabase = mDatabDataBaseHelper.getSqLiteDatabase();
        mApiClient = new ApiClient();
    }


    public String getConveners() throws IOException {

        String response = mApiClient.executeHttpGetWithHeader(ApiUrls.CONVENERS_LIST_API_PATH);

        return response;
    }


    public void saveConvenersInDB (JSONObject dataObject) throws JSONException {

        JSONArray jsonArray = dataObject.getJSONArray("conveners_list");

        for (int i = 0; i<jsonArray.length(); i++){

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(CONVENERS_ID, jsonObject.getString(CONVENERS_ID));
            contentValues.put(CONVENER_NAME, jsonObject.getString(CONVENER_NAME));
            contentValues.put(DESIGNATION, jsonObject.getString(DESIGNATION));
            contentValues.put(CONVENER_TABLE, jsonObject.getString(CONVENER_TABLE));
            contentValues.put(CONVENER_EMAIL, jsonObject.getString(CONVENER_EMAIL));
            contentValues.put(CONVENER_MOBILE, jsonObject.getString(CONVENER_MOBILE));

            mSqLiteDatabase.insert(CONVENERS_TABLE, null,contentValues);
        }

    }


    public void clearTable (){

        mSqLiteDatabase.delete(CONVENERS_TABLE, null, null);
    }


    public Cursor getCursor(){

        return mSqLiteDatabase.rawQuery("SELECT rowId _id, * FROM "+CONVENERS_TABLE, null);
    }

}
