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
import app.roundtable.nepal.activity.activity.TableMembersListActivity;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.network.ApiUrls;

/**
 * Created by afif on 7/6/15.
 */
public class ClubTablesAdapter extends RecyclerView.Adapter<ClubTablesAdapter.ViewHolder> implements Tables.RTNTables{

//    private int[] mLogos = new int[]{R.drawable.kathmandu_logo, R.drawable.kert_2_logo, R.drawable.birganj_logo,R.drawable.kathmandu_city_logo,
//                                    R.drawable.biratnagar_logo, R.drawable.siddharthnagar_logo,R.drawable.kathmandu_2_logo, R.drawable.birgunj_friends_logo,
//                                    R.drawable.roundtable_10,R.drawable.birtanagar_platinum_logo,R.drawable.kathmandu_city_logo,R.drawable.birgunj_friends_logo, R.drawable.logo14,
//                                    R.drawable.biratnagar_central_logo, R.drawable.lumbini_round_table, R.drawable.kathmandu_united_logo,R.drawable.biratnagar_sapphire_logo ,R.drawable.kathmandu_blue_logo, R.drawable.birgunj_rising_logo};
//
//    private String[] mTableNames = new String[] {"KRT", "KERT", "BRT", "KMRT", "BRT", "SRT", "KIRT", "BFRT", "BPRT-10", "BPRT-11", "KCRT", "BYRT",
//                                                "NRT", "BCRT", "LRT", "KURT", "BSRT", "KBRT", "BRRT" };

    private Context mContext;
    private LayoutInflater mInflater;
    private Cursor mCursor;


    public ClubTablesAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mCursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.grid_item_tables,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        holder.mTableName.setText(mCursor.getString(mCursor.getColumnIndex(TABLE_DESCRIPTION)));
        String memberCount = mCursor.getString(mCursor.getColumnIndex(TABLE_MEMBERS_COUNT));

        if(memberCount.equals("0"))
            holder.mMemberCountTextView.setText( "No member found");
        else
            holder.mMemberCountTextView.setText( memberCount+" Members");

        Picasso.with(mContext).load(ApiUrls.BASE_URL_PATH + mCursor.getString(mCursor.getColumnIndex(TABLE_THUMB_URL))).into(holder.mTableLogo);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mTableLogo;
        private TextView mTableName, mMemberCountTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mTableLogo = (ImageView) itemView.findViewById(R.id.tableLogoImageView);
            mTableName = (TextView) itemView.findViewById(R.id.tableNameGridTextView);
            mMemberCountTextView = (TextView) itemView.findViewById(R.id.numberOfMembersTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCursor.moveToPosition(getPosition());
                    Intent intent = new Intent(mContext, TableMembersListActivity.class);
                    Bundle bundle= new Bundle();
                    bundle.putString("tableName", mCursor.getString(mCursor.getColumnIndex(TABLE_DESCRIPTION)));
                    bundle.putString(TABLE_ID, mCursor.getString(mCursor.getColumnIndex(TABLE_ID)));
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
