package app.roundtable.nepal.activity.asynktasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.LoginActivity;
import app.roundtable.nepal.activity.activity.RequestAccessactivity;
import app.roundtable.nepal.activity.managers.LoginManager;

/**
 * Created by afif on 20/6/15.
 */
public class RequestAccessAsyncTask extends AsyncTask<String,String,String> {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private LoginManager mManager;

    public RequestAccessAsyncTask(Context context) {
        this.mContext = context;
        mManager = new LoginManager(mContext);
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

        String name = params[0];
        String email = params[1];
        String tableName = params[2];

        try {
            response = mManager.requestNewAccess(name, email , tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);


        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            String status = jsonObject.getString("success");

            if(status.equals("true")){

                ((RequestAccessactivity)mContext).showConfirmationDialog();

            }else{

                String errorMessage = mManager.getErrorMessage(result);
                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
