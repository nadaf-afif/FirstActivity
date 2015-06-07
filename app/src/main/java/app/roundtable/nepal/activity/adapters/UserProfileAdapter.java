package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.databeans.ProfileData;

/**
 * Created by afif on 7/6/15.
 */
public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.RecycleViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<ProfileData> mListdata;


    public UserProfileAdapter(Context context, List<ProfileData> data) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mListdata = data;
    }

    @Override
    public UserProfileAdapter.RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_item_profile,parent,false);
        RecycleViewHolder viewHolder = new RecycleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserProfileAdapter.RecycleViewHolder holder, int position) {

        ProfileData profileData = mListdata.get(position);
        holder.mLabelTextView.setText(profileData.getLabel());
        holder.mValueTextView.setText(profileData.getValue());
    }

    @Override
    public int getItemCount() {
        return mListdata.size();
    }


    class RecycleViewHolder extends RecyclerView.ViewHolder {

        private TextView  mLabelTextView, mValueTextView;

        public RecycleViewHolder (View itemView) {
            super(itemView);
            mLabelTextView = (TextView) itemView.findViewById(R.id.labelTextView);
            mValueTextView = (TextView) itemView.findViewById(R.id.valueTextView);
        }


    }

}
