package app.roundtable.nepal.activity.asynktasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.managers.LoginManager;

/**
 * Created by afif on 11/7/15.
 */
public class EditProfileAsyncTask extends AsyncTask<String, String, String> {

    private Context mContext;
    private LoginManager mManager;
    private ProgressDialog mProgressDialog;
    private boolean mSuccess = true;

    public EditProfileAsyncTask(Context context) {
        mContext = context;
        mManager = new LoginManager(mContext);

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

        String response = null;

        try {
            response = mManager.updateProfile(params);

            String success = mManager.getSuccessString(response);
            if(success.equals("true")){

                mManager.saveupdatedData(response);

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

        return response;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();

        if(mSuccess){
            Toast.makeText(mContext, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, mContext.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

        }

    }
}
