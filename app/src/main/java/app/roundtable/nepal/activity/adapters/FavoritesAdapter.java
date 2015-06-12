package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.roundtable.nepal.R;

/**
 * Created by afif on 8/6/15.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public FavoritesAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.grid_item_favorites,parent,false);
        FavoritesViewHolder holder = new FavoritesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder{

        private ImageView mMerchantIV;
        private TextView mMerchantTV;

        public FavoritesViewHolder(View itemView) {
            super(itemView);

            mMerchantIV = (ImageView) itemView.findViewById(R.id.favoriteImageView);
            mMerchantTV = (TextView)itemView.findViewById(R.id.merchantNameTextView);
        }
    }

}
