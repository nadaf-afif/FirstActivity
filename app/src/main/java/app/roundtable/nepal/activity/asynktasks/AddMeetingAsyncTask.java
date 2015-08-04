package app.roundtable.nepal.activity.asynktasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.AddNewEventActivity;
import app.roundtable.nepal.activity.activity.AddNewMeetingActivity;
import app.roundtable.nepal.activity.database.EventsManager;
import app.roundtable.nepal.activity.database.Tables;

/**
 * Created by afif on 3/7/15.
 */
public class AddMeetingAsyncTask extends AsyncTask<String,String,String> {

    private Context mContext;
    private EventsManager mManager;
    private boolean mSuccess = true;
    private ProgressDialog mProgressDialog;


    public AddMeetingAsyncTask(Context context) {
        this.mContext = context;
        mManager = new EventsManager(mContext, Tables.Events.MEETING_TABLE);
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
            response = mManager.createNewMeeting(params);
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

                ((AddNewMeetingActivity)mContext).showConfirmationDialog();

            }else {

                String message = mManager.getErrorMessage(result);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

            }


        }else {

            Toast.makeText(mContext, mContext.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

        }

    }
}
