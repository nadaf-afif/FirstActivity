package app.roundtable.nepal.activity.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.asynktasks.UpdateRSVPAsyncTask;
import app.roundtable.nepal.activity.database.EventsManager;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.network.ApiUrls;

/**
 * Created by afif on 13/6/15.
 */
public class EventDetailsActivity extends AppCompatActivity implements Tables.Events, View.OnClickListener{

    private Toolbar mToolbar;
    private TextView mEventNameTextView, mVenueTextView, mDateTextView, mTimeTextView, mInviteesTextView, mYesTextView, mNoTextView, mMaybeTextView;
    private Switch mChildrenSwitch, mSpouseSwitch;
    private Cursor mCursor;
    private EventsManager mManager;
    private UpdateRSVPAsyncTask mUpdateStatusAsyncTask;
    private ImageView mEventPhotoImageView;


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
        mInviteesTextView.setText(mCursor.getString(mCursor.getColumnIndex(TABLE_COUNT))+" Tables");

        mChildrenSwitch.setChecked(Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(IS_CHILDREN))));
        mSpouseSwitch.setChecked(Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(IS_SPOUSE))));

        mChildrenSwitch.setClickable(false);
        mSpouseSwitch.setClickable(false);

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

    }

    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolbar);

        mManager = new EventsManager(this, EVENTS_TABLE);

        String eventId = getIntent().getStringExtra(EVENT_ID);

        mCursor = mManager.getEventDetails(eventId);

        getSupportActionBar().setTitle(getString(R.string.event_details_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEventNameTextView = (TextView) findViewById(R.id.eventNameTextView);
        mVenueTextView = (TextView) findViewById(R.id.venueTextView);
        mDateTextView = (TextView) findViewById(R.id.dateTextView);
        mTimeTextView = (TextView) findViewById(R.id.timeTextView);
        mInviteesTextView = (TextView) findViewById(R.id.inviteesTextView);
        mEventPhotoImageView = (ImageView) findViewById(R.id.eventPhotoImageView);

        mSpouseSwitch = (Switch) findViewById(R.id.spouceSwitch);
        mChildrenSwitch = (Switch) findViewById(R.id.childrenSwitch);

        mYesTextView = (TextView) findViewById(R.id.yesTextView);
        mNoTextView = (TextView) findViewById(R.id.noTextView);
        mMaybeTextView = (TextView) findViewById(R.id.maybeTextView);

        mYesTextView.setOnClickListener(this);
        mNoTextView.setOnClickListener(this);
        mMaybeTextView.setOnClickListener(this);

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

}
