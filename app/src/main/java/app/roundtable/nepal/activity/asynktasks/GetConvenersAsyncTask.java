package app.roundtable.nepal.activity.asynktasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.roundtable.nepal.activity.database.ConvenersManager;
import app.roundtable.nepal.activity.interfaces.DataLoader;

/**
 * Created by afif on 7/7/15.
 */
public class GetConvenersAsyncTask extends AsyncTask<String, String, Cursor> {

    private ConvenersManager mManager;
    private boolean mSuccess = true;
    private Context mContext;
    private DataLoader mDataLoader;


    public GetConvenersAsyncTask(Context context, DataLoader dataLoader) {
        this.mContext = context;
        this.mDataLoader = dataLoader;
        mManager = new ConvenersManager(mContext);
    }

    @Override
    protected Cursor doInBackground(String... params) {
        Cursor cursor = null;
        try {
            String response = mManager.getConveners();

            String success = mManager.getSuccessString(response);

            if(success.equals("true")){

                mManager.clearTable();

                JSONObject jsonObject = new JSONObject(response);
                JSONObject dataObject = jsonObject.getJSONObject("data");
                mManager.saveConvenersInDB(dataObject);
                cursor = mManager.getCursor();

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

        if(mSuccess){

            mDataLoader.setFirstPageData(cursor);
        }else {
            mDataLoader.onNoData();
        }

    }



}
