package app.roundtable.nepal.activity.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.asynktasks.GetOTPAsyncTasks;
import app.roundtable.nepal.activity.asynktasks.LoginAsyncTask;
import app.roundtable.nepal.activity.network.NetworkManager;
import app.roundtable.nepal.activity.util.ApplicationPreferences;
import app.roundtable.nepal.activity.util.Constants;

/**
 * Created by afif on 2/6/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener{


    private EditText mEmailEditText, mPasswordEditText;
    private Button mLoginButton;
    private TextView mGetOTPTextView, mRequestNewAccessTextView;
    private LoginAsyncTask mAsyncTask;
    private ApplicationPreferences mSharedPreferences;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        if(!mSharedPreferences.isDeviceRegistered())
            getRegistrationId();


    }



    private void getRegistrationId() {


        new AsyncTask<String, Void, String>(){

            ProgressDialog mProgressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(LoginActivity.this);
                mProgressDialog.setMessage(getString(R.string.please_wait));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(String registrationId) {
                super.onPostExecute(registrationId);

                if(mProgressDialog!=null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Toast.makeText(LoginActivity.this, registrationId,Toast.LENGTH_SHORT).show();

                if(registrationId!=null && !registrationId.equals("")) {
                    mSharedPreferences.setGcmRegistrationId(registrationId);
                    mSharedPreferences.setIsRegistered(true);
                }
            }

            @Override
            protected String doInBackground(String... params) {

                String regId = null;

                InstanceID instanceID = InstanceID.getInstance(LoginActivity.this);
                try {
                    regId = instanceID.getToken(Constants.SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return regId;
            }



        }.execute();



    }

    private void initViews() {

        mEmailEditText = (EditText)findViewById(R.id.emailEditText);
        mPasswordEditText = (EditText)findViewById(R.id.passwordEditText);
        mLoginButton = (Button)findViewById(R.id.loginButton);
        mGetOTPTextView = (TextView)findViewById(R.id.getOTPTextView);
        mRequestNewAccessTextView = (TextView)findViewById(R.id.requestNewAccessTextView);

        mRequestNewAccessTextView.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mGetOTPTextView.setOnClickListener(this);

        mSharedPreferences = new ApplicationPreferences(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()){

            case R.id.getOTPTextView :

               if(NetworkManager.isConnectedToInternet(this))
                   showEmailDialog();
               else
                   Toast.makeText(this, getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();

                break;

            case R.id.loginButton :

                if(NetworkManager.isConnectedToInternet(this))
                    isValidData();
                else
                    Toast.makeText(this, getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();

                break;

            case R.id.requestNewAccessTextView :

                intent = new Intent(LoginActivity.this, RequestAccessactivity.class);
                startActivity(intent);
                break;

        }
    }

    private void isValidData() {

        String email = mEmailEditText.getText().toString();
        String password= mPasswordEditText.getText().toString();

        if(!email.equals("") && emailValid(email) && ! password.equals("")){

            executeLoginAsynctask(email, password);

        }else if(email.equals("")){
            Toast.makeText(this, getString(R.string.enter_email_text),Toast.LENGTH_SHORT).show();
        }else if(password.equals("")){
            Toast.makeText(this, getString(R.string.password_error_message),Toast.LENGTH_SHORT).show();
        }else if (! emailValid(email)){
            Toast.makeText(this, getString(R.string.enter_valid_email),Toast.LENGTH_SHORT).show();
        }

    }

    private void executeLoginAsynctask(String email, String password) {

        mAsyncTask = new LoginAsyncTask(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){

            mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, email, password);

        }else{

            mAsyncTask.execute(email,password);
        }


    }

    private boolean emailValid(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showEmailDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setTitle(getString(R.string.request_otp_text));
        dialog.setContentView(R.layout.dialog_get_otp);
        dialog.getWindow().setLayout(getWindowManager().getDefaultDisplay().getWidth(), (int) (getWindowManager().getDefaultDisplay().getHeight()/2.5));

        Button doneButton = (Button)dialog.findViewById(R.id.doneButton);
        final EditText emailEditText = (EditText)dialog.findViewById(R.id.emailAddressEditText);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString();
                if(emailValid(email)){

                    dialog.dismiss();
                    executeGetOTPAsyncTasks(email);

                }else{

                    Toast.makeText(LoginActivity.this, getString(R.string.enter_valid_email),Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();

    }

    private void executeGetOTPAsyncTasks(String email) {

        GetOTPAsyncTasks getOTPAsyncTasks = new GetOTPAsyncTasks(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            getOTPAsyncTasks.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,email);
        else
            getOTPAsyncTasks.execute(email);
    }

    public void startHomeActivity() {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}
