package app.roundtable.nepal.activity.asynktasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.roundtable.nepal.activity.interfaces.DataLoader;
import app.roundtable.nepal.activity.database.RTNTablesManager;

/**
 * Created by afif on 24/6/15.
 */
public class GetTablesAsyncTasks extends AsyncTask<String, Cursor, Cursor> {


    private RTNTablesManager mManager;
    private boolean mSuccess = true;
    private DataLoader mDataLoader;

    private Context mContext;

    public GetTablesAsyncTasks(Context context , DataLoader dataLoader) {
        this.mDataLoader = dataLoader;
        mContext = context;
        mManager = new RTNTablesManager(context);
    }

    @Override
    protected Cursor doInBackground(String... params) {

       Cursor cursor = null;

        try {
            String response = mManager.getRtnTables();

            mManager.ClearTable();

            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");

            if(success.equals("true")){

                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                mManager.saveDataInDataBase(jsonObject1);

                cursor = mManager.getTables();

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


        }
    }
}
