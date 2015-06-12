package app.roundtable.nepal.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.ClubTablesAdapter;

/**
 * Created by afif on 7/6/15.
 */
public class RTNClubsFragment extends Fragment {

    private RecyclerView mTableNameGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragement_rtn_clubs_list,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTableNameGridView = (RecyclerView) view.findViewById(R.id.tableNamesGridRecyclerView);

        mTableNameGridView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        ClubTablesAdapter adapter = new ClubTablesAdapter(getActivity());
        mTableNameGridView.setAdapter(adapter);

    }
}
