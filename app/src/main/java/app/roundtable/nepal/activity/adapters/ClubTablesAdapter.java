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
 * Created by afif on 7/6/15.
 */
public class ClubTablesAdapter extends RecyclerView.Adapter<ClubTablesAdapter.ViewHolder> {

    private int[] mLogos = new int[]{R.drawable.kathmandu_logo, R.drawable.kathmandu_2_logo, R.drawable.kathmandu_blue_logo,R.drawable.kathmandu_city_logo,
                                    R.drawable.kathmandu_united_logo, R.drawable.biratnagar_logo,R.drawable.birtanagar_platinum_logo, R.drawable.biratnagar_central_logo,
                                    R.drawable.biratnagar_sapphire_logo,R.drawable.birganj_logo,R.drawable.birgunj_rising_logo,R.drawable.birgunj_friends_logo,
                                    R.drawable.kert_2_logo, R.drawable.logo14, R.drawable.siddharthnagar_logo,R.drawable.lumbini_round_table ,R.drawable.roundtable_10};

    private String[] mTableNames = new String[] {"Kathmandu Round Table", "Kathmandu 2 Round Table", "Kathmandu Blue Round Table", "Kathmandu City Round Table",
                                                "Kathmandu United Round Table", "BiratNagar Round Table", "BiratNagar Platinum Round Table", "BiratNagar Central Round Table",
                                                "BiratNagar Sapphire Round Table", "Birganj Round Table", "Birganj Rising Round Table", "Birgunj Friends Round Table",
                                                "Kert 2 Table Name", "14 Round Table", "SiddharthNagar Round Table", "Lumbini Round Table", "10 Round Table"};

    private Context mContext;
    private LayoutInflater mInflater;


    public ClubTablesAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.grid_item_tables,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTableName.setText(mTableNames[position]);
        holder.mTableLogo.setImageResource(mLogos[position]);
    }

    @Override
    public int getItemCount() {
        return mTableNames.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mTableLogo;
        private TextView mTableName;

        public ViewHolder(View itemView) {
            super(itemView);

            mTableLogo = (ImageView) itemView.findViewById(R.id.tableLogoImageView);
            mTableName = (TextView) itemView.findViewById(R.id.tableNameGridTextView);
        }
    }

}
