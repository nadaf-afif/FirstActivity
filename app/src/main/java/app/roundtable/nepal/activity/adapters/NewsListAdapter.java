package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.NewsDetailsActivity;

/**
 * Created by afif on 8/6/15.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>{


    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public NewsListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.list_item_news,parent,false);
        NewsViewHolder holder = new NewsViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {





    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        private ImageView mNewsPhotoIV;
        private TextView mNewsHeadLineTV, mNewsDescriptionTV;

        public NewsViewHolder(View itemView) {
            super(itemView);

            mNewsDescriptionTV = (TextView) itemView.findViewById(R.id.newsDesriptionTextView);
            mNewsHeadLineTV = (TextView)itemView.findViewById(R.id.newsHeadLineTextView);
            mNewsPhotoIV = (ImageView)itemView.findViewById(R.id.newsImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                    mContext.startActivity(intent);
                }
            });

        }
    }

}
