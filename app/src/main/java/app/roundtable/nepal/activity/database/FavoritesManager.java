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
 * Created by afif on 6/7/15.
 */
public class FavoritesManager extends Manager implements Tables.Favorites{

    private Context mContext;
    private DataBaseHelper mDatabasBaseHelper;
    private SQLiteDatabase mSqlSqLiteDatabase;
    private ApiClient mApiClient;

    public FavoritesManager(Context context) {
        this.mContext = context;
        mDatabasBaseHelper = new DataBaseHelper(mContext);
        mSqlSqLiteDatabase = mDatabasBaseHelper.getSqLiteDatabase();
        mApiClient = new ApiClient();
    }


    public String getFavorites() throws IOException {

        String response = mApiClient.executeHttpGetWithHeader(ApiUrls.FAVORITES_LIST_API_PATH);

        return response;
    }

    public void saveFavoritesInDataBase(JSONObject jsonObject) throws JSONException {

        JSONArray jsonArray = jsonObject.getJSONArray("favorites_list");

        for (int i = 0; i< jsonArray.length();i++){

            JSONObject dataObject = jsonArray.getJSONObject(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(BRAND_ID, dataObject.getString(BRAND_ID));
            contentValues.put(BRAND_NAME, dataObject.getString(BRAND_NAME));
            contentValues.put(BRAND_LOGO_URL, dataObject.getString(BRAND_LOGO_URL));
            contentValues.put(BRAND_WEBSITE_URL, dataObject.getString(BRAND_WEBSITE_URL));

            mSqlSqLiteDatabase.insert(FAVORITE_TABLE, null, contentValues);
        }

    }


    public void clearTable(){

        mSqlSqLiteDatabase.delete(FAVORITE_TABLE,null,null);
    }


    public Cursor getCursor(){

        Cursor cursor = mSqlSqLiteDatabase.rawQuery("SELECT rowId _id, * FROM "+FAVORITE_TABLE, null);
        return cursor;
    }
}
