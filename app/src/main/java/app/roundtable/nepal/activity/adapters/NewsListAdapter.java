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
import app.roundtable.nepal.activity.activity.NewsDetailsActivity;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.interfaces.DataLoader;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.widget.BezelImageView;

/**
 * Created by afif on 8/6/15.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> implements Tables.News{


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Cursor mCursor;
    private DataLoader mDataLoader;

    public NewsListAdapter(Context context, Cursor cursor, DataLoader dataLoader) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCursor = cursor;
        mDataLoader = dataLoader;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.list_item_news,parent,false);
        NewsViewHolder holder = new NewsViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        holder.mNewsHeadLineTV.setText(mCursor.getString(mCursor.getColumnIndex(NEWS_HEADLINE)));
        holder.mNewsDescriptionTV.setText(mCursor.getString(mCursor.getColumnIndex(NEWS_DESCRIPTION)));

        String imageUrl = ApiUrls.BASE_URL_PATH + mCursor.getString(mCursor.getColumnIndex(NEWS_THUMB_URL));
        Picasso.with(mContext).load(imageUrl).into(holder.mNewsPhotoIV);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        private BezelImageView mNewsPhotoIV;
        private TextView mNewsHeadLineTV, mNewsDescriptionTV;

        public NewsViewHolder(View itemView) {
            super(itemView);

            mNewsDescriptionTV = (TextView) itemView.findViewById(R.id.newsDesriptionTextView);
            mNewsHeadLineTV = (TextView)itemView.findViewById(R.id.newsHeadLineTextView);
            mNewsPhotoIV = (BezelImageView)itemView.findViewById(R.id.newsImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mCursor.moveToPosition(getPosition());
                    Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(NEWS_ID, mCursor.getString(mCursor.getColumnIndex(NEWS_ID)));
                    bundle.putString(NEWS_HEADLINE, mCursor.getString(mCursor.getColumnIndex(NEWS_HEADLINE)));
                    bundle.putString(NEWS_DESCRIPTION, mCursor.getString(mCursor.getColumnIndex(NEWS_DESCRIPTION)));
                    bundle.putString(NEWS_BIG_URL, mCursor.getString(mCursor.getColumnIndex(NEWS_BIG_URL)));
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

        }
    }

}
