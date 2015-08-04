package app.roundtable.nepal.activity.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.asynktasks.UpdateRSVPAsyncTask;
import app.roundtable.nepal.activity.database.EventsManager;
import app.roundtable.nepal.activity.database.Tables;

/**
 * Created by afif on 13/6/15.
 */
public class MeetingDetailsActivity extends AppCompatActivity implements Tables.Events , View.OnClickListener{

    private Toolbar mToolbar;
    private EventsManager mManager;
    private Cursor mCursor;
    private TextView mMeetingNameTextView, mVenueTextView, mDateTextView, mTimeTextView, mInviteesTextView,mYesTextView, mNoTextView, mMaybeTextView;
    private Switch mSpouseSwitch, mChildrenSwitch;
    private UpdateRSVPAsyncTask mUpdateStatusAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_deatils);

        initViews();

        setMeetingsDetails();

    }

    private void setMeetingsDetails() {

        if(mCursor.getCount() > 0){
        mCursor.moveToFirst();

            mMeetingNameTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_NAME)));
            mVenueTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_VENUE)));
            mDateTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_DATE)));
            mTimeTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_TIME)));
            mInviteesTextView.setText(mCursor.getString(mCursor.getColumnIndex(TABLE_COUNT)) + " Tables");

            mSpouseSwitch.setChecked(Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(IS_SPOUSE))));
            mChildrenSwitch.setChecked(Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(IS_CHILDREN))));

            mChildrenSwitch.setClickable(false);
            mSpouseSwitch.setClickable(false);

            String status = mCursor.getString(mCursor.getColumnIndex(RSVP));

            if (status.equalsIgnoreCase("yes")) {
                mYesTextView.setBackgroundColor(getResources().getColor(R.color.name_background));
                mYesTextView.setTextColor(Color.WHITE);
            } else if (status.equalsIgnoreCase("no")) {
                mNoTextView.setBackgroundColor(getResources().getColor(R.color.name_background));
                mNoTextView.setTextColor(Color.WHITE);
            } else if (status.equalsIgnoreCase("maybe")) {
                mMaybeTextView.setBackgroundColor(getResources().getColor(R.color.name_background));
                mMaybeTextView.setTextColor(Color.WHITE);
            }
        }else {
            Toast.makeText(this, getString(R.string.no_members_found), Toast.LENGTH_SHORT).show();
        }

    }

    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.actionToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.meeting_detail_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mManager = new EventsManager(this, MEETING_TABLE);

        String eventId = getIntent().getStringExtra(EVENT_ID);
        mCursor = mManager.getEventDetails(eventId);

        mMeetingNameTextView = (TextView) findViewById(R.id.meetingNameTextView);
        mVenueTextView = (TextView) findViewById(R.id.venueTextView);
        mDateTextView = (TextView) findViewById(R.id.dateTextView);
        mTimeTextView = (TextView) findViewById(R.id.timeTextView);
        mInviteesTextView = (TextView) findViewById(R.id.inviteesTextView);
        mSpouseSwitch = (Switch) findViewById(R.id.spouceSwitch);
        mChildrenSwitch = (Switch)findViewById(R.id.childrenSwitch);



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

        if(mCursor.getCount() > 0 ) {
            String eventId = mCursor.getString(mCursor.getColumnIndex(EVENT_ID));
             mUpdateStatusAsyncTask = new UpdateRSVPAsyncTask(this, MEETING_TABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mUpdateStatusAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, eventId, status);
            else
                mUpdateStatusAsyncTask.execute(eventId, status);
        }

    }
}
