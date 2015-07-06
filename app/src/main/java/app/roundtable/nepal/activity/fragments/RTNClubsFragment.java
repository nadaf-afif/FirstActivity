package app.roundtable.nepal.activity.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.ClubTablesAdapter;
import app.roundtable.nepal.activity.asynktasks.GetTablesAsyncTasks;
import app.roundtable.nepal.activity.interfaces.DataLoader;
import app.roundtable.nepal.activity.network.NetworkManager;

/**
 * Created by afif on 7/6/15.
 */
public class RTNClubsFragment extends Fragment implements DataLoader{

    private RecyclerView mTableNameGridView;
    private GetTablesAsyncTasks mAsyncTasks;
    private ClubTablesAdapter mAdapter;
    public static final String tag = RTNClubsFragment.class.getSimpleName();

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

        getFirstPageData();
    }

    @Override
    public void getFirstPageData() {

        if(NetworkManager.isConnectedToInternet(getActivity())){

            executeAsyncTask();
        }

    }

    private void executeAsyncTask() {

        mAsyncTasks = new GetTablesAsyncTasks(getActivity(),this);

        mAsyncTasks.execute();
    }

    @Override
    public void setFirstPageData(Cursor cursor) {

        if(isAdded()) {
            mAdapter = new ClubTablesAdapter(getActivity(), cursor);

            mTableNameGridView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onNoInternet() {



    }

    @Override
    public void onNoData() {


    }
}
