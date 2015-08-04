package app.roundtable.nepal.activity.asynktasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.roundtable.nepal.activity.database.NewsManager;
import app.roundtable.nepal.activity.interfaces.DataLoader;

/**
 * Created by afif on 25/6/15.
 */
public class GetNewsListAsyncTask extends AsyncTask<String, Cursor, Cursor> {

    private Context mContext;
    private DataLoader mDataLoader;
    private boolean mSuccess = true;
    private NewsManager mManager;

    public GetNewsListAsyncTask(Context context, DataLoader dataLoader) {
        this.mContext = context;
        this.mDataLoader = dataLoader;
        mManager = new NewsManager(mContext);
    }

    @Override
    protected Cursor doInBackground(String... params) {

        Cursor cursor = null;


        try {
            String response = mManager.getAllNews();

            JSONObject jsonObject = new JSONObject(response);

            String success = jsonObject.getString("success");

            if(success.equals("true")){

                mManager.ClearTable();
                JSONObject dataObject = jsonObject.getJSONObject("data");
                mManager.saveNewsInDatabase(dataObject);

                cursor = mManager.getNewsCursor();
            }else {
                mSuccess = false;
            }


        } catch (IOException e) {
            e.printStackTrace();
            mSuccess = false;
        } catch (JSONException e) {
            e.printStackTrace();
            mSuccess = false;
        }


        return cursor;
    }


    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);

        if (mSuccess )
        {
            mDataLoader.setFirstPageData(cursor);
        }else {
            mDataLoader.onNoData();
        }
    }
}
