package app.roundtable.nepal.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import app.roundtable.nepal.activity.network.ApiClient;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.network.MultipartUtility;
import app.roundtable.nepal.activity.util.ApplicationPreferences;

/**
 * Created by afif on 25/6/15.
 */
public class NewsManager extends Manager implements Tables.News{

    private DataBaseHelper mDataHelper;
    private SQLiteDatabase mSqlSqLiteDatabase;
    private Context mContext;
    public ApiClient mApiClient;
    private ApplicationPreferences mSharedPreferences;

    public NewsManager(Context context) {
        this.mContext = context;
        mDataHelper = new DataBaseHelper(mContext);
        mSqlSqLiteDatabase = mDataHelper.getSqLiteDatabase();
        mApiClient = new ApiClient();
        mSharedPreferences = new ApplicationPreferences(mContext);
    }


    public String getAllNews() throws IOException {

        String response = mApiClient.executeHttpGetWithHeaderMemberId(ApiUrls.ALL_NEWS_API_PATH, mSharedPreferences.getUserId());

        return response;
    }


    public void saveNewsInDatabase(JSONObject jsonObject) throws JSONException {

        JSONArray jsonArray = jsonObject.getJSONArray("news_list");

        for (int i = 0; i<jsonArray.length();i++){

            ContentValues contentValues = new ContentValues();
            JSONObject newsObject = jsonArray.getJSONObject(i);

            contentValues.put(NEWS_ID, newsObject.getString(NEWS_ID));
            contentValues.put(NEWS_HEADLINE, newsObject.getString(NEWS_HEADLINE));
            contentValues.put(NEWS_DESCRIPTION, newsObject.getString(NEWS_DESCRIPTION));
            contentValues.put(NEWS_BIG_URL, newsObject.getString(NEWS_BIG_URL));
            contentValues.put(NEWS_THUMB_URL, newsObject.getString(NEWS_THUMB_URL));
            contentValues.put(NEWS_DATE, newsObject.getString(NEWS_DATE));
            contentValues.put(NEWS_MEMBER_ID, newsObject.getString(NEWS_MEMBER_ID));

            mSqlSqLiteDatabase.insert(NEWS_TABLE, null, contentValues);
        }

    }

    public void ClearTable(){

        mSqlSqLiteDatabase.delete(NEWS_TABLE,null,null);
    }

    public Cursor getNewsCursor() {

        return mSqlSqLiteDatabase.rawQuery("SELECT rowId _id, * FROM "+NEWS_TABLE, null);
    }

    public String createNews(String[] params) throws IOException {

        MultipartUtility builder = new MultipartUtility(ApiUrls.ADD_NEWS_API_PATH,"UTF-8");

        String imagePath = params[3];

        if(imagePath != null && !imagePath.equals("")){
            File file = new File(imagePath);
            builder.addFilePart("news_image", file);
        }

        builder.addFormField(NEWS_HEADLINE, params[0]);
        builder.addFormField(NEWS_DESCRIPTION, params[1]);
        builder.addFormField("concern_tables", params[2]);
        builder.addFormField("member_id",mSharedPreferences.getUserId());

        String response = builder.finish();

        return response;
    }
}
