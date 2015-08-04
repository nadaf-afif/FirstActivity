package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.database.Tables;

/**
 * Created by afif on 7/7/15.
 */
public class ConvenersListAdapter extends RecyclerView.Adapter<ConvenersListAdapter.ConvenerHolder> implements Tables.Conveners{

    private Context mContext;
    private Cursor mCursor;
    private LayoutInflater mInflater;

    public ConvenersListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ConvenerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_item_conveners,parent,false);
        ConvenerHolder holder = new ConvenerHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ConvenerHolder holder, int position) {

        mCursor.moveToPosition(position);

        holder.nameTextView.setText(mCursor.getString(mCursor.getColumnIndex(CONVENER_NAME)));
        holder.emailTextView.setText(mCursor.getString(mCursor.getColumnIndex(CONVENER_EMAIL)));
        holder.designationTextView.setText(mCursor.getString(mCursor.getColumnIndex(DESIGNATION)));
        holder.mobileTextView.setText(mCursor.getString(mCursor.getColumnIndex(CONVENER_MOBILE)));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class ConvenerHolder extends RecyclerView.ViewHolder{

        private TextView designationTextView, nameTextView, mobileTextView, emailTextView;

        public ConvenerHolder(View itemView) {
            super(itemView);

            designationTextView = (TextView) itemView.findViewById(R.id.designationTextView);
            nameTextView = (TextView) itemView.findViewById(R.id.convenerNameTextView);
            mobileTextView = (TextView) itemView.findViewById(R.id.convenersMobileTextView);
            emailTextView = (TextView) itemView.findViewById(R.id.convenersEmailTextView);

            mobileTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCursor.moveToPosition(getPosition());
                    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse("tel:" + mCursor.getString(mCursor.getColumnIndex(CONVENER_MOBILE))));
                    mContext.startActivity(phoneIntent);
                }
            });


            emailTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCursor.moveToPosition(getPosition());
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{mCursor.getString(mCursor.getColumnIndex(CONVENER_EMAIL))});

                    try {
                        mContext.startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

}
