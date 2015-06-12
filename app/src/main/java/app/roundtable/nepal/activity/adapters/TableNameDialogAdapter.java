package app.roundtable.nepal.activity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.R;

/**
 * Created by afif on 10/6/15.
 */
public class TableNameDialogAdapter extends ArrayAdapter<String> {

    private String[] tableNames;
    private Context mContext;
    private ArrayList<Boolean> selected = new ArrayList<>();

    public TableNameDialogAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        mContext = context;
        tableNames = objects;
    }

    @Override
    public int getCount() {
        return tableNames.length + 1;
    }

    @Override
    public String getItem(int position) {
        return tableNames[position];
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){

            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item_table_names,parent,false);

            holder.mtableName = (TextView) convertView.findViewById(R.id.tableNameTextView);
            holder.mCheckBox = (CheckBox)convertView.findViewById(R.id.tableCheckBox);

            convertView.setTag(holder);
        }else{

            holder= (ViewHolder) convertView.getTag();
        }


        if(position == 0)
        {
            holder.mtableName.setText("Select All");

        }else {

            holder.mtableName.setText(tableNames[position-1]);
        }
        return convertView;
    }


    private class ViewHolder{
        TextView mtableName;
        CheckBox mCheckBox;
    }

}
