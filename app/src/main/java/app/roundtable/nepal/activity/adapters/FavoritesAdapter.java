package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.network.ApiUrls;

/**
 * Created by afif on 8/6/15.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> implements Tables.Favorites{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Cursor mCursor;

    public FavoritesAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCursor = cursor;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.grid_item_favorites,parent,false);
        FavoritesViewHolder holder = new FavoritesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        holder.mMerchantTextView.setText(mCursor.getString(mCursor.getColumnIndex(BRAND_NAME)));

        String imagePath = ApiUrls.BASE_URL_PATH + mCursor.getString(mCursor.getColumnIndex(BRAND_LOGO_URL));
        Picasso.with(mContext).load(imagePath).placeholder(R.drawable.rtn_logo).into(holder.mMerchantImageView);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder{

        private ImageView mMerchantImageView;
        private TextView mMerchantTextView;

        public FavoritesViewHolder(View itemView) {
            super(itemView);

            mMerchantImageView = (ImageView) itemView.findViewById(R.id.favoriteImageView);
            mMerchantTextView = (TextView)itemView.findViewById(R.id.merchantNameTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCursor.moveToPosition(getPosition());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+mCursor.getString(mCursor.getColumnIndex(BRAND_WEBSITE_URL))));
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
