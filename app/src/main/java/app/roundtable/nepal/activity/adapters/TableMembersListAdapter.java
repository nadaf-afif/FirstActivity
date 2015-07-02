package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.ProfileViewActivity;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.network.ApiUrls;

/**
 * Created by afif on 13/6/15.
 */
public class TableMembersListAdapter extends RecyclerView.Adapter<TableMembersListAdapter.ViewHolder> implements Tables.Members{

    private Context mContext;
    private LayoutInflater mInflater;
    private Cursor mCursor;

    public TableMembersListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mCursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_items_table_members,parent,false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        String userName = mCursor.getString(mCursor.getColumnIndex(MEMBER_FIRST_NAME)).toUpperCase() + " "+ mCursor.getString(mCursor.getColumnIndex(MEMBER_LAST_NAME)).toUpperCase();
        holder.mUserNameTextView.setText(userName);

        holder.mEmailTextView.setText(mCursor.getString(mCursor.getColumnIndex(EMAIL)));
        holder.mPhoneNumberTextView.setText(mCursor.getString(mCursor.getColumnIndex(RESIDENCE_PHONE)));

        String imageUrl = ApiUrls.BASE_URL_PATH + mCursor.getString(mCursor.getColumnIndex(IMAGE_THUMB_URL));
        Picasso.with(mContext).load(imageUrl).into(holder.mAvatarImageView);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarImageView;
        TextView mUserNameTextView, mEmailTextView, mPhoneNumberTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mAvatarImageView = (ImageView) itemView.findViewById(R.id.avtarImageView);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.userNameTextView);
            mEmailTextView = (TextView) itemView.findViewById(R.id.userEmailTextView);
            mPhoneNumberTextView = (TextView) itemView.findViewById(R.id.userPhoneTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCursor.moveToPosition(getPosition());
                    Intent intent = new Intent(mContext, ProfileViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(MEMBER_ID, mCursor.getString(mCursor.getColumnIndex(MEMBER_ID)));
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

        }
    }

}
