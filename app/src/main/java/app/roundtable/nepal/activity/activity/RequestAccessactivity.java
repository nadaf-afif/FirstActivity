package app.roundtable.nepal.activity.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.asynktasks.GetOTPAsyncTasks;
import app.roundtable.nepal.activity.asynktasks.RequestAccessAsyncTask;
import app.roundtable.nepal.activity.network.NetworkManager;
import app.roundtable.nepal.activity.util.Utils;

/**
 * Created by afif on 13/6/15.
 */
public class RequestAccessactivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmailEditText, mUserNameEditText, mTableNameEditText;
    private Button mSendRequestButton;
    private Toolbar mToolBar;
    private RequestAccessAsyncTask mAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_access);

        initViews();
    }

    private void initViews() {

        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mUserNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        mTableNameEditText = (EditText)findViewById(R.id.tableNameEditText);
        mSendRequestButton = (Button) findViewById(R.id.sendRequestAccessButton);
        mToolBar = (Toolbar)findViewById(R.id.actionToolbar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.request_access_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSendRequestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.sendRequestAccessButton :

                 validData();

                break;

        }
    }

    private void validData() {

        String email = mEmailEditText.getText().toString();
        String userName = mUserNameEditText.getText().toString();
        String tableName = mTableNameEditText.getText().toString();

        if(!email.equals("") && !userName.equals("")  && !tableName.equals("") && Utils.emailValid(email))
             executeAsyncTask(email, userName, tableName);

        else if(email.equals("") )
            Toast.makeText(this, getString(R.string.enter_email_text), Toast.LENGTH_SHORT).show();
        else if(userName.equals(""))
            Toast.makeText(this, getString(R.string.enter_name_msg),Toast.LENGTH_SHORT).show();
        else if (tableName.equals(""))
            Toast.makeText(this, getString(R.string.enter_table_name_msg),Toast.LENGTH_SHORT).show();
        else if(!Utils.emailValid(email))
            Toast.makeText(this, getString(R.string.enter_valid_email),Toast.LENGTH_SHORT).show();

    }

    private void executeAsyncTask(String email, String userName, String tableName) {

        if(NetworkManager.isConnectedToInternet(this))
        {

            mAsyncTask = new RequestAccessAsyncTask(this);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)

                mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userName,email,tableName);
            else

                mAsyncTask.execute(userName,email,tableName);

        }else{

            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

        }

    }

    public void showConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Request Accepted !");
        builder.setMessage(getString(R.string.request_access_confirmation));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();
            }

        });

        builder.show();

    }

}
