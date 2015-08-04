package app.roundtable.nepal.activity.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;

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
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mEmptyTextView = (TextView) view.findViewById(R.id.emptyListTextView);

        mTableNameGridView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        getFirstPageData();
    }

    @Override
    public void getFirstPageData() {

        if(NetworkManager.isConnectedToInternet(getActivity())){

            executeAsyncTask();
        }else
            onNoInternet();

    }

    private void executeAsyncTask() {

        mAsyncTasks = new GetTablesAsyncTasks(getActivity(),this);

        mAsyncTasks.execute();
    }

    @Override
    public void setFirstPageData(Cursor cursor) {

        if(isAdded()) {

            mEmptyTextView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            mTableNameGridView.setVisibility(View.VISIBLE);

            mAdapter = new ClubTablesAdapter(getActivity(), cursor);

            mTableNameGridView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onNoInternet() {

        mProgressBar.setVisibility(View.GONE);
        mEmptyTextView.setText(getString(R.string.no_internet_connection));
    }

    @Override
    public void onNoData() {

        mProgressBar.setVisibility(View.GONE);
        mEmptyTextView.setText(getString(R.string.no_members_found));
    }
}
