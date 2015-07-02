package app.roundtable.nepal.activity.asynktasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.roundtable.nepal.activity.database.EventsManager;
import app.roundtable.nepal.activity.fragments.MeetingsFragment;
import app.roundtable.nepal.activity.interfaces.DataLoader;

/**
 * Created by afif on 25/6/15.
 */
public class GetMeetingsAsyncTask extends AsyncTask<String, Cursor,Cursor> {

    private EventsManager mManager;
    private boolean mSuccess = true;
    private Context mContext;
    private DataLoader mDataLoader;

    public GetMeetingsAsyncTask(Context context, DataLoader dataLoader) {
        this.mContext = context;
        this.mDataLoader = dataLoader;
        mManager = new EventsManager(mContext);
    }

    @Override
    protected Cursor doInBackground(String... params) {

        Cursor cursor = null;

        try {
            String response = mManager.getAllMeetings();

            JSONObject jsonObject = new JSONObject(response);

            String success = jsonObject.getString("success");

            if(success.equals("true")) {


                JSONObject dataObject = jsonObject.getJSONObject("data");
                mManager.saveEventsInDatabase(dataObject);
                cursor = mManager.getMeetings();

                return cursor;
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

        if(mSuccess){

            mDataLoader.setFirstPageData(cursor);

        }
    }
}
