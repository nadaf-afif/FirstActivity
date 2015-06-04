package app.roundtable.nepal.activity.navigation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.databeans.RecyclerData;


/**
 * Created by afif on 13/5/15.
 */
public class RoundTableMenuAdapter extends RecyclerView.Adapter<RoundTableMenuAdapter.MyViewHolder>{

    private LayoutInflater mInflater;
    List<RecyclerData> data = Collections.emptyList();
    private ClickListener mClickListener;

    public RoundTableMenuAdapter(Context context, List<RecyclerData> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = mInflater.inflate(R.layout.item_navigation_item,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        RecyclerData currentData = data.get(position);
        myViewHolder.iconIV.setImageResource(currentData.getIconId());
        myViewHolder.titleTV.setText(currentData.getItemLabel());

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iconIV;
        private TextView titleTV;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            iconIV = (ImageView) itemView.findViewById(R.id.iconImageView);
            titleTV = (TextView)itemView.findViewById(R.id.titleTextView);
        }

        @Override
        public void onClick(View v) {

            if(mClickListener!=null)
            {
                mClickListener.itemClick(v,getPosition());
            }
        }
    }


    public void setClickListener(ClickListener clickListener){
        mClickListener = clickListener;

    }

    public interface ClickListener{

        public void itemClick(View view, int position);
    }


 }
