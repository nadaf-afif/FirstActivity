package app.roundtable.nepal.activity.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.ResponseListAdapter;
import app.roundtable.nepal.activity.asynktasks.UpdateRSVPAsyncTask;
import app.roundtable.nepal.activity.database.EventsManager;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.databeans.ResponseDataBean;
import app.roundtable.nepal.activity.network.ApiClient;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.util.ApplicationPreferences;

/**
 * Created by afif on 13/6/15.
 */
public class EventDetailsActivity extends AppCompatActivity implements Tables.Events, View.OnClickListener{

    private Toolbar mToolbar;
    private TextView mEventNameTextView, mVenueTextView, mDateTextView, mTimeTextView, mInviteesTextView,
            mYesTextView, mNoTextView, mMaybeTextView, mVenueDetailTextView, mHostNameTextView;
    private TextView mChildrenInvitedTextView, mSpouseInvitedTextView;
    private Cursor mCursor;
    private EventsManager mManager;
    private UpdateRSVPAsyncTask mUpdateStatusAsyncTask;
    private ImageView mEventPhotoImageView;
    private LinearLayout mVenueLinearLayout;
    private String mLatitude, mLongitude;
    private boolean mShowRSVP = false ;
    private ApplicationPreferences mSharedPreferences;
    private ArrayList<ResponseDataBean> mRSVPData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_deatils);

        initViews();

        setEventsdata();

    }

    private void setEventsdata() {

        mCursor.moveToFirst();
        mEventNameTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_NAME)));
        mVenueTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_VENUE)));
        mDateTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_DATE)));
        mTimeTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_TIME)));
        mInviteesTextView.setText(mCursor.getString(mCursor.getColumnIndex(TABLE_COUNT)) + " Tables");
        mVenueDetailTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_ADDRESS_LINE)));

        boolean isSpouseInvited = Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(IS_SPOUSE)));

        if(isSpouseInvited)
            mSpouseInvitedTextView.setText(getString(R.string.invited_text));
        else
            mSpouseInvitedTextView.setText(getString(R.string.not_invited_text));

        boolean isChildrenInvited = Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(IS_CHILDREN)));

        if (isChildrenInvited)
            mChildrenInvitedTextView.setText(getString(R.string.invited_text));
        else
            mChildrenInvitedTextView.setText(getString(R.string.not_invited_text));

        mHostNameTextView.setText("Host : \t"+mCursor.getString(mCursor.getColumnIndex(HOST)));

        String RSVPResponse = mCursor.getString(mCursor.getColumnIndex(SHOW_RSVP));

        if(RSVPResponse!=null && RSVPResponse.equalsIgnoreCase("true"))
            mShowRSVP = true;
        else
            mShowRSVP = false;

        mChildrenInvitedTextView.setClickable(false);
        mSpouseInvitedTextView.setClickable(false);

        String status = mCursor.getString(mCursor.getColumnIndex(RSVP));

        if(status.equalsIgnoreCase("yes")){
            mYesTextView.setBackgroundColor(getResources().getColor(R.color.name_background));
            mYesTextView.setTextColor(Color.WHITE);
        }else if(status.equalsIgnoreCase("no")){
            mNoTextView.setBackgroundColor(getResources().getColor(R.color.name_background));
            mNoTextView.setTextColor(Color.WHITE);
        }else if (status.equalsIgnoreCase("maybe")){
            mMaybeTextView.setBackgroundColor(getResources().getColor(R.color.name_background));
            mMaybeTextView.setTextColor(Color.WHITE);
        }

        String imagepath = ApiUrls.BASE_URL_PATH + mCursor.getString(mCursor.getColumnIndex(EVENT_BIG_IMAGE));
        Picasso.with(this).load(imagepath).into(mEventPhotoImageView);

        String showRSVP = mCursor.getString(mCursor.getColumnIndex(SHOW_RSVP));
        if (showRSVP.equals("true"))
            mShowRSVP = true;


    }

    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolbar);

        mManager = new EventsManager(this, EVENTS_TABLE);

        String eventId = getIntent().getStringExtra(EVENT_ID);

        mCursor = mManager.getEventDetails(eventId);

        getSupportActionBar().setTitle(getString(R.string.event_details_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSharedPreferences = new ApplicationPreferences(this);
        mEventNameTextView = (TextView) findViewById(R.id.eventNameTextView);
        mVenueTextView = (TextView) findViewById(R.id.venueTextView);
        mDateTextView = (TextView) findViewById(R.id.dateTextView);
        mTimeTextView = (TextView) findViewById(R.id.timeTextView);
        mInviteesTextView = (TextView) findViewById(R.id.inviteesTextView);
        mEventPhotoImageView = (ImageView) findViewById(R.id.eventPhotoImageView);
        mVenueDetailTextView = (TextView) findViewById(R.id.venueAddressTextView);
        mVenueLinearLayout = (LinearLayout) findViewById(R.id.venueLL);
        mHostNameTextView = (TextView) findViewById(R.id.hostName);

        mSpouseInvitedTextView = (TextView) findViewById(R.id.spouseInvitedTextView);
        mChildrenInvitedTextView = (TextView) findViewById(R.id.childrenInvitedTextView);

        mYesTextView = (TextView) findViewById(R.id.yesTextView);
        mNoTextView = (TextView) findViewById(R.id.noTextView);
        mMaybeTextView = (TextView) findViewById(R.id.maybeTextView);

        mYesTextView.setOnClickListener(this);
        mNoTextView.setOnClickListener(this);
        mMaybeTextView.setOnClickListener(this);
        mVenueLinearLayout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.yesTextView :

                mYesTextView.setBackgroundColor(getResources().getColor(R.color.name_background));
                mYesTextView.setTextColor(Color.WHITE);

                mNoTextView.setBackgroundColor(Color.WHITE);
                mNoTextView.setTextColor(getResources().getColor(R.color.name_background));

                mMaybeTextView.setBackgroundColor(Color.WHITE);
                mMaybeTextView.setTextColor(getResources().getColor(R.color.name_background));

                executeUpdateAsyncTask(mYesTextView.getText().toString());

                break;

            case R.id.noTextView :

                mNoTextView.setBackgroundColor(getResources().getColor(R.color.name_background));
                mNoTextView.setTextColor(Color.WHITE);

                mYesTextView.setBackgroundColor(Color.WHITE);
                mYesTextView.setTextColor(getResources().getColor(R.color.name_background));

                mMaybeTextView.setBackgroundColor(Color.WHITE);
                mMaybeTextView.setTextColor(getResources().getColor(R.color.name_background));

                executeUpdateAsyncTask(mNoTextView.getText().toString());


                break;

            case R.id.maybeTextView :
                mMaybeTextView.setBackgroundColor(getResources().getColor(R.color.name_background));
                mMaybeTextView.setTextColor(Color.WHITE);

                mNoTextView.setBackgroundColor(Color.WHITE);
                mNoTextView.setTextColor(getResources().getColor(R.color.name_background));

                mYesTextView.setBackgroundColor(Color.WHITE);
                mYesTextView.setTextColor(getResources().getColor(R.color.name_background));

                executeUpdateAsyncTask(mMaybeTextView.getText().toString());


                break;


            case R.id.venueLL :

                String venueLocation = mCursor.getString(mCursor.getColumnIndex(EVENT_ADDRESS_LINE));
                if(venueLocation!=null && venueLocation.length()>0)
                {

                    Intent intent = new Intent(EventDetailsActivity.this, ShowLocationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("latitude", mCursor.getString(mCursor.getColumnIndex(EVENT_LATITUDE)));
                    bundle.putString("longitude",mCursor.getString(mCursor.getColumnIndex(EVENT_LONGITUDE)));
                    bundle.putString("location", mCursor.getString(mCursor.getColumnIndex(EVENT_ADDRESS_LINE)));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;
        }
    }

    private void executeUpdateAsyncTask(String status) {

        String eventId = mCursor.getString(mCursor.getColumnIndex(EVENT_ID));
        mUpdateStatusAsyncTask = new UpdateRSVPAsyncTask(this, EVENTS_TABLE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mUpdateStatusAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,eventId,status);
        else
            mUpdateStatusAsyncTask.execute(eventId, status);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if(mShowRSVP)
          getMenuInflater().inflate(R.menu.menu_update, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case R.id.action_status :

                getAvailabilityStatus();
                break;
        }

        return false;
    }

    private void getAvailabilityStatus() {


        new AsyncTask<String,String,String>(){

            ProgressDialog mProgressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(EventDetailsActivity.this);
                mProgressDialog.setMessage(getString(R.string.please_wait));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

            }

            @Override
            protected String doInBackground(String... params) {

                String response = null;
                String memberId = mCursor.getString(mCursor.getColumnIndex(EVENT_ID));
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("member_id",params[0]));
                pairs.add(new BasicNameValuePair("event_id",memberId));
                ApiClient apiClient = new ApiClient();
                try {
                    response = apiClient.executePostRequestWithMemberIdHeader(pairs, ApiUrls.RSVP_RESPONSE_API_PATH, params[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(mProgressDialog!=null)
                    mProgressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String success = jsonObject.getString("success");

                    if(success.equals("true")){
                        JSONObject dataObject  = jsonObject.getJSONObject("data");
                        showDataInList(dataObject);
                    }else {
                        JSONObject errorObject = jsonObject.getJSONObject("error");
                        String message = errorObject.getString("msg");
                        Toast.makeText(EventDetailsActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }.execute(mSharedPreferences.getUserId());
    }

    private void showDataInList(JSONObject dataObject) throws JSONException {

        JSONArray jsonArray = dataObject.getJSONArray("rsvp_response");
        mRSVPData.clear();
        for (int i = 0; i < jsonArray.length(); i++) {

            ResponseDataBean responseData = new ResponseDataBean();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            responseData.setMemberId(jsonObject.getString("member_id"));
            responseData.setMemberName(jsonObject.getString("member_name"));
            responseData.setResponseDate(jsonObject.getString("response_date"));
            responseData.setStatus(jsonObject.getString("rsvp"));
            responseData.setTableName(jsonObject.getString("table"));
            mRSVPData.add(responseData);
        }

        showListDialog();

    }

    private void showListDialog() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog_response);
        RecyclerView listView = (RecyclerView) dialog.findViewById(R.id.recycleListView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        ResponseListAdapter adapter = new ResponseListAdapter(mRSVPData,this);
        listView.setAdapter(adapter);

        dialog.show();
        dialog.getWindow().setLayout((int)(getWindowManager().getDefaultDisplay().getWidth()*0.9),(int)(getWindowManager().getDefaultDisplay().getHeight()*0.9));
    }

}
