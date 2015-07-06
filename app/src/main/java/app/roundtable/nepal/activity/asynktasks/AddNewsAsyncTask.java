package app.roundtable.nepal.activity.asynktasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.AddNewNewsActivity;
import app.roundtable.nepal.activity.database.NewsManager;

/**
 * Created by afif on 4/7/15.
 */
public class AddNewsAsyncTask extends AsyncTask<String, String, String> {

    private Context mContext;
    private NewsManager mManager;
    private boolean mSuccess = true;
    private ProgressDialog mProgressDialog;


    public AddNewsAsyncTask(Context context) {
        this.mContext = context;
        mManager = new NewsManager(mContext);
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
            response = mManager.createNews(params);

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

                ((AddNewNewsActivity)mContext).showConfirmationDialog();

            }else {

                String errorMessage = mManager.getErrorMessage(result);
                Toast.makeText(mContext,errorMessage,Toast.LENGTH_SHORT).show();
            }

        }else {

            Toast.makeText(mContext,mContext.getString(R.string.something_went_wrong),Toast.LENGTH_SHORT).show();

        }

    }


}
