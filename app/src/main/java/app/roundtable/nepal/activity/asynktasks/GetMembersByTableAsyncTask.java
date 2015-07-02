package app.roundtable.nepal.activity.asynktasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.roundtable.nepal.activity.database.MembersManager;
import app.roundtable.nepal.activity.interfaces.DataLoader;

/**
 * Created by afif on 27/6/15.
 */
public class GetMembersByTableAsyncTask extends AsyncTask<String , Cursor, Cursor> {


    private Context mContext;
    private MembersManager mManager;
    private DataLoader mDataLoader;
    private boolean mSuccess = true;
    private String mResponse;


    public GetMembersByTableAsyncTask(Context context, DataLoader dataLoader) {
        this.mContext = context;
        this.mDataLoader = dataLoader;
        mManager = new MembersManager(mContext);
    }

    @Override
    protected Cursor doInBackground(String... params) {

        Cursor cursor = null;
        String memberId = params[0];

        try {
            mResponse = mManager.getMembersByTable(memberId);

            JSONObject jsonObject = new JSONObject(mResponse);
            String success = jsonObject.getString("success");

            if(success.equals("true")){

                mManager.ClearTable();
                JSONObject dataObject = jsonObject.getJSONObject("data");
                mManager.saveMembersInDatabase(dataObject);

                cursor = mManager.getMembers();

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

        if (mSuccess){

            mDataLoader.setFirstPageData(cursor);

        }else {

            int responseStatusCode = mManager.getResponseStatusCode(mResponse);
            if(responseStatusCode == 402){
                mDataLoader.onNoData();
            }

        }

    }



}
