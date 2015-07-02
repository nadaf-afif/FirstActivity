package app.roundtable.nepal.activity.asynktasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.AddNewEventActivity;
import app.roundtable.nepal.activity.database.EventsManager;

/**
 * Created by afif on 2/7/15.
 */
public class AddEventAsyncTasks extends AsyncTask<String, String, String> {


    private Context mContext;
    private EventsManager mManager;
    private ProgressDialog mProgressDialog;
    private boolean mSuccess = true;

    public AddEventAsyncTasks(Context context) {
        this.mContext = context;
        mManager = new EventsManager(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(mContext.getString(R.string.please_wait));
        mProgressDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {

        String response = null;
        try {
            response = mManager.createNewEvent(params);
        } catch (IOException e) {
            e.printStackTrace();
            mSuccess = false;
        }

        return response;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();



        if(mSuccess){

            String success = mManager.getSuccessString(result);

            if(success.equals("true")){

                ((AddNewEventActivity)mContext).showConfirmationDialog();

            }else {

                String message = mManager.getErrorMessage(result);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

            }


        }else {

            Toast.makeText(mContext, mContext.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

        }

    }


}
