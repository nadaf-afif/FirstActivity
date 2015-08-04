package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.MeetingDetailsActivity;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.widget.BezelImageView;

/**
 * Created by afif on 25/6/15.
 */
public class MeetingsListAdapter extends RecyclerView.Adapter<MeetingsListAdapter.MeetingHolder> implements Tables.Events{

    private Context mContext;
    private Cursor mCursor;
    private LayoutInflater mInflater;

    public MeetingsListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MeetingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_item_metting, parent, false);

        MeetingHolder holder = new MeetingHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MeetingHolder holder, int position) {

        mCursor.moveToPosition(position);
        holder.mMeetingNameTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_NAME)));
        holder.mMeetingVenueTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_VENUE)));
        holder.mMeetingDateTimeTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_DATE)));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class MeetingHolder extends RecyclerView.ViewHolder{

        private TextView mMeetingNameTextView, mMeetingVenueTextView, mMeetingDateTimeTextView;

        public MeetingHolder(View itemView) {
            super(itemView);

            mMeetingNameTextView = (TextView) itemView.findViewById(R.id.meetingNameTextView);
            mMeetingVenueTextView = (TextView) itemView.findViewById(R.id.meetingVenueNameTextView);
            mMeetingDateTimeTextView = (TextView) itemView.findViewById(R.id.meetingDateTimeTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getPosition();
                    mCursor.moveToPosition(position);
                    String eventId = mCursor.getString(mCursor.getColumnIndex(EVENT_ID));
                    Intent intent = new Intent(mContext, MeetingDetailsActivity.class);
                    intent.putExtra(EVENT_ID,eventId );
                    mContext.startActivity(intent);

                }
            });
        }
    }


}
