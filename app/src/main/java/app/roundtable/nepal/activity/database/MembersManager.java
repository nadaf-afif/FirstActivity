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
 * Created by afif on 27/6/15.
 */
public class MembersManager extends Manager implements Tables.Members{

    private Context mContext;
    private DataBaseHelper mDatabaseBaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    private ApiClient mApiClient;

    public MembersManager(Context context) {
        this.mContext = context;
        mDatabaseBaseHelper = new DataBaseHelper(mContext);
        mSqLiteDatabase = mDatabaseBaseHelper.getSqLiteDatabase();
        mApiClient = new ApiClient();
    }



    public String getMembersByTable(String tableId) throws IOException {

        String response = mApiClient.executeHttpGetWithHeader(ApiUrls.MEMBERS_BY_TABLE_API_PATH+tableId+"/get_members");

        return response;
    }


    public void saveMembersInDatabase (JSONObject jsonObject) throws JSONException {

        JSONArray jsonArray = jsonObject.getJSONArray("members_list");

        for (int i = 0; i< jsonArray.length(); i++){

            ContentValues contentValues = new ContentValues();
            JSONObject dataObject = jsonArray.getJSONObject(i);
            contentValues.put(MEMBER_ID, dataObject.getString(MEMBER_ID));
            contentValues.put(MEMBER_FIRST_NAME, dataObject.getString(MEMBER_FIRST_NAME));
            contentValues.put(MEMBER_LAST_NAME, dataObject.getString(MEMBER_LAST_NAME));
            contentValues.put(GENDER, dataObject.getString(GENDER));
            contentValues.put(MOBILE, dataObject.getString(MOBILE));
            contentValues.put(EMAIL, dataObject.getString(EMAIL));
            contentValues.put(BLOOD_GROUP, dataObject.getString(BLOOD_GROUP));
            contentValues.put(SPOUSE_NAME, dataObject.getString(SPOUSE_NAME));
            contentValues.put(DATE_OF_BIRTH, dataObject.getString(DATE_OF_BIRTH));
            contentValues.put(SPOUSE_DATE_OF_BIRTH, dataObject.getString(SPOUSE_DATE_OF_BIRTH));
            contentValues.put(ANNIVERSARY_DATE, dataObject.getString(ANNIVERSARY_DATE));
            contentValues.put(IMAGE_THUMB_URL, dataObject.getString(IMAGE_THUMB_URL));
            contentValues.put(IMAGE_BIG_URL, dataObject.getString(IMAGE_BIG_URL));
            contentValues.put(RESIDENCE_PHONE, dataObject.getString(RESIDENCE_PHONE));
            contentValues.put(OFFICE_PHONE, dataObject.getString(OFFICE_PHONE));
            contentValues.put(OFFICE_CITY, dataObject.getString(OFFICE_CITY));
            contentValues.put(STATE, dataObject.getString(STATE));
            contentValues.put(MEMBER_TABLE_ID, dataObject.getString(MEMBER_TABLE_ID));

            mSqLiteDatabase.insert(MEMBERS_TABLE, null, contentValues);

        }

    }


    public void ClearTable ()
    {

        mSqLiteDatabase.delete(MEMBERS_TABLE,null,null);
    }


    public Cursor getMembers(){

        Cursor cursor = mSqLiteDatabase.rawQuery("SELECT rowId _id, * FROM "+MEMBERS_TABLE, null);
        return  cursor;
    }

    public Cursor getMemberDetail(String memberId){

        Cursor cursor = mSqLiteDatabase.rawQuery("SELECT rowId _id, * FROM "+MEMBERS_TABLE + " WHERE "+MEMBER_ID+"=?", new String[]{memberId});

        return cursor;
    }

}
