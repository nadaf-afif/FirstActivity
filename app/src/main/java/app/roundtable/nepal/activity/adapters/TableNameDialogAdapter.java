package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.databeans.TablesInfoBean;

/**
 * Created by afif on 10/6/15.
 */
public class TableNameDialogAdapter extends BaseAdapter {

    public ArrayList<TablesInfoBean> tableNames ;
    private Context mContext;
    public Set<String> mSelectedId ;

    public TableNameDialogAdapter(Context context, ArrayList<TablesInfoBean> objects, Set<String> selectedId) {
        mContext = context;
        tableNames = objects;
        mSelectedId = selectedId;

    }

    @Override
    public int getCount() {
        return tableNames.size();
    }

    @Override
    public String getItem(int position) {
        return tableNames.get(position).getTableName();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if(convertView == null){

            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item_table_names,parent,false);

            holder.mtableName = (TextView) convertView.findViewById(R.id.tableNameTextView);
            holder.mCheckBox = (CheckBox)convertView.findViewById(R.id.tableCheckBox);




            convertView.setTag(holder);

            final ViewHolder viewHolder = holder;
            holder.mCheckBox.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    int tag = (int) v.getTag();
                    boolean isChecked = viewHolder.mCheckBox.isChecked();

                    if (tag > 0) {

                        if (isChecked ) {

                            tableNames.get(tag).setIsSelected(true);
                            mSelectedId.add(tableNames.get(tag).getTableId());

                        } else  {

                            tableNames.get(tag).setIsSelected(false);
                            mSelectedId.remove(tableNames.get(tag).getTableId());

                        }

                    } else if (isChecked) {


                        for (int j = 0; j < tableNames.size(); j++) {

                            tableNames.get(j).setIsSelected(true);

                            if (!tableNames.get(j).getTableId().equals("0")) {
                                mSelectedId.add(tableNames.get(j).getTableId());
                            }
                        }

                        notifyDataSetChanged();

                    } else {


                        for (int j = 0; j < tableNames.size(); j++) {

                                tableNames.get(j).setIsSelected(false);
                                mSelectedId.remove(tableNames.get(j).getTableId());

                        }

                        notifyDataSetChanged();
                    }

                }


            });


        }else{

            holder= (ViewHolder) convertView.getTag();
        }

        holder.mCheckBox.setTag(position);

        holder.mtableName.setText(tableNames.get(position).getTableName());
        holder.mCheckBox.setChecked(tableNames.get(position).isSelected());


        return convertView;
    }


    private class ViewHolder{
        TextView mtableName;
        CheckBox mCheckBox;
    }

}
