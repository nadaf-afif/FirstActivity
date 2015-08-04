package app.roundtable.nepal.activity.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.MeetingDetailsActivity;
import app.roundtable.nepal.activity.adapters.MeetingsListAdapter;
import app.roundtable.nepal.activity.asynktasks.GetEventsAsyncTasks;
import app.roundtable.nepal.activity.asynktasks.GetMeetingsAsyncTask;
import app.roundtable.nepal.activity.interfaces.DataLoader;
import app.roundtable.nepal.activity.network.NetworkManager;

/**
 * Created by afif on 4/6/15.
 */
public class MeetingsFragment extends Fragment implements DataLoader{

    private RecyclerView mRecyclerView;
    private GetMeetingsAsyncTask mAsyncTask;
    private MeetingsListAdapter mAdapter;
    public static final String tag = MeetingsFragment.class.getSimpleName();
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_meetings_list,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.meetingRecyclerView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mEmptyTextView = (TextView) view.findViewById(R.id.emptyListTextView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getFirstPageData();
    }

    @Override
    public void getFirstPageData() {

        mAsyncTask = new GetMeetingsAsyncTask(getActivity(), this);
        if(NetworkManager.isConnectedToInternet(getActivity())){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                mAsyncTask.execute(tag);

        }else {

            onNoInternet();
        }
    }

    @Override
    public void setFirstPageData(Cursor cursor) {

        if(isAdded()) {
            if (cursor.getCount() > 0) {

                mEmptyTextView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                mAdapter = new MeetingsListAdapter(getActivity(), cursor);
                mRecyclerView.setAdapter(mAdapter);
            } else
                onNoData();
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
        mEmptyTextView.setText(getString(R.string.no_meeting));
    }
}
