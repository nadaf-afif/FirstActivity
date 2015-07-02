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
import app.roundtable.nepal.activity.managers.LoginManager;

/**
 * Created by afif on 20/6/15.
 */
public class LoginAsyncTask extends AsyncTask<String, String, String> {


    private LoginManager mManager;
    private Context mContext;
    private ProgressDialog mProgressDialog;


    public LoginAsyncTask(Context context) {
        this.mContext = context;
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

        String email = params[0];
        String password = params[1];

        try {
            response = mManager.doEmailLogin(email,password);

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

                ((LoginActivity)mContext).startHomeActivity();

            }else{

                String errorMessage = mManager.getErrorMessage(result);
                Toast.makeText(mContext,errorMessage,Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
