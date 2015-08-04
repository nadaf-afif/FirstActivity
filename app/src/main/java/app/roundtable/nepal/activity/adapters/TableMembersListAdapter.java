package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.ProfileViewActivity;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.network.ApiUrls;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by afif on 13/6/15.
 */
public class TableMembersListAdapter extends RecyclerView.Adapter<TableMembersListAdapter.ViewHolder> implements Tables.Members{

    private Context mContext;
    private LayoutInflater mInflater;
    private Cursor mCursor;
    private String mTableName, mMemberTableName;

    public TableMembersListAdapter(Context context, Cursor cursor, String tableName, String memberTableName) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mCursor = cursor;
        mTableName = tableName;
        mMemberTableName = memberTableName;
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
        holder.mPhoneNumberTextView.setText(mCursor.getString(mCursor.getColumnIndex(MOBILE)));

        String imageUrl = ApiUrls.BASE_URL_PATH + mCursor.getString(mCursor.getColumnIndex(IMAGE_THUMB_URL));
        Picasso.with(mContext).load(imageUrl).into(holder.mAvatarImageView);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView mAvatarImageView;
        TextView mUserNameTextView, mEmailTextView, mPhoneNumberTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mAvatarImageView = (CircleImageView) itemView.findViewById(R.id.avtarImageView);
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
                    bundle.putString("table_name",mTableName);
                    bundle.putString("tableName",mMemberTableName);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });


            mEmailTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCursor.moveToPosition(getPosition());
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{mCursor.getString(mCursor.getColumnIndex(EMAIL))});

                    try {
                        mContext.startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext , "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            mPhoneNumberTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCursor.moveToPosition(getPosition());
                    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse("tel:" + mCursor.getString(mCursor.getColumnIndex(MOBILE))));
                    mContext.startActivity(phoneIntent);
                }
            });

        }
    }

}
