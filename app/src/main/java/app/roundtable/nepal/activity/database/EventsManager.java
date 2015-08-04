package app.roundtable.nepal.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.activity.network.ApiClient;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.util.ApplicationPreferences;

/**
 * Created by afif on 24/6/15.
 */
public class EventsManager extends Manager implements Tables.Events{

    private Context mContext;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mSqlSqLiteDatabase;
    private ApiClient mApiClient;
    private ApplicationPreferences mSharedPreferences;
    private String mTableName ;


    public EventsManager(Context context, String tableName) {
        this.mContext = context;
        mApiClient = new ApiClient();
        mDataBaseHelper = new DataBaseHelper(mContext);
        mSqlSqLiteDatabase = mDataBaseHelper.getSqLiteDatabase();
        mSharedPreferences = new ApplicationPreferences(mContext);
        mTableName = tableName;
    }



    public String getAllEvents() throws IOException {

        String response = mApiClient.executeHttpGetWithHeaderMemberId(ApiUrls.ALL_EVENTS_API_PATH, mSharedPreferences.getUserId());

        return response;

    }

    public String getAllMeetings() throws IOException {

        String response = mApiClient.executeHttpGetWithHeaderMemberId(ApiUrls.ALL_MEETINGS_API_PATH, mSharedPreferences.getUserId());

        return response;
    }


    public String createNewEvent(String[] params) throws IOException {

        String response = mApiClient.executeMultipartUtilityWithHeader(params, mContext, ApiUrls.ADD_NEW_EVENT_API_PATH);

        return response;
    }


    public void clearTable (){

        mSqlSqLiteDatabase.delete(mTableName, null, null);
    }


    public void saveEventsInDatabase(JSONObject dataObject) throws JSONException {

        JSONArray dataArray = dataObject.getJSONArray("event_list");

        for (int i = 0 ; i<dataArray.length(); i++) {

            ContentValues contentValues = new ContentValues();

            JSONObject jsonObject = dataArray.getJSONObject(i);
            contentValues.put(EVENT_ID, jsonObject.getString(EVENT_ID));
            contentValues.put(EVENT_NAME, jsonObject.getString(EVENT_NAME));
            contentValues.put(EVENT_DATE, jsonObject.getString(EVENT_DATE));
            contentValues.put(EVENT_TIME, jsonObject.getString(EVENT_TIME));
            contentValues.put(EVENT_TYPE, jsonObject.getString(EVENT_TYPE));
            contentValues.put(EVENT_VENUE, jsonObject.getString(EVENT_VENUE));
            contentValues.put(EVENT_BIG_IMAGE, jsonObject.getString(EVENT_BIG_IMAGE));
            contentValues.put(EVENT_THUMB_IMAGE, jsonObject.getString(EVENT_THUMB_IMAGE));
            contentValues.put(CREATED_AT, jsonObject.getString(CREATED_AT));
            contentValues.put(MEMBER_CREATED, jsonObject.getString(MEMBER_CREATED));
            contentValues.put(IS_CHILDREN , jsonObject.getString(IS_CHILDREN));
            contentValues.put(IS_SPOUSE, jsonObject.getString(IS_SPOUSE));
            contentValues.put(TABLE_COUNT, jsonObject.getString(TABLE_COUNT));
            contentValues.put(RSVP, jsonObject.getString(RSVP));


            mSqlSqLiteDatabase.insert(mTableName, null, contentValues);
        }
    }

//    public Cursor getEvents() {
//
//        return mSqlSqLiteDatabase.rawQuery("SELECT rowId _id, * FROM "+mTableName+" where "+EVENT_TYPE + "=?", new String[]{"event"});
//    }
//
//    public Cursor getMeetings () {
//
//        return mSqlSqLiteDatabase.rawQuery("SELECT rowId _id, * FROM "+mTableName+" where "+EVENT_TYPE + "=?", new String[]{"meeting"});
//
//    }
//
    public Cursor getEventDetails (String eventId){

        Cursor cursor = mSqlSqLiteDatabase.rawQuery("SELECT rowId _id, * FROM "+mTableName+ " where "+EVENT_ID+ "=?", new String[]{eventId});

        return cursor;
    }


    public Cursor getCursor() {

        return mSqlSqLiteDatabase.rawQuery("SELECT rowId _id, * FROM "+mTableName,null);
    }

    public String createNewMeeting(String[] params) throws IOException {

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair(EVENT_TYPE, "meeting"));
        pairs.add(new BasicNameValuePair("invites", params[0]));
        pairs.add(new BasicNameValuePair(IS_SPOUSE,params[1]));
        pairs.add(new BasicNameValuePair(IS_CHILDREN, params[2]));
        pairs.add(new BasicNameValuePair(EVENT_NAME,params[3]));
        pairs.add(new BasicNameValuePair(EVENT_VENUE,params[4]));
        pairs.add(new BasicNameValuePair(EVENT_DATE,params[5]));
        pairs.add(new BasicNameValuePair(EVENT_TIME,params[6]));
        pairs.add(new BasicNameValuePair("member_id",mSharedPreferences.getUserId()));

        String response = mApiClient.executePostRequestWithHeader(pairs, ApiUrls.ADD_NEW_MEETING_API_PATH);

        return response;
    }

    public String updateStatus(String eventId, String status) throws IOException {

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("event_id", eventId));
        pairs.add(new BasicNameValuePair("response", status));
        pairs.add(new BasicNameValuePair("member_id", mSharedPreferences.getUserId()));

        String response = mApiClient.executePostRequestWithHeader(pairs,ApiUrls.RSVP_UPDATE_API_PATH);

        return response;
    }

    public void updateLocalDB(String eventId, String status) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(RSVP,status);
        mSqlSqLiteDatabase.update(mTableName,contentValues,EVENT_ID+"=?",new String[]{eventId});

    }
}
