package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.EventDetailsActivity;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.widget.BezelImageView;

/**
 * Created by afif on 24/6/15.
 */
public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.EventHolder>  implements Tables.Events{


    private Context mContext;
    private LayoutInflater mInflater;
    private Cursor mCursor;

    public EventsListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mCursor = cursor;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_item_event,parent, false);

        EventHolder holder = new EventHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {

        mCursor.moveToPosition(position);

        holder.mEventNameTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_NAME)));
        holder.mEventVenueTextView.setText(mCursor.getString(mCursor.getColumnIndex(EVENT_VENUE)));

        String date = mCursor.getString(mCursor.getColumnIndex(EVENT_DATE));
        String time = mCursor.getString(mCursor.getColumnIndex(EVENT_TIME));
        holder.mEventDateTimeTextView.setText(date + " " + time);

        String imageUrl = ApiUrls.BASE_URL_PATH + mCursor.getString(mCursor.getColumnIndex(EVENT_THUMB_IMAGE));
        Picasso.with(mContext).load(imageUrl).into(holder.mEventPhotoImageView);
    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }

    public class EventHolder extends RecyclerView.ViewHolder{

        private BezelImageView mEventPhotoImageView;
        private TextView mEventNameTextView, mEventVenueTextView, mEventDateTimeTextView;

        public EventHolder(View itemView) {
            super(itemView);

            mCursor.moveToPosition(getPosition());
            mEventPhotoImageView = (BezelImageView) itemView.findViewById(R.id.eventPhotoImageView);
            mEventNameTextView = (TextView) itemView.findViewById(R.id.eventNameTextView);
            mEventVenueTextView = (TextView) itemView.findViewById(R.id.eventVenueNameTextView);
            mEventDateTimeTextView = (TextView) itemView.findViewById(R.id.eventDateTimeTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, EventDetailsActivity.class);
                    intent.putExtra(EVENT_ID, mCursor.getString(mCursor.getColumnIndex(EVENT_ID)));
                    mContext.startActivity(intent);
                }
            });

        }
    }

}
