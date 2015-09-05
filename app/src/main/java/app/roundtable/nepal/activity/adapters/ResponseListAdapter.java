package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.databeans.ResponseDataBean;

/**
 * Created by afif on 23/8/15.
 */
public class ResponseListAdapter extends RecyclerView.Adapter<ResponseListAdapter.ResponseViewHolder>{

    private List<ResponseDataBean> mResponseData;
    private Context mContext;
    private LayoutInflater mInflater;

    public ResponseListAdapter(List<ResponseDataBean> responseData, Context context) {
        this.mResponseData = responseData;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ResponseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_list_rsvp,parent,false);
        ResponseViewHolder holder = new ResponseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ResponseViewHolder holder, int position) {

        ResponseDataBean dataBean = mResponseData.get(position);
        holder.mResponseDateTextView.setText("On "+dataBean.getResponseDate());
        holder.mUserNameTextView.setText(dataBean.getMemberName() + " ("+dataBean.getTableName()+")");

        String responseStatus = dataBean.getStatus();
        if (responseStatus.equals("yes"))
            holder.mResponseStatusImageView.setImageResource(R.drawable.ic_yes);
        else if (responseStatus.equals("no"))
            holder.mResponseStatusImageView.setImageResource(R.drawable.ic_no);
        else
            holder.mResponseStatusImageView.setImageResource(R.drawable.ic_maybe);
    }

    @Override
    public int getItemCount() {
        return mResponseData.size();
    }


    class ResponseViewHolder extends RecyclerView.ViewHolder{

        private TextView mUserNameTextView, mResponseDateTextView;
        private ImageView mResponseStatusImageView;

        public ResponseViewHolder(View itemView) {
            super(itemView);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.userNameTextView);
            mResponseDateTextView = (TextView) itemView.findViewById(R.id.responseDateTextView);
            mResponseStatusImageView = (ImageView) itemView.findViewById(R.id.responseStatusImageView);
        }
    }

}
