package app.roundtable.nepal.activity.asynktasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.database.FavoritesManager;
import app.roundtable.nepal.activity.interfaces.DataLoader;

/**
 * Created by afif on 6/7/15.
 */
public class GetFavoritesAsyncTask extends AsyncTask<String, String,Cursor> {

    private Context mContext;
    private boolean mSuccess = true;
    private FavoritesManager mManager;
    private DataLoader mDataLoader;


    public GetFavoritesAsyncTask(DataLoader dataLoader, Context context) {
        this.mDataLoader = dataLoader;
        this.mContext = context;
        mManager = new FavoritesManager(mContext);
    }


    @Override
    protected Cursor doInBackground(String... params) {

        Cursor cursor = null;
        try {
            String response = mManager.getFavorites();

            String success = mManager.getSuccessString(response);

            if(success.equals("true")){

                mManager.clearTable();

                JSONObject jsonObject = new JSONObject(response);
                JSONObject dataObject = jsonObject.getJSONObject("data");
                mManager.saveFavoritesInDataBase(dataObject);
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
