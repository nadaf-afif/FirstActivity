package app.roundtable.nepal.activity.asynktasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.database.EventsManager;

/**
 * Created by afif on 11/7/15.
 */
public class UpdateRSVPAsyncTask extends AsyncTask<String, String, String> {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private boolean mSuccess = true;
    private EventsManager mManager;

    public UpdateRSVPAsyncTask(Context context, String tableName) {
        this.mContext = context;
        mManager = new EventsManager(mContext, tableName);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(mContext.getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }


    @Override
    protected String doInBackground(String... params) {

        String eventId = params[0];
        String status = params[1];
        String response = null;

        try {
            response = mManager.updateStatus(eventId, status);

            String success = mManager.getSuccessString(response);

            if(success.equalsIgnoreCase("true")){

                mManager.updateLocalDB(eventId, status);
            }else
                mSuccess = false;

        } catch (IOException e) {
            e.printStackTrace();
            mSuccess = false;
        }


        return response;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();

        if(mSuccess){

            Toast.makeText(mContext, "Your response is updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, mContext.getString(R.string.something_went_wrong) , Toast.LENGTH_SHORT).show();

        }
    }


}
