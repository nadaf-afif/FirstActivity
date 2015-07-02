package app.roundtable.nepal.activity.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;
import android.widget.TextView;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.database.EventsManager;
import app.roundtable.nepal.activity.database.Tables;

/**
 * Created by afif on 13/6/15.
 */
public class MeetingDetailsActivity extends AppCompatActivity implements Tables.Events{

    private Toolbar mToolbar;
    private EventsManager mManager;
    private Cursor mCursor;
    private TextView mMeetingNameTextView, mVenueTextView, mDateTextView, mTimeTextView, mInviteesTextView;
    private Switch mSpouseSwitch, mChildrenSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_deatils);

        initViews();

        setMeetingsDetails();

    }

    private void setMeetingsDetails() {

        mCursor.moveToFirst();

        mMeetingNameTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_NAME)));
        mVenueTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_VENUE)));
        mDateTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_DATE)));
        mVenueTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_TIME)));
        mInviteesTextView.setText(mCursor.getString(mCursor.getColumnIndex(TABLE_COUNT))+" Tables");

        mSpouseSwitch.setChecked(Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(IS_SPOUSE))));
        mChildrenSwitch.setChecked(Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(IS_CHILDREN))));

    }

    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.actionToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.meeting_detail_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mManager = new EventsManager(this);

        String eventId = getIntent().getStringExtra(EVENT_ID);
        mCursor = mManager.getEventDetails(eventId);

        mMeetingNameTextView = (TextView) findViewById(R.id.meetingNameTextView);
        mVenueTextView = (TextView) findViewById(R.id.venueTextView);
        mDateTextView = (TextView) findViewById(R.id.dateTextView);
        mTimeTextView = (TextView) findViewById(R.id.timeTextView);
        mInviteesTextView = (TextView) findViewById(R.id.inviteesTextView);
        mSpouseSwitch = (Switch) findViewById(R.id.spouceSwitch);
        mChildrenSwitch = (Switch)findViewById(R.id.childrenSwitch);

    }
}
